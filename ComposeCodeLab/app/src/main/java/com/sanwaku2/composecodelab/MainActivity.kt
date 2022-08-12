package com.sanwaku2.composecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanwaku2.composecodelab.ui.theme.ComposeCodeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodeLabTheme {
                MyApp()
            }
        }
    }
}

// コンポーズ可能な関数は任意の順序で頻繁に実行される可能性があるため、コードが実行される順序、
// または関数が再コンポーズされる回数に依存しないようにする
@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
//    Surface(color = MaterialTheme.colors.background) {
//        Greeting(name = "Android")
//    }
}

@Composable
fun Greeting(name: String) {
    // StateとMutableStateは、何らかの値を保持し、その値が変化するたびにUIの更新をトリガーするインターフェース
    // 再コンポジションの前後で状態を保持するにはrememberを使用して記憶する
    // 同じコンポーザブルを複数の部分から別々に呼び出すと異なるUI要素が作成され、状態もそれぞれ
    // 別となる。内部状態はクラス内のプライベート変数と見なすことができる
    val expanded = remember { mutableStateOf(false) }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                // 修飾子は繋げることができる
                // weightを使うと要素で利用可能なスペースが全て使われるようになり柔軟性が高くなる
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(text = if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeCodeLabTheme {
        MyApp()
    }
}