package com.whiskeyReviewer.whiskeyreviewer.component.customComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomAppBarComponent(
    titleTextValue:String="",

    rightButton:@Composable () -> Unit,
    leftButton:@Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            leftButton()
        }

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            MainTitleComponent(value = titleTextValue)
        }

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            rightButton()
        }
    }
}