package com.example.utsandroid

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.list_layout.*
import org.json.JSONObject

class Dashboard : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tambah.setOnClickListener {
            val intent = Intent( this, Tambah::class.java)
            startActivity(intent)
            finish()
        }

        logout.setOnClickListener{
            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("STATUS","0")
            editor.apply()

            startActivity(Intent(this@Dashboard,MainActivity::class.java))
            finish()
        }

        val recyclerView = findViewById(R.id.recycle) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val users = ArrayList<User>()

        AndroidNetworking.get("http://192.168.0.14/berita/data_berita.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) { // do anything with response
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("judul)berita"))

                        var textViewJudul=jsonObject.optString("judul_berita").toString()
                        var textViewWaktu=jsonObject.optString("waktu_berita").toString()
                        var textViewPenulis=jsonObject.optString("penulis_berita").toString()
                        var textViewIsi=jsonObject.optString("isi_berita").toString()

                        users.add(User("$textViewJudul", "$textViewWaktu", "$textViewPenulis", "$textViewIsi"))

                    }

                    val adapter=CustomAdapter(users)
                    recycle.adapter=adapter
                }

                override fun onError(anError: ANError) { // handle error
                    Log.i("_err", anError.toString())
                }
            })


    }
}

