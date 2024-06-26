package timber.multiplatform.log

import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException
import java.util.*
import java.util.regex.Pattern

actual class DebugTree : Tree() {
    private val LOG_ID_MAIN = 0
    private val LOG_ID_RADIO = 1
    private val LOG_ID_EVENTS = 2
    private val LOG_ID_SYSTEM = 3
    private val LOG_ID_CRASH = 4

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
    protected open fun createStackElementTag(element: StackTraceElement): String? {
        var tag = element.className.substringAfterLast('.')
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        // Tag length limit was removed in API 26.
        return tag
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
        if (priority == ASSERT) {
            wtf(tag, spliceMessage)
        } else {
            println(priority, tag, spliceMessage)
        }
    }
    private fun processTagAndHead(): String? {
        var targetElement: StackTraceElement? = null
        val elements = Thread.currentThread().stackTrace
        for (i in elements.size - 2 downTo 0) {
            val caller = elements[i]
            if (LOG_STACK_FILTERING.indexOf(caller.className) >= 0) {
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
    fun wtf(tag: String?, msg: String): Int {
        return println(LOG_ID_MAIN, WARN, tag, msg)
    }

    fun wtf(tag: String, tr: Throwable): Int {
        return println(LOG_ID_MAIN, WARN, tag, getStackTraceString(tr))
    }

    fun wtf(tag: String, msg: String, tr: Throwable): Int {
        return println(LOG_ID_MAIN, WARN, tag, "$msg\\n${getStackTraceString(tr)}")
    }

    fun println(priority: Int, tag: String?, msg: String): Int {
        return println(LOG_ID_MAIN, priority, tag, msg)
    }

    private fun println(bufID: Int, priority: Int, tag: String?, msg: String): Int {
        println(
            "${Date()}:${getBufIDStr(bufID)}:${getPriorityStr(priority)}:$tag:$msg"
        )
        return 0
    }
    fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }
    private fun getBufIDStr(bufID: Int):String{
        return when(bufID){
            LOG_ID_MAIN->{
                "MAIN"
            }
            LOG_ID_RADIO->{
                "RADIO"
            }
            LOG_ID_EVENTS->{
                "EVENTS"
            }
            LOG_ID_SYSTEM->{
                "SYSTEM"
            }
            LOG_ID_CRASH->{
                "CRASH"
            }else->{
                ""
            }
        }
    }

    private fun getPriorityStr(priority: Int):String{
        return when(priority){
            VERBOSE->{"VERBOSE"}
            DEBUG->{"DEBUG"}
            INFO->{"INFO"}
            WARN->{"WARN"}
            ERROR->{"ERROR"}
            ASSERT->{"ASSERT"}
            else->{""}
        }
    }
    companion object {

        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }
}