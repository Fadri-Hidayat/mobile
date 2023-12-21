package myplayground.example.learningq.ui.screens.teacher.feedback

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import myplayground.example.learningq.model.Feedback

data class TeacherFeedbackData(
    val listFeedback: SnapshotStateList<Feedback> = listOf<Feedback>().toMutableStateList(),
    val isLoadingFetchFeedback: Boolean = false,
)

sealed class TeacherFeedbackEvent {
    object Init : TeacherFeedbackEvent()

    data class InitNLP(val context: Context) : TeacherFeedbackEvent()
    data class TeacherFeedbackFetch(val context: Context, val courseId: String) :
        TeacherFeedbackEvent()
}