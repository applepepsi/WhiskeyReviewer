package com.example.whiskeyreviewer.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.toolBar.TextAlignment
import com.example.whiskeyreviewer.component.toolBar.TextColors

import com.example.whiskeyreviewer.component.toolBar.TextStyleItems
import com.example.whiskeyreviewer.component.toolBar.TextStyleState
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.ToolBarItems
import com.example.whiskeyreviewer.data.WhiskeyReviewData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.repository.MainRepository
import com.example.whiskeyreviewer.repository.WriteReviewRepository
import com.example.whiskeyreviewer.utils.ImageConverter
import com.mohamedrejeb.richeditor.model.RichTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val writeReviewRepository: WriteReviewRepository
): ViewModel() {



    private val _selectedItem = mutableStateOf<ToolBarItems?>(null)
    val selectedItem: State<ToolBarItems?> = _selectedItem

    private val _selectedTextStyleItemState = mutableStateOf<Boolean?>(null)
    val selectedTextStyleItemState: State<Boolean?> = _selectedTextStyleItemState

    private val _selectedTextStyleItem = mutableStateOf<TextStyleItems?>(null)
    val selectedTextStyleItem: State<TextStyleItems?> = _selectedTextStyleItem

    private val _selectedImageUri = mutableStateOf<List<Uri>>(emptyList())
    val selectedImageUri: State<List<Uri>> = _selectedImageUri

    private val _textStyleState = mutableStateOf(TextStyleState())
    val textStyleState: State<TextStyleState> = _textStyleState

    private val _alignmentSelected = mutableStateOf(0)
    val alignmentSelected: State<Int> = _alignmentSelected

    private val _textSize = mutableStateOf(15)
    val textSize: State<Int> = _textSize

    private val _textColor = mutableStateOf(TextColors(Color(0xFF000000),0))
    val textColor: State<TextColors> = _textColor

    private val _textBackgroundColor = mutableStateOf(TextColors(Color(0xFFFFFFFF),0))
    val textBackgroundColor: State<TextColors> = _textBackgroundColor

    private val _textColorIndex= mutableStateOf<Int?>(null)
    val textColorIndex: State<Int?> = _textColorIndex

    private val _textBackgroundColorIndex= mutableStateOf<Int?>(null)
    val textBackgroundColorIndex: State<Int?> = _textBackgroundColorIndex

    private val _selectDateBottomSheetState= mutableStateOf<Boolean>(false)
    val selectDateBottomSheetState: State<Boolean> = _selectDateBottomSheetState



    private val _writeReviewDate = mutableStateOf(WriteReviewData())
    val writeReviewDate: State<WriteReviewData> = _writeReviewDate

    private val _currentTag=mutableStateOf<String>("")
    val currentTag: State<String> = _currentTag

    private val _tagList=mutableStateOf<List<String>>(emptyList())
    val tagList: State<List<String>> = _tagList

    private val _score= mutableDoubleStateOf(1.0)
    val score: State<Double> = _score

    private val _scoreDialogState= mutableStateOf(false)
    val scoreDialogState: State<Boolean> = _scoreDialogState

    private val _errorToastState=mutableStateOf<Boolean>(false)
    val errorToastState: State<Boolean> = _errorToastState

    private val _errorToastMessage=mutableStateOf<String>("")
    val errorToastMessage: State<String> = _errorToastMessage

    private val _errorToastIcon=mutableStateOf<Int>(R.drawable.fail_icon)
    val errorToastIcon: State<Int> = _errorToastIcon


    fun selectItem(item: ToolBarItems) {
        Log.d("아이템", item.toString())
        if (item is ToolBarItems.Picture) {
            _selectedItem.value = item
        } else {
            _selectedItem.value = if (_selectedItem.value == item) null else item
        }
    }

    fun resetItem(){
        _selectedItem.value=null
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

    fun resetTextStyleItem(){
        _selectedTextStyleItem.value=null
        _textStyleState.value = _textStyleState.value.copy(textSize = false, textColor = false, textBackgroundColor = false)
    }

    fun setSelectedImage(uris: List<Uri>) {


        val currentSize = _selectedImageUri.value.size
        val maxItems = 3

        Log.d("이미지들",uris.toString())

        if (currentSize >= maxItems) {
            setErrorToastMessage(
                icon = R.drawable.fail_icon,
                text = "사진은 최대 세 장까지 선택할 수 있습니다."
            )
            return
        }

        val availableSpace = maxItems - currentSize

        val itemsToAdd =
            if (uris.size > availableSpace) {

            val items = uris.take(availableSpace)

            setErrorToastMessage(
                icon = R.drawable.fail_icon,
                text = "사진은 최대 세 장까지 선택할 수 있습니다."
            )
            items
        } else {
            uris
        }

        _selectedImageUri.value += itemsToAdd
    }

    fun deleteImage(index: Int){
        val oldList=_selectedImageUri.value.toMutableList()
        if (index in oldList.indices) {
            oldList.removeAt(index)
            _selectedImageUri.value = oldList
        }
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


    fun exportReview(richTextEditorState: RichTextState,tag:String?=null) {

        Log.d("richTextEditorState",richTextEditorState.toHtml())
        Log.d("richTextEditorState",richTextEditorState.annotatedString.text)

        _writeReviewDate.value=_writeReviewDate.value.copy(
            content = richTextEditorState.toHtml()
        )

        val imageFiles=if(selectedImageUri.value.isNotEmpty()){
            ImageConverter.convertUrisToFiles(applicationContext,selectedImageUri.value)
        }else{
            null
        }


        if(_writeReviewDate.value.open_date > LocalDate.now()){
            _errorToastState.value=true
            _errorToastMessage.value="현재 시간보다 이전 시간을 선택해 주세요"
            _errorToastIcon.value=R.drawable.fail_icon
        }else if(richTextEditorState.annotatedString.text.isBlank()){

            _errorToastState.value=true
            _errorToastMessage.value="내용을 입력해 주세요."
            _errorToastIcon.value=R.drawable.fail_icon
        }else{

            if(tag=="modify"){
                //수정 태그가 들어왔다면 수정으로 전송
            }else{
                writeReviewRepository.reviewSave(
                    imageFiles=imageFiles,
                    reviewData = writeReviewDate.value
                ){ saveResult->

                }
            }

            Log.d("작성이미지", imageFiles.toString())
            Log.d("작성내용", writeReviewDate.value.toString())
        }
//
//
//        _writeReviewDate.value
    }

    fun resetToastErrorState() {
        _errorToastState.value=false
        _errorToastMessage.value=""
    }
    fun toggleDateSelectBottomSheetState(){
        _selectDateBottomSheetState.value=!_selectDateBottomSheetState.value
    }

    fun updateSelectDate(selectDate:LocalDate){
        _writeReviewDate.value=_writeReviewDate.value.copy(
            open_date = selectDate
        )
    }


    fun togglePrivateState() {
        _writeReviewDate.value=writeReviewDate.value.copy(
            is_anonymous = !_writeReviewDate.value.is_anonymous
        )
    }

    fun updateCurrentTag(currentTag:String){

        if (currentTag.contains(" ") && currentTag.isNotEmpty()) {

            _writeReviewDate.value = _writeReviewDate.value.copy(
                tags = _writeReviewDate.value.tags + currentTag
            )
            _currentTag.value=""
        } else {
            _currentTag.value=currentTag
        }
    }

    fun deleteTag(index: Int) {
        val tagList=_writeReviewDate.value.tags.toMutableList()
        if (index in tagList.indices) {
            tagList.removeAt(index)
            _writeReviewDate.value = _writeReviewDate.value.copy(
                tags = tagList
            )
        }
    }

    fun toggleRatingScoreDialogState() {
        _scoreDialogState.value=!_scoreDialogState.value
    }

    fun updateScore(score:Double){
        _writeReviewDate.value=_writeReviewDate.value.copy(score=score)
    }

    fun setErrorToastMessage(icon: Int, text: String) {
        _errorToastState.value=true
        _errorToastMessage.value=text
        _errorToastIcon.value=icon
    }

    fun synchronizationWhiskyData(whiskyData: WhiskeyReviewData, whiskyName: String, bottleNum: Int) {
        //todo 수정 기능 구현해야함
        _writeReviewDate.value=_writeReviewDate.value.copy(
            whiskey_uuid = "",
            content = whiskyData.content,
            is_anonymous = whiskyData.is_anonymous,
            open_date = whiskyData.open_date,
            tags= whiskyData.tags,
            score=whiskyData.score,
            bottle_num = bottleNum,
//            imageList = whiskyData.imageList,
            whiskyName=whiskyName
        )
        val stringList: List<Uri> = whiskyData.imageList.map { it.toUri() }
        _selectedImageUri.value = stringList
    }

    //todo 새로운 병을 추가했을떄 한번 데이터를 리셋하고 시작해야함 지금 어떤걸 리셋해야할지 고민중 서버측에서 알려줘야함
    fun resetWriteReviewData(selectWhiskyData:SingleWhiskeyData){
        _writeReviewDate.value=_writeReviewDate.value.copy(
            whiskey_uuid = "",
            imageList = emptyList(),
            content = "",
        )
    }

}
