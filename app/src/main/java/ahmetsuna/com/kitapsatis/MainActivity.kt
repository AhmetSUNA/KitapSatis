package ahmetsuna.com.kitapsatis

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()/*,
    SearchView.OnQueryTextListener*/ {


    lateinit var myAuthStateListener: FirebaseAuth.AuthStateListener

    lateinit var tumKitapBilgileri: ArrayList<Kitap>

    //var tumKitaplar = ArrayList<Kitap>()

    lateinit var myAdapter: KitaplarBaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAuthStateListener()

        veriKaynagiHazirla()

        ListeGuncelle(tumKitapBilgileri)

        searchViewKitap.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                myAdapter.filter.filter(newText)

                return false
            }


        })

        myAdapter = KitaplarBaseAdapter(this, this, tumKitapBilgileri)
        listKitaplar.adapter = myAdapter

        listKitaplar.setOnItemClickListener { parent, view, position, id ->

            var intent = Intent(this@MainActivity, KitapDetayActivity::class.java)
            intent.putExtra("tiklanilanOgePosition", position)
            intent.putExtra("tumKitapBilgileri", tumKitapBilgileri)
            startActivity(intent)
        }


    }


    public fun ListeGuncelle(kitaplar: ArrayList<Kitap>) {
        var myAdapter = KitaplarBaseAdapter(this, this, kitaplar)
        listKitaplar.adapter = myAdapter

    }


    private fun veriKaynagiHazirla() {

        tumKitapBilgileri = ArrayList<Kitap>(12)

        var kitaplar = resources.getStringArray(R.array.kitaplar)
        var kitapYazarlari = resources.getStringArray(R.array.kitapYazarlari)
        var kitapResimleri = arrayOf(
            R.drawable.calikusu1,
            R.drawable.dovuskulubu2,
            R.drawable.eylul3,
            R.drawable.farelerveinsanlar4,
            R.drawable.hayvanciftligi5,
            R.drawable.insanciklar6,
            R.drawable.kurkmantolumadonna7,
            R.drawable.satranc8,
            R.drawable.sineklerintanrisi9,
            R.drawable.sineklibakkal10,
            R.drawable.sucveveza11,
            R.drawable.yuzyillikyalnizlik12
        )
        var kitapBuyukResimler = arrayOf(
            R.drawable.resat_nuri1,
            R.drawable.chuck_palahniuk2,
            R.drawable.mehmet_rauf3,
            R.drawable.john_steinbeck4,
            R.drawable.george_orwell5,
            R.drawable.dostoyevski6,
            R.drawable.sabahattin_ali7,
            R.drawable.stefan_zweig8,
            R.drawable.william_galding9,
            R.drawable.halide_edip_adivar10,
            R.drawable.dostoyevski11,
            R.drawable.gabriel_garcia_marquez12
        )

        var kitapOzellikler = resources.getStringArray(R.array.kitapOzellikleri)

        for (i in 0..11) {
            var arraylisteAtanacakKitap =
                Kitap(kitaplar[i], kitapYazarlari[i], kitapResimleri[i], kitapBuyukResimler[i], kitapOzellikler[i])
            tumKitapBilgileri.add(arraylisteAtanacakKitap)
        }

    }

    private fun initAuthStateListener() {
        myAuthStateListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(p0: FirebaseAuth) {

                var kullanici = p0.currentUser

                if (kullanici != null) {

                } else {

                    var intent = Intent(this@MainActivity, GirisActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }

            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.anamenu, menu)
        /*menuInflater.inflate(R.menu.filtre_menu, menu)
        var aramaItem = menu?.findItem(R.id.app_bar_search)

        var searchView = aramaItem?.actionView as SearchView

        searchView.setOnQueryTextListener(this)*/

        return true


    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.menuCikisYap -> {
                cikisyap()
                return true
            }

            R.id.menuHesapAyalari -> {

                var intent = Intent(this@MainActivity, KullaniciAyarlariActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //Giriş ekranına yolla
    private fun cikisyap() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun onResume() {
        super.onResume()

        kullaniciKontrolEt()
    }

    private fun kullaniciKontrolEt() {

        var kullanici = FirebaseAuth.getInstance().currentUser

        if (kullanici == null) {

            var intent = Intent(this@MainActivity, GirisActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        if (myAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateListener)
        }
    }

    /*override fun onQueryTextSubmit(query: String?): Boolean {
        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {

        var girilenVeri = newText?.toLowerCase()

        var arananlar = ArrayList<Kitap>()

        for (kitap in tumKitaplar){

            var adi = kitap.kitapAdi.toLowerCase()

            if (adi.contains(girilenVeri.toString())){
                arananlar.add(kitap)
            }
        }

        myAdapter.setFilter(arananlar)

        return true
    }*/


}
