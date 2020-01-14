package test

import com.example.SinglePost
import com.example.main
import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.*


class PostTest {

    @Test
    fun itShouldListPostsForUser() = withTestApplication(Application::main) {
        with(handleRequest (HttpMethod.Get, "/posts")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun itShouldVerifyPostIsSaved() = withTestApplication(Application::main) {
        with(handleRequest (HttpMethod.Post, "/posts"){
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Gson().toJson(SinglePost(null, "test", "test description")))
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun itShouldVerifyInvalidPostIsNotAccepted() = withTestApplication(Application::main) {
        with(handleRequest (HttpMethod.Post, "/posts"){
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("{\"description\": \"Test description!!\"}")
        }) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
        }
    }

    @Test
    fun itShouldFetchPostWithCorrectId() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, "/posts/1")){
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun itShouldUpdatePostWithCorrectId() = withTestApplication(Application::main) {
        with(handleRequest (HttpMethod.Put, "/posts/1"){
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Gson().toJson(SinglePost(1, "update test", "updated test description")))
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun itShouldVerifyInvalidPostIsNotAcceptedForUpdate() = withTestApplication(Application::main) {
        with(handleRequest (HttpMethod.Put, "/posts/1"){
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("{\"id\": 1,\"description\": \"Test description!!\"}")
        }) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
        }
    }
}