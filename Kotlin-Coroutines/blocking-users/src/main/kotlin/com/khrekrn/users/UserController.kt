package com.khrekrn.users

import com.khrekrn.users.storage.User
import com.khrekrn.users.storage.UserRepo
import org.springframework.web.bind.annotation.*

data class UserModel(val name: String, val emailId: String)

@RestController
@RequestMapping("/api/v1")
class UserController(private val userRepo: UserRepo) {

    @PostMapping("/blocking/user")
    @ResponseBody
    fun storeUser(@RequestBody model: UserModel, @RequestParam(required = true) wait: Long): User {
        Thread.sleep(wait)
        val userEntity = User(name = model.name, emailId = model.emailId)
        return userRepo.save(userEntity)
    }
}


