package com.example.profileexchange

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class ViewAdapter (val list: List<User>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    //var RecyclerView : RecyclerView? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.my_item, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = list[position].nameData
        holder.gitView.text = list[position].gitData
        holder.twiView.text = list[position].twiData

        holder.twi_button.setOnClickListener {
            //Toast.makeText(context, "Twitterタップ", Toast.LENGTH_SHORT).show()
            val twi_url = "https://twitter.com/"+list[position].twiData
            val intent =  Intent(context, WebActivity::class.java)
            intent.putExtra("Result",twi_url)
            context.startActivity(intent)
        }

        holder.twiView.setOnClickListener {
            val twi_url = "https://twitter.com/"+list[position].twiData
            val intent =  Intent(context, WebActivity::class.java)
            intent.putExtra("Result",twi_url)
            context.startActivity(intent)
        }

        holder.git_button.setOnClickListener {
            //Toast.makeText(context, "Gitタップ", Toast.LENGTH_SHORT).show()
            val git_url = "https://github.com/"+list[position].gitData
            val intent =  Intent(context, WebActivity::class.java)
            intent.putExtra("Result",git_url)
            context.startActivity(intent)
        }

        holder.gitView.setOnClickListener {
            //Toast.makeText(context, "Gitタップ", Toast.LENGTH_SHORT).show()
            val git_url = "https://github.com/"+list[position].gitData
            val intent =  Intent(context, WebActivity::class.java)
            intent.putExtra("Result",git_url)
            context.startActivity(intent)
        }

        holder.del_button.setOnClickListener {
            val username = list[position].nameData
            AlertDialog.Builder(context).apply {
                setTitle("$username さん")
                setMessage("を本当に削除しますか？")
                setPositiveButton("OK", DialogInterface.OnClickListener{ _, _ ->
                    val intent =  Intent(context, ListActivity::class.java)
                    intent.putExtra("NameID",list[position].uid)
                    context.startActivity(intent)
                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show()
                })
                setNegativeButton("Cancel",null)
                show()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}