package com.example.demoapp.sign_up_screen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.demoapp.model.User
import com.example.demoapp.R
import com.example.demoapp.common.Constant
import com.example.demoapp.user_screen.UserScreen
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

class SignUpScreen : AppCompatActivity(), SignUpContract.View {
    var presenter = SignUpPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        initView()
        initAction()
    }

    private fun initView() {
        presenter.setView(this)
        setHideKeyboard(nameSignUp)
        setHideKeyboard(usernameSignUp)
        setHideKeyboard(passwordSignUp)
        setHideKeyboard(re_passwordSignUp)
    }

    private fun initAction() {
        signUpScreenBtn.setOnClickListener {
            val textName = nameSignUp.text.toString()
            val textUsername = usernameSignUp.text.toString()
            val textPass = passwordSignUp.text.toString()
            val textRePass = re_passwordSignUp.text.toString()
            presenter.handleSignUp(textName, textUsername, textPass, textRePass)
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(this.resources.getString(R.string.title_alert))
        builder.setMessage(message)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun showError(mess: String) {
        showAlertDialog(mess)
    }

    override fun signUpSuccess(user: User?) {
        val intent = Intent(this, UserScreen::class.java)
        if (user != null) {
            intent.putExtra(Constant.KEY_ID, user.id)
        }
        startActivity(intent)
    }

    override fun setHideKeyboard(editText: EditText?) {
        editText?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager: InputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }
}
