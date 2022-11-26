buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    dependencies {
        classpath(Dep.androidGradlePlugin)
        classpath(Dep.Kotlin.gradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://naver.jfrog.io/artifactory/maven/")
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}