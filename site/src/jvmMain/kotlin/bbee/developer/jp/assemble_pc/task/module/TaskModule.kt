package bbee.developer.jp.assemble_pc.task.module

import bbee.developer.jp.assemble_pc.task.data.repository.TaskRepositoryImpl
import bbee.developer.jp.assemble_pc.task.domain.repository.TaskRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val TaskModule = module {
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
}