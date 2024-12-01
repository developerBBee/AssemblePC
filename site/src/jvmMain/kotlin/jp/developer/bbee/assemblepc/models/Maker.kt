package jp.developer.bbee.assemblepc.models

enum class Maker(private val reg: Regex?) {
    OTHER(null),
    INTEL(Regex(""".*(intel|インテル).*""", RegexOption.IGNORE_CASE)),
    AMD(null),
    NVIDIA(null),
    ASUS(null),
    GIGABYTE(null),
    MSI(null),
    ASROCK(null),
    COLORFUL(null),
    ZOTAC(null),
    EVGA(null),
    ;

    fun toId() = MakerId(this.ordinal)

    companion object {
        fun fromId(id: MakerId) = entries.first { id.id == it.ordinal }

        fun fromName(name: String?): Maker =
            entries.firstOrNull { it.name.equals(other = name, ignoreCase = true) }
                ?: kotlin.run {
                    if (name == null) OTHER
                    else entries.firstOrNull { it.reg?.matches(name) ?: false } ?: OTHER
                }
    }
}