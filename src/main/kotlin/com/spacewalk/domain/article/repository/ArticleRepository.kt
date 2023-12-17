package com.spacewalk.domain.article.repository

import com.spacewalk.domain.article.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ArticleRepository : JpaRepository<Article, Long> {

    fun findByIdAndUserIdAndDeletedIsFalse(id: Long, userId: Long) : Optional<Article>

    @Query("select ar from Article ar join fetch ar.user where ar.id = :id and ar.deleted is false")
    fun findByIdJoinedUser(id: Long) : Optional<Article>

    @Query("select ar from Article ar join fetch ar.user where ar.deleted is false", countQuery = "select count (ar) from Article ar inner join ar.user where ar.deleted is false")
    fun findAllJoinedUser(pageable: Pageable) : Page<Article>
    fun findAllByUserIdAndDeletedIsFalse(userId: Long, pageable: Pageable) : Page<Article>

}