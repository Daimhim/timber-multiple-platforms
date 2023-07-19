import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import timber.multiplatform.log.DebugTree
import timber.multiplatform.log.Timber


fun main() = application {
    Timber.plant(DebugTree())
    Timber.i("Hi!")
    Window(onCloseRequest = ::exitApplication) {

    }
}
