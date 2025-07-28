## 🎵 Android Music Player App

### 📌 Project Name

**Androidstudio_Musicplayer** is a native Android music player built using **Java/Kotlin** and XML layouts in **Android Studio**. It allows users to play locally stored audio files through a clean and responsive interface.

---
## ✨ Key Features

-  **Audio Playback**: Play MP3s and other audio files from device storage.
-  **Now Playing Screen** with track info, playback controls, and progress bar.
-  **Material Design UI** with minimal and modern look.
-  **Dark-Themed Interface** optimized for mobile view.
-  **Reads Local Storage** to fetch music files using MediaStore.

---

## 🧰 Tech Stack

- **Language:** Kotlin  
- **Framework:** Android SDK  
- **UI Components:** `MediaPlayer`, `RecyclerView`, `ListView`, `TextView`, `SeekBar`, `ImageView`, etc.  
- **Build System:** Gradle  
- **IDE:** Android Studio  
- **Minimum SDK:** (as configured in `build.gradle`)

---


## 🚀 How to Run the Project

### 📦 Prerequisites

### 1. Android Studio
- Download and install Android Studio from the official site:
    https://developer.android.com/studio
- This will also install most tools you need, including the Android SDK.

### 2. Android SDK Setup
- After installing Android Studio, the Android SDK should already be installed.
- Ensure the SDK path is correctly set in your project’s local.properties file:
- sdk.dir=C:\\Users\\your-username\\AppData\\Local\\Android\\Sdk   (for Windows)

### 3. Device to Run the App  
- Use an Android Emulator in Android Studio.
- Open Android Studio.
- Go to Tools > Device Manager.
- Create and start a virtual device (emulator).


### 💡 Steps

#### 1. Clone the Repository

```bash
git clone https://github.com/your-username/Androidstudio_Musicplayer.git
```

#### Step 2: Select "Get from Version Control"
- On the welcome screen, click on "Get from Version Control".
- If a project is already open, go to:
- File > New > Project from Version Control

  
#### 3. Sync and Build

- Wait for **Gradle sync** to complete  
- Resolve any SDK or dependency issues  

#### 4. Run the App

- Connect a device or start an emulator  
- Click the **Run ▶️** button  

---
## 🖼️ Screens Overview

### 1. 🎵 Main Screen

- Lists all available songs  
- Tap to start playback  
