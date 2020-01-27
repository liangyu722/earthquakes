package com.github.liangyu.earthquakes.data.networking

import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.data.networking.response.EarthquakeResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

object EarthquakeResponseConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type, annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {

        return if (type == EarthquakeResponse::class.java) {
            EarthquakeConverter
        } else {
            null
        }
    }

    private object EarthquakeConverter : Converter<ResponseBody, EarthquakeResponse> {

        private val parser: JsonParser = JsonParser()

        override fun convert(value: ResponseBody): EarthquakeResponse {
            val body = value.string()
            return if (body.isEmpty()) {
                EarthquakeResponse(emptyList())
            } else {
                val earthquakes = parser.parse(body).asJsonObject
                val listOfEarthquake = earthquakes.get("earthquakes").asJsonArray.map {
                    val jsonObj: JsonObject = it.asJsonObject
                    EarthquakeEntity(
                        eqid = jsonObj.get("eqid").asString,
                        datetime = jsonObj.get("datetime").asString,
                        depth = jsonObj.get("depth").asDouble,
                        lat = jsonObj.get("lat").asDouble,
                        lng = jsonObj.get("lng").asDouble,
                        magnitude = jsonObj.get("magnitude").asDouble,
                        src = jsonObj.get("src").asString
                    )
                }
                EarthquakeResponse(listOfEarthquake)
            }
        }
    }
}
