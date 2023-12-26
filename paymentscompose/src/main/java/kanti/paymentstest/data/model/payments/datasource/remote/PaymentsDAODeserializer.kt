package kanti.paymentstest.data.model.payments.datasource.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kanti.paymentstest.Const
import java.lang.ClassCastException
import java.lang.reflect.Type

object PaymentsDAODeserializer : JsonDeserializer<PaymentDTO> {

	private const val idKey = Const.Gson.Payments.ID_KEY
	private const val titleKey = Const.Gson.Payments.TITLE_KEY
	private const val amountKey = Const.Gson.Payments.AMOUNT_KEY
	private val createdKey = Const.Gson.Payments.CREATED_KEY

	override fun deserialize(
		json: JsonElement,
		typeOfT: Type?,
		context: JsonDeserializationContext?
	): PaymentDTO {
		val jsonObject = json.asJsonObject
		val id = jsonObject.getId()
		val title = jsonObject.getTitle()
		val amount = jsonObject.getAmount()
		val created = jsonObject.getCreated()

		return PaymentDTO(
			id = id,
			title = title,
			amount = amount,
			created = created
		)
	}

	private fun JsonObject.getId(): Int? {
		val id = get(idKey) ?: return null
		return id.getInt()
	}

	private fun JsonObject.getTitle(): String? {
		val title = get(titleKey) ?: return null
		return title.getString()
	}

	private fun JsonObject.getAmount(): Double? {
		val amount = get(amountKey) ?: return null
		return amount.getDouble()
	}

	private fun JsonObject.getCreated(): Long? {
		val created = get(createdKey) ?: return null
		return created.getLong()
	}

	private fun JsonElement.getInt(): Int? {
		return try {
			asInt
		} catch (ex: ClassCastException) {
			val stg = getString() ?: return null
			stg.toIntOrNull()
		} catch (th: Throwable) {
			null
		}
	}

	private fun JsonElement.getString(): String? {
		return try {
			asString
		} catch (th: Throwable) {
			null
		}
	}

	private fun JsonElement.getDouble(): Double? {
		return try {
			asDouble
		} catch (ex: ClassCastException) {
			val stg = getString() ?: return null
			stg.toDoubleOrNull()
		} catch (th: Throwable) {
			null
		}
	}

	private fun JsonElement.getLong(): Long? {
		return try {
			asLong
		} catch (ex: ClassCastException) {
			val stg = getString() ?: return null
			stg.toLongOrNull()
		} catch (th: Throwable) {
			null
		}
	}

}