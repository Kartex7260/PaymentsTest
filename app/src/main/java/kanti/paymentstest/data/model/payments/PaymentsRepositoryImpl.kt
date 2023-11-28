package kanti.paymentstest.data.model.payments

import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteDataSource
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
	private val remoteDataSource: PaymentsRemoteDataSource
) : PaymentsRepository {

	override suspend fun getPayments(authToken: String): PaymentsResult {
		return remoteDataSource.getPayments(authToken)
	}

}