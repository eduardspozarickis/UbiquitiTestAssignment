package lv.eduardspozarickis.ubiquititestassignment.utils

import android.content.Context
import lv.eduardspozarickis.ubiquititestassignment.data.local.AppDatabase
import lv.eduardspozarickis.ubiquititestassignment.data.local.LocalDataStore
import lv.eduardspozarickis.ubiquititestassignment.data.local.provideDatabase
import lv.eduardspozarickis.ubiquititestassignment.data.remote.Api
import lv.eduardspozarickis.ubiquititestassignment.data.remote.provideHttpClient
import lv.eduardspozarickis.ubiquititestassignment.data.remote.provideRetrofit
import lv.eduardspozarickis.ubiquititestassignment.domain.ProductsRepository
import lv.eduardspozarickis.ubiquititestassignment.domain.usecase.FetchProductsListUseCase
import lv.eduardspozarickis.ubiquititestassignment.feature.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun networkModule(
    baseUrl: String,
    isLogEnabled: Boolean,
) = module {
    single { provideHttpClient(isLogEnabled) }
    single { provideRetrofit(baseUrl, get()).create(Api::class.java) }
}

val databaseModule = module {
    single { provideDatabase(androidContext()) }
    single { get<AppDatabase>().productDao() }
    single<android.content.SharedPreferences>  {
        androidContext().getSharedPreferences(
            "ubiquiti_prefs",
            Context.MODE_PRIVATE
        )
    }
}

val storeModule = module {
    single { LocalDataStore(get(), get()) }
}

val repositoryModule = module {
    single { ProductsRepository(get(), get()) }
}

val useCaseModule = module {
    single { FetchProductsListUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}