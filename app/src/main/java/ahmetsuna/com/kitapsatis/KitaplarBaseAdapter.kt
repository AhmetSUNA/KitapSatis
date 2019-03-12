package ahmetsuna.com.kitapsatis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.tek_satir.view.*

class KitaplarBaseAdapter(activity: MainActivity, context: Context, tumKitapBilgileri: ArrayList<Kitap>) :
    BaseAdapter(), Filterable {


    var tumKitaplar: ArrayList<Kitap>
    var context: Context
    var activity: MainActivity

    var myFilter: Filtre = Filtre(tumKitapBilgileri, this)

    init {
        this.tumKitaplar = tumKitapBilgileri
        this.context = context
        this.activity = activity
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var tek_satir_view = convertView

        var viewHolder: ViewHolder

        if (tek_satir_view == null) {
            var inflater = LayoutInflater.from(context)
            tek_satir_view = inflater.inflate(R.layout.tek_satir, parent, false)

            viewHolder = ViewHolder(tek_satir_view)
            tek_satir_view.tag = viewHolder
        } else {

            viewHolder = tek_satir_view.getTag() as ViewHolder
        }

        viewHolder.kitapResim.setImageResource(tumKitaplar.get(position).kitapSembol)
        viewHolder.kitapAdi.setText(tumKitaplar.get(position).kitapAdi)
        viewHolder.kitapYazari.setText(tumKitaplar.get(position).kitapYazari)
        viewHolder.satinAlSil.setOnClickListener {
            Toast.makeText(context, "satın alındı.", Toast.LENGTH_SHORT).show()

            tumKitaplar.remove(tumKitaplar.get(position))
            activity.ListeGuncelle(tumKitaplar)
        }

        return tek_satir_view
    }

    override fun getItem(position: Int): Any {
        return tumKitaplar.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return tumKitaplar.size
    }

    fun setFilter(arrayList: ArrayList<Kitap>) {

        tumKitaplar = arrayList //arraylist nesnesi filtrelenmiş sonuçları içerir
    }

    override fun getFilter(): Filter {
        return myFilter  //adapter sınıfı içinde oluşturulan filter tipindeki nesneyi döndürür
    }

    /*fun setFilter(aranilanlar: ArrayList<Kitap>) {

        tumKitaplar = ArrayList<Kitap>()

        tumKitaplar.addAll(aranilanlar)

        notifyDataSetChanged()

    }*/

}


class ViewHolder(tek_satir_view: View) {

    var kitapResim: ImageView
    var kitapAdi: TextView
    var kitapYazari: TextView
    var satinAlSil: Button

    init {
        this.kitapResim = tek_satir_view.imgKitapSembol
        this.kitapAdi = tek_satir_view.tvKitapAdi
        this.kitapYazari = tek_satir_view.tvKitapYazari
        this.satinAlSil = tek_satir_view.btnSatınalSil
    }


}