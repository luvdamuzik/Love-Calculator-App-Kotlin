package com.example.android.navigation.User

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.R
import com.example.android.navigation.data.RandomUsers.models.User
import com.example.android.navigation.databinding.FragmentUserBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user),
    UserAdapter.OnItemClickListener {

    private val viewModel: UserViewModel by viewModels()
    private val userAdapter = UserAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        val binding = FragmentUserBinding.bind(view)

        binding.apply {
            user_grid.apply {
                adapter = userAdapter
            }
        }

        viewModel.getAllUsers().observe(viewLifecycleOwner) {
            userAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.savedArticleEvent.collect { event ->
                when (event) {
                    is UserViewModel.UserEvent.ShowUndoDeleteUserMessage -> {
                        Snackbar.make(requireView(), "User Deleted!",Snackbar.LENGTH_LONG)
                            .setAction("UNDO"){
                                viewModel.onUndoDeleteClick(event.user)
                            }.show()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if(id == R.id.all || id == R.id.male || id==R.id.female){filter(id)}
        return if(id == R.id.all || id == R.id.male || id==R.id.female){true} else {NavigationUI.onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)}
    }

    private fun filter(id:Int){
        if(id == R.id.all){
            viewModel.getAllUsers().observe(viewLifecycleOwner){
                userAdapter.submitList(it)
            }
        }else if(id == R.id.male){
            viewModel.getAllUsers().observe(viewLifecycleOwner){
                val MaleList:MutableList<User> = mutableListOf()
                for(i in it.indices){
                    if(it[i].results?.get(0)?.gender == "male"){
                        MaleList.add(it[i])
                    }
                }
                userAdapter.submitList(MaleList)
            }
        }else if(id == R.id.female){
            viewModel.getAllUsers().observe(viewLifecycleOwner){
                val FemaleList:MutableList<User> = mutableListOf()
                for(i in it.indices){
                    if(it[i].results?.get(0)?.gender == "female"){
                        FemaleList.add(it[i])
                    }
                }
                userAdapter.submitList(FemaleList)
            }
        }
    }

    override fun onItemClick(user: User) {
        val action = UserFragmentDirections.actionUserFragmentToSingleUserFragment(user)
        findNavController().navigate(action)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(activity, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(activity, "portrait", Toast.LENGTH_SHORT).show()
        }
    }
}