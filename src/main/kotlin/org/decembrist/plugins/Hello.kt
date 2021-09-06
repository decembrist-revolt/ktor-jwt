package org.decembrist.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.decembrist.domain.MessageDto

fun Application.helloRoutes() {

    routing {
        get("/hello") {
            val start = call.request.queryParameters["start"]?.toInt() ?: 0
            val count = call.request.queryParameters["count"]?.toInt() ?: 3
            val data = listOf("Hello, reactive!", "More then one", "Third post", "Fourth post", "Fifth post")
                .drop(start)
                .take(count)
                .map { MessageDto(null, it) }
            call.respond(data)
        }
    }
}
