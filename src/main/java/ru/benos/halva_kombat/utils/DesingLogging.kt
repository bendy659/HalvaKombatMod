package ru.benos.halva_kombat.utils

import ru.benos.halva_kombat.HalvaKombat.Companion.MODID

object DesingLogging {
  fun desingLogging(desc: String): String {
    val separator0 = List(desc.length) {"="}.joinToString("=", "")
    val separator1 = List(desc.length) {"-"}.joinToString("-", "")

    return """
    /$separator0
    |>---[${MODID.uppercase()}]
    |$separator1
    |>---[$desc]
    \$separator0
    """.trimIndent()
  }
}