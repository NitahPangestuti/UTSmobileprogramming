package id.rdev.catatanpenjualan.network

import id.rdev.catatanpenjualan.activity.data_tas.data.ResultDataTas
import id.rdev.catatanpenjualan.activity.login.data.ResultLogin
import id.rdev.catatanpenjualan.activity.penjualan.data.ResultKeranjang
import id.rdev.catatanpenjualan.activity.penjualan.data.ResultSearchTas
import id.rdev.catatanpenjualan.activity.report.detail.data.ResultItemKeranjang
import id.rdev.catatanpenjualan.response.ResultSimple
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CatatanPenjualanService {


    /***
     *
     * Report
     *
     */

    @FormUrlEncoded
    @POST("getLastDay")
    fun getReportLastDay(@Field("id_user") id_user: Int?,
                         @Field("status") status: String) : Call<ResultKeranjang>
    @FormUrlEncoded
    @POST("getLastWeek")
    fun getReportLastWeek(@Field("id_user") id_user: Int?,
                          @Field("status") status: String) : Call<ResultKeranjang>
    @FormUrlEncoded
    @POST("getLastMonth")
    fun getReportLastMonth(@Field("id_user") id_user: Int?,
                           @Field("status") status: String) : Call<ResultKeranjang>
    @FormUrlEncoded
    @POST("getNowDay")
    fun getReportNowDay(@Field("id_user") id_user: Int?,
                        @Field("status") status: String) : Call<ResultKeranjang>
    @FormUrlEncoded
    @POST("getNowWeek")
    fun getReportNowWeek(@Field("id_user") id_user: Int?,
                         @Field("status") status: String) : Call<ResultKeranjang>
    @FormUrlEncoded
    @POST("getNowMonth")
    fun getReportNowMonth(@Field("id_user") id_user: Int?,
                          @Field("status") status: String) : Call<ResultKeranjang>


    /***
     * Keranjang
     */

    @FormUrlEncoded
    @POST("getKeranjang")
    fun getKeranjang(@Field("status") status: String,
                     @Field("id_user") id_user: Int?
    ) : Call<ResultKeranjang>

    @FormUrlEncoded
    @POST("searchTas")
    fun searchTas(@Field("keyword") keyword: String,
                     @Field("id_user") id_user: Int?) : Call<ResultSearchTas>

    @FormUrlEncoded
    @POST("addItemToKeranjang")
    fun addItemToKeranjang(@Field("id_user") id_user: Int?,
                           @Field("id_keranjang") id_keranjang: Int?,
                           @Field("id_tas") id_tas: Int?,
                           @Field("nama_tas) nama_tas: String?,
                           @Field("qty") qty: Int?,
                           @Field("harga_beli") harga_beli: Double?,
                           @Field("harga_jual") harga_jual: Double?) : Call<ResultSimple>

    @FormUrlEncoded
    @POST("deleteItemKeranjang")
    fun deleteItemKeranjang(@Field("id_user") id_user: Int?,
                            @Field("id_keranjang") id_keranjang: Int?,
                            @Field("id_tas") id_tas: Int?,
                            @Field("id_penjualan") id_penjualan: Int?) : Call<ResultSimple>

    @FormUrlEncoded
    @POST("jualTas")
    fun jualTas(@Field("id_user") id_user: Int?,
                   @Field("id_keranjang") id_keranjang: Int?,
                   @Field("status") status: String?,
                   @Field("qty") qty: Int?,
                   @Field("total_harga") total_harga: Double?
    ) : Call<ResultSimple>


    @FormUrlEncoded
    @POST("addKeranjang")
    fun addKeranjang(@Field("id_user") id_user: Int?,
                     @Field("status") status : String?) : Call<ResultSimple>

    @FormUrlEncoded
    @POST("getReport")
    fun getReport(@Field("id_user") id_user: Int?) : Call<ResultKeranjang>

    @FormUrlEncoded
    @POST("getItemKeranjang")
    fun getItemKeranjang(@Field("id_user") id_user: Int?,
                         @Field("id_keranjang") id_keranjang : Int?) : Call<ResultItemKeranjang>


    /****
     * Data Tas CRUD
     */

    @FormUrlEncoded
    @POST("getDataTas")
    fun getDataTas(@Field("id_user") id_user: Int?) : Call<ResultDataTas>

    @FormUrlEncoded
    @POST("addTas")
    fun addTas(@Field("id_user") id_user: Int?,
                  @Field("barcode") barcode: String?,
                  @Field("nama_tas") nama_tas: String?,
                  @Field("kategori") karegori: String?,
                  @Field("harga_beli") harga_beli: Double?,
                  @Field("harga_jual") harga_jual: Double?) : Call<ResultSimple>

    @FormUrlEncoded
    @POST("updateTas")
    fun updateTas(@Field("id_user") id_user: Int?,
                     @Field("id_tas") id_tas: Int?,
                     @Field("barcode") barcode: String?,
                     @Field("nama_tas") nama_tas: String?,
                     @Field("kategori") karegori: String?,
                     @Field("harga_beli") harga_beli: Double?,
                     @Field("harga_jual") harga_jual: Double?) : Call<ResultSimple>

    @FormUrlEncoded
    @POST("deleteTas")
    fun deleteTas(@Field("id_user") id_user: Int?,
                     @Field("id_tas") id_tas: Int?,
                     @Field("nama_tas") nama_tas: String?) : Call<ResultSimple>

    /*****
     *
     * User Service For Login Register
     *
     */

    @FormUrlEncoded
    @POST("registerUser")
    fun registerUser (@Field("username") username : String?,
                      @Field("email") email : String?,
                      @Field("password") password : String?,
                      @Field("hp") hp : String?) : Call<ResultSimple>

    @FormUrlEncoded
    @POST("loginUser")
    fun loginUser (@Field("username") username : String?,
                   @Field("password") password : String?) : Call<ResultLogin>
}