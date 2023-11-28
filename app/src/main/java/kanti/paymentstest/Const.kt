package kanti.paymentstest

object Const {

	const val BASE_URL = "https://easypay.world/api-test/"

	const val DATABASE_NAME = "payments"

	const val APP_KEY_KEY = "app-key"
	const val APP_KEY_VALUE = "12345"

	const val API_VERSION_KEY = "v"
	const val API_VERSION_VALUE = "1"

	const val DATA_STORE_KEY_TOKEN = "token"

	object Gson {
		object Payments {
			const val ID_KEY = "id"
			const val TITLE_KEY = "title"
			const val AMOUNT_KEY = "amount"
			const val CREATED_KEY = "created"
		}
	}

}