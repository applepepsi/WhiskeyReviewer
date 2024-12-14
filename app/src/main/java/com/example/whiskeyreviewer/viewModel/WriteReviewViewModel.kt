package com.example.whiskeyreviewer.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.lifecycle.ViewModel
import com.example.nextclass.utils.RECENT_SEARCH_REVIEW_TEXT
import com.example.nextclass.utils.RECENT_SEARCH_WHISKEY_TEXT
import com.example.whiskeyreviewer.component.toolBar.TextAlignment
import com.example.whiskeyreviewer.component.toolBar.TextColors

import com.example.whiskeyreviewer.component.toolBar.TextStyleItems
import com.example.whiskeyreviewer.component.toolBar.TextStyleState
import com.example.whiskeyreviewer.data.ToolBarItems
import com.example.whiskeyreviewer.data.FilterDropDownMenuState
import com.example.whiskeyreviewer.data.MyReviewFilterDropDownMenuState
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.ReviewData
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TapLayoutItems
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.utils.ImageConverter
import com.mohamedrejeb.richeditor.model.RichTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context
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


    private val _myReviewList = mutableStateOf<List<SingleWhiskeyData>>(emptyList())
    val myReviewList: State<List<SingleWhiskeyData>> = _myReviewList

    private val _currentWhiskeyFilter = mutableStateOf<TapLayoutItems>(TapLayoutItems.AllWhiskey)
    val currentWhiskeyFilter: State<TapLayoutItems> = _currentWhiskeyFilter

    private val _currentDayFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.DayAscendingOrder)
    val currentDayFilter: State<WhiskeyFilterItems> = _currentDayFilter

    private val _currentScoreFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.ScoreAscendingOrder)
    val currentScoreFilter: State<WhiskeyFilterItems> = _currentScoreFilter

    private val _currentOpenDateFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.OpenDateAscendingOrder)
    val currentOpenDateFilter: State<WhiskeyFilterItems> = _currentOpenDateFilter

    private val _filterDropDownMenuState = mutableStateOf(FilterDropDownMenuState())
    val filterDropDownMenuState: State<FilterDropDownMenuState> = _filterDropDownMenuState

    private val _writeReviewDate = mutableStateOf(WriteReviewData())
    val writeReviewDate: State<WriteReviewData> = _writeReviewDate

    private val _homeFloatingActionButtonState = mutableStateOf(false)
    val homeFloatingActionButtonState: State<Boolean> = _homeFloatingActionButtonState

    private val _myWhiskyFilterDropDownMenuState = mutableStateOf(MyReviewFilterDropDownMenuState())
    val myWhiskyFilterDropDownMenuState: State<MyReviewFilterDropDownMenuState> = _myWhiskyFilterDropDownMenuState

    private val _currentMyReviewBottleNumFilter = mutableIntStateOf(10)
    val currentMyReviewBottleNumFilter: State<Int> = _currentMyReviewBottleNumFilter

    private val _myReviewData = mutableStateOf<ReviewData>(ReviewData())
    val myReviewData: State<ReviewData> = _myReviewData

    private val _currentMyReviewDayFilter = mutableStateOf<MyReviewFilterItems>(MyReviewFilterItems.New)
    val currentMyReviewDayFilter: State<MyReviewFilterItems> = _currentMyReviewDayFilter

    private val _currentMyReviewTypeFilter = mutableStateOf<MyReviewFilterItems>(MyReviewFilterItems.Review)
    val currentMyReviewTypeFilter: State<MyReviewFilterItems> = _currentMyReviewTypeFilter

    private val _currentTag=mutableStateOf<String>("")
    val currentTag: State<String> = _currentTag

    private val _tagList=mutableStateOf<List<String>>(emptyList())
    val tagList: State<List<String>> = _tagList

    private val _score= mutableDoubleStateOf(1.0)
    val score: State<Double> = _score

    private val _scoreDialogState= mutableStateOf(false)
    val scoreDialogState: State<Boolean> = _scoreDialogState

    private val _myReviewDataList=mutableStateOf<List<WriteReviewData>>(
        listOf(
            WriteReviewData(
                content = "스타일 A",
                is_anonymous = false,
                open_date = LocalDate.of(2024, 1, 10),
                tags = listOf(""),
                score = 4.0
            ),
            WriteReviewData(
                content = "스타일 B",
                is_anonymous = true,
                open_date = LocalDate.of(2025, 2, 15),
                tags = listOf(""),
                score = 3.0
            ),
            WriteReviewData(
                content = "스타일 C",
                is_anonymous = false,
                open_date = LocalDate.of(2022, 3, 20),
                tags = listOf(""),
                score = 5.0
            ),
            WriteReviewData(
                content = "스타일 D",
                is_anonymous = true,
                open_date = LocalDate.of(2021, 4, 5),
                tags = listOf(""),
                score = 2.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 4.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 4.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 2.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 3.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 2.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 5.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 3.0
            ),
            WriteReviewData(
                content = "스타일 E",
                is_anonymous = false,
                open_date = LocalDate.of(2020, 5, 30),
                tags = listOf(""),
                score = 1.0
            ),
        )
    )
    val myReviewDataList: State<List<WriteReviewData>> = _myReviewDataList

    private val _homeSearchBarState= mutableStateOf(false)
    val homeSearchBarState: State<Boolean> = _homeSearchBarState

    private val _homeSearchBarSText= mutableStateOf("")
    val homeSearchBarSText: State<String> = _homeSearchBarSText

    private val _drawerSearchBarText= mutableStateOf("")
    val drawerSearchBarText: State<String> = _drawerSearchBarText

    private val _recentSearchReviewTextList=mutableStateOf(listOf<String>())
    val recentSearchReviewTextList: State<List<String>> = _recentSearchReviewTextList

    private val _recentSearchWhiskeyTextList=mutableStateOf(listOf<String>())
    val recentSearchWhiskeyTextList: State<List<String>> = _recentSearchWhiskeyTextList

    private val _whiskeySearchBarState= mutableStateOf(true)
    val whiskeySearchBarState: State<Boolean> = _whiskeySearchBarState

    private val _selectWhiskyData=mutableStateOf(WhiskyReviewData())
    val selectWhiskyData: State<WhiskyReviewData> = _selectWhiskyData



    fun setRecentSearchTextList(recentSearchWordList: MutableList<String>,type:String) {
        Log.d("최근검색어", recentSearchWordList.toString())
        when(type){
            RECENT_SEARCH_REVIEW_TEXT->{
                _recentSearchReviewTextList.value=recentSearchWordList
            }
            RECENT_SEARCH_WHISKEY_TEXT->{
                _recentSearchWhiskeyTextList.value=recentSearchWordList
            }
        }
    }

    fun updateSearchWord(searchWord:String,type:String){
        when(type){
            RECENT_SEARCH_REVIEW_TEXT->{

            }
            RECENT_SEARCH_WHISKEY_TEXT->{

            }
        }
    }




    fun toggleHomeSearchBarState(){
        _homeSearchBarState.value=!_homeSearchBarState.value
    }

    fun updateHomeSearchBarText(newSearchText:String){
        _homeSearchBarSText.value=newSearchText
        //실시간 검색 구현 예정
    }

    fun updateDrawerSearchBarText(newSearchText:String){
        _drawerSearchBarText.value=newSearchText
        //실시간 검색 구현 예정
    }

    fun toggleDrawerSearchBarState(state:Boolean?=null){
        if(state!=null){
            _whiskeySearchBarState.value=state
        }else{
            _whiskeySearchBarState.value=!_whiskeySearchBarState.value
        }
    }

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

    fun setSelectedImage(uri: List<Uri>) {

        _selectedImageUri.value += uri
        Log.d("이미지", _selectedImageUri.value.toString())
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

    fun exportReview(richTextEditorState: RichTextState,) {

        Log.d("richTextEditorState",richTextEditorState.toHtml())
        Log.d("richTextEditorState",richTextEditorState.annotatedString.text)

        val imageFiles=if(selectedImageUri.value.isNotEmpty()){
            ImageConverter.convertUrisToFiles(applicationContext,selectedImageUri.value)
        }else{
            null
        }
        Log.d("imageFiles", imageFiles.toString())
        _writeReviewDate.value
    }


    fun toggleDateSelectBottomSheetState(){
        _selectDateBottomSheetState.value=!_selectDateBottomSheetState.value
    }

    fun updateSelectDate(selectDate:LocalDate){
        _writeReviewDate.value=_writeReviewDate.value.copy(
            open_date = selectDate
        )
    }

    fun getFilteredWhiskeyReview(currentWhiskey: TapLayoutItems) {
        _currentWhiskeyFilter.value=currentWhiskey
    }

    fun updateFilter( filterKey: WhiskeyFilterItems) {
        when(filterKey.type){
            WhiskeyFilterItems.DAY->_currentDayFilter.value=filterKey
            WhiskeyFilterItems.SCORE->_currentScoreFilter.value=filterKey
            WhiskeyFilterItems.OPEN_DATE->_currentOpenDateFilter.value=filterKey
        }
    }

    fun toggleFilterDropDownMenuState(filterKey: String) {

        _filterDropDownMenuState.value = _filterDropDownMenuState.value.copy(
            day = if (filterKey == WhiskeyFilterItems.DAY) !_filterDropDownMenuState.value.day else false,
            score = if (filterKey == WhiskeyFilterItems.SCORE) !_filterDropDownMenuState.value.score else false,
            openDate = if (filterKey == WhiskeyFilterItems.OPEN_DATE) !_filterDropDownMenuState.value.openDate else false
        )
    }

    fun toggleMyWhiskeyReviewDropDownMenuState(filterKey: String) {

        _myWhiskyFilterDropDownMenuState.value = _myWhiskyFilterDropDownMenuState.value.copy(
            bottleNum = if (filterKey == MyReviewFilterItems.BOTTLE_NUM) !_myWhiskyFilterDropDownMenuState.value.bottleNum else false,
            day = if (filterKey == MyReviewFilterItems.DAY) !_myWhiskyFilterDropDownMenuState.value.day else false,
            reviewType = if (filterKey == MyReviewFilterItems.REVIEW_TYPE) !_myWhiskyFilterDropDownMenuState.value.reviewType else false,
        )
    }

    fun updateMyWhiskeyFilter( filterKey: MyReviewFilterItems) {
        when(filterKey.type){

            MyReviewFilterItems.DAY->_currentMyReviewDayFilter.value=filterKey
            MyReviewFilterItems.REVIEW_TYPE->_currentMyReviewTypeFilter.value=filterKey
        }
    }

    fun updateMyBottleNumFilter( filterKey: Int) {
        _currentMyReviewBottleNumFilter.intValue=filterKey
    }

    fun togglePrivateState() {
        _writeReviewDate.value=writeReviewDate.value.copy(
            is_anonymous = !_writeReviewDate.value.is_anonymous
        )
    }

    fun toggleHomeFloatingActionButtonState(){
        _homeFloatingActionButtonState.value=!_homeFloatingActionButtonState.value
    }

    fun updateSelectReview(selectReview:SingleWhiskeyData) {

    }

    fun updateCurrentTag(currentTag:String){

        if (currentTag.contains(" ") && currentTag.isNotEmpty()) {
            _tagList.value+=currentTag
            _currentTag.value=""
        } else {
            _currentTag.value=currentTag
        }
    }

    fun deleteTag(index: Int) {
        val tagList=_tagList.value.toMutableList()
        if (index in tagList.indices) {
            tagList.removeAt(index)
            _tagList.value = tagList
        }
    }

    fun toggleRatingScoreDialogState() {
        _scoreDialogState.value=!_scoreDialogState.value
    }

    fun updateScore(score:Double){
        _score.value=score
    }

    fun setSelectReviewData(reviewData: WhiskyReviewData){
        _selectWhiskyData.value=reviewData
    }
}
