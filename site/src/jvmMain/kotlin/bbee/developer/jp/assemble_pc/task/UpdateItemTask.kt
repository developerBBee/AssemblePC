package bbee.developer.jp.assemble_pc.task

import bbee.developer.jp.assemble_pc.task.domain.controller.TaskController
import bbee.developer.jp.assemble_pc.util.currentDateTime
import bbee.developer.jp.assemble_pc.util.minus
import bbee.developer.jp.assemble_pc.util.plusDays
import bbee.developer.jp.assemble_pc.util.set4Hour
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.varabyte.kobweb.api.log.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@InitApi
fun initTask(context: InitApiContext) {
    UpdateItemTask.start(context.logger)
}

class UpdateItemTask {
    companion object {
        fun start(logger: Logger) {
            CoroutineScope(Dispatchers.IO).launch {
                while (isActive) {
                    val next = getNext()
                    logger.debug("Task is scheduled to run in ${next/1000} [s]")
                    delay(next)
                    val controller = TaskController()
                    controller.getItemHtml()
                }
            }
        }

        private fun getNext(): Long {
            val now = currentDateTime

            return if (now.hour >= 4) {
                now.plusDays(1)
            } else {
                now
            }.set4Hour().minus(now)
        }
    }
}
