package com.example.MyNotes.ui.detail

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.MyNotes.MainActivity
import com.example.MyNotes.R
import com.example.MyNotes.databinding.ListDetailFragmentBinding
import com.example.MyNotes.models.TaskList

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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ListDetailViewModel::class.java)
        // TODO: Use the ViewModel

        /*val list:TaskList? = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)
        list?.let{
            viewModel.list = list
            requireActivity().title = list.name
        }*/

        Log.d(TAG,"Fragment Created")
        try {
            (activity as MainActivity?)?.LoadEditText()
            Log.d(TAG,"Done Load")
        }
        catch (e: ClassCastException) { Log.d(TAG,"ClassCastException") }
        finally {
        }
    }

}