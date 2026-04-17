package com.demo.practicequiz.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.demo.practicequiz.screens.QuestionsViewModel

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