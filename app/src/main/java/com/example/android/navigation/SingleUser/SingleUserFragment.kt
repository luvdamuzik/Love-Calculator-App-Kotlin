package com.example.android.navigation.SingleUser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentSingleUserBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_single_user.*
import kotlinx.android.synthetic.main.fragment_single_user.view.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SingleUserFragment : Fragment(R.layout.fragment_single_user) {

    private val viewModel: SingleUserViewModel by viewModels()
    private val args by navArgs<SingleUserFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSingleUserBinding.bind(view)
        binding.apply {
            val user = args.user
            userImage.apply {
                Glide.with(view)
                    .load(user.results?.get(0)?.picture?.large)
                    .into(userImage)
            }

            user_details_first.text = getString(R.string.user_details_first,user.results?.get(0)?.name?.first)
            user_details_last.text = getString(R.string.user_details_last,user.results?.get(0)?.name?.last)
            user_details_age.text = getString(R.string.user_details_age,user.results?.get(0)?.dob?.age)
            user_details_country.text = getString(R.string.user_details_country,user.results?.get(0)?.location?.country)
            user_details_email.text = getString(R.string.user_details_email,user.results?.get(0)?.email)

            deleteButton.setOnClickListener {
                viewModel.deleteUser(user)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.userEvent.collect { event ->
                when (event) {
                    is SingleUserViewModel.UserEvent.ShowUserMessage -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}