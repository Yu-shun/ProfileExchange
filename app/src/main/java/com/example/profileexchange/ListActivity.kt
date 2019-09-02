package com.example.profileexchange

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_list.*
import kotlin.concurrent.thread

class ListActivity : AppCompatActivity() {
    var db:AppDatabase? = null
    var user = User()

    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.title = "友達リスト"
        var selectId = 0

        val id = intent.getIntExtra("ID",0)
        val userId = intent.getIntExtra("NameID",-1)
        //Log.v("dball",id.toString())

        db = AppDatabase.get(this)

        val userList = arrayOf("Name", "Twitter", "GitHub")

        text_select.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("並び替え")
                .setIcon(R.drawable.ic_swap_vert_black_24dp)
                .setSingleChoiceItems(userList, selectId) { dialog, which ->
                    selectId = which
                }
                .setPositiveButton("決定"){dialog, which ->
                    text_select.setText(userList[selectId])
                }
            builder.show()
        }

        b_selector.setOnClickListener {
            b_selector.setSelected(!b_selector.isSelected())
            thread {
                if(selectId == 0){
                    if (b_selector.isSelected == true) {
                        var database = db?.userDao()?.namedownAll().orEmpty()
                        listView(database)
                    }else {
                        var database = db?.userDao()?.nametopAll().orEmpty()
                        listView(database)
                    }
                }else if(selectId == 1){
                    if (b_selector.isSelected == true) {
                        var database = db?.userDao()?.twidownAll().orEmpty()
                        listView(database)
                    }else {
                        var database = db?.userDao()?.twitopAll().orEmpty()
                        listView(database)
                    }
                }else{
                    if (b_selector.isSelected == true) {
                        var database = db?.userDao()?.gitdownAll().orEmpty()
                        listView(database)
                    }else {
                        var database = db?.userDao()?.gittopAll().orEmpty()
                        listView(database)
                    }
                }
            }
        }

        thread {
            if (id == 1) {
                var database = db?.userDao()?.getAll().orEmpty()
                listView(database)
            } else if (userId != -1 && id == 0) {
                db?.userDao()?.deleteSelect(userId)
                var database = db?.userDao()?.nametopAll().orEmpty()
                listView(database)
//                if (selectId == 0){
//                    if (b_selector.isSelected == true) {
//                        var database = db?.userDao()?.namedownAll().orEmpty()
//                        listView(database)
//                    }else {
//                        var database = db?.userDao()?.nametopAll().orEmpty()
//                        listView(database)
//                    }
//                }else if (selectId == 1){
//                    if (b_selector.isSelected == true) {
//                        var database = db?.userDao()?.twidownAll().orEmpty()
//                        listView(database)
//                    }else {
//                        var database = db?.userDao()?.twitopAll().orEmpty()
//                        listView(database)
//                    }
//                }else{
//                    if (b_selector.isSelected == true) {
//                        var database = db?.userDao()?.gitdownAll().orEmpty()
//                        listView(database)
//                    }else {
//                        var database = db?.userDao()?.gittopAll().orEmpty()
//                        listView(database)
//                    }
//                }
            } else {
                val result = Uri.parse(intent.getStringExtra("Result"))
                user.nameData = result.getQueryParameter("iam")
                user.gitData = result.getQueryParameter("gh")
                user.twiData = result.getQueryParameter("tw")

                //db?.userDao()?.deleteAll()
                db?.userDao()?.deleteUser(user.gitData.toString())
                db?.userDao()?.insertAll(user)
                var database = db?.userDao()?.getAll().orEmpty()
                listView(database)
            }
        }
    }

    fun listView(data: List<User>){
        Handler(Looper.getMainLooper()).post {
            viewManager = LinearLayoutManager(this)
            viewAdapter = ViewAdapter(data,this)

            recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        menuInflater.inflate(R.menu.search,menu)
        val searchItem = menu!!.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setSubmitButtonEnabled(true)
        searchView.setQueryHint("Search")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                getSearchDb(newText)
                //Log.v("Search",newText)
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                getSearchDb(query)
                //Log.v("Submit",query)
                hideKeyboard()
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun getSearchDb(searchText: String){
        thread{
            val search_text = "%$searchText%"
            var database = db?.userDao()?.searchAllId(search_text).orEmpty()
            listView(database)
        }
    }

    fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.item_pro -> {
                val intent =  Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.item_cam -> {
                IntentIntegrator(this).initiateScan();
            }
            R.id.item_list -> {
                false
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
