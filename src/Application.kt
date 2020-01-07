package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
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
    Database.connect("jdbc:mysql://localhost:3306/Posts?allowPublicKeyRetrieval=true&useSSL=false", driver = "com.mysql.jdbc.Driver", user = "root", password = "Hitman@123#")
}