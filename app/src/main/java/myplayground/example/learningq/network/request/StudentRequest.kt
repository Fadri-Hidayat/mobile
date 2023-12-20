package myplayground.example.learningq.network.request

data class StudentCourseFetchRequest(
    val classId: String,
    val page: Int,
    val limit: Int,
)