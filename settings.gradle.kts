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
    ":feature-auth-domain",
    ":feature-auth-domainimpl",
    ":feature-auth-data",
    ":feature-auth-presentation",
    ":feature-products-domain",
    ":feature-products-domainimpl",
    ":feature-products-data",
    ":feature-products-presentation"
)
