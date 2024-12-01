package jp.developer.bbee.assemblepc.task

import jp.developer.bbee.assemblepc.models.ItemCategory
import jp.developer.bbee.assemblepc.task.domain.controller.TaskController
import jp.developer.bbee.assemblepc.util.currentDateTime
import jp.developer.bbee.assemblepc.util.logger
import jp.developer.bbee.assemblepc.util.minus
import jp.developer.bbee.assemblepc.util.plusDays
import jp.developer.bbee.assemblepc.util.set4Hour
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val DEBUG = false
private val WAIT_INTERVAL = 30000.milliseconds

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
                var debugRun = DEBUG

                while (isActive) {
                    val next = if (debugRun) {
                        debugRun = false
                        10000L
                    } else {
                        getNext()
                    }

                    logger.debug("Task is scheduled to run in ${next/1000} [s]")
                    delay(next)
                    ItemCategory.entries.forEach { category ->
                        controller.getItemHtml(category = category)
                        delay(WAIT_INTERVAL)
                        controller.getItemLocal(category = category)
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
