package org.decembrist

import com.github.mustachejava.DefaultMustacheFactory
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.mustache.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.decembrist.plugins.*

fun main() {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        config = HoconApplicationConfig(ConfigFactory.load())

        module {
            install(Mustache) {
                mustacheFactory = DefaultMustacheFactory("templates")
            }

            index()
            helloRoutes()
            configureSecurity()
            configureSerialization()
        }

        connector {
            port = config.property("ktor.deployment.port").getString().toInt()
            host = config.property("ktor.deployment.host").getString()
        }
    }).start(wait = true)
}
