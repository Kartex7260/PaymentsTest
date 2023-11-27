package kanti.paymentstest.data.model.common

sealed class LocalResult<T> {

	class Success<T>(
		val value: T
	) : LocalResult<T>()

	class Fail<T>(
		val message: String,
		val throwable: Throwable?
	) : LocalResult<T>()

}
