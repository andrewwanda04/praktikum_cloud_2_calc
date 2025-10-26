# Dockerfile: Untuk Build Aplikasi Android
# Menggunakan OpenJDK 17 sebagai base image (Android SDK butuh Java)
FROM openjdk:17-jdk-slim

# Instal tools dasar (git, unzip, dll)
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    wget \
    libc6-i386 \
    libstdc++6 \
    && rm -rf /var/lib/apt/lists/*

# Konfigurasi Environment Variables untuk Android SDK
ENV ANDROID_SDK_ROOT /opt/android-sdk

# 1. Buat folder 'cmdline-tools'
RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools

# 2. Download Command Line Tools
RUN wget -O android-cli.zip https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip

# 3. Unzip file ke folder TEMPORER 'temp', lalu pindahkan isinya (cmdline-tools) ke 'latest'
# Ini memastikan 'sdkmanager' berada di path yang benar: .../latest/bin/sdkmanager
RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools/temp \
    && unzip android-cli.zip -d $ANDROID_SDK_ROOT/cmdline-tools/temp \
    && mv $ANDROID_SDK_ROOT/cmdline-tools/temp/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest \
    && rm -rf $ANDROID_SDK_ROOT/cmdline-tools/temp \
    && rm android-cli.zip

# Tambahkan SDK Tools ke PATH
ENV PATH $PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools

# Terima semua lisensi SDK dan Instal Platform SDK terbaru dalam SATU LANGKAH
RUN yes | sdkmanager --licenses \
    && sdkmanager "platforms;android-34" "build-tools;34.0.0"

# Set working directory ke tempat Jenkins akan checkout code
WORKDIR /home/jenkins/workspace/calculator