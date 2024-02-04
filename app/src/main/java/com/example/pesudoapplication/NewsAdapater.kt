package com.example.pesudoapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapater : RecyclerView.Adapter<MyViewHolder>() {
    private var itemList = ArrayList<Artical>()

    private var onExpandClickListener: OnExpandClickListener? = null
//
    interface OnExpandClickListener {
        fun onExpandClick(position: Int)
    }
//
//    fun setOnExpandClickListener(onExpandClickListener: OnExpandClickListener) {
//        this.onExpandClickListener = onExpandClickListener
//    }
//
//    fun  getItem(position: Int):Artical{
//        return itemList[position]
//    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = itemList[position]

        holder.itemTitleTextView.text = item.title

        holder.itemDesr.text = item.description
    }


     @SuppressLint("NotifyDataSetChanged")
     fun oldToNewNews(){

        val oldToNewList = ArrayList<Artical>();

        for (i in itemList.lastIndex downTo 0) {
            oldToNewList.add(itemList[i])
        }
        itemList = oldToNewList;

         notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun  newNews(){

        val oldToNewList = ArrayList<Artical>();

        for (i in itemList.lastIndex downTo 0) {

            oldToNewList.add(itemList[i])

        }

        itemList = oldToNewList;

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return MyViewHolder(itemView, onExpandClickListener)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateData(itemList1: Artical){
        itemList.add(itemList1)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
       return  itemList.size
    }

}

@SuppressLint("ClickableViewAccessibility")
class MyViewHolder(itemView: View, onExpandClickListener: NewsAdapater.OnExpandClickListener?) : RecyclerView.ViewHolder(itemView){
    val itemTitleTextView: TextView = itemView.findViewById(R.id.oss_title_textview)
    val itemDesr: TextView = itemView.findViewById(R.id.oss_description_textview)
    val imgageView : ImageView = itemView.findViewById(R.id.icon_click);
    var boolValue = false



   init {

       itemTitleTextView.setOnClickListener {
           val position =adapterPosition

           if(position!=RecyclerView.NO_POSITION){
                 onExpandClickListener?.onExpandClick(position)
           }
       }

       imgageView.setOnClickListener {
           println("item cilcik o icon")
           boolValue= true
           if(boolValue){
               itemDesr.visibility = View.VISIBLE
               boolValue = !boolValue
           }
       }

       itemTitleTextView.setOnClickListener {
           itemDesr.visibility = View.GONE

       }

   }



}