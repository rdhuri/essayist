package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import getLogger
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import posts

@Location("/posts")
class Posts()

@Location("/posts/{id}")
class PostPage(val id: Int)

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        main()
    }.start(true)
}

fun Application.main() {
    initDB()

    install(Locations)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    routing {
        posts()
    }
}


fun initDB() {
    Database.connect(DatabaseConstants.dbURL, driver = DatabaseConstants.dbDriver, user = DatabaseConstants.dbUser, password = DatabaseConstants.dbPassword)
}