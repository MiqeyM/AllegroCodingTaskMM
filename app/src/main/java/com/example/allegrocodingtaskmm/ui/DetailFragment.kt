package com.example.allegrocodingtaskmm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.allegrocodingtaskmm.R
import com.example.allegrocodingtaskmm.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding
        get() = _binding!!


    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentRepo = viewModel.details
        binding.nameTextView.text = currentRepo?.fullName
        binding.descTextView.text = currentRepo?.description
            ?: getString(R.string.no_description_info)
        binding.languageTextView.text = resources.getString(
            R.string.language,
            currentRepo?.language ?: getString(R.string.language_null_info)
        )
        binding.starCountTextView.text = currentRepo?.stars.toString()


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object {
        const val DETAIL_FRAGMENT_TAG: String = "detail_fragment"

        fun newInstance() = DetailFragment()
    }

}