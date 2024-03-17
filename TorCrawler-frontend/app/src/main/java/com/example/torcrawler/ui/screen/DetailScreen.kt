package com.example.torcrawler.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.torcrawler.R
import com.example.torcrawler.data.model.AnalaysisResponse
import com.example.torcrawler.data.model.IdentifiedThreats
import com.example.torcrawler.util.Util

@Composable
fun DetailScreen(
) {

    val url = Util.curr_onion
    val scrollState =  rememberScrollState()
    val viewModel: DetailScreenViewModel = viewModel()
    val analaysisResponse by viewModel.analysisResult.observeAsState()

    LaunchedEffect(Unit) {
        val urls = url.replace("^","/")
        Log.d(TAG, "DetailScreen: $urls")
        viewModel.searchOnion(urls)
    }

    LaunchedEffect(analaysisResponse) {
        Log.d(TAG, "DetailScreen: $analaysisResponse")
    }

    if(analaysisResponse == null){
        // show loading

        return
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .scrollable(scrollState, orientation = Orientation.Vertical)
    ){

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
        Card(
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            colors = CardColors(containerColor = Color(0xFF313D5D), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                modifier = Modifier.padding(start = 20.dp,top = 20.dp, bottom = 12.dp)
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_cross),
                    contentDescription = "security icon",
                    tint = Color(0xFFF84747),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = analaysisResponse!!.security_risk_title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFF84747))
            }
        }
        Card(
            shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp),
            colors = CardColors(containerColor = Color(0xFF242C43), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier.padding(16.dp)
            ){
                Text(text = analaysisResponse!!.url)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = analaysisResponse!!.title, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
                Text(text = analaysisResponse!!.market_place, fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        val tabs = listOf(
            "Summary",
            "Headers",
            "Links",
            "Identified Threats",
            "Images",
        )

        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }
        val outlineButtonColor = ButtonDefaults.buttonColors().copy(contentColor = Color.White, containerColor = Color.Transparent)
        val filledButtonColors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.primary)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(tabs.size){
                Button(
                    onClick = { selectedTabIndex = it },
                    colors = if(selectedTabIndex == it) filledButtonColors else outlineButtonColor,
                    border = if(selectedTabIndex == it) null else BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = tabs[it])
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        when(selectedTabIndex){
            0 -> Summary(analaysisResponse!!)
            1 -> Header(analaysisResponse!!)
            2 -> Links(analaysisResponse!!)
            3 -> IdentifiedThreatsView(analaysisResponse!!)
            4 -> Images()
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Summary(analaysisResponse: AnalaysisResponse) {
    val scrollstate = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scrollable(scrollstate, Orientation.Vertical)
    ){
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardColors(containerColor = Color(0xFF242C43), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column{
                Text(text = "Site Score", modifier = Modifier.padding(top = 20.dp,start = 20.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    //horizontalArrangement = Arrangement.spacedBy(72.dp),
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ){
                    Box{
                        CircularProgressIndicator(
                            strokeWidth = 15.dp,
                            strokeCap = StrokeCap.Round,
                            trackColor = Color(0xFF161625),
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .size(125.dp)
                                .align(Alignment.TopCenter),
                            progress = {
                                minOf(0.85f,analaysisResponse.threat_risk.toFloat() / 100)
                            },
                            color = Color(0xFFF4D86D)
                        )
                        Text(text = "${minOf(85,analaysisResponse.threat_risk)}%", modifier = Modifier.align(Alignment.Center))
                        Text(text = "Threat Level", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White, modifier = Modifier.align(Alignment.BottomCenter))
                    }
                    Spacer(modifier = Modifier.size(50.dp))
                    Box{
                        CircularProgressIndicator(
                            progress = {
                                analaysisResponse.anonymity_score.toFloat() / 100
                            },
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .size(125.dp)
                                .align(Alignment.TopCenter),
                            strokeWidth = 15.dp,
                            trackColor = Color(0xFF161625),
                            strokeCap = StrokeCap.Round,
                            color = Color(0xFF7114A0)
                        )
                        Text(text = "${analaysisResponse.anonymity_score}%", modifier = Modifier.align(Alignment.Center))
                        Text(text = "Anonymity", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White, modifier = Modifier.align(Alignment.BottomCenter))
                    }

                    Box{
                        CircularProgressIndicator(
                            strokeWidth = 15.dp,
                            strokeCap = StrokeCap.Round,
                            trackColor = Color(0xFF161625),
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .size(125.dp)
                                .align(Alignment.TopCenter),
                            progress = {
                                analaysisResponse.privacy_risk.toFloat() / 100
                            }
                        )
                        Text(text = "${analaysisResponse.privacy_risk}%", modifier = Modifier.align(Alignment.Center))
                        Text(text = "Privacy", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White, modifier = Modifier.align(Alignment.BottomCenter))
                    }
                    Spacer(modifier = Modifier.size(50.dp))
                    Box{
                        CircularProgressIndicator(
                            strokeWidth = 15.dp,
                            strokeCap = StrokeCap.Round,
                            trackColor = Color(0xFF161625),
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .size(125.dp)
                                .align(Alignment.TopCenter),
                            progress = {
                                analaysisResponse.legal_risk.toFloat() / 100
                            },
                            color = Color(0xFF48F145)
                        )
                        Text(text = "${analaysisResponse.legal_risk}%", modifier = Modifier.align(Alignment.Center))
                        Text(text = "Illegality", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White, modifier = Modifier.align(Alignment.BottomCenter))
                    }
                }
            }
        }
    }
}

@Composable
fun Header(
    analaysisResponse: AnalaysisResponse
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = Color(0xFF242C43), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Headers",
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ){
                items(analaysisResponse.header_tags.size) {
                    Card(
                        colors = CardColors(containerColor = Color(0xFF8CABFC), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
                    ){
                        Text(text = analaysisResponse.header_tags[it],modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}

@Composable 
fun Links(
    analaysisResponse: AnalaysisResponse
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = Color(0xFF242C43), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Links",
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ){
                items(analaysisResponse.header_tags.size) {
                    Card(
                        colors = CardColors(containerColor = Color(0xFF8CABFC), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Column {
                            Text(text = analaysisResponse.header_tags[it],modifier = Modifier.padding(top = 10.dp,start = 10.dp, end = 10.dp), textDecoration = TextDecoration.Underline)
                            Text(text = "Title: ${analaysisResponse.header_tags[it]}",modifier = Modifier.padding(10.dp),color = Color(0xFF272525))
                        }
                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IdentifiedThreatsView(
    analaysisResponse: AnalaysisResponse
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(containerColor = Color(0xFF242C43), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Identified Thearts",
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            FlowRow {
                analaysisResponse.identified_threats.threats.forEach {
                    Card(
                        colors = CardColors(containerColor = getThreatColor(it.threat_rating), contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White),
                        modifier = Modifier.padding(10.dp)
                    ){
                        Text(text = it.threat_title,modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun Images() {

}

private const val TAG = "DetailScreen"

private fun getThreatColor(threat: Int): Color {
    return when(threat){
        1 -> Color(0xFFF4D86D)
        2 -> Color(0xFFF0750F)
        else -> Color(0xFFFF0000)
    }
}



