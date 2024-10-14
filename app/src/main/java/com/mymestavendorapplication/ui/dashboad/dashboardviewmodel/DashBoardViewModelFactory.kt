package com.mymestavendorapplication.ui.dashboad.dashboardviewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class DashBoardViewModelFactory  @Inject constructor(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashBoardViewModel::class.java)) {
            return DashBoardViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
