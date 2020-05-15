package com.example.demoapp.profile_screen


import android.widget.EditText
import android.widget.TextView

class ProfileContract {
    interface View {
        fun showToast(mess: String)
        fun setHideKeyboard(editText: EditText?)
        fun displayDialogEditName()
        fun displayDialogEditPass()
        fun displayDialogDelete()
        fun hideDialog(dialog: androidx.appcompat.app.AlertDialog)
        fun setText(textView: TextView, text: String)
    }

    abstract class Presenter {
        abstract fun deleteUser(id: Int)
        abstract fun editName(
            id: Int,
            name: String,
            textView: TextView,
            dialog: androidx.appcompat.app.AlertDialog
        )

        abstract fun editPass(
            id: Int,
            password: String,
            oldPass: String,
            newPass: String,
            rePass: String,
            textView: TextView,
            dialog: androidx.appcompat.app.AlertDialog
        )
    }

    interface Model {
        fun deleteUser(id: Int)
        fun editName(id: Int, name: String)
        fun editPass(id: Int, newPass: String)
        fun checkEmpty(textInput: String): Boolean
        fun checkMatchPass(pass1: String, pass2: String): Boolean
    }
}