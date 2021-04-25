package id.rdev.catatanpenjualan.activity.data_tas.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import id.rdev.catatanpenjualan.model.Tas

@Generated("com.robohorse.robopojogenerator")
data class ResultDataTas(

    @field:SerializedName("tas")
	val tas: List<Tas?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Int? = null
)