package com.khrekrn.users.storage

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user", schema = "public")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String?,

    @Column(name = "email_id")
    var emailId: String?,

    var createdAt: LocalDateTime? = LocalDateTime.now()

)

@Repository
interface UserRepo : CrudRepository<User, Long> {

}