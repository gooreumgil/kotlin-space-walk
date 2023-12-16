package com.spacewalk.domain.article.controller

import com.spacewalk.domain.article.service.ArticleService
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.awt.print.Pageable

@RestController
@RequestMapping("/api/articles")
class ArticleController(articleService: ArticleService) {

    @GetMapping
    fun getAll(@PageableDefault pageable: Pageable) {



    }

}