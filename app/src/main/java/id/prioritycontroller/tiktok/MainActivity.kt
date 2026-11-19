package id.prioritycontroller.tiktok

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF090E18)
private val Panel = Color(0xFF141E2E)
private val Accent = Color(0xFF60A5FA)
private val Muted = Color(0xFF9AA9BE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PriorityApp() }
    }
}

@Composable
private fun PriorityApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var ram by remember { mutableStateOf(readRam(context)) }
    var network by remember { mutableStateOf(readNetwork(context)) }

    MaterialTheme(colorScheme = darkColorScheme(
        primary = Accent, background = Bg, surface = Panel,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Surface(Modifier.fillMaxSize(), color = Bg) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFF1C3555)) {
                        Icon(Icons.Default.Bolt, null, tint = Accent,
                            modifier = Modifier.padding(13.dp).size(30.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("TikTok Priority", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text("CONTROLLER • DARK PREMIUM", color = Muted, fontSize = 10.sp)
                    }
                    Text("TANPA ROOT", color = Color(0xFF6EE7B7), fontSize = 10.sp)
                }

                Card(colors = CardDefaults.cardColors(containerColor = Panel),
                    shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("PROFIL PERANGKAT", color = Muted, fontSize = 11.sp)
                        Text("POCO M5s • MIUI 14", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("RAM 6 GB • Penyimpanan 256 GB", color = Muted, fontSize = 13.sp)
                        Text("Android 10+  |  6 akun  |  Wi-Fi", color = Accent, fontSize = 12.sp)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricCard("Jaringan", network, Icons.Default.Wifi, Modifier.weight(1f))
                    MetricCard("RAM tersedia", ram, Icons.Default.Memory, Modifier.weight(1f))
                }

                Text("KONTROL PRIORITAS", color = Muted, fontSize = 11.sp,
                    fontWeight = FontWeight.Bold)
                ActionCard("Pengaturan aplikasi TikTok", "Baterai, izin, dan penggunaan data",
                    Icons.Default.AppSettingsAlt) { openTikTokDetails(context) }
                ActionCard("Optimasi baterai", "Buka pengaturan pengecualian baterai",
                    Icons.Default.BatteryFull) { safeStart(context,
                    Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)) }
                ActionCard("Pengaturan Wi-Fi", "Pilih 5 GHz jika sinyal kuat",
                    Icons.Default.Wifi) { safeStart(context, Intent(Settings.ACTION_WIFI_SETTINGS)) }
                ActionCard("Pengaturan jaringan", "Periksa koneksi dan penggunaan data",
                    Icons.Default.SettingsEthernet) { safeStart(context,
                    Intent(Settings.ACTION_DATA_USAGE_SETTINGS)) }
                ActionCard("Pengaturan aplikasi", "Kelola aplikasi dan autostart MIUI",
                    Icons.Default.Tune) { safeStart(context,
                    Intent(Settings.ACTION_APPLICATION_SETTINGS)) }

                Card(colors = CardDefaults.cardColors(containerColor = Panel),
                    shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("PROFIL KERJA 6 AKUN", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Gunakan satu aplikasi TikTok dan berpindah akun melalui fitur pengalih akun resmi. Hindari banyak aplikasi kloning sekaligus pada RAM 6 GB.",
                            color = Muted, fontSize = 13.sp)
                        Text("Checklist: TikTok tanpa batas baterai jika tersedia • data latar belakang diizinkan • kunci di Recent Apps jika tersedia • upload satu per satu melalui Wi-Fi stabil.",
                            color = Color.White, fontSize = 12.sp, lineHeight = 19.sp)
                    }
                }

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF302719)),
                    shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("BATASAN ANDROID", color = Color(0xFFFBBF24), fontWeight = FontWeight.Bold)
                        Text("Aplikasi ini tidak memaksa frekuensi CPU/GPU, tidak mengubah prioritas bandwidth, dan tidak dapat mengaktifkan autostart MIUI secara otomatis. QoS harus diatur dari router jika didukung.",
                            color = Color(0xFFE8D9B8), fontSize = 12.sp)
                    }
                }

                OutlinedButton(onClick = {
                    ram = readRam(context)
                    network = readNetwork(context)
                }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Perbarui status")
                }
                Text("TikTok Priority Controller v1.0.0", color = Muted, fontSize = 11.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 12.dp))
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, null, tint = Accent)
            Text(title, color = Muted, fontSize = 11.sp)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun ActionCard(title: String, subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(onClick = onClick, colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(16.dp)) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Accent, modifier = Modifier.size(25.dp))
            Spacer(Modifier.width(13.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(subtitle, color = Muted, fontSize = 11.sp)
            }
            Icon(Icons.Default.ChevronRight, null, tint = Muted)
        }
    }
}

private fun readRam(context: Context): String = try {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val info = ActivityManager.MemoryInfo()
    manager.getMemoryInfo(info)
    String.format("%.1f GB", info.availMem / (1024.0 * 1024.0 * 1024.0))
} catch (_: Exception) { "Tidak tersedia" }

private fun readNetwork(context: Context): String {
    return try {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val net = cm.activeNetwork
        if (net == null) {
            "Offline"
        } else {
            val caps = cm.getNetworkCapabilities(net)
            when {
                caps == null -> "Tidak diketahui"
                caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi aktif"
                caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Seluler"
                else -> "Terhubung"
            }
        }
    } catch (_: Exception) { "Tidak tersedia" }
}

private fun openTikTokDetails(context: Context) {
    safeStart(context, Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = android.net.Uri.parse("package:com.zhiliaoapp.musically")
    })
}

private fun safeStart(context: Context, intent: Intent) {
    try {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (_: Exception) {
        try { context.startActivity(Intent(Settings.ACTION_SETTINGS)) } catch (_: Exception) { }
    }
}
