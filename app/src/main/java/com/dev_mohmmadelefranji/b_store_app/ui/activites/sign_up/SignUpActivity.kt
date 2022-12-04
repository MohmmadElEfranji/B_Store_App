package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_up

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.ActivitySignUpBinding
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in.SignInActivity
import com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_up.activate_account.ActivateAccountActivity
import com.dev_mohmmadelefranji.b_store_app.utils.Constants
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.STORE_API_KEY


class SignUpActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private val list: ArrayList<String> = arrayListOf()

    /*----------------------------------------*/
    private lateinit var viewModel: SignUpViewModel

    /*----------------------------------------*/
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        /*----------------------------------------*/

        val factory = SignUpViewModel(AuthRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]
        /*----------------------------------------*/

        binding.lMobileNumber.isEndIconVisible = false
        binding.edMobileNumber.addTextChangedListener(generalTextWatcher)

        binding.lUserName.isEndIconVisible = false
        binding.edUserName.addTextChangedListener(generalTextWatcher)

        /*----------------------------------------*/

        viewModel.getAllCities()

        observeGetAllCites()
        /*----------------------------------------*/
        //region UI action
        binding.btnSignUP.setOnClickListener {
            //  Toast.makeText(this, "${binding.spinner.selectedIndex + 1}", Toast.LENGTH_SHORT).show()
            checkFieldsThenSignUp()
        }

        binding.tvHaveAccount.setOnClickListener {
            goToSignInActivity()
        }
        //endregion

        //region Test saveList
        /*
         val m = sharedPreferences.getStringSet(Constants.KEY_TOKEN, emptySet())!!

         if (m.isEmpty()) {
             viewModel.getAllCities()
             Log.d("getAllCities", "onCreate: getAllCities()")
             binding.spinner.setItems(m.toMutableList())
         }
         binding.spinner.setItems(m.toMutableList())

         */
        //endregion
    }

    private fun observeGetAllCites() {

        lifecycleScope.launchWhenCreated {
            viewModel.getAllCities.collect {
                when (it) {
                    is ApiResponse.Loading -> Unit
                    is ApiResponse.Success -> {
                        it.data?.let { data ->
                            for (i in data.citiesList) {

                                list.add(i.nameAr)
                            }
                            // saveList(list)
                            binding.spinner.setItems(list)

                        }
                    }
                    is ApiResponse.Failure -> Unit
                    is ApiResponse.Empty -> Unit
                }
            }
        }
    }

    //region TextWatcher
    private val generalTextWatcher: TextWatcher = object : TextWatcher {

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val mobileNumber = binding.edMobileNumber.text.toString()
            val userName = binding.edUserName.text.toString()

            binding.lMobileNumber.isEndIconVisible = mobileNumber.length == 9
            binding.lUserName.isEndIconVisible = userName.length >= 3

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
    }
    //endregion

    //region Sign Up
    private fun checkFieldsThenSignUp() {
        val fullName = binding.edUserName.text.toString().trim()
        val mobileNumber = binding.edMobileNumber.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()


        when {
            fullName.isEmpty() -> {
                binding.edUserName.error = "Fill first name"
            }
            mobileNumber.isEmpty() -> {
                binding.edMobileNumber.error = "Fill email "
            }
            password.isEmpty() -> {
                binding.edPassword.error = "Fill password"
            }
            else -> {
                signUp(
                    userName = fullName,
                    mobileNumber = mobileNumber,
                    password = password,
                )
            }
        }
    }

    private fun signUp(
        userName: String,
        mobileNumber: String,
        password: String,

        ) {
        if ((binding.rbMale.isChecked || binding.rbFemale.isChecked)) {

            when (binding.rbAccountType.checkedRadioButtonId) {
                binding.rbMale.id -> {

                    viewModel.signUp(
                        userName,
                        mobileNumber,
                        password,
                        gender = "M",
                        STORE_API_KEY,
                        cityID = binding.spinner.selectedIndex + 1
                    )
                    lifecycleScope.launchWhenCreated {
                        viewModel.signUp.collect {
                            when (it) {
                                is ApiResponse.Loading -> {
                                    binding.progressBar.show()
                                }
                                is ApiResponse.Success -> {
                                    binding.progressBar.hide()

                                    it.data?.let { data ->
                                        Toast.makeText(
                                            this@SignUpActivity,
                                            data.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        if (binding.edMobileNumber.text!!.isNotEmpty()) {
                                            goToActivateAccountActivity(
                                                binding.edMobileNumber.text.toString(),
                                                data.code.toString()
                                            )
                                        } else {
                                            Toast.makeText(
                                                this@SignUpActivity,
                                                "edMobileNumber",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }

                                }
                                is ApiResponse.Failure -> {
                                    binding.progressBar.hide()
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is ApiResponse.Empty -> Unit
                            }
                        }
                    }


                }
                binding.rbFemale.id -> {
                    viewModel.signUp(
                        userName,
                        mobileNumber,
                        password,
                        gender = "F",
                        STORE_API_KEY,
                        cityID = binding.spinner.selectedIndex + 1
                    )

                    lifecycleScope.launchWhenCreated {
                        viewModel.signUp.collect {
                            when (it) {
                                is ApiResponse.Loading -> {
                                    binding.progressBar.show()
                                }
                                is ApiResponse.Success -> {
                                    binding.progressBar.hide()

                                    it.data?.let { data ->
                                        Toast.makeText(
                                            this@SignUpActivity,
                                            data.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        if (binding.edMobileNumber.text!!.isNotEmpty()) {
                                            goToActivateAccountActivity(
                                                binding.edMobileNumber.text.toString(),
                                                data.code.toString()
                                            )
                                        } else {
                                            Toast.makeText(
                                                this@SignUpActivity,
                                                "edMobileNumber",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }
                                }
                                is ApiResponse.Failure -> {
                                    binding.progressBar.hide()
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is ApiResponse.Empty -> Unit
                            }
                        }
                    }
                }
            }
        }

    }
    //endregion

    //region Intent
    private fun goToActivateAccountActivity(mobileNumber: String, activateCode: String) {
        val intent = Intent(this, ActivateAccountActivity::class.java)
        intent.putExtra("mobileNumber", mobileNumber)
        intent.putExtra("activateCode", activateCode)
        startActivity(intent)
    }

    private fun goToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
    //endregion

    private fun saveList(list: ArrayList<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(Constants.KEY_TOKEN, list.toMutableSet())
        editor.apply()
    }
}