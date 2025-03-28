package com.sparkfusion.quiz.brainvoyage.data.di

import com.sparkfusion.quiz.brainvoyage.data.datastore.AccountEmailStore
import com.sparkfusion.quiz.brainvoyage.data.datastore.AccountInfoStore
import com.sparkfusion.quiz.brainvoyage.data.datastore.SaveAccountSignInStore
import com.sparkfusion.quiz.brainvoyage.data.datastore.Session
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountEmailStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISaveAccountSignInStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISession
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    fun bindSessionToISession(session: Session): ISession

    @Binds
    fun bindAccountInfoStoreToIAccountInfoStore(accountInfoStore: AccountInfoStore): IAccountInfoStore

    @Binds
    fun bindSaveAccountSignInStoreToISaveAccountSignInStore(saveAccountSignInStore: SaveAccountSignInStore): ISaveAccountSignInStore

    @Binds
    fun bindAccountEmailStoreToIAccountEmailStore(accountEmailStore: AccountEmailStore): IAccountEmailStore
}
















