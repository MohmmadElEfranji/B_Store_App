package com.dev_mohmmadelefranji.b_store_app.ui.fragments.order_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentOrderBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.Address
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.OrdersRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.MySpinnerAdapter
import com.dev_mohmmadelefranji.b_store_app.utils.Constants
import com.google.gson.Gson
import com.skydoves.powerspinner.PowerSpinnerView
import kotlin.properties.Delegates


class OrderFragment : Fragment() {
    /*----------------------------------------*/
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private val mySpinnerAdapter by lazy {
        MySpinnerAdapter(
            PowerSpinnerView(requireContext())
        )
    }

    /*----------------------------------------*/
    private lateinit var viewModel: OrderViewModel
    private val args: OrderFragmentArgs by navArgs()

    /*----------------------------------------*/
    var addressID by Delegates.notNull<Int>()

    /*----------------------------------------*/
    private lateinit var binding: FragmentOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                com.dev_mohmmadelefranji.b_store_app.R.layout.fragment_order,
                container,
                false
            )
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region createFactory
        val factory = OrderViewModel(OrdersRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
        //endregion

        /*----------------------------------------*/
        val totalPrice = args.totalPrice
        val totalItems = args.totalItems
        binding.tvTotalPrice.text = "$$totalPrice"
        binding.tvTotalItems.text = "$totalItems items"
        /*----------------------------------------*/
        //region AddressSpinner
        binding.AddressSpinner.setSpinnerAdapter(mySpinnerAdapter)

        binding.AddressSpinner.setOnSpinnerItemSelectedListener<Address> { oldIndex, oldItem, newIndex, newItem ->

            binding.AddressSpinner.text = newItem.name
            binding.AddressSpinner.dismiss()

            addressID = newItem.addressID

        }
        //endregion
        /*----------------------------------------*/
        requireView().setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {

                if (binding.AddressSpinner.isShowing) {
                    binding.AddressSpinner.dismiss()
                }
            }
            true
        }
        /*----------------------------------------*/
        binding.btnOrderNow.setOnClickListener {
            checkFieldsThenSendOrder()
        }
        observeSendOrder()
        /*----------------------------------------*/
        viewModel.getAllAddresses(getUserToken())
        observeGetAllAddresses()
        /*----------------------------------------*/
        //region test setOnKeyListener
        /*     requireView().isFocusableInTouchMode = true;
             requireView().requestFocus()
             requireView().setOnKeyListener(object : View.OnKeyListener {
                 override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {

                     if (p1 == KeyEvent.KEYCODE_BACK && p2!!.action == KeyEvent.ACTION_UP) {

                         Log.d("onKey", "onKey: hi")

                         return true
                     }
                     return false
                 }

             })
     */
//endregion
    }

    override fun onPause() {
        super.onPause()
        if (binding.AddressSpinner.isShowing) {
            binding.AddressSpinner.dismiss()
        }
    }

    private fun observeGetAllAddresses() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAllAddresses.collect { it ->
                when (it) {
                    is ApiResponse.Loading -> Unit
                    is ApiResponse.Success -> {
                        it.data?.let { data ->
                            if (data.listOfAddress.isNotEmpty()) {
                                mySpinnerAdapter.submitList(data.listOfAddress)
                            }
                        }
                    }
                    is ApiResponse.Failure -> {
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

    private fun observeSendOrder() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.sendOrder.collect { it ->
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

                            findNavController().popBackStack()

                            /* val toast = Toast(requireContext())
                             val view = ImageView(requireContext())
                             view.setImageResource(R.drawable.ic_delete)
                             toast.view = view
                             toast.show()*/

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


    private fun checkFieldsThenSendOrder() {

        if ((binding.rvCash.isChecked || binding.rbOnline.isChecked)) {

            when (binding.rbPaymentType.checkedRadioButtonId) {
                binding.rvCash.id -> {
                    binding.PaymentCardSpinner.visibility = View.GONE

                    val cart = args.productOrderObj
                    val mCart = Gson().toJson(cart)

                    sendOrders(cart = mCart, "Cash", addressID)

                    /*val gson = GsonBuilder().create()
                    val myCustomArray = gson.toJsonTree(cart).asJsonArray*/
                }
                binding.rbOnline.id -> {
                    binding.PaymentCardSpinner.visibility = View.VISIBLE

                }
            }
        } else {
            Toast.makeText(requireContext(), "Plz Choose Payment Type", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendOrders(
        cart: String,
        paymentType: String,
        addressID: Int,
    ) {
        viewModel.sendOrder(
            getUserToken(),
            cart,
            paymentType,
            addressID
        )
    }

    private fun getUserToken(): String {
        return sharedPreferences.getString(Constants.KEY_TOKEN, null)!!
    }

}