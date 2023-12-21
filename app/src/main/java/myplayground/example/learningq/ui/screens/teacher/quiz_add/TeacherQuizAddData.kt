package myplayground.example.learningq.ui.screens.teacher.quiz_add

import androidx.compose.runtime.snapshots.SnapshotStateList
import myplayground.example.learningq.model.Class

data class TeacherQuizAddData(
    val classList: SnapshotStateList<Class>? = null,
    val isFetchClassLoading: Boolean = false,

    val selectedClass: Class? = null,
    val title: String = "",
    val totalQuestion: Int = 0,
)


sealed class TeacherQuizAddEvent {
    object Init : TeacherQuizAddEvent()

    data class ClassSelected(val selectedClass: Class) : TeacherQuizAddEvent()
    data class TitleChanged(val title: String) : TeacherQuizAddEvent()
    data class TotalQuestionChanged(val totalQuestion: Int) : TeacherQuizAddEvent()
}

