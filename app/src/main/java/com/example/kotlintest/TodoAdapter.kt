package com.example.kotlintest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val mTodoModels :List<TodoModel>,private val context : Context) :RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    class TodoViewHolder(containerView: View): RecyclerView.ViewHolder(containerView) {

            var Title : TextView=containerView.findViewById(R.id.txt_title)
            var Fulltitle : TextView =containerView.findViewById(R.id.txtdetails)
            var cns_Layout:ConstraintLayout =containerView.findViewById(R.id.cns_layout)
            var cns_Expandeddetail:ConstraintLayout=containerView.findViewById(R.id.cns_detailsExpanded)
            var img_arrow : ImageView=containerView.findViewById(R.id.img_arrow)
            var img_complete : ImageView=containerView.findViewById(R.id.img_complete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.customtodorow,parent,false)
        return TodoViewHolder(view);
    }

    override fun getItemCount(): Int {
        return mTodoModels.size;
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var data=mTodoModels.get(position);


       if(data!=null)
       {
           holder.Fulltitle.setText(data.title);

           var isExpanded:Boolean=data.isExpanded!!;
           if(isExpanded==true) {
               holder.cns_Expandeddetail.visibility = View.VISIBLE;
           }
           else {
               holder.cns_Expandeddetail.visibility = View.GONE;


           }
           if (data.title.length > 20)
               holder.Title.setText(data.title.substring(0, 20) + "...")
           else
               holder.Title.setText(data.title);

           holder.cns_Layout.setOnClickListener{
                data.isExpanded=!data.isExpanded;
                notifyItemChanged(position)

                if(data.isExpanded==true)
                {
                    holder.img_arrow.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)

                }
               else
                {
                    holder.img_arrow.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)

                }

           }
           if (data.completed === true) {
               holder.img_complete.setImageResource(R.drawable.correct2)
           } else {
               holder.img_complete.setImageResource(0)
           }

       }




    }


}


