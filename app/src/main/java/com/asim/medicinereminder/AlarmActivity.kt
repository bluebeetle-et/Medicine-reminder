package com.asim.medicinereminder
import android.app.*
import android.os.*
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
class AlarmActivity:ComponentActivity(){override fun onCreate(b:Bundle?){super.onCreate(b);setShowWhenLocked(true);setTurnScreenOn(true);val id=intent.getLongExtra("id",0);val name=intent.getStringExtra("name")?:"Medicine";val dose=intent.getStringExtra("dose")?:"";val scheduled=intent.getLongExtra("scheduled",System.currentTimeMillis());val snoozes=intent.getIntExtra("snoozes",0);setContent{val t=rememberInfiniteTransition(label="blink");val alpha by t.animateFloat(0.35f,1f,infiniteRepeatable(tween(650),RepeatMode.Reverse),label="a");Box(Modifier.fillMaxSize().background(Color(1f,0.15f,0.12f,alpha)),contentAlignment=Alignment.Center){Column(Modifier.padding(28.dp),horizontalAlignment=Alignment.CenterHorizontally){Text("MEDICINE TIME",fontSize=34.sp,color=Color.White);Spacer(Modifier.height(20.dp));Text(name,fontSize=30.sp,color=Color.White);if(dose.isNotBlank())Text(dose,fontSize=22.sp,color=Color.White);Spacer(Modifier.height(40.dp));Button(onClick={Store.log(this@AlarmActivity,DoseEvent(name,scheduled,System.currentTimeMillis(),"TAKEN",snoozes));(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(id.toInt());Store.medicines(this@AlarmActivity).firstOrNull{it.id==id}?.let{AlarmScheduler.scheduleDaily(this@AlarmActivity,it)};finish()},modifier=Modifier.fillMaxWidth().height(64.dp)){Text("TOOK MEDICINE",fontSize=20.sp)};Spacer(Modifier.height(16.dp));Button(enabled=snoozes<3,onClick={val m=Medicine(id,name,dose,0,0);val next=System.currentTimeMillis()+20*60*1000L;Store.log(this@AlarmActivity,DoseEvent(name,scheduled,System.currentTimeMillis(),"SNOOZE",snoozes+1));AlarmScheduler.schedule(this@AlarmActivity,m,next,snoozes+1);(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(id.toInt());finish()},modifier=Modifier.fillMaxWidth()){Text(if(snoozes<3)"SNOOZE 20 MIN ("+(3-snoozes)+" LEFT)" else "NO SNOOZES LEFT")};Spacer(Modifier.height(16.dp));OutlinedButton(onClick={},modifier=Modifier.fillMaxWidth()){Text("SILENCE")}}}}}
 @Deprecated("Deprecated in Java") override fun onBackPressed(){}
}
