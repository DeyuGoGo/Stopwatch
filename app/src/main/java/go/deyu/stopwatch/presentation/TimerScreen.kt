package go.deyu.stopwatch.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 顯示當前時間
        Text(text = timeLeft, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // 快速啟動按鈕
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf(30, 45, 60, 90, 120, 180, 300).forEach { timeInSeconds ->
                item{
                    Spacer(modifier = Modifier.width(16.dp))
                    QuickStartButton(timeInSeconds) {
                        viewModel.setTimer(timeInSeconds)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 控制按鈕
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(onClick = { viewModel.toggleTimer() }) {
                Text(text = if (isRunning) "暫停" else "開始")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.resetTimer() }) {
                Text(text = "重置")
            }
        }
    }
}

@Composable
fun QuickStartButton(timeInSeconds: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = when (timeInSeconds) {
            30 -> "30秒"
            45 -> "45秒"
            60 -> "1分"
            90 -> "1分半"
            120 -> "2分"
            180 -> "3分"
            300 -> "5分"
            else -> "${timeInSeconds}秒"
        })
    }
}
