package com.example.upfiles.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.upfiles.R
import com.example.upfiles.data.FirebaseRepository
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private var fragmentsList = arrayListOf<Fragment>(TabFragment(true),TabFragment(false))
    private var fragmentTitles = arrayListOf("Private Files", "Public Files")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(isAdded) {
            val adapter = PageAdapter(requireActivity(), fragmentsList)
            home_view_pager.adapter = adapter

            TabLayoutMediator(
                home_tab_layout,
                home_view_pager
            ) { tab, position ->
                tab.text = fragmentTitles[position]
            }.attach()
        }

        add_file_btn.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_addFileFragment)
        }
    }

    class PageAdapter(activity: FragmentActivity, private val fragmentsList: ArrayList<Fragment>): FragmentStateAdapter(activity){


        override fun getItemCount(): Int {
            return fragmentsList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentsList[position]
        }


    }

}