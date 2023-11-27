package kanti.paymentstest.data.model.common

suspend fun <T> localResultTryCatch(block: suspend () -> Result<T>): Result<T> {
	return try {
		block()
	} catch (th: Throwable) {
		Result.failure(th)
	}
}

suspend fun localTryCatch(block: suspend () -> Unit): Result<Unit> {
	return localResultTryCatch {
		block()
		Result.success(Unit)
	}
}