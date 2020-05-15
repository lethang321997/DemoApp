package com.example.demoapp.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.demoapp.common.DemoApp
import com.example.demoapp.R
import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.model.User
import com.example.demoapp.model.UserMoney
import kotlinx.android.synthetic.main.dialog_edit.*


class CustomAdapter(var context: Context, private var list: ArrayList<UserMoney>) : BaseAdapter() {
    class ViewHolder(v: View) {
        val textList: TextView = v.findViewById(R.id.textList)
        val buttonList: Button = v.findViewById(R.id.buttonList)
        val image: ImageView = v.findViewById(R.id.imageView)
    }

    private val db = DataHandler(DemoApp.context())
    private var newMoney: Int = 0
    private val txtView =
        (context as Activity).findViewById<View>(R.id.textMoneyHistory) as TextView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.list_view, null)
            viewHolder =
                ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }
        val userMoney: UserMoney = getItem(position) as UserMoney
        viewHolder.textList.text = userMoney.toString()
        viewHolder.buttonList.setOnClickListener {
            displayDialogEdit(position)
        }
        viewHolder.image.setOnClickListener {
            displayDialogDelete(position)

        }
        return view as View
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }


    private fun displayDialogEdit(position: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
        val dialogAdd = builder.show()
        val userMoney: UserMoney = getItem(position) as UserMoney
        val user: User? = db.getUser(userMoney.userID)
        dialogAdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogAdd.editNote.setText(userMoney.note)
        dialogAdd.editMoney.setText(userMoney.moneyAdd.toString())
        dialogAdd.buttonEditMoney.setOnClickListener {
            val moneyEdit = dialogAdd.editMoney.text.toString()
            val note = dialogAdd.editNote.text.toString()
            when {
                note.isEmpty() -> {
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.alert_enter_note),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                moneyEdit.isEmpty() -> {
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.alert_enter_money),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    newMoney = (user?.money?.minus(userMoney.moneyAdd) ?: 0) + moneyEdit.toInt()
                    list[position].moneyAdd = moneyEdit.toInt()
                    list[position].note = dialogAdd.editNote.text.toString()
                    if (user != null) {
                        db.editMoney(
                            user.id,
                            userMoney.id,
                            note,
                            newMoney,
                            moneyEdit.toInt()
                        )
                    }
                    notifyDataSetChanged()
                    txtView.text = String.format("%,d",newMoney)
                    dialogAdd.dismiss()
                }
            }
        }
    }

    private fun displayDialogDelete(position: Int) {
        val builder = AlertDialog.Builder(context)
            .setTitle(context.resources.getString(R.string.title_confirm))
            .setMessage(context.resources.getString(R.string.text_delete_confirm))
            .setPositiveButton(context.resources.getString(R.string.text_yes)) { _, _ ->
                val userMoney: UserMoney = getItem(position) as UserMoney
                val user: User? = db.getUser(userMoney.userID)
                if (user != null) {
                    newMoney = user.money - userMoney.moneyAdd
                    db.deleteMoney(userMoney.id, user.id, newMoney)
                }
                txtView.text = String.format("%,d",newMoney)
                list.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.alert_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(context.resources.getString(R.string.text_no)) { _, _ ->

            }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}