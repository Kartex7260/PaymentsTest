package kanti.paymentstest.data.model.payments

import kanti.paymentstest.data.model.payments.datasource.local.PaymentsLocalDataSource
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteDataSource
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
	private val remoteDataSource: PaymentsRemoteDataSource,
	private val localDataSource: PaymentsLocalDataSource
) : PaymentsRepository {

	override val payments: Flow<List<Payment>>
		get() = localDataSource.payments

	override suspend fun getPayments(authToken: String): PaymentsResult {
		return when (val remoteResult = remoteDataSource.getPayments(authToken)) {
			is PaymentsRemoteResult.Success -> {
				localDataSource.deleteCache()
				localDataSource.cachePayments(remoteResult.payments)
				PaymentsResult.Success
			}
			is PaymentsRemoteResult.NoConnection -> {
				PaymentsResult.NoConnection
			}
			is PaymentsRemoteResult.IncorrectToken -> {
				PaymentsResult.IncorrectToken
			}
			is PaymentsRemoteResult.Error -> {
				PaymentsResult.Error(remoteResult.error)
			}
			is PaymentsRemoteResult.Fail -> {
				PaymentsResult.Fail(remoteResult.message, remoteResult.th)
			}
		}
	}

	override suspend fun deleteCache(): Result<Unit> {
		return localDataSource.deleteCache()
	}
}