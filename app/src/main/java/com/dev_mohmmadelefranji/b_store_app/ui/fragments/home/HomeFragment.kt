package com.dev_mohmmadelefranji.b_store_app.ui.fragments.home

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentHomeBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.AllCategory
import com.dev_mohmmadelefranji.b_store_app.model.entity.AllProduct
import com.dev_mohmmadelefranji.b_store_app.model.entity.Category
import com.dev_mohmmadelefranji.b_store_app.model.entity.Product
import com.dev_mohmmadelefranji.b_store_app.model.entity.listener.OnClickItemListener
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.HomeScreenRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.HomeAdapter
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.MainRvAdapter
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.item_adapters.CategoryItemAdapter
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.item_adapters.ProductItemAdapter
import com.dev_mohmmadelefranji.b_store_app.utils.ConnectivityObserver
import com.dev_mohmmadelefranji.b_store_app.utils.Constants
import com.dev_mohmmadelefranji.b_store_app.utils.NetworkConnectivityObserver
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.divider.MaterialDividerItemDecoration

class HomeFragment : Fragment(), ProductItemAdapter.OnLikeProductListener,
    OnClickItemListener<Product>, CategoryItemAdapter.OnCategoryClickListener {

    companion object {
        const val TAG = "_HomeFragment"
    }

    /*----------------------------------------*/
    //region test
    private val allCategory: MutableList<AllCategory> = arrayListOf()
    private val allProduct: MutableList<AllProduct> = arrayListOf()
    val itemList = ArrayList<Any>()
    private val allCategory2: MutableList<Category> = arrayListOf()
    private val adapterList by lazy { HomeAdapter(requireContext(), allCategory2) }
    private val adapterList2 by lazy {
        MainRvAdapter(
            requireContext(), allCategory, allProduct, this, this
        )
    }

    //endregion
    /*----------------------------------------*/
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private lateinit var connectivityObserver: ConnectivityObserver

    /*----------------------------------------*/
    private val categoryAdapter by lazy { CategoryItemAdapter(this) }

    private val productAdapter by lazy { ProductItemAdapter(this, this) }

    /*----------------------------------------*/
    private lateinit var viewModel: HomeViewModel
    private var imageList = ArrayList<SlideModel>()

    /*----------------------------------------*/
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*----------------------------------------*/
        // observeConnect()
        //region createFactory
        val factory = HomeViewModel(HomeScreenRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        //endregion
        connectivityObserver = NetworkConnectivityObserver(requireActivity().applicationContext)

        /*----------------------------------------*/

        val divider = MaterialDividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL /*or LinearLayoutManager.HORIZONTAL*/
        )

        // binding.rvHome.addItemDecoration(divider)

        getDataOfHome()
        observeGetDataOfHome()



        setCategoriesRv()
        setProductsRv()
    }

    //region set up Rv Categories & Products
    private fun setCategoriesRv() {
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = categoryAdapter
            itemAnimator = null //to hide the effect after data change
        }

    }

    private fun setProductsRv() {
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = productAdapter
            itemAnimator = null //for hide effects after data change
        }
    }

    //endregion


    private fun getDataOfHome() {
        viewModel.getDataOfHomeScreen(token = getUserToken())
        Log.d(TAG, "observeGetDataOfHome ${getUserToken()}")
    }

    private fun observeConnect() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            connectivityObserver.observer().collect {
                Toast.makeText(
                    requireContext(),
                    "Network Status :${it.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeGetDataOfHome() {

        Log.d(TAG, "observeGetDataOfHome")
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getDataOfHomeScreen.collect { it ->
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()

                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->
                            //region slider
                            if (data.data.slider.isNotEmpty() || data.data.categories.isNotEmpty() || data.data.famousProducts.isNotEmpty()) {

                                for (i in data.data.slider) {
                                    val slideModel =
                                        SlideModel(imageUrl = i.imageUrl)
                                    imageList.add(slideModel)
                                    Log.d(TAG, "observeGetDataOfHome: ${i.imageUrl}")
                                }
                                val imageListAfterDistinct = imageList.distinctBy {
                                    it.imageUrl
                                }
                                binding.imageSlider.setImageList(
                                    imageListAfterDistinct,
                                    ScaleTypes.CENTER_INSIDE
                                )
                            }
                            //endregion

                            //region multi view holders
                            /*        itemList.add(Headers.CategoryHeader(commonKey = true))
                                    itemList.add(Headers.LatestProductsHeader())
                                    itemList.add(Headers.FamousProductsHeader())
                                    if (data.data.categories.isNotEmpty()) {
                                        //itemList.addAll(data.data.categories)

                                  allCategory2.addAll(data.data.categories)

                                    val sjs = DataOfMain(true,allCategory2)
                                      itemList.add(sjs)
                                    }
                                    if (data.data.latestProducts.isNotEmpty()) {
                                        itemList.addAll(data.data.latestProducts)
                                    }
                                    if (data.data.famousProducts.isNotEmpty()) {
                                        itemList.addAll(data.data.famousProducts)
                                    }

                                    val itemGroupBy = itemList.groupBy {
                                        when (it) {

                                            is DataOfMain -> {
                                              //  val startIndex = 0
                                              //  val endIndex = 10
                                              //  val substring = it.imageName.subSequence(startIndex, endIndex)
                                                it.name
                                               // substring
                                            }

                                            is Product -> {
                                               val m = it.imageUrl.contains("products")

                                                if (m){
                                                    "products"
                                                } else {
                                                    ""
                                                }

                                            }

                                            is LatestProduct -> {
                                                val m = it.imageUrl.contains("products")

                                                if (m){
                                                    "LatestProduct"
                                                } else {
                                                    ""
                                                }

                                            }

                                            is Headers.CategoryHeader -> {
                                                it.commonKey
                                            }
                                            is Headers.LatestProductsHeader -> {
                                                it.commonKey
                                            }
                                            is Headers.FamousProductsHeader -> {
                                                it.commonKey
                                            }
                                            else -> {}
                                        }
                                    }

                                    adapterList.updateList(itemGroupBy.values.flatten())

                                  Log.d(TAG, "observeGetDataOfHome flatten: ${itemGroupBy.values.flatten()}")*/
                            //endregion


                            //region nested rv

                            /*  if (data.data.categories.isNotEmpty()) {
                                  /*val categoryItemList: MutableList<Category> = ArrayList()
                                  categoryItemList.addAll(data.data.categories)*/
                                  //   allCategory.clear()
                                  allCategory.add(AllCategory("Categories", data.data.categories))

                                  val allCategoryAfterDistinct = allCategory.distinctBy {
                                      it.categoryTitle
                                  }
                                  adapterList2.addCategory(allCategoryAfterDistinct)

                              }*/


                            //      if (data.data.famousProducts.isNotEmpty()) {

                            /*  val famousProductsAfterFilter = data.data.famousProducts.filter {
                                  it.quantity != "0"
                              }
                              /*val productItemList: MutableList<Product> = ArrayList()
                              productItemList.addAll(famousProductsAfterFilter)*/
                              // allProduct.clear()
                              allProduct.add(
                                  AllProduct(
                                      "Famous Products",
                                      famousProductsAfterFilter
                                  )
                              )

                              val allProductAfterDistinct = allProduct.distinctBy {
                                  it.productTitle
                              }

                          Log.d(TAG, "observeGetDataOfHome: $allProduct")
                              adapterList2.addProduct(allProductAfterDistinct)
                        //  }
*/
                            //endregion


                            if (data.data.categories.isNotEmpty()) {
                                binding.rvCategoryTitle.text =
                                    resources.getText(R.string.categories)
                                categoryAdapter.submitList(data.data.categories)
                            }
                            if (data.data.famousProducts.isNotEmpty()) {
                                binding.rvProductTitle.text =
                                    resources.getText(R.string.famous_products)

                                productAdapter.submitList(data.data.famousProducts)
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

    private fun addChip(text: String) {
        val chipDrawable: ChipDrawable = ChipDrawable.createFromAttributes(
            requireContext(),
            null,
            0,
            R.style.CustomChipStyle
        )
        val chip = Chip(requireContext())
        chip.text = text
        chip.setTextColor(Color.BLACK)
        chip.setChipDrawable(chipDrawable)
        //binding.chipGroup.addView(chip)

    }

    override fun likedListener(productID: Int, position: Int) {

        viewModel.addAndRemoveFavoriteProducts(getUserToken(), productID)
        observeAddAndRemoveFavoriteProducts()


        if (productAdapter.currentList[position].isFavorite) {
            productAdapter.currentList[position].isFavorite = false
        } else if (!productAdapter.currentList[position].isFavorite) {
            productAdapter.currentList[position].isFavorite = true
        }

    }


    private fun observeAddAndRemoveFavoriteProducts() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.addAndRemoveFavoriteProducts.collect {
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()
                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->
                            Toast.makeText(
                                requireContext(),
                                data.message,
                                Toast.LENGTH_SHORT
                            ).show()


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

    override fun onClickItem(item: Product) {

        val action = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(item)
        findNavController().navigate(action)
    }

    override fun onClickCategory(item: Category) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToCategoryDetailsFragment(item.categoryID)
        findNavController().navigate(action)
    }


}
