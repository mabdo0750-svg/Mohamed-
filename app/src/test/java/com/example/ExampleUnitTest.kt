package com.example

import com.example.model.CompoundFilter
import com.example.model.CompoundStatus
import com.example.model.DaraRepository
import com.example.viewmodel.DaraViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun repositoryContainsExactCompoundsFacilitiesPricingAndBanks() {
        val compounds = DaraRepository.compounds
        assertEquals(3, compounds.size)

        val hayat = DaraRepository.getCompoundById("hayat")
        val bustan = DaraRepository.getCompoundById("bustan")
        val kayan = DaraRepository.getCompoundById("kayan")

        assertNotNull(hayat)
        assertNotNull(bustan)
        assertNotNull(kayan)

        assertEquals(CompoundStatus.SOLD_OUT, hayat?.status)
        assertEquals(CompoundStatus.SOLD_OUT, bustan?.status)
        assertEquals(CompoundStatus.AVAILABLE_LAST_FLOOR, kayan?.status)

        assertTrue((hayat?.facilities?.size ?: 0) >= 4)
        assertTrue((bustan?.facilities?.size ?: 0) >= 4)
        assertTrue((kayan?.facilities?.size ?: 0) >= 5)
        assertEquals(3, kayan?.gatesAr?.size)

        val offer = kayan?.unitOffer
        assertNotNull(offer)
        assertEquals(90, offer?.unitAreaSqm)
        assertEquals(847_000L, offer?.priceMinEgp)
        assertEquals(880_000L, offer?.priceMaxEgp)
        assertEquals(10_000L, offer?.bookingDepositEgp)
        assertEquals(15, offer?.downPaymentPercent)
        assertEquals(127_000L, offer?.downPaymentEgp)
        assertEquals(36_000L, offer?.quarterlyInstallmentEgp)
        assertTrue(offer?.mortgageAvailable == true)

        assertEquals("15444", DaraRepository.HOTLINE_NUMBER)
        assertEquals("01033389338", DaraRepository.SALES_MOBILE_NUMBER)
        assertEquals(9, DaraRepository.bankPartners.size)
    }

    @Test
    fun viewModelComputesOfficialDeveloperScheduleFiltersAndComparison() {
        val vm = DaraViewModel()
        val initial = vm.uiState.value
        assertEquals(36_000L, initial.developerQuarterlyInstallmentEgp)
        assertEquals(20, initial.developerSchedule.size)
        assertEquals(0L, initial.developerSchedule.last().remainingAfterEgp)

        vm.selectCompoundForComparison("hayat")
        assertEquals("hayat", vm.uiState.value.selectedComparisonCompound.id)

        vm.setFilter(CompoundFilter.AVAILABLE)
        assertEquals(1, vm.uiState.value.filteredCompounds.size)
        assertEquals("kayan", vm.uiState.value.filteredCompounds.first().id)

        vm.setFilter(CompoundFilter.SOLD_OUT)
        assertEquals(2, vm.uiState.value.filteredCompounds.size)
    }
}
