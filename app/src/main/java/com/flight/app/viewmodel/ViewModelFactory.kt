package com.flight.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <VM : ViewModel> ViewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
    }