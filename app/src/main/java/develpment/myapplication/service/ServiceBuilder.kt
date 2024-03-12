package develpment.myapplication.service

import develpment.myapplication.BuildConfig
import develpment.myapplication.utils.AuthorizationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * We create a builder of the retrofit object which can be reused for all method calls
 * declared in the RestApi interface.
 */
object ServiceBuilder {
    private val client =
        OkHttpClient.Builder().addInterceptor(AuthorizationInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL) // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

