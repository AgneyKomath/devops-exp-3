pipeline {
  agent any
  environment {
    APP_NAME = "myapp"
  }
  tools {
    jdk 'jdk11'       // must match name in Global Tool Config
    maven 'Maven3'    // must match name in Global Tool Config
  }
  parameters {
    booleanParam(name: 'RUN_DEPLOY', defaultValue: false, description: 'If true, pipeline will try to deploy after tests')
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build') {
      steps {
        script {
          if (isUnix()) {
            sh 'mvn -B -DskipTests=false clean package'
          } else {
            bat 'mvn -B -DskipTests=false clean package'
          }
        }
      }
      post {
        success {
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
      }
    }

    stage('Test') {
      steps {
        script {
          if (isUnix()) {
            sh 'mvn -B test'
          } else {
            bat 'mvn -B test'
          }
        }
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }

    stage('Prepare for Deploy (Manual)') {
      when { expression { return params.RUN_DEPLOY } }
      steps {
        input message: "Approve deployment to demo environment?", ok: "Deploy"
      }
    }

    stage('Deploy') {
      when { expression { return params.RUN_DEPLOY } }
      steps {
        script {
          // Option A: local deploy script
          if (isUnix()) {
            sh './deploy.sh'
          } else {
            bat 'deploy.bat'
          }
        }
      }
    }
  }

  post {
    always {
      echo "Build finished. Status: ${currentBuild.currentResult}"
    }
    success {
      echo 'Pipeline succeeded!'
    }
    failure {
      echo 'Pipeline failed :('
    }
  }
}