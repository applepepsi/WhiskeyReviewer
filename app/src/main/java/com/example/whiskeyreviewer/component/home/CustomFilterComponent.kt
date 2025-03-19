package com.example.whiskeyreviewer.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
                .padding( horizontal = 8.dp).padding(top=12.dp,bottom=10.dp)
                .fillMaxWidth(),
//            .clip(GenericShape { size, _ ->
//                lineTo(size.width, 0f)
//                lineTo(size.width, Float.MAX_VALUE)
//                lineTo(0f, Float.MAX_VALUE)
//            })
//            .shadow(16.dp)
//            .background(Color.White),

            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
//            item{
//                CustomDropDownMenuComponent(
//                    category = WhiskeyFilterItems.DAY,
//                    value = mainViewModel.myWhiskyFilterData.value.date_order,
//                    onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
//                    dropDownMenuOption = mainViewModel.myWhiskyFilterDropDownMenuState.value.day,
//                    toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskyFilterDropDownMenuState(WhiskeyFilterItems.DAY) },
//                    menuItems = listOf(WhiskeyFilterItems.DayAscendingOrder,WhiskeyFilterItems.DayDescendingOrder),
//
//                    )
//            }
//
//            item{
//                CustomDropDownMenuComponent(
//                    category = WhiskeyFilterItems.SCORE,
//                    value = mainViewModel.myWhiskyFilterData.value.score_order,
//                    onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
//                    dropDownMenuOption = mainViewModel.myWhiskyFilterDropDownMenuState.value.score,
//                    toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskyFilterDropDownMenuState(WhiskeyFilterItems.SCORE) },
//                    menuItems = listOf(WhiskeyFilterItems.ScoreAscendingOrder,WhiskeyFilterItems.ScoreDescendingOrder)
//                )
//            }
//            item{
//                CustomDropDownMenuComponent(
//                    category = WhiskeyFilterItems.OPEN_DATE,
//                    value = mainViewModel.currentOpenDateFilter.value,
//                    onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
//                    dropDownMenuOption = mainViewModel.myWhiskyFilterDropDownMenuState.value.openDate,
//                    toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskyFilterDropDownMenuState(WhiskeyFilterItems.OPEN_DATE) },
//                    menuItems = listOf(WhiskeyFilterItems.OpenDateAscendingOrder,WhiskeyFilterItems.OpenDateDescendingOrder)
//                )
//            }

            item{
                CustomDropDownMenuComponent(
                    category = mainViewModel.currentHomeFilter.value.title,
                    value = mainViewModel.currentHomeFilter.value,
                    onValueChange = { mainViewModel.updateMyWhiskyFilter(it) },
                    dropDownMenuOption = mainViewModel.homeFilterDropDownMenuState.value,
                    toggleDropDownMenuOption = { mainViewModel.toggleHomeFilterState()},
                    menuItems = listOf(
                        WhiskeyFilterItems.DayAscendingOrder,
                        WhiskeyFilterItems.DayDescendingOrder,
                        WhiskeyFilterItems.ScoreAscendingOrder,
                        WhiskeyFilterItems.ScoreDescendingOrder,
                        WhiskeyFilterItems.OpenDateAscendingOrder,
                        WhiskeyFilterItems.OpenDateDescendingOrder

                    ),
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
            .padding(vertical = 3.dp, horizontal = 8.dp)
            .fillMaxWidth(),


        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.VOTE,
                value = mainViewModel.whiskyFilterData.value.vote_order,
                onValueChange = { mainViewModel.updateWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.whiskyFilterDropDownMenuState.value.vote,
                toggleDropDownMenuOption = { mainViewModel.toggleWhiskyFilterDropDownMenuState(WhiskeyFilterItems.VOTE) },
                menuItems = listOf(WhiskeyFilterItems.VoteAscendingOrder,WhiskeyFilterItems.VoteDescendingOrder)
            )
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
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.NAME,
                value = mainViewModel.whiskyFilterData.value.name_order,
                onValueChange = { mainViewModel.updateWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.whiskyFilterDropDownMenuState.value.name,
                toggleDropDownMenuOption = { mainViewModel.toggleWhiskyFilterDropDownMenuState(WhiskeyFilterItems.NAME) },
                menuItems = listOf(WhiskeyFilterItems.NameAscendingOrder,WhiskeyFilterItems.NameDescendingOrder)
            )
        }


        item{
            CustomDropDownMenuComponent(
                category = WhiskeyFilterItems.DAY,
                value = mainViewModel.whiskyFilterData.value.date_order,
                onValueChange = { mainViewModel.updateWhiskyFilter(it) },
                dropDownMenuOption = mainViewModel.whiskyFilterDropDownMenuState.value.day,
                toggleDropDownMenuOption = { mainViewModel.toggleWhiskyFilterDropDownMenuState(WhiskeyFilterItems.DAY) },
                menuItems = listOf(WhiskeyFilterItems.DayAscendingOrder,WhiskeyFilterItems.DayDescendingOrder)
            )
        }
    }
}