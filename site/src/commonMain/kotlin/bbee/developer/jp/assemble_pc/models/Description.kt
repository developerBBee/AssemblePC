package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val DESCRIPTION_MODULE = SerializersModule {
    polymorphic(Description::class) {
        subclass(Description.CaseDescription::class)
        subclass(Description.MotherBoardDescription::class)
        subclass(Description.PSUDescription::class)
        subclass(Description.CPUDescription::class)
        subclass(Description.CoolerDescription::class)
        subclass(Description.MemoryDescription::class)
        subclass(Description.SSDDescription::class)
        subclass(Description.HDDDescription::class)
        subclass(Description.VideoCardDescription::class)
        subclass(Description.OSDescription::class)
        subclass(Description.DisplayDescription::class)
        subclass(Description.KeyboardDescription::class)
        subclass(Description.MouseDescription::class)
        subclass(Description.DVDDriveDescription::class)
        subclass(Description.BDDriveDescription::class)
        subclass(Description.SoundCardDescription::class)
        subclass(Description.SpeakerDescription::class)
        subclass(Description.FanControllerDescription::class)
        subclass(Description.FanDescription::class)
    }
}

val DESC_JSON = Json { serializersModule = DESCRIPTION_MODULE }

@Serializable
sealed class Description {
    abstract fun desc(): String

    @Serializable
    data class CaseDescription(
        val maker: String,
        val powerSpecification: String,
        val motherboards: String,
        val size: String,
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + powerSpecification.newLine() + motherboards.newLine() +
                    size.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class MotherBoardDescription(
        val maker: String = "",
        val socket: String = "",
        val motherboardType: String = "",
        val memoryType: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + socket.newLine() + motherboardType.newLine() +
                    memoryType.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class PSUDescription(
        val maker: String = "",
        val powerSpecification: String = "",
        val powerCapacity: String = "",
        val power80PlusCertified: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + powerSpecification.newLine() + powerCapacity.newLine() +
                    power80PlusCertified.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class CPUDescription(
        val maker: String = "",
        val processor: String = "",
        val socket: String = "",
        val core: String = "",
        val baseClock: String = "",
        val maxClock: String = "",
        val thread: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + socket.newLine() + core.newLine() + baseClock.newLine() +
                    maxClock.newLine() + thread.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class CoolerDescription(
        val maker: String = "",
        val socketLGA: String = "",
        val socketAM: String = "",
        val coolerType: String = "",
        val rotationSpeed: String = "",
        val airFlow: String = "",
        val size: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + socketLGA.newLine() + socketAM.newLine() +
                    coolerType.newLine() + rotationSpeed.newLine() + airFlow.newLine() +
                    size.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class MemoryDescription(
        val maker: String = "",
        val memoryCapacity: String = "",
        val setQuantity: String = "",
        val memoryInterface: String = "",
        val memoryStandard: String = "",
        val memorySpeed: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + memoryCapacity.newLine() + setQuantity.newLine() +
                    memoryInterface.newLine() + memoryStandard.newLine() + memorySpeed.newLine()
                    ).trimEnd('\n')
        }
    }

    @Serializable
    data class SSDDescription(
        val maker: String = "",
        val storageCapacity: String = "",
        val ssdStandard: String = "",
        val ssdInterface: String = "",
        val readSpeed: String = "",
        val writeSpeed: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + storageCapacity.newLine() + ssdStandard.newLine() +
                    ssdInterface.newLine() + readSpeed.newLine() + writeSpeed.newLine()
                    ).trimEnd('\n')
        }
    }

    @Serializable
    data class HDDDescription(
        val maker: String = "",
        val storageCapacity: String = "",
        val hddInterface: String = "",
        val rotationSpeed: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + storageCapacity.newLine() + hddInterface.newLine() +
                    rotationSpeed.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class VideoCardDescription(
        val maker: String = "",
        val graphicChip: String = "",
        val graphicMemory: String = "",
        val busInterface: String = "",
        val monitorConnectors: String = "",
        val powerConsumption: String = "",
        val powerConnector: String = "",
        val size: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + graphicChip.newLine() + graphicMemory.newLine() +
                    busInterface.newLine() + monitorConnectors.newLine() +
                    powerConsumption.newLine() + powerConnector.newLine() + size.newLine()
                    ).trimEnd('\n')
        }
    }

    @Serializable
    data class OSDescription(
        val vendor: String = "",
        val series: String = "",
    ) : Description() {
        override fun desc(): String {
            return (vendor.newLine() + series.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class DisplayDescription(
        val maker: String = "",
        val size: String = "",
        val aspectRatio: String = "",
        val surfaceTreatment: String = "",
        val panelType: String = "",
        val resolution: String = "",
        val responseTime: String = "",
        val refreshRate: String = "",
        val connections: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + size.newLine() + aspectRatio.newLine() +
                    surfaceTreatment.newLine() + panelType.newLine() + resolution.newLine() +
                    responseTime.newLine() + refreshRate.newLine() + connections.newLine()
                    ).trimEnd('\n')
        }
    }

    @Serializable
    data class KeyboardDescription(
        val maker: String = "",
        val connections: String = "",
        val cableLength: String = "",
        val keyLayout: String = "",
        val keySwitch: String = "",
        val keyAxis: String = "",
        val power: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + connections.newLine() + cableLength.newLine() +
                    keyLayout.newLine() + keySwitch.newLine() + keyAxis.newLine() + power.newLine()
                    ).trimEnd('\n')
        }
    }

    @Serializable
    data class MouseDescription(
        val maker: String = "",
        val mouseType: String = "",
        val connections: String = "",
        val buttons: String = "",
        val resolution: String = "",
        val weight: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + mouseType.newLine() + connections.newLine() +
                    buttons.newLine() + resolution.newLine() + weight.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class DVDDriveDescription(
        val maker: String = "",
        val installation: String = "",
        val connections: String = "",
        val dvdMediaType: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + installation.newLine() + connections.newLine() +
                    dvdMediaType.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class BDDriveDescription(
        val maker: String = "",
        val installation: String = "",
        val connections: String = "",
        val ultraHD: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + installation.newLine() + connections.newLine() +
                    ultraHD.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class SoundCardDescription(
        val maker: String = "",
        val installation: String = "",
        val connections: String = "",
        val surround: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + installation.newLine() + connections.newLine() +
                    surround.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class SpeakerDescription(
        val maker: String = "",
        val speakerType: String = "",
        val powerSupply: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + speakerType.newLine() + powerSupply.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class FanControllerDescription(
        val maker: String = "",
        val numberOfControls: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + numberOfControls.newLine()).trimEnd('\n')
        }
    }

    @Serializable
    data class FanDescription(
        val maker: String = "",
        val size: String = "",
        val airFlow: String = "",
        val rotationSpeed: String = "",
        val noise: String = "",
        val connectorPin: String = "",
        val led: String = "",
        val setQuantity: String = "",
    ) : Description() {
        override fun desc(): String {
            return (maker.newLine() + size.newLine() + airFlow.newLine() + rotationSpeed.newLine() +
                    noise.newLine() + connectorPin.newLine() + led.newLine() + setQuantity.newLine()
                    ).trimEnd('\n')
        }
    }
}

private fun String.newLine(): String =
    if (this.isBlank()) {
        ""
    } else {
        "${this}\n"
    }

private fun List<String>.newLine(): String =
    if (this.isEmpty()) {
        ""
    } else {
        "${this.joinToString("\n")}\n"
    }
