package org.decembrist.plugins

import io.ktor.application.*
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import org.jooq.DSLContext
import org.jooq.impl.DSL

lateinit var database: DSLContext

fun Application.connectDatabase() {
    val url = environment.config.property("r2dbc.url").getString()
    val username = environment.config.property("r2dbc.username").getString()
    val password = environment.config.property("r2dbc.password").getString()

    val connectionFactory = ConnectionFactories.get(
        ConnectionFactoryOptions
            .parse(url)
            .mutate()
            .option(ConnectionFactoryOptions.USER, username)
            .option(ConnectionFactoryOptions.PASSWORD, password)
            .build()
    )

    database = DSL.using(connectionFactory)
}