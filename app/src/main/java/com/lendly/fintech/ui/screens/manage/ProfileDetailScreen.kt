package com.lendly.fintech.ui.screens.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.*

@Composable
fun ProfileDetailScreen(
    detailId: String,
    onBack: () -> Unit,
) {
    val config = profileDetailConfig(detailId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen),
    ) {
        // Top Bar
        ProfileDetailTopBar(title = config.title, onBack = onBack)

        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg),
        ) {
            Spacer(modifier = Modifier.height(Spacing.sm))

            config.fields.forEach { field ->
                ProfileDetailField(field = field)
            }

            Spacer(modifier = Modifier.height(Spacing.sm))
        }

        // Bottom Save button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundScreen)
                .padding(horizontal = Spacing.md, vertical = Spacing.md),
        ) {
            Button(
                onClick = { onBack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = InteractiveAccent,
                    contentColor = Color(0xFF102000),
                ),
            ) {
                Text(
                    text = "Save",
                    style = ButtonLabel,
                )
            }
        }
    }
}

@Composable
private fun ProfileDetailTopBar(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = ContentPrimary,
            )
        }
        Text(
            text = title,
            style = TitleMedium,
            color = ContentPrimary,
            modifier = Modifier.padding(start = Spacing.xs),
        )
    }
}

@Composable
private fun ProfileDetailField(field: DetailField) {
    var value by remember { mutableStateOf(field.value) }

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        Text(
            text = field.label,
            style = CaptionMedium,
            color = ContentTertiary,
        )
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = field.placeholder,
                    style = Body,
                    color = ContentTertiary,
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = InteractiveAccent,
                unfocusedBorderColor = BorderNeutral,
                focusedTextColor = ContentPrimary,
                unfocusedTextColor = ContentPrimary,
                cursorColor = InteractiveAccent,
            ),
            textStyle = Body,
            singleLine = true,
        )
    }
}

// ── Data models ──────────────────────────────────────────────────────────────

data class DetailField(
    val label: String,
    val value: String = "",
    val placeholder: String = "",
)

data class ProfileDetailConfig(
    val title: String,
    val fields: List<DetailField>,
)

fun profileDetailConfig(detailId: String): ProfileDetailConfig = when (detailId) {
    "personal" -> ProfileDetailConfig(
        title = "Personal information",
        fields = listOf(
            DetailField("Full name", "Kathryn Murphy", "Enter your name"),
            DetailField("Username", "@kathrynmurphy", "Enter your username"),
            DetailField("Date of birth", "", "DD / MM / YYYY"),
            DetailField("Gender", "", "Select gender"),
            DetailField("Nationality", "", "Select nationality"),
        ),
    )
    "address" -> ProfileDetailConfig(
        title = "Address",
        fields = listOf(
            DetailField("Street address", "", "Enter your street"),
            DetailField("City", "", "Enter your city"),
            DetailField("State / Province", "", "Enter your state"),
            DetailField("Postal code", "", "Enter postal code"),
            DetailField("Country", "", "Select country"),
        ),
    )
    "payment" -> ProfileDetailConfig(
        title = "Payment methods",
        fields = listOf(
            DetailField("Card number", "", "XXXX XXXX XXXX XXXX"),
            DetailField("Cardholder name", "", "Name on card"),
            DetailField("Expiry date", "", "MM / YY"),
            DetailField("CVV", "", "XXX"),
        ),
    )
    "settings" -> ProfileDetailConfig(
        title = "Account settings",
        fields = listOf(
            DetailField("Email", "", "Enter your email"),
            DetailField("Phone number", "", "+1 (000) 000-0000"),
            DetailField("Language", "", "Select language"),
        ),
    )
    "security" -> ProfileDetailConfig(
        title = "Privacy & security",
        fields = listOf(
            DetailField("Current password", "", "Enter current password"),
            DetailField("New password", "", "Enter new password"),
            DetailField("Confirm password", "", "Confirm new password"),
        ),
    )
    "notifications" -> ProfileDetailConfig(
        title = "Notifications",
        fields = listOf(
            DetailField("Email notifications", "", "Enable / Disable"),
            DetailField("Push notifications", "", "Enable / Disable"),
            DetailField("SMS notifications", "", "Enable / Disable"),
        ),
    )
    "about" -> ProfileDetailConfig(
        title = "About Lendly",
        fields = listOf(
            DetailField("App version", "1.0.0", ""),
            DetailField("Terms of service", "", "View terms"),
            DetailField("Privacy policy", "", "View policy"),
        ),
    )
    "help" -> ProfileDetailConfig(
        title = "Help center",
        fields = listOf(
            DetailField("Contact support", "", "Email / Chat"),
            DetailField("FAQs", "", "View FAQs"),
            DetailField("Report a problem", "", "Describe your issue"),
        ),
    )
    else -> ProfileDetailConfig(
        title = "Details",
        fields = emptyList(),
    )
}