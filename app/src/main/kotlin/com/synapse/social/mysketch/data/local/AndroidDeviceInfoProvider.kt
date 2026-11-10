package com.mysketch.data.local

import android.os.Build
import com.mysketch.domain.repository.DeviceInfoProvider
import javax.inject.Inject

class AndroidDeviceInfoProvider @Inject constructor() : DeviceInfoProvider {
    override fun getDeviceModel(): String = Build.MODEL
}
