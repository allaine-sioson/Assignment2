package sheridan.siosona.assignment2

import android.app.Application
import sheridan.siosona.assignment2.data.AppContainer
import sheridan.siosona.assignment2.data.DefaultAppContainer

class ProfileDataApplication: Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}