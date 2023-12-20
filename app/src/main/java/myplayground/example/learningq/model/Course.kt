package myplayground.example.learningq.model

import java.time.DayOfWeek

data class Course(
    val id: String = "",
    val classId: String = "",
    val name: String = "",
    val teacherUserId: String = "",
    val dayOfWeek: Int = 0, // 1 Monday, 7 Sunday
    val startTimeInMinutes: Int = 0, // 3600 = 01:00, 7200 = 02:00
    val endTimeInMinutes: Int = 0,
    val description: String? = null,
)

data class CourseStudent(
    val courseId: String = "",
    val studentUserId: String = "",
)