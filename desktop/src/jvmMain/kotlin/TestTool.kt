import timber.multiplatform.log.CustomTagTree

class TestTool {
    private val customTagTree = CustomTagTree("hihihihi",TestTool::class.java.name)
    fun i(message:String){
        customTagTree.i(message)
    }
}