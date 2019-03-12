package ahmetsuna.com.kitapsatis

import java.io.Serializable

data class Kitap(var kitapAdi: String, var kitapYazari: String, var kitapSembol: Int,var kitapBuyukResim:Int, var kitapOzellikleri:String):Serializable {}
