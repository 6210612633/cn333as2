package com.example.MyNotes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.MyNotes.databinding.ListDetailActivityBinding
import com.example.MyNotes.databinding.ListDetailFragmentBinding
import com.example.MyNotes.models.TaskList
import com.example.MyNotes.ui.detail.ListDetailFragment

class ListDetailActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var binding: ListDetailActivityBinding
    //private lateinit var binding: ListDetailFragmentBinding
    private lateinit var noteContent : EditText
    //private lateinit var showContent: TextView
    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListDetailActivityBinding.inflate(layoutInflater)
        //binding = ListDetailFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.list_detail_activity)
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title = list.name


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListDetailFragment.newInstance())
                .commitNow()
        }

    }
    public override fun onPostCreate(savedInstanceState: Bundle?) {
        //TEST
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        sharedPreference = getSharedPreferences(list.name, MODE_PRIVATE)
        noteContent = findViewById(R.id.noteContent)
        var loadText = sharedPreference.getString(list.name,"")
        noteContent.setText(loadText)
        //TEST
        super.onPostCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        sharedPreference = getSharedPreferences(list.name, MODE_PRIVATE)
        noteContent = findViewById(R.id.noteContent)

        var edited = noteContent.text.toString()

        noteContent.setText(edited)
        val editor:SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(list.name,edited).apply()


        super.onBackPressed()
    }
}