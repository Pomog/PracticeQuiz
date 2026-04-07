package com.demo.practicequiz.repo

import com.demo.practicequiz.data.DataOrException
import com.demo.practicequiz.model.QuestionItem
import com.demo.practicequiz.network.QuestionsAPI
import jakarta.inject.Inject

class QuestionRepository @Inject constructor(
    private val api: QuestionsAPI
){
    private val listOfQuestion
    = DataOrException<ArrayList<QuestionItem>,
    Boolean,
    Exception>()
}