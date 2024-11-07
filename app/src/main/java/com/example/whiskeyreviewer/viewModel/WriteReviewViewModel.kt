package com.example.whiskeyreviewer.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.whiskeyreviewer.view.toolBar.TextStyleItems
import com.example.whiskeyreviewer.view.toolBar.ToolBarItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WriteReviewViewModel @Inject constructor(

): ViewModel() {

    private val _selectedItem = mutableStateOf<ToolBarItems?>(null)
    val selectedItem: State<ToolBarItems?> = _selectedItem

    private val _selectedTextStyleItemState = mutableStateOf<Boolean?>(null)
    val selectedTextStyleItemState: State<Boolean?> = _selectedTextStyleItemState

    private val _selectedTextStyleItem = mutableStateOf<TextStyleItems?>(null)
    val selectedTextStyleItem: State<TextStyleItems?> = _selectedTextStyleItem

    private val _selectedImageUri = mutableStateOf<Uri?>(null)
    val selectedImageUri: State<Uri?> = _selectedImageUri

    private val _boldSelected = mutableStateOf(false)
    val boldSelected: State<Boolean> = _boldSelected

    private val _italicSelected = mutableStateOf(false)
    val italicSelected: State<Boolean> = _italicSelected

    private val _underlineSelected = mutableStateOf(false)
    val underlineSelected: State<Boolean> = _underlineSelected

    private val _titleSelected = mutableStateOf(false)
    val titleSelected: State<Boolean> = _titleSelected

    private val _subtitleSelected = mutableStateOf(false)
    val subtitleSelected: State<Boolean> = _subtitleSelected

    private val _textColorSelected = mutableStateOf(false)
    val textColorSelected: State<Boolean> = _textColorSelected

    private val _linkSelected = mutableStateOf(false)
    val linkSelected: State<Boolean> = _linkSelected

    private val _alignmentSelected = mutableStateOf(0)
    val alignmentSelected: State<Int> = _alignmentSelected

    private val _textSize = mutableStateOf(15)
    val textSize: State<Int> = _textSize

    fun selectItem(item: ToolBarItems) {

        if (item is ToolBarItems.Picture) {
            _selectedItem.value= null
            _selectedItem.value = item
        } else {
            _selectedItem.value = if (_selectedItem.value == item) null else item
        }
    }

    fun selectTextStyleItem(item: TextStyleItems) {
        Log.d("아이템", item.toString())
        _selectedTextStyleItem.value = if(_selectedTextStyleItem.value==item) null else item
    }

    fun setSelectedImage(uri: Uri) {
        Log.d("이미지", uri.toString())
        _selectedImageUri.value = uri
    }

    fun updateTextSize(textSize: Int) {
        _textSize.value=textSize
    }

}
