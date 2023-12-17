package com.spacewalk.domain.user.controller.authenticated

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/*
* 인증받은 매니저(ROLE_MANAGER)를 위한 api
* */
@RestController
@RequestMapping("/api/authenticated/manager")
class AuthenticatedManagerController {
}