package myplayground.example.learningq.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import myplayground.example.learningq.model.Quiz
import myplayground.example.learningq.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class QuizPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, Quiz>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quiz> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.fetchStudentQuiz(
                currentPage,
                LIMIT,
            )
            val quiz = response.data

            LoadResult.Page(
                data = quiz,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (quiz.isEmpty()) null else response.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Quiz>): Int? {
        return state.anchorPosition
    }

    companion object {
        const val LIMIT = 5
    }
}