package kanti.paymentstest.data.model.authorization

import kanti.paymentstest.data.model.authorization.datasource.local.AuthorizationLocalDataSource
import kanti.paymentstest.data.model.authorization.datasource.remote.AuthorizationRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
	private val localDataSource: AuthorizationLocalDataSource,
	private val remoteDataSource: AuthorizationRemoteDataSource
) : AuthorizationRepository {

	override val token: Flow<TokenResult>
		get() = localDataSource.token

	override suspend fun login(login: String, password: String): AuthorizationResult {
		val authResult = remoteDataSource.login(login, password)
		if (authResult is AuthorizationResult.Success) {
			val result = localDataSource.setToken(authResult.loginToken)
			if (result.isFailure) {
				val th = result.exceptionOrNull()
				return AuthorizationResult.Fail(
					message = th?.message ?: "[Not message]",
					throwable = th
				)
			}
		}
		return authResult
	}

	override suspend fun logout(): Result<Unit> {
		return localDataSource.deleteToken()
	}
}