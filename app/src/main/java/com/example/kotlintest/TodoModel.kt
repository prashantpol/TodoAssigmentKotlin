package com.example.kotlintest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TodoModel
{
    var userId: Int=0
    var id: Int=0
    var completed: Boolean=false
    var title : String="";
    var isExpanded: Boolean=false

}
