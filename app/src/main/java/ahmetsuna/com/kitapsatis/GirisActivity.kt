package ahmetsuna.com.kitapsatis

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_giris.*


class GirisActivity : AppCompatActivity() {

    //Mailim onaylanmışmı onaylanmamışmı kontrolünü yapacağım interface nesnem
    lateinit var myAuthStateLitener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giris)

        initMyAuthStateListener()

        //kayıt sayfasına yönlendirme
        tvKayitOl.setOnClickListener {
            var intent = Intent(this, kayitSayfasiActivity::class.java)
            startActivity(intent)
        }

        btnGirisYap.setOnClickListener {

            if (etMail.text.isNotEmpty() && etSifre.text.isNotEmpty()) {

                progressBarGoster()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(etMail.text.toString(), etSifre.text.toString())
                    .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                        override fun onComplete(p0: Task<AuthResult>) {
                            //işlem başarılı ise
                            if (p0.isSuccessful) {
                                progressBarGizle()
                                //Toast.makeText(this@GirisActivity, "Başarılı Giriş: " + FirebaseAuth.getInstance().currentUser?.email, Toast.LENGTH_SHORT).show()
                            }
                            //işlem başarısız ise
                            else {
                                progressBarGizle()
                                Toast.makeText(
                                    this@GirisActivity,
                                    "Hatalı Giriş: " + p0.exception?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })

            } else {
                Toast.makeText(this@GirisActivity, "Boş alanları doldurunuz", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun progressBarGoster() {
        progressBarGiris.visibility = View.VISIBLE
    }

    private fun progressBarGizle() {
        progressBarGiris.visibility = View.INVISIBLE
    }

    private fun initMyAuthStateListener() {

        myAuthStateLitener = object : FirebaseAuth.AuthStateListener {
            //kullanıcı sisteme giriş veya çıkış işlemleri yaptıgında tetiklenecek olan method
            override fun onAuthStateChanged(p0: FirebaseAuth) {

                var kullanici = p0.currentUser

                if (kullanici != null) {

                    if (kullanici.isEmailVerified) {

                        //Başarılı giriş ile ana sayfaya yönlendirme işlemi
                        var intent = Intent(this@GirisActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()


                    } else {
                        Toast.makeText(
                            this@GirisActivity,
                            "Lütfen mail adresinizi onaylayın ve tekrar deneyin",
                            Toast.LENGTH_SHORT
                        ).show()
                        FirebaseAuth.getInstance().signOut()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateLitener)
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateLitener)
    }
}
