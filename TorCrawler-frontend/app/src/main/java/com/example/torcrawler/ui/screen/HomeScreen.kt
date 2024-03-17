package com.example.torcrawler.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.torcrawler.R
import com.example.torcrawler.ui.components.SearchTab
import com.example.torcrawler.ui.components.URLTab

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController){
    val tabs = listOf(
        "URL",
        "SEARCH",
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabs.size
    }

    LaunchedEffect (selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(34.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_main),
            contentDescription = "Darkweb Detective",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "DARKWEB DETECTIVE",
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(50.dp))
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = { Text(title, fontSize = 16.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            userScrollEnabled = true
        ){index ->
            when(index){
                0 -> URLTab(navController)
                1 -> SearchTab(navController)
            }
        }
    }

}