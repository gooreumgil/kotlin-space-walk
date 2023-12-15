package com.spacewalk.security.jwt

import com.spacewalk.domain.account.Account
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct

@Slf4j
@Component
class JwtTokenProvider(
    private val userDetailsService: ReactiveUserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {

    @Value("\${security.jwt.token.secret-key:secret-key}")
    private lateinit var secretKey: String

    @Value("\${security.jwt.token.expire-length:3600000}")
    private var validityInMilliseconds: Long = 0

    private var key: Key? = null

    @PostConstruct
    protected fun init() {
        key = Keys.hmacShaKeyFor(secretKey!!.toByteArray())
    }

    fun createToken(account: Account): String {

        val claims = Jwts.claims().setSubject(account.username)

        val roleList = account.roleList

        val userRoles = roleList
            .map { it.role!!.roleName }
            .toSet()

        claims["roles"] = userRoles
        claims["id"] = account.id
        claims["age"] = account.age

        val now = Date();
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key)
            .compact();

    }

    fun getAuthentication(token: String): Authentication {
        val userdetails: UserDetails = this.userDetailsService.findByUsername(getUsername(token)).block()!!
        return UsernamePasswordAuthenticationToken(userdetails, null, userdetails.authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parserBuilder().build().parseClaimsJwt(secretKey).body.subject;
    }

    fun resolveToken(req: ServerHttpRequest): String? {
        val bearerToken = req.headers.getFirst("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }

    fun validateToken(token: String): Boolean {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(secretKey)
            return true
        } catch (e: Exception) {
        }

        return false
    }

}