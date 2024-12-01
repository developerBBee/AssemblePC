package jp.developer.bbee.assemblepc.util

import jp.developer.bbee.assemblepc.models.Assembly
import jp.developer.bbee.assemblepc.models.AssemblyId
import jp.developer.bbee.assemblepc.models.Price

fun Assembly.totalAmount(): Price =
    if (assemblyDetails.isEmpty()) {
        Price(0)
    } else {
        assemblyDetails
            .map { it.item.price }
            .reduce { acc, price -> acc + price }
    }

fun MutableList<Assembly>.favoriteUpdate(assemblyId: AssemblyId, addCount: Int) {
    find { it.assemblyId == assemblyId }
        ?.also { assembly ->
            val newCount = (assembly.favoriteCount + addCount).coerceAtLeast(0)
            this[indexOf(assembly)] = assembly.copy(favoriteCount = newCount)
        }
}