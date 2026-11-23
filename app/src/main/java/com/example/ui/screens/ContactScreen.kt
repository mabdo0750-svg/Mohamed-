package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.R
import com.example.model.DaraRepository
import com.example.ui.components.BankPartnerCard
import com.example.ui.components.PhoneActionCard
import com.example.ui.components.shareText
import com.example.viewmodel.DaraUiState

@Composable
fun ContactScreen(
    uiState: DaraUiState,
    onDialNumber: (String) -> Unit,
    onCopyNumber: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var clientName by rememberSaveable { mutableStateOf("") }
    var preferredPayment by rememberSaveable { mutableStateOf("تقسيط مباشر (مقدم 15% حتى 5 سنوات)") }
    var requestFinishingVideo by rememberSaveable { mutableStateOf(true) }
    val paymentChoices = listOf(
        "تقسيط مباشر (مقدم 15% حتى 5 سنوات)",
        "تمويل عقاري (مقدم 15%)"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("contact_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "contact_header") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_darah_logo),
                    contentDescription = "شعار دارة",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "المبيعات والبنوك المتعاونة",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = DaraRepository.DEVELOPER_ENTITY,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        item(key = "sales_office_card") {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
                    .testTag("sales_office_card"),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Badge,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Column {
                            Text(
                                text = "أ/ ${DaraRepository.SALES_DIRECTOR_NAME}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = DaraRepository.SALES_DIRECTOR_TITLE,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = DaraRepository.SALES_OFFICE_ADDRESS,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = DaraRepository.SALES_OFFICE_HOURS,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PlayCircleOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "متاح إرسال فيديو توضيحي لشكل الوحدة بعد التشطيب بالكامل عند التواصل.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        items(
            items = uiState.contactChannels,
            key = { it.id }
        ) { channel ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
            ) {
                PhoneActionCard(
                    channel = channel,
                    onCallClick = { onDialNumber(channel.phoneNumber) },
                    onCopyClick = { onCopyNumber(channel.titleAr, channel.phoneNumber) }
                )
            }
        }

        item(key = "inquiry_builder") {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "طلب حجز أو فيديو التشطيب الكامل",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "جهز رسالة استفسار موجهة لإدارة المبيعات لحجز وحدة دارة كيان (90 م²) أو طلب الفيديو التوضيحي للتشطيب.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("الاسم (اختياري)") },
                        placeholder = { Text("مثال: أحمد محمد") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("inquiry_name_input")
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "نظام السداد المطلوب",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            paymentChoices.forEachIndexed { idx, choice ->
                                FilterChip(
                                    selected = preferredPayment == choice,
                                    onClick = { preferredPayment = choice },
                                    label = {
                                        Text(
                                            text = if (idx == 0) "تقسيط مباشر" else "تمويل عقاري",
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .minimumInteractiveComponentSize()
                                        .testTag("inquiry_choice_$idx"),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }
                    }

                    FilterChip(
                        selected = requestFinishingVideo,
                        onClick = { requestFinishingVideo = !requestFinishingVideo },
                        label = { Text("إرسال فيديو توضيحي لشكل الوحدة بعد التشطيب") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.PlayCircleOutline,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("inquiry_video_chip")
                    )

                    Button(
                        onClick = {
                            val namePart = if (clientName.isBlank()) "" else "الاسم: ${clientName.trim()}\n"
                            val videoPart = if (requestFinishingVideo) "أرجو إرسال الفيديو التوضيحي لشكل الوحدة بعد التشطيب بالكامل.\n" else ""
                            val message = "السلام عليكم أ/ محمد عبده، أستفسر عن كمباوند دارة كيان بالصفا (دارة السويس) - وحدة 90 م² (847 – 880 ألف جنيه، حجز 10,000 جنيه).\n" +
                                namePart +
                                "النظام المفضل: $preferredPayment\n" +
                                videoPart +
                                "مكتب المبيعات: كمباوند دارة حياة – شارع إبراهيم نافع – عمارة 145 – الدور الأول"
                            shareText(context, "استفسار حجز - دارة السويس", message)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("send_inquiry_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Send,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("إرسال / مشاركة طلب الاستفسار")
                    }

                    OutlinedButton(
                        onClick = {
                            val projectSummary = "مشروع دارة السويس (${DaraRepository.DEVELOPER_ENTITY}):\n" +
                                "• كمباوند دارة كيان بالصفا (بوابات: عتاقة، مصر إيران، أحمد زويل)\n" +
                                "  - مساحة الوحدات: 90 م² متشطبة وجاهزة\n" +
                                "  - السعر: من 847,000 إلى 880,000 ج.م (متاح دور أخير بـ 847 ألف)\n" +
                                "  - قيمة الحجز: 10,000 ج.م | مقدم 15% | تقسيط حتى 5 سنوات أو تمويل عقاري\n" +
                                "• كمباوند دارة حياة (غير متاح حالياً - المرحلة الأولى Sold Out) – يضم مكتب المبيعات (شارع إبراهيم نافع – عمارة 145 – الدور الأول)\n" +
                                "• كمباوند دارة بستان (غير متاح حالياً - المرحلة الأولى Sold Out)\n" +
                                "للتواصل: ${DaraRepository.HOTLINE_NUMBER} أو ${DaraRepository.SALES_MOBILE_NUMBER}"
                            shareText(context, "ملخص مشروع دارة السويس", projectSummary)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("share_project_brochure_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("مشاركة بطاقة مشروع دارة السويس")
                    }
                }
            }
        }

        item(key = "banks_section_header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "أرقام موظفي البنوك للتمويل العقاري",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "البنوك المتعاونة: بنك مصر، الإسكان والتعمير، CIB، QNB، البنك الأهلي المصري، بنك القاهرة، تنمية الصادرات، وتنمية الصناعات",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(
            items = uiState.bankPartners,
            key = { "contact_bank_${it.id}" }
        ) { partner ->
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
}
