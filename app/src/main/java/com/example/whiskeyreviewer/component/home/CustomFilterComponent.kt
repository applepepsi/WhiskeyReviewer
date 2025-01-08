package com.example.whiskeyreviewer.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.whiskeyreviewer.component.customComponent.CustomDropDownMenuComponent
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
import com.example.whiskeyreviewer.viewModel.MainViewModel

@Composable
fun MyWhiskyCustomFilterRow(
    mainViewModel: MainViewModel,

) {


    LazyRow(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),

        horizontalArrangement = Arrangement.SpaceAround
    ) {
        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.DAY,
                value = mainViewModel.myWhiskyFilterData.value.date_order,
                onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.myWhiskyFilterDropDownMenuState.value.day,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskyFilterDropDownMenuState(WhiskeyFilterItems.DAY) },
                menuItems = listOf(WhiskeyFilterItems.DayAscendingOrder,WhiskeyFilterItems.DayDescendingOrder),

            )
        }

        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.SCORE,
                value = mainViewModel.myWhiskyFilterData.value.score_order,
                onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.myWhiskyFilterDropDownMenuState.value.score,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskyFilterDropDownMenuState(WhiskeyFilterItems.SCORE) },
                menuItems = listOf(WhiskeyFilterItems.ScoreAscendingOrder,WhiskeyFilterItems.ScoreDescendingOrder)
            )
        }
        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.OPEN_DATE,
                value = mainViewModel.currentOpenDateFilter.value,
                onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.myWhiskyFilterDropDownMenuState.value.openDate,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskyFilterDropDownMenuState(WhiskeyFilterItems.OPEN_DATE) },
                menuItems = listOf(WhiskeyFilterItems.OpenDateAscendingOrder,WhiskeyFilterItems.OpenDateDescendingOrder)
            )
        }
        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.NAME,
                value = mainViewModel.myWhiskyFilterData.value.name_order,
                onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.myWhiskyFilterDropDownMenuState.value.name,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskyFilterDropDownMenuState(WhiskeyFilterItems.NAME) },
                menuItems = listOf(WhiskeyFilterItems.NameAscendingOrder,WhiskeyFilterItems.NameDescendingOrder)
            )
        }
    }
}


@Composable
fun WhiskyCustomFilterRow(
    mainViewModel: MainViewModel,

    ) {


    LazyRow(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxWidth(),


        horizontalArrangement = Arrangement.Start
    ) {
        item{
            Spacer(modifier = Modifier.width(4.dp))
        }


        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.SCORE,
                value = mainViewModel.whiskyFilterData.value.score_order,
                onValueChange = { mainViewModel.updateWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.whiskyFilterDropDownMenuState.value.score,
                toggleDropDownMenuOption = { mainViewModel.toggleWhiskyFilterDropDownMenuState(WhiskeyFilterItems.SCORE) },
                menuItems = listOf(WhiskeyFilterItems.ScoreAscendingOrder,WhiskeyFilterItems.ScoreDescendingOrder)
            )
        }

        item{
            Spacer(modifier = Modifier.width(13.dp))
        }

        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.NAME,
                value = mainViewModel.whiskyFilterData.value.name_order,
                onValueChange = { mainViewModel.updateWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.whiskyFilterDropDownMenuState.value.name,
                toggleDropDownMenuOption = { mainViewModel.toggleWhiskyFilterDropDownMenuState(WhiskeyFilterItems.NAME) },
                menuItems = listOf(WhiskeyFilterItems.NameAscendingOrder,WhiskeyFilterItems.NameDescendingOrder)
            )
        }
    }
}