package id.rdev.catatanpenjualan.activity.penjualan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import id.rdev.catatanpenjualan.R
import id.rdev.catatanpenjualan.activity.penjualan.adapter.PenjualanAdapter
import id.rdev.catatanpenjualan.activity.penjualan.presenter.PenjualanPresenter
import id.rdev.catatanpenjualan.activity.penjualan.presenter.PenjualanView
import id.rdev.catatanpenjualan.activity.report.adapter.ReportAdapter
import id.rdev.catatanpenjualan.base.BaseActivity
import id.rdev.catatanpenjualan.model.Barang
import id.rdev.catatanpenjualan.model.Keranjang
import id.rdev.catatanpenjualan.model.KeranjangStatus
import id.rdev.catatanpenjualan.model.Penjualan
import id.rdev.catatanpenjualan.utils.Uang
import kotlinx.android.synthetic.main.activity_penjualan_tas.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PenjualanTas : BaseActivity(), PenjualanView {
    private lateinit var penjualanPresenter : PenjualanPresenter

    // list keranjang yang ada
    private var listKeranjang : List<Keranjang?>? = null
    // list keranjang yang sedang aktif / dipilih
    private var keranjangNow : Keranjang? = null
    private var keranjangIndex : Int = 0

    private var totalQty : Int = 0
    private var totalHarga : Double = 0.0

    // tas yang dipilih saat pencarian
    private var tasNow : Tas? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        cekSesi(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penjualan_tas)

        penjualanPresenter = PenjualanPresenter(this)

        setAction()
        getKeranjang()
    }

    private fun getKeranjang() {
        penjualanPresenter.getKeranjang(KeranjangStatus.PENDING.status, user?.idUser)
    }

    override fun onSuccessGetKeranjang(keranjang: List<Keranjang?>?) {
        listKeranjang = keranjang
        keranjangNow = listKeranjang?.get(keranjangIndex)

        val penjualan = keranjangNow?.penjualan

        rvKeranjangTas.adapter = PenjualanAdapter(penjualan, object : PenjualanAdapter.OnDelete{
            override fun click(penjualan: Penjualan?) {
                penjualanPresenter.deleteItemKeranjang(penjualan)
            }
        })
        // hitung total total qty dan harga
        if (penjualan != null) {
            totalQty = 0
            totalHarga = 0.0
            for (i in penjualan.iterator())
            {
                totalQty += i?.qty ?: 0
                totalHarga += i?.qty?.let { i.hargaJual?.times(it) } ?:  0.0
            }
            tvKeranjangTotalQty.text = "$totalQty"
            tvKeranjangTotalHarga.text = "${Uang.indonesia(totalHarga)}"
        }

        // set keranjang status
        tvKeranjangNow.text = keranjang?.size.toString()
        rvKeranjang.adapter = ReportAdapter(listKeranjang, object : ReportAdapter.OnClick {
            override fun restore(keranjang: Keranjang?) {}
            override fun click(keranjang: Keranjang?, position: Int) {
                keranjangIndex = position
                getKeranjang()
                rvKeranjang.visibility = View.GONE
            }
        })
    }

    private fun moveToKeranjang() {
        val qtys = etQty.text.toString()

        if (qtys.isNotBlank()) {
            val qty = qtys.toInt()
            penjualanPresenter.addItemToKeranjang(qty, tasNow, keranjangNow)
        } else {
            toast("Qty wajib diisi").show()
        }
    }

    override fun onFailedGetKeranjang(msg: String?) {
        Log.d("PenjualanTas", msg ?: "")
    }

    // search
    private fun setAction() {
        // saat mengetik di form search
        etSearchTas.addTextChangedListener {
            val input = etSearchTas.text.toString()

            if (input == "") {
                tasNow = null
                changeVisibility(View.GONE)
            } else {
                doAsync {
                    penjualanPresenter.searcTas(input, user?.idUser)
                }
            }
        }

        // saat klik tombol +
        btAddToKeranjang.onClick {
            if (tasNow != null)
            moveToKeranjang()
        }

        btBayar.setOnClickListener {
            if (totalQty > 0) {
                alert {
                    title ="Aksi"
                    message = "Bayar transaksi ?"
                    yesButton {
                        penjualanPresenter.jualTas(user?.idUser, Integer.parseInt(keranjangNow?.idKeranjang), KeranjangStatus.TERJUAL.status,
                            totalQty, totalHarga)
                    }
                    noButton {  }
                }.show()
            } else {
                toast("Keranjang kosong").show()
            }
        }

        imKeranjangNow.setOnClickListener {
            rvKeranjang.visibility = if (rvKeranjang.visibility == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        imKeranjangAdd.setOnClickListener {
            penjualanPresenter.addKeranjang(user?.idUser)
        }

        imKeranjangDelete.setOnClickListener {
            if (listKeranjang?.size ?: 0 > 1) {
                alert {
                    title ="Aksi"
                    message = "Hapus keranjang ?\nTotal: "+ Uang.indonesia(keranjangNow?.totalHarga ?: 0.0)
                    yesButton {
                        penjualanPresenter.jualTas(user?.idUser, Integer.parseInt(keranjangNow?.idKeranjang), KeranjangStatus.TERHAPUS.status,
                            totalQty, totalHarga)
                    }
                    noButton {  }
                }.show()
            } else {
                toast("Tidak bisa menghapus keranjang sisa 1").show()
            }
        }
    }

    private fun changeVisibility(v : Int) {
        lvResultSearch.visibility = v
    }


    override fun onSuccessSearchItem(tass: List<Tas?>?) {
        // biar nama aja yang tampil
        val onlyName = tass?.map {
            it?.namaTas
        }

        // kalau satu langsung masukin ke berangNow
        if (tass?.size == 1) {
            tasNow = tass[0]
        }

        changeVisibility(View.VISIBLE)
        lvResultSearch.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, onlyName)

        // kalau klik item hasil search
        lvResultSearch.setOnItemClickListener { _, _, position, _ ->
            tasNow = tass?.get(position)
            etSearchTas.setText(tasNow?.barcode)
            etQty.requestFocus()
        }
    }
    override fun onFailedSearchItem(msg: String?) {
        changeVisibility(View.GONE)
    }

    // implement penjualanview
    override fun onSuccessAddItemToKeranjang(msg: String?) {
        getKeranjang()
        etSearchTas.setText("")
        etQty.setText("1")
        changeVisibility(View.GONE)
    }
    override fun onFailedAddItemToKeranjang(msg: String?) {
        Log.d("PenjualanTas", msg ?: "")
    }
    override fun onSuccessDeleteItemKeranjang(msg: String?) {
        getKeranjang()
    }
    override fun onFailedDeleteItemKeranjang(msg: String?) {
        Log.d("PenjualanTas", msg ?: "")
    }
    override fun onSuccessJualTas(msg: String?) {
        keranjangIndex = 0
        getKeranjang()
    }
    override fun onFailedJualTas(msg: String?) {
        Log.d("PenjualanTas", msg ?: "")
    }

    override fun onSuccessAddKeranjang(msg: String?) {
        getKeranjang()
        keranjangIndex = listKeranjang?.size ?: 0
    }

    override fun onFailedAddKeranjang(msg: String?) {
        Log.d("PenjualanTas", msg ?: "")
    }

}
