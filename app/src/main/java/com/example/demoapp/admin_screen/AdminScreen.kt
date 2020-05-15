package com.example.demoapp.admin_screen

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.R
import com.example.demoapp.home_screen.MainActivity
import kotlinx.android.synthetic.main.activity_admin_screen.buttonLogOutAdmin
import kotlinx.android.synthetic.main.activity_admin_screen.listView

class AdminScreen : AppCompatActivity() {
    private lateinit var dataHandler: DataHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_screen)
        initView()
        initAction()
    }

    private fun initView() {
        showAllUsers()
    }

    private fun initAction() {
        buttonLogOutAdmin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAllUsers() {
        dataHandler = DataHandler(this)
        val users = dataHandler.readAllUser()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        listView.adapter = adapter

    }
}
