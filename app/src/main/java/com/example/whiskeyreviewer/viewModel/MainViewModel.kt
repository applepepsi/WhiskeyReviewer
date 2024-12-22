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
import com.example.whiskeyreviewer.data.NavigationDrawerItems
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
class MainViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context
): ViewModel() {



    private val _selectedItem = mutableStateOf<ToolBarItems?>(null)
    val selectedItem: State<ToolBarItems?> = _selectedItem

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

    private val _currentNameFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.NameAscendingOrder)
    val currentNameFilter: State<WhiskeyFilterItems> = _currentNameFilter


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

    private val _loginResult=mutableStateOf(null)
    val loginResult: State<Boolean?> = _loginResult

    private val _getBackupCodeDialogState= mutableStateOf(false)
    val getBackupCodeDialogState: State<Boolean> = _getBackupCodeDialogState

    private val _insertBackupCodeDialogState= mutableStateOf(false)
    val insertBackupCodeDialogState: State<Boolean> = _insertBackupCodeDialogState


    private val _backupCodeResult=mutableStateOf<Boolean?>(null)
    val backupCodeResult: State<Boolean?> = _backupCodeResult

    private val _selectWhiskyDialogState=mutableStateOf<Boolean>(false)
    val selectWhiskyDialogState: State<Boolean> = _selectWhiskyDialogState


    private val _selectWhiskyText=mutableStateOf<String>("")
    val selectWhiskyText: State<String> = _selectWhiskyText

    private val _currentWhiskyFilterType=mutableStateOf<TapLayoutItems>(TapLayoutItems.AllWhiskey)
    val currentWhiskyFilterType: State<TapLayoutItems> = _currentWhiskyFilterType

    private val _currentWhiskyFilterDropDownState=mutableStateOf<Boolean>(false)
    val currentWhiskyFilterDropDownState: State<Boolean> = _currentWhiskyFilterDropDownState

    private val _currentCustomWhiskyDropDownState=mutableStateOf<Boolean>(false)
    val currentCustomWhiskyDropDownState: State<Boolean> = _currentCustomWhiskyDropDownState

    private val _selectCustomWhiskyDialogState=mutableStateOf<Boolean>(false)
    val selectCustomWhiskyDialogState: State<Boolean> = _selectCustomWhiskyDialogState


    private val _currentCustomWhiskyType=mutableStateOf<TapLayoutItems>(TapLayoutItems.AmericanWhiskey)
    val currentCustomWhiskyType: State<TapLayoutItems> = _currentCustomWhiskyType



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
            openDate = if (filterKey == WhiskeyFilterItems.OPEN_DATE) !_filterDropDownMenuState.value.openDate else false,
            name = if (filterKey == WhiskeyFilterItems.NAME) !_filterDropDownMenuState.value.name else false,
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

    fun toggleHomeFloatingActionButtonState(){
        _homeFloatingActionButtonState.value=!_homeFloatingActionButtonState.value
    }

    fun updateSelectReview(selectReview:SingleWhiskeyData) {

    }



    fun setSelectReviewData(reviewData: WhiskyReviewData){
        _selectWhiskyData.value=reviewData
    }

    fun tryLogin(ssaid: String) {

    }


    fun toggleDrawerDialogState(item: NavigationDrawerItems){
        when(item){
            NavigationDrawerItems.Backup->{
                _getBackupCodeDialogState.value=!_getBackupCodeDialogState.value
            }
            NavigationDrawerItems.InsertBackupCode->{
                _insertBackupCodeDialogState.value=!_insertBackupCodeDialogState.value
            }

            NavigationDrawerItems.Setting -> {

            }
        }
    }

    fun submitBackupCode(backupCode:String){
        //백업 코드 제출 후 결과 구현예정
        _backupCodeResult.value=false

    }
    fun resetSubmitBackupCodeResultState(){
        _backupCodeResult.value=null
    }

    fun toggleWhiskySelectDialogState(){
        _selectWhiskyDialogState.value=!_selectWhiskyDialogState.value
    }

    fun toggleCustomWhiskySelectDialogState(){
        _selectCustomWhiskyDialogState.value=!_selectCustomWhiskyDialogState.value
    }

    fun updateSelectWhiskey(text:String){
        Log.d("text",text)
        _selectWhiskyText.value=text
    }

    fun updateCurrentWhiskyFilterType(whisky:TapLayoutItems){
        _currentWhiskyFilterType.value=whisky
    }

    fun updateCurrentCustomWhiskyType(whisky:TapLayoutItems){
        _currentCustomWhiskyType.value=whisky
    }

    fun toggleWhiskyFilterDropDownMenuState(){
        _currentWhiskyFilterDropDownState.value=!_currentWhiskyFilterDropDownState.value
    }

    fun toggleCustomWhiskyDropDownMenuState(){
        _currentCustomWhiskyDropDownState.value=!_currentCustomWhiskyDropDownState.value
    }

    fun getWhiskeyData(){
        _homeSearchBarSText.value //검색
        _currentWhiskeyFilter.value //위스키 종류

        _currentDayFilter.value //작성일
        _currentScoreFilter.value //점수
        _currentOpenDateFilter.value //개봉일
        _currentNameFilter.value //이름순


    }

}
