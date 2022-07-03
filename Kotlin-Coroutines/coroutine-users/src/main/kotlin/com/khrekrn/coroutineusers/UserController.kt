package com.khrekrn.coroutineusers

import com.khrekrn.coroutineusers.storage.User
import com.khrekrn.coroutineusers.storage.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.springframework.web.bind.annotation.*

data class UserModel(val name: String, val emailId: String)

@RestController
@RequestMapping("/api/v1")
class UserController(private val userRepo: UserRepo) {

    @PostMapping("/reactor/user")
    @ResponseBody
    suspend fun storeUser(@RequestBody model: UserModel, @RequestParam(required = true) wait: Long): User {
        return withContext(Dispatchers.IO + SupervisorJob()){
            delay(wait)
            val userEntity = User(name = model.name, emailId = model.emailId)
            userRepo.save(userEntity)
        }
    }
}


