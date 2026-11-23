package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Apartment
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.model.DaraRepository
import com.example.ui.components.copyToClipboard
import com.example.ui.components.dialPhoneNumber
import com.example.ui.screens.CalculatorScreen
import com.example.ui.screens.CompoundDetailScreen
import com.example.ui.screens.CompoundsSectionScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.OverviewScreen
import com.example.ui.theme.DaraElSuezTheme
import com.example.viewmodel.DaraViewModel
import kotlinx.coroutines.launch

object DaraRoutes {
    const val OVERVIEW = "overview"
    const val COMPOUNDS = "compounds"
    const val CALCULATOR = "calculator"
    const val CONTACT = "contact"
    const val COMPOUND_DETAIL = "compound/{compoundId}"

    fun compoundDetail(compoundId: String) = "compound/$compoundId"
}

private data class TopLevelDestination(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val topLevelDestinations = listOf(
    TopLevelDestination(
        route = DaraRoutes.OVERVIEW,
        labelRes = R.string.nav_overview,
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        testTag = "nav_overview"
    ),
    TopLevelDestination(
        route = DaraRoutes.COMPOUNDS,
        labelRes = R.string.nav_compounds,
        selectedIcon = Icons.Rounded.Apartment,
        unselectedIcon = Icons.Outlined.Apartment,
        testTag = "nav_compounds"
    ),
    TopLevelDestination(
        route = DaraRoutes.CALCULATOR,
        labelRes = R.string.nav_calculator,
        selectedIcon = Icons.Rounded.Calculate,
        unselectedIcon = Icons.Outlined.Calculate,
        testTag = "nav_calculator"
    ),
    TopLevelDestination(
        route = DaraRoutes.CONTACT,
        labelRes = R.string.nav_contact,
        selectedIcon = Icons.Rounded.Call,
        unselectedIcon = Icons.Outlined.Call,
        testTag = "nav_contact"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DaraElSuezTheme {
                DaraElSuezApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaraElSuezApp(
    viewModel: DaraViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isTopLevelRoute = topLevelDestinations.any { dest ->
        currentDestination?.hierarchy?.any { it.route == dest.route } == true
    }

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showMessage: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message)
        }
    }

    val onDialNumber: (String) -> Unit = { phone ->
        dialPhoneNumber(
            context = context,
            phoneNumber = phone,
            onNoDialer = showMessage
        )
    }

    val onCopyNumber: (String, String) -> Unit = { label, phone ->
        copyToClipboard(
            context = context,
            label = label,
            text = phone,
            onCopied = showMessage
        )
    }

    val navigateToTopLevel: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Row(modifier = Modifier.fillMaxSize()) {
            if (useNavigationRail && isTopLevelRoute) {
                NavigationRail(
                    modifier = Modifier
                        .fillMaxHeight()
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Spacer(modifier = Modifier.weight(0.2f))
                    topLevelDestinations.forEach { dest ->
                        val selected = currentDestination?.hierarchy?.any { it.route == dest.route } == true
                        val label = stringResource(id = dest.labelRes)
                        NavigationRailItem(
                            selected = selected,
                            onClick = { navigateToTopLevel(dest.route) },
                            icon = {
                                Icon(
                                    imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                    contentDescription = label
                                )
                            },
                            label = { Text(label) },
                            modifier = Modifier.testTag(dest.testTag)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background,
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                topBar = {
                    if (isTopLevelRoute) {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }
                },
                bottomBar = {
                    if (!useNavigationRail && isTopLevelRoute) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface
                        ) {
                            topLevelDestinations.forEach { dest ->
                                val selected = currentDestination?.hierarchy?.any { it.route == dest.route } == true
                                val label = stringResource(id = dest.labelRes)
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { navigateToTopLevel(dest.route) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                            contentDescription = label
                                        )
                                    },
                                    label = { Text(label) },
                                    modifier = Modifier.testTag(dest.testTag)
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = DaraRoutes.OVERVIEW,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable(DaraRoutes.OVERVIEW) {
                        OverviewScreen(
                            uiState = uiState,
                            onSelectFilter = viewModel::setFilter,
                            onCompoundClick = { compoundId ->
                                navController.navigate(DaraRoutes.compoundDetail(compoundId))
                            },
                            onOpenCompoundsSection = { navigateToTopLevel(DaraRoutes.COMPOUNDS) },
                            onOpenCalculator = { navigateToTopLevel(DaraRoutes.CALCULATOR) },
                            onDialNumber = onDialNumber
                        )
                    }

                    composable(DaraRoutes.COMPOUNDS) {
                        CompoundsSectionScreen(
                            uiState = uiState,
                            onSelectCompoundTab = viewModel::selectCompoundForComparison,
                            onOpenCompoundDetail = { compoundId ->
                                navController.navigate(DaraRoutes.compoundDetail(compoundId))
                            },
                            onOpenCalculator = { navigateToTopLevel(DaraRoutes.CALCULATOR) }
                        )
                    }

                    composable(DaraRoutes.CALCULATOR) {
                        CalculatorScreen(
                            uiState = uiState,
                            onModeSelected = viewModel::setPaymentPlanMode,
                            onSelectUnitPricePreset = viewModel::selectUnitPricePreset,
                            onDeveloperDownPaymentChanged = viewModel::updateDeveloperDownPayment,
                            onResetDeveloperPlan = viewModel::resetToOfficialDeveloperPlan,
                            onToggleSchedule = viewModel::toggleFullSchedule,
                            onMortgageDownPaymentChanged = viewModel::updateMortgageDownPayment,
                            onMortgageYearsChanged = viewModel::updateMortgageYears,
                            onMortgageRateChanged = viewModel::updateMortgageRate,
                            onDialNumber = onDialNumber,
                            onCopyNumber = onCopyNumber
                        )
                    }

                    composable(DaraRoutes.CONTACT) {
                        ContactScreen(
                            uiState = uiState,
                            onDialNumber = onDialNumber,
                            onCopyNumber = onCopyNumber
                        )
                    }

                    composable(
                        route = DaraRoutes.COMPOUND_DETAIL,
                        arguments = listOf(navArgument("compoundId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val compoundId = backStackEntry.arguments?.getString("compoundId") ?: "kayan"
                        val compound = DaraRepository.getCompoundById(compoundId) ?: uiState.kayanCompound
                        CompoundDetailScreen(
                            compound = compound,
                            onBack = { navController.popBackStack() },
                            onNavigateToKayan = {
                                navController.navigate(DaraRoutes.compoundDetail("kayan")) {
                                    popUpTo(DaraRoutes.OVERVIEW)
                                }
                            },
                            onOpenCalculator = {
                                navController.popBackStack(DaraRoutes.OVERVIEW, inclusive = false)
                                navigateToTopLevel(DaraRoutes.CALCULATOR)
                            },
                            onDialHotline = { onDialNumber(DaraRepository.HOTLINE_NUMBER) },
                            onDialSalesMobile = { onDialNumber(DaraRepository.SALES_MOBILE_NUMBER) }
                        )
                    }
                }
            }
        }
    }
}
