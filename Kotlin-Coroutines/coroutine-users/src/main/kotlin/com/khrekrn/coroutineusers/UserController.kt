package com.khrekrn.coroutineusers

import com.khrekrn.coroutineusers.client.AvatarClient
import com.khrekrn.coroutineusers.client.EmailClient
import com.khrekrn.coroutineusers.storage.User
import com.khrekrn.coroutineusers.storage.UserRepo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

data class UserModel(val name: String, val emailId: String)

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userRepo: UserRepo,
    private val avatarClient: AvatarClient,
    private val emailClient: EmailClient
) {

    @PostMapping("/reactor/user")
    suspend fun storeUser(
        @RequestBody model: UserModel,
        @RequestParam(required = true) delay: Long
    ): ResponseEntity<Any> {
        if (!emailClient.validateEmail(model.emailId, delay)) {
            return ResponseEntity.internalServerError().body("Internal Server Error")
        }

        val avatar = avatarClient.getAvatar(delay)
        val userEntity = User(null, model.name, model.emailId, avatar.url, LocalDateTime.now())

        val user = userRepo.save(userEntity)

        return ResponseEntity.ok(user)
    }
}


