package com.example.android.navigation.Love

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.User.UserAdapter
import com.example.android.navigation.data.LoveCalculator.Love
import com.example.android.navigation.data.RandomUsers.models.User
import com.example.android.navigation.databinding.LoveItemBinding


class LoveAdapter(private val listener: OnItemClickListener): ListAdapter<Love, LoveAdapter.LoveViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoveViewHolder {
        val binding = LoveItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return LoveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoveViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class LoveViewHolder(private val binding: LoveItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val love = getItem(position)
                        listener.onItemClick(love)
                    }
                }
            }
        }
        fun bind(love: Love){
            binding.apply {
                fname.text = love.fname
                percentage.text = love.percentage.toString()
                sname.text = love.sname
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(love: Love)
    }

    class DiffCallback : DiffUtil.ItemCallback<Love>(){
        override fun areItemsTheSame(oldItem: Love, newItem: Love): Boolean {
            return oldItem.fname == newItem.fname && oldItem.sname == newItem.sname
        }

        override fun areContentsTheSame(oldItem: Love, newItem: Love): Boolean {
            return oldItem == newItem
        }

    }
}