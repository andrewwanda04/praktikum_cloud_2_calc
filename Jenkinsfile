// Jenkinsfile: Pipeline CI/CD untuk Aplikasi Android
pipeline {
    // Kembali ke agent Docker di level pipeline, ini lebih efisien
    agent {
        docker {
            image 'my-android-build-image:latest' // Harus sesuai dengan nama image di docker-compose.yml
            args '-u root'
            // Gunakan path Linux di WORKDIR yang sudah didefinisikan di Dockerfile
            // WORKDIR di Dockerfile adalah: /home/jenkins/workspace/calculator
            // Jenkins akan mengurus mapping volume C:/ProgramData ke path Linux di container.
        }
    }

    environment {
        // GANTI INI DENGAN LINK REPO GITHUB ANDROID KALKULATOR KAMU
        GITHUB_REPO = 'https://github.com/andrewwanda04/praktikum_cloud_2_calc.git'
        CREDENTIALS_ID = 'github-token' // ID Credential Github di Jenkins
        
        // Output APK yang akan disimpan sebagai Artifact
        APK_PATH = 'app/build/outputs/apk/debug/app-debug.apk' 
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Mengambil kode dari GitHub
                git branch: 'main',
                    credentialsId: env.CREDENTIALS_ID,
                    url: env.GITHUB_REPO
            }
        }

        stage('Build Android APK') {
            steps {
                echo '============================'
                echo 'MULAI BUILD DENGAN GRADLE...'
                echo '============================'
                
                // Gunakan 'sh' (Linux shell) untuk menjalankan perintah di dalam container Docker
                // Kita harus pastikan menjalankan ini di path kerja Linux: /home/jenkins/workspace/calculator
                // Karena kita menggunakan agent di level pipeline, PWD (present working directory) sudah disiapkan
                
                // 1. Memberi izin eksekusi pada Gradle Wrapper
                sh 'chmod +x ./gradlew'

                // 2. Menjalankan perintah build Android
                sh './gradlew assembleDebug'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo '============================'
                echo 'MENYIMPAN ARTIFACTS (APK)...'
                echo '============================'
                
                // Menyimpan file APK hasil build
                archiveArtifacts artifacts: env.APK_PATH, onlyIfSuccessful: true
            }
        }
    }

    post {
        success {
            echo '✅ Build Android sukses! APK dapat didownload dari Artifacts.'
        }
        failure {
            echo '❌ Build gagal, cek log Jenkins console output.'
        }
    }
}
