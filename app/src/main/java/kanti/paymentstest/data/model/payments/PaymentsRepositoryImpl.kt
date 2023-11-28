package kanti.paymentstest.data.model.payments

import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteDataSource
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsRemoteResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
	private val remoteDataSource: PaymentsRemoteDataSource
) : PaymentsRepository {

	private val _payments = MutableStateFlow<List<Payment>>(listOf())
	override val payments: Flow<List<Payment>>
		get() = _payments

	override suspend fun getPayments(authToken: String): PaymentsResult {
		return when (val remoteResult = remoteDataSource.getPayments(authToken)) {
			is PaymentsRemoteResult.Success -> {
				_payments.value = remoteResult.payments
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

}