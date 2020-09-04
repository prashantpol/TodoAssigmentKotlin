package com.example.kotlintest

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var todoModelList: ArrayList<TodoModel>? = ArrayList();
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: TodoAdapter
    lateinit var btnNext : Button
    lateinit var btnPrevious : Button
    lateinit var radioGroup : RadioGroup
    lateinit var radAll :RadioButton
    lateinit var radComplete :RadioButton
    lateinit var radIncomplete :RadioButton
    lateinit var searchView : SearchView
    lateinit var  pagination :  TodoPagination
    var currentpage : Int =0
    var totpages : Int=0
    var radiofilteredList :ArrayList<TodoModel> = ArrayList();
    var paginatedList : ArrayList<TodoModel> = ArrayList();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        LoadData();

        recyclerView = findViewById(R.id.recyclerview_todo)


        btnNext=findViewById(R.id.btnnext);
        btnNext.setOnClickListener{
            //Toast.makeText(this,"TEST",Toast.LENGTH_LONG).show();
            currentpage=currentpage+1
             SetAdapter(currentpage)
            ButtonEnableDisable()
        }
        btnPrevious=findViewById(R.id.btnprevious);
        btnPrevious.setOnClickListener{
            currentpage=currentpage-1
            SetAdapter(currentpage)
            ButtonEnableDisable()
        }

        radioGroup=findViewById(R.id.radiogrp);
        radioGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
//                    Toast.makeText(applicationContext," On checked change : ${radio.text}",
//                            Toast.LENGTH_SHORT).show()
                    if(radio.text.equals("All"))
                    {
                        FilterRadio(todoModelList,0)

                    }
                    else if(radio.text.equals("Complete"))
                    {
                        FilterRadio(todoModelList,1)
                    }
                    else if(radio.text.equals("Incomplete"))
                    {
                        FilterRadio(todoModelList,2)
                    }
                })

        searchView=findViewById(R.id.txtsearch);

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (recyclerAdapter != null) {
                    recyclerAdapter.getFilter().filter(newText)
                }
                return false
            }
        })

        ButtonEnableDisable()
     }
    fun ButtonEnableDisable()
    {
        if(currentpage==0)
        {
            btnPrevious.isEnabled=false;
            btnNext.isEnabled=true;
        }
        else if(currentpage==totpages)
        {
            btnPrevious.isEnabled=true;
            btnNext.isEnabled=false;
        }
        else if(currentpage>=1 && currentpage<=totpages)
        {
            btnPrevious.isEnabled=true;
            btnNext.isEnabled=true;
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
                    for (i in 0..todoModelList!!.size-1)
                    {
                        todoModelList!![i].isExpanded=false;
                    }
                }
                FilterRadio(response.body(),0)

            }

            override fun onFailure(call: Call<ArrayList<TodoModel>>?, t: Throwable?) {
            }
        })

    }
    fun FilterRadio(todos : ArrayList<TodoModel>?,radioSelected : Int)
    {

        radiofilteredList.clear();
        if(radioSelected==0)
        {
            radiofilteredList=todos!!;
        }
        else if(radioSelected==1)
        {
            for (i in 0..todos!!.size - 1) {
                if (todos[i]!!.completed === true) {
                    radiofilteredList.add(todos[i])
                }
            }
        }
        else if(radioSelected==2)
        {
            for (i in 0..todos!!.size - 1) {
                if (todos[i].completed === false) {
                    radiofilteredList.add(todos[i])
                }
            }
        }
        if(radiofilteredList.size>0) {
            pagination = TodoPagination(radiofilteredList)
            SetAdapter(currentpage)
        }
    }

    fun SetAdapter(pageno : Int)
    {
        paginatedList=pagination.GetTodoList(pageno);
        recyclerAdapter = TodoAdapter(paginatedList,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
        totpages=pagination.totalPages-1;
    }

}

