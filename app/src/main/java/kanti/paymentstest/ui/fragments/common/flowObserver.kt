package kanti.paymentstest.ui.fragments.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.observe(
	flow: Flow<T>,
	lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
	block: (T) -> Unit) {
	viewLifecycleOwner.lifecycleScope.launch {
		repeatOnLifecycle(lifecycleState) {
			flow.collectLatest {
				block(it)
			}
		}
	}
}