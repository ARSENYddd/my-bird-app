import android.Manifest
import android.graphics.Bitmap
import android.media.MediaRecorder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import io.github.sceneview.SceneView
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val recorder = remember { MediaRecorder() }
    val sceneView = remember { SceneView(context) }

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val audioPermissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)
    val storagePermissionState = rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var isRecording by remember { mutableStateOf(false) }
    var isLongPress by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (isRecording) {
                    stopRecording(recorder)
                    isRecording = false
                } else {
                    takeScreenshot(sceneView)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onLongClick = {
                coroutineScope.launch {
                    if (!isRecording) {
                        if (cameraPermissionState.hasPermission &&
                            audioPermissionState.hasPermission &&
                            storagePermissionState.hasPermission) {
                            startRecording(recorder)
                            isRecording = true
                        } else {
                            cameraPermissionState.launchPermissionRequest()
                            audioPermissionState.launchPermissionRequest()
                            storagePermissionState.launchPermissionRequest()
                        }
                    } else {
                        stopRecording(recorder)
                        isRecording = false
                    }
                }
            }
        ) {
            Text(if (isRecording) "Stop Recording" else "Take Screenshot")
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AndroidView(factory = { sceneView })
        }
    }
}

private fun takeScreenshot(sceneView: SceneView) {
    // Implement screenshot logic here
}

private fun startRecording(recorder: MediaRecorder) {
    // Implement start recording logic here
}

private fun stopRecording(recorder: MediaRecorder) {
    // Implement stop recording logic here
}
