package bbee.developer.jp.assemble_pc.task.data.repository

import bbee.developer.jp.assemble_pc.task.data.client.TaskHttpClient
import bbee.developer.jp.assemble_pc.task.domain.repository.TaskRepository
import io.ktor.http.Url
import java.io.InputStream

class TaskRepositoryImpl : TaskRepository {
    override suspend fun getHtml(url: Url, streamHandler: (InputStream) -> Unit) {
        val taskHttpClient = TaskHttpClient()
        taskHttpClient.use {
            taskHttpClient.getHtml(url, streamHandler)
        }
    }
}