package com.example.upfiles.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upfiles.R
import com.example.upfiles.adapter.FileAdapter
import kotlinx.android.synthetic.main.fragment_tab.*


class TabFragment(val isPrivate: Boolean) : Fragment() {

    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        file_rv.layoutManager = LinearLayoutManager(requireContext())

        if(isPrivate) {
            val uid = homeViewModel.getUid()
            homeViewModel.getAllFile(uid).observe(requireActivity()) {
                val fileAdapter = FileAdapter(requireContext(), it)
                file_rv.adapter = fileAdapter
            }
        }else{
            homeViewModel.getAllFile("public").observe(requireActivity()) {
                val fileAdapter = FileAdapter(requireContext(), it)
                file_rv.adapter = fileAdapter
            }
        }
    }

}