package com.spacewalk.domain.article.service

import com.spacewalk.common.GlobalExceptionHelper
import com.spacewalk.common.PageDto
import com.spacewalk.domain.article.Article
import com.spacewalk.domain.article.dto.ArticleResDto
import com.spacewalk.domain.article.dto.ArticleSaveReqDto
import com.spacewalk.domain.article.repository.ArticleRepository
import com.spacewalk.domain.user.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun saveArticle(dto: ArticleSaveReqDto, userId: Long) {

        val user = userRepository.findById(userId).orElseThrow {
            GlobalExceptionHelper.createNotFoundException("User", userId)
        }

        user.addArticle(Article.create(dto))

    }

    fun findAllWithUser(pageable: Pageable): PageDto.ListResponse<Unit> {
        val articlePage = articleRepository.findAllJoinedUser(pageable)
        val dtoList = articlePage.map { article ->
            ArticleResDto.fromArticle(article).updateUser(article.user!!)
        }.toList()

        return PageDto.ListResponse(articlePage, dtoList)

    }

    @Transactional
    fun findByIdWithUser(articleId: Long?): ArticleResDto {

        val article = articleRepository.findByIdJoinedUser(articleId!!)
            .orElseThrow { GlobalExceptionHelper.createNotFoundException("Article", articleId) }

        val articleResDto = ArticleResDto.fromArticle(article)
        articleResDto.updateUser(article.user!!)

        return articleResDto

    }

}