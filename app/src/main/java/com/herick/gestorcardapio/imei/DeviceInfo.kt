package com.herick.gestorcardapio.imei

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import java.io.IOException

object DeviceInfo {

    fun getAdvertisingId(context: Context): String? {
        try {
            val adInfo: AdvertisingIdClient.Info = AdvertisingIdClient.getAdvertisingIdInfo(context)
            val advertisingId: String? = adInfo.id
            return advertisingId
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        }
        return ""
    }
}
