package com.example.submission_1_fundamental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_1_fundamental.databinding.ItemRowUsersBinding
import com.example.submission_1_fundamental.data.remote.response.UserFollow

class ListUserFollowAdapter(private val listUserFollow: List<UserFollow>) : RecyclerView.Adapter<ListUserFollowAdapter.ListViewHolder>() {

    class ListViewHolder(var binding : ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (login, avatarUrl, _) = listUserFollow[position]
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = login
    }

    override fun getItemCount(): Int = listUserFollow.size
}