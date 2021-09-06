package org.decembrist.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.decembrist.domain.MessageDto
import org.decembrist.generated.tables.Message.Companion.MESSAGE

fun Application.controllerRoutes() {

    routing {
        get("/controller") {
            val messages = database.selectFrom(MESSAGE)
                .asFlow()
                .map { it.into(MessageDto::class.java) }
                .toList()

            call.respond(messages)
        }
    }
}