package timber.multiplatform.log

/** A facade for handling logging calls. Install instances via [`Timber.plant()`][.plant]. */
abstract class Tree {
    val VERBOSE = 2
    val DEBUG = 3
    val INFO = 4
    val WARN = 5
    val ERROR = 6
    val ASSERT = 7

    abstract fun getTag(): String?
    abstract fun setTag(tag: String?)

    /** Log a verbose message with optional format args. */
    open fun v(message: String?, vararg args: Any?) {
        prepareLog(VERBOSE, null, message, *args)
    }

    /** Log a verbose exception and a message with optional format args. */
    open fun v(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(VERBOSE, t, message, *args)
    }

    /** Log a verbose exception. */
    open fun v(t: Throwable?) {
        prepareLog(VERBOSE, t, null)
    }

    /** Log a debug message with optional format args. */
    open fun d(message: String?, vararg args: Any?) {
        prepareLog(DEBUG, null, message, *args)
    }

    /** Log a debug exception and a message with optional format args. */
    open fun d(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(DEBUG, t, message, *args)
    }

    /** Log a debug exception. */
    open fun d(t: Throwable?) {
        prepareLog(DEBUG, t, null)
    }

    /** Log an info message with optional format args. */
    open fun i(message: String?, vararg args: Any?) {
        prepareLog(INFO, null, message, *args)
    }

    /** Log an info exception and a message with optional format args. */
    open fun i(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(INFO, t, message, *args)
    }

    /** Log an info exception. */
    open fun i(t: Throwable?) {
        prepareLog(INFO, t, null)
    }

    /** Log a warning message with optional format args. */
    open fun w(message: String?, vararg args: Any?) {
        prepareLog(WARN, null, message, *args)
    }

    /** Log a warning exception and a message with optional format args. */
    open fun w(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(WARN, t, message, *args)
    }

    /** Log a warning exception. */
    open fun w(t: Throwable?) {
        prepareLog(WARN, t, null)
    }

    /** Log an error message with optional format args. */
    open fun e(message: String?, vararg args: Any?) {
        prepareLog(ERROR, null, message, *args)
    }

    /** Log an error exception and a message with optional format args. */
    open fun e(t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(ERROR, t, message, *args)
    }

    /** Log an error exception. */
    open fun e(t: Throwable?) {
        prepareLog(ERROR, t, null)
    }

    open fun printlnStackTrace(tag:String?=""){
        StackTraceUtils.printStackTrace(this,tag)
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

    /** Log at `priority` a message with optional format args. */
    open fun log(priority: Int, message: String?, vararg args: Any?) {
        prepareLog(priority, null, message, *args)
    }

    /** Log at `priority` an exception and a message with optional format args. */
    open fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        prepareLog(priority, t, message, *args)
    }

    /** Log at `priority` an exception. */
    open fun log(priority: Int, t: Throwable?) {
        prepareLog(priority, t, null)
    }

    /** Return whether a message at `priority` or `tag` should be logged. */
    protected open fun isLoggable(tag: String?, priority: Int) = true

    private fun prepareLog(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        // Consume tag even when message is not loggable so that next message is correctly tagged.
        val tag = getTag()
        if (!isLoggable(tag, priority)) {
            return
        }
        var message = message
        if (message.isNullOrEmpty()) {
            if (t == null) {
                return  // Swallow message if it's null and there's no throwable.
            }
            message = getStackTraceString(t)
        } else {
            if (args.isNotEmpty()) {
                message = formatMessage(message, args)
            }
            if (t != null) {
                message += "\n" + getStackTraceString(t)
            }
        }

        log(priority, tag, message, t)
    }

    /** Formats a log message with optional arguments. */
    protected open fun formatMessage(message: String, args: Array<out Any?>) = StackTraceUtils.stringFormat(message,args)

    private fun getStackTraceString(t: Throwable): String {
        return StackTraceUtils.getStackTraceString(t)
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