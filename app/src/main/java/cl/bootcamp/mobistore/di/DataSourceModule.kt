package cl.bootcamp.mobistore.di

import android.content.Context
import androidx.room.Room
import cl.bootcamp.mobistore.dataSource.DbDataSource
import cl.bootcamp.mobistore.dataSource.RestDataSource
import cl.bootcamp.mobistore.room.PhoneDao
import cl.bootcamp.mobistore.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): RestDataSource =
        retrofit.create(RestDataSource::class.java)

    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context: Context): DbDataSource {
        return Room.databaseBuilder(
            context,
            DbDataSource::class.java,
            "phone_db"
        )
            .fallbackToDestructiveMigrationFrom()
            .build()
    }

    @Singleton
    @Provides
    fun PhoneDao(db: DbDataSource): PhoneDao = db.phoneDao()

}