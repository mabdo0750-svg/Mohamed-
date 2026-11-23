package com.example.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Apartment
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Construction
import androidx.compose.material.icons.rounded.Directions
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.SquareFoot
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.Compound
import com.example.model.CompoundStatus
import com.example.model.DaraRepository
import com.example.ui.components.CompoundStatusBadge
import com.example.ui.components.FacilityItemRow
import com.example.ui.components.FinancialMetricTile
import com.example.ui.components.MortgageAvailableBanner
import com.example.ui.components.formatEgp
import com.example.viewmodel.DaraUiState

@Composable
fun CompoundsSectionScreen(
    uiState: DaraUiState,
    onSelectCompoundTab: (String) -> Unit,
    onOpenCompoundDetail: (String) -> Unit,
    onOpenCalculator: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCompound = uiState.selectedComparisonCompound

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("compounds_section_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "section_header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "دليل كمباوندات دارة السويس",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "استعرض تفاصيل ومرافق وموقف التنفيذ لكل كمباوند: دارة حياة، دارة بستان، ودارة كيان.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item(key = "compound_switcher") {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.allCompounds, key = { it.id }) { compound ->
                    val isSelected = compound.id == selectedCompound.id
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCompoundTab(compound.id) },
                        label = {
                            Text(
                                text = compound.nameAr.removePrefix("كمباوند "),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Apartment,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("compound_tab_${compound.id}"),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        item(key = "selected_compound_card") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )
            ) {
                DetailedCompoundShowcaseCard(
                    compound = selectedCompound,
                    onOpenDetail = { onOpenCompoundDetail(selectedCompound.id) },
                    onOpenCalculator = onOpenCalculator
                )
            }
        }

        item(key = "all_compounds_summary_title") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "مقارنة سريعة للكمباوندات الثلاثة",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "اضغط على أي كمباوند للعرض الكامل أو التبديل أعلاه",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(uiState.allCompounds, key = { "summary_${it.id}" }) { compound ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
            ) {
                CompactCompoundSummaryCard(
                    compound = compound,
                    isSelected = compound.id == selectedCompound.id,
                    onSelect = { onSelectCompoundTab(compound.id) },
                    onOpenDetail = { onOpenCompoundDetail(compound.id) }
                )
            }
        }
    }
}

@Composable
private fun DetailedCompoundShowcaseCard(
    compound: Compound,
    onOpenDetail: () -> Unit,
    onOpenCalculator: () -> Unit
) {
    val isAvailable = compound.status == CompoundStatus.AVAILABLE_LAST_FLOOR

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("detailed_compound_card_${compound.id}"),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(
            width = if (isAvailable) 1.5.dp else 1.dp,
            color = if (isAvailable) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
            } else {
                MaterialTheme.colorScheme.outlineVariant
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Image(
                    painter = painterResource(id = compound.imageRes),
                    contentDescription = compound.nameAr,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.12f),
                                    Color.Black.copy(alpha = 0.68f)
                                )
                            )
                        )
                )

                CompoundStatusBadge(
                    status = compound.status,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(14.dp)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = compound.nameAr,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = compound.locationAr,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = compound.descriptionAr,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FinancialMetricTile(
                        label = "مساحة الوحدات",
                        value = "90 م²",
                        subLabel = "مساحة موحدة متشطبة",
                        icon = Icons.Rounded.SquareFoot,
                        modifier = Modifier.weight(1f)
                    )
                    FinancialMetricTile(
                        label = "موقف التنفيذ",
                        value = "مباني منتهية",
                        subLabel = "وحدات متشطبة وجاهزة",
                        icon = Icons.Rounded.Construction,
                        highlighted = isAvailable,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Gates & Location access
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Directions,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "البوابات والمداخل الرئيسية",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        compound.gatesAr.forEach { gate ->
                            Text(
                                text = "• $gate",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Unit Offer for Kayan
                val offer = compound.unitOffer
                if (offer != null) {
                    HorizontalDivider()
                    Text(
                        text = "الأسعار وطرق السداد (${compound.nameAr})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FinancialMetricTile(
                            label = "نطاق سعر الوحدة",
                            value = "${formatEgp(offer.priceMinEgp)} - ${formatEgp(offer.priceMaxEgp)}",
                            subLabel = "المتاح دور أخير بـ 847 ألف",
                            icon = Icons.Rounded.Payments,
                            highlighted = true,
                            modifier = Modifier.weight(1f)
                        )
                        FinancialMetricTile(
                            label = "قيمة الحجز",
                            value = formatEgp(offer.bookingDepositEgp),
                            subLabel = "مقدم 15% (127 ألف)",
                            icon = Icons.Rounded.Payments,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    MortgageAvailableBanner(summary = offer.mortgageSummaryAr)
                }

                HorizontalDivider()

                Text(
                    text = "المرافق المميزة والخدمات",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    compound.facilities.forEach { facility ->
                        FacilityItemRow(facility = facility)
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "حالة المشروع: ${compound.constructionStatusAr}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onOpenDetail,
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("section_open_detail_${compound.id}")
                    ) {
                        Text("صفحة الكمباوند")
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    if (isAvailable) {
                        OutlinedButton(
                            onClick = onOpenCalculator,
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("section_open_calc_${compound.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Calculate,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("احسب القسط")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CompactCompoundSummaryCard(
    compound: Compound,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onOpenDetail: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = compound.nameAr,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${compound.locationAr} • ${compound.unitAreaAr}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                CompoundStatusBadge(status = compound.status)
            }

            Text(
                text = "المرافق الرئيسية: ${compound.facilities.joinToString(" • ") { it.titleAr }}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (compound.id == "hayat") {
                Text(
                    text = DaraRepository.SALES_OFFICE_ADDRESS,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onSelect,
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("select_comparison_${compound.id}")
                ) {
                    Text(if (isSelected) "معروض أعلاه" else "عرض المرافق")
                }
                Button(
                    onClick = onOpenDetail,
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("goto_detail_${compound.id}")
                ) {
                    Text("التفاصيل الكاملة")
                }
            }
        }
    }
}
