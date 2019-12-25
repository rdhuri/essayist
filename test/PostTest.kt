package test

import com.example.SinglePost
import com.example.main
import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.*


class PostTest {

    @Test
    fun itShouldListPostsForUser() = withTestApplication(Application::main) {
        with(handleRequest (HttpMethod.Get, "/posts")) {
            assertNotNull(response.content)
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

}