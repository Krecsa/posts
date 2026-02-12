package ru.netology

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = false,
    val canClose: Boolean = false,
    val canOpen: Boolean = true
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = false
)

interface Attachment {
    val type: String
}

data class Photo(
    val id: Int = 0,
    val ownerId: Int = 0,
    val albumId: Int = 0,
    val text: String? = null,
    val width: Int? = null,
    val height: Int? = null,
)

data class PhotoAttachment(
    val photo: Photo
) : Attachment {
    override val type: String = "photo"
}

data class Video(
    val id: Int = 0,
    val ownerId: Int = 0,
    val views: Int? = null,
    val comments: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val duration: Int = 0
)

data class VideoAttachment(
    val video: Video
) : Attachment {
    override val type: String = "video"
}

data class Audio(
    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String? = null,
    val title: String? = null,
    val duration: Int = 0,
)

data class AudioAttachment(
    val audio: Audio
) : Attachment {
    override val type: String = "audio"
}

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Long = System.currentTimeMillis() / 1000,
    val text: String = "",
    val friendsOnly: Boolean = false,
    val comments: Comments? = null,
    val likes: Likes? = null,
    val repostsCount: Int = 0,
    val viewsCount: Int = 0,
    val canPin: Boolean = false,
    val attachments: List<Attachment> = emptyList()
)

class PostNotFoundException(postId: Int) : Exception("Post with ID $postId not found")

data class Comment(
    val id: Int = 0,
    val fromId: Int = 0,
    val postId: Int = 0,
    val text: String = "",
    val date: Long = System.currentTimeMillis() / 1000
)

object WallService {

    private var posts = emptyArray<Post>()
    private var nextPostId: Int = 1

    private var comments = emptyArray<Comment>()
    private var nextCommentId: Int = 1

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextPostId)
        posts += newPost
        nextPostId++
        return newPost
    }

    fun update(post: Post): Boolean {
        val index = posts.indexOfFirst { it.id == post.id }
        if (index == -1) return false
        posts[index] = post
        return true
    }

    fun printPosts() {
        for (post in posts) {
            println(post)
        }
    }

    fun clear() {
        posts = emptyArray()
        nextPostId = 1
    }

    fun createComment(postId: Int, comment: Comment): Comment {

        if (posts.none { it.id == postId }) {
            throw PostNotFoundException(postId)
        }

        val newComment = comment.copy(
            id = nextCommentId,
            postId = postId,
            date = System.currentTimeMillis() / 1000
        )
        comments += newComment
        nextCommentId++
        return newComment
    }
}

fun main() {

    val post1 = Post(
        ownerId = 1,
        text = "Whenever I find the key to success, someone changes the lock",
        comments = Comments(count = 5, canPost = true),
        canPin = true
    )

    val post2 = Post(
        ownerId = 2,
        likes = Likes(count = 903, userLikes = true),
        text = "What do you call a fake noodle? An impasta!"
    )

    // Пост с отключенными лайками и комментариями
    val post3 = Post(
        ownerId = 3,
        text = "A post without user reaction",
        comments = null,
        likes = null
    )

    // Пост с фото
    val post4 = Post(
        ownerId = 4,
        text = "Check out this photo!",
        attachments = listOf(
            PhotoAttachment(
                photo = Photo(
                    id = 1,
                    ownerId = 4,
                    albumId = 35,
                    text = "look at this",
                    width = 800,
                    height = 600
                )
            )
        )
    )

    // Пост с несколькими вложениями разных типов
    val post5 = Post(
        ownerId = 5,
        text = "Mix",
        attachments = listOf(
            VideoAttachment(
                video = Video(
                    id = 1,
                    ownerId = 5,
                    title = "Snowboarding World's Craziest Videos | Red Bull Top 5",
                    duration = 444,
                    views = 4_800_000
                )
            ),
            AudioAttachment(
                audio = Audio(
                    id = 1,
                    ownerId = 5,
                    artist = "Neoni",
                    title = "Horror Movies",
                    duration = 189
                )
            ),
        )
    )

    val savedPost1 = WallService.add(post1)
    val savedPost2 = WallService.add(post2)
    val savedPost3 = WallService.add(post3)
    val savedPost4 = WallService.add(post4)
    val savedPost5 = WallService.add(post5)

    println("New post ID: ${savedPost1.id}")
    println("New post ID: ${savedPost2.id}")
    println("New post ID: ${savedPost3.id}")
    println("New post ID: ${savedPost4.id}")
    println("New post ID: ${savedPost5.id}")

    // Успешное обновление поста с добавлением вложений
    val updatedPost = savedPost1.copy(
        text = "A gathering of ferrets is known as a business",
        likes = Likes(count = 12),
        attachments = listOf(
            PhotoAttachment(
                photo = Photo(
                    id = 2,
                    ownerId = 1,
                    albumId = 42,
                    text = "Business ferret",
                    width = 1024,
                    height = 768
                )
            )
        )
    )
    val isUpdated = WallService.update(updatedPost)
    println("The post has been updated: $isUpdated")

    // Обновление несуществующего поста
    val fakePost = Post(id = 999, text = "The post was not found")
    val isFailed = WallService.update(fakePost)
    println("The post has been updated $isFailed")

    // Попытка добавить комментарий
    try {
        val comment = WallService.createComment(
            postId = savedPost1.id,
            comment = Comment(
                fromId = 100,
                text = "Nice try"
            )
        )
        println("Comment created with ID: ${comment.id}")
    } catch (e: PostNotFoundException) {
        println("Error: ${e.message}")
    }

    WallService.printPosts()
}