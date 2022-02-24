package com.example.MyNotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.MyNotes.databinding.MainActivityBinding
import com.example.MyNotes.models.TaskList
import com.example.MyNotes.ui.detail.ListDetailFragment
import com.example.MyNotes.ui.main.MainFragment
import com.example.MyNotes.ui.main.MainViewModel
import com.example.MyNotes.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity(),MainFragment.MainFragmentInteractionListener {
    private lateinit var list: TaskList
    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel
    lateinit var sharedPreference: SharedPreferences
    lateinit var listDetailEdittext: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        ).get(MainViewModel::class.java)


        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val mainFragment = MainFragment.newInstance(this)
            val fragmentContainerViewId: Int = if(binding.mainFragmentContainer == null){
                R.id.container
            } else{
                R.id.main_fragment_container
            }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainerViewId,mainFragment)
            }

        }

        binding.taskListAddButton.setOnClickListener{
            showCreateListDialog()
        }
    }

    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle){ dialog, _ ->
            dialog.dismiss()
            val taskList = TaskList(listTitleEditText.text.toString())
            viewModel.saveList(taskList)
            showListDetail(taskList)
        }
        builder.create().show()
    }

    private fun showListDetail(list:TaskList){
        if(binding.mainFragmentContainer == null){
            val listDetailIntent = Intent(this,ListDetailActivity::class.java)
            listDetailIntent.putExtra(INTENT_LIST_KEY,list)
            startActivity(listDetailIntent)
        }else {
            /*val bundle = bundleOf(INTENT_LIST_KEY to list)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.list_detail_fragment_container,ListDetailFragment::class.java,bundle,null)
            }*/
            title = list.name
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.list_detail_fragment_container, ListDetailFragment.newInstance())
                LoadEditText()
            }

        }

    }


    companion object{
        const val INTENT_LIST_KEY = "list"
        var LIST_NAME = ""
    }

    override fun listItemTapped(list: TaskList) {
        LIST_NAME = list.name
        showListDetail(list)
    }

    fun LoadEditText() {
        if (binding.listDetailFragmentContainer != null){
            //list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
            sharedPreference = getSharedPreferences("", MODE_PRIVATE)
            listDetailEdittext = findViewById(R.id.noteContent)
            var loadText = sharedPreference.getString(LIST_NAME,"")
            listDetailEdittext.setText(loadText)
        }
    }

    override fun onBackPressed() {
        if (binding.listDetailFragmentContainer != null){

            sharedPreference = getSharedPreferences("", MODE_PRIVATE)
            listDetailEdittext = findViewById(R.id.noteContent)

            var edited = listDetailEdittext.text.toString()
            sharedPreference.edit().putString(LIST_NAME,edited).apply()


            title = resources.getString(R.string.app_name)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                remove(supportFragmentManager.findFragmentById(R.id.list_detail_fragment_container)!!)
            }
        }
        else{super.onBackPressed()}
    }

    /*override fun onBackPressed() {
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        sharedPreference = getSharedPreferences(list.name, MODE_PRIVATE)
        listDetailEdittext = findViewById(R.id.noteContent)

        var edited = listDetailEdittext.text.toString()

        listDetailEdittext.setText(edited)
        val editor:SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(list.name,edited).apply()

        super.onBackPressed()
    }*/

}