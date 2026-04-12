package com.demo.practicequiz.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.practicequiz.data.DataOrException
import com.demo.practicequiz.model.QuestionItem
import com.demo.practicequiz.repo.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repo: QuestionRepository
): ViewModel(){
    private val data: MutableState<DataOrException<ArrayList<QuestionItem>,
            Boolean, Exception>> = mutableStateOf(DataOrException(
        null,true,
        Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repo.getAllQuestions()
            if(data.value.data.toString().isNotEmpty()){
                data.value.loading = false
                }
        }

    }

}