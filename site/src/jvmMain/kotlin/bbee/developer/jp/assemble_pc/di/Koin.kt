package bbee.developer.jp.assemble_pc.di

import bbee.developer.jp.assemble_pc.task.module.TaskModule
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import org.koin.core.context.startKoin

@InitApi
fun initKoin(context: InitApiContext) {
    startKoin {
        modules(TaskModule)
    }
}