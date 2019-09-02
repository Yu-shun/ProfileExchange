package com.example.profileexchange

import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "プロフィール編集"

        val dataStore = getSharedPreferences("DataStore", Context.MODE_PRIVATE)

        var readName = dataStore.getString("InputN","")
        var readGit = dataStore.getString("InputG","")
        var readTwi = dataStore.getString("InputT","")
        editName.setText(readName)
        editGit.setText(readGit)
        editTwi.setText(readTwi)

        editGit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length > textInputLayout.counterMaxLength){
                    textInputLayout2.error = "20文字以内で入力してください"
                }else{
                    textInputLayout2.error = null
                }
            }
        })

        editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > textInputLayout.counterMaxLength) {
                    textInputLayout.error = "名前は20文字以内で入力してください"
                }else{
                    textInputLayout.error = null
                }
            }
        })

        editTwi.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > textInputLayout3.counterMaxLength) {
                    textInputLayout3.error = "20文字以内で入力してください"
                }else{
                    textInputLayout3.error = null
                }
            }
        })

        buttonQr.setOnClickListener{
            if (editGit.text.toString() == ""){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("入力ミスがあります")
                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                    .setPositiveButton("閉じる") { dialog, id -> }
                builder.show()
                textInputLayout2.error = "GitHubアカウント名を入力してください"
                //editGit.error = "GitHubアカウント名は必須です"
            } else if (textInputLayout.error != null || textInputLayout2.error != null || textInputLayout3.error != null){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("入力ミスがあります")
                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                    .setPositiveButton("閉じる") { dialog, id -> }
                builder.show()
            } else{
                val intent =  Intent(this, QrcordActivity::class.java)
                intent.putExtra("Name",editName.text.toString())
                intent.putExtra("Git",editGit.text.toString())
                intent.putExtra("Twi",editTwi.text.toString())
                startActivity(intent)
            }
        }

        butSave.setOnClickListener {
            val editor = dataStore.edit()
            editor.putString("InputN", editName.text.toString())
            editor.putString("InputG", editGit.text.toString())
            editor.putString("InputT", editTwi.text.toString())
            editor.apply()
            Toast.makeText(this, "保存しました", Toast.LENGTH_LONG).show()
        }
    }

    fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.item_pro -> {
                false
            }
            R.id.item_cam -> {
                IntentIntegrator(this).initiateScan();
            }
            R.id.item_list -> {
                val intent =  Intent(this, com.example.profileexchange.ListActivity::class.java)
                intent.putExtra("ID",1)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_SHORT).show()//第3引数は表示時間

                val intent =  Intent(this, ListActivity::class.java)
                intent.putExtra("Result",result.contents)
                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
