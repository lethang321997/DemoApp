package com.example.demoapp.home_screen


import android.widget.EditText
import com.example.demoapp.model.User

class HomeContract {
    interface View {
        fun loginSuccess(user: User?)
        fun loginFail()
        fun loginWithAdmin()
        fun setHideKeyboard(editText: EditText?)
    }

    abstract class Presenter {
        abstract fun handleLogin(userName: String, password: String)
    }

    interface Model {
        fun login(userName: String, password: String): User?
    }
}