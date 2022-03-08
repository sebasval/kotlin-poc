package com.app.koltinpoc.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.app.koltinpoc.di.DBRepository
import com.app.koltinpoc.model.RedditListInfo
import com.app.koltinpoc.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(private val dbRepository: DBRepository) : ViewModel() {

    private val _deleteState = MutableLiveData<Boolean>()
    val deleteState: LiveData<Boolean> = _deleteState

    fun deleteElement(redditListInfo: RedditListInfo) {
        viewModelScope.launch {
            when (val result = dbRepository.deleteElementByName(redditListInfo)) {
                is DataHandler.SUCCESS -> {
                    _deleteState.postValue(true)
                }
                is DataHandler.ERROR -> {
                    _deleteState.postValue(false)
                }
                else -> {}
            }
        }
    }

    fun updateReadStatus(redditListInfo: RedditListInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = dbRepository.updateElementState(redditListInfo)) {
                is DataHandler.SUCCESS -> {}
                is DataHandler.ERROR -> {
                    Log.v("update_error", result.message.toString())
                }
                else -> {}
            }
        }
    }
}