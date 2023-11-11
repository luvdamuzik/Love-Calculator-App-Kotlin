package com.example.android.navigation.Add

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.android.navigation.R
import com.example.android.navigation.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add.*


@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add){

    private val viewModel: AddViewModel by viewModels()
    var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    isLoading = false
                    it.data?.let { data ->
                        println(data.results?.get(0))
                        user_image1.apply {
                            Glide.with(view)
                                .load(data.results?.get(0)?.picture?.large)
                                .into(user_image1)
                        }
                        user_details1_first.text = getString(R.string.user_details_first,data.results?.get(0)?.name?.first)
                        user_details1_last.text = getString(R.string.user_details_last,data.results?.get(0)?.name?.last)
                        user_details1_age.text = getString(R.string.user_details_age,data.results?.get(0)?.dob?.age)
                        user_details1_country.text = getString(R.string.user_details_country,data.results?.get(0)?.location?.country)
                        user_details1_email.text = getString(R.string.user_details_email,data.results?.get(0)?.email)

                        save_button.setOnClickListener {
                            viewModel.saveUser(data)
                        }
                    }
                }
                is Resource.Error -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    isLoading = true
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    paginationProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

}