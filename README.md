# TikTok Priority Controller

Aplikasi utilitas Android Kotlin berbahasa Indonesia untuk membantu menyiapkan POCO/Xiaomi untuk pengelolaan akun TikTok. Tanpa root.

## Fitur
- Dashboard status koneksi Wi-Fi/seluler.
- Informasi perkiraan RAM tersedia.
- Pintasan ke detail aplikasi TikTok.
- Pintasan pengecualian optimasi baterai Android.
- Pintasan Wi-Fi dan pengaturan aplikasi.
- Panduan profil kerja enam akun.

## Batasan
Android biasa tidak mengizinkan aplikasi pihak ketiga memaksa prioritas CPU/GPU, mengatur bandwidth khusus TikTok, atau mengubah autostart MIUI secara diam-diam. Aplikasi ini membaca status yang diizinkan dan membuka halaman pengaturan resmi. QoS harus diatur di router jika didukung.

## Build APK
GitHub Actions berjalan otomatis saat ada commit ke branch `main`. Buka tab **Actions**, pilih workflow **Build Android APK**, lalu buka hasil run dan unduh artifact **TikTok-Priority-Controller-debug**. Ekstrak ZIP artifact dan instal `app-debug.apk`.

## Build
- JDK 17
- Gradle 8.9
- Android Gradle Plugin 8.7.3
- Compile SDK 35
- Min SDK 29 (Android 10)

Package: `id.prioritycontroller.tiktok`

APK debug untuk pengujian pribadi, bukan rilis Play Store.
