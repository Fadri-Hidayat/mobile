package myplayground.example.learningq.ui.screens.student.presence_detail

import myplayground.example.learningq.model.Course

data class StudentPresenceDetailData(
    val processedCourseId: String? = null,
    val isLoading: Boolean = false,
)

sealed class StudentPresenceDetailEvent {
    object Init : StudentPresenceDetailEvent()
    data class FetchPresence(val classId: String) : StudentPresenceDetailEvent()

    data class CoursePresenceSubmit(val course: Course) : StudentPresenceDetailEvent()
}
