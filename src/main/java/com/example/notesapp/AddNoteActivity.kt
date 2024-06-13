package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding

class AddNoteActivity : AppCompatActivity() {
    
    private  lateinit var binding: ActivityAddNoteBinding
    private lateinit var db : NoteDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        
        db = NoteDatabase(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleText.text.toString()
            val des = binding.desText.text.toString()
            val note = Note(0,title,des)
            db.insertNote(note)
            finish()
            Toast.makeText(this,"note saved",Toast.LENGTH_SHORT).show()
        }


    }
}