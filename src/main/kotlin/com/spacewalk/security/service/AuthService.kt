package com.spacewalk.security.service

import com.spacewalk.domain.user.User
import com.spacewalk.domain.user.dto.UserResDto
import com.spacewalk.domain.user.repository.UserRepository
import com.spacewalk.exception.HttpException
import com.spacewalk.exception.HttpExceptionCode
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder

) {

    fun authenticate(username: String, password: String): User {

        return userRepository.findByUsernameJoinedRoleList(username).orElseThrow {
            throw HttpException(
                HttpStatus.BAD_REQUEST,
                HttpExceptionCode.NOT_FOUND_OR_PASSWORD_NOT_MATCHED,
                "존재하지 않는 사용자이거나 패스워드가 일치하지 않습니다. $username"
            )
        }.let { user ->
            if (!passwordEncoder.matches(password, user.password)) {
                throw HttpException(
                    HttpStatus.BAD_REQUEST,
                    HttpExceptionCode.NOT_FOUND_OR_PASSWORD_NOT_MATCHED,
                    "존재하지 않는 사용자이거나 패스워드가 일치하지 않습니다. $username"
                )
            }
            user
        }

    }

}