package com.spacewalk.domain.user.controller.authenticated

import com.spacewalk.common.PageDto
import com.spacewalk.domain.article.dto.ArticleResDto
import com.spacewalk.domain.article.dto.ArticleSaveReqDto
import com.spacewalk.domain.article.dto.ArticleUpdateReqDto
import com.spacewalk.domain.user.service.UserService
import com.spacewalk.facade.ReactiveFacade
import com.spacewalk.security.UserContext
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/authenticated/user")
class AuthenticatedUserController(
    private val userService: UserService) {

    @PostMapping("/articles")
    fun writeArticle(
        @AuthenticationPrincipal userContext: UserContext,
        @RequestBody dto: ArticleSaveReqDto) : Mono<ArticleResDto> {

        return ReactiveFacade.wrapInMono { userService.addArticle(userContext.id, dto) }

    }

    @GetMapping("/articles")
    fun getUserArticlesPage(
        @AuthenticationPrincipal userContext: UserContext,
        @PageableDefault(sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable): Mono<PageDto.ListResponse<Unit>> {

        return ReactiveFacade.wrapInMono { userService.findArticlesPage(userContext.id, pageable) }

    }

    @PatchMapping("/articles/{articleId}")
    fun updateArticle(
        @AuthenticationPrincipal userContext: UserContext,
        @PathVariable articleId: Long,
        @RequestBody articleUpdateReqDto: ArticleUpdateReqDto): Mono<Unit> {

        return ReactiveFacade.wrapInMono { userService.modifyArticle(userContext.id, articleId, articleUpdateReqDto) }

    }

    @DeleteMapping("/articles/{articleId}")
    fun removeArticle(@AuthenticationPrincipal userContext: UserContext, @PathVariable articleId: Long) {

        userService.deleteArticle(userContext.id, articleId)

    }

}