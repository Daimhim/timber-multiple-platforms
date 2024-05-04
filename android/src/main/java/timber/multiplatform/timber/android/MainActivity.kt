package timber.multiplatform.timber.android

import android.app.Activity
import android.os.Bundle
import timber.multiplatform.log.CustomTagTree
import timber.multiplatform.log.DebugTree
import timber.multiplatform.log.Timber

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(DebugTree())
        Timber.i("Hi!")

        val customTagTree = CustomTagTree("hihihihi")
        customTagTree.i("CustomTagTree Hi!")

        val testTool = TestTool()
        testTool.i("TestTool i")
    }


}