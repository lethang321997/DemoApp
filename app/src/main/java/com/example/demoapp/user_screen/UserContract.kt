package com.example.demoapp.user_screen


import androidx.appcompat.app.AlertDialog

class UserContract {
    interface View {
        fun displayDialogAdd()
        fun displayDialogLogOut()
        fun clickProfile()
        fun clickHistory()
        fun setTextMoney(text: String)
        fun showToast(mess: String)
        fun hideDialog(dialog: AlertDialog)
    }

    abstract class Presenter {
        abstract fun handleMoneyAdd(id: Int,note: String, money: Int, moneyAdd: String, dialog: AlertDialog)
    }

    interface Model {
        fun addMoney(id: Int,note: String, money: Int, moneyAdd: Int)
    }
}