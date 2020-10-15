package com.light.mybackgrounduithread

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.lang.Exception
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), MyAsyncCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = INPUT_STRING

        val demoAsync = DemoAsync(this)
        demoAsync.execute(input)
    }

    override fun onPreExecute() {
        val tvStatus = findViewById<TextView>(R.id.tv_status)
        val tvDesc = findViewById<TextView>(R.id.tv_desc)
        tvStatus.text = (R.string.status_pre).toString()
        tvDesc.text = INPUT_STRING
    }

    override fun onPostExecute(text: String) {
        TODO("Not yet implemented")
    }

    companion object{
        private const val INPUT_STRING = "Halo ini Demo AsyncTasc"
    }

    private class DemoAsync(var myListener: MyAsyncCallback): AsyncTask<String, Void, String>() {

        companion object{
            private val LOG_ASNYC = "DemoAsync"
        }

        private var myListnr = WeakReference<MyAsyncCallback>()
        init {
            this.myListnr = WeakReference<myListener>()
        }

        override fun doInBackground(vararg params: String?): String {
            Log.d(LOG_ASNYC, "status: doInBackground: ")

            var output: String? = null

            try {
                val input = params[0]
                output = "$input Selamat Belajar!!"
                Thread.sleep(2000)
            } catch(e: Exception){
                Log.d(LOG_ASNYC, e.message.toString())
            }
            return output.toString()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(LOG_ASNYC, "status : onPreExecute")

            val myListener = myListener.get()
            myListener?.onPreExecute()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            Log.d(LOG_ASNYC, "status : onPostExecute")
            val myListener = this.myListener.get()
            myListener?.onPostExecute(result)
        }



    }
}

interface MyAsyncCallback{
    fun onPreExecute()
    fun onPostExecute(text: String)
}