package myplayground.example.learningq.ui.screens.teacher.quiz_add

import myplayground.example.learningq.model.Class

data class TeacherQuizAddInputData(
    val selectedQuizType: Int = 0,
    val selectedClass: Class? = null,
    val title: String = "",
    val description: String = "",
)


sealed class TeacherQuizAddEvent {
    object Init : TeacherQuizAddEvent()

    data class QuizTypeSelected(val quizType: Int) : TeacherQuizAddEvent()
    data class ClassSelected(val selectedClass: Class) : TeacherQuizAddEvent()
    data class TitleChanged(val title: String) : TeacherQuizAddEvent()
    data class DescriptionChanged(val description: String) : TeacherQuizAddEvent()
}

