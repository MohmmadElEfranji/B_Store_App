package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.ActivitySignInBinding
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.activites.containers.MainContainerActivity
import com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in.forget_password.ForgetPasswordActivity
import com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_up.SignUpActivity
import com.dev_mohmmadelefranji.b_store_app.utils.Constants
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.KEY_USER_ID
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInViewModel

    /*----------------------------------------*/
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        /*----------------------------------------*/
        val factory = SignInViewModel(AuthRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[SignInViewModel::class.java]
        /*----------------------------------------*/

        //region UI action
        binding.btnSignIn.setOnClickListener {
            checkFieldsThenSignIn()
        }

        binding.tvCreateAnAccount.setOnClickListener {
            goToSignUpActivity()
        }

        binding.tvForgetPassword.setOnClickListener {

            val mobileNumber = binding.edMobileNumber.text.toString().trim()
            when {
                mobileNumber.isEmpty() -> {
                    binding.edMobileNumber.error = "Fill mobileNumber "
                }
                else -> {
                    goToForgetPasswordActivity(mobileNumber = mobileNumber)
                }
            }
        }

        //endregion
        observeSignIn()
    }


    private fun checkFieldsThenSignIn() {
        val mobileNumber = binding.edMobileNumber.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()

        when {
            mobileNumber.isEmpty() -> {
                binding.edMobileNumber.error = "Fill mobileNumber "
            }
            password.isEmpty() -> {
                binding.edPassword.error = "Fill password"
            }

            else -> {

                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d("Fcm", "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result
                    //Log.d("Fcm", "onViewCreated: token -> $token ")
                    viewModel.signIn(
                        mobileNumber = mobileNumber,
                        password = password,
                        fcmToken = token
                    )
                    Log.d("Fcm", "onViewCreated: token -> $token ")
                })


            }
        }
    }

    private fun observeSignIn() {
        lifecycleScope.launchWhenCreated {
            viewModel.signIn.collect {
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()
                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->
                            Toast.makeText(
                                this@SignInActivity,
                                data.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            val user = data.user
                            saveUserToken(user.token, user.id)
                            goToMainContainerActivity()
                        }

                    }
                    is ApiResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            this@SignInActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiResponse.Empty -> Unit
                }
            }
        }
    }

    private fun saveUserToken(token: String, userID: Int) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.KEY_TOKEN, token)
        editor.putInt(KEY_USER_ID, userID)
        editor.apply()
    }

    private fun goToSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun goToMainContainerActivity() {
        val intent = Intent(this, MainContainerActivity::class.java)
        startActivity(intent)
    }

    private fun goToForgetPasswordActivity(mobileNumber: String) {
        val intent = Intent(this, ForgetPasswordActivity::class.java)
        intent.putExtra("mobileNumber", mobileNumber)
        startActivity(intent)
        finish()
    }
}