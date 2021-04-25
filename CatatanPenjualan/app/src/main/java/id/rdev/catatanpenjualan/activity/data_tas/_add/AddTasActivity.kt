package id.rdev.catatanpenjualan.activity.data_tas._add

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import id.rdev.catatanpenjualan.R
import id.rdev.catatanpenjualan.activity.data_tas._add.presenter.AddTasPresenter
import id.rdev.catatanpenjualan.activity.data_tas._add.presenter.AddTasView
import id.rdev.catatanpenjualan.base.BaseActivity
import id.rdev.catatanpenjualan.model.Tas
import kotlinx.android.synthetic.main.activity_add_tas.*
import org.jetbrains.anko.toast
import java.io.Serializable

class AddTasActivity : BaseActivity(), AddTasView {

    override fun onCreate(savedInstanceState: Bundle?) {
        cekSesi(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tas)

        val intent = intent.getSerializableExtra(TAGS.TAS)

        if (intent != null) {
            setActionEditButton(intent)
        } else {
            setActionTambahButton()
        }
    }

    // edit tas
    private fun setActionEditButton(serializable: Serializable) {
        btAddTas.text = "Simpan"
        val tas = serializable as Tas
        etAddTasBarcode.setText(tas.barcode)
        etAddTasNamaTas.setText(tas.namaTas)
        etAddTasKategori.setText(tas.kategori)
        etAddTasHargaBeli.setText(tas.hargaBeli.toString())
        etAddTasHargaJual.setText(tas.hargaJual.toString())

        btAddTas.setOnClickListener {
            val barcode = etAddTasBarcode.text.toString()
            val nama_tas= etAddTasNamaTas.text.toString()
            val kategori = etAddTasKategori.text.toString()
            val harga_beli_s = etAddTasHargaBeli.text.toString()
            val harga_jual_s = etAddTasHargaJual.text.toString()

            if (harga_beli_s.isNotBlank() && harga_jual_s.isNotBlank()) {
                val harga_beli = harga_beli_s.toDouble()
                val harga_jual = harga_jual_s.toDouble()

                // buat objek tas
                tas.idUser = user?.idUser.toString()
                tas.barcode = barcode
                tas.namaTas = nama_tas.toUpperCase()
                tas.kategori = kategori.toLowerCase().capitalize()
                tas.hargaBeli = harga_beli
                tas.hargaJual = harga_jual

                // simpan ke database
                AddTasPresenter(this@AddTasActivity).updateTas(tas)
            } else {
                // jangan input
                Snackbar.make(it, "Harga tidak boleh kosong", Snackbar.LENGTH_SHORT)
            }
        }
    }
    // tambah tas
    private fun setActionTambahButton() {
        btAddTas.setOnClickListener {
            btAddTas.text = "Tambah"
            val barcode = etAddTasBarcode.text.toString()
            val nama_tas = etAddTasNamaTas.text.toString()
            val kategori = etAddTasKategori.text.toString()
            val harga_beli_s = etAddTasHargaBeli.text.toString()
            val harga_jual_s = etAddTasHargaJual.text.toString()

            if (harga_beli_s.isNotBlank() && harga_jual_s.isNotBlank()) {
                val harga_beli = harga_beli_s.toDouble()
                val harga_jual = harga_jual_s.toDouble()

                // buat objek tas
                val tas = Tas()
                tas.idUser = user?.idUser.toString()
                tas.barcode = barcode
                tas.namaTas = nama_tas.toUpperCase()
                tas.kategori = kategori.toLowerCase().capitalize()
                tas.hargaBeli = harga_beli
                tas.hargaJual = harga_jual

                // simpan ke database
                AddTasPresenter(this@AddTasActivity).addTas(tas)
            } else {
                // jangan input
                Snackbar.make(it, "Harga tidak boleh kosong", Snackbar.LENGTH_SHORT)
            }
        }
    }

    /**
     * Result baik edit ataupun hapus sama saja
     */

    override fun onSuccessAddTas(msg: String?) {
        toast(msg ?: "").show()
        finish()
    }

    override fun onErrorAddTas(msg: String?) {
        toast(msg ?: "").show()
    }

}
