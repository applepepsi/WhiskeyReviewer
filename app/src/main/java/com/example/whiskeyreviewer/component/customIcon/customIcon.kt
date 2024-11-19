package com.example.whiskeyreviewer.component.customIcon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme

@Composable
fun WriteViewButtonComponent(
    icon: ImageVector,
    onClick:()->Unit
) {

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(7.dp))
            .background(MainColor)
            .size(38.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .size(25.dp),
            imageVector = icon,
            contentDescription = "",
            tint = Color.White,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CustomIconPreview() {


    WhiskeyReviewerTheme {
        WriteViewButtonComponent(icon=ImageVector.vectorResource(R.drawable.write_complete_button), onClick = {})
    }
}