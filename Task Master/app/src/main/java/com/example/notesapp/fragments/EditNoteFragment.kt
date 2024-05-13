package com.example.notesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewmodel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editnoteBinding : FragmentEditNoteBinding? = null
    private val binding get() = editnoteBinding!!

    private lateinit var notesviewmodel: NoteViewmodel
    private lateinit var currentNote: Note

    private val args : EditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editnoteBinding = FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesviewmodel = (activity as MainActivity).noteviewmodel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.content)

        binding.editNoteFab.setOnClickListener {
            val title = binding.editNoteTitle.text.toString().trim()
            val content = binding.editNoteDesc.text.toString().trim()

            if (title.isNotEmpty()){
                val note = Note(currentNote.id,title,content)
                notesviewmodel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(context,"Title can't be empty",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Task")
            setMessage("Are you sure you want to delete this Task?")
            setPositiveButton("Delete"){_,_ ->
                notesviewmodel.deleteNote(currentNote)
                Toast.makeText(context,"Task Completed",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editnoteBinding = null
    }



}