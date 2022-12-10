package com.example.mochatest.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

open class BaseViewModel : ViewModel(), CoroutineScope by MainScope() {


    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}