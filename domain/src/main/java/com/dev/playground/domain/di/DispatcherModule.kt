package com.dev.playground.domain.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    factory(qualifier = named(IO)) { Dispatchers.IO }
    factory(qualifier = named(MAIN)) { Dispatchers.Main }
    factory(qualifier = named(DEFAULT)) { Dispatchers.Default }
}

const val IO = "dispatcher_io"
const val MAIN = "dispatcher_main"
const val DEFAULT = "dispatcher_work"