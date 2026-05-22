package com.example.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppInfo(
    val name: String,
    val packageName: String
)

class AppViewModel : ViewModel() {
    private val _clonedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val clonedApps: StateFlow<List<AppInfo>> = _clonedApps.asStateFlow()

    private val _cameraMode = MutableStateFlow("2. Use Local Video")
    val cameraMode: StateFlow<String> = _cameraMode.asStateFlow()

    private val _enableAudio = MutableStateFlow(true)
    val enableAudio: StateFlow<Boolean> = _enableAudio.asStateFlow()

    private val _videoUri = MutableStateFlow<Uri?>(null)
    val videoUri: StateFlow<Uri?> = _videoUri.asStateFlow()

    fun cloneApp(appInfo: AppInfo) {
        val currentList = _clonedApps.value
        if (!currentList.any { it.packageName == appInfo.packageName }) {
            _clonedApps.value = currentList + appInfo
        }
    }

    fun setCameraMode(mode: String) {
        _cameraMode.value = mode
    }

    fun setEnableAudio(enable: Boolean) {
        _enableAudio.value = enable
    }

    fun setVideoUri(uri: Uri?) {
        _videoUri.value = uri
    }
}
