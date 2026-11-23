package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.BankPartner
import com.example.model.Compound
import com.example.model.CompoundFilter
import com.example.model.CompoundStatus
import com.example.model.ContactChannel
import com.example.model.DaraRepository
import com.example.model.InstallmentRow
import com.example.model.PaymentPlanMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.pow
import kotlin.math.roundToLong

data class DaraUiState(
    val allCompounds: List<Compound> = DaraRepository.compounds,
    val selectedFilter: CompoundFilter = CompoundFilter.ALL,
    val selectedCompoundForComparisonId: String = "kayan",
    val contactChannels: List<ContactChannel> = DaraRepository.contactChannels,
    val bankPartners: List<BankPartner> = DaraRepository.bankPartners,
    val projectAdvantages: List<Pair<String, String>> = DaraRepository.projectAdvantages,
    val paymentPlanMode: PaymentPlanMode = PaymentPlanMode.DEVELOPER_QUARTERLY,
    val unitPriceEgp: Long = 847_000L,
    val developerDownPaymentEgp: Long = 127_000L,
    val mortgageDownPaymentEgp: Long = 127_000L,
    val mortgageYears: Int = 10,
    val mortgageAnnualRatePercent: Float = 8.0f,
    val showFullSchedule: Boolean = false
) {
    val filteredCompounds: List<Compound>
        get() = when (selectedFilter) {
            CompoundFilter.ALL -> allCompounds
            CompoundFilter.AVAILABLE -> allCompounds.filter { it.status == CompoundStatus.AVAILABLE_LAST_FLOOR }
            CompoundFilter.SOLD_OUT -> allCompounds.filter { it.status == CompoundStatus.SOLD_OUT }
        }

    val kayanCompound: Compound
        get() = allCompounds.first { it.id == "kayan" }

    val selectedComparisonCompound: Compound
        get() = allCompounds.firstOrNull { it.id == selectedCompoundForComparisonId } ?: kayanCompound

    val developerRemainingEgp: Long
        get() = (unitPriceEgp - developerDownPaymentEgp).coerceAtLeast(0L)

    val developerQuartersCount: Int = 20

    val developerQuarterlyInstallmentEgp: Long
        get() = if (unitPriceEgp == 847_000L && developerDownPaymentEgp == 127_000L) {
            36_000L
        } else {
            (developerRemainingEgp.toDouble() / developerQuartersCount).roundToLong()
        }

    val isOfficialDeveloperPlan: Boolean
        get() = unitPriceEgp == 847_000L && developerDownPaymentEgp == 127_000L

    val developerSchedule: List<InstallmentRow>
        get() {
            val rows = mutableListOf<InstallmentRow>()
            var remaining = developerRemainingEgp
            val baseInstallment = developerQuarterlyInstallmentEgp
            for (q in 1..developerQuartersCount) {
                val payment = if (q == developerQuartersCount) remaining else baseInstallment.coerceAtMost(remaining)
                remaining = (remaining - payment).coerceAtLeast(0L)
                val year = ((q - 1) / 4) + 1
                val quarterInYear = ((q - 1) % 4) + 1
                rows.add(
                    InstallmentRow(
                        quarterNumber = q,
                        labelAr = "السنة $year - الربع $quarterInYear",
                        amountEgp = payment,
                        remainingAfterEgp = remaining
                    )
                )
            }
            return rows
        }

    val mortgagePrincipalEgp: Long
        get() = (unitPriceEgp - mortgageDownPaymentEgp).coerceAtLeast(0L)

    val mortgageMonthlyPaymentEgp: Long
        get() {
            val principal = mortgagePrincipalEgp.toDouble()
            if (principal <= 0.0) return 0L
            val months = mortgageYears * 12
            val monthlyRate = (mortgageAnnualRatePercent / 100.0) / 12.0
            if (monthlyRate <= 0.0) {
                return (principal / months).roundToLong()
            }
            val factor = (1.0 + monthlyRate).pow(months)
            val pmt = principal * (monthlyRate * factor) / (factor - 1.0)
            return pmt.roundToLong()
        }

    val mortgageTotalPayableEgp: Long
        get() = mortgageDownPaymentEgp + (mortgageMonthlyPaymentEgp * mortgageYears * 12L)
}

class DaraViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DaraUiState())
    val uiState: StateFlow<DaraUiState> = _uiState.asStateFlow()

    fun setFilter(filter: CompoundFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun selectCompoundForComparison(compoundId: String) {
        _uiState.update { it.copy(selectedCompoundForComparisonId = compoundId) }
    }

    fun setPaymentPlanMode(mode: PaymentPlanMode) {
        _uiState.update { it.copy(paymentPlanMode = mode) }
    }

    fun selectUnitPricePreset(priceEgp: Long) {
        val clampedPrice = priceEgp.coerceIn(847_000L, 880_000L)
        val defaultFifteenPercent = if (clampedPrice == 847_000L) {
            127_000L
        } else {
            ((clampedPrice * 0.15) / 1000.0).roundToLong() * 1000L
        }
        _uiState.update {
            it.copy(
                unitPriceEgp = clampedPrice,
                developerDownPaymentEgp = defaultFifteenPercent,
                mortgageDownPaymentEgp = defaultFifteenPercent
            )
        }
    }

    fun updateDeveloperDownPayment(amountEgp: Long) {
        val minFifteenPercent = ((_uiState.value.unitPriceEgp * 0.15) / 1000.0).roundToLong() * 1000L
        val effectiveMin = minFifteenPercent.coerceAtMost(127_000L)
        val clamped = amountEgp.coerceIn(effectiveMin, 500_000L)
        _uiState.update { it.copy(developerDownPaymentEgp = clamped) }
    }

    fun resetToOfficialDeveloperPlan() {
        _uiState.update {
            it.copy(
                paymentPlanMode = PaymentPlanMode.DEVELOPER_QUARTERLY,
                unitPriceEgp = 847_000L,
                developerDownPaymentEgp = 127_000L
            )
        }
    }

    fun updateMortgageDownPayment(amountEgp: Long) {
        val clamped = amountEgp.coerceIn(127_000L, 600_000L)
        _uiState.update { it.copy(mortgageDownPaymentEgp = clamped) }
    }

    fun updateMortgageYears(years: Int) {
        _uiState.update { it.copy(mortgageYears = years.coerceIn(3, 25)) }
    }

    fun updateMortgageRate(ratePercent: Float) {
        _uiState.update { it.copy(mortgageAnnualRatePercent = ratePercent.coerceIn(3f, 22f)) }
    }

    fun toggleFullSchedule() {
        _uiState.update { it.copy(showFullSchedule = !it.showFullSchedule) }
    }
}
