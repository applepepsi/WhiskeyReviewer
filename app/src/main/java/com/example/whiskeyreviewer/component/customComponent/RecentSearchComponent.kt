package com.example.whiskeyreviewer.component.customComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.ui.theme.LightBlackColor

@Composable
fun RecentSearchWordComponent(
    text:String="test",
    deleteSearchWord:()->Unit,
    search:()->Unit
) {
    Column {
        Row(
            modifier = Modifier
//            .background(Background_Color2)
//            .clip(RoundedCornerShape(5.dp))
                .padding(end = 8.dp)
//            .clip(RoundedCornerShape(5.dp))
//            .background(Color.White)

//            .border(
//                BorderStroke(
//                    0.5.dp,
//                    Color.LightGray
//                ),
//                RoundedCornerShape(5.dp)
//            )
                .padding(start = 8.dp, end = 5.dp, top = 7.dp, bottom = 7.dp)

            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text=text,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable { search() }
            )

            IconButton(
                onClick = {
                    deleteSearchWord()
                },
                modifier = Modifier.size(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(23.dp)
                )

            }

        }
    }

}

@Preview
@Composable
fun RecentSearchWordComponentPreview(

) {
    RecentSearchWordComponent(deleteSearchWord = { /*TODO*/ }) {
        
    }
}