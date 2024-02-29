package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
       // Log.d("RRR","Start worker 1")
        delay(5000)
       // Log.d("RRR","Finish worker 1")

        val result = inputData.getString("key")
        val data: Data = Data.Builder().putString("key2","Hello from worker1").build()

        return Result.success(data)
    }
}