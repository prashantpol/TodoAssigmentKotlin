package com.example.kotlintest


import retrofit2.Call
import retrofit2.http.GET

interface TodoApiInterface {

    @GET("todos/")
    fun LoadTodoList(): Call<ArrayList<TodoModel>>



}