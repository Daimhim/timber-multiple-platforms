package timber.multiplatform.log

import android.util.Log
import java.util.*

actual class CustomTagTree actual constructor(
    private val customTag:String,
    vararg logStackFiltering:String) : Tree() {

    override val tag: String?
        get() = customTag

    private val fqcnIgnore = arrayOf<String>(
        *LOG_STACK_FILTERING,
        *logStackFiltering
    )
    /**
     * Break up `message` into maximum-length chunks (if needed) and send to either
     * [ProxyLog.println()][ProxyLog.println] or
     * [ProxyLog.wtf()][ProxyLog.wtf] for logging.
     *
     * {@inheritDoc}
     */
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val spliceMessage = "$message ${processTagAndHead()?:"Unknown Source"}"
        if (spliceMessage.length < MAX_LOG_LENGTH) {
            if (priority == ASSERT) {
                Log.wtf(tag, spliceMessage)
            } else {
                Log.println(priority, tag, spliceMessage)
            }
            return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = spliceMessage.length
        while (i < length) {
            var newline = spliceMessage.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = spliceMessage.substring(i, end)
                if (priority == ASSERT) {
                    Log.wtf(tag, part)
                } else {
                    Log.println(priority, tag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }
    private fun processTagAndHead(): String? {
        var targetElement: StackTraceElement? = null
        val elements = Thread.currentThread().stackTrace
        for (i in elements.size - 2 downTo 0) {
            val caller = elements[i]
            if (fqcnIgnore.indexOf(caller.className) >= 0){
                targetElement = elements[i + 1]
                break
            }
        }
        return if (targetElement == null) {
            null
        } else {
            Formatter()
                .format(
                    "%s(%s:%d) | Thread -> %s ",
                    targetElement.methodName,
                    targetElement.fileName,
                    targetElement.lineNumber,
                    Thread.currentThread().name
                )
                .toString()
        }
    }
    companion object {
        private const val MAX_LOG_LENGTH = 1000
    }
}