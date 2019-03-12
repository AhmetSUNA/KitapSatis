package ahmetsuna.com.kitapsatis

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_kullanici_ayarlari.*

class KullaniciAyarlariActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_ayarlari)


        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        val artNameArray = ArrayList<String>()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, artNameArray)
        listView.adapter = arrayAdapter

        try {

            val database = this.openOrCreateDatabase("Arts", Context.MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS arts(ad VARCHAR,numara VARCHAR,adres VARCHAR)")

            val cursor = database.rawQuery("SELECT * FROM arts", null)

            val nameIx = cursor.getColumnIndex("name")

            cursor.moveToFirst()

            while (cursor != null) {

                artNameArray.add(cursor.getString(nameIx))

                cursor.moveToNext()

                arrayAdapter.notifyDataSetChanged()
            }

            cursor?.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
