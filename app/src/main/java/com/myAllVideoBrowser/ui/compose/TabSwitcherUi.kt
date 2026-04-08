package com.myAllVideoBrowser.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TabItem(
    val id: String,
    val title: String,
    val url: String,
    val isSelected: Boolean = false
)

@Composable
fun TabSwitcherScreen(
    tabs: List<TabItem>,
    onTabClick: (TabItem) -> Unit,
    onTabClose: (TabItem) -> Unit,
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onForwardClick: () -> Unit = {},
    onAddTabClick: () -> Unit = {}
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "标签",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.Black
                )
            }

            // Tab List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(tabs) { tab ->
                    TabListItem(
                        tab = tab,
                        onClick = { onTabClick(tab) },
                        onClose = { onTabClose(tab) }
                    )
                }
            }
            
            Divider(color = Color(0xFFF1F3F7))

            // Bottom Toolbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
                IconButton(onClick = onHomeClick) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Black)
                }
                IconButton(onClick = onForwardClick) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Forward", tint = Color.Black)
                }
                IconButton(onClick = onAddTabClick) {
                    Icon(Icons.Default.Add, contentDescription = "New Tab", tint = Color.Black)
                }
            }
        }
    }
}

@Composable
fun TabListItem(
    tab: TabItem,
    onClick: () -> Unit,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (tab.isSelected) Color(0xFFF1F3F7) else Color.Transparent)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (tab.url.startsWith("https://haokan")) Icons.Default.PlayCircle else Icons.Default.Public,
            contentDescription = null,
            tint = if (tab.url.startsWith("https://haokan")) Color.Red else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = tab.title,
            fontSize = 16.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        
        IconButton(onClick = onClose) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
        }
    }
}
