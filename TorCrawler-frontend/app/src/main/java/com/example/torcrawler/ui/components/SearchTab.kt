package com.example.torcrawler.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.torcrawler.R

@Composable
fun SearchTab(
    navController: NavController
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp),
            modifier = Modifier.padding(top = 25.dp),
        ){
            val image: Painter = painterResource(id = R.drawable.ic_search_tor)
            Image(
                painter = image,
                contentDescription = "globe icon",
                modifier = Modifier.size(140.dp)
            )

            var textState by remember { mutableStateOf("") }

            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                placeholder = { Text(text = "Seach term to find related Onion links",color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            )

            OutlinedButton(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    navController.navigate("detectedOnions/$textState")
                },
                modifier = Modifier
                    .width(142.dp)
                    .height(44.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Search", fontSize = 16.sp)
            }
        }

        Text(
            text = stringResource(id = R.string.url_tab_disclaimer),
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

    }
}

private const val TAG = "SearchTab"