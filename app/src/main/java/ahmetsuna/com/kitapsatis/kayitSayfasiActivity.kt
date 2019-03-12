package ahmetsuna.com.kitapsatis

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_kayit_sayfasi.*

class kayitSayfasiActivity : Activity() {


    /*fun onCreateView(inflater: LayoutInflater, constainer: ViewGroup, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.activity_kayit_sayfasi, constainer, false)

        view.etAd.addTextChangedListener(watcher)
        view.etNo.addTextChangedListener(watcher)
        view.etAdres.addTextChangedListener(watcher)
        view.etMail.addTextChangedListener(watcher)
        view.etSifre.addTextChangedListener(watcher)
        view.etSifreTekrar.addTextChangedListener(watcher)


        return view
    }

    var watcher : TextWatcher = object  : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length > 5){
                if (etAd.text.toString().length>5 && etNo.text.toString().length>5 && etAdres.text.toString().length>5 &&
                    etSifre.text.toString().length>5 && etSifreTekrar.text.toString().length>5){

                    btnKayitOl.isEnabled = true

                }else{
                    btnKayitOl.isEnabled = false
                }

            }else{
                btnKayitOl.isEnabled = false
            }
        }

    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit_sayfasi)

        //kayıt ol butonuna basıldıgında yapılacaklar
        btnKayitOl.setOnClickListener {
            //metin'ler boşmu kontrolü
            if (etMail.text.isNotEmpty() && etSifre.text.isNotEmpty() && etSifreTekrar.text.isNotEmpty()) {
                //metin'ler dolu ise yapılacaklar
                if (etSifre.text.toString().equals(etSifreTekrar.text.toString())) {

                    yeniUyeKayit(etMail.text.toString(), etSifre.text.toString())

                } else {
                    Toast.makeText(this, "Şifreler aynı değil", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Boş alanları doldurunuz", Toast.LENGTH_SHORT).show()
            }

        }


    }

    fun kaydet(view: View) {

        val ad = etAd.text.toString()
        val no = etNo.text.toString()
        val adres = etAdres.text.toString()

        try {

            val database = this.openOrCreateDatabase("Arts", Context.MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS arts(ad VARCHAR,numara VARCHAR,adres VARCHAR)")

            val sqlString = "INSERT INTO arts (ad, numara, adres) VALUES(?,?,?)"
            val statement = database.compileStatement(sqlString)

            statement.bindString(1, ad)
            statement.bindString(2, no)
            statement.bindString(3, adres)
            statement.execute()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        /*val intent = Intent(applicationContext,KullaniciAyarlariActivity::class.java)
        startActivity(intent)*/

    }

    /*fun isValidEmail(kontrolEdilecekMail:String):Boolean{

        if (kontrolEdilecekMail == null){
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(kontrolEdilecekMail).matches()
    }*/

    //üye kayıt işlemleri
    private fun yeniUyeKayit(mail: String, sifre: String) {

        progressBarGoster()
        //fireBase email ve şifre kontrolü
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, sifre)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {

                    //işlem başarılı ise
                    if (p0.isSuccessful) {
                        Toast.makeText(
                            this@kayitSayfasiActivity,
                            "Üye kaydedildi: " + FirebaseAuth.getInstance().currentUser?.uid,
                            Toast.LENGTH_SHORT
                        ).show()
                        onayMailiGonder()
                        FirebaseAuth.getInstance().signOut()
                    }
                    //işlem başarısız ise
                    else {
                        Toast.makeText(
                            this@kayitSayfasiActivity,
                            "Üye kaydedilirken sorun oluştu: " + p0.exception?.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        progressBarGizle()

    }

    //Mail'e onay mesajı gönderme
    private fun onayMailiGonder() {

        var kullanici = FirebaseAuth.getInstance().currentUser
        if (kullanici != null) {

            kullanici.sendEmailVerification().addOnCompleteListener(object : OnCompleteListener<Void> {
                override fun onComplete(p0: Task<Void>) {

                    if (p0.isSuccessful) {
                        Toast.makeText(
                            this@kayitSayfasiActivity,
                            "Mail kutunuzu kontrol edin ve Mailinizi onaylayın", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@kayitSayfasiActivity,
                            "Mail gönderilirken sorun oluştu", Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            })
        }
    }

    //progress bar fonksiyonları göster-gizle
    private fun progressBarGoster() {
        progressBar.visibility = View.VISIBLE
    }

    private fun progressBarGizle() {
        progressBar.visibility = View.INVISIBLE
    }
}
