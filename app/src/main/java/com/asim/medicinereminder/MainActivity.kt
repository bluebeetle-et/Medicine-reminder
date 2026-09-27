package com.asim.medicinereminder
import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
class MainActivity:ComponentActivity(){private val notifications=registerForActivityResult(ActivityResultContracts.RequestPermission()){};override fun onCreate(b:Bundle?){super.onCreate(b);if(android.os.Build.VERSION.SDK_INT>=33)notifications.launch(Manifest.permission.POST_NOTIFICATIONS);setContent{App()}}
 @Composable fun App(){var refresh by remember{mutableIntStateOf(0)};var name by remember{mutableStateOf("")};var dose by remember{mutableStateOf("")};var hour by remember{mutableIntStateOf(8)};var minute by remember{mutableIntStateOf(0)};val meds=remember(refresh){Store.medicines(this)};val events=remember(refresh){Store.events(this)};MaterialTheme{LazyColumn(Modifier.fillMaxSize().padding(20.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){item{Text("Medicine Reminder",style=MaterialTheme.typography.headlineMedium)};item{OutlinedTextField(name,{name=it},label={Text("Medicine name")},modifier=Modifier.fillMaxWidth())};item{OutlinedTextField(dose,{dose=it},label={Text("Dose (optional)")},modifier=Modifier.fillMaxWidth())};item{Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){OutlinedTextField(hour.toString(),{hour=it.toIntOrNull()?.coerceIn(0,23)?:hour},label={Text("Hour 0-23")},modifier=Modifier.weight(1f));OutlinedTextField(minute.toString(),{minute=it.toIntOrNull()?.coerceIn(0,59)?:minute},label={Text("Minute")},modifier=Modifier.weight(1f))}};item{Button(enabled=name.isNotBlank(),onClick={val m=Medicine(System.currentTimeMillis(),name.trim(),dose.trim(),hour,minute);Store.saveMedicine(this@MainActivity,m);AlarmScheduler.scheduleDaily(this@MainActivity,m);name="";dose="";refresh++},modifier=Modifier.fillMaxWidth()){Text("ADD REMINDER")}};item{HorizontalDivider();Text("Reminders",style=MaterialTheme.typography.titleLarge)};items(meds){Text("• "+it.name+" "+it.dose+"  "+String.format("%02d:%02d",it.hour,it.minute))};item{HorizontalDivider();Text("History",style=MaterialTheme.typography.titleLarge)};items(events.take(30)){val f=SimpleDateFormat("MMM d, h:mm a",Locale.getDefault());Text(it.medicine+": "+it.action+" • "+f.format(Date(it.actual)))}}}}
}
