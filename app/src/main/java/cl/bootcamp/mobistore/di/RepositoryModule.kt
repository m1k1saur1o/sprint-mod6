package cl.bootcamp.mobistore.di

import cl.bootcamp.mobistore.repository.PhoneRepository
import cl.bootcamp.mobistore.repository.PhoneRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun phoneRepository(
        phoneRepositoryImp: PhoneRepositoryImp
    ): PhoneRepository
}