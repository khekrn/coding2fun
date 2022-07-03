package com.khrekrn.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlockingUsersApplication

fun main(args: Array<String>) {
	runApplication<BlockingUsersApplication>(*args)
}
