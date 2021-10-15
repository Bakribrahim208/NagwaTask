package com.nagwa.task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory<T : ViewModel> @Inject constructor(private val viewModel: dagger.Lazy<T>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel.get() as T
}