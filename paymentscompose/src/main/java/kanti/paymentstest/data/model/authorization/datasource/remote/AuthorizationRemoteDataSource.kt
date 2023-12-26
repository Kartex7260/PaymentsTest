package kanti.paymentstest.data.model.authorization.datasource.remote

import kanti.paymentstest.data.model.authorization.AuthorizationResult

interface AuthorizationRemoteDataSource {

	suspend fun login(login: String, password: String): AuthorizationResult

}