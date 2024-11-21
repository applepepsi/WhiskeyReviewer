package com.example.whiskeyreviewer.component.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomSearchBoxComponent
import com.example.whiskeyreviewer.component.customIcon.TagComponent
import com.example.whiskeyreviewer.component.customIcon.WhiskeyScoreComponent
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun SingleWhiskeyComponent(
    singleWhiskeyData:SingleWhiskeyData
) {
    Column {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .padding(horizontal = 20.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                imageModel = singleWhiskeyData.picture,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)

            )

            Text(
                text = singleWhiskeyData.name,
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 15.dp)

            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = singleWhiskeyData.capacity.toString() + "ml",
                style = TextStyle.Default.copy(
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 17.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WhiskeyScoreComponent(
                    score = singleWhiskeyData.score
                )

                Spacer(modifier = Modifier.width(15.dp))

                TagComponent(text = "개봉 D - " + singleWhiskeyData.dday.toString())
            }

        }
    }
}

@Composable
fun ModalNavComponent(drawerState: DrawerState, scope: CoroutineScope) {
    ModalDrawerSheet (
        modifier = Modifier
            .fillMaxWidth(0.8f),

    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text="위스키 리뷰어",
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start=15.dp)
            )

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                imageVector = ImageVector.vectorResource(R.drawable.menu_icon),
                contentDescription = "",
                tint = Color.Black,
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(),

        ) {

            Text(
                text="위스키 검색",
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start=15.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                CustomSearchBoxComponent(
                    text="",
                    onValueChange = {},
                    search = { /*TODO*/ },
                    deleteInputText = {}
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeComponentPreview() {


    WhiskeyReviewerTheme {
        SingleWhiskeyComponent(
            singleWhiskeyData = SingleWhiskeyData()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ModalNavPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    WhiskeyReviewerTheme {
        ModalNavComponent(drawerState, scope)
    }
}
