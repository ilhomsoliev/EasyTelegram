pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "EasyTelegram"
include(":app", ":libtd")
include(":feature:login")
include(":feature:chat")
include(":data")
include(":core")
include(":shared")
include(":feature:home")
