package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quiz_info

import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.SubmittedQuizModel
import com.sparkfusion.quiz.brainvoyage.domain.model.tag.TagModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface MyQuizInfoContract {

    sealed interface Intent : CommonIntent {
        data class ReadQuiz(val quizId: Long) : Intent
        data class ReadTags(val quizId: Long) : Intent
        data class ReadQuestions(val quizId: Long) : Intent
    }

    sealed interface QuizReadingState {
        data object Loading : QuizReadingState
        data object Error : QuizReadingState
        data class Success(val quiz: SubmittedQuizModel) : QuizReadingState
    }

    sealed interface TagsReadingState {
        data object Empty : TagsReadingState
        data object Loading : TagsReadingState
        data object Error : TagsReadingState
        data class Success(val tags: List<TagModel>) : TagsReadingState
    }

    sealed interface QuestionsReadingState {
        data object Empty : QuestionsReadingState
        data object Loading : QuestionsReadingState
        data object Error : QuestionsReadingState
        data class Success(val tags: List<QuestionModel>) : QuestionsReadingState
    }

    sealed interface AnswersReadingState {
        data object Empty : AnswersReadingState
        data object Loading : AnswersReadingState
        data object Error : AnswersReadingState
        data object Success : AnswersReadingState
    }
}














