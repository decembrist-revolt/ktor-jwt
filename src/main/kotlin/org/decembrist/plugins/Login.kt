package org.decembrist.plugins

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.reactive.asFlow
import org.decembrist.generated.tables.records.UsrRecord
import org.decembrist.generated.tables.references.USR

fun Application.loginRoutes() {
    routing {
        post("/login") {
            val (username, password) = login(call.receiveParameters())
            if (username == null || password == null) {
                call.respond(HttpStatusCode.Unauthorized)
            } else {
                findUser(username, password)
                    ?.let { user -> createToken(username, user.role!!) }
                    ?.let{ token -> call.respond(token) }
                    ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}

fun login(parameters: Parameters) = parameters["username"] to parameters["password"]

suspend fun findUser(username: String, password: String): UsrRecord? {
    val userRecord = database.selectFrom(USR)
        .where(USR.USERNAME.eq(username))
        .asFlow()
        .singleOrNull()
    return userRecord.takeIf { userRecord != null && userRecord.password == password }
}
