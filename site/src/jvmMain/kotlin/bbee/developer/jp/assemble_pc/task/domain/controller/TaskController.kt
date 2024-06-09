package bbee.developer.jp.assemble_pc.task.domain.controller

import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.Maker
import bbee.developer.jp.assemble_pc.models.Price
import bbee.developer.jp.assemble_pc.task.domain.repository.TaskRepository
import bbee.developer.jp.assemble_pc.util.KakakuRegex
import bbee.developer.jp.assemble_pc.util.append
import bbee.developer.jp.assemble_pc.util.currentDateString
import bbee.developer.jp.assemble_pc.util.first
import bbee.developer.jp.assemble_pc.util.getDetail
import bbee.developer.jp.assemble_pc.util.localRepository
import bbee.developer.jp.assemble_pc.util.logger
import bbee.developer.jp.assemble_pc.util.replaceHtmlEntity
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.time.Duration.Companion.milliseconds

private const val SHIFT_JIS = "Shift-JIS"
private const val SPEC_PATH = "spec/"
private const val MAX_READ_LINE = 5000L
private const val URL_LIST_SIZE = 10L
private const val MAX_READ_WORDS = 50000
private val INTERVAL = 1000.milliseconds

class TaskController : KoinComponent {
    private val repository: TaskRepository by inject()

    private val urlListFlow: MutableStateFlow<Pair<ItemCategory, List<Url>>?> =
        MutableStateFlow(null)
    private val itemFlow: MutableStateFlow<Item?> = MutableStateFlow(null)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            urlListFlow
                .filterNotNull()
                .filter { (_, urlList) -> urlList.isNotEmpty() }
                .collect { (category, urlList) ->
                    urlList.forEach { url ->
                        getItemDetailHtml(category, url)
                        delay(INTERVAL)
                    }
                }
        }

        CoroutineScope(Dispatchers.IO).launch {
            itemFlow
                .filterNotNull()
                .collect(::saveItemDetail)
        }
    }

    suspend fun getItemHtml(category: ItemCategory) {
        runCatching {
            repository.getHtml(category.url) { inputStream ->
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, SHIFT_JIS))

                bufferedReader.use { br ->
                    br.lines()
                        .limit(MAX_READ_LINE)
                        .map { KakakuRegex.RANKING_URL.first(it) }
                        .filter { it != null }
                        .limit(URL_LIST_SIZE)
                        .toList()
                        .filterNotNull()
                        .map { Url(it) }
                        .also { urlListFlow.value = Pair(category, it) }
                }
            }
        }.onFailure { logger.error("getItemHtml Exception: $it") }
    }

    private suspend fun getItemDetailHtml(category: ItemCategory, url: Url) {
        runCatching {
            repository.getHtml(url.append(SPEC_PATH)) { inputStream ->
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, SHIFT_JIS))

                bufferedReader.use { br ->
                    br.readText()
                        .substring(0, MAX_READ_WORDS)
                        .replaceHtmlEntity()
                        .let {
                            Item(
                                itemCategoryId = category.toId(),
                                makerId = Maker.fromName(KakakuRegex.MAKER_NAME.first(it)).toId(),
                                itemName = KakakuRegex.ITEM_NAME.first(it) ?: "",
                                linkUrl = url.toString(),
                                imageUrl = KakakuRegex.IMAGE_URL.first(it) ?: "",
                                description = getDetail(it, category),
                                price = Price.from(KakakuRegex.PRICE_TEXT.first(it) ?: "0"),
                                rank = KakakuRegex.RANK_TEXT1.first(it)?.toInt()
                                    ?: KakakuRegex.RANK_TEXT2.first(it)?.toInt()
                                    ?: KakakuRegex.RANK_TEXT3.first(it)?.toInt()
                                    ?: Int.MAX_VALUE,
                                releaseDate = currentDateString,
                                outdated = false,
                                lastUpdate = currentDateString
                            )
                        }
                        .also {
                            logger.debug(it.toString())
                            itemFlow.value = it
                        }
                }
            }
        }.onFailure { logger.error("getItemDetailHtml Exception: $it") }
    }

    private suspend fun saveItemDetail(item: Item) {
        runCatching {
            localRepository.saveItem(item = item)
        }.onFailure { logger.error("saveItemDetail Exception: $it") }
    }

    suspend fun getItemLocal(category: ItemCategory) {
        runCatching {
            localRepository
                .getItems(category = category, skip = 0L, limit = 1000)
                .filterNot { it.outdated }
                .filter { it.lastUpdate != currentDateString }
                .map { Url(it.linkUrl) }
                .also {
                    logger.debug("update items size=${it.size}")
                    urlListFlow.value = Pair(category, it)
                }
        }.onFailure { logger.error("getItem Exception: $it") }
    }
}