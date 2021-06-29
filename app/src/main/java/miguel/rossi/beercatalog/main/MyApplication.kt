package miguel.rossi.beercatalog.main

import android.app.Application

class MyApplication : Application() {

    private lateinit var _appComponent: AppComponent
    val appComponent: AppComponent
        get() = _appComponent

    override fun onCreate() {
        super.onCreate()
        setUpDagger()
    }

    private fun setUpDagger() {
        _appComponent = DaggerAppComponent.create()
    }
}
