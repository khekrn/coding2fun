package com.khrekrn.users.storage

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
open class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    open var name: String? = null

    @Column(name = "email_id")
    open var emailId: String? = null

    open var avatar: String? = null

    open var createdAt: LocalDateTime? = null

    open var updatedAt: LocalDateTime? = LocalDateTime.now()
}

@Repository
interface UserRepo : CrudRepository<User, Long> {

}