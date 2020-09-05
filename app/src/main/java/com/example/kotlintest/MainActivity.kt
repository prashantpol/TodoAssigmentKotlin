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

    var todoModelList: ArrayList<TodoModel> = ArrayList();
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: TodoAdapter
    lateinit var btnNext : Button
    lateinit var btnPrevious : Button
    lateinit var radioGroup : RadioGroup
    lateinit var  pagination :  TodoPagination
    var currentpage : Int =0
    var totpages : Int=0
    lateinit var searchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        LoadData();

        recyclerView = findViewById(R.id.recyclerview_todo)


        btnNext = findViewById(R.id.btnnext);
        btnNext.setOnClickListener {
            //Toast.makeText(this,"TEST",Toast.LENGTH_LONG).show();
            currentpage = currentpage + 1
            SetAdapter(currentpage)
            ButtonEnableDisable()
        }
        btnPrevious = findViewById(R.id.btnprevious);
        btnPrevious.setOnClickListener {
            currentpage = currentpage - 1
            SetAdapter(currentpage)
            ButtonEnableDisable()
        }

        radioGroup = findViewById(R.id.radiogrp);
        radioGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
//                    Toast.makeText(applicationContext," On checked change : ${radio.text}",
//                            Toast.LENGTH_SHORT).show()
                    if (radio.text.equals("All")) {
                        FilterRadio(todoModelList, 0)

                    } else if (radio.text.equals("Complete")) {
                        FilterRadio(todoModelList, 1)
                    } else if (radio.text.equals("Incomplete")) {
                        FilterRadio(todoModelList, 2)
                    }
                })

        searchView=findViewById(R.id.txtsearch)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
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
                    for (i in 0..todoModelList.size-1)
                    {
                        todoModelList[i].isExpanded=false;
                    }
                }
                FilterRadio(response.body(),0)

            }

            override fun onFailure(call: Call<ArrayList<TodoModel>>?, t: Throwable?) {
            }
        })

    }
    fun FilterRadio(todos : ArrayList<TodoModel>,radioSelected : Int)
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
        if(filteredList.size>0) {
            pagination = TodoPagination(filteredList)
            SetAdapter(currentpage)
        }
    }
    fun SetAdapter(pageno : Int)
    {

        recyclerAdapter = TodoAdapter(pagination.GetTodoList(pageno),this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
        totpages=pagination.totalPages-1;
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<TodoModel> = ArrayList()

        if(!text.equals("")) {
            //looping through existing elements
            var templist :  ArrayList<TodoModel> = ArrayList()
            templist.addAll(pagination.GetTodoList(currentpage))
            for (s in templist) {
                //if the existing elements contains the search input
                if (s.title.replace(" ","").contains(text.replace(" ","").toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s)
                }
            }
            //calling a method of the adapter class and passing the filtered list
            recyclerAdapter = TodoAdapter(filterdNames, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = recyclerAdapter
        }
        else
        {
             SetAdapter(currentpage)
        }
    }

}

