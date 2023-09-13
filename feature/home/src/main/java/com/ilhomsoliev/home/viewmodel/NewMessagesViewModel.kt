package com.ilhomsoliev.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ilhomsoliev.profile.ContactsPagingSource
import com.ilhomsoliev.profile.ProfileRepository
import com.ilhomsoliev.shared.TgDownloadManager
import kotlinx.coroutines.launch

class NewMessagesViewModel(
    val downloadManager: TgDownloadManager,
    private val profileRepository: ProfileRepository,
    private val contactsPagingSource: ContactsPagingSource
) : ViewModel() {

    val contacts = Pager(
        PagingConfig(pageSize = 30)
    ){
        viewModelScope.launch {

        }
        contactsPagingSource
    }.flow

}