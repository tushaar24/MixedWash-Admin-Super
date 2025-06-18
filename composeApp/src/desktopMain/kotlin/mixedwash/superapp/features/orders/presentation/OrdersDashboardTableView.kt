package mixedwash.superapp.features.orders.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OrdersDashboardTableView(
    columnValues: List<List<String>>,
    modifier: Modifier = Modifier,
){
    var scrollable = rememberScrollState()
    LazyColumn(
        modifier = modifier
            .horizontalScroll(scrollable)
    ) {
        items(count = columnValues.size){ index ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(
                        if (index % 2 == 0)
                            Color(0xff252525)
                        else
                            Color(0xff2A2A2A)
                    )
            ) {
                columnValues[index].forEachIndexed { yIndex, value ->
                    OrdersDashboardColumnItemView(
                        label = value,
                        isTitle = yIndex == 0,
                        modifier = Modifier
                            .width(150.dp)
                    )
                }
            }
        }
    }
}