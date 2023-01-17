package com.example.elioapp.ui.ShowGithubRepo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elioapp.R
import com.example.elioapp.databinding.ActivityShowGithubRepoBinding
import com.example.elioapp.repository.Repository
import com.example.elioapp.ui.Login.LoginActivity
import com.example.elioapp.ui.Login.LoginActivity.Companion.TOKEN_KEY
import kotlinx.coroutines.*


class ShowGithubRepoActivity : AppCompatActivity() {

    private lateinit var activityShowGithubRepoBinding: ActivityShowGithubRepoBinding
    private lateinit var viewModel: ShowGithubRepoActivityViewModel
    private var token = ""


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        token = intent.getStringExtra(TOKEN_KEY).toString()

        init()
        observeViewState()
        setUpRecycleView()

        viewModel.getAllGithubRepo(token)

        // bind the view model
        activityShowGithubRepoBinding.lifecycleOwner = this

    }

    private fun init() {
        activityShowGithubRepoBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_github_repo)
        val repository = Repository()
        val viewModelProviderFactory =
            ShowGithubRepoViewModelProviderFactory(application,applicationContext, repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ShowGithubRepoActivityViewModel::class.java)
    }


    @SuppressLint("WrongConstant")
    private fun setUpRecycleView() {
        activityShowGithubRepoBinding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        viewModel.githubRepos.observe(this, Observer {
            it?.let {
                val adapter = CustomAdapter(it)

                // Setting the Adapter with the recyclerview
                activityShowGithubRepoBinding.recyclerView.adapter = adapter
                Log.d("", it.toString())
            }
        })
    }

    private fun showLoading() {
        activityShowGithubRepoBinding.progressBar.visibility = View.VISIBLE

        activityShowGithubRepoBinding.recyclerView.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtNotResultMsg.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtWrongToken .visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE
    }


    private fun showResult() {
        activityShowGithubRepoBinding.recyclerView.visibility = View.VISIBLE

        activityShowGithubRepoBinding.progressBar.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtNotResultMsg.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtWrongToken .visibility = View.INVISIBLE


    }


    private fun showNoResultFound() {
        activityShowGithubRepoBinding.txtNotResultMsg.visibility = View.VISIBLE

        activityShowGithubRepoBinding.progressBar.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.recyclerView.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtWrongToken .visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtWrongToken .visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE

    }


    private fun showAnErrorHappened() {
        activityShowGithubRepoBinding.txtSomethingWentWrongMsg.visibility = View.VISIBLE

        activityShowGithubRepoBinding.progressBar.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.recyclerView.visibility = View.VISIBLE
        activityShowGithubRepoBinding.txtNotResultMsg.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtWrongToken .visibility = View.INVISIBLE


    }

    private fun showWrongToken() {
        activityShowGithubRepoBinding.txtWrongToken.visibility = View.VISIBLE

        activityShowGithubRepoBinding.progressBar.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.recyclerView.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtNotResultMsg.visibility = View.INVISIBLE
        activityShowGithubRepoBinding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE

    }

    private fun showLoginActivity() {
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                ShowGithubRepoActivityViewModel.MainActivityState.LOADING.ordinal -> {
                    showLoading()
                }

                ShowGithubRepoActivityViewModel.MainActivityState.RESULT.ordinal -> {
                    showResult()
                }

                ShowGithubRepoActivityViewModel.MainActivityState.ERROR.ordinal -> {
                    showAnErrorHappened()
                }

                ShowGithubRepoActivityViewModel.MainActivityState.WRONG_TOKEN .ordinal -> {
                    showWrongToken()
                }

                ShowGithubRepoActivityViewModel.MainActivityState.EMPTY.ordinal -> {
                    showNoResultFound()
                }

                ShowGithubRepoActivityViewModel.MainActivityState.EXIT.ordinal -> {
                    showLoginActivity()
                }
            }
        })
    }
}
