package com.example.whiskeyreviewer.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

import com.example.nextclass.utils.RECENT_SEARCH_REVIEW_TEXT
import com.example.nextclass.utils.RECENT_SEARCH_WHISKEY_TEXT
import com.example.nextclass.utils.SUCCESS_CODE
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.data.AddImageTag
import com.example.whiskeyreviewer.data.CustomWhiskyData

import com.example.whiskeyreviewer.data.ToolBarItems
import com.example.whiskeyreviewer.data.FilterDropDownMenuState
import com.example.whiskeyreviewer.data.ImageSelectState
import com.example.whiskeyreviewer.data.ImageSelectType
import com.example.whiskeyreviewer.data.MyReviewFilterDropDownMenuState
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.NavigationDrawerItems
import com.example.whiskeyreviewer.data.ReviewData
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TapLayoutItems
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
import com.example.whiskeyreviewer.data.WhiskeyReviewData
import com.example.whiskeyreviewer.data.WhiskyFilterData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.repository.MainRepository
import com.example.whiskeyreviewer.utils.ImageConverter
import com.example.whiskeyreviewer.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val mainRepository: MainRepository
): ViewModel() {



    private val _selectedItem = mutableStateOf<ToolBarItems?>(null)
    val selectedItem: State<ToolBarItems?> = _selectedItem

    private val _myReviewList = mutableStateOf<List<SingleWhiskeyData>>(
        listOf(
            SingleWhiskeyData(
                whisky_name="잭 다니엘 10년",
                strength = 20.0,
                score=4.5,
                dday=6,
                picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000039"),

                ), SingleWhiskeyData(
                whisky_name="글렌 리뱃 12년산",
                strength = 18.5,
                score=3.5,
                dday=3,
                picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000037"),

                ),
            SingleWhiskeyData(
            )
        )
//        emptyList()
    )
    val myReviewList: State<List<SingleWhiskeyData>> = _myReviewList

    private val _reviewList = mutableStateOf<List<SingleWhiskeyData>>(
        listOf(
            SingleWhiskeyData(
                whisky_name="잭 다니엘 10년",
                strength = 20.0,
                score=4.5,
                dday=6,
                picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000039"),

                ), SingleWhiskeyData(
                whisky_name="글렌 리뱃 12년산",
                strength = 18.5,
                score=3.5,
                dday=3,
                picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000037"),

                ),
            SingleWhiskeyData(
            )
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


    private val _myWhiskyFilterDropDownMenuState = mutableStateOf(FilterDropDownMenuState())
    val myWhiskyFilterDropDownMenuState: State<FilterDropDownMenuState> = _myWhiskyFilterDropDownMenuState

    private val _writeReviewDate = mutableStateOf(WriteReviewData())
    val writeReviewDate: State<WriteReviewData> = _writeReviewDate

    private val _homeFloatingActionButtonState = mutableStateOf(false)
    val homeFloatingActionButtonState: State<Boolean> = _homeFloatingActionButtonState

    private val _myReviewFilterDropDownMenuState = mutableStateOf(MyReviewFilterDropDownMenuState())
    val myReviewFilterDropDownMenuState: State<MyReviewFilterDropDownMenuState> = _myReviewFilterDropDownMenuState

    private val _currentMyReviewBottleNumFilter = mutableIntStateOf(10)
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

    private val _whiskyFilterDropDownMenuState = mutableStateOf(FilterDropDownMenuState())
    val whiskyFilterDropDownMenuState: State<FilterDropDownMenuState> = _whiskyFilterDropDownMenuState

    private val _myReviewDataList=mutableStateOf<List<WhiskeyReviewData>>(
        listOf(
            WhiskeyReviewData(
                content = "<p>이 위스키는 정말 부드럽고 향이 풍부합니다&period;wfaf<span style=\"font-size: 31.0px;\">wfqwqwfqwfqwf<span style=\"background: rgba(0, 128, 0, 1.0);\">qwfqwfqwfqwfqwf</span></span></p>",
                is_anonymous = false,
                open_date = LocalDate.of(2023, 1, 15),
                score = 4.5,
                tags = listOf("부드러운", "향긋한", "프루티"),
                imageList = listOf("content://media/picker/0/com.android.providers.media.photopicker/media/1000000073", "content://media/picker/0/com.android.providers.media.photopicker/media/1000000064")
            ),
            WhiskeyReviewData(
                content = "이 가격에 이 맛은 정말 훌륭합니다.",
                is_anonymous = true,
                open_date = LocalDate.of(2023, 2, 10),
                score = 4.0,
                tags = listOf("가성비", "추천"),
                imageList = emptyList()
            ),
            WhiskeyReviewData(
                content = "약간의 스파이시함과 함께 깊은 맛이 느껴집니다.",
                is_anonymous = false,
                open_date = LocalDate.of(2023, 3, 5),
                score = 4.2,
                tags = listOf("스파이시", "깊은맛"),
                imageList = listOf("image3.jpg")
            ),
            WhiskeyReviewData(
                content = "너무 강한 맛이어서 개인적으로는 별로였습니다.",
                is_anonymous = true,
                open_date = LocalDate.of(2023, 4, 20),
                score = 2.5,
                tags = listOf("강한맛"),
                imageList = listOf("image4.jpg", "image5.jpg")
            ),
            WhiskeyReviewData(
                content = "달콤한 맛과 함께 부드러운 목넘김이 인상적입니다.",
                is_anonymous = false,
                open_date = LocalDate.of(2023, 5, 30),
                score = 4.8,
                tags = listOf("달콤한", "부드러운"),
                imageList = listOf("image6.jpg")
            )
        )
    )
    val myReviewDataList: State<List<WhiskeyReviewData>> = _myReviewDataList

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

    private val _selectWhiskyReviewData=mutableStateOf(WhiskeyReviewData())
    val selectWhiskyReviewData: State<WhiskeyReviewData> = _selectWhiskyReviewData

    private val _loginResult=mutableStateOf<Boolean?>(null)
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

    private val _customWhiskyData=mutableStateOf<CustomWhiskyData>(CustomWhiskyData())
    val customWhiskyData: State<CustomWhiskyData> = _customWhiskyData


    private val _dialogSelectWhiskyData=mutableStateOf<List<WhiskyName>>(
        listOf(

        )
    )
    val dialogSelectWhiskyData: State<List<WhiskyName>> = _dialogSelectWhiskyData


    private val _selectedImageUri = mutableStateOf<Uri>(Uri.EMPTY)
    val selectedImageUri: State<Uri> = _selectedImageUri

    private val _selectedWhiskyImageUri = mutableStateOf<Uri>(Uri.EMPTY)
    val selectedWhiskyImageUri: State<Uri> = _selectedWhiskyImageUri

    private val _selectedWhiskyImageUriState=mutableStateOf<Boolean>(false)
    val selectedWhiskyImageUriState: State<Boolean> = _selectedWhiskyImageUriState

    private val _errorToastState=mutableStateOf<Boolean>(false)
    val errorToastState: State<Boolean> = _errorToastState

    private val _errorToastMessage=mutableStateOf<String>("")
    val errorToastMessage: State<String> = _errorToastMessage

    private val _errorToastIcon=mutableStateOf<Int>(R.drawable.fail_icon)
    val errorToastIcon: State<Int> = _errorToastIcon

    private val _writeReviewWhiskyName=mutableStateOf<String>("")
    val writeReviewWhiskyName: State<String> = _writeReviewWhiskyName

    private val _selectWhiskyState=mutableStateOf<Boolean>(false)
    val selectWhiskyState: State<Boolean> = _selectWhiskyState

    private val _myWhiskyFilterData=mutableStateOf<WhiskyFilterData>(WhiskyFilterData())
    val myWhiskyFilterData: State<WhiskyFilterData> = _myWhiskyFilterData

    private val _whiskyFilterData=mutableStateOf<WhiskyFilterData>(WhiskyFilterData())
    val whiskyFilterData: State<WhiskyFilterData> = _whiskyFilterData


    private val _confirmDialogState=mutableStateOf<Boolean>(false)
    val confirmDialogState: State<Boolean> = _confirmDialogState

    //임시로 넣음
    private val _whiskyOptionDropDownMenuState= mutableStateListOf<Boolean>(false).apply {
        repeat(2) {
            add(false)
        }
    }
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

    private val _selectImageUrl=mutableStateOf<String>("")
    val selectImageUrl: State<String> = _selectImageUrl

    private val _selectWhiskyData=mutableStateOf<SingleWhiskeyData>(SingleWhiskeyData())
    val selectWhiskyData: State<SingleWhiskeyData> = _selectWhiskyData


    private val _progressIndicatorText=mutableStateOf<String>("")
    val progressIndicatorText: State<String> = _progressIndicatorText

    private val _progressIndicatorState=mutableStateOf<Boolean>(false)
    val progressIndicatorState: State<Boolean> = _progressIndicatorState

    private val _smallProgressIndicatorState = mutableStateOf<Boolean>(false)
    val smallProgressIndicatorState: State<Boolean> = _smallProgressIndicatorState

    private val _searchButtonState=mutableStateOf<Boolean>(false)
    val searchButtonState: State<Boolean> = _searchButtonState
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
        _drawerSearchBarText.value=newSearchText
        //실시간 검색 구현 예정
        _whiskyFilterData.value=_whiskyFilterData.value.copy(
            name=newSearchText
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
    }

    fun updateMyWhiskyFilter(filterKey: WhiskeyFilterItems) {
        Log.d("필터키",filterKey.toString())
        when(filterKey.type){
            WhiskeyFilterItems.DAY->{


                _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
                    date_order = filterKey
                )
            }
            WhiskeyFilterItems.SCORE->{


                _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
                    score_order = filterKey
                )
            }
            WhiskeyFilterItems.OPEN_DATE->{
                _currentOpenDateFilter.value=filterKey

                //서버측에서 개봉일 기준을 만들지않았음 문의해야함
//                _whiskyFilterData.value=_whiskyFilterData.value.copy(
//                    name_order = filterKey
//                )
            }
            WhiskeyFilterItems.NAME->{
                _myWhiskyFilterData.value=_myWhiskyFilterData.value.copy(
                    name_order = filterKey
                )
            }
        }
    }

    fun toggleMyWhiskyFilterDropDownMenuState(filterKey: String) {

        _myWhiskyFilterDropDownMenuState.value = _myWhiskyFilterDropDownMenuState.value.copy(
            day = if (filterKey == WhiskeyFilterItems.DAY) !_myWhiskyFilterDropDownMenuState.value.day else false,
            score = if (filterKey == WhiskeyFilterItems.SCORE) !_myWhiskyFilterDropDownMenuState.value.score else false,
            openDate = if (filterKey == WhiskeyFilterItems.OPEN_DATE) !_myWhiskyFilterDropDownMenuState.value.openDate else false,
            name = if (filterKey == WhiskeyFilterItems.NAME) !_myWhiskyFilterDropDownMenuState.value.name else false,
        )
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

    fun setSelectReviewData(reviewData: WhiskeyReviewData){
        _selectWhiskyReviewData.value=reviewData
    }

    fun tryLogin(ssaid: String) {

        toggleProgressIndicatorState(state = true,text="로그인 중입니다.")
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

            toggleProgressIndicatorState(state = false,text="")
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
        resetAddCustomWhiskyDialog()

        _selectCustomWhiskyDialogState.value=!_selectCustomWhiskyDialogState.value
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

    fun updateCustomWhiskyText(text:String){
        _customWhiskyData.value=_customWhiskyData.value.copy(whisky_name = text)
    }


    fun updateCurrentWhiskyFilterType(whisky:TapLayoutItems){
        _currentWhiskyFilterType.value=whisky

    }

    fun updateCurrentCustomWhiskyType(whisky:TapLayoutItems){
        _currentCustomWhiskyType.value=whisky
        _customWhiskyData.value=_customWhiskyData.value.copy(whisky_type = whisky.name!!)
    }

    fun toggleWhiskyFilterDropDownMenuState(){
        _currentWhiskyFilterDropDownState.value=!_currentWhiskyFilterDropDownState.value
    }

    fun toggleCustomWhiskyDropDownMenuState(){
        _currentCustomWhiskyDropDownState.value=!_currentCustomWhiskyDropDownState.value
    }

    fun getMyWhiskeyData(){
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

        val searchWord=if (myWhiskyFilterData.value.name=="") null else myWhiskyFilterData.value.name

        mainRepository.getMyWhiskyList(
            name=searchWord,
            category= _myWhiskyFilterData.value.category.name,
            date_order= _myWhiskyFilterData.value.date_order.orderType,
            name_order=_myWhiskyFilterData.value.name_order.orderType,
            score_order=_myWhiskyFilterData.value.score_order.orderType,
        ){

        }
    }

    fun toggleDialogSelectWhiskyState(index: Int) {
        _dialogSelectWhiskyData.value = _dialogSelectWhiskyData.value.mapIndexed { i, whiskyData ->
            whiskyData.copy(check = i == index)
        }


    }

    fun setSelectedImage(uri: Uri) {
        Log.d("이미지",uri.toString())
        _selectedImageUri.value=uri
    }

    fun setSelectedWhiskyImage(uri: Uri) {
        Log.d("이미지",uri.toString())
        _selectedWhiskyImageUri.value=uri
    }

    fun toggleSelectedWhiskyDialogState(){

        _selectedWhiskyImageUriState.value=!_selectedWhiskyImageUriState.value
        Log.d("상태2", _selectedWhiskyImageUriState.value.toString())
    }

    fun setCurrentBottleNum(num:Int){
        _currentMyReviewBottleNum.value=num
    }

    fun submitNewWhiskey(){
        val info = _dialogSelectWhiskyData.value.find{ whiskyName ->
            whiskyName.check==true
        }

        Log.d("인포", info.toString())

        if (info != null) {
            if(!info.is_first){
                setWriteReviewWhiskyInfo(info.whisky_name)
//                setCurrentBottleNum(num=0)
                _selectWhiskyState.value=true
            }else{
                //기존에 있는거라면 세부 뷰로

            }
        } else {
            setErrorToastMessage(
                icon=R.drawable.fail_icon,
                text="위스키를 선택해 주세요"
            )

        }
    }

    fun submitCustomWhiskey(){
//        _customWhiskyData.value=_customWhiskyData.value.copy(whisky_type = currentCustomWhiskyType.value.name!!)

        if(_customWhiskyData.value.whisky_name=="" || _customWhiskyData.value.sale_year=="" || _customWhiskyData.value.strength==""){

            setErrorToastMessage(
                icon=R.drawable.fail_icon,
                text="모든 항목을 기입해 주세요."
            )
        }else{
            val customWhiskyImage=ImageConverter.convertUrisToFiles(applicationContext, selectedImageUri.value)

            mainRepository.addCustomWhisky(image = customWhiskyImage, data=_customWhiskyData.value){serverResponse ->
                if(serverResponse!=null){

                    //새로운 병이라면 데이터를 넣어주고
                    if(serverResponse.code== SUCCESS_CODE){
//                        val info=WhiskyName(
//                            whisky_name = _customWhiskyData.value.whisky_name,
//                            is_first = false,
//                            whisky_uuid = ""
//                        )
//
                        setWriteReviewWhiskyInfo(customWhiskyData.value.whisky_name)

//                        setCurrentBottleNum(num=0)
                        _selectWhiskyState.value=true
                    }else{

                    }
                }else{

                }
            }

            //서버측에 좀 구체적으로 물어봐야함
//            val info=WhiskyName(
//                whisky_name = _customWhiskyData.value.whisky_name,
//                is_first = false,
//                whisky_uuid = ""
//            )
//
//
//            setWriteReviewWhiskyInfo(info, bottleNum = 1)
//
//            setCurrentBottleNum(num=0)
//            _selectWhiskyState.value=true
        }

    }



    fun updateStrength(strength: String) {
        _customWhiskyData.value=_customWhiskyData.value.copy(strength = strength)
    }

    fun updateSaleYear(saleYear: String) {
        _customWhiskyData.value=_customWhiskyData.value.copy(sale_year = saleYear)
    }

    fun toggleSelectWhiskyState(state:Boolean) {
        _selectWhiskyState.value=state
    }


    fun setWriteReviewWhiskyInfo(info: String) {
        _writeReviewWhiskyName.value=info
//        _currentMyReviewBottleNum.value=bottleNum
        Log.d("병번호", currentMyReviewBottleNum.value.toString())
    }

    fun toggleWhiskyFilterDropDownMenuState(filterKey: String) {
        _whiskyFilterDropDownMenuState.value = _whiskyFilterDropDownMenuState.value.copy(
            day = if (filterKey == WhiskeyFilterItems.DAY) !_whiskyFilterDropDownMenuState.value.day else false,
            score = if (filterKey == WhiskeyFilterItems.SCORE) !_whiskyFilterDropDownMenuState.value.score else false,
            openDate = if (filterKey == WhiskeyFilterItems.OPEN_DATE) !_whiskyFilterDropDownMenuState.value.openDate else false,
            name = if (filterKey == WhiskeyFilterItems.NAME) !_whiskyFilterDropDownMenuState.value.name else false,
        )
    }

    fun updateWhiskyFilter(filterKey: WhiskeyFilterItems) {
        Log.d("필터키",filterKey.toString())
        when(filterKey.type){

            WhiskeyFilterItems.SCORE->{
                _whiskyFilterData.value=_whiskyFilterData.value.copy(
                    score_order = filterKey
                )
            }

            WhiskeyFilterItems.NAME->{
                _whiskyFilterData.value=_whiskyFilterData.value.copy(
                    name_order = filterKey
                )
            }
        }
    }

    fun getWhiskeyData(){

        val searchWord=if (whiskyFilterData.value.name=="") null else whiskyFilterData.value.name

        mainRepository.getMyWhiskyList(
            name=searchWord,
            category= _whiskyFilterData.value.category.title,
            date_order= "",
            name_order=_whiskyFilterData.value.name_order.orderType,
            score_order=_whiskyFilterData.value.score_order.orderType,
        ){

        }
    }

    fun updateWhiskyType(currentWhiskey: TapLayoutItems) {
//        _currentWhiskeyFilter.value=currentWhiskey

        _whiskyFilterData.value=_whiskyFilterData.value.copy(
            category = currentWhiskey
        )
    }

    fun toggleConfirmDialog(){
        Log.d("상태",confirmDialogState.value.toString())
        _confirmDialogState.value=!_confirmDialogState.value
    }


    fun resetAddWhiskyDialog(){
        updateWhiskySearchText("")
        _searchButtonState.value=false
        _dialogSelectWhiskyData.value= emptyList()
    }

    fun resetAddCustomWhiskyDialog(){
        _selectedImageUri.value= Uri.EMPTY
        _currentCustomWhiskyType.value=TapLayoutItems.AmericanWhiskey
        _customWhiskyData.value=CustomWhiskyData()
    }

    fun toggleWhiskyOptionDropDownMenuState(index:Int){

        _whiskyOptionDropDownMenuState[index]=!_whiskyOptionDropDownMenuState[index]
//        Log.d("상태2", _whiskyOptionDropDownMenuState.toString())
    }


    fun initializeDropDownStates(count: Int) {
        Log.d("카운트", count.toString())
        _whiskyOptionDropDownMenuState.clear()

        repeat(count) {
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

    fun setSelectImage(url:String){
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
}
