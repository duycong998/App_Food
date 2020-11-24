package com.sunasterisk.appfood.data.retrofit

class APIUtil {

    companion object {

        fun getData(): DataClient? {
            return ParseRetrofitWithJson.getClient(BASE_URL)?.create(DataClient::class.java)
        }
        private const val BASE_URL ="https://www.themealdb.com/api/json/v1/1/"
    }
}
