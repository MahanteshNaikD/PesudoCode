package com.example.pesudoapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView

    private var newsAdapter: NewsAdapater = NewsAdapater()

    private var itemList: ArrayList<Artical>? = null

    private lateinit var oldToNew : TextView

    private lateinit var NewData : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerview)

        oldToNew = findViewById(R.id.old_to_new);

        NewData = findViewById(R.id.newData);

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = newsAdapter



        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // url for getting
                val url = URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")

                // connection for url
                val connection = url.openConnection() as HttpURLConnection

                // getting response form server
                val responseCode = connection.responseCode

                // checking the response for success if
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    // data from server
                    val inputStream = connection.inputStream
                    val gson = Gson()

                    // from json to actaul data by using model class
                    val responseData: Model =
                        gson.fromJson(inputStream.bufferedReader().use { it.readText() }, Model::class.java)

                    itemList = ArrayList(responseData.articles)

                    withContext(Dispatchers.Main) {
                        // adding each data to array list
                        for (key in itemList!!){
                             newsAdapter.updateData(key)
                        }
                    }
                } else {
                    println("Error code: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        oldToNew.setOnClickListener {
            //sorting data item from last data to new data
           newsAdapter.oldToNewNews()
        }

        NewData.setOnClickListener {
            // sorting data new to old data
            newsAdapter.newNews()
        }

    }



}


