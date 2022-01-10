package com.example.allegrocodingtaskmm.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.allegrocodingtaskmm.model.Repo
import com.example.allegrocodingtaskmm.network.GithubService
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val service: GithubService) {
    fun getReposStream(): Flow<PagingData<Repo>> {
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(service) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}