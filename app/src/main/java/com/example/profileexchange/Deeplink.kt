package com.example.profileexchange

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Deeplink : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)
        supportActionBar?.title = "QRコード読込"

        //Log.v("URL",getIntent().dataString)
        test()
    }

    public override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        test()
    }

    fun test(){
        val intent =  Intent(this, ListActivity::class.java)
        intent.putExtra("Result",getIntent().dataString)
        startActivity(intent)
    }
}