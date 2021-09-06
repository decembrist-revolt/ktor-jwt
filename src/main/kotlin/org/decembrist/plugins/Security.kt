package org.decembrist.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import java.util.*

lateinit var audience: String
lateinit var domain: String
lateinit var secret: String

const val USERNAME = "username"
const val ROLE = "role"

fun Application.configureSecurity() {
    audience = environment.config.property("jwt.audience").getString()
    domain = environment.config.property("jwt.domain").getString()
    secret = environment.config.property("jwt.secret").getString()

    authentication {
        jwt {
            realm = environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(domain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(audience)) {
                    val claims = credential.payload.claims
                    UserPrincipal(claims[USERNAME]!!.asString(), claims[ROLE]!!.asString())
                } else null
            }
        }
    }
}

class UserPrincipal(
    val username: String,
    val role: String,
) : Principal

fun createToken(username: String, role: String): String = JWT.create()
    .withAudience(audience)
    .withIssuer(domain)
    .withClaim(USERNAME, username)
    .withClaim(ROLE, role)
    .withExpiresAt(Date(System.currentTimeMillis() + 60000))
    .sign(Algorithm.HMAC256(secret))

fun ApplicationCall.hasRole(role: String) = principal<UserPrincipal>()?.let { user -> user.role == role } == true