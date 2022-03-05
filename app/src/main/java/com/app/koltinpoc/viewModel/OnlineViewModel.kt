package com.app.koltinpoc.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.koltinpoc.di.NetworkRepository
import com.app.koltinpoc.model.NewResponse
import com.app.koltinpoc.model.RedditInfo
import com.app.koltinpoc.utils.Constants.API_KEY
import com.app.koltinpoc.utils.Constants.COUNTRY_CODE
import com.app.koltinpoc.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
 class OnlineViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _topHeadlines = MutableLiveData<DataHandler<RedditInfo>>()
    val topHeadlines: LiveData<DataHandler<RedditInfo>> = _topHeadlines

    fun getTopHeadlines() {
        _topHeadlines.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getTopHeadlines()
            _topHeadlines.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<RedditInfo>): DataHandler<RedditInfo> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}