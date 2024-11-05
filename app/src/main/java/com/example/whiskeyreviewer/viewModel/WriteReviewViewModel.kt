package com.example.whiskeyreviewer.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whiskeyreviewer.view.toolBar.ToolBarItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WriteReviewViewModel @Inject constructor(

): ViewModel() {

    private val _selectedItem = mutableStateOf<ToolBarItems?>(null)
    val selectedItem: State<ToolBarItems?> = _selectedItem

    fun selectItem(item: ToolBarItems) {

        _selectedItem.value = if (_selectedItem.value == item) null else item

    }



}
