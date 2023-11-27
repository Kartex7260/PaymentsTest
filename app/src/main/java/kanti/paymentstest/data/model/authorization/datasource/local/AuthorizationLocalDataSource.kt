package kanti.paymentstest.data.model.authorization.datasource.local

import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.authorization.TokenResult
import kotlinx.coroutines.flow.Flow

interface AuthorizationLocalDataSource {

	val token: Flow<TokenResult>

	suspend fun setToken(token: LoginToken): Result<Unit>

	suspend fun deleteToken(): Result<Unit>

}