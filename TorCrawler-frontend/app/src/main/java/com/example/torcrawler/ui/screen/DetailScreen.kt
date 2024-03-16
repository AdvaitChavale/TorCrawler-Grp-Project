package com.example.torcrawler.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.torcrawler.R

@Composable
fun DetailScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
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
                Text(text = "High Security Risk", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFF84747))
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
                Text(text = "https:ieeexplore.ieee.orgdocument/10064292.onion")
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Site Title", fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "marketplace", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        val tabs = listOf(
            "Summary",
            "Headers",
            "Links",
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
    }

}


