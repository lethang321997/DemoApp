package com.example.demoapp.model


data class User(
    var id: Int = 0,
    var name: String = "",
    var username: String = "",
    var password: String = "",
    var money: Int = 0,
    var isAdmin: Boolean = false

) {
    override fun toString(): String {
        return "$id - $username - $name - $money"
    }
}
