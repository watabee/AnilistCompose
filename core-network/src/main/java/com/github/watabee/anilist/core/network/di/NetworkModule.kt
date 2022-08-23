package com.github.watabee.anilist.core.network.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.github.watabee.anilist.core.network.NetworkDataSource
import com.github.watabee.anilist.core.network.graphql.GraphqlDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECT_TIMEOUT_SECONDS = 10L
private const val READ_TIMEOUT_SECONDS = 10L

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    @Binds
    abstract fun bindNetworkDataSource(dataSource: GraphqlDataSource): NetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()

        @Provides
        @ElementsIntoSet
        fun provideEmptyInterceptors(): Set<Interceptor> {
            return emptySet()
        }

        @Provides
        @Singleton
        fun provideApolloClient(okHttpClient: OkHttpClient, interceptors: Set<@JvmSuppressWildcards Interceptor>): ApolloClient {
            return ApolloClient.Builder()
                .serverUrl("https://graphql.anilist.co")
                .okHttpClient(
                    okHttpClient.newBuilder()
                        .apply {
                            interceptors.forEach(::addInterceptor)
                        }
                        .build()
                )
                .build()
        }
    }
}
