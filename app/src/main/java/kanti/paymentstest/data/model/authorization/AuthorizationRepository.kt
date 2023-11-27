package kanti.paymentstest.data.model.authorization

import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {

	val token: Flow<TokenResult>

	suspend fun login(login: String, password: String): AuthorizationResult

	suspend fun logout(): Result<Unit>

}