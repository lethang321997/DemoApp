package com.example.demoapp.home_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.admin_screen.AdminScreen
import com.example.demoapp.model.User
import com.example.demoapp.R
import com.example.demoapp.sign_up_screen.SignUpScreen
import com.example.demoapp.user_screen.UserScreen
import com.example.demoapp.common.Constant
import com.example.demoapp.common.DemoApp
import com.example.demoapp.dataBaseHandler.DataHandler
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), HomeContract.View {

    val presenter = HomePresenter()
 private lateinit var db : DataHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
        initAction()

    }

    private fun initView() {
        presenter.setView(this)
        setHideKeyboard(text_username)
        setHideKeyboard(text_password)
    }

    private fun initData(){
        db = DataHandler(DemoApp.context())
        db.createTable()
    }

    private fun initAction() {
        buttonSignIn.setOnClickListener {
            val user = text_username.text.toString()
            val pass = text_password.text.toString()
            presenter.handleLogin(user, pass)
        }
        buttonSignUp.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }
    }

    override fun loginSuccess(user: User?) {
        val intent = Intent(this, UserScreen::class.java)
        if (user != null) {
              intent.putExtra(Constant.KEY_ID, user.id)
        }
        startActivity(intent)
    }

    override fun loginFail() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(this.resources.getString(R.string.message_incorrect_user_pass))
        builder.setTitle(this.resources.getString(R.string.title_alert))
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun loginWithAdmin() {
        val intent = Intent(this, AdminScreen::class.java)
        startActivity(intent)
    }

    override fun setHideKeyboard(editText: EditText?) {
        editText?.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager: InputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }
}
