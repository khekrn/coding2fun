package com.khrekrn.coroutineusers.storage

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Table(name = "user", schema = "public")
data class User(

    @Id
    var id: Long? = null,

    var name: String?,

    @Column("email_id")
    var emailId: String?,

    var avatar: String? = null,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = LocalDateTime.now()

)

@Repository
interface UserRepo : CoroutineCrudRepository<User, Long> {

}