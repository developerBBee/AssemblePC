package bbee.developer.jp.assemble_pc.task.domain.repository

import java.io.InputStream

interface TaskRepository {
    fun getHtml(url: String, streamHandler: (InputStream) -> Unit)
}