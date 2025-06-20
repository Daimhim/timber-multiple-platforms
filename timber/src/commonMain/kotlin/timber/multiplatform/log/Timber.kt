package timber.multiplatform.log

expect object Timber {
    
    fun v(t: Throwable?)
    
    fun v(message: String?, vararg args: Any?)
    
    fun v(t: Throwable?, message: String?, vararg args: Any?)
    
    fun d(t: Throwable?)
    
    fun d(message: String?, vararg args: Any?)
    
    fun d(t: Throwable?, message: String?, vararg args: Any?)
    
    fun i(t: Throwable?)
    
    fun i(message: String?, vararg args: Any?)
    
    fun i(t: Throwable?, message: String?, vararg args: Any?)
    
    fun w(t: Throwable?)
    
    fun w(message: String?, vararg args: Any?)
    
    fun w(t: Throwable?, message: String?, vararg args: Any?)
    
    fun e(t: Throwable?)
    
    fun e(message: String?, vararg args: Any?)
    
    fun e(t: Throwable?, message: String?, vararg args: Any?)
    
    fun wtf(t: Throwable?)
    
    fun wtf(message: String?, vararg args: Any?)
    
    fun wtf(t: Throwable?, message: String?, vararg args: Any?)
    
    fun log(priority: Int, t: Throwable?)
    
    fun log(priority: Int, message: String?, vararg args: Any?)
    
    fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?)
    
    fun printlnStackTrace(tag:String? = "")

    
    fun asTree(): Tree
    
    fun tag(tag: String): Tree

    
    fun plant(tree: Tree)

    
    fun plant(vararg trees: Tree)

    
    fun uproot(tree: Tree)

    
    fun uprootAll()

    
    fun forest(): List<Tree>

    
    fun treeCount():Int
}