package com.example.kotlintest

class TodoPagination(var todoListModels : ArrayList<TodoModel>)  {
    var newList : ArrayList<TodoModel> = ArrayList()

    var itemsPerpage : Int = 5
    var totalitems : Int = todoListModels.size
    var totalPages : Int = totalitems / itemsPerpage
    var itemsRemaining : Int = totalPages % totalitems
    var lastpageno : Int = totalitems/itemsPerpage

    public fun GetTodoList(currentpage : Int) : ArrayList<TodoModel>
    {
        newList.clear()
        var startItem: Int = currentpage * itemsPerpage;
        if (currentpage == lastpageno && itemsRemaining > 0) {
            for (i in startItem until startItem + itemsRemaining) {
                newList.add(todoListModels[i])
            }

        }
        else
        {
            for (i in startItem until startItem + itemsPerpage) {
                newList.add(todoListModels[i])
            }
        }
        return  newList
    }



}