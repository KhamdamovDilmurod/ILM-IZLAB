package com.example.ilm_izlab.api

import com.example.ilm_izlab.model.*
import com.example.ilm_izlab.model.request.CheckPhoneRequest
import com.example.ilm_izlab.model.request.LoginRequest
import com.example.ilm_izlab.model.request.RegistrationRequest
import com.example.ilm_izlab.model.request.ResetPasswordRequest
import com.example.ilm_izlab.model.response.CheckPhoneResponse
import com.example.ilm_izlab.model.response.LoginResponse
import com.example.ilm_izlab.model.response.RegistrationModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface Api {

    @GET("offers")
    fun getOffers(): Observable<BaseResponse<List<OffersModel>>>

    @GET("categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @POST("training_centers")
    fun getTopTrainingCentres(@Body LCFilter: LCFilterModel): Observable<BaseResponse<List<TrainingCentersModel>>>

    @GET("course_teachers/{course_id}")
    fun getCourseTeachers(@Path("course_id") courseId: Int): Observable<BaseResponse<List<CourseTeachersModel>>>

    @GET("regions")
    fun getRegions(): Observable<BaseResponse<List<RegionsModel>>>

    @GET("get_news")
    fun getAllNews(): Observable<BaseResponse<List<AllNewsModel>>>

    @POST("make_rating")
    fun makeRating(@Body makeRatingRequest: MakeRatingRequest): Observable<BaseResponse<Any>>

    @GET("get_ratings/{center_id}")
    fun getRatings(@Path("center_id") centerId: Int): Observable<BaseResponse<List<RatingsModel>>>

    @POST("set_subscriber")
    fun setSubscriber(@Body request: HashMap<String, Any>): Observable<BaseResponse<Any>>

    @GET("check_subscriber/{center_id}")
    fun checkSubscriber(@Path("center_id") centerId: Int): Observable<BaseResponse<Boolean>>

    //---------------------------------------------
    @POST("check_phone")
    fun checkPhone(@Body checkPhone: CheckPhoneRequest): Observable<BaseResponse<CheckPhoneResponse>>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<BaseResponse<LoginResponse>>

    @POST("registration")
    fun register(@Body registrationRequest: RegistrationRequest): Observable<BaseResponse<RegistrationModel>>

    @POST("update_profile")
    fun updateProfile(@Body fullname: HashMap<String, Any>): Observable<BaseResponse<LoginResponse>>

    @Multipart
    @POST("update_profile")
    fun updateAvatar(
        @Part file: MultipartBody.Part?,
        @Part fullname: MultipartBody.Part,
    ): Observable<BaseResponse<LoginResponse>>

    @GET("user")
    fun getUser():Observable<BaseResponse<LoginResponse>>

    @POST("reset_password")
    fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Observable<BaseResponse<LoginResponse>>

    @POST("send_confirm_code")
    fun sendConfirmCode(@Body phone: String): Observable<BaseResponse<CheckPhoneResponse>>

}