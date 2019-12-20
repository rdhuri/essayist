package com.example

import org.jetbrains.exposed.sql.Table

object Post: Table("post") {
    val id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", length = 200)
    val description = text("description")
}

data class SinglePost(val id: Int?, val title: String, val description: String)

