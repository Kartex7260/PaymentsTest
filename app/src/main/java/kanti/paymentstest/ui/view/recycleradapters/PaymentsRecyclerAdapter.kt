package kanti.paymentstest.ui.view.recycleradapters

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kanti.paymentstest.R
import kanti.paymentstest.data.model.payments.Payment
import java.util.Date

class PaymentsRecyclerAdapter(
	payments: List<Payment> = listOf()
) : RecyclerView.Adapter<PaymentsRecyclerAdapter.PaymentViewHolder>() {

	private val dateFormat = DateFormat.getDateTimeInstance()

	var payments: List<Payment> = payments
		@SuppressLint("NotifyDataSetChanged")
		set(value) {
			field = value

		}

	inner class PaymentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		private val textViewTitle = view.findViewById<MaterialTextView>(R.id.payment_card_text_view_title)
		private val textViewAmount = view.findViewById<MaterialTextView>(R.id.payment_card_text_view_amount)
		private val textViewCreated = view.findViewById<MaterialTextView>(R.id.payment_card_text_view_created)

		fun setPayment(payment: Payment) {
			textViewTitle.text = payment.title

			showAmount(payment.amount)
			showCreated(payment.created)
		}

		private fun showAmount(amount: Double?) {
			if (amount == null) {
				textViewAmount.visibility = View.GONE
				textViewAmount.text = ""
			} else {
				textViewAmount.text = amount.toString()
				textViewAmount.visibility = View.VISIBLE
			}
		}

		private fun showCreated(created: Long?) {
			if (created == null) {
				textViewCreated.visibility = View.GONE
				textViewCreated.text = ""
			} else {
				val date = Date(created)
				textViewCreated.text = dateFormat.format(date)
				textViewCreated.visibility = View.VISIBLE
			}
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.view_card_payment, parent, false)
		return PaymentViewHolder(view)
	}

	override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
		val payment = payments[position]
		holder.setPayment(payment)
	}

	override fun getItemCount(): Int = payments.size
}