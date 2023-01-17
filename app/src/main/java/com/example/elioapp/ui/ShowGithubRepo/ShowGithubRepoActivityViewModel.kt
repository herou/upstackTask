package com.example.elioapp.ui.ShowGithubRepo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elioapp.models.GithubRepository
import com.example.elioapp.repository.Repository
import com.example.elioapp.util.InternetConnection
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.M)
class ShowGithubRepoActivityViewModel(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {


    var githubRepos = MutableLiveData<List<GithubRepository>>()

    var viewState = MutableLiveData<Int>()

    enum class MainActivityState(viewState: Int) {
        EMPTY(0), LOADING(1), ERROR(2), RESULT(3), EXIT(4), WRONG_TOKEN(5)
    }

    //the context
    private val context = getApplication<Application>().applicationContext

    fun getAllGithubRepo(token: String) {
        viewModelScope.launch {
            if (InternetConnection.isOnline(context)) {
                viewState.value = MainActivityState.LOADING.ordinal
                val call = repository.getAllRepos(token)


                if (call.body()?.isEmpty() == true) {
                    viewState.value = MainActivityState.EMPTY.ordinal
                } else if(call.code() == 401){
                    viewState.value = MainActivityState.WRONG_TOKEN.ordinal
                }

                if (call.isSuccessful && call != null && (call.body()?.isNotEmpty() == true)) {
                    viewState.value = MainActivityState.RESULT.ordinal
                    githubRepos.value = call.body()

                }

            } else {
                viewState.value = MainActivityState.ERROR.ordinal
            }
        }
    }

}