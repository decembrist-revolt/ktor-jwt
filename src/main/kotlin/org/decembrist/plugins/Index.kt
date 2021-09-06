package org.decembrist.plugins

import io.ktor.application.*
import io.ktor.mustache.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.indexRoutes() {

    routing {
        get("/") {
            val user = call.request.queryParameters["user"] ?: "Nobody"
            call.respond(MustacheContent("index.mustache", mapOf("user" to user)))
        }
    }
}
