package info.covid.data.models

import com.squareup.moshi.Json
import info.covid.data.utils.Const.statesCodes
import info.covid.data.utils.toNumber

data class StateDailyItem(
    @field:Json(name = "date")
    var date: String? = null,
    @field:Json(name = "status")
    var status: String? = null,
    @field:Json(name = "an")
    var an: String? = null,
    @field:Json(name = "ap")
    var ap: String? = null,
    @field:Json(name = "ar")
    var ar: String? = null,
    @field:Json(name = "as")
    var `as`: String? = null,
    @field:Json(name = "br")
    var br: String? = null,
    @field:Json(name = "ch")
    var ch: String? = null,
    @field:Json(name = "ct")
    var ct: String? = null,
    @field:Json(name = "dd")
    var dd: String? = null,
    @field:Json(name = "dl")
    var dl: String? = null,
    @field:Json(name = "dn")
    var dn: String? = null,
    @field:Json(name = "ga")
    var ga: String? = null,
    @field:Json(name = "gj")
    var gj: String? = null,
    @field:Json(name = "hp")
    var hp: String? = null,
    @field:Json(name = "hr")
    var hr: String? = null,
    @field:Json(name = "jh")
    var jh: String? = null,
    @field:Json(name = "jk")
    var jk: String? = null,
    @field:Json(name = "ka")
    var ka: String? = null,
    @field:Json(name = "kl")
    var kl: String? = null,
    @field:Json(name = "la")
    var la: String? = null,
    @field:Json(name = "ld")
    var ld: String? = null,
    @field:Json(name = "mh")
    var mh: String? = null,
    @field:Json(name = "ml")
    var ml: String? = null,
    @field:Json(name = "mn")
    var mn: String? = null,
    @field:Json(name = "mp")
    var mp: String? = null,
    @field:Json(name = "mz")
    var mz: String? = null,
    @field:Json(name = "nl")
    var nl: String? = null,
    @field:Json(name = "or")
    var or: String? = null,
    @field:Json(name = "pb")
    var pb: String? = null,
    @field:Json(name = "py")
    var py: String? = null,
    @field:Json(name = "rj")
    var rj: String? = null,
    @field:Json(name = "sk")
    var sk: String? = null,
    @field:Json(name = "sta")
    var sta: String? = null,
    @field:Json(name = "tg")
    var tg: String? = null,
    @field:Json(name = "tn")
    var tn: String? = null,
    @field:Json(name = "tr")
    var tr: String? = null,
    @field:Json(name = "tt")
    var tt: String? = null,
    @field:Json(name = "up")
    var up: String? = null,
    @field:Json(name = "ut")
    var ut: String? = null,
    @field:Json(name = "wb")
    var wb: String? = null
) {
    fun getCount(state: String): Int {
        return when (statesCodes[state]?.toLowerCase()) {
            "an" -> an.toNumber()
            "ap" -> ap.toNumber()
            "ar" -> ar.toNumber()
            "as" -> `as`.toNumber()
            "br" -> br.toNumber()
            "ch" -> ch.toNumber()
            "ct" -> ct.toNumber()
            "dd" -> dd.toNumber()
            "dl" -> dl.toNumber()
            "dn" -> dn.toNumber()
            "ga" -> ga.toNumber()
            "gj" -> gj.toNumber()
            "hp" -> hp.toNumber()
            "hr" -> hr.toNumber()
            "jh" -> jh.toNumber()
            "jk" -> jk.toNumber()
            "ka" -> ka.toNumber()
            "kl" -> kl.toNumber()
            "la" -> la.toNumber()
            "ld" -> ld.toNumber()
            "mh" -> mh.toNumber()
            "ml" -> ml.toNumber()
            "mn" -> mn.toNumber()
            "mp" -> mp.toNumber()
            "mz" -> mz.toNumber()
            "nl" -> nl.toNumber()
            "or" -> or.toNumber()
            "pb" -> pb.toNumber()
            "py" -> py.toNumber()
            "rj" -> rj.toNumber()
            "sk" -> sk.toNumber()
            "tg" -> tg.toNumber()
            "tn" -> tn.toNumber()
            "tr" -> tr.toNumber()
            "tt" -> tt.toNumber()
            "up" -> up.toNumber()
            "ut" -> ut.toNumber()
            "wb" -> wb.toNumber()
            else -> 0
        }
    }
}
