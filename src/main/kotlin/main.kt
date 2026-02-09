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
    private var nextId: Int = 1

    fun add(post: Post): Post {

        // Копия с уникальным ID
        val newPost = post.copy(id = nextId)
        posts += newPost
        nextId++
        return newPost
    }

    fun update(post: Post): Boolean {

        // Поиск поста с таким же ID и его замена
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
        nextId = 1
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

    val savedPost1 = WallService.add(post1)
    val savedPost2 = WallService.add(post2)
    println("New post ID: ${savedPost1.id}")
    println("New post ID: ${savedPost2.id}")

    // Успешное обновление поста
    val updatedPost = savedPost1.copy(text = "A gathering of ferrets is known as a business", likes = Likes(count = 10))
    val isUpdated = WallService.update(updatedPost)
    println("The post has been updated: $isUpdated")

    // Обновление несуществующего поста
    val fakePost = Post(id = 999, text = "The post was not found")
    val isFailed = WallService.update(fakePost)
    println("The post has been updated $isFailed")

    WallService.printPosts()
}