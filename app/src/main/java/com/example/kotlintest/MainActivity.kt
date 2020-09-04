package com.example.kotlintest

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var todoModelList: ArrayList<TodoModel> = ArrayList();
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: TodoAdapter
    lateinit var btnNext : Button;
    lateinit var btnPrevious : Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        LoadData();
        recyclerView = findViewById(R.id.recyclerview_todo)
        recyclerAdapter = TodoAdapter(todoModelList!!,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        btnNext=findViewById(R.id.btnnext);
        btnNext.setOnClickListener{
            //Toast.makeText(this,"TEST",Toast.LENGTH_LONG).show();

        }

        btnPrevious=findViewById(R.id.btnprevious);
        btnPrevious.setOnClickListener{

        }
    }

    fun LoadData() {
        /*Create handle for the RetrofitInstance interface*/
        var apiInterface: TodoApiInterface = RetrofitTodoServices.getApiClient()!!.create(TodoApiInterface::class.java)
        apiInterface.LoadTodoList().enqueue(object : Callback<ArrayList<TodoModel>> {
            override fun onResponse(call: Call<ArrayList<TodoModel>>?, response: Response<ArrayList<TodoModel>>?) {
                todoModelList = response?.body()!!

                if(todoModelList!=null)
                {
                    for (i in 0..todoModelList.size-1)
                    {
                        todoModelList[i].isExpanded=false;
                    }
                }
                recyclerView.adapter = TodoAdapter(response?.body()!!, this@MainActivity)
            }

            override fun onFailure(call: Call<ArrayList<TodoModel>>?, t: Throwable?) {
            }
        })

    }

}

