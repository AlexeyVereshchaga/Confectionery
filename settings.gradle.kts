pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Confectionery"
include(
    ":app",
    ":core",
    ":core-network",
    ":core-db",
    ":features:auth:domain",
    ":features:auth:domainimpl",
    ":features:auth:data",
    ":features:auth:presentation",
    ":features:products:domain",
    ":features:products:domainimpl",
    ":features:products:data",
    ":features:products:presentation"
)
