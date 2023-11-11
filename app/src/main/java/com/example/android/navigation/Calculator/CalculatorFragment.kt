package com.example.android.navigation.Calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.android.navigation.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calculator.*

@AndroidEntryPoint
class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllUsers().observe(viewLifecycleOwner){data ->
            for(i in data.indices){
                viewModel.array_test.add(data[i].results?.get(0)?.name?.first + " " + data[i].results?.get(0)?.name?.last)
                data[i].results?.get(0)?.picture?.let { viewModel.array_pictures.add(it.large) }
            }
        }

        val adapter1 = this.activity?.let { ArrayAdapter(it, R.layout.dropdown_entity, viewModel.array_test) }
        view.findViewById<AutoCompleteTextView>(R.id.act_dropdown1)?.setAdapter(adapter1)

        view.findViewById<AutoCompleteTextView>(R.id.act_dropdown1).onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemText = parent.getItemAtPosition(position)
                viewModel.setParameters1(selectedItemText.toString())
            }

        viewModel.selected_item1.observe(viewLifecycleOwner){
            if(viewModel.showButton()){
                calc_button.visibility = View.VISIBLE
                calc_button.setOnClickListener {
                    viewModel.saveLove()
                }
            }else{
                calc_button.visibility = View.INVISIBLE
            }
            view.findViewById<ImageView>(R.id.dropdown_image1).apply {
                Glide.with(view)
                    .load(viewModel.selected_item_picture1)
                    .into(this)
            }
        }

        val adapter2 = this.activity?.let { ArrayAdapter(it, R.layout.dropdown_entity, viewModel.array_test) }
        view.findViewById<AutoCompleteTextView>(R.id.act_dropdown2)?.setAdapter(adapter2)

        view.findViewById<AutoCompleteTextView>(R.id.act_dropdown2).onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemText = parent.getItemAtPosition(position)
                viewModel.setParameters2(selectedItemText.toString())
            }

        viewModel.selected_item2.observe(viewLifecycleOwner){
            if(viewModel.showButton()){
                calc_button.visibility = View.VISIBLE
                calc_button.setOnClickListener {
                    viewModel.saveLove()
                }
            }else{
                calculated.visibility = View.INVISIBLE
                calc_button.visibility = View.INVISIBLE
            }
            view.findViewById<ImageView>(R.id.dropdown_image2).apply {
                Glide.with(view)
                    .load(viewModel.selected_item_picture2)
                    .into(this)
            }
        }
        viewModel.love.observe(viewLifecycleOwner){
            calc_button.visibility = View.INVISIBLE
            view.findViewById<TextView>(R.id.calculated).text = getString(R.string.calculated,viewModel.love.value?.percentage.toString(),viewModel.love.value?.result)
            calculated.visibility = View.VISIBLE
        }
    }
}