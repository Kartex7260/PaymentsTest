package kanti.paymentstest.ui.fragments.common

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

interface ToolbarOwner {

	val toolbar: Toolbar

}

fun Fragment.setUpToolbar(
	title: String = "",
	@DrawableRes navIcon: Int? = null,
	navClick: (() -> Unit)? = null
) {
	val activity = activity
	if (activity == null || activity !is ToolbarOwner)
		return
	activity.apply {
		ToolbarLifecycleObserver(
			context = requireContext(),
			lifecycleOwner = viewLifecycleOwner,
			toolbar = toolbar,
			title = title,
			navIcon = navIcon,
			navClick = navClick
		)
	}
}

class ToolbarLifecycleObserver(
	private val context: Context,
	lifecycleOwner: LifecycleOwner,
	private val toolbar: Toolbar,
	private val title: String = "",
	@DrawableRes private val navIcon: Int? = null,
	private val navClick: (() -> Unit)? = null
) : DefaultLifecycleObserver {

	init {
		lifecycleOwner.lifecycle.addObserver(this)
	}

	override fun onStart(owner: LifecycleOwner) {
		super.onStart(owner)
		toolbar.title = title
		toolbar.navigationIcon = navIcon?.let { AppCompatResources.getDrawable(context, it) }
		toolbar.setNavigationOnClickListener {
			navClick?.let { it() }
		}
	}

	override fun onStop(owner: LifecycleOwner) {
		super.onStop(owner)
		toolbar.title = ""
		toolbar.navigationIcon = null
		toolbar.setNavigationOnClickListener {}
	}

}
