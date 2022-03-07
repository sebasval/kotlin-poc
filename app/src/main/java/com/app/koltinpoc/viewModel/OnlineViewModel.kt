package com.app.koltinpoc.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.koltinpoc.di.DBRepository
import com.app.koltinpoc.di.NetworkRepository
import com.app.koltinpoc.model.RedditInfo
import com.app.koltinpoc.model.RedditListInfo
import com.app.koltinpoc.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OnlineViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dbRepository: DBRepository
) : ViewModel() {

    private val _topHeadlines = MutableLiveData<DataHandler<List<RedditListInfo>>>()
    val topHeadlines: LiveData<DataHandler<List<RedditListInfo>>> = _topHeadlines

    fun getTopHeadlines() {
        _topHeadlines.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getTopHeadlines()
            handleResponse(response)
        }
    }

    private fun handleResponse(response: Response<RedditInfo>) {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                saveRedditInfo(it)
            }
        }
    }

     fun getAllLocalRedditInfo() {
         _topHeadlines.postValue(DataHandler.LOADING())
        viewModelScope.launch(Dispatchers.IO) {
            _topHeadlines.postValue(DataHandler.SUCCESS(dbRepository.getAllRedditInfo()))
        }
    }

    private fun saveRedditInfo(redditInfo: RedditInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = dbRepository.insertRedditInfo(redditInfo)) {
                is DataHandler.SUCCESS -> {
                    val data = dbRepository.getAllRedditInfo()
                    _topHeadlines.postValue(DataHandler.SUCCESS(data))
                }
                is DataHandler.ERROR -> {
                    _topHeadlines.postValue(DataHandler.ERROR(message = result.message.toString()))
                }
                else -> {}
            }
        }
    }
}