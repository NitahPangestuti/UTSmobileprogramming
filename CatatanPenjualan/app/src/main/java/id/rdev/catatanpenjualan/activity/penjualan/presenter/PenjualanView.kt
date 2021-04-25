package id.rdev.catatanpenjualan.activity.penjualan.presenter

import id.rdev.catatanpenjualan.model.Tas
import id.rdev.catatanpenjualan.model.Keranjang

interface PenjualanView {
    fun onSuccessGetKeranjang(keranjang: List<Keranjang?>?)
    fun onFailedGetKeranjang(msg: String?)

    fun onSuccessSearchItem(tass: List<Tas?>?)
    fun onFailedSearchItem(msg : String?)

    fun onSuccessAddItemToKeranjang(msg: String?)
    fun onFailedAddItemToKeranjang(msg: String?)

    fun onSuccessDeleteItemKeranjang(msg: String?)
    fun onFailedDeleteItemKeranjang(msg: String?)

    fun onSuccessJualTas(msg: String?)
    fun onFailedJualTas(msg: String?)

    fun onSuccessAddKeranjang(msg: String?)
    fun onFailedAddKeranjang(msg: String?)
}