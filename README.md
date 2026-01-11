# MyMiaw - Cat Catalog App

**MyMiaw** adalah aplikasi katalog kucing yang memungkinkan pengguna untuk menjelajahi dan mempelajari berbagai ras kucing dari seluruh dunia.

---

## Fitur

- **Autentikasi** - Login dengan Email/Password atau Google Sign-In
- **Katalog Kucing** - Menampilkan 20+ ras kucing dari TheCatAPI
- **Detail Kucing** - Informasi lengkap: asal, usia, temperamen, deskripsi
- **UI Modern** - Material Design dengan tema konsisten
- **Navigation Drawer** - Menu samping untuk navigasi
- **Halaman About** - Informasi aplikasi dan developer

---

## Tech Stack

| Kategori | Teknologi |
|----------|-----------|
| Bahasa | Kotlin |
| Min SDK | API 33 (Android 13) |
| Target SDK | API 36 |
| UI | Material Design 3, ConstraintLayout |
| Navigation | Jetpack Navigation Component |
| Image Loading | Glide 4.16.0 |
| Networking | Retrofit 2.9.0 + Gson |
| Authentication | Firebase Auth + Credential Manager |
| Database | Firebase Firestore |
| API | [TheCatAPI](https://thecatapi.com/) |

---

## Struktur Project

```
app/src/main/java/com/pab/mymiaw/
├── adapter/
│   └── CatAdapter.kt           # RecyclerView adapter
├── data/
│   ├── api/
│   │   ├── CatApiService.kt    # Retrofit interface
│   │   └── RetrofitClient.kt   # Retrofit singleton
│   └── model/
│       └── Cat.kt              # Data class
├── AboutFragment.kt
├── DetailFragment.kt
├── HomeFragment.kt
├── MainActivity.kt
├── SignInFragment.kt
└── SignUpFragment.kt
```

---

## Setup

### Prerequisites
- Android Studio Hedgehog atau lebih baru
- JDK 11+
- Akun Firebase

### Langkah-langkah

1. Clone repository
   ```bash
   git clone https://github.com/Andrian206/My-Miaw-Android.git
   cd My-Miaw-Android
   ```

2. Setup Firebase
   - Buat project di [Firebase Console](https://console.firebase.google.com/)
   - Tambahkan aplikasi Android dengan package name `com.pab.mymiaw`
   - Download `google-services.json` dan letakkan di folder `app/`
   - Enable Authentication (Email/Password dan Google)
   - Enable Firestore Database

3. Konfigurasi Google Sign-In
   - Tambahkan SHA-1 fingerprint ke Firebase Console
   - Salin Web Client ID dari Firebase Auth
   - Update di `SignInFragment.kt`:
     ```kotlin
     private const val WEB_CLIENT_ID = "YOUR_WEB_CLIENT_ID.apps.googleusercontent.com"
     ```

4. Build & Run
   ```bash
   ./gradlew assembleDebug
   ```

---

## API

Aplikasi menggunakan [TheCatAPI](https://thecatapi.com/).

- Base URL: `https://api.thecatapi.com/v1/`
- Endpoint: `GET /breeds`

---

## Dependencies

```kotlin
// Core
implementation("androidx.core:core-splashscreen:1.0.1")
implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")

// Image Loading
implementation("com.github.bumptech.glide:glide:4.16.0")

// Firebase
implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")

// Networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Google Sign In
implementation("androidx.credentials:credentials:1.3.0")
implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
```

---

## Design

- Font: Inter (Regular, SemiBold, Bold)
- Color Palette:
  - Primary Background: `#FFF4F1E9`
  - Primary Yellow: `#FFE9B649`
  - Secondary Yellow: `#FFA06D00`

---

## Developer

Andrian206

---

## License

MIT License