package com.github.watabee.anilist.core.network.di

import android.util.Log
import coil.util.DebugLogger
import coil.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
internal object DebugNetworkModule {

    @Provides
    fun provideLogger(): Logger = DebugLogger(Log.VERBOSE)

    @Provides
    @IntoSet
    fun provideOkHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor(logger = { Timber.tag("OkHttp").w(it) })
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}
