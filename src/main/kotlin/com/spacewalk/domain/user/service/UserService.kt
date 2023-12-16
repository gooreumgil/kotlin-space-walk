package com.spacewalk.domain.user.service

import com.spacewalk.domain.user.User
import com.spacewalk.domain.user.dto.UserResDto
import com.spacewalk.domain.user.dto.UserSaveReqDto
import com.spacewalk.domain.user.repository.UserRepository
import com.spacewalk.exception.HttpException
import com.spacewalk.exception.HttpExceptionCode
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun createUser(dto: UserSaveReqDto): UserResDto {
        return userRepository.save(User.create(dto))
            .let { user ->
                UserResDto.fromUser(user = user)
            }
    }

    fun findById(id: Long): UserResDto {
        return userRepository.findByIdJoinedRoleList(id)
            .orElseThrow {
                HttpException(
                    status = HttpStatus.BAD_REQUEST,
                    HttpExceptionCode.NOT_FOUND,
                    message = "존재하지 않는 User 입니다. id = $id"
                )
            }
            .let { user ->
                UserResDto.fromUser(user = user)
            }
    }

}