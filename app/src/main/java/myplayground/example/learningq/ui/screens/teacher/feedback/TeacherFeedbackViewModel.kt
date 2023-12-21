package myplayground.example.learningq.ui.screens.teacher.feedback

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.model.Feedback
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.utils.AuthManager
import myplayground.example.learningq.utils.Classifier
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TeacherFeedbackViewModel(
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
    authManager: AuthManager,
) : ViewModel() {
    private val _uiState = mutableStateOf(TeacherFeedbackData())
    private val user = authManager.user.value

    private var tfLiteModel: Interpreter? = null

    val uiState: State<TeacherFeedbackData> = _uiState

    init {
        onEvent(TeacherFeedbackEvent.Init)
    }

    fun onEvent(event: TeacherFeedbackEvent) {
        viewModelScope.launch {
            when (event) {
                is TeacherFeedbackEvent.Init -> {
                }

                is TeacherFeedbackEvent.InitNLP -> {
                    tfLiteModel = Interpreter(loadModelFile(event.context))
                }

                is TeacherFeedbackEvent.TeacherFeedbackFetch -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingFetchFeedback = true
                    )

                    val feedbacks = repository.fetchTeacherFeedbackByTeacherUserIdAndCourseId(
                        user!!.id,
                        event.courseId,
                        Injection.provideApiService(localStorageManager),
                    )

                    _uiState.value = _uiState.value.copy(
                        listFeedback = feedbacks.toMutableStateList(),
                        isLoadingFetchFeedback = false
                    )

                    predict(event.context, feedbacks.toMutableList())
                }

            }
        }
    }


    fun predict(context: Context, listFeedback: MutableList<Feedback>) {
        listFeedback.forEachIndexed { idx, feedback ->

            val inputSentence = feedback.content
            val inputSize = inputSentence.split(" ").size;

            val classifier = Classifier(context, "nlp_token.json", inputSize)
            classifier.processVocab(object : Classifier.VocabCallback {
                override fun onVocabProcessed() {


                    val tokenizedMessage = classifier.tokenize(inputSentence)
                    val paddedMessage = classifier.padSequence(tokenizedMessage)

                    val results = classifySequence(paddedMessage)

                    val updatedListFeedback = _uiState.value.listFeedback
                    updatedListFeedback[idx] = updatedListFeedback[idx].copy(
                        isGoodResponse = results[0] >= 0.5
                    )

                    _uiState.value = _uiState.value.copy(
                        listFeedback = updatedListFeedback
                    )
                }
            })
        }
    }

    private fun classifySequence(sequence: IntArray): FloatArray {
        val inputs: Array<FloatArray> = arrayOf(sequence.map { it.toFloat() }.toFloatArray())

        val outputs: Array<FloatArray> = arrayOf(FloatArray(1))
        tfLiteModel?.run(inputs, outputs)
        return outputs[0]
    }


    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("nlp.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declareLength = fileDescriptor.declaredLength

        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            startOffset,
            declareLength,
        )
    }

    override fun onCleared() {
        super.onCleared()


        tfLiteModel?.close()
    }

}