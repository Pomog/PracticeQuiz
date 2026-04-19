package com.demo.practicequiz.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.practicequiz.screens.QuestionsViewModel
import com.demo.practicequiz.util.AppColors

@Composable
fun Questions(
    viewModel: QuestionsViewModel,
) {
    val state = viewModel.data.value
    val questions = state.data

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
        Log.d("Loading", "Questions are loading...")
    } else {
        questions?.forEach { item ->
            Log.d("Result", "Question = ${item.question}")
        }
        Log.d("SIZE", "size = ${questions?.size}")
    }

}

@Composable
fun QuestionDisplay() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top,
            horizontalAlignment = androidx.compose.ui.Alignment.Start
        ) {
            QuestionTracker()
        }

    }
}

@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
                withStyle(style = SpanStyle(color = AppColors.mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp)) {
                    append("$outOf")
                }
            }
        }
    },
        modifier = Modifier.padding(20.dp)
    )
}