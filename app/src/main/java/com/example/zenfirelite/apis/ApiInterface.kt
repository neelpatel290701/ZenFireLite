package com.example.zenfirelite.apis

import com.example.zenfirelite.apis.datamodels.AddCustomerRequestBody
import com.example.zenfirelite.apis.datamodels.AddCustomerResponse
import com.example.zenfirelite.apis.datamodels.BussinessInformationResponse
import com.example.zenfirelite.apis.datamodels.CustomerListRequestBody
import com.example.zenfirelite.apis.datamodels.CustomerListResponse
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_RequestBody
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.apis.datamodels.FormTemplatesListResponse
import com.example.zenfirelite.apis.datamodels.InspectionListRequestBody
import com.example.zenfirelite.apis.datamodels.InspectionListResponse
import com.example.zenfirelite.apis.datamodels.TicketFormsResponse
import com.example.zenfirelite.apis.datamodels.TechnicianListRequestBody
import com.example.zenfirelite.apis.datamodels.TechnicianListResponse
import com.example.zenfirelite.apis.datamodels.TicketFormsRequestBody
import com.example.zenfirelite.apis.datamodels.UserAuthRequestBody
import com.example.zenfirelite.apis.datamodels.UserAuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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


    @Headers("Content-Type:application/json",
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


    @Headers("request-from:ZENFIRE_LITE",
        "timezone-offset:-330",
        "timezonecode:IST",
        "timezonename:Asia/Calcutta",
        "user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36")
    @POST("customer/getCustomers")
    fun getCustomerListWithBillingService(
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
        @Body customerListRequestServiceBilling : CustomerList_ServiceBilling_RequestBody
    ) : Call<CustomerList_ServiceBilling_Response>

    @Headers("Content-Type:application/json; charset=UTF-8",
        "request-from:ZENFIRE_LITE",
        "timezone-offset:-330",
        "timezonecode:IST",
        "timezonename:Asia/Calcutta")
    @POST("customer")
    fun addCustomer(
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
        @Body customerDetails : AddCustomerRequestBody
    ): Call<AddCustomerResponse>


    @Headers("request-from:ZENFIRE_LITE")
    @GET("company")
    fun getBussinessInfo(
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
        @Query("timestamp") timestamp :Long
    ) : Call<BussinessInformationResponse>


    @Headers("Content-Type:application/json; charset=UTF-8",
             "request-from:ZENFIRE_LITE")
    @GET("common/fp/formtemplatecf/list")
    fun getFormTemplates(
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
    ) : Call<FormTemplatesListResponse>

    @Headers("Content-Type:application/json",
             "request-from:ZENFIRE_LITE")
    @POST("common/fp/forms/filter")
    fun getPreviousForms(
        @Query("sortBy[]") sortBy: String,
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
        @Body ticketId : TicketFormsRequestBody
    ) : Call<TicketFormsResponse>


    @Headers("Content-Type:application/json",
             "request-from:ZENFIRE_LITE")
    @POST("common/staticlists")
    fun getTechnicianList(
        @Header("user-id") userId : String,
        @Header("access-token") accessToken:String,
        @Header("company-id") companyId : String,
        @Body technicianIds : TechnicianListRequestBody
    ) : Call<TechnicianListResponse>
}