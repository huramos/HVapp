package com.example.hvapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.os.Build
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import com.example.hvapp.Fonts.customFontFamily

@Composable
fun VideosScreen(navController: NavHostController, activity: FragmentActivity) {
    val greenMossColor = Color(red = 85, green = 107, blue = 47)
    val titleStyle = TextStyle(
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = customFontFamily,
        color = greenMossColor
    )

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Solicitar permisos en tiempo de ejecución
        if (!allPermissionsGranted(activity)) {
            requestPermissions(activity)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pantalla de Videos",
            style = titleStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Vista previa del primer video
        AndroidView(factory = { context ->
            WebView(context).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WebView.setWebContentsDebuggingEnabled(true)
                }
                settings.apply {
                    javaScriptEnabled = true
                    mediaPlaybackRequiresUserGesture = false // Permitir reproducción automática
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }
                clearCache(true)
                clearHistory()
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        Log.d("WebView", "Started loading: $url")
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isLoading = false
                        Log.d("WebView", "Finished loading: $url")
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        isLoading = false
                        Log.e("WebView", "Error loading: ${error?.description}")
                        Toast.makeText(context, "Error loading page: ${error?.description}", Toast.LENGTH_SHORT).show()
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    // Sobreescribe los métodos para evitar el acceso a la cámara
                    override fun onPermissionRequest(request: android.webkit.PermissionRequest?) {
                        // Rechaza todas las solicitudes de permisos
                        request?.deny()
                    }
                }
                loadUrl("https://www.youtube.com/embed/q6WZM7Mp-yk")
            }
        }, modifier = Modifier.height(250.dp).fillMaxWidth())

        Spacer(modifier = Modifier.height(32.dp))

        // Vista previa del segundo video
        AndroidView(factory = { context ->
            WebView(context).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WebView.setWebContentsDebuggingEnabled(true)
                }
                settings.apply {
                    javaScriptEnabled = true
                    mediaPlaybackRequiresUserGesture = false // Permitir reproducción automática
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }
                clearCache(true)
                clearHistory()
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        Log.d("WebView", "Started loading: $url")
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isLoading = false
                        Log.d("WebView", "Finished loading: $url")
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        isLoading = false
                        Log.e("WebView", "Error loading: ${error?.description}")
                        Toast.makeText(context, "Error loading page: ${error?.description}", Toast.LENGTH_SHORT).show()
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    // Sobreescribe los métodos para evitar el acceso a la cámara
                    override fun onPermissionRequest(request: android.webkit.PermissionRequest?) {
                        // Rechaza todas las solicitudes de permisos
                        request?.deny()
                    }
                }
                loadUrl("https://www.youtube.com/embed/kgeoWSjueAE")
            }
        }, modifier = Modifier.height(250.dp).fillMaxWidth())

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = greenMossColor),
            modifier = Modifier.width(250.dp).height(60.dp)
        ) {
            Text("Volver", fontSize = 18.sp, color = Color.White)
        }
    }
}

// Funciones de ayuda para permisos
fun allPermissionsGranted(activity: FragmentActivity) = arrayOf(
    Manifest.permission.MODIFY_AUDIO_SETTINGS,
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.CAMERA
).all {
    ContextCompat.checkSelfPermission(
        activity.baseContext, it
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestPermissions(activity: FragmentActivity) {
    ActivityCompat.requestPermissions(
        activity, arrayOf(
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        ), 10
    )
}
