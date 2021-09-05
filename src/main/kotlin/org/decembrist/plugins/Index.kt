package org.decembrist.plugins

import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.mustache.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.index() {
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }

    routing {
        get("/") {
            val user = call.request.queryParameters["user"] ?: "Nobody"
            call.respond(MustacheContent("index.mustache", mapOf("user" to user)))
        }
    }
}
