package mixedwash.superapp.features.orders.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import mixedwash.superapp.features.orders.data.entity.OrderDashboardTableItemEntity
import mixedwash.superapp.features.orders.data.mapper.OrderDashboardMapper
import mixedwash.superapp.features.orders.data.service.remote.OrderDashboardService
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersDashboardScreen(
    modifier: Modifier = Modifier
){
    var tableData by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    var orders by remember { mutableStateOf<List<OrderDashboardTableItemEntity>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Filter states
    var fromDate by remember { mutableStateOf("") }
    var toDate by remember { mutableStateOf("") }
    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var service by remember { mutableStateOf("") }

    // Date picker states
    var showFromDatePicker by remember { mutableStateOf(false) }
    var showToDatePicker by remember { mutableStateOf(false) }
    val fromDatePickerState = rememberDatePickerState()
    val toDatePickerState = rememberDatePickerState()

    val orderService = remember { OrderDashboardService() }
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun applyFilters() {
        tableData = OrderDashboardMapper.mapToFilteredTableData(
            orders = orders,
            fromDate = fromDate.takeIf { it.isNotBlank() },
            toDate = toDate.takeIf { it.isNotBlank() },
            customerName = customerName.takeIf { it.isNotBlank() },
            phoneNumber = phoneNumber.takeIf { it.isNotBlank() },
            emailAddress = emailAddress.takeIf { it.isNotBlank() },
            service = service.takeIf { it.isNotBlank() }
        )
    }

    fun clearFilters() {
        fromDate = ""
        toDate = ""
        customerName = ""
        phoneNumber = ""
        emailAddress = ""
        service = ""
        fromDatePickerState.selectedDateMillis = null
        toDatePickerState.selectedDateMillis = null
        tableData = OrderDashboardMapper.mapToTableData(orders)
    }

    LaunchedEffect(Unit) {
        launch {
            try {
                isLoading = true
                errorMessage = null
                val fetchedOrders = orderService.getOrders()
                orders = fetchedOrders
                tableData = OrderDashboardMapper.mapToTableData(fetchedOrders)
            } catch (e: Exception) {
                errorMessage = "Failed to load orders: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // From Date Picker Dialog
    if (showFromDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showFromDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        fromDatePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            fromDate = localDate.format(dateFormatter)
                        }
                        showFromDatePicker = false
                    }
                ) {
                    Text("OK", color = Color(0xff4CAF50))
                }
            },
            dismissButton = {
                TextButton(onClick = { showFromDatePicker = false }) {
                    Text("Cancel", color = Color(0xffB0B0B0))
                }
            }
        ) {
            DatePicker(
                state = fromDatePickerState,
                colors = androidx.compose.material3.DatePickerDefaults.colors(
                    containerColor = Color(0xff1E1E1E),
                    titleContentColor = Color(0xffE0E0E0),
                    headlineContentColor = Color(0xffE0E0E0),
                    weekdayContentColor = Color(0xffB0B0B0),
                    subheadContentColor = Color(0xffB0B0B0),
                    yearContentColor = Color(0xffE0E0E0),
                    currentYearContentColor = Color(0xff4CAF50),
                    selectedYearContentColor = Color.White,
                    selectedYearContainerColor = Color(0xff4CAF50),
                    dayContentColor = Color(0xffE0E0E0),
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color(0xff4CAF50),
                    todayContentColor = Color(0xff4CAF50),
                    todayDateBorderColor = Color(0xff4CAF50)
                )
            )
        }
    }

    // To Date Picker Dialog
    if (showToDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showToDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        toDatePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            toDate = localDate.format(dateFormatter)
                        }
                        showToDatePicker = false
                    }
                ) {
                    Text("OK", color = Color(0xff4CAF50))
                }
            },
            dismissButton = {
                TextButton(onClick = { showToDatePicker = false }) {
                    Text("Cancel", color = Color(0xffB0B0B0))
                }
            }
        ) {
            DatePicker(
                state = toDatePickerState,
                colors = androidx.compose.material3.DatePickerDefaults.colors(
                    containerColor = Color(0xff1E1E1E),
                    titleContentColor = Color(0xffE0E0E0),
                    headlineContentColor = Color(0xffE0E0E0),
                    weekdayContentColor = Color(0xffB0B0B0),
                    subheadContentColor = Color(0xffB0B0B0),
                    yearContentColor = Color(0xffE0E0E0),
                    currentYearContentColor = Color(0xff4CAF50),
                    selectedYearContentColor = Color.White,
                    selectedYearContainerColor = Color(0xff4CAF50),
                    dayContentColor = Color(0xffE0E0E0),
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color(0xff4CAF50),
                    todayContentColor = Color(0xff4CAF50),
                    todayDateBorderColor = Color(0xff4CAF50)
                )
            )
        }
    }

    Column(
        modifier = modifier
            .padding(vertical = 28.dp, horizontal = 24.dp)
    ) {
        Text(
            text = "Order Dashboard",
            color = Color(0xffE0E0E0),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        )

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            else -> {
                // Filter Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(
                            Color(0xff1E1E1E),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Filters",
                        color = Color(0xffE0E0E0),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // First row of filters
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = fromDate,
                            onValueChange = { },
                            label = { Text("From Date", fontSize = 12.sp) },
                            placeholder = { Text("Select date", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).clickable { showFromDatePicker = true },
                            enabled = false,
                            trailingIcon = {
                                Text(
                                    "📅",
                                    fontSize = 16.sp,
                                    color = Color(0xff4CAF50),
                                    modifier = Modifier.clickable { showFromDatePicker = true }
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color(0xffE0E0E0),
                                disabledBorderColor = Color(0xff666666),
                                disabledLabelColor = Color(0xffB0B0B0),
                                disabledTrailingIconColor = Color(0xff4CAF50)
                            )
                        )

                        OutlinedTextField(
                            value = toDate,
                            onValueChange = { },
                            label = { Text("To Date", fontSize = 12.sp) },
                            placeholder = { Text("Select date", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).clickable { showToDatePicker = true },
                            enabled = false,
                            trailingIcon = {
                                Text(
                                    "📅",
                                    fontSize = 16.sp,
                                    color = Color(0xff4CAF50),
                                    modifier = Modifier.clickable { showToDatePicker = true }
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color(0xffE0E0E0),
                                disabledBorderColor = Color(0xff666666),
                                disabledLabelColor = Color(0xffB0B0B0),
                                disabledTrailingIconColor = Color(0xff4CAF50)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Second row of filters
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Customer Name", fontSize = 12.sp) },
                            placeholder = { Text("Search customer (fuzzy)", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xffE0E0E0),
                                unfocusedTextColor = Color(0xffE0E0E0),
                                focusedBorderColor = Color(0xff4CAF50),
                                unfocusedBorderColor = Color(0xff666666),
                                focusedLabelColor = Color(0xff4CAF50),
                                unfocusedLabelColor = Color(0xffB0B0B0),
                                cursorColor = Color(0xff4CAF50)
                            )
                        )

                        OutlinedTextField(
                            value = service,
                            onValueChange = { service = it },
                            label = { Text("Service", fontSize = 12.sp) },
                            placeholder = { Text("Search service (fuzzy)", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xffE0E0E0),
                                unfocusedTextColor = Color(0xffE0E0E0),
                                focusedBorderColor = Color(0xff4CAF50),
                                unfocusedBorderColor = Color(0xff666666),
                                focusedLabelColor = Color(0xff4CAF50),
                                unfocusedLabelColor = Color(0xffB0B0B0),
                                cursorColor = Color(0xff4CAF50)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Third row of filters
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("Phone Number", fontSize = 12.sp) },
                            placeholder = { Text("Search phone number", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xffE0E0E0),
                                unfocusedTextColor = Color(0xffE0E0E0),
                                focusedBorderColor = Color(0xff4CAF50),
                                unfocusedBorderColor = Color(0xff666666),
                                focusedLabelColor = Color(0xff4CAF50),
                                unfocusedLabelColor = Color(0xffB0B0B0),
                                cursorColor = Color(0xff4CAF50)
                            )
                        )

                        OutlinedTextField(
                            value = emailAddress,
                            onValueChange = { emailAddress = it },
                            label = { Text("Email Address", fontSize = 12.sp) },
                            placeholder = { Text("Search email address", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xffE0E0E0),
                                unfocusedTextColor = Color(0xffE0E0E0),
                                focusedBorderColor = Color(0xff4CAF50),
                                unfocusedBorderColor = Color(0xff666666),
                                focusedLabelColor = Color(0xff4CAF50),
                                unfocusedLabelColor = Color(0xffB0B0B0),
                                cursorColor = Color(0xff4CAF50)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Filter buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { applyFilters() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff4CAF50)
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text("Apply Filters", color = Color.White)
                        }

                        Button(
                            onClick = { clearFilters() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff666666)
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text("Clear", color = Color.White)
                        }
                    }
                }

                OrdersDashboardTableView(
                    tableData,
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
                )
            }
        }
    }
}
