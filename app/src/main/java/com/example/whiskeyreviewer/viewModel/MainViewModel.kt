package com.example.whiskeyreviewer.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map

import com.example.nextclass.utils.RECENT_SEARCH_REVIEW_TEXT
import com.example.nextclass.utils.RECENT_SEARCH_WHISKEY_TEXT
import com.example.nextclass.utils.SUCCESS_CODE
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.data.AddImageTag
import com.example.whiskeyreviewer.data.BackupCodeData
import com.example.whiskeyreviewer.data.CustomWhiskyData

import com.example.whiskeyreviewer.data.ToolBarItems
import com.example.whiskeyreviewer.data.FilterDropDownMenuState
import com.example.whiskeyreviewer.data.ImageSelectState
import com.example.whiskeyreviewer.data.ImageSelectType
import com.example.whiskeyreviewer.data.MyReviewFilterDropDownMenuState
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.NavigationDrawerItems
import com.example.whiskeyreviewer.data.OtherUserReviewSearchText
import com.example.whiskeyreviewer.data.ReviewData
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TapLayoutItems
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.MyWhiskyFilterData
import com.example.whiskeyreviewer.data.ReviewFilterData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.data.pagingResponse.ImageState
import com.example.whiskeyreviewer.data.pagingResponse.LikeState
import com.example.whiskeyreviewer.repository.MainRepository
import com.example.whiskeyreviewer.utils.ImageConverter
import com.example.whiskeyreviewer.utils.TokenManager
import com.example.whiskeyreviewer.utils.WhiskyLanguageTransfer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val mainRepository: MainRepository
): ViewModel() {



    private val _selectedItem = mutableStateOf<ToolBarItems?>(null)
    val selectedItem: State<ToolBarItems?> = _selectedItem

    private val _myWhiskyList = mutableStateOf<List<SingleWhiskeyData>>(
        listOf(
//            SingleWhiskeyData(
//                name="test1",
//                category=null,
//                strength=23.0,
//                score=1.0,
//                reg_date="2025-02-05T19:38:12.493258",
//                release_year=null,
//                photo_url="/home/ubuntu/file_images/f8ad4f54-136d-484d-b09b-da8e961bf6f0/1000000092.jpeg",
//                whisky_uuid="d9323bf8-ff49-401f-b41e-e3596f81c591",
//                mod_date=null
//            ),
//            SingleWhiskeyData(
//                name="test1",
//                category=null,
//                strength=23.0,
//                score=1.0,
//                reg_date="2025-02-05T19:38:12.493258",
//                release_year=null,
//                photo_url="/home/ubuntu/file_images/f8ad4f54-136d-484d-b09b-da8e961bf6f0/1000000092.jpeg",
//                whisky_uuid="d9323bf8-ff49-401f-b41e-e3596f81c591",
//                mod_date=null
//            ),
//            SingleWhiskeyData(
//                name="test1",
//                category=null,
//                strength=23.0,
//                score=1.0,
//                reg_date="2025-02-05T19:38:12.493258",
//                release_year=null,
//                photo_url="/home/ubuntu/file_images/f8ad4f54-136d-484d-b09b-da8e961bf6f0/1000000092.jpeg",
//                whisky_uuid="d9323bf8-ff49-401f-b41e-e3596f81c591",
//                mod_date=null
//            ),
//            SingleWhiskeyData(
//                name="test1",
//                category=null,
//                strength=23.0,
//                score=1.0,
//                reg_date="2025-02-05T19:38:12.493258",
//                release_year=null,
//                photo_url="/home/ubuntu/file_images/f8ad4f54-136d-484d-b09b-da8e961bf6f0/1000000092.jpeg",
//                whisky_uuid="d9323bf8-ff49-401f-b41e-e3596f81c591",
//                mod_date=null
//            ),
//            SingleWhiskeyData(
//                name="test1",
//                category=null,
//                strength=23.0,
//                score=1.0,
//                reg_date="2025-02-05T19:38:12.493258",
//                release_year=null,
//                photo_url="/home/ubuntu/file_images/f8ad4f54-136d-484d-b09b-da8e961bf6f0/1000000092.jpeg",
//                whisky_uuid="d9323bf8-ff49-401f-b41e-e3596f81c591",
//                mod_date=null
//            )
        )
//        emptyList()
    )
    val myWhiskyList: State<List<SingleWhiskeyData>> = _myWhiskyList

    private val _reviewList = mutableStateOf<List<SingleWhiskeyData>>(
        listOf(

        )
    )
    val reviewList: State<List<SingleWhiskeyData>> = _reviewList

//    private val _currentWhiskeyFilter = mutableStateOf<TapLayoutItems>(TapLayoutItems.AllWhiskey)
//    val currentWhiskeyFilter: State<TapLayoutItems> = _currentWhiskeyFilter

//    private val _currentDayFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.DayAscendingOrder)
//    val currentDayFilter: State<WhiskeyFilterItems> = _currentDayFilter

//    private val _currentScoreFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.ScoreAscendingOrder)
//    val currentScoreFilter: State<WhiskeyFilterItems> = _currentScoreFilter

    private val _currentOpenDateFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.OpenDateAscendingOrder)
    val currentOpenDateFilter: State<WhiskeyFilterItems> = _currentOpenDateFilter

//    private val _currentNameFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.NameAscendingOrder)
//    val currentNameFilter: State<WhiskeyFilterItems> = _currentNameFilter

    private val _currentHomeFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.DayAscendingOrder)
    val currentHomeFilter: State<WhiskeyFilterItems> = _currentHomeFilter

    private val _currentReviewFilter = mutableStateOf<WhiskeyFilterItems>(WhiskeyFilterItems.VoteAscendingOrder)
    val currentReviewFilter: State<WhiskeyFilterItems> = _currentReviewFilter

    private val _myWhiskyFilterDropDownMenuState = mutableStateOf(FilterDropDownMenuState())
    val myWhiskyFilterDropDownMenuState: State<FilterDropDownMenuState> = _myWhiskyFilterDropDownMenuState

    private val _homeFilterDropDownMenuState = mutableStateOf(false)
    val homeFilterDropDownMenuState: State<Boolean> = _homeFilterDropDownMenuState

    private val _writeReviewDate = mutableStateOf(WriteReviewData())
    val writeReviewDate: State<WriteReviewData> = _writeReviewDate

    private val _homeFloatingActionButtonState = mutableStateOf(false)
    val homeFloatingActionButtonState: State<Boolean> = _homeFloatingActionButtonState

    private val _myReviewFilterDropDownMenuState = mutableStateOf(MyReviewFilterDropDownMenuState())
    val myReviewFilterDropDownMenuState: State<MyReviewFilterDropDownMenuState> = _myReviewFilterDropDownMenuState

    private val _currentMyReviewBottleNumFilter = mutableIntStateOf(1)
    val currentMyReviewBottleNumFilter: State<Int> = _currentMyReviewBottleNumFilter

    private val _currentMyReviewBottleNum = mutableIntStateOf(1)
    val currentMyReviewBottleNum: State<Int> = _currentMyReviewBottleNum

    private val _myReviewData = mutableStateOf<ReviewData>(ReviewData())
    val myReviewData: State<ReviewData> = _myReviewData

    private val _currentMyReviewDayFilter = mutableStateOf<MyReviewFilterItems>(MyReviewFilterItems.New)
    val currentMyReviewDayFilter: State<MyReviewFilterItems> = _currentMyReviewDayFilter


    private val _currentMyReviewTypeFilter = mutableStateOf<MyReviewFilterItems>(MyReviewFilterItems.Review)
    val currentMyReviewTypeFilter: State<MyReviewFilterItems> = _currentMyReviewTypeFilter

    private val _currentMyReviewVoteFilter = mutableStateOf<MyReviewFilterItems>(MyReviewFilterItems.Best)
    val currentMyReviewVoteFilter: State<MyReviewFilterItems> = _currentMyReviewVoteFilter

//    private val _whiskyFilterDropDownMenuState = mutableStateOf(OderUserReviewDropDownMenuState())
//    val whiskyFilterDropDownMenuState: State<OderUserReviewDropDownMenuState> = _whiskyFilterDropDownMenuState

    private val _reviewFilterDropDownMenuState = mutableStateOf(false)
    val reviewFilterDropDownMenuState: State<Boolean> = _reviewFilterDropDownMenuState

    private val _myReviewDataList=mutableStateOf<List<WhiskyReviewData>>(
        listOf(
        )
    )
    val myReviewDataList: State<List<WhiskyReviewData>> = _myReviewDataList

    private val _homeSearchBarState= mutableStateOf(false)
    val homeSearchBarState: State<Boolean> = _homeSearchBarState

    private val _homeSearchBarSText= mutableStateOf("")
    val homeSearchBarSText: State<String> = _homeSearchBarSText

    private val _otherUserWhiskySearchText= mutableStateOf<OtherUserReviewSearchText>(
        OtherUserReviewSearchText()
    )
    val otherUserWhiskySearchText: State<OtherUserReviewSearchText> = _otherUserWhiskySearchText



    private val _recentSearchReviewTextList=mutableStateOf(listOf<String>())
    val recentSearchReviewTextList: State<List<String>> = _recentSearchReviewTextList

    private val _recentSearchWhiskeyTextList=mutableStateOf(listOf<String>())
    val recentSearchWhiskeyTextList: State<List<String>> = _recentSearchWhiskeyTextList

    private val _whiskeySearchBarState= mutableStateOf(true)
    val whiskeySearchBarState: State<Boolean> = _whiskeySearchBarState

    private val _selectWhiskyReviewData=mutableStateOf(WhiskyReviewData())
    val selectWhiskyReviewData: State<WhiskyReviewData> = _selectWhiskyReviewData

    private val _loginResult=mutableStateOf<Boolean>(false)
    val loginResult: State<Boolean> = _loginResult

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

    private val _insertWhiskyDetailDialogState=mutableStateOf<Boolean>(false)
    val insertWhiskyDetailDialogState: State<Boolean> = _insertWhiskyDetailDialogState


    private val _currentCustomWhiskyType=mutableStateOf<TapLayoutItems>(TapLayoutItems.AmericanWhiskey)
    val currentCustomWhiskyType: State<TapLayoutItems> = _currentCustomWhiskyType

    private val _customWhiskyData=mutableStateOf<CustomWhiskyData>(CustomWhiskyData())
    val customWhiskyData: State<CustomWhiskyData> = _customWhiskyData


    private val _dialogSelectWhiskyData=mutableStateOf<List<WhiskyName>>(
        listOf(

        )
    )
    val dialogSelectWhiskyData: State<List<WhiskyName>> = _dialogSelectWhiskyData


    private val _selectedImageUri = mutableStateOf<Uri>(Uri.EMPTY)
    val selectedImageUri: State<Uri> = _selectedImageUri



    private val _selectedWhiskyImageUriState=mutableStateOf<Boolean>(false)
    val selectedWhiskyImageUriState: State<Boolean> = _selectedWhiskyImageUriState

    private val _errorToastState=mutableStateOf<Boolean>(false)
    val errorToastState: State<Boolean> = _errorToastState

    private val _errorToastMessage=mutableStateOf<String>("")
    val errorToastMessage: State<String> = _errorToastMessage

    private val _errorToastIcon=mutableStateOf<Int>(R.drawable.fail_icon)
    val errorToastIcon: State<Int> = _errorToastIcon



    private val _selectNewWhiskyState=mutableStateOf<Boolean>(false)
    val selectNewWhiskyState: State<Boolean> = _selectNewWhiskyState

    private val _selectWhiskyState=mutableStateOf<Boolean>(false)
    val selectWhiskyState: State<Boolean> = _selectWhiskyState

    private val _myWhiskyFilterData=mutableStateOf<MyWhiskyFilterData>(MyWhiskyFilterData())
    val myWhiskyFilterData: State<MyWhiskyFilterData> = _myWhiskyFilterData


    private val _reviewFilterData=mutableStateOf<ReviewFilterData>(ReviewFilterData())
    val reviewFilterData: State<ReviewFilterData> = _reviewFilterData




    private val _deleteReviewConfirmDialogState=mutableStateOf<Boolean>(false)
    val deleteReviewConfirmDialogState: State<Boolean> = _deleteReviewConfirmDialogState

    private val _deleteWhiskyConfirmDialogState=mutableStateOf<Boolean>(false)
    val deleteWhiskyConfirmDialogState: State<Boolean> = _deleteWhiskyConfirmDialogState

    //임시로 넣음
    private val _whiskyOptionDropDownMenuState= mutableStateListOf<Boolean>(false)
    val whiskyOptionDropDownMenuState: SnapshotStateList<Boolean> = _whiskyOptionDropDownMenuState

    private val _imageTypeSelectState = mutableStateOf(ImageSelectState())
    val imageTypeSelectState: State<ImageSelectState> = _imageTypeSelectState

    private val _imageTypeSelectDialogState=mutableStateOf<Boolean>(false)
    val imageTypeSelectDialogState: State<Boolean> = _imageTypeSelectDialogState

    private val _cameraState=mutableStateOf<Boolean>(false)
    val cameraState: State<Boolean> = _cameraState

    private val _cameraTag=mutableStateOf<AddImageTag>(AddImageTag.ChangeWhiskyImage)
    val cameraTag: State<AddImageTag> = _cameraTag

    private val _imageDialogState=mutableStateOf<Boolean>(false)
    val imageDialogState: State<Boolean> = _imageDialogState

    private val _selectImageUrl=mutableStateOf<ByteArray?>(null)
    val selectImageUrl: State<ByteArray?> = _selectImageUrl

    private val _selectWhiskyData=mutableStateOf<SingleWhiskeyData>(SingleWhiskeyData())
    val selectWhiskyData: State<SingleWhiskeyData> = _selectWhiskyData

    private val _progressIndicatorText=mutableStateOf<String>("")
    val progressIndicatorText: State<String> = _progressIndicatorText

    private val _progressIndicatorState=mutableStateOf<Boolean>(false)
    val progressIndicatorState: State<Boolean> = _progressIndicatorState

    private val _smallProgressIndicatorState = mutableStateOf<Boolean>(false)
    val smallProgressIndicatorState: State<Boolean> = _smallProgressIndicatorState

    private val _tinyProgressIndicatorState = mutableStateOf<Boolean>(false)
    val tinyProgressIndicatorState: State<Boolean> = _tinyProgressIndicatorState

    private val _postProgressIndicatorState = mutableStateOf<Boolean>(false)
    val postProgressIndicatorState: State<Boolean> = _postProgressIndicatorState

    private val _searchButtonState=mutableStateOf<Boolean>(false)
    val searchButtonState: State<Boolean> = _searchButtonState

    private val _selectNewWhiskyData=mutableStateOf<WhiskyName>(WhiskyName())
    val selectNewWhiskyData: State<WhiskyName> = _selectNewWhiskyData

//    private val _currentCountry=mutableStateOf<String>("")
//    val currentCountry: State<String> = _currentCountry

    private val _currentCountryDropDownMenuState=mutableStateOf<Boolean>(false)
    val currentCountryDropDownMenuState: State<Boolean> = _currentCountryDropDownMenuState

    private val _openDateBottomSheetState=mutableStateOf<Boolean>(false)
    val openDateBottomSheetState: State<Boolean> = _openDateBottomSheetState

    private val _detailSearchDialogState=mutableStateOf<Boolean>(false)
    val detailSearchDialogState: State<Boolean> = _detailSearchDialogState


    private val _singleWhiskyDropDownMenuState=mutableStateOf<Boolean>(false)
    val singleWhiskyDropDownMenuState: State<Boolean> = _singleWhiskyDropDownMenuState

    private val _whiskyImageList= mutableStateOf<List<ByteArray>>(emptyList())
    val whiskyImageList: State<List<ByteArray>> = _whiskyImageList


    private val _whiskyModifyState=mutableStateOf<Boolean>(false)
    val whiskyModifyState: State<Boolean> = _whiskyModifyState
//    private val _openDate=mutableStateOf<LocalDate>(LocalDate.now())
//    val openDate: State<LocalDate> = _openDate
//
//    private val _caskType=mutableStateOf<String>("")
//    val caskType: State<String> = _caskType
//
//    private val _whiskyEngName=mutableStateOf<String>("")
//    val whiskyEngName: State<String> = _whiskyEngName

    private val _whiskyListRefreshState=mutableStateOf<Boolean>(false)
    val whiskyListRefreshState: State<Boolean> = _whiskyListRefreshState

    private val _otherUserReviewDataList = MutableStateFlow<PagingData<WhiskyReviewData>>(PagingData.empty())
    val otherUserReviewDataList = _otherUserReviewDataList.asStateFlow()

    private val likeStateFlow = MutableStateFlow<Map<String, LikeState>>(emptyMap())

    private val imageListFlow = MutableStateFlow<Map<String, ImageState>>(emptyMap())

    private val _backupCode=mutableStateOf<String?>(null)
    val backupCode: State<String?> = _backupCode

    private val _inputBackupCode=mutableStateOf<BackupCodeData>(BackupCodeData())
    val inputBackupCode: State<BackupCodeData> = _inputBackupCode

    private val _whiskyDeleteState=mutableStateOf<Boolean>(false)
    val whiskyDeleteState: State<Boolean> = _whiskyDeleteState

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

        _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
            name=newSearchText
        )


        //실시간 검색 구현 예정
    }

    fun updateDrawerSearchBarText(newSearchText:String){
//        _otherUserWhiskySearchText.value=_otherUserWhiskySearchText.value.copy(searchText = newSearchText)
        //실시간 검색 구현 예정
        _reviewFilterData.value=_reviewFilterData.value.copy(
            searchText =newSearchText
        )

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


    fun updateMyWhiskyType(currentWhiskey: TapLayoutItems) {
//        _currentWhiskeyFilter.value=currentWhiskey

        _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
            category = currentWhiskey
        )
        getMyWhiskeyData()
    }

//    fun updateMyWhiskyFilter(filterKey: WhiskeyFilterItems) {
//        Log.d("필터키",filterKey.toString())
//        when(filterKey.type){
//            WhiskeyFilterItems.DAY->{
//
//
//                _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
//                    date_order = filterKey
//                )
//            }
//            WhiskeyFilterItems.SCORE->{
//                _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
//                    score_order = filterKey
//                )
//            }
//            WhiskeyFilterItems.OPEN_DATE->{
//                _currentOpenDateFilter.value=filterKey
//
//                //서버측에서 개봉일 기준을 만들지않았음 문의해야함
////                _whiskyFilterData.value=_whiskyFilterData.value.copy(
////                    name_order = filterKey
////                )
//            }
//            WhiskeyFilterItems.NAME->{
//                _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
//                    name_order = filterKey
//                )
//            }
//
//            else -> {}
//        }.also {
//            getMyWhiskeyData()
//        }
//    }

    //필터를 하나로 통합하기로 결정
    fun updateMyWhiskyFilter(filterKey: WhiskeyFilterItems) {
        Log.d("필터키",filterKey.toString())
        val updatedFilterData = _myWhiskyFilterData.value.copy(
            date_order = if (filterKey.type == WhiskeyFilterItems.DAY) filterKey else null,
            score_order = if (filterKey.type == WhiskeyFilterItems.SCORE) filterKey else null,
//            name_order = if (filterKey.type == WhiskeyFilterItems.NAME) filterKey else null,
            open_date_order = if (filterKey.type == WhiskeyFilterItems.OPEN_DATE) filterKey else null
        )

        _myWhiskyFilterData.value = updatedFilterData

        _currentHomeFilter.value = filterKey
        getMyWhiskeyData()

    }

    fun toggleMyWhiskyFilterDropDownMenuState(filterKey: String) {

        _myWhiskyFilterDropDownMenuState.value = _myWhiskyFilterDropDownMenuState.value.copy(
            day = if (filterKey == WhiskeyFilterItems.DAY) !_myWhiskyFilterDropDownMenuState.value.day else false,
            score = if (filterKey == WhiskeyFilterItems.SCORE) !_myWhiskyFilterDropDownMenuState.value.score else false,
            openDate = if (filterKey == WhiskeyFilterItems.OPEN_DATE) !_myWhiskyFilterDropDownMenuState.value.openDate else false,
            name = if (filterKey == WhiskeyFilterItems.NAME) !_myWhiskyFilterDropDownMenuState.value.name else false,
        )
    }

    fun toggleHomeFilterState(){
        _homeFilterDropDownMenuState.value=!_homeFilterDropDownMenuState.value
    }

    fun toggleMyWhiskeyReviewDropDownMenuState(filterKey: String) {

        _myReviewFilterDropDownMenuState.value = _myReviewFilterDropDownMenuState.value.copy(
            bottleNum = if (filterKey == MyReviewFilterItems.BOTTLE_NUM) !_myReviewFilterDropDownMenuState.value.bottleNum else false,
            day = if (filterKey == MyReviewFilterItems.DAY) !_myReviewFilterDropDownMenuState.value.day else false,
            reviewType = if (filterKey == MyReviewFilterItems.REVIEW_TYPE) !_myReviewFilterDropDownMenuState.value.reviewType else false,
            vote = if (filterKey == MyReviewFilterItems.VOTE) !_myReviewFilterDropDownMenuState.value.vote else false,
        )
    }

    fun updateMyWhiskeyFilter( filterKey: MyReviewFilterItems) {
        when(filterKey.type){
            MyReviewFilterItems.DAY->_currentMyReviewDayFilter.value=filterKey
            MyReviewFilterItems.REVIEW_TYPE->_currentMyReviewTypeFilter.value=filterKey
            MyReviewFilterItems.VOTE->_currentMyReviewVoteFilter.value=filterKey
        }
    }

    fun updateMyReviewFilter( filterKey: MyReviewFilterItems) {
        _currentMyReviewDayFilter.value=filterKey

        Log.d("내 리뷰 필터", filterKey.orderType)
    }


    fun updateMyBottleNumFilter( filterKey: Int) {
        _currentMyReviewBottleNumFilter.intValue=filterKey
    }

    fun toggleHomeFloatingActionButtonState(){
        _homeFloatingActionButtonState.value=!_homeFloatingActionButtonState.value
    }

    fun updateSelectWhisky(selectWhisky:SingleWhiskeyData) {

        Log.d("선택된 위스키",selectWhisky.toString())
        _selectWhiskyData.value=selectWhisky

    }



    fun updateOderUserSelectReview(selectReview:SingleWhiskeyData) {

    }

    fun setSelectReviewData(reviewData: WhiskyReviewData){
        _selectWhiskyReviewData.value=reviewData
    }

    fun tryLogin(ssaid: String) {
        _loginResult.value=false
//        toggleProgressIndicatorState(state = true,text="로그인 중입니다.")
        mainRepository.register(device_id = ssaid){ postDetailResult->
            Log.d("로그인 결과",postDetailResult.toString())
            if (postDetailResult != null) {
                if(postDetailResult.code== SUCCESS_CODE){
                    TokenManager.saveToken(applicationContext, postDetailResult.data!!)
                    _loginResult.value=true


                }else{
                    _loginResult.value=false
                }
            }else{
                _loginResult.value=false
            }
            Log.d("로그인 결과", _loginResult.value.toString())
//            toggleProgressIndicatorState(state = false,text="")
        }
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


    fun resetSubmitBackupCodeResultState(){
        _backupCodeResult.value=null
    }

    fun toggleWhiskySelectDialogState(state:Boolean?=null){
        if(state==null){
            _selectWhiskyDialogState.value=!_selectWhiskyDialogState.value
        }else{
            _selectWhiskyDialogState.value=state
        }

    }

    fun modifyWhiskyMode( data:SingleWhiskeyData?=null){
        resetAddCustomWhiskyDialog()
        Log.d("커스텀", data!!.toString())
        _customWhiskyData.value=_customWhiskyData.value.copy(
            whisky_uuid=data!!.whisky_uuid,
            image_name=data.image_name,
            korea_name=data.korea_name!!,
            english_name = data.english_name,
            category=data.category!!,
            strength=data.strength.toString(),
            country=data.country,
            bottled_year=data.bottled_year ?: 0,
            open_date=data.open_date ?: "",
            cask_type=data.cask_type,
            memo=data.memo,
        )
        data.image?.let{
            ImageConverter.byteArrayToCacheUri(context = applicationContext,byteArray = data.image,fileName=data.image_name!!)?.let{
                _selectedImageUri.value=it
            }
        }
        _currentCustomWhiskyType.value=WhiskyLanguageTransfer.fineWhiskyCategory(data.category)
        _whiskyModifyState.value=true
        toggleInsertWhiskyState()
    }

    fun toggleInsertWhiskyState(){

        _insertWhiskyDetailDialogState.value=!_insertWhiskyDetailDialogState.value
    }

    fun selectNewWhiskyInfoMode(){
        resetAddCustomWhiskyDialog()
        val checkResult=setWhiskyInfo()
        _whiskyModifyState.value=false
        if(checkResult){
            _insertWhiskyDetailDialogState.value=!_insertWhiskyDetailDialogState.value
        }
    }

    fun selfInputWhiskyMode(){

        resetAddCustomWhiskyDialog()
        _whiskyModifyState.value=false
        _insertWhiskyDetailDialogState.value=!_insertWhiskyDetailDialogState.value
    }



    fun updateWhiskySearchText(text:String){
        Log.d("텍스트",text)

        _selectWhiskyText.value=text
    }

    fun whiskySearch(){
        if(selectWhiskyText.value !=""){
            _searchButtonState.value=true
            _smallProgressIndicatorState.value=true
            mainRepository.addWhiskyNameSearch(name = selectWhiskyText.value,category=currentWhiskyFilterType.value.name) { whiskyNameList ->
                if (whiskyNameList != null) {


                    val updatedList = whiskyNameList.data!!.map{ oldName ->

                        oldName.copy(check = false)
                    }
                    _dialogSelectWhiskyData.value = updatedList
                    Log.d("이름들", updatedList.size.toString())
                }
                _smallProgressIndicatorState.value=false
            }

        }
    }

    fun updateCustomWhiskyName(text:String){
        _customWhiskyData.value=_customWhiskyData.value.copy(
            korea_name = text,
//            tag_Text = text
        )

    }


    fun updateCurrentWhiskyFilterType(whisky:TapLayoutItems){
        _currentWhiskyFilterType.value=whisky

    }

    fun updateCurrentCustomWhiskyType(whisky:TapLayoutItems){
        _currentCustomWhiskyType.value=whisky
        _customWhiskyData.value=_customWhiskyData.value.copy(category = whisky.name!!)
    }

    fun toggleWhiskyFilterDropDownMenuState(){
        _currentWhiskyFilterDropDownState.value=!_currentWhiskyFilterDropDownState.value
    }

    fun toggleCustomWhiskyDropDownMenuState(){
        _currentCustomWhiskyDropDownState.value=!_currentCustomWhiskyDropDownState.value
    }

    fun getMyWhiskeyData(refresh:Boolean=false){
//        _homeSearchBarSText.value //검색
//        _currentWhiskeyFilter.value //위스키 종류
//
//        _currentDayFilter.value //작성일
//        _currentScoreFilter.value //점수
//        _currentOpenDateFilter.value //개봉일
//        _currentNameFilter.value //이름순

//        _whiskyFilterData

//        mainRepository.getMyWhiskyList(
//            name=_homeSearchBarSText.value,
//            category= _currentWhiskeyFilter.value.name,
//            date_order= _currentDayFilter.value.orderType,
//            name_order=_currentNameFilter.value.orderType,
//            score_order=_currentScoreFilter.value.orderType,
//        ){
//
//        }


//        _whiskyListRefreshState.value=true
        val searchWord=if (myWhiskyFilterData.value.name=="" || !_searchButtonState.value) null else myWhiskyFilterData.value.name
        if(refresh) _whiskyListRefreshState.value=true else _postProgressIndicatorState.value=true

//        _postProgressIndicatorState.value=true

        Log.d("필터 내용", _myWhiskyFilterData.value.toString())
        mainRepository.getMyWhiskyList(
            name=searchWord,
//            category= _myWhiskyFilterData.value.category.name,
//            date_order= _myWhiskyFilterData.value.date_order?.orderType,
////            name_order=_myWhiskyFilterData.value.name_order.orderType,
//            score_order=_myWhiskyFilterData.value.score_order?.orderType,
            category= _myWhiskyFilterData.value.category.name,
            date_order= _myWhiskyFilterData.value.date_order?.orderType,
//            name_order=_myWhiskyFilterData.value.name_order.orderType,

            score_order=_myWhiskyFilterData.value.score_order?.orderType,
            open_date_order=_myWhiskyFilterData.value.open_date_order?.orderType
            //개봉일 추가해야함 필터 초기값 null로 할당하기
        ){serverResponse ->
            if(serverResponse !=null ){
                if(serverResponse.code== SUCCESS_CODE){
                    _myWhiskyList.value=serverResponse.data!!
                    Log.d("위스키 데이터", _myWhiskyList.value.toString())

                    initializeListSize()

                }else{
                    _myWhiskyList.value= emptyList()
                    setErrorToastMessage(
                        text = "데이터를 가져오는데 실패했습니다. 다시 시도해 주세요",
                        icon=R.drawable.fail_icon,
                    )
                }

            }else{
                _myWhiskyList.value= emptyList()
                setErrorToastMessage(
                    text = "데이터를 가져오는데 실패했습니다. 다시 시도해 주세요",
                    icon=R.drawable.fail_icon,
                )
            }

//            _postProgressIndicatorState.value=false
            if(refresh) _whiskyListRefreshState.value=false else _postProgressIndicatorState.value=false

        }


    }

    fun toggleDialogSelectWhiskyState(index: Int) {
        _dialogSelectWhiskyData.value = _dialogSelectWhiskyData.value.mapIndexed { i, whiskyData ->
            whiskyData.copy(check = i == index)
        }


    }

    fun setSelectedImage(uri: Uri) {
        Log.d("선택한 이미지",uri.toString())
        _selectedImageUri.value=uri
    }


    fun toggleSelectedWhiskyDialogState(){

        _selectedWhiskyImageUriState.value=!_selectedWhiskyImageUriState.value
        Log.d("상태2", _selectedWhiskyImageUriState.value.toString())
    }

    fun setCurrentBottleNum(num:Int){
        _currentMyReviewBottleNum.value=num
    }

    //위스키 등록화면으로 이동하는것이 아니라 등록만 하는 기능으로 수정해야함
//    fun submitCustomWhiskey(){
////        _customWhiskyData.value=_customWhiskyData.value.copy(whisky_type = currentCustomWhiskyType.value.name!!)
//
//        if(_customWhiskyData.value.whisky_name=="" || _customWhiskyData.value.bottled_year==0 || _customWhiskyData.value.strength==""){
//
//            setErrorToastMessage(
//                icon=R.drawable.fail_icon,
//                text="모든 항목을 기입해 주세요."
//            )
//        }else{
//            _tinyProgressIndicatorState.value=true
//            val customWhiskyImage=ImageConverter.convertUrisToFiles(applicationContext, selectedImageUri.value)
//            Log.d("커스텀 위스키 데이터", _customWhiskyData.value.toString())
//            mainRepository.addCustomWhisky(image = customWhiskyImage, data=_customWhiskyData.value){serverResponse ->
//                if(serverResponse!=null){
//
//                    if(serverResponse.code== SUCCESS_CODE){
//
//                        setErrorToastMessage(
//                            icon=R.drawable.success_icon,
//                            text="위스키가 등록되었습니다."
//                        )
//                        toggleCustomWhiskySelectDialogState()
////                        _selectWhiskyState.value=true
//                    }else{
//
//                    }
//                }else{
//
//                }
//                _tinyProgressIndicatorState.value=false
//            }
//
//        }
//
//    }

    fun submitWhiskyDetail(){
//        _customWhiskyData.value=_customWhiskyData.value.copy(whisky_type = currentCustomWhiskyType.value.name!!)

        if(_customWhiskyData.value.korea_name=="" || _customWhiskyData.value.bottled_year==0 || _customWhiskyData.value.strength==""){

            setErrorToastMessage(
                icon=R.drawable.fail_icon,
                text="모든 항목을 기입해 주세요."
            )
        }else{
            _tinyProgressIndicatorState.value=true
            val customWhiskyImage=ImageConverter.convertUrisToFiles(applicationContext, selectedImageUri.value)
            Log.d("커스텀 위스키 데이터", customWhiskyImage.toString())

                mainRepository.saveOrModifyCustomWhisky(image=customWhiskyImage,data=_customWhiskyData.value,_whiskyModifyState.value){serverResponse ->
                    if(serverResponse!=null){

                        if(serverResponse.code== SUCCESS_CODE){

                            if(_whiskyModifyState.value){
                                setErrorToastMessage(
                                    icon=R.drawable.success_icon,
                                    text="정보를 수정했습니다."
                                )
                            }else{
                                setErrorToastMessage(
                                    icon=R.drawable.success_icon,
                                    text="위스키가 등록되었습니다."
                                )
                            }


                            getMyWhiskeyData()
                            toggleInsertWhiskyState()
                            toggleWhiskySelectDialogState(state=false)

                            if(serverResponse.data!=null){
                                _selectWhiskyData.value=serverResponse.data
                            }


                            //서버측에 수정 했을 때 리턴값으로 이미지 바이트로 줄 수 있는지 물어보기
//                            _selectWhiskyData.value=_selectWhiskyData.value.copy(
//                                image_name = _customWhiskyData.value.image_name,
//                                korea_name = _customWhiskyData.value.korea_name,
//                                english_name = _customWhiskyData.value.english_name,
//                                category = _customWhiskyData.value.category,
//                                strength = _customWhiskyData.value.strength.toDouble(),
//                                country = _customWhiskyData.value.country,
//                                bottled_year = _customWhiskyData.value.bottled_year,
//                                open_date = _customWhiskyData.value.open_date,
//                                cask_type = _customWhiskyData.value.cask_type,
//                                memo=_customWhiskyData.value.memo,
//                            )

                        }else{

                        }
                    }else{

                    }
                    ImageConverter.clearCache(context = applicationContext)
                    _tinyProgressIndicatorState.value=false
                    _whiskyModifyState.value=if(_whiskyModifyState.value) false else _whiskyModifyState.value

                }

        }

    }




    fun updateStrength(strength: String) {
        _customWhiskyData.value=_customWhiskyData.value.copy(strength = strength)
    }

    fun updateSaleYear(saleYear: String) {
        if(saleYear==""){
            _customWhiskyData.value=_customWhiskyData.value.copy(bottled_year = 0)
        }else{
            _customWhiskyData.value=_customWhiskyData.value.copy(bottled_year = saleYear.toInt())
        }
    }

    fun toggleSelectNewWhiskyState(state:Boolean) {
        _selectNewWhiskyState.value=state
    }

    fun toggleSelectWhiskyState(state:Boolean) {
        _selectWhiskyState.value=state
    }
//    fun setWriteReviewWhiskyInfo(info: String) {
//        _writeReviewWhiskyName.value=info
////        _currentMyReviewBottleNum.value=bottleNum
//        Log.d("병번호", currentMyReviewBottleNum.value.toString())
//    }

//    fun toggleWhiskyFilterDropDownMenuState(filterKey: String) {
//        _whiskyFilterDropDownMenuState.value = _whiskyFilterDropDownMenuState.value.copy(
//            day = if (filterKey == WhiskeyFilterItems.DAY) !_whiskyFilterDropDownMenuState.value.day else false,
//            score = if (filterKey == WhiskeyFilterItems.SCORE) !_whiskyFilterDropDownMenuState.value.score else false,
//            vote = if (filterKey == WhiskeyFilterItems.VOTE) !_whiskyFilterDropDownMenuState.value.vote else false,
//            name = if (filterKey == WhiskeyFilterItems.NAME) !_whiskyFilterDropDownMenuState.value.name else false,
//        )
//    }

    fun toggleReviewSearchFilterDropDownMenuState() {
        _reviewFilterDropDownMenuState.value = !_reviewFilterDropDownMenuState.value
    }


    fun updateReviewFilter(filterKey: WhiskeyFilterItems) {
        Log.d("필터키",filterKey.toString())

        val updateReviewFilterData=_reviewFilterData.value.copy(
            vote_order = if(filterKey.type == WhiskeyFilterItems.VOTE) filterKey else null,
            score_order = if(filterKey.type==WhiskeyFilterItems.SCORE) filterKey else null,
            date_order = if(filterKey.type==WhiskeyFilterItems.DAY) filterKey else null,
        )

        _reviewFilterData.value=updateReviewFilterData
        _currentReviewFilter.value=filterKey
        getSearchReviewData()
//        when(filterKey.type){
//
//            WhiskeyFilterItems.VOTE->{
//                _whiskyFilterData.value=_whiskyFilterData.value.copy(
//                    vote_order = filterKey
//                )
//            }
//
//            WhiskeyFilterItems.SCORE->{
//                _whiskyFilterData.value=_whiskyFilterData.value.copy(
//                    score_order = filterKey
//                )
//            }
//
//            WhiskeyFilterItems.DAY->{
//                _whiskyFilterData.value=_whiskyFilterData.value.copy(
//                    date_order = filterKey
//                )
//            }
//
//        }
    }

//    fun getWhiskeyData(){
//
//        val searchWord=if (whiskyFilterData.value.name=="") null else whiskyFilterData.value.name
//
//        mainRepository.getMyWhiskyList(
//            name=searchWord,
//            category= _myWhiskyFilterData.value.category.title,
//            date_order= _myWhiskyFilterData.value.date_order.title,
////            name_order=_myWhiskyFilterData.value.name_order.orderType,
//            score_order=_myWhiskyFilterData.value.score_order.orderType,
//        ){
//
//        }
//    }

    fun updateWhiskyType(currentWhiskey: TapLayoutItems) {
//        _currentWhiskeyFilter.value=currentWhiskey

        _reviewFilterData.value=_reviewFilterData.value.copy(
//            category = currentWhiskey
        )
    }

    fun toggleDeleteReviewConfirmDialog(){
        Log.d("상태",deleteReviewConfirmDialogState.value.toString())
        _deleteReviewConfirmDialogState.value=!_deleteReviewConfirmDialogState.value
    }

    fun toggleDeleteWhiskyConfirmDialog(){
        Log.d("상태",_deleteWhiskyConfirmDialogState.value.toString())
        _deleteWhiskyConfirmDialogState.value=!_deleteWhiskyConfirmDialogState.value
    }

    fun resetAddWhiskyDialog(){
        updateWhiskySearchText("")
        _searchButtonState.value=false
        _dialogSelectWhiskyData.value= emptyList()
    }

    fun resetAddCustomWhiskyDialog(){
        _tinyProgressIndicatorState.value=false
        _selectedImageUri.value= Uri.EMPTY
        _currentCustomWhiskyType.value=TapLayoutItems.AmericanWhiskey
        _customWhiskyData.value=CustomWhiskyData()
    }

    fun toggleWhiskyOptionDropDownMenuState(index:Int){

        _whiskyOptionDropDownMenuState[index]=!_whiskyOptionDropDownMenuState[index]
//        Log.d("상태2", _whiskyOptionDropDownMenuState.toString())
    }


    fun initializeListSize() {
//        Log.d("카운트", count.toString())
        //todo 여기에 게시물의 수 만큼 이미지
        _whiskyOptionDropDownMenuState.clear()

        repeat(_myWhiskyList.value.size) {
            _whiskyOptionDropDownMenuState.add(false)
        }


    }

    fun updateSelectImageType(type: ImageSelectType) {
        _imageTypeSelectState.value = when (type) {
            ImageSelectType.ALBUM -> ImageSelectState(albumSelected = true, cameraSelected = false)
            ImageSelectType.CAMERA -> ImageSelectState(albumSelected = false, cameraSelected = true)
        }
    }

    fun toggleImageTypeSelectDialogState(){
        _imageTypeSelectDialogState.value=!_imageTypeSelectDialogState.value
    }

//    fun addImage(uri: Uri?) {
//
//        Log.d("이미지", uri.toString())
//
//        if(uri==null){
//            _errorToastMessage.value="이미지 저장에 실패했습니다."
//            _errorToastIcon.value=R.drawable.fail_icon
//            _errorToastState.value=true
//        }else{
//            _errorToastMessage.value="이미지 저장에 성공했습니다."
//            _errorToastIcon.value=R.drawable.success_icon
//            _errorToastState.value=true
//
//            when(cameraTag.value){
//                AddImageTag.ChangeWhiskyImage->{
//
//                }
//                AddImageTag.AddWhisky->{
//                    setSelectedImage(uri)
//                }
//                AddImageTag.InsertReview->{
//                    setSelectedImage(uri)
//                }
//            }
//        }
//
//    }

//    fun setCameraTag(tag: AddImageTag){

//
//        _cameraTag.value=tag
//    }

    fun toggleImageDialogState(){
        _imageDialogState.value=!_imageDialogState.value
    }

    fun setSelectImage(url: ByteArray){
        _selectImageUrl.value=url
    }

    fun toggleProgressIndicatorState(state:Boolean,text:String){
        _progressIndicatorState.value=state
        _progressIndicatorText.value=text
    }

    fun setErrorToastMessage(icon: Int, text: String) {
        _errorToastState.value=true
        _errorToastMessage.value=text
        _errorToastIcon.value=icon
    }
    fun resetToastErrorState() {
        _errorToastState.value=false
        _errorToastMessage.value=""
    }

//    fun otherUserWhiskySearch(){
//
////        _otherUserWhiskySearchText.value
////        toggleProgressIndicatorState(
////            state = true,
////            text=_otherUserWhiskySearchText.value
////        )
//    }

    fun myWhiskySearch(){
        _homeSearchBarSText.value
        toggleProgressIndicatorState(
            state = true,
            text=_homeSearchBarSText.value
        )
    }

    fun getMyReviewList(){
        Log.d("리뷰 필터",currentMyReviewDayFilter.value.orderType)
        _smallProgressIndicatorState.value=true
        mainRepository.getMyReviewList(
            whiskyUuid = _selectWhiskyData.value.whisky_uuid,
            order = currentMyReviewDayFilter.value.orderType
        ){ serverResponse ->
            if(serverResponse !=null ){

                if(serverResponse.code== SUCCESS_CODE){

                    _myReviewDataList.value=serverResponse.data ?: emptyList()
                    Log.d("리뷰 데이터", _myReviewDataList.value.toString())
                }
            }
            _smallProgressIndicatorState.value=false
        }
    }

    fun updateCurrentCountry(country:String){
        _customWhiskyData.value=customWhiskyData.value.copy(
            country=country
        )
    }
    fun toggleCurrentCountryDropDownMenuState(){
        _currentCountryDropDownMenuState.value=!_currentCountryDropDownMenuState.value
    }

    fun toggleOpenDateBottomSheetState(){
        _openDateBottomSheetState.value=!_openDateBottomSheetState.value
    }

    fun updateOpenDate(openDate:LocalDate){
        _customWhiskyData.value=_customWhiskyData.value.copy(open_date=openDate.toString())
    }

    fun updateCaskType(text:String){
        _customWhiskyData.value=_customWhiskyData.value.copy(cask_type = text)
    }

    fun updateWhiskyEngName(text:String){
        _customWhiskyData.value=_customWhiskyData.value.copy(english_name = text)
    }

    fun toggleDetailSearchDialogState(){
        _detailSearchDialogState.value=!_detailSearchDialogState.value
    }

    fun updateDetailSearchWordText(newSearchText: String){
//        _otherUserWhiskySearchText.value=_otherUserWhiskySearchText.value.copy(
//            detailSearchText = newSearchText
//        )
        _reviewFilterData.value=_reviewFilterData.value.copy(
            detailSearchText = newSearchText
        )
    }

    fun updateWhiskyTagText(tagText:String){
        _customWhiskyData.value=_customWhiskyData.value.copy(memo = tagText)
    }

    fun toggleSingleWhiskyDropDownMenuState(){
        _singleWhiskyDropDownMenuState.value=!_singleWhiskyDropDownMenuState.value
    }

    fun deleteWhiskyData(){

    }

    fun setWhiskyInfo():Boolean {
        val info = _dialogSelectWhiskyData.value.find{ whiskyName ->
            whiskyName.check==true
        }


        Log.d("인포", info.toString())

        return if (info != null) {
            _customWhiskyData.value=_customWhiskyData.value.copy(
                whisky_uuid = info.whisky_uuid,
                korea_name = info.korea_name ?: "",
                english_name = info.english_name,
                strength = info.strength ?:"",
                country = info.country,
            )
            true
        } else {
            setErrorToastMessage(
                icon=R.drawable.fail_icon,
                text="위스키를 선택해 주세요"
            )
            false
        }
    }

    fun deleteReviewData() {
        toggleProgressIndicatorState(state = true,text="")
        mainRepository.deleteReview(
            reviewUuid = selectWhiskyReviewData.value.review_uuid
        ){ serverResponse ->
            if(serverResponse !=null ){

                if(serverResponse.code== SUCCESS_CODE){
                    getMyReviewList()
                }else{
                    setErrorToastMessage(
                        icon=R.drawable.fail_icon,
                        text="리뷰를 제거하지 못했습니다.."
                    )
                }
            }else{
                setErrorToastMessage(
                    icon=R.drawable.fail_icon,
                    text="리뷰를 제거하지 못했습니다.."
                )
            }


            toggleProgressIndicatorState(state = false,text="")
        }
    }


    fun getSearchReviewData(refresh:Boolean=false){

        val searchWord=if (reviewFilterData.value.searchText=="" || !_searchButtonState.value) null else reviewFilterData.value.searchText
        val detailSearchWord=if (reviewFilterData.value.detailSearchText=="") null else reviewFilterData.value.detailSearchText

        Log.d("필터 내용", detailSearchWord.toString())
        Log.d("필터 내용2", " voteAsc: ${reviewFilterData.value.vote_order?.orderType}, scoreAsc: ${reviewFilterData.value.score_order?.orderType}, createdAtAsc: ${reviewFilterData.value.date_order?.orderType}")
        viewModelScope.launch {
            _postProgressIndicatorState.value=true
            mainRepository.getReviewSearchList(
                searchWord=reviewFilterData.value.searchText,
                detailSearchWord = detailSearchWord,
                likeAsc = reviewFilterData.value.vote_order?.orderType,
                scoreAsc = reviewFilterData.value.score_order?.orderType,
                createdAtAsc = reviewFilterData.value.date_order?.orderType
            ).cachedIn(viewModelScope).collect { pagingData ->


                _otherUserReviewDataList.value=pagingData

                //todo 다시 알아보기

                _postProgressIndicatorState.value=false
                Log.d("로딩2", _postProgressIndicatorState.value.toString())
            }

        }
    }

    fun updateReviewLikeState(whiskyReviewData: WhiskyReviewData) {
        Log.d("좋아요 상태", whiskyReviewData.toString())

        val reviewUuid = selectWhiskyReviewData.value.review_uuid
        val currentLikeStates = likeStateFlow.value.toMutableMap()

        if (whiskyReviewData.like_state) {
            cancelLike(reviewUuid, whiskyReviewData, currentLikeStates)
        } else {
            likeReview(reviewUuid, whiskyReviewData, currentLikeStates)
        }
    }

    fun getOtherUserImageList(whiskyReviewData: WhiskyReviewData) {
        Log.d("상태", whiskyReviewData.toString())

        val currentImageList = imageListFlow.value.toMutableMap()

        //사용자가 이미 열어본 경우
        if (whiskyReviewData.isOpened) {
            updateImageState(currentImageList, whiskyReviewData)
        } else {
            //처음 열었을 때
            fetchImageList(whiskyReviewData, currentImageList)
        }
    }

    private fun updateImageState(currentImageList: MutableMap<String, ImageState>, whiskyReviewData: WhiskyReviewData) {
        currentImageList[whiskyReviewData.review_uuid] = ImageState(
            isOpened = true,
            extendedState = !whiskyReviewData.expendedState,
            imageList = whiskyReviewData.imageList ?: emptyList()
        )
        imageListFlow.value = currentImageList
        updateReviewList()
    }

    private fun fetchImageList(whiskyReviewData: WhiskyReviewData, currentImageList: MutableMap<String, ImageState>) {
        viewModelScope.launch {
            Log.e("이미지 가져오기 시작", whiskyReviewData.toString())
            try {
                val result = mainRepository.getImageList(whiskyReviewData)

                result.imageList?.let { imageList ->
                    currentImageList[whiskyReviewData.review_uuid] = ImageState(
                        isOpened = true,
                        extendedState = true,
                        imageList = imageList
                    )
                }
                imageListFlow.value = currentImageList
                updateReviewList()
            } catch (e: Exception) {
                Log.e("실패", "이미지 리스트 가져오기 실패: ${e.message}")
            }
        }
    }


    private fun cancelLike(reviewUuid: String, whiskyReviewData: WhiskyReviewData, currentLikeStates: MutableMap<String, LikeState>) {
        mainRepository.cancelLikeReview(reviewUuid) { serverResponse ->
            serverResponse?.let {
                if (it.code == SUCCESS_CODE) {
                    setErrorToastMessage(icon = R.drawable.fail_icon, text = "추천 취소")
                    updateLikeState(currentLikeStates, whiskyReviewData, -1, false)
                }
            }
        }
    }

    private fun likeReview(reviewUuid: String, whiskyReviewData: WhiskyReviewData, currentLikeStates: MutableMap<String, LikeState>) {
        mainRepository.likeReview(reviewUuid) { serverResponse ->
            serverResponse?.let {
                if (it.code == SUCCESS_CODE) {
                    setErrorToastMessage(icon = R.drawable.success_icon, text = "추천 성공")
                    updateLikeState(currentLikeStates, whiskyReviewData, 1, true)
                }
            }
        }
    }

    private fun updateLikeState(currentLikeStates: MutableMap<String, LikeState>, whiskyReviewData: WhiskyReviewData, countChange: Int, newState: Boolean) {
        currentLikeStates[whiskyReviewData.review_uuid] = LikeState(
            likeCount = (whiskyReviewData.like_count + countChange),
            likeState = newState
        )

        Log.d("현재 추천", currentLikeStates.toString())
        likeStateFlow.value = currentLikeStates

        updateReviewList()
    }

    private fun updateReviewList() {
        viewModelScope.launch {
            combine(_otherUserReviewDataList, likeStateFlow, imageListFlow) { paging, likeState, imageList ->
                paging.map { review ->
                    val newLikeState = likeState[review.review_uuid]
                    val newImageList=imageList[review.review_uuid]
                    review.copy(
                        like_state = newLikeState?.likeState ?: review.like_state,
                        like_count = newLikeState?.likeCount ?: review.like_count,
                        imageList = newImageList?.imageList ?:review.imageList,
                        isOpened = newImageList?.isOpened ?: review.isOpened,
                        expendedState = newImageList?.extendedState ?: review.expendedState
                    )
                }
            }.collect { newReviewData ->
                _otherUserReviewDataList.value = newReviewData
            }
        }
    }

    fun toggleCameraState(state:Boolean){
        _cameraState.value=state
    }
    fun setCameraTag(tag:AddImageTag){
        _cameraTag.value=tag
    }

    fun getBackupCode(){
        mainRepository.getBackupCode {serverResponse->
            if(serverResponse !=null){
                if(serverResponse.code== SUCCESS_CODE){
                    _backupCode.value=serverResponse.data?.code
                }else{

                }
            }else{

            }
        }

    }

    fun submitBackupCode(backupCode:String){
        Log.d("입력 백업 코드",backupCode)
        _inputBackupCode.value=_inputBackupCode.value.copy(
            code = backupCode
        )
        mainRepository.submitBackupCode(inputBackupCode.value) {serverResponse->
            if(serverResponse !=null){
                if(serverResponse.code== SUCCESS_CODE){

                    _backupCodeResult.value=true
                }else{
                    _backupCodeResult.value=false
                }
            }else{
                _backupCodeResult.value=false
            }
        }
    }

    fun deleteWhisky(){
        toggleProgressIndicatorState(state = true,text="")
        toggleDeleteWhiskyConfirmDialog()
        mainRepository.deleteWhisky(
            whisky_uuid = selectWhiskyData.value.whisky_uuid
        ){serverResponse ->
            if(serverResponse !=null){
                if(serverResponse.code== SUCCESS_CODE){
                    findAndDeleteWhiskyList()
                    toggleWhiskyDeleteState(state = true)

                }else{

                }
            }else{

            }
            toggleProgressIndicatorState(state = false,text="")
        }
    }

    private fun findAndDeleteWhiskyList(){
        _myWhiskyList.value = _myWhiskyList.value.filter { it.whisky_uuid != selectWhiskyData.value.whisky_uuid }
    }

    fun toggleWhiskyDeleteState(state:Boolean){
        Log.d("제거 상태", state.toString())
        _whiskyDeleteState.value=state
    }
}
