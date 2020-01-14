import com.example.PostPage
import com.example.Posts
import com.example.SinglePost
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.locations.put
import io.ktor.request.receive
import io.ktor.response.respond
import java.lang.Exception

fun Route.posts() {

    get<Posts> {
        val allPosts = Database.getAllPosts()
        call.respondText(allPosts)
    }

    post<Posts> {
        try {
            val post = call.receive<SinglePost>()
            Database.addPost(post)
            call.respond(HttpStatusCode.OK, "Post saved successfully")
        } catch (e: MissingKotlinParameterException) {
            call.respondText(e.localizedMessage,
                ContentType.Text.Plain, HttpStatusCode.BadRequest)
        }
    }

    get<PostPage> {
        val id = call.parameters["id"]
        if (id ==  null) call.respond(HttpStatusCode.BadRequest) else {
            val post = Database.getPostsWithId(id.toInt())
            call.respondText(post)
        }
    }

    put<PostPage> {
        try{
            val post = call.receive<SinglePost>()
            if (post.id ==  null) call.respond(HttpStatusCode.BadRequest) else {
                Database.updatePostWithId(post.id, post)
                call.respond(HttpStatusCode.OK, "Post updated successfully")
            }
        }catch (e: MissingKotlinParameterException) {
            call.respondText(e.localizedMessage,
                ContentType.Text.Plain, HttpStatusCode.BadRequest)
        }

    }
}
