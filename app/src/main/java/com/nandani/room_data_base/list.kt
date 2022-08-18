package com.nandani.room_data_base

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.nandani.room_data_base.R
import com.nandani.room_data_base.databinding.FragmentListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [List.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class List : Fragment() ,RecyclerClick{
    class ArrayListFragment : Fragment() ,RecyclerClick{
        // TODO: Rename and change types of parameters
        private var param1: String? = null
        private var param2: String? = null
        var arrayList : ArrayList<Notes> =ArrayList()
        lateinit var binding :FragmentListBinding
        lateinit var arrayListFragment:  MainActivity
        lateinit var adapter: Notes


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arrayListFragment = activity as MainActivity
            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            binding = FragmentListBinding.inflate(layoutInflater)
            adapter = Notes(arrayList, this)
            binding.listView.layoutManager = LinearLayoutManager(requireContext())
            binding.listView.adapter=adapter
            binding.fabAdd.setOnClickListener{
                var dialogBinding = NewItemAddLayoutBinding.inflate(layoutInflater)
                var dialog= Dialog(requireContext())
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)

                dialogBinding.btnOk.setOnClickListener {
                    if (dialogBinding.etNewItem.text.toString().isNullOrEmpty()) {
                        dialogBinding.etNewItem.setError("Enter Title")
                    }else if (dialogBinding.etDescription.text.toString().isNullOrEmpty()) {
                        dialogBinding.etDescription.setError("Enter Description")
                    }
                    else{
                        saveData(dialogBinding.etNewItem.text.toString(), dialogBinding.etDescription.text.toString())
                        dialog.dismiss()
                    }

                }
                dialog.show()
            }
            getNotes()
            return binding.root
        }
        private fun saveData(title: String, description: String) {
            class save : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg p0: Void?): Void? {
                    var notesEntity = Notes()
                    notesEntity.title = title
                    notesEntity.description = description
                    NotesDatabase.getDatabase(requireContext()).NotesDao.insertNotes(notesEntity)
                    return  null
                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    Toast.makeText(requireContext(), "data saved", Toast.LENGTH_SHORT).show()

                }
            }
            save().execute()
        }

        fun getNotes(){
            arrayList.clear()
            class getNote: AsyncTask<Void, Void, Void>(){
                override fun doInBackground(vararg p0: Void?): Void? {
                    arrayList.addAll(NotesDatabase.getDatabase(requireContext()).NotesDao.getNotes())
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    adapter.notifyDataSetChanged()
                }
            }

            getNote().execute()
        }
        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment ArrayListFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                com.harman.roomdatabase.ArrayListFragment.apply {
                    ArrayListFragment.apply {
                        Bundle().apply {
                            putString(ARG_PARAM1, param1)
                            putString(ARG_PARAM2, param2)
                        }
                    }
                }
            override fun notesClicked(notesEntity: Notes) {
                var dialogBinding = NewItemAddLayoutBinding.inflate(layoutInflater)
                var dialog=Dialog(requireContext())
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
                dialogBinding.etNewItem.setText(notesEntity.title)
                dialogBinding.etDescription.setText(notesEntity.description)
                dialogBinding.btnDelete.visibility = View.VISIBLE
                dialogBinding.btnDelete.setOnClickListener {
                    deleteNotes(notesEntity)
                    dialog.dismiss()

                }
                dialogBinding.btnOk.setOnClickListener {
                    if (dialogBinding.etNewItem.text.toString().isNullOrEmpty()) {
                        dialogBinding.etNewItem.setError("Enter Title")
                    }else if (dialogBinding.etDescription.text.toString().isNullOrEmpty()) {
                        dialogBinding.etDescription.setError("Enter Description")
                    }
                    else{
                        saveData(dialogBinding.etNewItem.text.toString(), dialogBinding.etDescription.text.toString())
                        dialog.dismiss()
                    }

                }
                dialog.show()
            }

            private fun deleteNotes(notesEntity: Notes) {
                class deleteN : AsyncTask<Void, Void,Void>(){
                    override fun doInBackground(vararg p0: Void?): Void? {
                        NotesDatabase.getDatabase(requireContext()).NotesDao.delete(notesEntity)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        Toast.makeText(requireContext(), "Notes Delete",Toast.LENGTH_SHORT).show()
                        getNotes()
                    }
                }

                deleteN().execute()

            }
        }