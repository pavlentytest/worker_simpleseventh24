package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data: Data = Data.Builder().putString("key","Hello from activity!").build()
        val constraint = Constraints.Builder().setRequiresCharging(true).build()

        val worker3 = PeriodicWorkRequestBuilder<MyWorker2>(15, TimeUnit.MINUTES).build()

        // воркер будет запускать каждый 20 минут в течение 1
        val worker4 = PeriodicWorkRequestBuilder<MyWorker2>(1, TimeUnit.HOURS, 20, TimeUnit.MINUTES).build()

        val worker = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(data)
            .build()
        val worker2 = OneTimeWorkRequestBuilder<MyWorker2>()
            .setConstraints(constraint)
            .build()

        val list: ArrayList<OneTimeWorkRequest> = ArrayList()
        list.add(worker)
        list.add(worker2)


        WorkManager.getInstance(this).getWorkInfoByIdLiveData(worker2.id).observe(
            this, Observer {
                if(it != null) {
                    Log.d("RRR","worker2 = ${it.state}")
                }
            }
        )

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(worker.id).observe(
            this, Observer {
                if(it != null) {
                    it.outputData.getString("key2")?.let { it1 -> Log.d("RRR", it1) }
                    Log.d("RRR","worker1 = ${it.state}")
                }
            }
        )


        findViewById<Button>(R.id.button).setOnClickListener {
            // запускаем воркеры параллельно!
            WorkManager.getInstance(this).enqueue(list)

            // запускает воркеры последовательно!
            /*WorkManager.getInstance(this)
                .beginWith(worker)
                .then(worker2)
                .enqueue()


             */
           // WorkManager.getInstance(this).enqueue(worker)
        }
    }
}