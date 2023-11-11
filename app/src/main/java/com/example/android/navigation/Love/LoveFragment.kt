package com.example.android.navigation.Love

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.R
import com.example.android.navigation.User.UserAdapter
import com.example.android.navigation.User.UserFragmentDirections
import com.example.android.navigation.data.LoveCalculator.Love
import com.example.android.navigation.data.RandomUsers.models.User
import com.example.android.navigation.databinding.FragmentLoveBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_love.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoveFragment : Fragment(R.layout.fragment_love),
    LoveAdapter.OnItemClickListener{

    private val viewModel: LoveViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoveBinding.bind(view)
        val loveAdapter = LoveAdapter(this)
        binding.apply {
            recycler_love.apply {
                adapter = loveAdapter
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val love = loveAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onLoveSwiped(love)
                }
            }).attachToRecyclerView(recycler_love)
        }

        viewModel.getAllLove().observe(viewLifecycleOwner) {
            loveAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.loveEvent.collect { event ->
                when (event) {
                    is LoveViewModel.LoveEvent.ShowUndoDeleteLoveMessage -> {
                        Snackbar.make(requireView(), "Item Deleted!",Snackbar.LENGTH_LONG)
                            .setAction("UNDO"){
                                viewModel.onUndoDeleteClick(event.love)
                            }.show()
                    }
                }
            }
        }
    }

    override fun onItemClick(love: Love) {
        val action = LoveFragmentDirections.actionLoveFragmentToSingleLoveFragment(love)
        findNavController().navigate(action)
    }
}