package me.arwan.mov.inject

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.arwan.mov.data.remote.services.MoviesServices
import me.arwan.mov.data.remote.datasource.MoviesRemoteDataSource
import me.arwan.mov.data.remote.datasource.MoviesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.arwan.mov.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Named("BaseUrl")
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL_MOVIES

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY_MOVIES)
                .addQueryParameter("language", "en-US")
                .build()
            val request = chain.request().newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BaseUrl") baseUrl: String,
        gson: Gson,
    ): Retrofit = Retrofit.Builder().client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideMoviesApiServices(
        retrofit: Retrofit,
    ): MoviesServices = retrofit.create(MoviesServices::class.java)

    @Singleton
    @Provides
    fun provideMoviesDataSource(
        moviesServices: MoviesServices,
    ): MoviesRemoteDataSource = MoviesRemoteDataSourceImpl(moviesServices)

}