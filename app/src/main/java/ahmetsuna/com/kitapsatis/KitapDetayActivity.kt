package ahmetsuna.com.kitapsatis

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_kitap_detay.*

class KitapDetayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitap_detay)

        var position = intent.extras.get("tiklanilanOgePosition") as Int
        var tumKitapBilgileri = intent.extras.get("tumKitapBilgileri") as ArrayList<Kitap>

        tvKitapOzellikleri.setText(tumKitapBilgileri.get(position).kitapOzellikleri)
        header.setImageResource(tumKitapBilgileri.get(position).kitapBuyukResim)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        collapsing_toolbar.title = tumKitapBilgileri.get(position).kitapAdi

        var bitmap = BitmapFactory.decodeResource(resources, tumKitapBilgileri.get(position).kitapBuyukResim)

        Palette.from(bitmap).generate(object : Palette.PaletteAsyncListener {
            override fun onGenerated(palette: Palette?) {

            }

        })

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

    }

    //önceki geldigin aktiviteye geri dön
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.anamenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.menuHesapAyalari -> {

                var intent = Intent(this@KitapDetayActivity, KullaniciAyarlariActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
