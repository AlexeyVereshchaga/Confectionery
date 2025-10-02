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
    ":feature-products-domain",
    ":feature-products-domainimpl",
    ":feature-products-data",
    ":feature-products-presentation"
)
