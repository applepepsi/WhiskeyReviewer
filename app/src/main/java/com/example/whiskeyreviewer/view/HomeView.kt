package com.example.whiskeyreviewer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whiskeyreviewer.component.home.SingleWhiskeyComponent
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme


@Composable
fun HomeView(

) {
    val testData= listOf(
        SingleWhiskeyData(), SingleWhiskeyData(),SingleWhiskeyData()
    )
    Column(
        modifier=Modifier
            .fillMaxSize()
    ) {
        LazyColumn(

            horizontalAlignment = Alignment.CenterHorizontally,
//            contentPadding = PaddingValues(vertical = 10.dp),
        ) {
            items(items = testData){ singleWhiskeyData->
                SingleWhiskeyComponent(singleWhiskeyData = singleWhiskeyData)

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomePreview() {


    WhiskeyReviewerTheme {
        HomeView()
    }
}