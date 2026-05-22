package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppViewModel
import com.example.ui.theme.DangerRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: AppViewModel,
    onNavigateBack: () -> Unit,
    onTestCamera: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    
    val selectedOption by viewModel.cameraMode.collectAsState()
    val enableAudio by viewModel.enableAudio.collectAsState()
    val videoUri by viewModel.videoUri.collectAsState()

    val videoPickerLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        if (uri != null) {
            viewModel.setVideoUri(uri)
        }
    }

    val options = listOf(
        "1. Disable,use real camera device",
        "2. Use Local Video",
        "3. Use Network Video Stream",
        "4. Use Local Picture"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Virtual Camera Setting", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(top = 8.dp)
        ) {
            Text(
                "Protect Your Camera Privacy.",
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            // Dropdown menu manually implemented to look like Android Spinner block
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { expanded = !expanded }
                    .padding(16.dp)
            ) {
                Text(
                    text = selectedOption,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            
            if (expanded) {
                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF00796B))) {
                    options.forEach { option ->
                        Text(
                            text = option,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setCameraMode(option)
                                    expanded = false
                                }
                                .padding(16.dp),
                            fontSize = 18.sp
                        )
                        HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                    }
                }
            }

            if (selectedOption == "2. Use Local Video" && !expanded) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Tips: This will replace the camera device with local video.",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = { videoPickerLauncher.launch("video/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Choose Local Video", color = Color.White, fontSize = 16.sp)
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.White)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    OutlinedTextField(
                        value = videoUri?.toString() ?: "",
                        onValueChange = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = Color.Gray,
                            disabledBorderColor = Color.LightGray
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        "(Video Size should match target app require,\nOtherwise the display will be abnormal or black screen.)",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Enable Audio:", fontSize = 18.sp, color = Color.Black)
                        Switch(
                            checked = enableAudio,
                            onCheckedChange = { viewModel.setEnableAudio(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary, checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha=0.5f))
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Button(
                        onClick = onTestCamera,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    ) {
                        Text("Test Virtual Camera", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = {
                            android.widget.Toast.makeText(context, "Settings Saved", android.widget.Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    ) {
                        Text("Save", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
// primaryDark doesn't exist natively, fallback used inline.
