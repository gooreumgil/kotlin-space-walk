package com.spacewalk.domain.user.controller.authenticated

import com.spacewalk.domain.article.service.ArticleService
import com.spacewalk.domain.user.service.UserService
import com.spacewalk.facade.ReactiveFacade
import com.spacewalk.security.UserContext
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/*
* 인증받은 어드민(ROLE_ADMIN)를 위한 api
* */
@RestController
@RequestMapping("/api/authenticated/admin")
class AuthenticatedAdminController(
    private val articleService: ArticleService
) {

    @DeleteMapping("/articles/{articleId}")
    fun removeArticle(@AuthenticationPrincipal userContext: UserContext, @PathVariable articleId: Long): Mono<Unit> {
        return ReactiveFacade.wrapInMono { articleService.deleteById(articleId) }
    }

}