package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import posts

@Location("/posts")
class Posts()

fun main(args: Array<String>) {
    initDB()
    embeddedServer(Netty, 8080) {
        install(Locations)
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }

        routing {
            posts()
        }

    }.start(wait = true)
}


fun initDB() {
    Database.connect("jdbc:mysql://localhost:3306/Posts?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "Rahul", password = "Rahul123#")
}