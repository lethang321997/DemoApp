package com.example.demoapp.user_screen


import androidx.appcompat.app.AlertDialog
import com.example.demoapp.common.DemoApp
import com.example.demoapp.R


class UserPresenter : UserContract.Presenter() {
    private var view: UserContract.View? = null
    var model: UserModel? = UserModel()
    fun setView(v: UserContract.View) {
        view = v
    }

    override fun handleMoneyAdd(
        id: Int,
        note: String,
        money: Int,
        moneyAdd: String,
        dialog: AlertDialog
    ) {
        when {
            note.isEmpty() -> {
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_note))
            }
            moneyAdd.isEmpty() -> {
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_money))
            }
            else -> {
                model?.addMoney(id, note, money, moneyAdd.toInt())
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_success))
                view?.setTextMoney((money + moneyAdd.toInt()).toString())
                view?.hideDialog(dialog)
            }
        }

    }
}