package com.example.allegrocodingtaskmm.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.allegrocodingtaskmm.data.GithubRepository.Companion.NETWORK_PAGE_SIZE
import com.example.allegrocodingtaskmm.model.Repo
import com.example.allegrocodingtaskmm.network.GithubService
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: GithubService
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            /*val response = service.getRepos(position, params.loadSize)
            val repos = response.repos*/
            val repos = service.getRepos(position, params.loadSize)

            val nextKey = if (repos.isEmpty())
                null
            else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                repos,
                if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}