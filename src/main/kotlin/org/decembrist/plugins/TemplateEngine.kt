package org.decembrist.plugins

import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.application.*
import io.ktor.mustache.*

fun Application.configureTemplateEngine() = install(Mustache) {
    mustacheFactory = DefaultMustacheFactory("templates")
}