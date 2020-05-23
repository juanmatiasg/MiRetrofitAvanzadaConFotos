package com.example.retrofitavanzadaconfotos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var mRecyclerView: RecyclerView
    var mAdapter: PhotosAdapter = PhotosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView.setOnQueryTextListener(this)
        setUpRecyclerView()
        getLista()

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun getLista() {
        var service = getRetrofit().create(PhotoApi::class.java)
        service.getAll().enqueue(object : Callback<MutableList<Photos>> {
            override fun onFailure(call: Call<MutableList<Photos>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No hay conexion", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MutableList<Photos>>,
                response: Response<MutableList<Photos>>
            ) {
                if (response.isSuccessful) {
                    var objeto = response.body() as MutableList
                    mAdapter.PhotosAdapter(objeto, this@MainActivity)
                    mRecyclerView.adapter = mAdapter

                }
            }

        })

    }


    fun searchById(query: String) {
        var service = getRetrofit().create(PhotoApi::class.java)
        service.getByID(query).enqueue(object : Callback<Photos> {
            override fun onFailure(call: Call<Photos>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No hay conexion", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Photos>, response: Response<Photos>) {
                if (response.isSuccessful) {
                    var objetoDos = response.body() as Photos
                    var listaDos: MutableList<Photos> =
                        mutableListOf(
                            Photos(
                                albumId = objetoDos.albumId,
                                id = objetoDos.id,
                                title = objetoDos.title,
                                url = objetoDos.url,
                                thumbnailUrl = objetoDos.thumbnailUrl
                            )
                        )
                    mAdapter.PhotosAdapter(listaDos,this@MainActivity)
                    mRecyclerView.adapter=mAdapter
                }

            }

        })
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchById(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        getLista()
        return true
    }


}
