# Patient App 🏥

Aplikasi Android Kotlin untuk login dan menampilkan data pasien menggunakan REST API.

## Fitur
- Login dengan email & password via API
- Menyimpan token autentikasi (SharedPreferences)
- Menampilkan daftar pasien dengan RecyclerView
- Header Authorization Bearer Token
- Pull-to-refresh
- Loading & error state
- Logout

## Tech Stack
- Kotlin
- Retrofit2 + OkHttp3
- ViewModel + LiveData
- ViewBinding
- Material Design 3

## API
- **Login:** `POST https://api.pahrul.my.id/api/login`
- **Data Pasien:** `GET https://api.pahrul.my.id/api/pasien` (Bearer Token)

## Screenshots
> Tambahkan screenshot di folder `screenshots/`

## Setup
1. Clone repo ini
2. Buka di Android Studio
3. Sync Gradle
4. Run di emulator atau device (min SDK 24)
