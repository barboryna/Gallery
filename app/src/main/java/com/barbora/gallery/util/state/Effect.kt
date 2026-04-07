package com.barbora.gallery.util.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.structuralEqualityPolicy
import kotlinx.coroutines.CoroutineScope


@Composable
@NonRestartableComposable
fun <T> StateTriggeredEffect(state: MutableState<T?>, block: suspend CoroutineScope.(T) -> Unit) {
    LaunchedEffect(state.value) {
        val newestValue = state.value
        if (newestValue != null) {
            try {
                block(newestValue)
                state.value = null
            } finally {
                state.value = null
            }
        }
    }
}

/**
 * Helper function to create a mutable state that has a nullable generic by default.
 */
fun <T> mutableEffectOf(
    value: T? = null,
    policy: SnapshotMutationPolicy<T?> = structuralEqualityPolicy()
): MutableState<T?> = mutableStateOf(value, policy)
