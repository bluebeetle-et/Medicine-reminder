package com.asim.medicinereminder
import android.app.*
import android.content.*
import java.util.Calendar
object AlarmScheduler { fun scheduleDaily(c:Context,m:Medicine){val cal=Calendar.getInstance().apply{set(Calendar.HOUR_OF_DAY,m.hour);set(Calendar.MINUTE,m.minute);set(Calendar.SECOND,0);set(Calendar.MILLISECOND,0);if(timeInMillis<=System.currentTimeMillis())add(Calendar.DAY_OF_YEAR,1)};schedule(c,m,cal.timeInMillis,0)}
 fun schedule(c:Context,m:Medicine,whenMs:Long,snoozes:Int){val i=Intent(c,AlarmReceiver::class.java).putExtra("id",m.id).putExtra("name",m.name).putExtra("dose",m.dose).putExtra("scheduled",whenMs).putExtra("snoozes",snoozes);val pi=PendingIntent.getBroadcast(c,(m.id+snoozes).toInt(),i,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE);(c.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,whenMs,pi)} }
