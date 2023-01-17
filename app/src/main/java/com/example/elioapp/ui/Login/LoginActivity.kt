package com.example.elioapp.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.elioapp.R
import com.example.elioapp.databinding.ActivityLoginBinding
import com.example.elioapp.ui.ShowGithubRepo.ShowGithubRepoActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        loginActivityBinding.btnLogin.setOnClickListener{
            if (loginActivityBinding.edtApiKey.text.isNotEmpty()) {
                intent = Intent(this, ShowGithubRepoActivity::class.java)
                intent.putExtra(TOKEN_KEY, loginActivityBinding.edtApiKey.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    private fun init() {
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    companion object {
        const val TOKEN_KEY = "TOKEN"
    }
}