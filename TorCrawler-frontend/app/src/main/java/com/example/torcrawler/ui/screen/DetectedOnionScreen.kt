package com.example.torcrawler.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.utils.Utils
import com.example.torcrawler.Screen
import com.example.torcrawler.util.Util

@Composable
fun DetectedOnionScreen(
    search_term: String,
    navController: NavController
) {

    val viewmodel : DetectedOnionViewModel = viewModel()
    val result by viewmodel.searchResults.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        viewmodel.searchOnion(search_term)
    }

    if(result.isEmpty()){
        LottieSearchAnimation()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ){
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "DARKWEB DETECTIVE",
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Detected Onions", fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
        HorizontalDivider(thickness = 2.dp, color = Color.White)
        Spacer(modifier = Modifier.padding(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(result.size) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Util.curr_onion = result[it]
                            navController.navigate(Screen.Detail.route)
                       },
                    colors = CardColors(containerColor = Color(0xFF242C43), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.White),
                ) {
                    Text(text = result[it], textDecoration = TextDecoration.Underline, modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp))
                }
            }
        }
    }


}

private const val TAG = "DetectedOnionScreen"