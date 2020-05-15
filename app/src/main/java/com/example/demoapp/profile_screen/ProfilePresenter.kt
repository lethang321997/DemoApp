package com.example.demoapp.profile_screen

import android.widget.TextView
import com.example.demoapp.common.DemoApp
import com.example.demoapp.R

class ProfilePresenter : ProfileContract.Presenter() {
    private var view: ProfileContract.View? = null
    val model: ProfileModel? = ProfileModel()
    fun setView(v: ProfileContract.View) {
        view = v
    }

    override fun deleteUser(id: Int) {
        model?.deleteUser(id)
        view?.showToast(DemoApp.context().resources.getString(R.string.alert_success))
    }

    override fun editName(
        id: Int,
        name: String,
        textView: TextView,
        dialog: androidx.appcompat.app.AlertDialog
    ) {
        if (name.isEmpty()) {
            view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_name))
        } else {
            model?.editName(id, name)
            view?.showToast(DemoApp.context().resources.getString(R.string.alert_success))
            view?.setText(textView, name)
            view?.hideDialog(dialog)
        }
    }

    override fun editPass(
        id: Int,
        password: String,
        oldPass: String,
        newPass: String,
        rePass: String,
        textView: TextView,
        dialog: androidx.appcompat.app.AlertDialog
    ) {
        when {
            model?.checkEmpty(oldPass) == true -> {
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_old_pass))
            }
            model?.checkEmpty(newPass) == true -> {
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_new_pass))
            }
            model?.checkEmpty(rePass) == true -> {
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_re_pass))
            }
            model?.checkMatchPass(oldPass, password) == false -> {
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_exactly_old_pass))
            }
            model?.checkMatchPass(oldPass, rePass) == false -> {
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_enter_exactly_pass))
            }
            else -> {
                model?.editPass(id, newPass)
                view?.setText(textView, newPass)
                view?.showToast(DemoApp.context().resources.getString(R.string.alert_success))
                view?.hideDialog(dialog)
            }
        }
    }
}