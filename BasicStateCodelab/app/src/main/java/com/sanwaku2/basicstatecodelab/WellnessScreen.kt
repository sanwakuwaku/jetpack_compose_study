package com.sanwaku2.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column {
        StatefulCounter(modifier)

        // ArrayListやmutableListOfは変更をComposeに通知しないのでリストの変更操作には使えない
        // 監視可能なMutableListのインスタンスを作成するにはtoMutableStateList()を使用する
        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTaskList(
            list = list,
            onCloseTask = { task -> list.remove(task) }
        )
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }