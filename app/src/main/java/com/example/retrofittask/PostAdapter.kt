package com.example.roomdatabasetask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofittask.PostModel
import com.example.retrofittask.UserResponse
import com.example.retrofittask.databinding.CardItemStdBinding
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext


class PostAdapter:RecyclerView.Adapter<PostAdapter.UserViewHolder>() {
    private var stdList:ArrayList<PostModel> = ArrayList()
    private var onClickItem:((PostModel)-> Unit)?=null
    private var onClickDeleteItem:((PostModel)-> Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // val view= LayoutInflater.from(parent.context).inflate(R.layout.card_items_std,parent,false)
        val view=CardItemStdBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(view)
    }

//    fun setOnClickItem(callback:(PostModel)->Unit){
//        this.onClickItem=callback
//    }
//    fun setOnClickDeleteItem(callback: (PostModel) -> Unit){
//        this.onClickDeleteItem=callback
//    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val std=stdList[position]
        holder.binding.txtid.text=std.id.toString()
        holder.binding.txtfname.text=std.title
        holder.binding.txtlname.text=std.body

        //holder.bindView(std)

        //holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        //holder.binding.btndelete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }
    fun setData(data:List<PostModel>){
        stdList.apply {
            clear()
            addAll(data)
        }
    }


    class UserViewHolder(var binding:CardItemStdBinding): RecyclerView.ViewHolder(binding.root){

    }
}