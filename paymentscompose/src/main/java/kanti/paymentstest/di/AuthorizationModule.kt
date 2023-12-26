package kanti.paymentstest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.paymentstest.data.model.authorization.AuthorizationRepository
import kanti.paymentstest.data.model.authorization.AuthorizationRepositoryImpl
import kanti.paymentstest.data.model.authorization.datasource.local.AuthorizationDataStoreDataSource
import kanti.paymentstest.data.model.authorization.datasource.local.AuthorizationLocalDataSource
import kanti.paymentstest.data.model.authorization.datasource.remote.AuthorizationRemoteDataSource
import kanti.paymentstest.data.model.authorization.datasource.remote.AuthorizationRetrofitDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthorizationModule {

	@Binds
	abstract fun bindAuthorizationRetrofitDataSource(
		dataSource: AuthorizationRetrofitDataSource
	): AuthorizationRemoteDataSource

	@Binds
	abstract fun bindAuthorizationDataStoreDataSource(
		dataSource: AuthorizationDataStoreDataSource
	): AuthorizationLocalDataSource

	@Binds
	abstract fun bindAuthorizationRepositoryImpl(
		repository: AuthorizationRepositoryImpl
	): AuthorizationRepository

}