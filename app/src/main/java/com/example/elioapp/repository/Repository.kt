package com.example.elioapp.repository

import com.example.elioapp.api.RetrofitInstance


class Repository{

     suspend fun getAllRepos(api_key: String) = RetrofitInstance.api.getAllRepos(api_key)

}