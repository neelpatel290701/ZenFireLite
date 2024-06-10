package com.example.zenfirelite.apis

import com.example.zenfirelite.apis.datamodels.CustomerListRequestBody
import com.example.zenfirelite.apis.datamodels.CustomerListResponse
import com.example.zenfirelite.apis.datamodels.CustomerListResult
import com.example.zenfirelite.apis.datamodels.InspectionListRequestBody
import com.example.zenfirelite.apis.datamodels.InspectionListResponse
import com.example.zenfirelite.apis.datamodels.UserAuthRequestBody
import com.example.zenfirelite.apis.datamodels.UserAuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Content-Type:application/json; charset=UTF-8",
             "request-from:ZENFIRE_LITE",
             "user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36")
    @POST("auth/login")
    fun userAuth(
        @Body userAuth: UserAuthRequestBody
    ) : Call<UserAuthResponse>


    @Headers("Content-Type:application/json; charset=UTF-8",
             "request-from:ZENFIRE_LITE")
    @POST("zenfirelite/inspections/list")
    fun inspectionList(
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
        @Body inspectionListRequest : InspectionListRequestBody
    ) : Call<InspectionListResponse>


    @Headers("request-from:ZENFIRE_LITE",
             "timezone-offset:-330",
             "timezonecode:IST",
             "timezonename:Asia/Calcutta")
    @POST("customer/getServiceAddresses")
    fun customerList(
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
        @Query("timestamp") timestamp :Long,
        @Body customerListRequest : CustomerListRequestBody
    ):Call<CustomerListResponse>
}