package timber.multiplatform.log

import android.os.Build
import android.util.Log
import java.util.*
import java.util.regex.Pattern

actual class DebugTree : Tree() {

    override val tag: String?
        get() = super.tag ?: Throwable().stackTrace
            .first { it.className !in LOG_STACK_FILTERING }
            .let(::createStackElementTag)

    /**
     * Extract the tag which should be used for the message from the `element`. By default
     * this will use the class name without any anonymous class suffixes (e.g., `Foo$1`
     * becomes `Foo`).
     *
     * Note: This will not be called if a [manual tag][.tag] was specified.
     */
    protected fun createStackElementTag(element: StackTraceElement): String? {
        var tag = element.className.substringAfterLast('.')
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        // Tag length limit was removed in API 26.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
            tag
        } else {
            tag.substring(0, MAX_TAG_LENGTH)
        }
    }

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
            if (LOG_STACK_FILTERING.indexOf(caller.className) >= 0){
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
        private const val MAX_TAG_LENGTH = 23
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }

}