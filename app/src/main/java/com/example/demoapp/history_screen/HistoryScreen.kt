package com.example.demoapp.history_screen


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.R
import com.example.demoapp.common.Constant
import com.example.demoapp.adapter.CustomAdapter
import com.example.demoapp.dataBaseHandler.DataHandler
import kotlinx.android.synthetic.main.activity_history_screen.*


class HistoryScreen : AppCompatActivity() {
    var id: Int = 0
    var money: Int = 0
    private lateinit var dataHandler: DataHandler
    private lateinit var customAdapter: CustomAdapter
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_screen)
        initData()
        initView()
        intent.putExtra(Constant.KEY_ID, id)
        setResult(RESULT_OK, intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        showAllMoney()
        textMoneyHistory.text = String.format("%,d", money)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initData() {
        id = intent.getIntExtra(Constant.KEY_ID, -1)
        money = intent.getIntExtra(Constant.KEY_MONEY, -1)
    }

    private fun showAllMoney() {
        dataHandler = DataHandler(this)
        val moneyList = dataHandler.readAllUserMoney(id)
        customAdapter = CustomAdapter(
            this@HistoryScreen,
            moneyList
        )
        listViewMoney.adapter = customAdapter
        listViewMoney.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                view.isSelected = true
            }
    }

}
