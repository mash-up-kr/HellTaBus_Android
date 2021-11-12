package com.mashup.healthyup.features.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        viewModel.onClickLogin.observe(this, {
            val signInIntent = googleSignInClient.signInIntent
            resultLauncher?.launch(signInIntent)
        })

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.e(TAG, "firebaseAuthWithGoogle id:" + account.id)
                    Log.e(TAG, "firebaseAuthWithGoogle idToken:" + account.idToken)
                    Log.e(TAG, "firebaseAuthWithGoogle account:" + account.account)
                    Log.e(TAG, "firebaseAuthWithGoogle email:" + account.email)
                    Log.e(TAG, "firebaseAuthWithGoogle serverAuthCode:" + account.serverAuthCode)
                    Log.e(TAG, "firebaseAuthWithGoogle requestedScopes:" + account.requestedScopes)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.e(TAG, "Google sign in failed", e)
                }
            }
        }
    }

}
