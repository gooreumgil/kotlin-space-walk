package com.spacewalk.security.jwt

import com.spacewalk.domain.user.User
import com.spacewalk.security.UserContext
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashSet

@Slf4j
class JwtTokenProvider {

    @Value("\${security.jwt.token.secret-key:secret-key}")
    private lateinit var secretKeyString: String

    @Value("\${security.jwt.token.expire-length:3600000}")
    private var validityInMilliseconds: Long = 0

    private var secretKey: Key? = null

    @PostConstruct
    fun init() {
        val keyBytes = secretKeyString.toByteArray()
        secretKey = SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.jcaName)
    }

    fun createToken(user: User): String {

        val claims = Jwts.claims().setSubject(user.username)

        val roleList = user.roleList

        val userRoles = roleList
            .map { it.role!!.roleName }
            .toSet()

        claims["roles"] = userRoles
        claims["id"] = user.id
        claims["age"] = user.age

        val now = Date();
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();

    }

    fun getAuthentication(token: String): Authentication? {
        val claims = getClaims(token)
        if (claims == null) {
            return null
        }

        val id = (claims["id"] as? Number)?.toLong()
        val username = claims.subject
        val age = (claims["age"] as? Number)?.toInt()


        val roles: MutableSet<GrantedAuthority> = HashSet()
        val rolesList = claims["roles"] as? List<*> ?: listOf<Any>()
        val roleSet = rolesList.toSet()
        roleSet.map { role ->
            roles.add(SimpleGrantedAuthority(role.toString()))
        }

        return UsernamePasswordAuthenticationToken(UserContext(id = id!!, username = username, age = age!!), null, roles)
    }

    private fun getUsername(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject;
    }

    fun resolveToken(req: ServerHttpRequest): String? {
        val bearerToken = req.headers.getFirst("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }

    fun validateToken(token: String): Boolean {
        return !getClaims(token).expiration.before(Date())

    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

}