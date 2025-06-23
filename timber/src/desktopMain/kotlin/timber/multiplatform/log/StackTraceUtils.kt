package timber.multiplatform.log

import java.io.PrintWriter
import java.io.StringWriter

actual object StackTraceUtils {
    actual fun getStackTraceString(throwable: Throwable): String {
        // Don't replace this with getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        throwable.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    actual fun printStackTrace(tree:Tree,tag:String?){
        Thread
            .currentThread()
            .stackTrace
            .forEach {
                tree.i("${tag}$it")
            }
    }

    actual fun stringFormat(message: String, args: Array<out Any?>): String{
        return String.format(message,*args)
    }
}