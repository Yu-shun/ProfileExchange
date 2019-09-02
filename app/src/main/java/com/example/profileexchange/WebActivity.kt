package com.example.profileexchange

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        supportActionBar?.title = "ブラウザ"

        val url_data = intent.getStringExtra("Result")

        web.webViewClient = WebViewClient()
        web.loadUrl(url_data)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)//ActionBar戻る
    }

    //ActionBar戻る
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
