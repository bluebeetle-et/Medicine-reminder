package com.asim.medicinereminder
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
data class Medicine(val id:Long,val name:String,val dose:String,val hour:Int,val minute:Int)
data class DoseEvent(val medicine:String,val scheduled:Long,val actual:Long,val action:String,val snoozes:Int)
object Store { private const val PREF="medicine_store"
 fun medicines(c:Context):List<Medicine>{val a=JSONArray(c.getSharedPreferences(PREF,0).getString("medicines","[]"));return (0 until a.length()).map{a.getJSONObject(it)}.map{Medicine(it.getLong("id"),it.getString("name"),it.optString("dose"),it.getInt("hour"),it.getInt("minute"))}}
 fun saveMedicine(c:Context,m:Medicine){val all=medicines(c).toMutableList().apply{removeAll{it.id==m.id};add(m)};val a=JSONArray();all.forEach{a.put(JSONObject().put("id",it.id).put("name",it.name).put("dose",it.dose).put("hour",it.hour).put("minute",it.minute))};c.getSharedPreferences(PREF,0).edit().putString("medicines",a.toString()).apply()}
 fun log(c:Context,e:DoseEvent){val p=c.getSharedPreferences(PREF,0);val a=JSONArray(p.getString("events","[]"));a.put(JSONObject().put("medicine",e.medicine).put("scheduled",e.scheduled).put("actual",e.actual).put("action",e.action).put("snoozes",e.snoozes));p.edit().putString("events",a.toString()).apply()}
 fun events(c:Context):List<DoseEvent>{val a=JSONArray(c.getSharedPreferences(PREF,0).getString("events","[]"));return (0 until a.length()).map{a.getJSONObject(it)}.map{DoseEvent(it.getString("medicine"),it.getLong("scheduled"),it.getLong("actual"),it.getString("action"),it.optInt("snoozes"))}.reversed()}
}
