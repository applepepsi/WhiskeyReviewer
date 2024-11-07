package com.example.whiskeyreviewer.component.wheelPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.whiskeyreviewer.ui.theme.MainColor
import ir.kaaveh.sdpcompose.sdp


@Composable
fun HorizontalWheelPicker(
    modifier: Modifier = Modifier,
    wheelPickerWidth: Dp? = null,
    totalItems: Int,
    initialSelectedItem: Int,
    lineWidth: Dp = 2.sdp,
    selectedLineHeight: Dp = 32.sdp,
    multipleOfFiveLineHeight: Dp = 15.sdp,
    normalLineHeight: Dp = 15.sdp,
    selectedMultipleOfFiveLinePaddingBottom: Dp = 0.sdp,
    normalMultipleOfFiveLinePaddingBottom: Dp = 6.sdp,
    normalLinePaddingBottom: Dp = 8.sdp,
    lineSpacing: Dp = 8.sdp,
    lineRoundedCorners: Dp = 2.sdp,
    selectedLineColor: Color = MainColor,
    unselectedLineColor: Color = Color.LightGray,
    fadeOutLinesCount: Int = 4,
    maxFadeTransparency: Float = 0.7f,
    onItemSelected: (Int) -> Unit
) {
    val screenWidthDp = LocalContext.current.resources.displayMetrics.run {
        widthPixels / density
    }.dp
    val effectiveWidth = wheelPickerWidth ?: screenWidthDp

    var currentSelectedItem by remember { mutableIntStateOf(initialSelectedItem) }
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = initialSelectedItem)

    val visibleItemsInfo by remember { derivedStateOf { scrollState.layoutInfo.visibleItemsInfo } }
    val firstVisibleItemIndex = visibleItemsInfo.firstOrNull()?.index ?: -1
    val lastVisibleItemIndex = visibleItemsInfo.lastOrNull()?.index ?: -1
    val totalVisibleItems = lastVisibleItemIndex - firstVisibleItemIndex + 1
    val middleIndex = firstVisibleItemIndex + totalVisibleItems / 2
    val bufferIndices = totalVisibleItems / 2

    LaunchedEffect(currentSelectedItem) {
        onItemSelected(currentSelectedItem)
    }

    LazyRow(
        modifier = modifier.width(effectiveWidth),
        state = scrollState,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(totalItems + totalVisibleItems) { index ->
            val adjustedIndex = index - bufferIndices

            if (index == middleIndex) {
                currentSelectedItem = adjustedIndex
            }

            val lineHeight = when {
                index == middleIndex -> selectedLineHeight
                adjustedIndex % 5 == 0 -> multipleOfFiveLineHeight
                else -> normalLineHeight
            }

            val paddingBottom = when {
                index == middleIndex -> selectedMultipleOfFiveLinePaddingBottom
                adjustedIndex % 5 == 0 -> normalMultipleOfFiveLinePaddingBottom
                else -> normalLinePaddingBottom
            }

            val lineTransparency = calculateLineTransparency(
                index,
                totalItems,
                bufferIndices,
                firstVisibleItemIndex,
                lastVisibleItemIndex,
                fadeOutLinesCount,
                maxFadeTransparency
            )

            VerticalLine(
                lineWidth = lineWidth,
                lineHeight = lineHeight,
                paddingBottom = paddingBottom,
                roundedCorners = lineRoundedCorners,
                indexAtCenter = index == middleIndex,
                lineTransparency = lineTransparency,
                selectedLineColor = selectedLineColor,
                unselectedLineColor = unselectedLineColor
            )

            Spacer(modifier = Modifier.width(lineSpacing))
        }
    }
}

@Composable
private fun VerticalLine(
    lineWidth: Dp,
    lineHeight: Dp,
    paddingBottom: Dp,
    roundedCorners: Dp,
    indexAtCenter: Boolean,
    lineTransparency: Float,
    selectedLineColor: Color,
    unselectedLineColor: Color
) {
    Box(
        modifier = Modifier
            .width(lineWidth)
            .height(lineHeight)
            .clip(RoundedCornerShape(roundedCorners))
            .alpha(lineTransparency)
            .background(if (indexAtCenter) selectedLineColor else unselectedLineColor)
            .padding(bottom = paddingBottom)
    )
}


private fun calculateLineTransparency(
    lineIndex: Int,
    totalLines: Int,
    bufferIndices: Int,
    firstVisibleItemIndex: Int,
    lastVisibleItemIndex: Int,
    fadeOutLinesCount: Int,
    maxFadeTransparency: Float
): Float {
    val actualCount = fadeOutLinesCount + 1
    val transparencyStep = maxFadeTransparency / actualCount

    return when {
        lineIndex < bufferIndices || lineIndex > (totalLines + bufferIndices) -> 0.0f
        lineIndex in firstVisibleItemIndex until firstVisibleItemIndex + fadeOutLinesCount -> {
            transparencyStep * (lineIndex - firstVisibleItemIndex + 1)
        }

        lineIndex in (lastVisibleItemIndex - fadeOutLinesCount + 1)..lastVisibleItemIndex -> {
            transparencyStep * (lastVisibleItemIndex - lineIndex + 1)
        }

        else -> 1.0f
    }
}