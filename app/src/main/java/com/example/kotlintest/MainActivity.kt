package com.example.kotlintest

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var todoModelList: ArrayList<TodoModel> = ArrayList();
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: TodoAdapter
    lateinit var btnNext : Button
    lateinit var btnPrevious : Button
    lateinit var radioGroup : RadioGroup
    lateinit var radAll :RadioButton
    lateinit var radComplete :RadioButton
    lateinit var radIncomplete :RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        LoadData();
        recyclerView = findViewById(R.id.recyclerview_todo)


        btnNext=findViewById(R.id.btnnext);
        btnNext.setOnClickListener{
            //Toast.makeText(this,"TEST",Toast.LENGTH_LONG).show();

        }
        btnPrevious=findViewById(R.id.btnprevious);
        btnPrevious.setOnClickListener{

        }

        radioGroup=findViewById(R.id.radiogrp);
        radioGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
                    Toast.makeText(applicationContext," On checked change : ${radio.text}",
                            Toast.LENGTH_SHORT).show()
                    if(radio.text.equals("All"))
                    {
                        SetAdapter(todoModelList,0)

                    }
                    else if(radio.text.equals("Complete"))
                    {
                        SetAdapter(todoModelList,1)
                    }
                    else if(radio.text.equals("Incomplete"))
                    {
                        SetAdapter(todoModelList,2)
                    }
                })

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
                SetAdapter(response.body(),0)

            }

            override fun onFailure(call: Call<ArrayList<TodoModel>>?, t: Throwable?) {
            }
        })

    }
    fun SetAdapter(todos : ArrayList<TodoModel>,radioSelected : Int)
    {
        var filteredList :ArrayList<TodoModel> = ArrayList();
        filteredList.clear();
        if(radioSelected==0)
        {
            filteredList=todos;
        }
        else if(radioSelected==1)
        {
            for (i in 0..todos.size - 1) {
                if (todos[i].completed === true) {
                    filteredList.add(todos[i])
                }
            }
        }
        else if(radioSelected==2)
        {
            for (i in 0..todos.size - 1) {
                if (todos[i].completed === false) {
                    filteredList.add(todos[i])
                }
            }
        }
        recyclerAdapter = TodoAdapter(filteredList,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

    }

}

