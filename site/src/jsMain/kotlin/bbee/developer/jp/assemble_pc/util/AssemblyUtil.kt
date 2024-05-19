package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.Price

fun Assembly.totalAmount(): Price =
    if (assemblyDetails.isEmpty()) {
        Price(0)
    } else {
        assemblyDetails
            .map { it.item.price }
            .reduce { acc, price -> acc + price }
    }