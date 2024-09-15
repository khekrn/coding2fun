import io.netty.channel.ChannelOption
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollChannelOption
import io.netty.channel.kqueue.KQueue
import io.netty.channel.unix.UnixChannelOption
import org.slf4j.LoggerFactory
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.netty.http.server.HttpServer
import reactor.netty.resources.LoopResources
import java.time.Duration

@Configuration
class NettyConfig : WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    private val logger = LoggerFactory.getLogger(NettyConfig::class.java)

    @Bean
    fun loopResources(): LoopResources {
        return LoopResources.create("event-loop", 1, WORKER_COUNT, true)
    }

    override fun customize(factory: NettyReactiveWebServerFactory) {
        factory.addServerCustomizers({ httpServer: HttpServer ->
            httpServer.doOnChannelInit { _, channel, _ ->
                channel.config().apply {
                    setOption(ChannelOption.SO_KEEPALIVE, true)
                    setOption(ChannelOption.SO_BACKLOG, 1000)
                    setOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    setOption(ChannelOption.TCP_FASTOPEN, 3000)
                }
                configureNativeTransport(channel.config())
            }
                .runOn(loopResources())
                .accessLog(false)
                .wiretap(false)
                .idleTimeout(Duration.ofSeconds(300))
                .compress(true)
                .doOnBound { logTransportInfo() }
        })
    }

    private fun configureNativeTransport(config: io.netty.channel.ChannelConfig) {
        when {
            isLinux() -> {
                config.setOption(EpollChannelOption.TCP_KEEPIDLE, 300)
                config.setOption(EpollChannelOption.TCP_KEEPINTVL, 60)
                config.setOption(EpollChannelOption.TCP_KEEPCNT, 8)
                config.setOption(EpollChannelOption.SO_REUSEPORT, true)
            }

            isMac() -> {
                config.setOption(UnixChannelOption.SO_REUSEPORT, true)
                // macOS doesn't support direct configuration of TCP keepalive intervals via channel options
            }
        }
    }

    private fun logTransportInfo() {
        logger.info("OS: ${System.getProperty("os.name")}")
        logger.info("OS Arch: ${System.getProperty("os.arch")}")
        logger.info("Java Version: ${System.getProperty("java.version")}")
        logger.info("Epoll available: ${Epoll.isAvailable()}")
        logger.info("KQueue available: ${KQueue.isAvailable()}")

        val transport = when {
            isLinux() && Epoll.isAvailable() -> "Epoll"
            isMac() && KQueue.isAvailable() -> "KQueue"
            else -> "NIO"
        }
        logger.info("Using transport: $transport")

        if (isMac() && !KQueue.isAvailable()) {
            logger.warn("KQueue is not available. Checking for potential issues...")
            checkKQueueAvailability()
        }
    }

    private fun checkKQueueAvailability() {
        try {
            val kqueueClass = Class.forName("io.netty.channel.kqueue.KQueueEventLoop")
            logger.info("KQueue classes are present on the classpath")
        } catch (e: ClassNotFoundException) {
            logger.warn("KQueue classes are not found on the classpath. Make sure netty-transport-native-kqueue dependency is included.")
        }

        val javaLibraryPath = System.getProperty("java.library.path")
        logger.info("Java Library Path: $javaLibraryPath")

        // Check if running on M1 Mac
        if (System.getProperty("os.arch") == "aarch64") {
            logger.info("Running on M1 Mac. Ensure you're using the correct version of netty-transport-native-kqueue for ARM64.")
        }
    }

    companion object {
        private const val WORKER_COUNT = 8

        private val name = System.getProperty("os.name").lowercase()

        fun isLinux(): Boolean = name.contains("linux")
        fun isMac(): Boolean = name.contains("mac")
    }
}