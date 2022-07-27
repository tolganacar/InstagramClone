package com.tolganacar.kotlininstaclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tolganacar.kotlininstaclone.databinding.RecyclerRowBinding
import com.tolganacar.kotlininstaclone.model.Post

class PostAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostHolder>() {

    class PostHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerViewEmail.text = postList.get(position).email
        holder.binding.recyclerViewComment.text = postList.get(position).comment
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerViewImageView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}