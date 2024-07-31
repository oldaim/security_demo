package org.toy.security_demo.entity

import jakarta.persistence.*
import org.toy.security_demo.constants.UserRoleType

@Entity
@Table(name = "demo_user")
data class DemoUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false, unique = true, length = 20)
    val userId: String? = null,

    @Column(name = "user_name", nullable = false, unique = true, length = 20)
    val username: String? = null,

    @Column(name = "password_hash", nullable = false, length = 255)
    val passwordHash: String? = null,

    @Column(name = "email", nullable = true, length = 255)
    val email: String? = null,

    @Column(name = "phone_number", nullable = true, length = 255)
    val phoneNumber: String? = null,

    @Column(name = "user_authority", nullable = true, length = 255)
    val role: UserRoleType? = null
)
