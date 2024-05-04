import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import timber.multiplatform.log.CustomTagTree
import timber.multiplatform.log.DebugTree
import timber.multiplatform.log.Timber


fun main() = application {
    Timber.plant(DebugTree())
    Timber.i("Hi!")
    test()
    testCustomTagTree()

    val testTool = TestTool()
    testTool.i("TestTool i")
    Window(onCloseRequest = ::exitApplication) {

    }
}

fun test(){
    Timber.i("Hi22!")
}

fun testCustomTagTree(){
    CustomTagTree("456").i("testCustomTagTree")
}
