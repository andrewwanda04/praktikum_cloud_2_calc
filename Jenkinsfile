// Jenkinsfile: Pipeline CI/CD untuk Aplikasi Android
pipeline {
    // Menggunakan image Docker Android Build Agent
    agent {
        docker {
            image 'my-android-build-image:latest' // Harus sesuai dengan nama image di docker-compose.yml
            args '-u root' // Memastikan izin eksekusi
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
                
                // Memberi izin eksekusi pada Gradle Wrapper
                sh 'chmod +x ./gradlew'

                // Menjalankan perintah build Android
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