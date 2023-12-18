package myplayground.example.learningq.network.utils

data class WithPagination<T>(
    val data: T,
    val status: String,
    val page: Int,
    val totalPage: Int,
)