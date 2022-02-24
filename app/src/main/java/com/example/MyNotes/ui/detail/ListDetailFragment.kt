package com.example.MyNotes.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.MyNotes.R
import com.example.MyNotes.databinding.ListDetailFragmentBinding

class ListDetailFragment : Fragment() {
    lateinit var binding:ListDetailFragmentBinding
    companion object {
        fun newInstance() = ListDetailFragment()
    }

    private lateinit var viewModel: ListDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDetailFragmentBinding.inflate(inflater,container,false)
        //return inflater.inflate(R.layout.list_detail_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ListDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}