package jp.developer.bbee.assemblepc.task.data.repository

import jp.developer.bbee.assemblepc.task.data.client.TaskHttpClient
import jp.developer.bbee.assemblepc.task.domain.repository.TaskRepository
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