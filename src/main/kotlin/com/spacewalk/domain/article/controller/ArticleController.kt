package com.spacewalk.domain.article.controller

import com.spacewalk.common.PageDto
import com.spacewalk.domain.article.dto.ArticleResDto
import com.spacewalk.domain.article.service.ArticleService
import com.spacewalk.facade.ReactiveFacade
import com.spacewalk.security.UserContext
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    private val articleService: ArticleService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Mono<ArticleResDto> {
        return ReactiveFacade.wrapInMono { articleService.findByIdWithUser(id) }
    }

    @GetMapping
    fun getAll(@PageableDefault(sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable): Mono<PageDto.ListResponse<ArticleResDto>> {
        return ReactiveFacade.wrapInMono { articleService.findAllWithUser(pageable) }
    }

}