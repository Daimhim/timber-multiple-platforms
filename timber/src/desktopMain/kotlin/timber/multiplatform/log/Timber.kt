package timber.multiplatform.log

actual object Timber {
    @JvmStatic
    actual fun v(t: Throwable?) {
        Forest.v(t)
    }

    @JvmStatic
    actual fun v(message: String?, vararg args: Any?) {
        Forest.v(message, args)
    }

    @JvmStatic
    actual fun v(t: Throwable?, message: String?, vararg args: Any?) {
        Forest.v(t, message, args)
    }

    @JvmStatic
    actual fun d(t: Throwable?) {
        Forest.d(t)
    }

    @JvmStatic
    actual fun d(message: String?, vararg args: Any?) {
        Forest.d(message, args)
    }

    @JvmStatic
    actual fun d(t: Throwable?, message: String?, vararg args: Any?) {
        Forest.d(t, message, args)
    }

    @JvmStatic
    actual fun i(t: Throwable?) {
        Forest.i(t)
    }

    @JvmStatic
    actual fun i(message: String?, vararg args: Any?) {
        Forest.i(message, args)
    }

    @JvmStatic
    actual fun i(t: Throwable?, message: String?, vararg args: Any?) {
        Forest.i(t, message, args)
    }

    @JvmStatic
    actual fun w(t: Throwable?) {
        Forest.w(t)
    }

    @JvmStatic
    actual fun w(message: String?, vararg args: Any?) {
        Forest.w(message, args)
    }

    @JvmStatic
    actual fun w(t: Throwable?, message: String?, vararg args: Any?) {
        Forest.w(t, message, args)
    }

    @JvmStatic
    actual fun e(t: Throwable?) {
        Forest.e(t)
    }

    @JvmStatic
    actual fun e(message: String?, vararg args: Any?) {
        Forest.e(message, args)
    }

    @JvmStatic
    actual fun e(t: Throwable?, message: String?, vararg args: Any?) {
        Forest.e(t, message, args)
    }

    @JvmStatic
    actual fun wtf(t: Throwable?) {
        Forest.wtf(t)
    }

    @JvmStatic
    actual fun wtf(message: String?, vararg args: Any?) {
        Forest.wtf(message, args)
    }

    @JvmStatic
    actual fun wtf(t: Throwable?, message: String?, vararg args: Any?) {
        Forest.wtf(t, message, args)
    }

    @JvmStatic
    actual fun log(priority: Int, t: Throwable?) {
        Forest.log(priority, t)
    }

    @JvmStatic
    actual fun log(priority: Int, message: String?, vararg args: Any?) {
        Forest.log(priority, message, args)
    }

    @JvmStatic
    actual fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        Forest.log(priority, t, message, args)
    }

    @JvmStatic
    actual fun printlnStackTrace(tag: String?){
        Forest.printlnStackTrace(tag)
    }

    @JvmStatic
    actual inline fun asTree(): Tree {
        return Forest.asTree()
    }

    @JvmStatic
    actual fun tag(tag: String): Tree {
        return Forest.tag(tag)
    }

    @JvmStatic
    actual fun plant(tree: Tree) {
        Forest.plant(tree)
    }

    @JvmStatic
    actual fun plant(vararg trees: Tree) {
        Forest.plant(*trees)
    }

    @JvmStatic
    actual fun uproot(tree: Tree) {
        Forest.uproot(tree)
    }

    @JvmStatic
    actual fun uprootAll() {
        Forest.uprootAll()
    }

    @JvmStatic
    actual fun forest(): List<Tree> {
        return Forest.forest()
    }

    @JvmStatic
    actual fun treeCount():Int {
        return Forest.treeCount
    }

}