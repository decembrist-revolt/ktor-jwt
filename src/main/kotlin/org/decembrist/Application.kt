package org.decembrist

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.decembrist.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureSerialization()
        configureTemplating()
    }.start(wait = true)
}
