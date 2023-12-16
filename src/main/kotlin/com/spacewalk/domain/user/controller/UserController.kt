package com.spacewalk.domain.user.controller

import com.spacewalk.domain.user.dto.UserResDto
import com.spacewalk.domain.user.dto.UserSaveReqDto
import com.spacewalk.domain.user.service.UserService
import com.spacewalk.facade.ReactiveFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun create(@RequestBody dto: UserSaveReqDto): Mono<UserResDto>? {
        return ReactiveFacade.wrapInMono { userService.createUser(dto) }
            .flatMap { userResDto -> Mono.just(userResDto) }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Mono<UserResDto> {
        return ReactiveFacade.wrapInMono { userService.findById(id) }
            .flatMap { userResDto -> Mono.just(userResDto) }
    }

}