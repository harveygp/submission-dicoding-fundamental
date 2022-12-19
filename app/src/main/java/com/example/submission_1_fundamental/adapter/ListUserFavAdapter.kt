package com.example.submission_1_fundamental.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_1_fundamental.data.local.entity.UserFavEntity
import com.example.submission_1_fundamental.data.remote.response.UserItem
import com.example.submission_1_fundamental.databinding.ItemRowUsersBinding

class ListUserFavAdapter(private val listResponseUser: List<UserFavEntity>) : ListAdapter<UserFavEntity, ListUserFavAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding : ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        listResponseUser[position].let {
            Glide.with(holder.itemView.context)
                .load(it.avatarUrl)
                .circleCrop()
                .into(holder.binding.imgItemPhoto)
            holder.binding.tvItemName.text = it.login
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listResponseUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listResponseUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserFavEntity)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserFavEntity> =
            object : DiffUtil.ItemCallback<UserFavEntity>() {
                override fun areItemsTheSame(oldUser: UserFavEntity, newUser: UserFavEntity): Boolean {
                    return oldUser.login == newUser.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: UserFavEntity, newUser: UserFavEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}