package com.spacewalk.common

import com.spacewalk.domain.article.Article
import org.springframework.data.domain.Page

class PageDto {
    data class PageResponse(
        var currentPage: Int = 0,
        var totalPages: Int = 0,
        var size: Int = 0,
        var totalElements: Long = 0L,
        var last: Boolean? = null
    ) {
        constructor(page: Page<*>) : this(
            currentPage = page.number + 1,
            totalPages = page.totalPages,
            size = page.size,
            totalElements = page.totalElements,
            last = page.isLast
        )
    }

    data class ListResponse<T>(
        var pageInfo: PageResponse? = null,
        var list: List<T>? = null
    ) {
        constructor(page: Page<*>, dtoList: List<T>) : this(
            pageInfo = PageResponse(page),
            list = dtoList
        )
    }
}