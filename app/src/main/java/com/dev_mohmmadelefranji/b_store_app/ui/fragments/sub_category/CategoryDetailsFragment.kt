package com.dev_mohmmadelefranji.b_store_app.ui.fragments.sub_category

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentCategoryDetailsBinding
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.CategoryRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.SubCategoryItemAdapter
import com.dev_mohmmadelefranji.b_store_app.utils.Constants


class CategoryDetailsFragment : Fragment() {

    private val args: CategoryDetailsFragmentArgs by navArgs()

    private lateinit var viewModel: CategoryDetailsViewModel
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }
    private val subCategoryItemAdapter by lazy { SubCategoryItemAdapter() }

    private lateinit var binding: FragmentCategoryDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_category_details, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*----------------------------------------*/

        //region createFactory
        val factory = CategoryDetailsViewModel(CategoryRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[CategoryDetailsViewModel::class.java]
        //endregion
        /*----------------------------------------*/

        setRecyclerView()
        viewModel.getSubCategory(getUserToken(), args.categoryID)
        observeGetSubCategory()
    }

    private fun setRecyclerView() {
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = subCategoryItemAdapter
        }
    }

    private fun observeGetSubCategory() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getSubCategory.collect { it ->
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()

                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->

                            if (data.listOfCategory.isNotEmpty()) {

                                subCategoryItemAdapter.submitList(data.listOfCategory)
                            }

                        }
                    }
                    is ApiResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiResponse.Empty -> Unit
                }
            }
        }
    }

    private fun getUserToken(): String {
        return sharedPreferences.getString(Constants.KEY_TOKEN, null)!!
    }
}