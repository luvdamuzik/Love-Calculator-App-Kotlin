package com.example.android.navigation.User

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.navigation.data.RandomUsers.models.User
import com.example.android.navigation.databinding.GridItemBinding

class UserAdapter(private val listener: OnItemClickListener): ListAdapter<User, UserAdapter.UserViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class UserViewHolder(private val binding: GridItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val user = getItem(position)
                        listener.onItemClick(user)
                    }
                }
            }
        }

        fun bind(user: User){
            binding.apply {
                Glide.with(itemView)
                    .load(user.results?.get(0)?.picture?.large)
                    .into(slika)
                ime.text = user.results?.get(0)?.name?.first + " " + user.results?.get(0)?.name?.last
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(user: User)
    }

    class DiffCallback : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.results?.get(0)?.name?.first == newItem.results?.get(0)?.name?.first && oldItem.results?.get(0)?.name?.last == newItem.results?.get(0)?.name?.last
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}