// Jenkinsfile: Pipeline CI/CD Komprehensif untuk Aplikasi Android
pipeline {
    agent {
        docker {
            image 'my-android-build-image:latest' // Image builder Android
            args '-u root'
            // Kunci untuk Windows: Gunakan ulang node Jenkins, yang membantu resolusi path.
            reuseNode true 
            // PERBAIKAN KRUSIAL UNTUK WINDOWS:
            // Memaksa Jenkins menggunakan path format Linux di dalam container Docker.
            // (Asumsi 'praktikum_cloud_2_calc' adalah nama folder workspace di Docker Home)
            customWorkspace '/home/jenkins/workspace/praktikum_cloud_2_calc'
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
        stage('1. Checkout Code') {
            steps {
                echo 'Mengambil kode dari GitHub...'
                git branch: 'main',
                    credentialsId: env.CREDENTIALS_ID,
                    url: env.GITHUB_REPO
            }
        }
        
        stage('2. Run Unit Tests') {
            steps {
                echo '============================'
                echo 'MENJALANKAN UNIT TESTS...'
                echo '============================'
                // Memberi izin eksekusi pada Gradle Wrapper
                sh 'chmod +x ./gradlew'
                // Menjalankan unit tests
                sh './gradlew testDebugUnitTest'
            }
        }

        stage('3. Assemble Debug APK') {
            steps {
                echo '============================'
                echo 'MEMBANGUN (BUILD) APK...'
                echo '============================'
                
                // Menjalankan perintah build Android
                sh './gradlew assembleDebug'
            }
        }

        stage('4. Archive APK') {
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
        always {
            // Memberikan feedback meskipun build gagal atau sukses
            echo 'Pipeline selesai berjalan.'
        }
        success {
            echo '✅ Build Android sukses! APK dapat didownload dari Artifacts.'
        }
        failure {
            echo '❌ Build gagal. Periksa log konsol untuk detail error Gradle.'
        }
    }
}
