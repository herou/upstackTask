package com.example.elioapp.ui.ShowGithubRepo

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.elioapp.repository.Repository


/** Boilerplate code*/
class ShowGithubRepoViewModelProviderFactory(
    private val app: Application,
    private val ctx: Context,
    private val repository: Repository
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowGithubRepoActivityViewModel(app, repository) as T
    }
}