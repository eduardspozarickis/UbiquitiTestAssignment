package lv.eduardspozarickis.ubiquititestassignment

import android.app.Application
import lv.eduardspozarickis.ubiquititestassignment.utils.databaseModule
import lv.eduardspozarickis.ubiquititestassignment.utils.networkModule
import lv.eduardspozarickis.ubiquititestassignment.utils.repositoryModule
import lv.eduardspozarickis.ubiquititestassignment.utils.storeModule
import lv.eduardspozarickis.ubiquititestassignment.utils.useCaseModule
import lv.eduardspozarickis.ubiquititestassignment.utils.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class UbiquitiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@UbiquitiApplication)
            modules(
                networkModule(
                    baseUrl = BuildConfig.BASE_URL,
                    isLogEnabled = BuildConfig.DEBUG
                ),
                databaseModule,
                storeModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
            )
        }
    }
}