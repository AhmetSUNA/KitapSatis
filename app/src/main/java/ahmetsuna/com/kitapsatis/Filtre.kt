package ahmetsuna.com.kitapsatis

import android.widget.Filter


class Filtre(tumKitaplar:ArrayList<Kitap>, adapter: KitaplarBaseAdapter) : Filter() {

    var suankiListe = tumKitaplar
    var suankiAdapter = adapter

    override fun performFiltering(constraint: CharSequence?): FilterResults {

        var sonuc = FilterResults()

        if (constraint != null && constraint.length>0){

            var aranilanKitapAd = constraint.toString().toLowerCase()

            var eslesenler = ArrayList<Kitap>()

            for (kitap in suankiListe){

                var adi = kitap.kitapAdi.toLowerCase()

                if (adi.contains(aranilanKitapAd.toString())){

                    eslesenler.add(kitap)
                }
            }

            sonuc.values = eslesenler
            sonuc.count = eslesenler.size
        }else{
            sonuc.values=suankiListe
            sonuc.count = suankiListe.size
        }

        return sonuc

    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

        suankiAdapter.setFilter(results?.values as ArrayList<Kitap>)
        suankiAdapter.notifyDataSetChanged()

    }


}