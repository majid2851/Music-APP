# 🎵 Music Player App

A modern Android music player built with **Clean Architecture**, **MVI pattern**, and **Jetpack Compose**.

## ✨ Features

- 🎵 Play local music files from your device
- ❤️ Add songs to favorites
- 📝 Create and manage playlists
- 🔍 Search songs, artists, and albums
- 🎨 Beautiful Material Design 3 UI
- 🔔 Background playback with notifications
- 🎯 Smooth infinite scrolling

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** Clean Architecture + MVI
- **DI:** Hilt
- **Database:** Room
- **Media Player:** Media3 ExoPlayer
- **Async:** Coroutines + Flow
- **Testing:** JUnit, MockK, Turbine, Truth

## 📱 Requirements

- Android Studio Hedgehog or later
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/music-apk.git
   cd music-apk
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Open the project folder
   - Sync Gradle files

3. **Run the app**
   - Connect your device or start an emulator
   - Click Run ▶️

## 📐 Architecture

This app follows Clean Architecture with three layers:

```
Presentation (MVI)
      ↓
Domain (Business Logic)
      ↓
Data (Room + Repositories)
```

### Project Structure
```
com.musicapk/
├── data/              # Data layer (Room, Repositories)
├── domain/            # Business logic (Use Cases, Models)
├── presentation/      # UI layer (ViewModels, Screens)
├── di/                # Dependency Injection
├── service/           # Background music service
└── ui/                # Reusable UI components
```

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

## 📚 Documentation

- [ARCHITECTURE.md](ARCHITECTURE.md) - Detailed architecture guide
- [TESTING_GUIDE.md](TESTING_GUIDE.md) - Testing documentation

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 🔐 Permissions

- `READ_MEDIA_AUDIO` - Access audio files (Android 13+)
- `READ_EXTERNAL_STORAGE` - Access audio files (Android 12 and below)
- `FOREGROUND_SERVICE` - Background playback
- `POST_NOTIFICATIONS` - Show playback controls

---

Made with ❤️ and Kotlin


![photo_2025-10-31_12-09-07](https://github.com/user-attachments/assets/51102210-141e-47dd-aeb7-46631b9a62f4)
![photo_2025-10-31_12-09-08 (2)](https://github.com/user-attachments/assets/c5978782-a350-477f-8e6f-a7b9db27b762)
![photo_2025-10-31_12-09-08 (3)](https://github.com/user-attachments/assets/044f18c4-c8c8-4915-b3f6-3cbf6ed3b947)
![photo_2025-10-31_12-09-08 (4)](https://github.com/user-attachments/assets/cad6a40f-3465-4c51-8ebf-51f0df4ee6d3)
![photo_2025-10-31_12-09-08](https://github.com/user-attachments/assets/104d4d7e-e3db-4d90-b124-1e9537f3b8d8)
![photo_2025-10-31_12-09-09 (2)](https://github.com/user-attachments/assets/4c5bdf2f-1cd7-49b5-aa63-bb64973618b0)
![photo_2025-10-31_12-09-09](https://github.com/user-attachments/assets/7839021c-fb4b-4397-89a9-9fc85d1013e8)









