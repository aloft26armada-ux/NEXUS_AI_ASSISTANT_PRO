# Nexus AI

An advanced Android application utilizing high-performance native C++ hardware execution layers to run local LLM (Llama) and Automatic Speech Recognition (Whisper) models completely offline.

## 🚀 Key Features
* **Local LLM Inference:** Powered by custom `llama.cpp` JNI integrations for private on-device intelligence.
* **Offline Voice Processing:** Built-in `whisper.cpp` infrastructure for real-time speech-to-text.
* **Hardware-Aware Performance:** Classifies device memory footprints to dynamically scale processing constraints.
* **Secure Storage:** Biometric-backed database protection paired with an encrypted data store.
* **Overlay Architecture:** Persistent background management with an interactive system overlay window.

---

## 📂 Repository Blueprint
```text
.
├── .github/workflows/          # GitHub CI/CD automation pipelines
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── cpp/            # Native JNI bridges (Llama/Whisper headers & logic)
│   │   │   ├── java/com/nexus/ # Clean architecture application layers
│   │   │   │   └── ai/
│   │   │       ├── core/       # Security, DB, AI Engines, DI Modules
│   │   │       ├── features/   # Feature viewmodels and background tasks
│   │   │       ├── floating/   # Background overlay window services
│   │   │       └── ui/         # Glassmorphic Jetpack Compose frontend
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── CMakeLists.txt          # Native compilation configurations
├── gradle/                     # Project build orchestration system
├── build.gradle.kts
└── README.md
