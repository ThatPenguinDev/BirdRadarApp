package com.example.loginpage
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class Observation(
    val lat: Double,
    val lng: Double,
    val comName: String
)

interface EBirdService {
    @GET("data/obs/geo/recent")
    fun getRecentObservations(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("dist") distance: Int,
        @Header("X-eBirdApiToken") apiKey: String
    ): Call<List<Observation>>
}

class BirdFragment : Fragment() {

    private lateinit var eBirdSightings: List<Observation>
    private val apiKey = "pupv1pi6f4dh"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.ebird.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val eBirdService = retrofit.create(EBirdService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_bird, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Make a network request to fetch eBird data
        val call = eBirdService.getRecentObservations(
            latitude = 40.7128, // Replace with your latitude
            longitude = -74.0060, // Replace with your longitude
            distance = 10, // Replace with the desired distance
            apiKey = apiKey
        )

        call.enqueue(object : Callback<List<Observation>> {
            override fun onResponse(call: Call<List<Observation>>, response: Response<List<Observation>>) {
                if (response.isSuccessful) {
                    eBirdSightings = response.body() ?: emptyList()
                    val adapter = EBirdSightingAdapter(eBirdSightings.map {
                        EBirdSighting(
                            speciesName = it.comName,
                            location = "Location", // You can set the location accordingly
                            date = "Date" // You can set the date accordingly
                        )
                    })
                    recyclerView.adapter = adapter
                } else {
                    // Handle API response error
                }
            }

            override fun onFailure(call: Call<List<Observation>>, t: Throwable) {
                // Handle network request failure
            }
        })

        return rootView
    }
}
