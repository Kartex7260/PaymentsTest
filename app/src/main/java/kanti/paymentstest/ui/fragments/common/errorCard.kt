package kanti.paymentstest.ui.fragments.common

import android.opengl.Visibility
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

interface ErrorCardOwner {

	val root: View
	val title: MaterialTextView
	val button: MaterialButton

}

fun Fragment.showErrorCard(
	title: String,
	buttonText: String,
	onClick: View.OnClickListener
) {
	val activity = activity
	if (activity == null || activity !is ErrorCardOwner)
		return
	activity.button.apply {
		setOnClickListener(onClick)
		text = buttonText
	}
	activity.title.text = title
	activity.root.visibility = View.VISIBLE
	ErrorCardLifecycleObserver(this)
}

fun Fragment.hideErrorCard() {
	val activity = activity
	if (activity == null || activity !is ErrorCardOwner)
		return
	activity.button.apply {
		setOnClickListener {}
		text = null
	}
	activity.title.text = null
	activity.root.visibility = View.GONE
}

class ErrorCardLifecycleObserver(
	private val fragment: Fragment
) : DefaultLifecycleObserver {

	init {
		fragment.viewLifecycleOwner.lifecycle.addObserver(this)
	}

	override fun onDestroy(owner: LifecycleOwner) {
		super.onDestroy(owner)
		fragment.hideErrorCard()
	}

}
