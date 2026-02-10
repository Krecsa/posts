import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.WallService
import ru.netology.Post
import ru.netology.Likes
import ru.netology.PhotoAttachment
import ru.netology.Photo
import ru.netology.VideoAttachment
import ru.netology.Video
import ru.netology.AudioAttachment
import ru.netology.Audio

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
    @Test
    fun addWithAttachments() {
        val post = Post(
            ownerId = 1,
            text = "Post with photo",
            attachments = listOf(
                PhotoAttachment(
                    photo = Photo(
                        id = 1,
                        ownerId = 1,
                        albumId = 10,
                        text = "Night",
                        width = 1920,
                        height = 1080
                    )
                )
            )
        )
        val savedPost = WallService.add(post)
        assertNotEquals(0, savedPost.id)
    }

    @Test
    fun updateWithAttachments() {
        val originalPost = WallService.add(Post(ownerId = 1, text = "Original"))
        val updatedPost = originalPost.copy(
            attachments = listOf(
                VideoAttachment(
                    video = Video(
                        id = 1,
                        ownerId = 1,
                        title = "Skateboarding",
                        duration = 60
                    )
                ),
                AudioAttachment(
                    audio = Audio(
                        id = 1,
                        ownerId = 1,
                        artist = "Artist",
                        title = "Song",
                        duration = 120
                    )
                )
            )
        )
        val result = WallService.update(updatedPost)
        assertTrue(result)
    }
}