package me.arwan.mov.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.arwan.mov.models.response.PagingResponse

class MoviePagingSource<T : Any>(
    private val response: suspend (Int) -> PagingResponse<T>,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 1
            val response = response.invoke(nextPage)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.page < response.total_pages) response.page.inc() else null
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}