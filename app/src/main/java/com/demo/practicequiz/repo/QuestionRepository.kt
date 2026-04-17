package com.demo.practicequiz.repo

import android.util.Log
import com.demo.practicequiz.data.DataOrException
import com.demo.practicequiz.model.QuestionItem
import com.demo.practicequiz.network.QuestionsAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val api: QuestionsAPI
){
    private val dataOrException
    = DataOrException<ArrayList<QuestionItem>,
    Boolean,
    Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception>{
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (e: Exception) {
            dataOrException.e = e
            Log.d("Exception", "getAllQuestions: ${dataOrException.e?.localizedMessage}")
        }

        return dataOrException
    }
}