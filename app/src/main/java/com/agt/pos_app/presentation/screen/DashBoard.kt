package com.agt.pos_app.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agt.pos_app.data.module.Table
import com.agt.pos_app.data.module.TableStatus
import com.agt.pos_app.presentation.ui.theme.availableColor
import com.agt.pos_app.presentation.ui.theme.bookedColor
import com.agt.pos_app.presentation.ui.theme.occupideColor
import com.agt.pos_app.util.inPosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(tableList: List<Table> = emptyList()) {

    Box(modifier = Modifier) {

        Column {
            TopAppBar(title = {
                Text(text = "Pos")
            })

            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(tableList) {
                    BookingTable(table = it)
                }
            }


        }


    }
}


@Composable
fun BookingTable(table: Table) {

    ElevatedCard(
        modifier = Modifier
            .size(150.dp)
            .padding(10.dp)
            .clickable(true) {

            }
        ,
        colors = CardDefaults.elevatedCardColors(
            contentColor = Color.White,
            containerColor = when (table.tableStatus) {
                TableStatus.AVAILABLE -> availableColor
                TableStatus.BOOKED -> bookedColor
                TableStatus.OCCUPIED -> occupideColor
            }
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 0.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Table No \n\n ${table.tableNo}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp,fontWeight = FontWeight.Bold)
            )
        }


    }


}


@Preview
@Composable
fun Dashboard_ScreenPrev() {
    inPosTheme {
        DashBoardScreen(demoTable)
    }

}

val demoTable = listOf(
    Table(
        1,
        TableStatus.AVAILABLE
    ),
    Table(
        2,
        TableStatus.BOOKED
    ),
    Table(
        3,
        TableStatus.OCCUPIED
    ),
    Table(
        4,
        TableStatus.BOOKED
    ),
    Table(
        5,
        TableStatus.AVAILABLE
    )
)