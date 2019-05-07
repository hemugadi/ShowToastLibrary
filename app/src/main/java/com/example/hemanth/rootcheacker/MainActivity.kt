package com.example.hemanth.rootcheacker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.scottyab.rootbeer.RootBeer
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text: TextView = findViewById(R.id.android_text) as TextView

        // by checking
        // 1.get from build info
        // 2.check if /system/app/Superuser.apk is present
        // 3.try executing commands

        // using Rootbeer library

       // var isRootedDevice : Boolean = isRooted();
        var isRootedDevice : Boolean = RootBeer(this).isRooted
        if (isRootedDevice) {
            //we found indication of root
            text.setText(getString(R.string.roted))
        } else {
            //we didn't find indication of root
            text.setText(getString(R.string.notroted))
        }

    }

    fun isRooted(): Boolean {

        val buildTags = android.os.Build.TAGS
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true
        }

        try {
            val file = File("/system/app/Superuser.apk")
            if (file.exists()) {
                return true
            }
        } catch (e1: Exception) {
        }

        return (canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su"))
    }

    private fun canExecuteCommand(command: String): Boolean {
        var executedSuccesfully: Boolean
        try {
            Runtime.getRuntime().exec(command)
            executedSuccesfully = true
        } catch (e: Exception) {
            executedSuccesfully = false
        }

        return executedSuccesfully
    }

}
