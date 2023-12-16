package com.spacewalk.domain.article.service

import com.spacewalk.domain.article.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ArticleService(articleRepository: ArticleRepository) {



}