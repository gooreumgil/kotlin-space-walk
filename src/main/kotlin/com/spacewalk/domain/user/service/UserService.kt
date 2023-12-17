package com.spacewalk.domain.user.service

import com.spacewalk.exception.GlobalExceptionHelper
import com.spacewalk.common.PageDto
import com.spacewalk.domain.article.Article
import com.spacewalk.domain.article.dto.ArticleResDto
import com.spacewalk.domain.article.dto.ArticleSaveReqDto
import com.spacewalk.domain.article.dto.ArticleUpdateReqDto
import com.spacewalk.domain.article.repository.ArticleRepository
import com.spacewalk.domain.user.User
import com.spacewalk.domain.user.UserRoleRelation
import com.spacewalk.domain.user.dto.UserResDto
import com.spacewalk.domain.user.dto.UserSaveReqDto
import com.spacewalk.domain.user.repository.UserRepository
import com.spacewalk.domain.user.repository.UserRoleRepository
import com.spacewalk.exception.HttpException
import com.spacewalk.exception.HttpExceptionCode
import com.spacewalk.security.UserContext
import com.spacewalk.utils.AES256Util
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val articleRepository: ArticleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun saveUser(dto: UserSaveReqDto): UserResDto {

        dto.password = passwordEncoder.encode(dto.password)
        dto.email = AES256Util.encrypt(dto.email)

        val user = User.create(dto)
        val userRole = userRoleRepository.findByRoleName("ROLE_USER").get()

        val userRoleRelation = UserRoleRelation()
        userRoleRelation.updateUser(user)
        userRoleRelation.updateUserRole(userRole)

        return userRepository.save(user)
            .let { savedUser ->
                UserResDto.fromUser(user = savedUser)
            }
    }

    fun findById(userId: Long): UserResDto {
        return userRepository.findByIdJoinedRoleList(userId)
            .orElseThrow {
                GlobalExceptionHelper.createNotFoundException("User", userId)
            }
            .let { user ->
                UserResDto.fromUser(user = user)
            }
    }



    @Transactional
    fun addArticle(userId: Long, dto: ArticleSaveReqDto) : ArticleResDto {

        val user = userRepository.findById(userId).orElseThrow { GlobalExceptionHelper.createNotFoundException("User", userId) }
        val article = Article.create(dto)
        user.addArticle(article)

        articleRepository.save(article)

        val articleResDto = ArticleResDto.fromArticle(article)
        articleResDto.updateUser(user)

        return articleResDto

    }

    fun findArticlesPage(userId: Long, pageable: Pageable): PageDto.ListResponse<ArticleResDto> {

        val articlePage = articleRepository.findAllByUserIdAndDeletedIsFalse(userId, pageable)
        val dtoList = articlePage.map { article ->
            val articleResDto = ArticleResDto.fromArticle(article)
            articleResDto.updateUser(article.user!!)
            articleResDto

        }.toList()

        return PageDto.ListResponse(articlePage, dtoList)

    }

    @Transactional
    fun modifyArticle(userId: Long, articleId: Long, dto: ArticleUpdateReqDto) {

        val article = articleRepository.findByIdAndUserIdAndDeletedIsFalse(articleId, userId).orElseThrow {
            GlobalExceptionHelper.createNotFoundException("Article", articleId)
        }

        article.update(dto)

    }


    @Transactional
    fun deleteArticle(userId: Long, articleId: Long) {

        val article = articleRepository.findByIdAndUserIdAndDeletedIsFalse(userId, articleId).orElseThrow {
            GlobalExceptionHelper.createNotFoundException("Article", articleId)
        }

        article.delete()

    }


}