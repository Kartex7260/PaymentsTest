package kanti.paymentstest.data.model.authorization.datasource.remote

import kanti.paymentstest.data.model.authorization.LoginToken
import javax.inject.Inject

class AuthorizationRetrofitDataSource @Inject constructor(
	private val authorizationService: AuthorizationService
) : AuthorizationRemoteDataSource {

	private val unexpectedError = "Unexpected error!"

	override suspend fun login(login: String, password: String): AuthorizationRemoteResult {
		val tokenDto = authorizationService.login(
			LoginDTO(
				login = login,
				password = password
			)
		)
		return if (tokenDto.success && tokenDto.response != null) {
			AuthorizationRemoteResult.Success(
				LoginToken(
					token = tokenDto.response.token
				)
			)
		} else if (!tokenDto.success && tokenDto.error != null) {
			when (tokenDto.error.errorCode) {
				1003 -> {
					AuthorizationRemoteResult.IncorrectCredentials
				}
				else -> AuthorizationRemoteResult.Error(tokenDto.error)
			}
		} else {
			AuthorizationRemoteResult.Fail(unexpectedError)
		}
	}

}