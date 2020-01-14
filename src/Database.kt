import com.example.Post
import com.example.SinglePost
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object Database {
    fun getAllPosts() : String {
        var allPosts = ""
        transaction {
            allPosts = Gson().toJson(Post.selectAll().map { SinglePost(it[Post.id], it[Post.title], it[Post.description]) })
        }
        return allPosts
    }

    fun addPost(post: SinglePost) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                transaction {
                    Post.insert {
                        it[title] = post.title
                        it[description] = post.description
                    }
                }
            }
        }
    }

    fun getPostsWithId(id: Int) : String {
        var post = ""
        transaction {
            post = Gson().toJson(Post.select { Post.id eq id }.map {SinglePost(it[Post.id], it[Post.title], it[Post.description])})
        }
        return post
    }

    fun updatePostWithId(id: Int, post: SinglePost) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                transaction {
                    Post.update ({ Post.id eq id }) {
                        it[title] = post.title
                        it[description] = post.description
                    }
                }
            }
        }
    }
}