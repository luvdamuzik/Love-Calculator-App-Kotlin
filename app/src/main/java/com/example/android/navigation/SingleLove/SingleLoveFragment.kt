package com.example.android.navigation.SingleLove

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentSingleLoveBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleLoveFragment : Fragment(R.layout.fragment_single_love) {

    private val viewModel: SingleLoveViewModel by viewModels()
    private val args by navArgs<SingleLoveFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        val binding = FragmentSingleLoveBinding.bind(view)
        binding.apply {
            val love = args.love

            viewModel.getAllUsers().observe(viewLifecycleOwner){
                val name1 = love.fname?.split(" ")
                val name2 = love.sname?.split(" ")
                for(i in it.indices){
                    if(it[i].results?.get(0)?.name?.first == name1?.get(0) && it[i].results?.get(0)?.name?.last == name1?.get(1)){
                        it[i].results?.get(0)?.picture?.large?.let { it1 -> viewModel.PlacePic1(it1) }
                    }
                    if(it[i].results?.get(0)?.name?.first == name2?.get(0) && it[i].results?.get(0)?.name?.last == name2?.get(1)){
                        it[i].results?.get(0)?.picture?.large?.let { it1 -> viewModel.PlacePic2(it1) }
                    }
                }
            }

            viewModel.pic1.observe(viewLifecycleOwner){
                imeImage1.apply {
                    Glide.with(view)
                        .load(it)
                        .into(imeImage1)
                }
            }

            viewModel.pic2.observe(viewLifecycleOwner){
                imeImage1.apply {
                    Glide.with(view)
                        .load(it)
                        .into(imeImage2)
                }
            }

            ime1.text = getString(R.string.name_first_last,love.fname)
            ime2.text = getString(R.string.name_first_last,love.sname)
            calculated1.text = getString(R.string.calculated,love.percentage.toString(),love.result)

        }
    }

    private fun getShareIntent() : Intent {
        val args = SingleLoveFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message,args.love.fname,args.love.sname,args.love.percentage.toString()))
        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
        if(getShareIntent().resolveActivity(requireActivity().packageManager)==null){
            menu.findItem(R.id.share).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}