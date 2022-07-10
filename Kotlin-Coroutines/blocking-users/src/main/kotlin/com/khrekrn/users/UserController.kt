package com.khrekrn.users

import com.khrekrn.users.client.AvatarClient
import com.khrekrn.users.client.EmailClient
import com.khrekrn.users.storage.User
import com.khrekrn.users.storage.UserRepo
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

    @PostMapping("/blocking/user")
    fun storeUser(@RequestBody model: UserModel, @RequestParam(required = true) delay: Long): ResponseEntity<Any> {

        if (!emailClient.validateEmail(model.emailId, delay)) {
            return ResponseEntity.internalServerError().body("Internal Server Error")
        }

        val avatar = avatarClient.getAvatar(delay)
        val userEntity = User()
        userEntity.name = model.name
        userEntity.emailId = model.emailId
        userEntity.createdAt = LocalDateTime.now()
        userEntity.avatar = avatar.url

        val user = userRepo.save(userEntity)

        return ResponseEntity.ok(user)
    }
}


