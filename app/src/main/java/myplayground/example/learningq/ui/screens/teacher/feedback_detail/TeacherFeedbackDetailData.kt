package myplayground.example.learningq.ui.screens.teacher.feedback_detail

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import myplayground.example.learningq.model.Feedback

data class TeacherFeedbackDetailData(
    val feedback: Feedback? = null,
    val isLoadingFetchFeedback: Boolean = false,
)

sealed class TeacherFeedbackDetailEvent {
    object Init : TeacherFeedbackDetailEvent()

    data class FetchFeedback(val feedbackId: String) : TeacherFeedbackDetailEvent()
}