package com.ilhomsoliev.shared.common.dropdown_menu

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.shared.shared.icons.CleanUpIcon
import com.ilhomsoliev.shared.shared.icons.SearchIcon
import com.ilhomsoliev.shared.shared.icons.TrashIcon

@Composable
fun Chat3DotsDropdownMenu(
    isMenuOpen: Boolean,
    onIsMenuOpenChange: () -> Unit,
    onCleanHistoryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onDeleteChatClick: () -> Unit,
) {
    DropdownMenu(expanded = isMenuOpen, onDismissRequest = {
        onIsMenuOpenChange()
    }, offset = DpOffset(x = 16.dp, 0.dp)) {
        DropdownMenuItem(leadingIcon = {
            Icon(
                imageVector = CleanUpIcon,
                contentDescription = null
            )
        }, onClick = {
            onCleanHistoryClick()
        }, text = { Text("Очистить историю") })
        DropdownMenuItem(leadingIcon = {
            Icon(
                imageVector = SearchIcon,
                contentDescription = null
            )
        }, onClick = {
            onSearchClick()
        }, text = { Text("Поиск") })
        DropdownMenuItem(leadingIcon = {
            Icon(
                imageVector = TrashIcon,
                contentDescription = null,
                tint = Color(0xFFFA5A5A)
            )
        }, onClick = {
            onDeleteChatClick()
        }, text = { Text("Удалить чат", color = Color(0xFFFA5A5A)) })
    }
}