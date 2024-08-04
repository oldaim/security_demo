package org.toy.security_demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.toy.security_demo.entity.DemoUser

interface DemoUserRepository: JpaRepository<DemoUser, Long> {
}