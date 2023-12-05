package myplayground.example.learningq.ui.screens.student.quiz

import androidx.lifecycle.ViewModel
import myplayground.example.learningq.repository.Repository

class StudentQuizViewModel(private val repository: Repository) : ViewModel() {

//    EXAMPLE
//    init {
//        repository.fetchQuiz(1, 5).enqueue(object : Callback<List<Quiz>> {
//            override fun onResponse(call: Call<List<Quiz>>, response: Response<List<Quiz>>) {
//                response.body()?.let { listQuiz ->
//                    Log.i("QUIZZZZZ", listQuiz.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<List<Quiz>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}