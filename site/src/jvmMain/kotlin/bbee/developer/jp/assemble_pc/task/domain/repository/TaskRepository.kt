package bbee.developer.jp.assemble_pc.task.domain.repository

import io.ktor.http.Url
import java.io.InputStream

interface TaskRepository {
    suspend fun getHtml(url: Url, streamHandler: (InputStream) -> Unit)
}