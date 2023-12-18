package myplayground.example.learningq.ui.screens.sign_in

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.LocalStorageManager
import myplayground.example.learningq.repository.Repository
import myplayground.example.learningq.repository.UserLoginInput
import myplayground.example.learningq.ui.utils.StringValidationRule
import myplayground.example.learningq.ui.utils.validate
import myplayground.example.learningq.utils.allNull
import org.tensorflow.lite.Interpreter
import retrofit2.HttpException
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SignInViewModel(
    private val context: Context,
    private val repository: Repository,
    private val localStorageManager: LocalStorageManager,
) : ViewModel() {
    private val _uiState = mutableStateOf(SignInInputData())
    private val _isLoading = mutableStateOf(false)

    val uiState: State<SignInInputData> = _uiState
    val isLoading: State<Boolean> = _isLoading

    val validationEvent = MutableSharedFlow<SignInUIEvent.ValidationEvent>()

    fun onEvent(event: SignInUIEvent) {
        when (event) {
            is SignInUIEvent.UsernameChanged -> {
                _uiState.value = _uiState.value.copy(username = event.username)
            }

            is SignInUIEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }

            is SignInUIEvent.Submit -> {
                validateAndSubmit()
            }
        }
    }

    private fun validateAndSubmit() {
        val usernameResultError = _uiState.value.username.validate(
            StringValidationRule.Required("Required field"),
        ).toErrorMessage()

        val passwordResultError = _uiState.value.password.validate(
            StringValidationRule.Required("Required field")
        ).toErrorMessage()

        _uiState.value = _uiState.value.copy(usernameError = usernameResultError)
        _uiState.value = _uiState.value.copy(passwordError = passwordResultError)

        val hasError = !listOf(
            usernameResultError,
            passwordResultError,
        ).allNull()

        viewModelScope.launch {
            if (!hasError) {
                _isLoading.value = true

                try {
                    val token = repository.userLogin(
                        UserLoginInput(
                            username = _uiState.value.username,
                            password = _uiState.value.password,
                        ),
                        Injection.provideApiService(localStorageManager),
                    )

                    if (token?.auth_token != null && token.auth_token.isNotEmpty()) {
                        localStorageManager.saveUserToken(token.auth_token ?: "")

                        validationEvent.emit(SignInUIEvent.ValidationEvent.Success())
                    }
                } catch (e: HttpException) {
                    _uiState.value =
                        _uiState.value.copy(formError = e.response()?.errorBody()?.string())
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun predict(context: Context) {
        val tfLiteModel = Interpreter(loadModelFile(context))

        val inputSentence =
            "Gurunya sangat baik, rajin mengajar, dan menjelaskan agar murid mudah mengerti tentang mata pelajaran yang diajarkan"
        val inputSize = inputSentence.length * 4;

        val byteBuffer = ByteBuffer.allocateDirect(inputSize)
        byteBuffer.order(ByteOrder.nativeOrder())

//        val temp = Float.parseFloat(inputSentence)

//        byteBuffer.putFloat(temp)
        for (element in inputSentence) {
            byteBuffer.putChar(element)
        }


//        val output = ByteBuffer.allocateDirect(2)
//        output.order(ByteOrder.nativeOrder())
        val output: Array<FloatArray> = arrayOf(floatArrayOf(0.0f, 0.0f))

        tfLiteModel.run(byteBuffer, output)

        tfLiteModel.close()

    }


//    private fun tokenize ( message : String ): IntArray {
//        val parts : List<String> = message.split(" " )
//        val tokenizedMessage = ArrayList<Int>()
//        for ( part in parts ) {
//            if (part.trim() != ""){
//                var index : Int? = 0
//                if ( vocabData!![part] == null ) {
//                    index = 0
//                }
//                else{
//                    index = vocabData!![part]
//                }
//                tokenizedMessage.add( index!! )
//            }
//        }
//        return tokenizedMessage.toIntArray()
//    }
//
//    private fun padSequence ( sequence : IntArray ) : IntArray {
//        val maxlen = this.maxlen
//        if ( sequence.size > maxlen!!) {
//            return sequence.sliceArray( 0..maxlen )
//        }
//        else if ( sequence.size < maxlen ) {
//            val array = ArrayList<Int>()
//            array.addAll( sequence.asList() )
//            for ( i in array.size until maxlen ){
//                array.add(0)
//            }
//            return array.toIntArray()
//        }
//        else{
//            return sequence
//        }
//    }

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

}