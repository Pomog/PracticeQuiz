package com.demo.practicequiz.network

import com.demo.practicequiz.model.Question
import javax.inject.Singleton
import retrofit2.http.GET

@Singleton
interface QuestionsAPI {
    @GET("world.json")
    suspend fun getAllQuestions(): Question

}