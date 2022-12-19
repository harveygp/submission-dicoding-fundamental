package com.example.submission_1_fundamental.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.submission_1_fundamental.databinding.ItemRowUsersBinding
import com.example.submission_1_fundamental.data.remote.response.UserItem

class ListUserAdapter(private val listResponseUser: List<UserItem>) : ListAdapter<UserItem, ListUserAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding : ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatarUrl, _, login) = listResponseUser[position]
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = login
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listResponseUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listResponseUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserItem> =
            object : DiffUtil.ItemCallback<UserItem>() {
                override fun areItemsTheSame(oldUser: UserItem, newUser: UserItem): Boolean {
                    return oldUser.login == newUser.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: UserItem, newUser: UserItem): Boolean {
                    return oldUser == newUser
                }
            }
    }
}