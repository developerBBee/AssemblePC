package bbee.developer.jp.assemble_pc.task.data.repository

import bbee.developer.jp.assemble_pc.task.data.client.TaskHttpClient
import bbee.developer.jp.assemble_pc.task.domain.repository.TaskRepository
import java.io.InputStream

class TaskRepositoryImpl : TaskRepository {
    override fun getHtml(url: String, streamHandler: (InputStream) -> Unit) {
        val taskHttpClient = TaskHttpClient()
        taskHttpClient.use {
            TODO()
        }
    }
}