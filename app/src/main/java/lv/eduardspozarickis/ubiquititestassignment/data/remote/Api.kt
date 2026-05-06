package lv.eduardspozarickis.ubiquititestassignment.data.remote

import lv.eduardspozarickis.ubiquititestassignment.data.remote.entity.Devices
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("/fingerprint/ui/public.json")
    suspend fun getProducts(): Response<Devices>
}