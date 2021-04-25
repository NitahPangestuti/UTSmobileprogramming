package id.rdev.catatanpenjualan.activity.data_tas

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import id.rdev.catatanpenjualan.R
import id.rdev.catatanpenjualan.activity.data_tas._add.AddTasActivity
import id.rdev.catatanpenjualan.activity.data_tas.adapter.DataTasAdapter
import id.rdev.catatanpenjualan.activity.data_tas.presenter.DataTasPresenter
import id.rdev.catatanpenjualan.activity.data_tas.presenter.DataTasView
import id.rdev.catatanpenjualan.base.BaseActivity
import id.rdev.catatanpenjualan.model.Tas
import kotlinx.android.synthetic.main.activity_data_tas.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DataTasActivity : BaseActivity(), DataTasView {
    override fun onCreate(savedInstanceState: Bundle?) {
        cekSesi(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_tas)

        setActionButton()

        refreshTas()
    }

    private fun setActionButton() {
        btAddDataTas.onClick {
            startActivity<AddTasActivity>(TAGS.USER to user)
        }
    }

    private fun refreshTas() {
        DataTasPresenter(this).getDataTas(user)
    }


    override fun onSuccessDataTas(data: List<Tas?>?) {
        rvDataTas.adapter = DataTasAdapter(data, object : DataTasAdapter.OnMenuClicked{
            override fun click(menuItem: MenuItem, tas: Tas?) {
                when(menuItem.itemId) {
                    R.id.editTas -> editTas(tas)
                    R.id.hapusTas -> hapusTas(tas)
                }
            }
        })
    }

    override fun onErrorDataTas(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun editTas(tas: Tas?) {
        val intent = Intent(this, AddTasActivity::class.java)
        intent.putExtra(TAGS.USER, user)
        intent.putExtra(TAGS.TAS, tas)

        startActivityForResult(intent, 1)
    }

    private fun hapusTas(tas: Tas?) {
        alert {
            title = "Konfirmasi"
            message = "Yakin ingin menghapus tas ${tas?.namaTas}"

            positiveButton("Hapus") {
                DataTasPresenter(this@DataTasActivity).deleteTas(user, tas)
            }
            negativeButton("Batal") {}
        }.show()
        refreshTas()
    }

    override fun onResume() {
        super.onResume()
        refreshTas()
    }


    override fun onSuccessDeleteTas(msg: String?) {
        toast(msg ?: "").show()
        refreshTas()
    }

    override fun onErrorDeleteTas(msg: String?) {
        toast(msg ?: "data sudah digunakan").show()
    }
}
