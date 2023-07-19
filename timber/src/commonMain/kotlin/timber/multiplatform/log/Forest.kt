package timber.multiplatform.log

import java.util.*

object Forest : Tree() {
    /** Log a verbose message with optional format args. */

    override fun v(message: String?, vararg args: Any?) {
        treeArray.forEach { it.v(message, *args) }
    }

    /** Log a verbose exception and a message with optional format args. */
    override fun v(t: Throwable?, message: String?, vararg args: Any?) {
        treeArray.forEach { it.v(t, message, *args) }
    }

    /** Log a verbose exception. */
    override fun v(t: Throwable?) {
        treeArray.forEach { it.v(t) }
    }

    /** Log a debug message with optional format args. */
    override fun d(message: String?, vararg args: Any?) {
        treeArray.forEach { it.d(message, *args) }
    }

    /** Log a debug exception and a message with optional format args. */
    override fun d(t: Throwable?, message: String?, vararg args: Any?) {
        treeArray.forEach { it.d(t, message, *args) }
    }

    /** Log a debug exception. */
    override fun d(t: Throwable?) {
        treeArray.forEach { it.d(t) }
    }

    /** Log an info message with optional format args. */
    override fun i(message: String?, vararg args: Any?) {
        treeArray.forEach { it.i(message, *args) }
    }

    /** Log an info exception and a message with optional format args. */
    override fun i(t: Throwable?, message: String?, vararg args: Any?) {
        treeArray.forEach { it.i(t, message, *args) }
    }

    /** Log an info exception. */
    override fun i(t: Throwable?) {
        treeArray.forEach { it.i(t) }
    }

    /** Log a warning message with optional format args. */
    override fun w(message: String?, vararg args: Any?) {
        treeArray.forEach { it.w(message, *args) }
    }

    /** Log a warning exception and a message with optional format args. */
    override fun w(t: Throwable?, message: String?, vararg args: Any?) {
        treeArray.forEach { it.w(t, message, *args) }
    }

    /** Log a warning exception. */
    override fun w(t: Throwable?) {
        treeArray.forEach { it.w(t) }
    }

    /** Log an error message with optional format args. */
    override fun e(message: String?, vararg args: Any?) {
        treeArray.forEach { it.e(message, *args) }
    }

    /** Log an error exception and a message with optional format args. */
    override fun e(t: Throwable?, message: String?, vararg args: Any?) {
        treeArray.forEach { it.e(t, message, *args) }
    }

    /** Log an error exception. */
    override fun e(t: Throwable?) {
        treeArray.forEach { it.e(t) }
    }

    override fun printlnStackTrace(tag: String?) {
        treeArray.forEach { it.printlnStackTrace(tag) }
    }

    /** Log an assert message with optional format args. */
    override fun wtf(message: String?, vararg args: Any?) {
        treeArray.forEach { it.wtf(message, *args) }
    }

    /** Log an assert exception and a message with optional format args. */
    override fun wtf(t: Throwable?, message: String?, vararg args: Any?) {
        treeArray.forEach { it.wtf(t, message, *args) }
    }

    /** Log an assert exception. */
    override fun wtf(t: Throwable?) {
        treeArray.forEach { it.wtf(t) }
    }

    /** Log at `priority` a message with optional format args. */
    override fun log(priority: Int, message: String?, vararg args: Any?) {
        treeArray.forEach { it.log(priority, message, *args) }
    }

    /** Log at `priority` an exception and a message with optional format args. */
    override fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        treeArray.forEach { it.log(priority, t, message, *args) }
    }

    /** Log at `priority` an exception. */
    override fun log(priority: Int, t: Throwable?) {
        treeArray.forEach { it.log(priority, t) }
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        throw AssertionError() // Missing override for log method.
    }

    /**
     * A view into Timber's planted trees as a tree itself. This can be used for injecting a logger
     * instance rather than using static methods or to facilitate testing.
     */
    @Suppress(
        "NOTHING_TO_INLINE", // Kotlin users should reference `Tree.Forest` directly.
        "NON_FINAL_MEMBER_IN_OBJECT" // For japicmp check.
    )
    @JvmStatic
    open inline fun asTree(): Tree = this

    /** Set a one-time tag for use on the next logging call. */
    fun tag(tag: String): Tree {
        for (tree in treeArray) {
            tree.explicitTag.set(tag)
        }
        return this
    }

    /** Add a new logging tree. */
    fun plant(tree: Tree) {
        require(tree !== this) { "Cannot plant Timber into itself." }
        synchronized(trees) {
            trees.add(tree)
            treeArray = trees.toTypedArray()
        }
    }

    /** Adds new logging trees. */
    fun plant(vararg trees: Tree) {
        for (tree in trees) {
            requireNotNull(tree) { "trees contained null" }
            require(tree !== this) { "Cannot plant Timber into itself." }
        }
        synchronized(this.trees) {
            Collections.addAll(this.trees, *trees)
            treeArray = this.trees.toTypedArray()
        }
    }

    /** Remove a planted tree. */
    fun uproot(tree: Tree) {
        synchronized(trees) {
            require(trees.remove(tree)) { "Cannot uproot tree which is not planted: $tree" }
            treeArray = trees.toTypedArray()
        }
    }

    /** Remove all planted trees. */
    fun uprootAll() {
        synchronized(trees) {
            trees.clear()
            treeArray = emptyArray()
        }
    }

    /** Return a copy of all planted [trees][Tree]. */
    fun forest(): List<Tree> {
        synchronized(trees) {
            return Collections.unmodifiableList(trees.toList())
        }
    }

    @get:[JvmStatic JvmName("treeCount")]
    val treeCount get() = treeArray.size

    // Both fields guarded by 'trees'.
    private val trees = ArrayList<Tree>()
    @Volatile private var treeArray = emptyArray<Tree>()
}