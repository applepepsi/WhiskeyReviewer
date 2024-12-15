package com.example.whiskeyreviewer.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.whiskeyreviewer.component.customComponent.CustomDropDownMenuComponent
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel

@Composable
fun CustomFilterRow(
    mainViewModel: MainViewModel
) {


    LazyRow(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.DAY,
                value = mainViewModel.currentDayFilter.value,
                onValueChange = { mainViewModel.updateFilter(it) },
                dropDownMenuOption = mainViewModel.filterDropDownMenuState.value.day,
                toggleDropDownMenuOption = { mainViewModel.toggleFilterDropDownMenuState(WhiskeyFilterItems.DAY) },
                menuItems = listOf(WhiskeyFilterItems.DayAscendingOrder,WhiskeyFilterItems.DayDescendingOrder)
            )
        }

        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.SCORE,
                value = mainViewModel.currentScoreFilter.value,
                onValueChange = { mainViewModel.updateFilter(it) },
                dropDownMenuOption = mainViewModel.filterDropDownMenuState.value.score,
                toggleDropDownMenuOption = { mainViewModel.toggleFilterDropDownMenuState(WhiskeyFilterItems.SCORE) },
                menuItems = listOf(WhiskeyFilterItems.ScoreAscendingOrder,WhiskeyFilterItems.ScoreDescendingOrder)
            )
        }
        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.OPEN_DATE,
                value = mainViewModel.currentOpenDateFilter.value,
                onValueChange = { mainViewModel.updateFilter(it) },
                dropDownMenuOption = mainViewModel.filterDropDownMenuState.value.openDate,
                toggleDropDownMenuOption = { mainViewModel.toggleFilterDropDownMenuState(WhiskeyFilterItems.OPEN_DATE) },
                menuItems = listOf(WhiskeyFilterItems.OpenDateAscendingOrder,WhiskeyFilterItems.OpenDateDescendingOrder)
            )
        }
    }
}