package com.example.whiskeyreviewer.component.myReview

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.LightOrangeColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.ConvertChartEntry
import com.example.whiskeyreviewer.utils.TimeFormatter
import com.example.whiskeyreviewer.view.testReviewDataList
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shadow
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.data.rememberExtraLambda
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.rememberVerticalLegend
import com.patrykandpatrick.vico.compose.common.shader.component
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.compose.common.shape.dashedShape
import com.patrykandpatrick.vico.compose.common.shape.markerCorneredShape
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.CartesianChart
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.HorizontalDimensions
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer.ColumnProvider.Companion.series
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarkerValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Defaults
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.LayeredComponent
import com.patrykandpatrick.vico.core.common.Legend
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.component.Shadow
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.MarkerCorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun MyReviewGraphComponent(
    writeReviewDataList: List<WriteReviewData> = (emptyList())
) {

    val sortedReviewDataList = writeReviewDataList.sortedBy { it.openDate }

//    val chartScrollState= rememberChartScrollState()

//
//    Chart(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(350.dp)
//            .padding(horizontal = 15.dp, vertical = 20.dp),
////        chart = columnChart(
////            columns = listOf(
////                lineComponent(
////                    color = MainColor,
////                    thickness = 5.dp,
////                    shape = Shapes.cutCornerShape(topRightPercent = 20, topLeftPercent = 20)
////                ),
////
////            ),
////            dataLabel = TextComponent.Builder().build()
////            ,
////            axisValuesOverrider = AxisValuesOverrider.fixed(
////                minY = 0f,
////                maxY = 5f,
////            ),
////            spacing = 40.dp
////        ),
//        chart = lineChart(
//            lines = listOf(
//                lineSpec(
//                    lineColor = MainColor,
//                    lineThickness = 3.dp,
//                ),
//
//                ),
//
//
//            axisValuesOverrider = AxisValuesOverrider.fixed(
//                minY = 0f,
//                maxY = 5f,
//            ),
//            spacing = 90.dp
//        ),
//
//        chartModelProducer = ChartEntryModelProducer(
//            ConvertChartEntry.convertChartEntry(sortedReviewDataList)
//        ),
//        startAxis = rememberStartAxis(
//
//            itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = 6)
//        ),
//        bottomAxis = rememberBottomAxis(
//            valueFormatter = { index, _ ->
//                TimeFormatter.formatDate(sortedReviewDataList[index.toInt()].openDate)
//            },
//            label = textComponent {
//                this.color = LightBlackColor.toArgb()
//                this.textSizeSp = 10f
//                this.lineCount = 2
//            }
//        ),
//
//    )
}

@Composable
fun Holder(

) {
//    val modelProducer = remember { CartesianChartModelProducer() }
//    LaunchedEffect(Unit) {
//        withContext(Dispatchers.Default) {
//            while (isActive) {
//                modelProducer.runTransaction {
//
//                    lineSeries { series(x = x, y = x.map { Random.nextFloat() * 30 - 10 }) }
//                }
//
//            }
//        }
//    }
//    MyReviewGraphComponent2()
}


@Composable
fun MyReviewGraphComponent2(
    writeReviewDataList: List<WriteReviewData> = (emptyList())
) {

    val TRANSACTION_INTERVAL_MS = 2000L

    val sortedReviewDataList = writeReviewDataList.sortedBy { it.openDate }

    val reviewDataMapList: Map<Int, Float> = sortedReviewDataList.mapIndexed { index, writeReviewData ->
        index to writeReviewData.score.toFloat()
    }.toMap()

    val bottomAxisValueFormatter = CartesianValueFormatter { _, x, _ ->
        TimeFormatter.formatDate(sortedReviewDataList[x.toInt()].openDate)
    }
    val verticalAxisValueFormatter = CartesianValueFormatter { _, y, _ ->
        "${y.toInt()} 점"
    }

    val modelProducer = remember { CartesianChartModelProducer() }



    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while (isActive) {
                modelProducer.runTransaction {

                    lineSeries {
                        series(reviewDataMapList.keys, reviewDataMapList.values)
                    }

                }
                delay(TRANSACTION_INTERVAL_MS)
            }
        }
    }


    CartesianChartHost(
        chart =
        rememberCartesianChart(

            //수직차트
//            rememberColumnCartesianLayer(
//                columnProvider =
//                ColumnCartesianLayer.ColumnProvider.series(
//                    rememberLineComponent(
//                        fill = fill(MainColor),
//                        thickness = 5.dp,
////                        shape = CorneredShape.rounded(allPercent = 40),
//                    )
//                ),
//                mergeMode = { ColumnCartesianLayer.MergeMode.Stacked },
//                verticalAxisPosition = Axis.Position.Vertical.Start,
//            ),
            //곡선 라인 그래프
//            rememberLineCartesianLayer(
//                lineProvider =
//                LineCartesianLayer.LineProvider.series(
//                    LineCartesianLayer.rememberLine(
//                        remember { LineCartesianLayer.LineFill.single(fill(color4)) }
//                    )
//                ),
//                verticalAxisPosition = Axis.Position.Vertical.End,
//                pointSpacing = 80.dp
//            ),
            //가파른 라인
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = remember { LineCartesianLayer.LineFill.single(fill(MainColor)) },
                        pointConnector = remember { LineCartesianLayer.PointConnector.cubic(curvature = 0f) },
                        //점 추가
//                        pointProvider = LineCartesianLayer.PointProvider.single(
//                            point=LineCartesianLayer.Point(
//
//                                component = rememberShapeComponent(
//                                    fill = fill(MainColor),
//                                    shape = CorneredShape.Pill,
////                                    strokeFill = fill(MainColor),
////                                    strokeThickness = 2.dp
//                                ),
//                                sizeDp = 10f
//                            )
//                        )
                        pointProvider = LineCartesianLayer.PointProvider.single(
                            point=LineCartesianLayer.Point(

                                component = LayeredComponent(
                                    rear = rememberShapeComponent(
                                        fill = fill(MainColor),
                                        shape = CorneredShape.Pill,

                                    ),
                                    padding = dimensions(2.dp),
                                    front =rememberShapeComponent(
                                        fill = fill(Color.White),
                                        shape = CorneredShape.Pill,
                                    ),
                                ),
                                sizeDp = 14f
                            )
                        )
                    )
                ),
                pointSpacing = 80.dp
            ),

            startAxis = VerticalAxis.rememberStart(
                label= TextComponent(
                    color= LightBlackColor.toArgb(),
                    textSizeSp =13f,
                    lineCount = 1,
                    typeface = Typeface.DEFAULT
                ),
                //구분선
                guideline = rememberAxisGuidelineComponent(),
                valueFormatter = verticalAxisValueFormatter,
                //플레이셔로 y축 간격 조정 가능
                itemPlacer = remember { VerticalAxis.ItemPlacer.count(count = { 6 }) },
            ),
//            endAxis = VerticalAxis.rememberEnd(guideline = null),
            bottomAxis =
            HorizontalAxis.rememberBottom(
                label= TextComponent(
                    color= LightBlackColor.toArgb(),
                    textSizeSp =10f,
                    lineCount = 2
                ),
                valueFormatter = bottomAxisValueFormatter,
                guideline = null,
                itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() }
            ),
            marker = rememberMarker(sortedReviewDataList = sortedReviewDataList),
            layerPadding = cartesianLayerPadding(scalableStart = 40.dp, scalableEnd = 40.dp),

        ),
        modelProducer = modelProducer,
        modifier = Modifier.padding(start=5.dp,end=5.dp).fillMaxHeight(),
        zoomState = rememberVicoZoomState(zoomEnabled = false),

    )

}

@Composable
internal fun rememberMarker(
    labelPosition: DefaultCartesianMarker.LabelPosition = DefaultCartesianMarker.LabelPosition.Top,
    showIndicator: Boolean = true,
    sortedReviewDataList:List<WriteReviewData>
): CartesianMarker {
    val labelBackgroundShape = markerCorneredShape(
        CorneredShape.Companion.rounded(10f)
    )
    val labelBackground =
        rememberShapeComponent(
            fill = fill(MaterialTheme.colorScheme.surfaceBright),
            shape = labelBackgroundShape,
            strokeFill = fill(Color.LightGray),
            strokeThickness = 0.5.dp
        )
    val label =
        rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = dimensions(12.dp, 8.dp),
            background = labelBackground,
            minWidth = TextComponent.MinWidth.fixed(40.dp),
            lineCount = 2
        )


    val guideline = rememberAxisGuidelineComponent()

    //마커는 스판을 사용해 각각 꾸며줄수있다
    val markerValueFormatter = CartesianMarkerValueFormatter{ _, target->
        val currentX = sortedReviewDataList[target.first().x.toInt()]

        val dateText = TimeFormatter.formatDate(currentX.openDate)
        val scoreText = "${currentX.score.toInt()} 점"

        val spanText = SpannableString("$scoreText\n$dateText")

        val scoreStartIndex = 0
        val scoreEndIndex = scoreStartIndex + scoreText.length
        spanText.setSpan(
            ForegroundColorSpan(LightBlackColor.toArgb()),
            scoreStartIndex, scoreEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanText.setSpan(
            StyleSpan(Typeface.BOLD),
            scoreStartIndex, scoreEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val dateStartIndex = scoreEndIndex + 1
        val dateEndIndex = dateText.length
        spanText.setSpan(
            ForegroundColorSpan(LightBlackColor.toArgb()),
            dateStartIndex, dateEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanText.setSpan(
            StyleSpan(Typeface.NORMAL),
            dateStartIndex, dateEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanText
    }


    return remember(label, labelPosition, showIndicator, guideline) {
        object :
            DefaultCartesianMarker(

                label = label,
                labelPosition = labelPosition,
                indicator =
                {color->
//                    ShapeComponent(
//                        fill = fill(MainColor),
//                        shape = CorneredShape.Pill,
////                        strokeFill = fill(MainColor),
////                        strokeThickness = 2.dp
//                    )
                    LayeredComponent(
                        rear = ShapeComponent(
                            Fill(
                                ColorUtils.setAlphaComponent(color, 38)),
                            CorneredShape.Pill
                        ),
                        front =
                        LayeredComponent(
                            rear =
                            ShapeComponent(
                                fill = Fill(Color.White.toArgb()),
                                shape = CorneredShape.Pill,
                            ),

                            front =
                            ShapeComponent(
                                fill = Fill(MainColor.toArgb()),
                                shape = CorneredShape.Pill,
                            ),
                            padding = dimensions(2.dp),
                        ),
                        padding = dimensions(6.dp),
                    )

                },
                indicatorSizeDp = 28f,
                guideline = guideline,
                valueFormatter = markerValueFormatter
            ) {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GraphPreview() {


    WhiskeyReviewerTheme {
        MyReviewGraphComponent(
            writeReviewDataList = testReviewDataList
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GraphPreview2() {


    WhiskeyReviewerTheme {
        MyReviewGraphComponent2(
            writeReviewDataList = testReviewDataList
        )
    }
}