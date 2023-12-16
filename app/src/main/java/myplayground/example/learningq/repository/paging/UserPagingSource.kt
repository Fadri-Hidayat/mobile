package myplayground.example.learningq.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import myplayground.example.learningq.model.User
import myplayground.example.learningq.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.fetchUser(
                currentPage,
                params.loadSize,
            )
            val user = response.data

            LoadResult.Page(
                data = user,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (user.isEmpty()) null else response.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}