import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.WallService
import ru.netology.Post
import ru.netology.Likes

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {

        val post = Post(ownerId = 1, text = "Test post")
        val savedPost = WallService.add(post)
        assertNotEquals(0, savedPost.id)
    }

    @Test
    fun updateTrue() {
        val post = WallService.add(Post(ownerId = 1, text = "First try"))
        val updatedPost = post.copy(text = "Updated", likes = Likes(count = 10))
        val result = WallService.update(updatedPost)
        assertTrue(result)
    }

    @Test
    fun updateFalse() {
        val nonExistingPost = Post(id = 999, ownerId = 1, text = "The post was not found")
        val result = WallService.update(nonExistingPost)
        assertFalse(result)
    }
}