import com.example.Post
import com.example.Posts
import com.example.SinglePost
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    fun getAllPosts() : String {
        var allPosts = ""
        transaction {
            allPosts = Gson().toJson(Post.selectAll().map { SinglePost(it[Post.id], it[Post.title], it[Post.description]) })
        }
        return allPosts
    }

    fun addPost(obj: SinglePost) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                transaction {
                    Post.insert {
                        it[title] = obj.title
                        it[description] = obj.description
                    }
                }
            }
        }
    }
}