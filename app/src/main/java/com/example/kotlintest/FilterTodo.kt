package com.example.kotlintest

import android.widget.Filter


public class FilterTodo(var todoModelList : ArrayList<TodoModel>?,var adapter : TodoAdapter) :  Filter(){


    override fun performFiltering(p0: CharSequence?): FilterResults {
        var result : FilterResults = FilterResults()
        if(todoModelList!!.size>0)
        {
            if(p0!=null && p0.length >0)
            {
                var text =p0;
                text=p0.toString().toUpperCase()
                var newFilteredData : ArrayList<TodoModel> = ArrayList()
                for(i in 0 until todoModelList!!.size)
                {
                    text = text.toString().replace(" ", "")
                    if(todoModelList!![i].title.replace(" ", "").toUpperCase().contains(text))
                    {
                        newFilteredData.add(todoModelList!!.get(i));
                    }


                    result.values=newFilteredData

                }


            }
            else
            {
                result.count=todoModelList!!.size
                result.values=todoModelList
            }
        }
        return  result

    }

    override fun publishResults(p0: CharSequence, p1: FilterResults?) {


            adapter!!.mTodoModels= p1?.values as ArrayList<TodoModel>

        adapter!!.notifyDataSetChanged()


    }
}