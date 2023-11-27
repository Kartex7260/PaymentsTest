package kanti.paymentstest.data.model.authorization.datasource.local

import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.authorization.TokenResult
import kanti.paymentstest.data.model.common.LocalResult
import kotlinx.coroutines.flow.Flow

interface AuthorizationLocalDataSource {

	val token: Flow<TokenResult>

	suspend fun setToken(token: LoginToken): LocalResult<Unit>

	suspend fun deleteToken(): LocalResult<Unit>

}