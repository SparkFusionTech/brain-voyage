package com.sparkfusion.quiz.brainvoyage.data.di

import com.sparkfusion.quiz.brainvoyage.data.repository.AnswerRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.CatalogProgressRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.ImageSearchRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.LoginRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.Online2VS2GameRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.QuestionRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.QuizRatingRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.QuizRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.TagRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAnswerRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ICatalogProgressRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IImageSearchRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IOnline2VS2GameRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuestionRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRatingRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ITagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindLoginRepositoryToILoginRepository(loginRepository: LoginRepository): ILoginRepository

    @Binds
    fun bindQuizRepositoryToIQuizRepository(quizRepository: QuizRepository): IQuizRepository

    @Binds
    fun bindImageSearchRepositoryToIImageSearchRepository(
        searchImageRepository: ImageSearchRepository
    ): IImageSearchRepository

    @Binds
    fun bindTagRepositoryToITagRepository(
        tagRepository: TagRepository
    ): ITagRepository

    @Binds
    fun bindQuestionRepositoryToIQuestionRepository(
        questionRepository: QuestionRepository
    ): IQuestionRepository

    @Binds
    fun bindAnswerRepositoryToIAnswerRepository(
        answerRepository: AnswerRepository
    ): IAnswerRepository

    @Binds
    fun bindCatalogProgressRepositoryToICatalogProgressRepository(
        catalogProgressRepository: CatalogProgressRepository
    ): ICatalogProgressRepository

    @Binds
    fun bindOnline2VS2GameRepositoryToIOnline2VS2GameRepository(
        online2VS2GameRepository: Online2VS2GameRepository
    ): IOnline2VS2GameRepository

    @Binds
    fun bindQuizRatingRepositoryToIQuizRatingRepository(
        quizRatingRepository: QuizRatingRepository
    ): IQuizRatingRepository
}

















