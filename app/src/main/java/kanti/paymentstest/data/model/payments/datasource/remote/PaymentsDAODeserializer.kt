package kanti.paymentstest.data.model.payments.datasource.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kanti.paymentstest.Const
import java.lang.ClassCastException
import java.lang.NumberFormatException
import java.lang.reflect.Type

object PaymentsDAODeserializer : JsonDeserializer<PaymentDAO> {

	private val idKey = Const.Gson.Payments.ID_KEY
	private val titleKey = Const.Gson.Payments.TITLE_KEY
	private val amountKey = Const.Gson.Payments.AMOUNT_KEY
	private val createdKey = Const.Gson.Payments.CREATED_KEY

	override fun deserialize(
		json: JsonElement,
		typeOfT: Type?,
		context: JsonDeserializationContext?
	): PaymentDAO {
		val jsonObject = json.asJsonObject
		val id = jsonObject.get(idKey).asInt
		val title = jsonObject.get(titleKey).asString
		val amount = jsonObject.getAmount()
		val created = jsonObject.getCreated()

		return PaymentDAO(
			id = id,
			title = title,
			amount = amount,
			created = created
		)
	}

	private fun JsonObject.getAmount(): Double? {
		val amount = get(amountKey) ?: return null

		val isDouble = amount.isDouble()
		if (isDouble != null)
			return isDouble

		return isDoubleInString()
	}

	private fun JsonObject.getCreated(): Long? {
		val created = get(createdKey) ?: return null
		return try {
			created.asLong
		} catch (ex: UnsupportedOperationException) {
			null
		}
	}

	private fun JsonElement.isDouble(): Double? {
		return try {
			asDouble
		} catch (ex: ClassCastException) {
			null
		} catch (ex: NumberFormatException) {
			null
		} catch (ex: UnsupportedOperationException) {
			null
		}
	}

	private fun JsonObject.isDoubleInString(): Double? {
		return try {
			val stg = asString
			if (stg.isBlank())
				return null

			stg.toDouble()
		} catch (ex: ClassCastException) {
			null
		} catch (ex: NumberFormatException) {
			null
		} catch (ex: UnsupportedOperationException) {
			null
		}
	}

}