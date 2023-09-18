package go.deyu.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import go.deyu.stopwatch.presentation.TimerScreen
import go.deyu.stopwatch.presentation.TimerViewModel
import go.deyu.stopwatch.ui.theme.StopwatchTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel :TimerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TimerScreen(viewModel  = viewModel)
                }
            }
        }
    }
}

