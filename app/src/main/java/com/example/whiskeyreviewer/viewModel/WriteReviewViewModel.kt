package com.example.whiskeyreviewer.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.lifecycle.ViewModel
import com.example.nextclass.utils.SUCCESS_CODE
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.toolBar.TextAlignment
import com.example.whiskeyreviewer.component.toolBar.TextColors

import com.example.whiskeyreviewer.component.toolBar.TextStyleItems
import com.example.whiskeyreviewer.component.toolBar.TextStyleState
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.SubmitWhiskyData
import com.example.whiskeyreviewer.data.ToolBarItems
import com.example.whiskeyreviewer.data.UriData
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.repository.WriteReviewRepository
import com.example.whiskeyreviewer.utils.ImageConverter
import com.example.whiskeyreviewer.utils.TimeFormatter
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

    private val _selectedImageUri = mutableStateOf<List<UriData>>(emptyList())
    val selectedImageUri: State<List<UriData>> = _selectedImageUri

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



    private val _writeReviewData = mutableStateOf(WriteReviewData())
    val writeReviewData: State<WriteReviewData> = _writeReviewData

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

    private val _imageSelectorState= mutableStateOf(false)
    val imageSelectorState: State<Boolean> = _imageSelectorState

    private val _loadingState= mutableStateOf(false)
    val loadingState: State<Boolean> = _loadingState

    private val _reviewSaveResultState= mutableStateOf(false)
    val reviewSaveResultState: State<Boolean> = _reviewSaveResultState



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

    fun setSelectedImage(uris: List<UriData>) {


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
//        val oldWriteReviewImageList=_writeReviewData.value.imageList.toMutableList()
        val oldList=_selectedImageUri.value.toMutableList()
        Log.d("제거된 이미지", oldList.toString())
//        Log.d("제거된 이미지", oldWriteReviewImageList.toString())
        Log.d("제거된 이미지", index.toString())
        if (index in oldList.indices) {
            oldList.removeAt(index)
//            oldWriteReviewImageList.removeAt(index)
//            _writeReviewData.value=_writeReviewData.value.copy(imageList = oldWriteReviewImageList)
            _selectedImageUri.value = oldList
        }
        Log.d("제거된 이미지", _writeReviewData.value.imageList.toString())
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

        _writeReviewData.value=_writeReviewData.value.copy(
            content = richTextEditorState.toHtml()
        )
        Log.d("_writeReviewData", _writeReviewData.value.toString())
        val imageFiles=if(selectedImageUri.value.isNotEmpty()){
            ImageConverter.convertUrisToFiles(applicationContext,selectedImageUri.value)
        }else{
            null
        }


        if(_writeReviewData.value.open_date > LocalDate.now()){
            _errorToastState.value=true
            _errorToastMessage.value="현재 시간보다 이전 시간을 선택해 주세요"
            _errorToastIcon.value=R.drawable.fail_icon
        }else if(richTextEditorState.annotatedString.text.isBlank()){

            _errorToastState.value=true
            _errorToastMessage.value="내용을 입력해 주세요."
            _errorToastIcon.value=R.drawable.fail_icon
        }else{

            val submitWhiskyData=SubmitWhiskyData(
                _writeReviewData.value.content,
                _writeReviewData.value.is_anonymous,
                _writeReviewData.value.open_date.toString(),
                _writeReviewData.value.tags,
                _writeReviewData.value.score.toInt(),
                _writeReviewData.value.whiskey_uuid,
                _writeReviewData.value.imageList
            )

            Log.d("submitWhiskyData", submitWhiskyData.toString())
            _loadingState.value=true
            if(tag=="modify"){
                //수정 태그가 들어왔다면 수정으로 전송
                Log.d("수정 이미지2", imageFiles.toString())
                writeReviewRepository.reviewModify(
                    imageFiles =imageFiles,
                    reviewData = submitWhiskyData
                ){modifyResult->

                    if(modifyResult!=null){
                        if(modifyResult.code== SUCCESS_CODE){

                            toggleReviewSaveResult()
                        }else{
                            setErrorToastMessage(
                                icon=R.drawable.fail_icon,
                                text=""
                            )
                        }
                    }else{
                        setErrorToastMessage(
                            icon=R.drawable.fail_icon,
                            text="서버와의 연결 상태가 좋지 않습니다."
                        )
                    }
                    _loadingState.value=false
                    ImageConverter.clearCache(context = applicationContext)
                }
            }else{
                writeReviewRepository.reviewSave(
                    imageFiles =imageFiles,
                    reviewData = submitWhiskyData
                ){ saveResult->

                    _loadingState.value=false
                    if(saveResult!=null){
                        if(saveResult.code== SUCCESS_CODE){
                            toggleReviewSaveResult()
                        }else{
                            setErrorToastMessage(
                                icon=R.drawable.fail_icon,
                                text=""
                            )
                        }
                    }else{
                        setErrorToastMessage(
                            icon=R.drawable.fail_icon,
                            text="서버와의 연결 상태가 좋지 않습니다."
                        )
                    }
                    _loadingState.value=false
                }

            }

            Log.d("작성이미지", imageFiles.toString())
            Log.d("작성내용", _writeReviewData.value.toString())
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
        _writeReviewData.value=_writeReviewData.value.copy(
            open_date = selectDate
        )
    }


    fun togglePrivateState() {
        _writeReviewData.value=_writeReviewData.value.copy(
            is_anonymous = !_writeReviewData.value.is_anonymous
        )
    }

    fun updateCurrentTag(currentTag:String){

        if (currentTag.contains(" ") && currentTag.isNotEmpty()) {

            _writeReviewData.value = _writeReviewData.value.copy(
                tags = _writeReviewData.value.tags + currentTag
            )
            _currentTag.value=""
        } else {
            _currentTag.value=currentTag
        }
    }

    fun deleteTag(index: Int) {
        val tagList=_writeReviewData.value.tags.toMutableList()
        if (index in tagList.indices) {
            tagList.removeAt(index)
            _writeReviewData.value = _writeReviewData.value.copy(
                tags = tagList
            )
        }
    }

    fun toggleRatingScoreDialogState() {
        _scoreDialogState.value=!_scoreDialogState.value
    }

    fun updateScore(score:Double){
        _writeReviewData.value=_writeReviewData.value.copy(score=score)
    }

    fun setErrorToastMessage(icon: Int, text: String) {
        _errorToastState.value=true
        _errorToastMessage.value=text
        _errorToastIcon.value=icon
    }

    fun synchronizationWhiskyData(reviewData: WhiskyReviewData, whiskyName: String,uriList:List<UriData>?) {
        Log.d("uriList", uriList.toString())
        //todo 수정 기능 구현해야함
        _writeReviewData.value=_writeReviewData.value.copy(
            whiskey_uuid =reviewData.review_uuid,
            content = reviewData.content,
            is_anonymous = reviewData.is_anonymous,
            open_date = TimeFormatter.stringToLocalDate(reviewData.open_date),
            tags= reviewData.tags,
            score=reviewData.score,
//            imageList = whiskyData.imageList,
            whiskyName=whiskyName,
            imageList = reviewData.image_names ?: emptyList()
        )
        _selectedImageUri.value=uriList ?: emptyList()

        //todo 이미지 새로 동기화 하기
//        val stringList: List<Uri> = whiskyData.imageList.map { it.toUri() }
//        _selectedImageUri.value = stringList
    }

    //todo 새로운 병을 추가했을떄 한번 데이터를 리셋하고 시작해야함 지금 어떤걸 리셋해야할지 고민중 서버측에서 알려줘야함
    fun resetWriteReviewData(selectWhiskyData:SingleWhiskeyData){
        _writeReviewData.value=_writeReviewData.value.copy(
            whiskey_uuid = "",
            imageList = emptyList(),
            content = "",
        )
    }

    fun toggleImageSelectorState(state:Boolean?=null){
        if(state!=null){
            _imageSelectorState.value=state
        }else{
            _imageSelectorState.value=!_imageSelectorState.value
        }

    }

    fun toggleReviewSaveResult(){
        _reviewSaveResultState.value=!_reviewSaveResultState.value
    }
}
