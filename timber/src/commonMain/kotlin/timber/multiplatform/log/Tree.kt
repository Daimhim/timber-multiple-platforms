package timber.multiplatform.log

import java.io.PrintWriter
import java.io.StringWriter

/** A facade for handling logging calls. Install instances via [`Timber.plant()`][.plant]. */
abstract class Tree {
    val VERBOSE = 2
    val DEBUG = 3
    val INFO = 4
    val WARN = 5
    val ERROR = 6
    val ASSERT = 7
    val LOG_STACK_FILTERING = arrayOf(
        Forest::class.java.name,
        Tree::class.java.name,
        DebugTree::class.java.name,
        Timber::class.java.name,
        CustomTagTree::class.java.name,
    )
    
    @get:JvmSynthetic // Hide from public API.
    internal val explicitTag = ThreadLocal<String>()

    @get:JvmSynthetic // Hide from public API.
    internal open val tag: String?
        get() {
            val tag = explicitTag.get()
            if (tag != null) {
                explicitTag.remove()
            }
            return tag
        }

    /** Log a verbose exception. */
    open fun v(t: Throwable?) {
        prepareLog(VERBOSE, t, null)
    }
    /** Log a verbose message with optional format args. */
    open fun v(message: String?, vararg args: Any?) {
        prepareLog(VERBOSE, null, message, *args)
    }

    open fun v(tag: String?, message: String?, vararg args: Any?) {
        prepareLog(tag, VERBOSE,null, message, *args)
    }

    /** Log a verbose exception and a message with optional format args. */
    open fun v(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(VERBOSE, t, message, *args)
    }

    /** Log a debug exception. */
    open fun d(t: Throwable?) {
        prepareLog(DEBUG, t, null)
    }
    /** Log a debug message with optional format args. */
    open fun d(message: String?, vararg args: Any?) {
        prepareLog(DEBUG, null, message, *args)
    }
    open fun d(tag: String?, message: String?, vararg args: Any?) {
        prepareLog(tag, DEBUG,null, message, *args)
    }

    /** Log a debug exception and a message with optional format args. */
    open fun d(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(DEBUG, t, message, *args)
    }

    /** Log an info exception. */
    open fun i(t: Throwable?) {
        prepareLog(INFO, t, null)
    }
    /** Log an info message with optional format args. */
    open fun i(message: String?, vararg args: Any?) {
        prepareLog(INFO, null, message, *args)
    }
    open fun i(tag: String?, message: String?, vararg args: Any?) {
        prepareLog(tag, INFO,null, message, *args)
    }
    /** Log an info exception and a message with optional format args. */
    open fun i(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(INFO, t, message, *args)
    }

    /** Log a warning exception. */
    open fun w(t: Throwable?) {
        prepareLog(WARN, t, null)
    }
    /** Log a warning message with optional format args. */
    open fun w(message: String?, vararg args: Any?) {
        prepareLog(WARN, null, message, *args)
    }
    open fun w(tag: String?, message: String?, vararg args: Any?) {
        prepareLog(tag, WARN,null, message, *args)
    }
    /** Log a warning exception and a message with optional format args. */
    open fun w(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(WARN, t, message, *args)
    }
    /** Log an error exception. */
    open fun e(t: Throwable?) {
        prepareLog(ERROR, t, null)
    }
    /** Log an error message with optional format args. */
    open fun e(message: String?, vararg args: Any?) {
        prepareLog(ERROR, null, message, *args)
    }

    open fun e(tag: String?, message: String?, vararg args: Any?) {
        prepareLog(tag, ERROR,null, message, *args)
    }

    /** Log an error exception and a message with optional format args. */
    open fun e(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(ERROR, t, message, *args)
    }

    open fun printlnStackTrace(tag:String?=""){
        Thread
            .currentThread()
            .stackTrace
            .forEach {
                i("${tag}$it")
            }
    }

    /** Log an assert message with optional format args. */
    open fun wtf(message: String?, vararg args: Any?) {
        prepareLog(ASSERT, null, message, *args)
    }

    /** Log an assert exception and a message with optional format args. */
    open fun wtf(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(ASSERT, t, message, *args)
    }

    /** Log an assert exception. */
    open fun wtf(t: Throwable?) {
        prepareLog(ASSERT, t, null)
    }

    /** Log at `priority` an exception. */
    open fun log(priority: Int, t: Throwable?) {
        prepareLog(priority, t, null)
    }

    /** Log at `priority` a message with optional format args. */
    open fun log(priority: Int, message: String?, vararg args: Any?) {
        prepareLog(priority, null, message, *args)
    }

    /** Log at `priority` an exception and a message with optional format args. */
    open fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(priority, t, message, *args)
    }


    /** Return whether a message at `priority` or `tag` should be logged. */
    protected open fun isLoggable(tag: String?, priority: Int) = true

    private fun prepareLog(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(tag, priority, t, message, *args)
    }
    private fun prepareLog(tag: String?,priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        // Consume tag even when message is not loggable so that next message is correctly tagged.
        if (!isLoggable(tag, priority)) {
            return
        }
        var msg = message
        if (msg.isNullOrEmpty()) {
            if (t == null) {
                return  // Swallow message if it's null and there's no throwable.
            }
            msg = getStackTraceString(t)
        } else {
            if (args.isNotEmpty()) {
                msg = formatMessage(msg, args)
            }
            if (t != null) {
                msg += "\n" + getStackTraceString(t)
            }
        }

        log(priority, tag, msg, t)
    }

    /** Formats a log message with optional arguments. */
    protected open fun formatMessage(message: String, args: Array<out Any?>) = message.format(*args)

    private fun getStackTraceString(t: Throwable): String {
        // Don't replace this with getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See [Log] for constants.
     * @param tag Explicit or inferred tag. May be `null`.
     * @param message Formatted log message.
     * @param t Accompanying exceptions. May be `null`.
     */
    protected abstract fun log(priority: Int, tag: String?, message: String, t: Throwable?)
}