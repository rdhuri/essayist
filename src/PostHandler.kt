import com.example.Posts
import com.example.SinglePost
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond

fun Route.posts() {

    get<Posts> {
        val allPosts = Database().getAllPosts()
        call.respondText(allPosts)
    }

    post<Posts> {
        val post = call.receive<SinglePost>()
        Database().addPost(post)
        call.respond(HttpStatusCode.OK, "Post saved successfully")
    }
}
