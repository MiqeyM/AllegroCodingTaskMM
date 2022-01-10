package com.example.allegrocodingtaskmm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.allegrocodingtaskmm.data.GithubRepository
import com.example.allegrocodingtaskmm.model.Repo
import com.example.allegrocodingtaskmm.network.GithubApi
import com.example.allegrocodingtaskmm.network.GithubApi.retrofitService
import com.example.allegrocodingtaskmm.network.GithubService
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    private val repository = GithubRepository(retrofitService)
    val pagingDataFlow: Flow<PagingData<Repo>> = repository.getReposStream()
        .cachedIn(viewModelScope)

    private var _details: Repo? = null
    val details: Repo?
        get() = _details

    fun setDetailedRepo(repo: Repo) {
        _details = repo
    }

}