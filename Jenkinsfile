// Jenkinsfile: Pipeline CI/CD Komprehensif untuk Aplikasi Android
pipeline {
    // KEMBALIKAN KE agent any KARENA agent docker BERMASALAH DI WINDOWS
    agent any

    environment {
        // GANTI INI DENGAN LINK REPO GITHUB ANDROID KALKULATOR KAMU
        GITHUB_REPO = 'https://github.com/andrewwanda04/praktikum_cloud_2_calc.git'
        CREDENTIALS_ID = 'github-token' // ID Credential Github di Jenkins
        
        // Output APK yang akan disimpan sebagai Artifact
        APK_PATH = 'app/build/outputs/apk/debug/app-debug.apk' 

        // VARIABEL KRUSIAL UNTUK WINDOWS: Menggunakan path Jenkins workspace saat ini
        // (Berada di C:/ProgramData/Jenkins/.jenkins/workspace/praktikum_cloud_2_calc)
        WINDOWS_WORKSPACE = "C:/ProgramData/Jenkins/.jenkins/workspace/praktikum_cloud_2_calc"

        // Path di dalam container Docker (Linux format)
        DOCKER_MOUNT_PATH = "/home/jenkins/workspace/praktikum_cloud_2_calc"
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
        
        // MENGGANTI STAGE BUILD DAN TEST MENJADI DOCKER MANUAL EXECUTION
        stage('2. Build & Test Inside Docker (Manual)') {
            steps {
                echo '============================'
                echo 'MENJALANKAN DOCKER MANUAL...'
                echo '============================'
                
                // Gunakan bat (Windows) untuk menjalankan docker run
                // SOLUSI TERBAIK UNTUK WINDOWS: Gunakan sed/mv untuk memperbaiki line ending
                // dan menghindari masalah permission/rename dari chmod dan sed -i
                bat """
                    docker run --rm ^
                        -u 0 ^
                        -v "${WINDOWS_WORKSPACE}":"${DOCKER_MOUNT_PATH}" ^
                        -w "${DOCKER_MOUNT_PATH}" ^
                        my-android-build-image:latest bash -c ^
                        "echo 'Running in container at \$(pwd)' && echo 'Fixing line endings and bypassing permission lock...' && sed 's/\\r//g' ./gradlew > gradlew.tmp && mv gradlew.tmp ./gradlew && ./gradlew testDebugUnitTest assembleDebug"
                """
            }
        }

        stage('3. Archive APK') {
            steps {
                echo '============================'
                echo 'MENYIMPAN ARTIFACTS (APK)...'
                echo '============================'
                
                // Menyimpan file APK hasil build (Build ada di workspace Jenkins saat ini)
                archiveArtifacts artifacts: env.APK_PATH, onlyIfSuccessful: true
            }
        }
    }

    post {
        always {
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
