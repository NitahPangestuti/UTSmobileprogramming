package id.rdev.catatanpenjualan.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Generated("com.robohorse.robopojogenerator")
data class Tas(

	@field:SerializedName("id_tas")
	val idTas: String? = null,

	@field:SerializedName("harga_beli")
	var hargaBeli: Double? = null,

	@field:SerializedName("nama_tas")
	var namaTas: String? = null,

	@field:SerializedName("kategori")
	var kategori: String? = null,

	@field:SerializedName("id_user")
	var idUser: String? = null,

	@field:SerializedName("stok")
	val stok: String? = null,

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("harga_jual")
	var hargaJual: Double? = null,

	@field:SerializedName("barcode")
	var barcode: String? = null
) : Serializable