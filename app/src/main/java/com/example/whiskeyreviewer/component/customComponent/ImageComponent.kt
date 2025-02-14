package com.example.whiskeyreviewer.component.customComponent

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageComponent(
    imageClick:()->Unit,
    image: Uri,
    modifier: Modifier
) {

    val selectImage= if(image== Uri.EMPTY) R.drawable.empty_image_icon else image

    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(12.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                imageClick()
            },
    ) {

        GlideImage(
            imageModel = selectImage,
            modifier = Modifier
                .size(200.dp)
        )
    }
}


@Preview
@Composable
fun SelectImagePreview(){
    ImageComponent(
        image = Uri.EMPTY,
        imageClick = {},
        modifier = Modifier
    )
}