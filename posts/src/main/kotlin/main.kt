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

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Long = System.currentTimeMillis() / 1000,
    val text: String = "",
    val friendsOnly: Boolean = false,
    val comments: Comments = Comments(),
    val likes: Likes = Likes(),
    val repostsCount: Int = 0,
    val viewsCount: Int = 0,
    val canPin: Boolean = false
)

object WallService {

    private var posts = emptyArray<Post>()

    fun add(post: Post): Post {
        posts += post
        return posts.last()
    }

    fun printPosts() {
        for (post in posts) {
            println(post)
        }
    }
}

fun main() {

    val post1 = Post(
        id = 200,
        ownerId = 1,
        text = "Whenever I find the key to success, someone changes the lock",
        comments = Comments(count = 5, canPost = true),
        canPin = true
    )

    val post2 = Post(
        id = 100,
        ownerId = 1,
        likes = Likes(count = 903, userLikes = true),
        text = "What do you call a fake noodle? An impasta!"

    )

    WallService.add(post1)
    WallService.add(post2)
    WallService.printPosts()
}