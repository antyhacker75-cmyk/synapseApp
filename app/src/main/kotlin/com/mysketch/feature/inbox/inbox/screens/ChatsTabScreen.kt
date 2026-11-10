package com.mysketch.feature.inbox.inbox.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Lock

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete

import androidx.compose.ui.platform.LocalLayoutDirection
import com.mysketch.R
import com.mysketch.shared.domain.model.settings.ChatFolder
import com.mysketch.feature.inbox.inbox.components.InboxEmptyState
import com.mysketch.feature.inbox.inbox.components.InboxShimmer
import com.mysketch.feature.inbox.inbox.components.isUndecryptableMessageContent
import com.mysketch.shared.domain.model.chat.Conversation
import com.mysketch.feature.shared.theme.Sizes
import com.mysketch.feature.shared.theme.Spacing
import com.mysketch.feature.shared.theme.StatusOnline
import com.mysketch.feature.inbox.inbox.theme.InboxTheme
import com.mysketch.feature.inbox.inbox.models.EmptyStateType
import com.mysketch.shared.domain.model.settings.ChatListLayout
import com.mysketch.shared.domain.model.settings.ChatSwipeGesture
import com.mysketch.ui.animations.PeekingBox
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Formats an ISO 8601 timestamp into a human-friendly relative string.
 */
 private fun formatTimestamp(isoTimestamp: String?): String {
    if (isoTimestamp == null) return ""
    return try {
        val instant = Instant.parse(isoTimestamp)
        val now = Instant.now()
        val minutesAgo = ChronoUnit.MINUTES.between(instant, now)
        val hoursAgo = ChronoUnit.HOURS.between(instant, now)
        val daysAgo = ChronoUnit.DAYS.between(instant, now)

        when {
            minutesAgo < 1 -> "Just now"
            minutesAgo < 60 -> "${minutesAgo}m"
            hoursAgo < 24 -> "${hoursAgo}h"
            daysAgo < 7 -> {
                val formatter = DateTimeFormatter.ofPattern("EEE").withZone(ZoneId.systemDefault())
                formatter.format(instant)
            }
            else -> {
                val formatter = DateTimeFormatter.ofPattern("MMM d").withZone(ZoneId.systemDefault())
                formatter.format(instant)
            }
        }
    } catch (e: Exception) {
        ""
    }
}
