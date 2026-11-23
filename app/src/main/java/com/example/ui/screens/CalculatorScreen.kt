package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.DaraRepository
import com.example.model.PaymentPlanMode
import com.example.ui.components.BankPartnerCard
import com.example.ui.components.FinancialMetricTile
import com.example.ui.components.formatEgp
import com.example.ui.components.shareText
import com.example.viewmodel.DaraUiState
import kotlin.math.roundToLong

@Composable
fun CalculatorScreen(
    uiState: DaraUiState,
    onModeSelected: (PaymentPlanMode) -> Unit,
    onSelectUnitPricePreset: (Long) -> Unit,
    onDeveloperDownPaymentChanged: (Long) -> Unit,
    onResetDeveloperPlan: () -> Unit,
    onToggleSchedule: () -> Unit,
    onMortgageDownPaymentChanged: (Long) -> Unit,
    onMortgageYearsChanged: (Int) -> Unit,
    onMortgageRateChanged: (Float) -> Unit,
    onDialNumber: (String) -> Unit,
    onCopyNumber: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val pricePresets = listOf(847_000L, 865_000L, 880_000L)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("calculator_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "طرق الحجز والسداد والتمويل العقاري",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "كمباوند دارة كيان بالصفا • مساحة 90 م² • قيمة الحجز 10,000 جنيه فقط • سعر الوحدة من 847,000 إلى 880,000 ج.م",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item(key = "price_preset_selector") {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "سعر الوحدة (نطاق 847 – 880 ألف)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = formatEgp(uiState.unitPriceEgp),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        pricePresets.forEach { preset ->
                            FilterChip(
                                selected = uiState.unitPriceEgp == preset,
                                onClick = { onSelectUnitPricePreset(preset) },
                                label = { Text("${preset / 1000} ألف") },
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("price_preset_$preset"),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                    Text(
                        text = "جدية الحجز: 10,000 جنيه فقط • المقدم المطلوب: 15% من سعر الوحدة",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        item(key = "mode_selector") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
            ) {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PaymentPlanMode.entries.forEachIndexed { index, mode ->
                        SegmentedButton(
                            selected = uiState.paymentPlanMode == mode,
                            onClick = { onModeSelected(mode) },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = PaymentPlanMode.entries.size
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("calc_mode_${mode.name.lowercase()}")
                        ) {
                            Text(
                                text = mode.labelAr,
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        if (uiState.paymentPlanMode == PaymentPlanMode.DEVELOPER_QUARTERLY) {
            item(key = "developer_plan_summary") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 840.dp)
                ) {
                    DeveloperPlanCard(
                        uiState = uiState,
                        onDownPaymentChanged = onDeveloperDownPaymentChanged,
                        onResetPlan = onResetDeveloperPlan,
                        onToggleSchedule = onToggleSchedule
                    )
                }
            }
        } else {
            item(key = "mortgage_calculator") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 840.dp)
                ) {
                    MortgageCalculatorCard(
                        uiState = uiState,
                        onDownPaymentChanged = onMortgageDownPaymentChanged,
                        onYearsChanged = onMortgageYearsChanged,
                        onRateChanged = onMortgageRateChanged
                    )
                }
            }

            item(key = "banks_header") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 840.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "البنوك المتعاونة وأرقام موظفي التمويل العقاري",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "تواصل مباشرة مع مسؤول التمويل العقاري بالبنك المناسب لك:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            items(uiState.bankPartners, key = { "calc_bank_${it.id}" }) { partner ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 840.dp)
                ) {
                    BankPartnerCard(
                        partner = partner,
                        onDialNumber = onDialNumber,
                        onCopyNumber = onCopyNumber
                    )
                }
            }
        }

        item(key = "calc_actions") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { onDialNumber(DaraRepository.HOTLINE_NUMBER) },
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("calc_call_hotline")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Call,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("تأكيد الحجز عبر 15444")
                }

                OutlinedButton(
                    onClick = {
                        val summary = if (uiState.paymentPlanMode == PaymentPlanMode.DEVELOPER_QUARTERLY) {
                            "حساب أقساط وحدة دارة كيان - دارة السويس (90 م²):\n" +
                                "سعر الوحدة: ${formatEgp(uiState.unitPriceEgp)}\n" +
                                "قيمة الحجز: 10,000 ج.م فقط\n" +
                                "المقدم (15%): ${formatEgp(uiState.developerDownPaymentEgp)}\n" +
                                "القسط الربع سنوي: ${formatEgp(uiState.developerQuarterlyInstallmentEgp)} (20 قسط / 5 سنوات)\n" +
                                "للتواصل: ${DaraRepository.HOTLINE_NUMBER} - ${DaraRepository.SALES_MOBILE_NUMBER}"
                        } else {
                            "محاكاة التمويل العقاري لوحدة دارة كيان - دارة السويس (90 م²):\n" +
                                "سعر الوحدة: ${formatEgp(uiState.unitPriceEgp)}\n" +
                                "المقدم: ${formatEgp(uiState.mortgageDownPaymentEgp)}\n" +
                                "مبلغ التمويل: ${formatEgp(uiState.mortgagePrincipalEgp)}\n" +
                                "المدة: ${uiState.mortgageYears} سنوات | الفائدة: ${"%.1f".format(uiState.mortgageAnnualRatePercent)}%\n" +
                                "القسط الشهري التقديري: ${formatEgp(uiState.mortgageMonthlyPaymentEgp)}\n" +
                                "للتواصل: ${DaraRepository.HOTLINE_NUMBER} - ${DaraRepository.SALES_MOBILE_NUMBER}"
                        }
                        shareText(context, "خطة سداد دارة كيان - دارة السويس", summary)
                    },
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("calc_share_button")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = "مشاركة الحساب",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("مشاركة")
                }
            }
        }
    }
}

@Composable
private fun DeveloperPlanCard(
    uiState: DaraUiState,
    onDownPaymentChanged: (Long) -> Unit,
    onResetPlan: () -> Unit,
    onToggleSchedule: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (uiState.isOfficialDeveloperPlan) {
                            "الخطة الرسمية للتقسيط المباشر"
                        } else {
                            "خطة تقسيط مباشر (${formatEgp(uiState.unitPriceEgp)})"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "مقدم 15% وتقسيط حتى 5 سنوات (20 قسط ربع سنوي)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (!uiState.isOfficialDeveloperPlan) {
                    TextButton(
                        onClick = onResetPlan,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("calc_reset_official")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("عرض 847 ألف")
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FinancialMetricTile(
                    label = "القسط الربع سنوي",
                    value = formatEgp(uiState.developerQuarterlyInstallmentEgp),
                    subLabel = "كل 3 أشهر",
                    icon = Icons.Rounded.CalendarMonth,
                    highlighted = true,
                    modifier = Modifier.weight(1f)
                )
                FinancialMetricTile(
                    label = "المقدم (15%+)",
                    value = formatEgp(uiState.developerDownPaymentEgp),
                    subLabel = "المتبقي ${formatEgp(uiState.developerRemainingEgp)}",
                    icon = Icons.Rounded.Payments,
                    modifier = Modifier.weight(1f)
                )
            }

            BreakdownBar(
                firstFraction = uiState.developerDownPaymentEgp.toFloat() / uiState.unitPriceEgp.toFloat(),
                firstLabel = "المقدم (${formatEgp(uiState.developerDownPaymentEgp)})",
                secondLabel = "الأقساط (${formatEgp(uiState.developerRemainingEgp)})"
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "تعديل قيمة المقدم للمحاكاة",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = formatEgp(uiState.developerDownPaymentEgp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Slider(
                    value = uiState.developerDownPaymentEgp.toFloat(),
                    onValueChange = { raw ->
                        val stepped = ((raw / 5000f).roundToLong() * 5000L).coerceIn(127_000L, 500_000L)
                        onDownPaymentChanged(if (raw <= 129_500f) 127_000L else stepped)
                    },
                    valueRange = 127_000f..500_000f,
                    modifier = Modifier.testTag("dev_down_payment_slider")
                )
            }

            HorizontalDivider()

            OutlinedButton(
                onClick = onToggleSchedule,
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("toggle_schedule_button")
            ) {
                Icon(
                    imageVector = if (uiState.showFullSchedule) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    if (uiState.showFullSchedule) "إخفاء جدول الأقساط (20 قسط)"
                    else "عرض جدول الأقساط الربع سنوية (20 قسط)"
                )
            }

            AnimatedVisibility(visible = uiState.showFullSchedule) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    uiState.developerSchedule.forEach { row ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "قسط #${row.quarterNumber} • ${row.labelAr}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "المتبقي بعد القسط: ${formatEgp(row.remainingAfterEgp)}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = formatEgp(row.amountEgp),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MortgageCalculatorCard(
    uiState: DaraUiState,
    onDownPaymentChanged: (Long) -> Unit,
    onYearsChanged: (Int) -> Unit,
    onRateChanged: (Float) -> Unit
) {
    val yearOptions = listOf(5, 7, 10, 15, 20)
    val ratePresets = listOf(3.0f, 8.0f, 12.0f, 15.0f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column {
                Text(
                    text = "محاكاة التمويل العقاري (مقدم 15%)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "مدة السداد حسب موافقة البنك الممول — اختر المدة والعائد التقديري",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FinancialMetricTile(
                    label = "القسط الشهري التقديري",
                    value = formatEgp(uiState.mortgageMonthlyPaymentEgp),
                    subLabel = "لمدة ${uiState.mortgageYears} سنوات",
                    icon = Icons.Rounded.AccountBalance,
                    highlighted = true,
                    modifier = Modifier.weight(1f)
                )
                FinancialMetricTile(
                    label = "مبلغ التمويل",
                    value = formatEgp(uiState.mortgagePrincipalEgp),
                    subLabel = "المقدم ${formatEgp(uiState.mortgageDownPaymentEgp)}",
                    icon = Icons.Rounded.Payments,
                    modifier = Modifier.weight(1f)
                )
            }

            BreakdownBar(
                firstFraction = uiState.mortgageDownPaymentEgp.toFloat() / uiState.mortgageTotalPayableEgp.coerceAtLeast(1L).toFloat(),
                firstLabel = "المقدم (${formatEgp(uiState.mortgageDownPaymentEgp)})",
                secondLabel = "إجمالي التمويل والعائد (${formatEgp(uiState.mortgageTotalPayableEgp - uiState.mortgageDownPaymentEgp)})"
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("المقدم", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = formatEgp(uiState.mortgageDownPaymentEgp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Slider(
                    value = uiState.mortgageDownPaymentEgp.toFloat(),
                    onValueChange = { raw ->
                        val stepped = ((raw / 5000f).roundToLong() * 5000L).coerceIn(127_000L, 600_000L)
                        onDownPaymentChanged(if (raw <= 129_500f) 127_000L else stepped)
                    },
                    valueRange = 127_000f..600_000f,
                    modifier = Modifier.testTag("mortgage_down_payment_slider")
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("مدة التمويل (حسب موافقة البنك)", style = MaterialTheme.typography.labelLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    yearOptions.forEach { yr ->
                        FilterChip(
                            selected = uiState.mortgageYears == yr,
                            onClick = { onYearsChanged(yr) },
                            label = { Text("$yr س") },
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("mortgage_year_$yr"),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("نسبة العائد السنوي (متناقص)", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = "${"%.1f".format(uiState.mortgageAnnualRatePercent)}%",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ratePresets.forEach { preset ->
                        FilterChip(
                            selected = uiState.mortgageAnnualRatePercent == preset,
                            onClick = { onRateChanged(preset) },
                            label = { Text("${preset.toInt()}%") },
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("mortgage_rate_${preset.toInt()}"),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondary,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "ملاحظة: مدة السداد ونسبة الفائدة النهائية تحدد حسب موافقة البنك الممول. يمكنك التواصل مباشرة مع مسؤولي البنوك بالأسفل.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
private fun BreakdownBar(
    firstFraction: Float,
    firstLabel: String,
    secondLabel: String
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val clampedFirst = firstFraction.coerceIn(0.05f, 0.95f)

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        ) {
            val radius = CornerRadius(size.height / 2f, size.height / 2f)
            drawRoundRect(
                color = secondaryColor.copy(alpha = 0.28f),
                topLeft = Offset.Zero,
                size = size,
                cornerRadius = radius
            )
            drawRoundRect(
                color = primaryColor,
                topLeft = Offset.Zero,
                size = Size(width = size.width * clampedFirst, height = size.height),
                cornerRadius = radius
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = firstLabel,
                style = MaterialTheme.typography.bodySmall,
                color = primaryColor,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = secondLabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
