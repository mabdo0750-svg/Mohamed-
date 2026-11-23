package com.example.model

import androidx.annotation.DrawableRes

enum class CompoundStatus {
    AVAILABLE_LAST_FLOOR,
    SOLD_OUT
}

enum class CompoundFilter(val labelAr: String) {
    ALL("الكل (3)"),
    AVAILABLE("متاح حالياً (1)"),
    SOLD_OUT("غير متاح حالياً (2)")
}

enum class FacilityCategory {
    SECURITY,
    LANDSCAPE,
    ROADS_GATES,
    FINISH_READY,
    SALES_FINANCE
}

data class CompoundFacility(
    val titleAr: String,
    val detailAr: String,
    val category: FacilityCategory
)

data class UnitOffer(
    val availabilityBadgeAr: String,
    val floorDescriptionAr: String,
    val unitAreaSqm: Int,
    val priceMinEgp: Long,
    val priceMaxEgp: Long,
    val bookingDepositEgp: Long,
    val downPaymentPercent: Int,
    val downPaymentEgp: Long,
    val quarterlyInstallmentEgp: Long,
    val quartersCount: Int,
    val durationYears: Int,
    val mortgageAvailable: Boolean,
    val mortgageSummaryAr: String
)

data class Compound(
    val id: String,
    val nameAr: String,
    val englishCode: String,
    val locationAr: String,
    val gatesAr: List<String>,
    val unitAreaAr: String,
    val constructionStatusAr: String,
    val taglineAr: String,
    val status: CompoundStatus,
    @DrawableRes val imageRes: Int,
    val descriptionAr: String,
    val facilities: List<CompoundFacility>,
    val highlightsAr: List<String>,
    val unitOffer: UnitOffer? = null
)

data class ContactChannel(
    val id: String,
    val titleAr: String,
    val subtitleAr: String,
    val phoneNumber: String,
    val isHotline: Boolean
)

data class BankPartner(
    val id: String,
    val bankNameAr: String,
    val representativeNameAr: String?,
    val phoneNumbers: List<String>,
    val noteAr: String? = null
)

enum class PaymentPlanMode(val labelAr: String) {
    DEVELOPER_QUARTERLY("التقسيط المباشر (ربع سنوي)"),
    MORTGAGE_FINANCING("التمويل العقاري والبنوك")
}

data class InstallmentRow(
    val quarterNumber: Int,
    val labelAr: String,
    val amountEgp: Long,
    val remainingAfterEgp: Long
)
