package com.turastory.miniaccount.di

import com.turastory.miniaccount.LocalRepository
import com.turastory.miniaccount.Repository
import com.turastory.miniaccount.TransactionListUseCase
import com.turastory.miniaccount.mapper.TransactionMapper
import org.koin.dsl.module.module

val appModule = module {
    single { TransactionMapper() }
    single { LocalRepository(get()) as Repository }
    single { TransactionListUseCase(get()) }
}