// Jenkinsfile: CI/CD Android Calculator (Windows + Docker Safe)
pipeline {
    agent any

    environment {
        GITHUB_REPO = 'https://github.com/andrewwanda04/praktikum_cloud_2_calc.git'
        CREDENTIALS_ID = 'github-token'

        // Output APK
        APK_PATH = 'app/build/outputs/apk/debug/app-debug.apk'

        // Lokasi workspace di Windows dan di dalam container
        WINDOWS_WORKSPACE = "C:/ProgramData/Jenkins/.jenkins/workspace/praktikum_cloud_2_calc"
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

        stage('2. Build & Test Inside Docker') {
            steps {
                echo '============================'
                echo 'MENJALANKAN BUILD DI DOCKER...'
                echo '============================'

                // Jalankan Gradle di dalam container Docker
                bat """
                    docker run --rm ^
                        -u 0 ^
                        -v "${WINDOWS_WORKSPACE}":"${DOCKER_MOUNT_PATH}" ^
                        -w "${DOCKER_MOUNT_PATH}" ^
                        my-android-build-image:latest bash -c ^
                        "echo 'Running in container at \$(pwd)' && chmod +x ./gradlew && dos2unix ./gradlew && ./gradlew testDebugUnitTest assembleDebug"
                """
            }
        }

        stage('3. Archive APK') {
            steps {
                echo '============================'
                echo 'MENYIMPAN ARTIFACTS (APK)...'
                echo '============================'
                archiveArtifacts artifacts: env.APK_PATH, onlyIfSuccessful: true
            }
        }
    }

    post {
        always {
            echo 'Pipeline selesai berjalan.'
        }
        success {
            echo '✅ Build Android sukses! APK dapat diunduh dari Artifacts Jenkins.'
        }
        failure {
            echo '❌ Build gagal. Cek log untuk detail error Gradle.'
        }
    }
}
