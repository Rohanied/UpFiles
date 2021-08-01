package com.example.upfiles.ui.addfile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.onimur.handlepathoz.HandlePathOz
import br.com.onimur.handlepathoz.HandlePathOzListener
import br.com.onimur.handlepathoz.model.PathOz
import com.example.upfiles.MainActivity
import com.example.upfiles.R
import com.example.upfiles.data.FirebaseRepository
import kotlinx.android.synthetic.main.fragment_add_file.*
import kotlinx.android.synthetic.main.fragment_add_file.view.*


class AddFileFragment : Fragment(), HandlePathOzListener.SingleUri {


    private lateinit var handlePathOz: HandlePathOz
    private lateinit var addFileViewModel: AddFileViewModel
    var filePath = ""
    var isPrivate = false
    var isRadioButtonSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFileViewModel = ViewModelProvider(this).get(AddFileViewModel::class.java)

        handlePathOz = HandlePathOz(requireContext(),this)

        select_file_btn.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }

        radio_grp.setOnCheckedChangeListener { group, checkedId ->
            isPrivate = checkedId==private_radio.id
            isRadioButtonSelected=true
        }

        upload_btn.setOnClickListener {

            if(isRadioButtonSelected) {
                if(filePath.isNotEmpty()) {
                    view.progress_bar.visibility = View.VISIBLE
                    addFileViewModel.uploadFile(filePath, isPrivate).observe(requireActivity()) {
                        if (it) {
                            Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_LONG).show()
                            view.progress_bar.visibility = View.GONE
                            findNavController().navigate(R.id.action_addFileFragment_to_homeFragment)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong, try again",
                                Toast.LENGTH_LONG
                            ).show()
                            view.progress_bar.visibility = View.GONE
                            findNavController().navigate(R.id.action_addFileFragment_to_homeFragment)
                        }
                    }
                }else{
                    Toast.makeText(requireContext(),"File Not Selected", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(requireContext(),"Select Private or Public", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
            handlePathOz.getRealPath(selectedFile!!)

        }

    }

    override fun onRequestHandlePathOz(pathOz: PathOz, tr: Throwable?) {

        filePath = pathOz.path
        val index = filePath.lastIndexOf("/")
        val fileName = filePath.substring(index+1)

        file_name_tv.text = fileName

        tr?.let {
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

}