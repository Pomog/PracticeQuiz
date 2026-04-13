package com.demo.practicequiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.demo.practicequiz.screens.QuestionsViewModel
import com.demo.practicequiz.ui.theme.PracticeQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeQuizTheme {
                Surface {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home(
    viewModel: QuestionsViewModel = hiltViewModel()
) {
    Questions(viewModel = viewModel)
}

@Composable
fun Questions(
    viewModel: QuestionsViewModel,
){
    val questions = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true){
        Log.d("Loading", "Questions: ... Loading STOPPED ...")
    } else {
        questions?.forEach { item ->
            Log.d("Result", "Questions = ${item.question}")
        }


    }
    Log.d("SIZE", "size = ${questions?.size}")

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PracticeQuizTheme {

    }
}