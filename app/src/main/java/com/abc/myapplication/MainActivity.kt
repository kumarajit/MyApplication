package com.abc.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.abc.myapplication.Adapter.CanadaCustomAdapter
import com.abc.myapplication.Model.Canada
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val canada = ArrayList<Canada>()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //crating an arraylist to store users using the data class user


        getUsers()
        //now adding the adapter to recyclerview
       // recyclerView.adapter = adapter
//let, also, run, let int, apply,run,
    }
    fun getUsers() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json"

        // Request a string response from the provided URL.
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->

            var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)

                val jsonArray: JSONArray = jsonObj.getJSONArray("rows")


                for (i in 0..jsonArray.length()-1){
                    var tirle=""
                    var description=""
                    var imageHref=""
                    if(jsonArray.getJSONObject(i).getString("title") !=null &&
                        !jsonArray.getJSONObject(i).getString("title").equals("null"))
                    {
                         tirle=jsonArray.getJSONObject(i).getString("title")

                    }
                    if(jsonArray.getJSONObject(i).getString("description") !=null &&
                        !jsonArray.getJSONObject(i).getString("description").equals("null"))
                    {
                        description=jsonArray.getJSONObject(i).getString("description")

                    }
                    if(jsonArray.getJSONObject(i).getString("imageHref") !=null &&
                        !jsonArray.getJSONObject(i).getString("imageHref").equals("null")) {
                        imageHref = jsonArray.getJSONObject(i).getString("imageHref")

                    }

                    val can=Canada(tirle,description,imageHref)
                    canada.add(can)

                }
                val actionBar = supportActionBar
                actionBar!!.title = jsonObj.getString("title")

                val recyclerView = findViewById(R.id.list_recycler_view) as RecyclerView

                //adding a layoutmanager
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


                val adapter = CanadaCustomAdapter(canada)
                recyclerView.adapter = adapter

            },
            Response.ErrorListener {
            })
        queue.add(request)
    }
}
