package com.qlmat.android.onelab_project.core.di

import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.qlmat.android.onelab_project.BuildConfig
import com.qlmat.android.onelab_project.core.NetworkChecker
import com.qlmat.android.onelab_project.core.interceptor.QueryInterceptor
import com.qlmat.android.onelab_project.core.notification.CustomNotificationManager
import com.qlmat.android.onelab_project.data.DATABASE_NAME_MOVIES
import com.qlmat.android.onelab_project.data.api.MoviesApi
import com.qlmat.android.onelab_project.data.local.FavoritesDao
import com.qlmat.android.onelab_project.data.local.MoviesDao
import com.qlmat.android.onelab_project.data.local.MyRoomDatabase
import com.qlmat.android.onelab_project.data.repository.FavoriteMoviesRepositoryImpl
import com.qlmat.android.onelab_project.data.repository.PopularMoviesRepositoryImpl
import com.qlmat.android.onelab_project.domain.FavoriteMoviesRepository
import com.qlmat.android.onelab_project.domain.PopularMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkChecker(context)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(QueryInterceptor(hashMapOf("api_key" to BuildConfig.TMDB_API_KEY)) )
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(okHttpClient: OkHttpClient): MoviesApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRoomDatabase(@ApplicationContext context: Context) : MyRoomDatabase {
        return Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java,
            DATABASE_NAME_MOVIES
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(myRoomDatabase: MyRoomDatabase) : MoviesDao {
        return myRoomDatabase.moviesDao()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(myRoomDatabase: MyRoomDatabase) : FavoritesDao {
        return myRoomDatabase.favouritesDao()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(
        movieDao: MoviesDao,
        moviesApi: MoviesApi,
        networkChecker: NetworkChecker
    ): PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(
            moviesApi = moviesApi,
            moviesDao = movieDao,
            networkChecker = networkChecker
        )
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        favoritesDao: FavoritesDao
    ): FavoriteMoviesRepository {
        return FavoriteMoviesRepositoryImpl(
            favoritesDao = favoritesDao
        )
    }

    @Provides
    @Singleton
    fun provideCustomNotificationManager(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager
    ): CustomNotificationManager {
        return CustomNotificationManager(context, notificationManager)
    }

//    @Provides
//    @Singleton
//    fun provideMoviesPagingSource(
//        moviesApi: MoviesApi,
//        networkChecker: NetworkChecker
//    ): MoviesPagingSource {
//        return MoviesPagingSource(
//            moviesApi = moviesApi,
//            networkChecker = networkChecker
//        )
//    }

}

