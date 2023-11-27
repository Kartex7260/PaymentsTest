package kanti.paymentstest.data.model.authorization.datasource.remote

interface AuthorizationRemoteDataSource {

	suspend fun login(login: String, password: String): AuthorizationRemoteResult

}