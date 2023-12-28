package kanti.paymentstest.data.model.payments

import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.payments.datasource.local.PaymentsLocalDataSource
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteDataSource
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteResult
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
	private val remoteDataSource: PaymentsRemoteDataSource,
	private val localDataSource: PaymentsLocalDataSource
) : PaymentsRepository {

	override suspend fun getPayments(authToken: LoginToken): PaymentsResult {
		return when (val remoteResult = remoteDataSource.getPayments(authToken.token)) {
			is PaymentsRemoteResult.Success -> {
				localDataSource.deleteCache()
				localDataSource.cachePayments(remoteResult.payments)
				PaymentsResult.Success(remoteResult.payments)
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