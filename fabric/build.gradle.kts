import com.iamkaf.multiloader.fabric.MultiloaderFabricExtension

plugins {
    id("com.iamkaf.multiloader.fabric")
}

extensions.configure<MultiloaderFabricExtension>("multiloaderFabric") {
    commonDatagen.set(true)
}
