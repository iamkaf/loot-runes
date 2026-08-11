plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") apply false
    id("net.fabricmc.fabric-loom") apply false
    id("com.iamkaf.multiloader.root")
    id("com.iamkaf.teakit") version "0.13.2"
}

stonecutter active "26.2".let { multiloaderStonecutter.active(it) }

multiloaderArtifacts {
    horizontalMerge {
        enabled.set(true)
        versions.add("26.2")
    }
}
