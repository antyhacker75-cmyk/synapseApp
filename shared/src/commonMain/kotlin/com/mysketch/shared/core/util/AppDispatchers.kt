package com.mysketch.shared.core.util
import com.mysketch.shared.core.util.AppDispatchers

import kotlinx.coroutines.CoroutineDispatcher

expect object AppDispatchers {
    val IO: CoroutineDispatcher
}
