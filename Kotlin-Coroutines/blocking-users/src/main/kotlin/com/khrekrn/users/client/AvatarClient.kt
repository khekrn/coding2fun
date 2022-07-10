package com.khrekrn.users.client

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

data class Avatar @JsonCreator constructor(@JsonProperty("url") val url: String)

@Service
class AvatarClient(private val restTemplate: RestTemplate) {

    fun getAvatar(delay: Long): Avatar {
        val uri = "http://localhost:8081/avatar?delay=$delay"
        return restTemplate.getForEntity(uri, Avatar::class.java).body!!
    }
}

@Service
class EmailClient(private val restTemplate: RestTemplate) {
    fun validateEmail(email: String, delay: Long): Boolean {
        val uri = "http://localhost:8081/echo/$email?delay=$delay"
        val responseEntity = restTemplate.getForEntity(uri, String::class.java)
        println("Result = $responseEntity")
        return true
    }
}