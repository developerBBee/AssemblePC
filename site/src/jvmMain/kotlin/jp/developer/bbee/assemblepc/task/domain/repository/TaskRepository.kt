package jp.developer.bbee.assemblepc.task.domain.repository

import io.ktor.http.Url
import java.io.InputStream

interface TaskRepository {
    suspend fun getHtml(url: Url, streamHandler: (InputStream) -> Unit)
}