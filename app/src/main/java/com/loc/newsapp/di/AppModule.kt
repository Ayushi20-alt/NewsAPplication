package com.loc.newsapp.di

import android.app.Application
import androidx.room.Room
import com.loc.newsapp.data.local.NewsDao
import com.loc.newsapp.data.local.NewsDatabase
import com.loc.newsapp.data.local.TypeConvertor
import com.loc.newsapp.data.manager.LocalUserManagerImpl
import com.loc.newsapp.data.remote.NewsApi
import com.loc.newsapp.data.repository.NewsRepositoryImpl
import com.loc.newsapp.domain.manager.LocalUserManager
import com.loc.newsapp.domain.repository.NewsRepository
import com.loc.newsapp.domain.usecases.app_entry.AppEntryUsecase
import com.loc.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.loc.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.loc.newsapp.domain.usecases.news.DeleteArticle
import com.loc.newsapp.domain.usecases.news.GetNews
import com.loc.newsapp.domain.usecases.news.NewsUseCases
import com.loc.newsapp.domain.usecases.news.SearchNews
import com.loc.newsapp.domain.usecases.news.SelectArticle
import com.loc.newsapp.domain.usecases.news.UpsertArticle
import com.loc.newsapp.util.Constants.BASE_URL
import com.loc.newsapp.util.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // this will live as long as the app will alive
object AppModule {

    // dependency for local User Manager
    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application) : LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUsecase(localUserManager: LocalUserManager) = AppEntryUsecase(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun ProvidesNewsApi() : NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // to convert json to kotlin object
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun proviesNewsRepository(
        newsApi: NewsApi
    ): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ) : NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsDao),
            deleteArticle = DeleteArticle(newsDao),
            selectArticle = SelectArticle(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(TypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsDao(newsDatabase: NewsDatabase) : NewsDao = newsDatabase.newsDao

}