package com.tanishkej.onerep.di

import android.content.Context
import com.tanishkej.onerep.OneRepApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext app: Context): OneRepApplication {
        return app as OneRepApplication
    }

}