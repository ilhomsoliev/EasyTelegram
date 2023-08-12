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
include(":core")
include(":shared")
include(":feature:home")
include(":data:auth")
include(":data:chat")
include(":data:tgcore")
include(":data:shared")
include(":data:profile")
