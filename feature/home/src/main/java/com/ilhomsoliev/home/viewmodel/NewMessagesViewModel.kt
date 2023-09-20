package com.ilhomsoliev.home.viewmodel

import androidx.lifecycle.ViewModel
import com.ilhomsoliev.profile.repository.ProfileRepository
import com.ilhomsoliev.shared.TgDownloadManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class NewMessagesViewModel(
    val downloadManager: TgDownloadManager,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _isSearchVisible = MutableStateFlow<Boolean>(false)
    val isSearchVisible = _isSearchVisible.asStateFlow()


    private val _searchRequest = MutableStateFlow<String>("")
    val searchRequest = _searchRequest.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val contacts by lazy {
        _searchRequest.debounce(250)
            .flatMapLatest { query ->
                profileRepository.getContactsPaging(query)
            }
    }


    suspend fun onIsSearchVisibleChange(value: Boolean) {
        _isSearchVisible.value = value
    }

    suspend fun onSearchRequestChange(value: String) {
        _searchRequest.value = value
    }

}