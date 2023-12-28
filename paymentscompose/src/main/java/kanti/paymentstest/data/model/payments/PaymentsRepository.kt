package kanti.paymentstest.data.model.payments

import kanti.paymentstest.data.model.authorization.LoginToken

interface PaymentsRepository {

	suspend fun getPayments(authToken: LoginToken): PaymentsResult

	suspend fun deleteCache(): Result<Unit>

}