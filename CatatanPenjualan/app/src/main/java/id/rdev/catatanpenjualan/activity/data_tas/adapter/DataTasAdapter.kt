package id.rdev.catatanpenjualan.activity.data_tas.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import id.rdev.catatanpenjualan.R
import id.rdev.catatanpenjualan.model.Tas
import kotlinx.android.synthetic.main.item_data_tas.view.*

class DataTasAdapter(val tas: List<Tas?>?, val onMenuClicked: OnMenuClicked) : RecyclerView.Adapter<DataTasAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_data_tas, parent, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int = tas?.size ?: 0

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(tas?.get(position))

        holder.itemView.ivMenuTas.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, it)
            popupMenu.menuInflater.inflate(R.menu.menu_tas, popupMenu.menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener {
                onMenuClicked.click(it, tas?.get(position))
                true
            }
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tas: Tas?) {
            itemView.tvBarcode.text = tas?.barcode
            itemView.tvNamaTas.text = tas?.namaTas
            itemView.tvCategory.text = tas?.kategori
        }
    }

    interface OnMenuClicked {
        fun click(menuItem: MenuItem, tas: Tas?)
    }
}