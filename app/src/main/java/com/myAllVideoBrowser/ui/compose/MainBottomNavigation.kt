package com.myAllVideoBrowser.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myAllVideoBrowser.R

@Composable
fun MainBottomNavigation(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val items = listOf(
            Triple("窗口", R.drawable.tab_group_24px, 0),
            Triple("下载中", R.drawable.downloading_24px, 1),
            Triple("已下载", R.drawable.video_library_24px, 2)
        )

        items.forEach { (label, iconRes, index) ->
            val isSelected = selectedIndex == index
            val color = if (isSelected) Color(0xFF007AFF) else Color.Gray

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onTabSelected(index) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = color,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
