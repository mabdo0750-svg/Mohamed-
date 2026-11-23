package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Apartment
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Construction
import androidx.compose.material.icons.rounded.Directions
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.SquareFoot
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.Compound
import com.example.model.CompoundStatus
import com.example.model.DaraRepository
import com.example.ui.components.CompoundStatusBadge
import com.example.ui.components.FacilityItemRow
import com.example.ui.components.FinancialMetricTile
import com.example.ui.components.MortgageAvailableBanner
import com.example.ui.components.formatEgp
import com.example.ui.components.shareText

@Composable
fun CompoundDetailScreen(
    compound: Compound,
    onBack: () -> Unit,
    onNavigateToKayan: () -> Unit,
    onOpenCalculator: () -> Unit,
    onDialHotline: () -> Unit,
    onDialSalesMobile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isAvailable = compound.status == CompoundStatus.AVAILABLE_LAST_FLOOR

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (isAvailable) {
                        Button(
                            onClick = onDialHotline,
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("detail_call_hotline")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Call,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("حجز عبر 15444")
                        }
                        OutlinedButton(
                            onClick = onOpenCalculator,
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("detail_open_calculator")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Calculate,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("حاسبة التمويل")
                        }
                    } else {
                        Button(
                            onClick = onNavigateToKayan,
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("detail_switch_to_kayan")
                        ) {
                            Text("عرض الوحدة المتاحة بدارة كيان")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item(key = "header") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp)
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
                                        Color.Black.copy(alpha = 0.45f),
                                        Color.Black.copy(alpha = 0.15f),
                                        Color.Black.copy(alpha = 0.72f)
                                    )
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledTonalIconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("detail_back_button"),
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = Color.Black.copy(alpha = 0.45f),
                                contentColor = Color.White
                            ),
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = stringResource(id = R.string.cd_back)
                            )
                        }

                        FilledTonalIconButton(
                            onClick = {
                                val summary = if (compound.unitOffer != null) {
                                    "مشروع دارة السويس - ${compound.nameAr} (${compound.locationAr})\n" +
                                        "الحالة: متاح دور أخير فقط • المساحة: 90 م²\n" +
                                        "سعر الوحدة: من 847,000 إلى 880,000 ج.م\n" +
                                        "قيمة الحجز: 10,000 ج.م | مقدم 15% (127,000 ج.م) | القسط الربع سنوي: 36,000 ج.م\n" +
                                        "متاح تمويل عقاري مع 8 بنوك\n" +
                                        "للتواصل: ${DaraRepository.HOTLINE_NUMBER} أو ${DaraRepository.SALES_MOBILE_NUMBER}"
                                } else {
                                    "مشروع دارة السويس - ${compound.nameAr} (غير متاح حالياً - المرحلة الأولى Sold Out).\n" +
                                        "الموقع: ${compound.locationAr} | ${compound.unitAreaAr}\n" +
                                        "للاستعلام: ${DaraRepository.HOTLINE_NUMBER} أو ${DaraRepository.SALES_MOBILE_NUMBER}"
                                }
                                shareText(context, compound.nameAr, summary)
                            },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("detail_share_button"),
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = Color.Black.copy(alpha = 0.45f),
                                contentColor = Color.White
                            ),
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Share,
                                contentDescription = "مشاركة تفاصيل ${compound.nameAr}"
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CompoundStatusBadge(status = compound.status)
                        Text(
                            text = compound.nameAr,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.LocationOn,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = compound.locationAr,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.92f)
                            )
                        }
                    }
                }
            }

            item(key = "body") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 840.dp)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = compound.descriptionAr,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FinancialMetricTile(
                            label = "مساحة الوحدات",
                            value = "90 م²",
                            subLabel = "مساحة موحدة",
                            icon = Icons.Rounded.SquareFoot,
                            modifier = Modifier.weight(1f)
                        )
                        FinancialMetricTile(
                            label = "موقف التنفيذ",
                            value = "متشطبة وجاهزة",
                            subLabel = "المباني منتهية بالكامل",
                            icon = Icons.Rounded.Construction,
                            highlighted = isAvailable,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Gates & Location
                    Card(
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Directions,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "الموقع والبوابات الرئيسية",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "الموقع: ${compound.locationAr}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            compound.gatesAr.forEach { gate ->
                                Text(
                                    text = "• $gate",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    val offer = compound.unitOffer
                    if (offer != null) {
                        Card(
                            shape = MaterialTheme.shapes.large,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Text(
                                    text = "تفاصيل الوحدة والأسعار المتاحة",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    FinancialMetricTile(
                                        label = "سعر الوحدة",
                                        value = formatEgp(offer.priceMinEgp),
                                        subLabel = "نطاق 847 إلى 880 ألف",
                                        icon = Icons.Rounded.Sell,
                                        highlighted = true,
                                        modifier = Modifier.weight(1f)
                                    )
                                    FinancialMetricTile(
                                        label = "قيمة حجز الوحدة",
                                        value = formatEgp(offer.bookingDepositEgp),
                                        subLabel = "جدية الحجز فقط",
                                        icon = Icons.Rounded.Payments,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    FinancialMetricTile(
                                        label = "المقدم (15%)",
                                        value = formatEgp(offer.downPaymentEgp),
                                        subLabel = "١٢٧ ألف جنيه",
                                        icon = Icons.Rounded.Payments,
                                        modifier = Modifier.weight(1f)
                                    )
                                    FinancialMetricTile(
                                        label = "القسط الربع سنوي",
                                        value = formatEgp(offer.quarterlyInstallmentEgp),
                                        subLabel = "تقسيط حتى 5 سنوات",
                                        icon = Icons.Rounded.CalendarMonth,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    FinancialMetricTile(
                                        label = "الطابق المتاح",
                                        value = "دور أخير فقط",
                                        subLabel = "وحدة ${offer.unitAreaSqm} م²",
                                        icon = Icons.Rounded.Apartment,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                MortgageAvailableBanner(summary = offer.mortgageSummaryAr)
                            }
                        }
                    } else {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.large,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "غير متاح حالياً (المرحلة الأولى Sold Out)",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "تم بيع وحدات المرحلة الأولى من ${compound.nameAr} بالكامل، والكمباوند غير متاح للحجز حالياً. يمكنك الاطلاع على الوحدة المتاحة حالياً في كمباوند دارة كيان بالصفا (دور أخير فقط بمساحة 90 م² وسعر يبدأ من 847 ألف جنيه).",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    // Facilities Section
                    Card(
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "المرافق المميزة",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            compound.facilities.forEach { facility ->
                                FacilityItemRow(facility = facility)
                            }
                        }
                    }

                    // Construction & Delivery highlights
                    Card(
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "موقف التنفيذ وأبرز المزايا",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = compound.constructionStatusAr,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            compound.highlightsAr.forEach { highlight ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = highlight,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }

                    OutlinedButton(
                        onClick = onDialSalesMobile,
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("detail_call_mobile")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Call,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("تواصل مع أ/ محمد عبده (المبيعات): ${DaraRepository.SALES_MOBILE_NUMBER}")
                    }
                }
            }
        }
    }
}
