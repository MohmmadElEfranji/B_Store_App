package com.dev_mohmmadelefranji.b_store_app.ui.fragments.cart

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentCartBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.ProductOrder
import com.dev_mohmmadelefranji.b_store_app.model.local.response.RoomDbResponse
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.CartProductItemAdapter
import com.dev_mohmmadelefranji.b_store_app.utils.Constants
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlin.properties.Delegates

class CartFragment : Fragment(), CartProductItemAdapter.OnValueOfQuantityChangeListener {
    /*----------------------------------------*/
    private val cartProductItemAdapter: CartProductItemAdapter by lazy {
        CartProductItemAdapter(this)
    }

    /*----------------------------------------*/
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private lateinit var viewModel: CartViewModel

    private val listOfProductOrder: ArrayList<ProductOrder> = arrayListOf()

    private var total by Delegates.notNull<Float>()
    var totalCalculation by Delegates.notNull<Float>()
    private val numbers: ArrayList<Float> = arrayListOf()
    private val totalItems: ArrayList<Int> = arrayListOf()

    /*----------------------------------------*/
    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*----------------------------------------*/
        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        /*----------------------------------------*/
        setRecyclerView()
        /*----------------------------------------*/
        viewModel.getProductOfCart(getUserID())
        observeGetProductOfCart()
        /*----------------------------------------*/


        binding.btnOrderNow.setOnClickListener {
            for (i in cartProductItemAdapter.currentList) {

                if (i.quantityOfOrder > 0) {

                    val productOrder = ProductOrder(i.id, i.quantityOfOrder)
                    listOfProductOrder.add(productOrder)

                    total = i.quantityOfOrder.toFloat() * i.price

                    numbers.add(total)

                    totalItems.add(i.quantityOfOrder)
                }

            }
            totalCalculation = numbers.sum()
            val action =
                CartFragmentDirections.actionCartFragmentToOrderFragment(
                    listOfProductOrder.toTypedArray(),
                    totalCalculation,
                    totalItems.sum()
                )
            findNavController().navigate(action)
            numbers.clear()
            totalItems.clear()
            listOfProductOrder.clear()
        }

        /*----------------------------------------*/       /*----------------------------------------*/
    }

    private fun setRecyclerView() {
        val divider = MaterialDividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL /*or LinearLayoutManager.HORIZONTAL*/
        )


        binding.rvProducts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cartProductItemAdapter
            addItemDecoration(divider)
            itemAnimator = null //for hide effects after data change
        }
    }

    private fun observeGetProductOfCart() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getProductOfCart.collect {
                when (it) {
                    is RoomDbResponse.Loading -> {
                        binding.progressBar.show()
                    }
                    is RoomDbResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->

                            if (data.isNotEmpty()) {
                                binding.imgEmptyCart.hide()
                                cartProductItemAdapter.submitList(data)

                            } else if (data.isEmpty()) {
                                binding.imgEmptyCart.show()
                            }
                        }
                    }
                    is RoomDbResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is RoomDbResponse.Empty -> Unit
                }
            }
        }
    }

    private fun observeUpdateQuantity() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.updateQuantity.collect {
                when (it) {
                    is RoomDbResponse.Loading -> Unit
                    is RoomDbResponse.Success -> Unit
                    is RoomDbResponse.Failure -> {
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is RoomDbResponse.Empty -> Unit
                }
            }
        }
    }

    override fun getNewValueOfQuantity(newValue: Int, productID: Int) {

        viewModel.updateQuantity(newValue, productID)

        observeUpdateQuantity()

        viewModel.getProductOfCart(getUserID())
    }


    private fun getUserID(): Int {
        return sharedPreferences.getInt(Constants.KEY_USER_ID, 0)
    }
}