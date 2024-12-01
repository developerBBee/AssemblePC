package jp.developer.bbee.assemblepc.task.module

import jp.developer.bbee.assemblepc.task.data.repository.TaskRepositoryImpl
import jp.developer.bbee.assemblepc.task.domain.repository.TaskRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val TaskModule = module {
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
}