package com.khrekrn.coroutineusers

import NettyConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
@Import(NettyConfig::class)
class CoroutineUsersApplication

fun main(args: Array<String>) {
    System.setProperty("reactor.netty.ioWorkerCount", "8")
    System.setProperty("reactor.netty.pool.maxConnections", "10000")
    System.setProperty("reactor.netty.native", "true")
    runApplication<CoroutineUsersApplication>(*args)
}
