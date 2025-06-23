package timber.multiplatform.log

expect object StackTraceUtils {
    /**
     * 将异常堆栈跟踪转换为字符串
     * @param throwable 要转换的异常
     * @return 包含堆栈跟踪的字符串
     */
    fun getStackTraceString(throwable: Throwable): String
    fun printStackTrace(tree:Tree,tag:String?="")
    fun stringFormat(message: String, args: Array<out Any?>): String
}