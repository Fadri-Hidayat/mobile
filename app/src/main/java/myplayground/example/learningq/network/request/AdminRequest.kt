package myplayground.example.learningq.network.request

data class AdminStudentRequest (
    val studentName: String,
    val email: String,
    val age: Int,
    val courses: List<String>,
)