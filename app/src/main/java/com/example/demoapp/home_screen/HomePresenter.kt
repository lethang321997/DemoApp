package com.example.demoapp.home_screen


class HomePresenter : HomeContract.Presenter() {
    private var view: HomeContract.View? = null
    val model: HomeModel? = HomeModel()

    fun setView(v: HomeContract.View) {
        view = v
    }

    override fun handleLogin(userName: String, password: String) {
        val user = model?.login(userName, password)
        if (user == null)
            view?.loginFail()
        else {
            if (user.isAdmin)
                view?.loginWithAdmin()
            else
                view?.loginSuccess(user)
        }
    }
}