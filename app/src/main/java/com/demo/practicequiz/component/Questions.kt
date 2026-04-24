package com.demo.practicequiz.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.practicequiz.model.QuestionItem
import com.demo.practicequiz.screens.QuestionsViewModel
import com.demo.practicequiz.util.AppColors

@Composable
fun Questions(
    viewModel: QuestionsViewModel,
) {
    val state = viewModel.data.value
    val questions = state.data
    val questionIndex = remember { mutableIntStateOf(0) }

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
        Log.d("Loading", "Questions are loading...")
    } else {

        val question = try {
            questions?.get(questionIndex.intValue)
        } catch (ex: Exception) {
            null
            Log.d("Error", "Questions: ${ex.localizedMessage}")
        }

        if (questions != null) {
            QuestionDisplay(
                question = question!! as QuestionItem,
                questionIndex = questionIndex,
                viewModel = viewModel
            ) {
                questionIndex.intValue = questionIndex.intValue + 1
            }
        }
        Log.d("SIZE", "size = ${questions?.size}")
    }

}

@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableIntState,
    viewModel: QuestionsViewModel,
    onNextClicked: (Int) -> Unit = {}
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 15f), 0f)
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.mDarkPurple
    ) {
        val choicesState = remember(question) {
            question.choices.toMutableList()
        }

        val answerState = remember(question) { mutableStateOf<Int?>(null) }
        val correctAnswerState = remember(question) { mutableStateOf<Boolean?>(null) }

        val updateAnswer: (Int) -> Unit = remember(question) {
            {
                answerState.value = it
                correctAnswerState.value = choicesState[it] == question.answer
            }
        }

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top,
            horizontalAlignment = androidx.compose.ui.Alignment.Start
        ) {
            QuestionTracker()
            DrawDottedLine(pathEffect = pathEffect)

            Column {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(androidx.compose.ui.Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 17.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                choicesState.forEachIndexed { index, answerText ->

                    val isSelected = answerState.value == index
                    val isCorrectSelected = isSelected && correctAnswerState.value == true
                    val isWrongSelected = isSelected && correctAnswerState.value == false


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .height(45.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomStartPercent = 50,
                                    bottomEndPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = when {
                                    isCorrectSelected -> Color.Yellow
                                    isWrongSelected -> Color.Blue
                                    else -> AppColors.mLightBlue
                                },
                                unselectedColor = AppColors.mLightGray,
                                disabledSelectedColor = AppColors.mLightGray,
                                disabledUnselectedColor = AppColors.mLightGray
                            )
                        )
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = when {
                                        isCorrectSelected -> Color.Yellow
                                        isWrongSelected -> Color.Blue
                                        else -> AppColors.mOffWhite
                                    },
                                    fontSize = 17.sp
                                )
                            ) {
                                append(answerText)
                            }
                        }
                        Text(
                            text = annotatedString,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
                Button(
                    onClick = { onNextClicked(questionIndex.intValue) },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.mLightBlue
                    )
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}


@Composable
fun DrawDottedLine(pathEffect: androidx.compose.ui.graphics.PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = androidx.compose.ui.geometry.Offset(0f, 0f),
            end = androidx.compose.ui.geometry.Offset(size.width, 0f),
            pathEffect = pathEffect
        )


    }

}


@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                ) {
                    append("Question $counter/")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.mLightGray,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    ) {
                        append("$outOf")
                    }
                }
            }
        },
        modifier = Modifier.padding(20.dp)
    )
}

@Preview
@Composable
fun ShowProgress(score: Int = 12, total: Int = 100) {
    val progress = (score.toFloat() / total.toFloat()).coerceIn(0f, 1f)
    val gradient = Brush.linearGradient(
        listOf(
            AppColors.mLightPurple,
            AppColors.mOffDarkPurple
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(45.dp)
            .border(
                width = 4.dp, brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightPurple,
                        AppColors.mLightPurple
                    )
                ),
                shape = RoundedCornerShape(34)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {

        }


    }
}
