package com.sanwaku2.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanwaku2.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentブロックではコンポーズ可能な関数が呼び出されるアクティビティのレイアウトを定義する
        setContent {
            ComposeTutorialTheme { // コンポーザブルはアプリのテーマを継承する
                Conversation(message = SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

// Unitを返す@Composable関数は必ずPascalCaseで命名し、名刺でなければならない。
// 名詞の前に説明的な形容詞を付けても良い。
// 理由: コンポーザブルは宣言的な存在とみなされ、クラスの命名規則に従うため。
@Composable
fun MessageCard(msg: Message) {
    // レイアウトは修飾子(modifier)を使って実現する
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(
                    1.5.dp,
                    MaterialTheme.colors.secondary,
                    CircleShape
                ) // ComposeTutorialThemeでラップしたテーマの色を使う
        )

        Spacer(modifier = Modifier.width(8.dp))

        // remember, mutableStateOfはComposeの状態API。
        // ローカルのUI状態をメモリに格納する
        // 値が更新されると自動的に再描画される
        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            targetValue = if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )
        // 垂直に揃える
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            // Textはコンポーズ可能な関数
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant, // ComposeTutorialThemeでラップしたテーマの色を使う
                style = MaterialTheme.typography.subtitle2 // マテリアルタイポグラフィスタイル
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Surfaceで形状と高度をカスタマイズ
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2 // マテリアルタイポグラフィスタイル
                )
            }
        }
    }
}

// Previewアノテーションを使うとビルドしなくてもAndroidStudioないでプレビューできる
// 引数なしの関数でないと使えない。
// プレビューアノテーションに名前と設定を追加することができ、複数のプレビューを表示できる。
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        MessageCard(
            msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
        )
    }
}

@Composable
fun Conversation(message: List<Message>) {
    // LazyColumnは画面上に表示される要素のみレンダリングするように設計されているのでリスト表示向き
    LazyColumn {
        items(message) { message ->
            MessageCard(msg = message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(message = SampleData.conversationSample)
    }
}