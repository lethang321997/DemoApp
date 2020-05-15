package com.example.demoapp.profile_screen


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.common.DemoApp
import com.example.demoapp.R
import com.example.demoapp.common.Constant
import com.example.demoapp.common.Constant.Companion.STRING_EMPTY
import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.home_screen.MainActivity
import com.example.demoapp.model.User
import kotlinx.android.synthetic.main.activity_profile_screen.*
import kotlinx.android.synthetic.main.dialog_edit_name.*
import kotlinx.android.synthetic.main.dialog_edit_name.view.*
import kotlinx.android.synthetic.main.dialog_password.*
import kotlinx.android.synthetic.main.dialog_password.view.*


class ProfileScreen : AppCompatActivity(), ProfileContract.View {
    lateinit var user : User
    var presenter: ProfilePresenter? = null
    var money: Int = 0
    var id: Int = 0
    var name: String = STRING_EMPTY
    private var username: String = STRING_EMPTY
    var password: String = STRING_EMPTY
    private val db: DataHandler = DataHandler(DemoApp.context())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)
        initData()
        initView()
        initAction()
    }

    private fun initData() {
        presenter = ProfilePresenter()
        id = intent.getIntExtra(Constant.KEY_ID,-1)
        user = db.getUser(id)!!
        username = user.username
        name = user.name
        password = user.password
        money = user.money
    }

    private fun initView() {
        presenter?.setView(this)
        idProfile.text = "$id"
        usernameProfile.text = username
        nameProfile.text = name
        passwordProfile.text = password
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initAction() {
        buttonEditName.setOnClickListener {
            displayDialogEditName()
        }
        buttonEditPass.setOnClickListener {
            displayDialogEditPass()
        }
        buttonDeleteUser.setOnClickListener {
            displayDialogDelete()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayDialogEditName() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_name, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        dialogView.editName.setText(name)
        val dialogAdd = builder.show()
        dialogAdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogAdd.buttonEditNameDone.setOnClickListener {
            presenter?.editName(
                id,
                dialogAdd.editName.text.toString(),
                nameProfile,
                dialogAdd
            )
            intent.putExtra(Constant.KEY_NAME, nameProfile.text)
            setResult(RESULT_OK, intent)
        }
    }

    override fun displayDialogEditPass() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_password, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        setHideKeyboard(dialogView.editOldPass)
        setHideKeyboard(dialogView.editNewPass)
        setHideKeyboard(dialogView.editRePass)
        val dialogAdd = builder.show()
        dialogAdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogAdd.buttonEditPassDone.setOnClickListener {
            val oldPass = dialogAdd.editOldPass.text.toString()
            val newPass = dialogAdd.editNewPass.text.toString()
            val rePass = dialogAdd.editRePass.text.toString()
            presenter?.editPass(
                id,
                password,
                oldPass,
                newPass,
                rePass,
                passwordProfile,
                dialogAdd
            )
        }
    }

    override fun displayDialogDelete() {
        val builder = AlertDialog.Builder(this)
            .setTitle(this.resources.getString(R.string.title_confirm))
            .setMessage(this.resources.getString(R.string.text_delete_account))
            .setPositiveButton(this.resources.getString(R.string.text_yes)) { _, _ ->
                presenter?.deleteUser(id)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton(this.resources.getString(R.string.text_no)) { _, _ ->

            }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun hideDialog(dialog: AlertDialog) {
        dialog.dismiss()
    }


    override fun setText(textView: TextView, text: String) {
        textView.text = text
    }

    override fun showToast(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
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
