package timber.multiplatform.log

expect object Timber {
    @JvmStatic
    fun v(t: Throwable?)
    @JvmStatic
    fun v(message: String?, vararg args: Any?)
    @JvmStatic
    fun v(tag: String?, message: String?, vararg args: Any?)
    @JvmStatic
    fun v(t: Throwable?, message: String?, vararg args: Any?)
    @JvmStatic
    fun d(t: Throwable?)
    @JvmStatic
    fun d(message: String?, vararg args: Any?)
    @JvmStatic
    fun d(tag: String?, message: String?, vararg args: Any?)
    @JvmStatic
    fun d(t: Throwable?, message: String?, vararg args: Any?)
    @JvmStatic
    fun i(t: Throwable?)
    @JvmStatic
    fun i(message: String?, vararg args: Any?)
    @JvmStatic
    fun i(tag: String?, message: String?, vararg args: Any?)
    @JvmStatic
    fun i(t: Throwable?, message: String?, vararg args: Any?)
    @JvmStatic
    fun w(t: Throwable?)
    @JvmStatic
    fun w(message: String?, vararg args: Any?)
    @JvmStatic
    fun w(tag: String?, message: String?, vararg args: Any?)
    @JvmStatic
    fun w(t: Throwable?, message: String?, vararg args: Any?)
    @JvmStatic
    fun e(t: Throwable?)
    @JvmStatic
    fun e(message: String?, vararg args: Any?)
    @JvmStatic
    fun e(tag: String?, message: String?, vararg args: Any?)
    @JvmStatic
    fun e(t: Throwable?, message: String?, vararg args: Any?)
    @JvmStatic
    fun wtf(t: Throwable?)
    @JvmStatic
    fun wtf(message: String?, vararg args: Any?)
    @JvmStatic
    fun wtf(t: Throwable?, message: String?, vararg args: Any?)
    @JvmStatic
    fun log(priority: Int, t: Throwable?)
    @JvmStatic
    fun log(priority: Int, message: String?, vararg args: Any?)
    @JvmStatic
    fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?)
    @JvmStatic
    fun printlnStackTrace(tag:String? = "")

    @JvmStatic
    fun asTree(): Tree
    @JvmStatic
    fun tag(tag: String): Tree

    @JvmStatic
    fun plant(tree: Tree)

    @JvmStatic
    fun plant(vararg trees: Tree)

    @JvmStatic
    fun uproot(tree: Tree)

    @JvmStatic
    fun uprootAll()

    @JvmStatic
    fun forest(): List<Tree>

    @JvmStatic
    fun treeCount():Int
}