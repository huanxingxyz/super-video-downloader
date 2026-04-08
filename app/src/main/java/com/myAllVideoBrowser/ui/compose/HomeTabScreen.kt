package com.myAllVideoBrowser.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myAllVideoBrowser.R

data class SiteInfo(
    val name: String,
    val url: String,
    val iconRes: Int? = null,
    val iconVector: ImageVector? = null,
    val backgroundColor: Color = Color.Gray
)

@Composable
fun HomeTabScreen(
    tabCount: Int = 1,
    onSearch: (String) -> Unit = {},
    onSiteClick: (String) -> Unit = {},
    onHelpClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onTabCountClick: () -> Unit = {},
    onDownloadMethodClick: () -> Unit = {}
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .clickable { onTabCountClick() }
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tabCount.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onHelpClick) {
                        Icon(Icons.Default.Help, contentDescription = "Help", tint = Color.Black)
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.Black)
                    }
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            var searchText by remember { mutableStateOf("") }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF1F3F7))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        decorationBox = { innerTextField ->
                            if (searchText.isEmpty()) {
                                Text(
                                    text = "搜索或输入网址",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                    // Enter icon
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_forward24px),
                        contentDescription = "Go",
                        tint = Color.Gray,
                        modifier = Modifier.clickable { onSearch(searchText) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Icon Grid
            val sites = listOf(
                SiteInfo("Facebook", "https://www.facebook.com", backgroundColor = Color(0xFF1877F2)),
                SiteInfo("Instagram", "https://www.instagram.com", backgroundColor = Color(0xFFE4405F)),
                SiteInfo("Vimeo", "https://vimeo.com", backgroundColor = Color(0xFF1AB7EA)),
                SiteInfo("Dailymotion", "https://www.dailymotion.com", backgroundColor = Color(0xFF0066DC)),
                SiteInfo("Twitter", "https://twitter.com", backgroundColor = Color(0xFF1DA1F2)),
                SiteInfo("铃声", "https://www.zedge.net", backgroundColor = Color(0xFFBC3FD2)), // Mock URL
                SiteInfo("WhatsApp", "https://www.whatsapp.com", backgroundColor = Color(0xFF25D366))
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sites) { site ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSiteClick(site.url) }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(site.backgroundColor),
                            contentAlignment = Alignment.Center
                        ) {
                            // Fallback to first letter if no icon
                            Text(
                                text = site.name.first().toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = site.name,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Download Method Button
            Button(
                onClick = onDownloadMethodClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Icon(
                    Icons.Default.QuestionMark,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "下载方法", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
