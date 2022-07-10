package com.khrekrn.coroutineusers.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun customWebClient(): WebClient {
//        val provider = ConnectionProvider.builder(WebClientConfig::class.simpleName!!)
//            .maxConnections(64)
//            .pendingAcquireTimeout(Duration.ofSeconds(30))
//            .maxIdleTime(Duration.ofSeconds(2))
//            .maxLifeTime(Duration.ofSeconds(5))
//            .evictInBackground(Duration.ofSeconds(5))
//            .lifo()
//            .build()
//
//        val httpClient: HttpClient = HttpClient.create(provider)
//            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000)
//            .option(ChannelOption.SO_KEEPALIVE, true)
//            .doOnConnected { conn ->
//                conn
//                    .addHandlerFirst(ReadTimeoutHandler(20, TimeUnit.SECONDS))
//                    .addHandlerLast(WriteTimeoutHandler(20))
//            }.responseTimeout(Duration.ofSeconds(25))

        return WebClient.create()
    }
}