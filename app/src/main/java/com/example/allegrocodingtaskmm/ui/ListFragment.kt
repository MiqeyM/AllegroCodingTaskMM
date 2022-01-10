package com.example.allegrocodingtaskmm.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allegrocodingtaskmm.R
import com.example.allegrocodingtaskmm.databinding.ListFragmentBinding
import com.example.allegrocodingtaskmm.model.Repo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListFragment : Fragment(), OnEntryClickListener {

    private var _binding: ListFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repoAdapter = RepoAdapter(this)
        val recyclerView = binding.recyclerView
        binding.refreshButton.apply {
            visibility = View.INVISIBLE
            setOnClickListener {  repoAdapter.retry()}
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                repoAdapter.withLoadStateHeaderAndFooter(ReposLoadStateAdapter { repoAdapter.retry() },
                    ReposLoadStateAdapter { repoAdapter.retry() })
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        val pagingData = viewModel.pagingDataFlow

        lifecycleScope.launch {
            pagingData.collectLatest { repoAdapter.submitData(it) }
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collect { state ->
                val refreshButton = binding.refreshButton
                refreshButton.isVisible = state.source.refresh is LoadState.Error

            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onEntryClick(clickedRepo: Repo) {
        Log.i("ListFragment", "${clickedRepo.name} clicked")
        viewModel.setDetailedRepo(clickedRepo)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            replace<DetailFragment>(R.id.container, DetailFragment.DETAIL_FRAGMENT_TAG)
        }
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}