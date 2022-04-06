package com.example.ilm_izlab.api.repository

import androidx.lifecycle.MutableLiveData
import com.example.ilm_izlab.api.ApiService
import com.example.ilm_izlab.model.*
import com.example.ilm_izlab.model.request.CheckPhoneRequest
import com.example.ilm_izlab.model.request.LoginRequest
import com.example.ilm_izlab.model.request.RegistrationRequest
import com.example.ilm_izlab.model.response.CheckPhoneResponse
import com.example.ilm_izlab.model.response.LoginResponse
import com.example.ilm_izlab.model.response.RegistrationModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class TrainingCentersRepository {
    val compositeDisposable = CompositeDisposable()

    fun getOffers(error: MutableLiveData<String>, success: MutableLiveData<List<OffersModel>>){
        compositeDisposable.add(
            ApiService.apiClient().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<OffersModel>>>(){
                    override fun onNext(t: BaseResponse<List<OffersModel>>) {
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    fun getCategories(error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<CategoryModel>>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<CategoryModel>>>(){
                    override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getTrainingCentres(filter: LCFilterModel, error: MutableLiveData<String>,progress: MutableLiveData<Boolean>, success: MutableLiveData<List<TrainingCentersModel>>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getTopTrainingCentres(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<TrainingCentersModel>>>(){
                    override fun onNext(t: BaseResponse<List<TrainingCentersModel>>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

    fun getCourseTeachers(id: Int, error: MutableLiveData<String>,progress: MutableLiveData<Boolean>, success: MutableLiveData<List<CourseTeachersModel>>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getCourseTeachers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<CourseTeachersModel>>>(){
                    override fun onNext(t: BaseResponse<List<CourseTeachersModel>>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getRegions(error: MutableLiveData<String>, success: MutableLiveData<List<RegionsModel>>){
        compositeDisposable.add(
            ApiService.apiClient().getRegions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<RegionsModel>>>(){
                    override fun onNext(t: BaseResponse<List<RegionsModel>>) {
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    fun getAllNews(error: MutableLiveData<String>,progress: MutableLiveData<Boolean>, success: MutableLiveData<List<AllNewsModel>>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getAllNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<AllNewsModel>>>(){
                    override fun onNext(t: BaseResponse<List<AllNewsModel>>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }
                })
        )
    }
    fun makeRating(makeRatingRequest: MakeRatingRequest, error: MutableLiveData<String>, success: MutableLiveData<Any>){
        compositeDisposable.add(
            ApiService.apiClient().makeRating(makeRatingRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<Any>>(){
                    override fun onNext(t: BaseResponse<Any>) {
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

    fun getRatings(centerId: Int,
                   error: MutableLiveData<String>,
                   progress: MutableLiveData<Boolean>,
                   success: MutableLiveData<List<RatingsModel>>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getRatings(centerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<RatingsModel>>>(){
                    override fun onNext(t: BaseResponse<List<RatingsModel>>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    fun setSubscriber(centerId: Int, error: MutableLiveData<String>, success: MutableLiveData<Any>){
        compositeDisposable.add(
            ApiService.apiClient().setSubscriber(hashMapOf(
                "center_id" to centerId
            ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<Any>>(){
                    override fun onNext(t: BaseResponse<Any>) {
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }

                })
        )
    }


    fun checkSubscriber(centerId: Int,error: MutableLiveData<String>,progress: MutableLiveData<Boolean>, success: MutableLiveData<Boolean>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().checkSubscriber(centerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<Boolean>>(){
                    override fun onNext(t: BaseResponse<Boolean>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    //--------------->> Sign in
    fun checkPhone(phone: CheckPhoneRequest, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<CheckPhoneResponse>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().checkPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<CheckPhoneResponse>>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<CheckPhoneResponse>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun login(loginRequest: LoginRequest, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<LoginResponse>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<LoginResponse>>(){
                    override fun onComplete() {

                    }
                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }
                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun registration(registrationRequest: RegistrationRequest, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<RegistrationModel>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().register(registrationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<RegistrationModel>>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<RegistrationModel>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun updateProfile(fullname:String, error: MutableLiveData<String>,progress: MutableLiveData<Boolean>, success: MutableLiveData<LoginResponse>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().updateProfile(
                hashMapOf("fullname" to fullname)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<LoginResponse>>(){
                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                        progress.value = false
                    }

                })
        )
    }

    fun updateAvatar(file: MultipartBody.Part?, fullname: MultipartBody.Part, error: MutableLiveData<String>,progress: MutableLiveData<Boolean>, success: MutableLiveData<LoginResponse>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().updateAvatar(file, fullname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<LoginResponse>>(){
                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

    fun getUser(error: MutableLiveData<String>, success: MutableLiveData<LoginResponse>){
        compositeDisposable.add(
            ApiService.apiClient().getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<LoginResponse>>(){
                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        if(t.success){
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

}