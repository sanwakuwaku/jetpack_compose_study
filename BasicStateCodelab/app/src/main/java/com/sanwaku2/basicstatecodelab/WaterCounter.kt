package com.sanwaku2.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // State型とMutableState型でComposeが状態を監視できる
        // valueが変更された時にそれを読み取る関数への再コンポーズがトリガーされる

        // rememberを使用して再コンポーズ後も変数を保持できる
        // オブジェクトないのプライベートvalプロパティと同じように、コンポジション内に単一のオブジェクトを
        // 保存するメカニズムと考えることができる

        // val count: MutableState<Int> = remember { mutableStateOf(0) }

        // デリゲートプロパティ (by) を使用して簡素化できる
        // valueプロパティを明示的に参照せずに間接的にcountを読み取ることができる
        var count by remember { mutableStateOf(0) }

        if (count > 0) {
            // count = 0 となった場合はrememberが呼び出されないのでshowTaskは破棄される
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                WellnessTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    onClose = { showTask = false }
                )
            }
            Text(text = "You've had ${count} glasses.")
        }

        Row(Modifier.padding(top = 8.dp)) {
            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(text = "Add one")
            }
            Button(
                onClick = { count = 0 },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Clear water count")
            }
        }
    }
}