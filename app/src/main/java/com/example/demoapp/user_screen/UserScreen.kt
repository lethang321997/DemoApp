package com.example.demoapp.user_screen


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.common.DemoApp
import com.example.demoapp.R
import com.example.demoapp.common.Constant
import com.example.demoapp.common.Constant.Companion.STRING_EMPTY
import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.history_screen.HistoryScreen
import com.example.demoapp.home_screen.MainActivity
import com.example.demoapp.model.User
import com.example.demoapp.profile_screen.ProfileScreen
import kotlinx.android.synthetic.main.activity_user_screen.*
import kotlinx.android.synthetic.main.dialog_add.*

class UserScreen : AppCompatActivity(), UserContract.View {
    lateinit var user: User
    var presenter = UserPresenter()
    var money: Int = 0
    var id: Int = 0
    var name: String = STRING_EMPTY
    private var username: String = STRING_EMPTY
    var password: String = STRING_EMPTY
    private val db: DataHandler = DataHandler(DemoApp.context())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_screen)
        initData()
        initView()
        initAction()
    }

    private fun initView() {
        presenter.setView(this)
        textWelcome.text = name
        textMoney.text = String.format("%,d", money)
    }

    private fun initData() {
        id = intent.getIntExtra(Constant.KEY_ID, -1)
        user = db.getUser(id)!!
        username = user.username
        name = user.name
        password = user.password
        money = user.money
    }

    private fun initAction() {
        buttonAdd.setOnClickListener {
            displayDialogAdd()
        }
        buttonProfile.setOnClickListener {
            clickProfile()
        }
        buttonLogOut.setOnClickListener {
            displayDialogLogOut()
        }
        buttonHistory.setOnClickListener {
            clickHistory()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    user = db.getUser(id)!!
                    textMoney.text = String.format("%,d", user.money)
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    textWelcome.text = data.getStringExtra(Constant.KEY_NAME)
                }
            }
        }
    }

    override fun displayDialogAdd() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val dialogAdd = builder.show()
        dialogAdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogAdd.buttonAddMoney.setOnClickListener {
            val moneyEdit = dialogAdd.addMoney.text.toString()
            val note = dialogAdd.addNote.text.toString()
            presenter.handleMoneyAdd(
                id,
                note,
                textMoney.text.toString().replace(",", "").toInt(),
                moneyEdit,
                dialogAdd
            )
        }
    }

    override fun displayDialogLogOut() {
        val builder = AlertDialog.Builder(this)
            .setTitle(this.resources.getString(R.string.title_confirm))
            .setMessage(this.resources.getString(R.string.text_log_out))
            .setPositiveButton(this.resources.getString(R.string.text_yes)) { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton(this.resources.getString(R.string.text_no)) { _, _ ->
            }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun clickProfile() {
        val intent = Intent(this, ProfileScreen::class.java)
        intent.putExtra(Constant.KEY_ID, id)
        startActivityForResult(intent, 2)
    }

    override fun clickHistory() {
        val intent = Intent(this, HistoryScreen::class.java)
        intent.putExtra(Constant.KEY_ID, id)
        intent.putExtra(Constant.KEY_MONEY, textMoney.text.toString().replace(",", "").toInt())
        startActivityForResult(intent, 1)
    }

    override fun setTextMoney(text: String) {
        textMoney.text = String.format("%,d", text.toInt())
    }

    override fun showToast(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
    }

    override fun hideDialog(dialog: AlertDialog) {
        dialog.dismiss()
    }

}
