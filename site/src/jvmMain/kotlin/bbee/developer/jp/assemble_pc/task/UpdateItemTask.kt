package bbee.developer.jp.assemble_pc.task

import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.task.domain.controller.TaskController
import bbee.developer.jp.assemble_pc.util.currentDateTime
import bbee.developer.jp.assemble_pc.util.logger
import bbee.developer.jp.assemble_pc.util.minus
import bbee.developer.jp.assemble_pc.util.plusDays
import bbee.developer.jp.assemble_pc.util.set4Hour
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private val WAIT_INTERVAL = 60000.milliseconds

@InitApi
fun initTask(context: InitApiContext) {
    logger = context.logger
    UpdateItemTask.start()
}

class UpdateItemTask {
    companion object {
        private val controller = TaskController()
        fun start() {
            CoroutineScope(Dispatchers.IO).launch {

                while (isActive) {
                    val next = getNext()
                    logger.debug("Task is scheduled to run in ${next/1000} [s]")
                    delay(next)
                    ItemCategory.entries.forEach { category ->
                        controller.getItemHtml(category = category)
                        delay(WAIT_INTERVAL)
                    }
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
