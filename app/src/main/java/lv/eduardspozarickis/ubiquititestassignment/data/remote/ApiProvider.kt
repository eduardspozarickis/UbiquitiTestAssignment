package lv.eduardspozarickis.ubiquititestassignment.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideHttpClient(
    isLogEnabled: Boolean,
): OkHttpClient {
    return OkHttpClient.Builder()
        .addLoggingInterceptor(isLogEnabled)
        .build()
}

fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun OkHttpClient.Builder.addLoggingInterceptor(isLogEnabled: Boolean) = apply {
    if (!isLogEnabled) {
        return@apply
    }
    val loggingInterceptor = HttpLoggingInterceptor { message -> println(message) }
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    addInterceptor(loggingInterceptor)
}
