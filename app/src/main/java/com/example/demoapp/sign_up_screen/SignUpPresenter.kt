package com.example.demoapp.sign_up_screen

import com.example.demoapp.common.DemoApp
import com.example.demoapp.R


class SignUpPresenter : SignUpContract.Presenter() {
    private var view: SignUpContract.View? = null
    var model: SignUpModel? = SignUpModel()
    fun setView(v: SignUpContract.View) {
        view = v
    }

    override fun handleSignUp(
        textName: String,
        textUsername: String,
        textPass: String,
        textRePass: String
    ) {
        when {
            model?.checkEmpty(textName) == true -> {
                view?.showError(DemoApp.context().resources.getString(R.string.alert_enter_name))
            }
            model?.checkEmpty(textUsername) == true -> {
                view?.showError(DemoApp.context().resources.getString(R.string.alert_enter_username))
            }
            model?.checkEmpty(textPass) == true -> {
                view?.showError(DemoApp.context().resources.getString(R.string.alert_enter_pass))
            }
            model?.checkEmpty(textRePass) == true -> {
                view?.showError(DemoApp.context().resources.getString(R.string.alert_enter_re_pass))
            }
            model?.checkMatchPass(textPass, textRePass) == false -> {
                view?.showError(DemoApp.context().resources.getString(R.string.alert_enter_exactly_pass))
            }
            model?.checkExistUser(textUsername) == true -> {
                view?.showError(DemoApp.context().resources.getString(R.string.alert_user_exist))
            }
            else -> {
                view?.signUpSuccess(model?.signUp(textName, textUsername, textPass))
            }
        }


    }
}