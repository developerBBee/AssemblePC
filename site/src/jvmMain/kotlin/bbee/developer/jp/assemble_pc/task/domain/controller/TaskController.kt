package bbee.developer.jp.assemble_pc.task.domain.controller

import bbee.developer.jp.assemble_pc.task.domain.model.Item
import bbee.developer.jp.assemble_pc.task.domain.repository.TaskRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TaskController : KoinComponent {
    private val repository: TaskRepository by inject()

    fun getItemHtml() {
        repository.getHtml(Item.CASE.url) { inputStream ->

        }
    }

    private fun getHtml(url: String) {
        repository.getHtml(url) { inputStream ->

        }
    }
}