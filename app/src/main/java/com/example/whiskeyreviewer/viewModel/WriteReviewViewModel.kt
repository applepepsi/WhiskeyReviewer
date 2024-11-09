package com.example.whiskeyreviewer.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import com.example.whiskeyreviewer.utils.ColorCompare
import com.example.whiskeyreviewer.utils.ColorCompare.colorsCompare
import com.example.whiskeyreviewer.view.toolBar.TextAlignment
import com.example.whiskeyreviewer.view.toolBar.TextColors

import com.example.whiskeyreviewer.view.toolBar.TextStyleItems
import com.example.whiskeyreviewer.view.toolBar.TextStyleState
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

    private val _textStyleState = mutableStateOf(TextStyleState())
    val textStyleState: State<TextStyleState> = _textStyleState

    private val _alignmentSelected = mutableStateOf(0)
    val alignmentSelected: State<Int> = _alignmentSelected

    private val _textSize = mutableStateOf(15)
    val textSize: State<Int> = _textSize

    private val _textColor = mutableStateOf(TextColors(Color(0xFF000000),0))
    val textColor: State<TextColors> = _textColor

    private val _textBackgroundColor = mutableStateOf(TextColors(Color(0xFF000000),0))
    val textBackgroundColor: State<TextColors> = _textBackgroundColor

    private val _textColorIndex= mutableStateOf<Int?>(null)
    val textColorIndex: State<Int?> = _textColorIndex

    private val _textBackgroundColorIndex= mutableStateOf<Int?>(null)
    val textBackgroundColorIndex: State<Int?> = _textBackgroundColorIndex
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
        _selectedTextStyleItem.value =
            if(_selectedTextStyleItem.value==item){
                null
            }else{
                when(item){
                    TextStyleItems.TEXT_SIZE->{
                        _textStyleState.value = _textStyleState.value.copy(textColor = false, textBackgroundColor = false)
                    }
                    TextStyleItems.TEXT_COLOR->{
                        _textStyleState.value = _textStyleState.value.copy(textSize = false, textBackgroundColor = false)
                    }
                    TextStyleItems.TEXT_BACKGROUND_COLOR->{
                        _textStyleState.value = _textStyleState.value.copy(textSize = false, textColor = false)
                    }
                }
                item
            }
    }

    fun setSelectedImage(uri: Uri) {
        Log.d("이미지", uri.toString())
        _selectedImageUri.value = uri
    }

    fun updateTextSize(textSize: Int) {
        Log.d("사이즈", textSize.toString())
        _textSize.value=textSize
    }

    fun updateTextColor(color: Color,index:Int){
        Log.d("컬러1", color.toString())
        _textColor.value=_textColor.value.copy(color=color,index=index)
    }

    fun updateTextBackgroundColor(color: Color,index:Int){
        Log.d("컬러2", color.toString())
        _textBackgroundColor.value=_textBackgroundColor.value.copy(color=color,index=index)
    }

    fun toggleBold() {
        _textStyleState.value = _textStyleState.value.copy(bold = !_textStyleState.value.bold)
    }

    fun toggleItalic() {
        _textStyleState.value = _textStyleState.value.copy(italic = !_textStyleState.value.italic)
    }

    fun toggleUnderline() {
        _textStyleState.value = _textStyleState.value.copy(underline = !_textStyleState.value.underline)
    }

    fun toggleTextSize() {
        _textStyleState.value = _textStyleState.value.copy(textSize = !_textStyleState.value.textSize)
    }

    fun toggleTextColor() {
        _textStyleState.value = _textStyleState.value.copy(textColor = !_textStyleState.value.textColor)
    }

    fun toggleTextBackgroundColor() {
        _textStyleState.value = _textStyleState.value.copy(textBackgroundColor = !_textStyleState.value.textBackgroundColor)
    }

    fun toggleStartAlign() = toggleAlign(TextAlignment.START)
    fun toggleMidAlign() = toggleAlign(TextAlignment.MID)
    fun toggleEndAlign() = toggleAlign(TextAlignment.END)

    private fun toggleAlign(alignment: TextAlignment) {
        resetAlignments(alignment)
        _textStyleState.value = when (alignment) {
            TextAlignment.START -> _textStyleState.value.copy(textStartAlign = !_textStyleState.value.textStartAlign)
            TextAlignment.MID -> _textStyleState.value.copy(textMidAlign = !_textStyleState.value.textMidAlign)
            TextAlignment.END -> _textStyleState.value.copy(textEndAlign = !_textStyleState.value.textEndAlign)
        }
    }

    private fun resetAlignments(except: TextAlignment) {
        _textStyleState.value = _textStyleState.value.copy(
            textStartAlign = except == TextAlignment.START && _textStyleState.value.textStartAlign,
            textMidAlign = except == TextAlignment.MID && _textStyleState.value.textMidAlign,
            textEndAlign = except == TextAlignment.END && _textStyleState.value.textEndAlign
        )
    }

    fun updateTextColorIndex(index: Int) {
        Log.d("인덱스",index.toString())
        _textColorIndex.value=index
    }

    fun updateTextBackgroundColorIndex(index: Int){
        Log.d("인덱스2",index.toString())
        _textBackgroundColorIndex.value=index
    }


    //커서를 옮겼을 때 해당 줄에 적용된 텍스트 효과를 받기 위해
    fun updateSpanStyle(currentRichTextState: SpanStyle) {
        Log.d("currentRichTextState",currentRichTextState.fontSize.toString())
//        updateTextSize(currentRichTextState.fontSize.value.toInt())
//        updateTextColor(currentRichTextState.color)
//        updateTextBackgroundColor(currentRichTextState.background)

        //커서를 옮겼을 때 아이콘 활성화 여부
        _textStyleState.value = _textStyleState.value.copy(
            bold = currentRichTextState.fontWeight != null,
            italic = currentRichTextState.fontStyle != null,
            underline = currentRichTextState.textDecoration != null
        )


        //색 자동 변경 기능은 임시 보류
//        colorsCompare(currentRichTextState.color, textColor.value.color)?.let {
//            Log.d("색",it.toString() )
//            updateTextColor(it.color, it.index)
//        }

//        Log.d("색",colorsCompare(currentRichTextState.color, textColor.value.color).toString() )

//        colorsCompare(currentRichTextState.background, textBackgroundColor.value.color)?.let {
//            updateTextBackgroundColor(it.color, it.index)
//        }



    }


}
