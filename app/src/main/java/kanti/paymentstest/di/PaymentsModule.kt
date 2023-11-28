package kanti.paymentstest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.paymentstest.data.model.payments.PaymentsRepository
import kanti.paymentstest.data.model.payments.PaymentsRepositoryImpl
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteDataSource
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRetrofitDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PaymentsModule {

	@Binds
	fun bindPaymentsRetrofitDataSource(
		dataSource: PaymentsRetrofitDataSource
	): PaymentsRemoteDataSource

	@Binds
	@Singleton
	fun bindPaymentsRepositoryImpl(
		repository: PaymentsRepositoryImpl
	): PaymentsRepository

}