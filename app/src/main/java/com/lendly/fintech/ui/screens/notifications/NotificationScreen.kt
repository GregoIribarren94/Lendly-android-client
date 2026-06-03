package com.lendly.fintech.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.navigation.LendlyTopBar
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.DividerNeutral
import com.lendly.fintech.ui.theme.IconTintDark
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

private val DotRead = Color(0xFFD9D9D9)
private val OkButtonBg = Color(0xFFF4F0EF)

private data class NotificationItem(
    val title: String,
    val date: String,
    val body: String,
    val unread: Boolean,
)

private const val SampleBody =
    "We'd like to remind you about your due date this month. Please pay this balance " +
        "within the date to keep your credit score. Tap to pay."

private val TodayItems = listOf(
    NotificationItem("Your due date is almost here!", "Mar 8", SampleBody, unread = true),
    NotificationItem("Your due date is almost here!", "Mar 8", SampleBody, unread = true),
    NotificationItem("Got  a minute to help us out?", "Mar 8", SampleBody, unread = false),
    NotificationItem("Got  a minute to help us out?", "Mar 8", SampleBody, unread = false),
)

private val AnnouncementItems = listOf(
    NotificationItem("Your due date is almost here!", "Mar 8", SampleBody, unread = true),
    NotificationItem("Got  a minute to help us out?", "Mar 8", SampleBody, unread = false),
)

@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    initiallyShowCalendar: Boolean = false,
) {
    var showCalendar by remember { mutableStateOf(initiallyShowCalendar) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen),
    ) {
        LendlyTopBar(
            leading = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = IconTintDark,
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
            actions = {
                IconButton(onClick = { showCalendar = !showCalendar }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_event),
                        contentDescription = "Due dates",
                        tint = IconTintDark,
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
        )

        Box(modifier = Modifier.fillMaxSize()) {
            // ── Lista (base) ──────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.md),
            ) {
                Text(
                    text = "Notification",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ContentOnSurface,
                    modifier = Modifier.padding(top = Spacing.xs, bottom = Spacing.md),
                )

                NotificationSection(title = "Today", items = TodayItems)
                Spacer(Modifier.size(Spacing.md))
                NotificationSection(title = "Announcement", items = AnnouncementItems)
                Spacer(Modifier.size(Spacing.lg))
            }

            // ── Modal calendario (Notification Page (1)) ──────────────────
            if (showCalendar) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.15f))
                        .clickable { showCalendar = false },
                )
                DueDatesCalendarCard(
                    modifier = Modifier.align(Alignment.TopCenter),
                    onOk = { showCalendar = false },
                )
            }
        }
    }
}

@Composable
private fun NotificationSection(title: String, items: List<NotificationItem>) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall,
        color = ContentTertiary,
        modifier = Modifier.padding(bottom = Spacing.sm),
    )
    HorizontalDivider(color = DividerNeutral)
    items.forEach { NotificationRow(it) }
}

@Composable
private fun NotificationRow(item: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.md),
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(10.dp)
                .clip(CircleShape)
                .background(if (item.unread) InteractiveAccent else DotRead),
        )
        Spacer(Modifier.width(Spacing.sm))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = ContentSecondary,
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(Spacing.sm))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = ContentTertiary,
                )
            }
            Spacer(Modifier.size(Spacing.xs))
            Text(
                text = item.body,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, lineHeight = 20.sp),
                color = ContentTertiary,
            )
        }
    }
}

// ── Calendario "Due dates" ────────────────────────────────────────────────

private val AugustWeeks: List<List<Int?>> = listOf(
    listOf(null, null, 1, 2, 3, 4, 5),
    listOf(6, 7, 8, 9, 10, 11, 12),
    listOf(13, 14, 15, 16, 17, 18, 19),
    listOf(20, 21, 22, 23, 24, 25, 26),
    listOf(27, 28, 29, 30, 31, null, null),
)

@Composable
private fun DueDatesCalendarCard(
    onOk: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(BackgroundScreen)
            .padding(start = Spacing.lg, end = Spacing.lg, top = Spacing.md, bottom = Spacing.lg),
    ) {
        Text(text = "Due dates", style = MaterialTheme.typography.bodySmall, color = ContentTertiary)
        Spacer(Modifier.size(4.dp))
        Text(
            text = "Mon, Aug 17",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = ContentPrimary,
        )

        Spacer(Modifier.size(Spacing.md))
        HorizontalDivider(color = DividerNeutral)
        Spacer(Modifier.size(Spacing.md))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "August 2023",
                style = MaterialTheme.typography.titleMedium,
                color = ContentSecondary,
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = ContentSecondary,
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Previous month",
                tint = ContentSecondary,
            )
            Spacer(Modifier.width(Spacing.lg))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Next month",
                tint = ContentSecondary,
            )
        }

        Spacer(Modifier.size(Spacing.md))

        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { d ->
                Text(
                    text = d,
                    style = MaterialTheme.typography.titleMedium,
                    color = ContentSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        AugustWeeks.forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { day ->
                    CalendarDay(day = day, modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(Modifier.size(Spacing.md))

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clip(RoundedCornerShape(8.dp))
                .background(OkButtonBg)
                .clickable(onClick = onOk)
                .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
        ) {
            Text(
                text = "OK",
                style = MaterialTheme.typography.titleMedium,
                color = ContentSecondary,
            )
        }
    }
}

@Composable
private fun CalendarDay(day: Int?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = 6.dp)
            .size(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (day == null) return@Box
        val selected = day == 17
        val highlighted = day == 5
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .then(
                    when {
                        selected -> Modifier.background(InteractiveAccent)
                        highlighted -> Modifier.border(1.dp, InteractiveAccent, CircleShape)
                        else -> Modifier
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = if (selected) ContentPrimary else ContentSecondary,
            )
        }
    }
}

@Preview(name = "Notification list", showBackground = true, showSystemUi = true)
@Composable
private fun NotificationScreenPreview() {
    LendlyTheme { NotificationScreen(onBack = {}) }
}

@Preview(name = "Notification calendar", showBackground = true, showSystemUi = true)
@Composable
private fun NotificationCalendarPreview() {
    LendlyTheme { NotificationScreen(onBack = {}, initiallyShowCalendar = true) }
}
