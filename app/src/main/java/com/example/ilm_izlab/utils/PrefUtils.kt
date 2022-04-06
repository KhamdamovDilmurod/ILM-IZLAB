package com.example.ilm_izlab.utils

import android.location.Location
import android.net.Uri
import com.example.ilm_izlab.model.LocationModel
import com.example.ilm_izlab.model.response.LoginResponse
import com.example.ilm_izlab.model.response.RegistrationModel
import com.orhanobut.hawk.Hawk

object PrefUtils {
    const val PREF_TOKEN= "pref_token"
    const val PREF_USER= "pref_user"
    const val PREF_FCM_TOKEN= "pref_fcm_token"
    const val PREF_LOCATION= "pref_location"
    const val PREF_PROFIL_IMAGE= "pref_profil_image"

    fun setFCMToken(value: String){
        Hawk.put(PREF_FCM_TOKEN, value)
    }

    fun getFCMToken(): String{
        return Hawk.get(PREF_FCM_TOKEN,"")
    }
    fun setToken(value: String){
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String{
        return Hawk.get(PREF_TOKEN,"")
    }

    fun setUser(user: LoginResponse){
        Hawk.put(PREF_USER,user)
    }

    fun getUser(): LoginResponse?{
        return Hawk.get(PREF_USER)
    }

    fun setLocation(location: LocationModel){
        Hawk.put(PREF_LOCATION,location)
    }

    fun getLocation(): LocationModel?{
        return Hawk.get(PREF_LOCATION)
    }

    fun setProfilImage(profilImage: String?){
        Hawk.put(PREF_PROFIL_IMAGE,profilImage)
    }

    fun getProfilImage():String?{
        return Hawk.get(PREF_PROFIL_IMAGE)
    }

    fun clear(){
        Hawk.deleteAll()
    }
}