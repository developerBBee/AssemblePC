package jp.developer.bbee.assemblepc.firebase.module

import com.google.firebase.cloud.FirestoreClient
import jp.developer.bbee.assemblepc.firebase.store.FirestoreRepository
import jp.developer.bbee.assemblepc.task.data.repository.DataRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val FirestoreModule = module {
    single { FirestoreClient.getFirestore() }
    singleOf(::FirestoreRepository) bind DataRepository::class
}
