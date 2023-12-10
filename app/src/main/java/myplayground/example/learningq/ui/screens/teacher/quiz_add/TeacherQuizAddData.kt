package myplayground.example.learningq.ui.screens.teacher.quiz_add

import myplayground.example.learningq.model.Class

sealed class TeacherQuizAddEvent {
    object Init : TeacherQuizAddEvent()

    data class ClassSelected(val selectedClass: Class) : TeacherQuizAddEvent()
    data class TitleChanged(val title: String): TeacherQuizAddEvent()
    data class DescriptionChanged(val description: String): TeacherQuizAddEvent()
}

data class TeacherQuizAddInputData(
    val selectedClass: Class? = null,
    val title: String = "",
    val description: String = "",
)

