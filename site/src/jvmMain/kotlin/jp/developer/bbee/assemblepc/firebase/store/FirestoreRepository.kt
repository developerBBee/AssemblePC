package jp.developer.bbee.assemblepc.firebase.store

import com.google.api.core.ApiFutureToListenableFuture
import com.google.cloud.firestore.Firestore
import jp.developer.bbee.assemblepc.models.Item
import jp.developer.bbee.assemblepc.models.ItemCategory
import jp.developer.bbee.assemblepc.task.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.asDeferred
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FirestoreRepository : DataRepository, KoinComponent {
    private val firestore: Firestore by inject()

    override suspend fun saveItem(item: Item) {
        val docRef = firestore
            .collection("categories")
            .document(ItemCategory.from(item.itemCategoryId).name)
            .collection("items")
            .document(item.itemId.id)
        ApiFutureToListenableFuture(docRef.set(item)).asDeferred().await()
    }

    override suspend fun getItems(category: ItemCategory, skip: Long, limit: Int): List<Item> {
        val query = firestore.collection("categories")
            .document(category.name)
            .collection("items")
            .offset(skip.toIntMaxCeil())
            .limit(limit)

        return withContext(Dispatchers.IO) {
            query.get().get().toObjects(Item::class.java)
        }
    }
}

private fun Long.toIntMaxCeil(): Int {
    return if (this > Int.MAX_VALUE) Int.MAX_VALUE else this.toInt()
}
