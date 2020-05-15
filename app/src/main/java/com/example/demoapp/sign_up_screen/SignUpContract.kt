package com.example.demoapp.sign_up_screen


import android.widget.EditText
import com.example.demoapp.model.User

class SignUpContract {
    interface View {
        fun showError(mess: String)
        fun signUpSuccess(user: User?)
        fun setHideKeyboard(editText: EditText?)
    }

    abstract class Presenter {
        abstract fun handleSignUp(
            textName: String,
            textUsername: String,
            textPass: String,
            textRePass: String
        )
    }

    interface Model {
        fun signUp(textName: String, textUsername: String, textPass: String): User?

        fun checkEmpty(textInput: String): Boolean

        fun checkMatchPass(textPass: String,textRePass: String): Boolean

        fun checkExistUser(textUsername: String): Boolean
    }
}