package com.spacewalk.init

import com.spacewalk.domain.article.Article
import com.spacewalk.domain.article.dto.ArticleSaveReqDto
import com.spacewalk.domain.user.User
import com.spacewalk.domain.user.UserRoleRelation
import com.spacewalk.domain.user.UserRole
import com.spacewalk.domain.user.dto.UserSaveReqDto
import com.spacewalk.domain.user.repository.UserRepository
import com.spacewalk.domain.user.repository.UserRoleRepository
import com.spacewalk.utils.AES256Util
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ApplicationInitializer(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    @EventListener(ApplicationReadyEvent::class)
    fun init() {

        val userRole = userRoleRepository.save(UserRole.create("ROLE_USER"))
        val userManager = userRoleRepository.save(UserRole.create("ROLE_MANAGER"))
        val userAdmin = userRoleRepository.save(UserRole.create("ROLE_ADMIN"))

        val user1 = userRepository.save(
            User.create(
                UserSaveReqDto(
                    "user",
                    AES256Util.encrypt("user@email.com"),
                    passwordEncoder.encode("pass"),
                    20)
            )
        )

        val user2 = userRepository.save(
            User.create(
                UserSaveReqDto(
                    "manager",
                    AES256Util.encrypt("manager@email.com"),
                    passwordEncoder.encode("pass"),
                    30)
            )
        )

        val user3 = userRepository.save(
            User.create(
                UserSaveReqDto(
                    "admin",
                    AES256Util.encrypt("admin@email.com"),
                    passwordEncoder.encode("pass"),
                    40)
            )
        )

        val userRoleRelation1 = UserRoleRelation()
        userRoleRelation1.updateUser(user1)
        userRoleRelation1.updateUserRole(userRole)

        val userRoleRelation2 = UserRoleRelation()
        userRoleRelation2.updateUser(user2)
        userRoleRelation2.updateUserRole(userManager)

        val userRoleRelation3 = UserRoleRelation()
        userRoleRelation3.updateUser(user3)
        userRoleRelation3.updateUserRole(userAdmin)

        user1.addArticle(Article.create(ArticleSaveReqDto("게시글 제목1입니다.", "게시글 내용1입니다. 게시글 내용1입니다. 게시글 내용1입니다.")))
        user1.addArticle(Article.create(ArticleSaveReqDto("게시글 제목2입니다.", "게시글 내용2입니다. 게시글 내용2입니다. 게시글 내용2입니다.")))
        user2.addArticle(Article.create(ArticleSaveReqDto("게시글 제목3입니다.", "게시글 내용3입니다. 게시글 내용3입니다. 게시글 내용3입니다.")))
        user2.addArticle(Article.create(ArticleSaveReqDto("게시글 제목4입니다.", "게시글 내용4입니다. 게시글 내용4입니다. 게시글 내용4입니다.")))
        user3.addArticle(Article.create(ArticleSaveReqDto("게시글 제목5입니다.", "게시글 내용5입니다. 게시글 내용4입니다. 게시글 내용5입니다.")))
        user3.addArticle(Article.create(ArticleSaveReqDto("게시글 제목6입니다.", "게시글 내용6입니다. 게시글 내용6입니다. 게시글 내용6입니다.")))

    }


}