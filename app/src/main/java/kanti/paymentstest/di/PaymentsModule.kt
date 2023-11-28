package kanti.paymentstest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.paymentstest.data.model.payments.PaymentsRepository
import kanti.paymentstest.data.model.payments.PaymentsRepositoryImpl
import kanti.paymentstest.data.model.payments.datasource.local.PaymentsLocalDataSource
import kanti.paymentstest.data.model.payments.datasource.local.PaymentsRoomDataSource
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
	fun bindPaymentsRoomDataSource(
		dataSource: PaymentsRoomDataSource
	): PaymentsLocalDataSource

	@Binds
	@Singleton
	fun bindPaymentsRepositoryImpl(
		repository: PaymentsRepositoryImpl
	): PaymentsRepository

}