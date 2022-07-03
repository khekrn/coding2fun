package com.khrekrn.coroutineusers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoroutineUsersApplication

fun main(args: Array<String>) {
	runApplication<CoroutineUsersApplication>(*args)
}
