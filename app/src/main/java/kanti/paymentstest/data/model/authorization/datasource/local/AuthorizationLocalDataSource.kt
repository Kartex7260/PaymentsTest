package kanti.paymentstest.data.model.authorization.datasource.local

import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.common.LocalResult
import kotlinx.coroutines.flow.Flow

interface AuthorizationLocalDataSource {

	val token: Flow<TokenLocalResult>

	suspend fun setToken(token: LoginToken): LocalResult<Unit>

	suspend fun deleteToken(): LocalResult<Unit>

}