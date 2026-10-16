package sheridan.siosona.assignment2.network

import sheridan.siosona.assignment2.data.RemoteData
import retrofit2.http.GET

interface ProfileDataApi {
    @GET("ballet.json")
    suspend fun getRemoteData(): RemoteData
}