package com.khrekrn.coroutineusers.client

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity

data class Avatar @JsonCreator constructor(@JsonProperty("url") val url: String)

@Service
class AvatarClient(private val webClient: WebClient) {

    suspend fun getAvatar(delay: Long): Avatar {
        val uri = "http://localhost:8081/avatar?delay=$delay"
        return webClient.get()
            .uri(uri)
            .retrieve()
            .toEntity<Avatar>()
            .awaitSingle().body!!
    }
}

@Service
class EmailClient(private val webClient: WebClient) {
    suspend fun validateEmail(email: String, delay: Long): Boolean {
        val uri = "http://localhost:8081/echo/$email?delay=$delay"
        val responseEntity = webClient.get()
            .uri(uri)
            .retrieve()
            .toEntity<String>()
            .awaitSingle().body!!
        println("Result = $responseEntity")
        return true
    }
}