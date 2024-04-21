package bbee.developer.jp.assemble_pc.task.data.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.jvm.javaio.toInputStream
import java.io.Closeable
import java.io.InputStream

class TaskHttpClient : Closeable {
    private val client: HttpClient by lazy {
        HttpClient(CIO) {
            expectSuccess = true
            install(HttpRequestRetry) {
                retryOnExceptionOrServerErrors(maxRetries = 3)
                exponentialDelay()
            }

            engine {
                endpoint {
                    connectTimeout = 10_000L
                }
            }
        }
    }

    suspend fun getHtml(url: String, streamHandler: (InputStream) -> Unit): Int {
        val response = client.get(url)
        response.bodyAsChannel().toInputStream().use {
            streamHandler(it)
        }
        return response.status.value
    }

    override fun close() {
        client.close()
    }
}