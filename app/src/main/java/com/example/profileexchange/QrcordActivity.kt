package com.example.profileexchange

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_qrcord.*

class QrcordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcord)
        supportActionBar?.title = "QRコード"

        val name = intent.getStringExtra("Name")
        val git = intent.getStringExtra("Git")
        val twi = intent.getStringExtra("Twi")

        val url = Uri.Builder().scheme("ca-tech").authority("dojo").path("/share").appendQueryParameter("iam",name).appendQueryParameter("tw",twi).appendQueryParameter("gh",git)
//            "ca-tech://dojo/share?iam="+name+"&tw="+twi+"&gh="+git

        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(url.toString(), BarcodeFormat.QR_CODE, 400, 400)
            val imageQr = findViewById<ImageView>(R.id.qrCode)
            imageQr.setImageBitmap(bitmap);
        } catch (e: Exception) {

        }

        camera.setOnClickListener {
            IntentIntegrator(this).initiateScan();
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)//ActionBar戻る
    }

    //ActionBar戻る
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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