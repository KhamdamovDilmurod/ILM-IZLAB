package com.example.ilm_izlab.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ilm_izlab.api.repository.TrainingCentersRepository
import com.example.ilm_izlab.model.*
import com.example.ilm_izlab.model.request.CheckPhoneRequest
import com.example.ilm_izlab.model.request.LoginRequest
import com.example.ilm_izlab.model.request.RegistrationRequest
import com.example.ilm_izlab.model.response.CheckPhoneResponse
import com.example.ilm_izlab.model.response.LoginResponse
import com.example.ilm_izlab.model.response.RegistrationModel
import com.example.ilm_izlab.utils.PrefUtils
import okhttp3.MultipartBody


class MainViewModel : ViewModel() {

    private val repository = TrainingCentersRepository()

    val error = MutableLiveData<String>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val progress = MutableLiveData<Boolean>()
    val topTrainingCentresData = MutableLiveData<List<TrainingCentersModel>>()
    val nearTrainingCentresData = MutableLiveData<List<TrainingCentersModel>>()
    val offersData = MutableLiveData<List<OffersModel>>()
    val courseTeachersData = MutableLiveData<List<CourseTeachersModel>>()
    val regionsData = MutableLiveData<List<RegionsModel>>()
    val allNewsData = MutableLiveData<List<AllNewsModel>>()
    val makeRatingData = MutableLiveData<Any>()
    val getRatingsData = MutableLiveData<List<RatingsModel>>()
    val setSubscriberData = MutableLiveData<Any>()
    val checkSubscriberData = MutableLiveData<Boolean>()

    //--------------------------------------------
    val checkPhoneData = MutableLiveData<CheckPhoneResponse>()
    val registrationData = MutableLiveData<RegistrationModel>()
    val confirmData = MutableLiveData<LoginResponse>()
    val loginData = MutableLiveData<LoginResponse>()
    val updateData = MutableLiveData<LoginResponse>()
    val userData = MutableLiveData<LoginResponse>()

    fun getOffers() {
        repository.getOffers(error, offersData)
    }

    fun getCategories() {
        repository.getCategories(error, progress, categoriesData)
    }

    fun getTopCenters(filter: LCFilterModel) {
        repository.getTrainingCentres(
            filter, error, progress, topTrainingCentresData
        )
    }

    fun getNearCenters(
        filter: LCFilterModel = LCFilterModel(
            0,
            0,
            0,
            0,
            "",
            "distance",
            40,
            PrefUtils.getLocation()?.latitude ?: 0.0,
            PrefUtils.getLocation()?.longitude ?: 0.0,
            false
        )
    ) {
        repository.getTrainingCentres(
            filter, error, progress, nearTrainingCentresData
        )
    }

    fun getCourseTeachers(id: Int) {
        repository.getCourseTeachers(id, error, progress, courseTeachersData)
    }

    fun getRegions() {
        repository.getRegions(error, regionsData)
    }

    fun getAllNews() {
        repository.getAllNews(error, progress, allNewsData)
    }

    fun makeRating(makeRatingRequest: MakeRatingRequest) {
        repository.makeRating(
            makeRatingRequest, error, makeRatingData
        )
    }

    fun getRatings(centerId: Int) {
        repository.getRatings(centerId, error, progress, getRatingsData)
    }

    fun setSubscriber(centerId: Int) {
        repository.setSubscriber(
            centerId, error, setSubscriberData
        )
    }

    fun checkSubscriber(centerId: Int) {
        repository.checkSubscriber(centerId, error, progress, checkSubscriberData)
    }

    // --------------->>> Sign in

    fun checkPhone(phone: CheckPhoneRequest) {
        repository.checkPhone(phone, error, progress, checkPhoneData)
    }

    fun login(loginRequest: LoginRequest) {
        repository.login(loginRequest, error, progress, loginData)
    }

    fun registration(registrationRequest: RegistrationRequest) {
        repository.registration(registrationRequest, error, progress, registrationData)
    }

    fun updateProfile(fullName: String) {
        repository.updateProfile(
            fullName, error, progress, loginData
        )
    }

    fun updateAvatar(file: MultipartBody.Part?, fullname: MultipartBody.Part) {
        repository.updateAvatar(
            file, fullname, error, progress, updateData
        )
    }

    fun getUser() {
        repository.getUser(
            error, userData
        )
    }


}