package myplayground.example.learningq.network.utils

data class WithPagination<T>(
    val data: T,
    val page: Int,
    val totalPage: Int,
)