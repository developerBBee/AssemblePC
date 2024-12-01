package jp.developer.bbee.assemblepc.di

import jp.developer.bbee.assemblepc.task.module.TaskModule
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import org.koin.core.context.startKoin

@InitApi
fun initKoin(context: InitApiContext) {
    startKoin {
        modules(TaskModule)
    }
}