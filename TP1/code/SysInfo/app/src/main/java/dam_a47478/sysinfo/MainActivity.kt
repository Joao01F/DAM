package dam_a47478.sysinfo

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val manufView: TextView = findViewById(R.id.manuf)
        val modelView: TextView = findViewById(R.id.model)
        val brandView: TextView = findViewById(R.id.brand)
        val typeView: TextView = findViewById(R.id.type)
        val userView: TextView = findViewById(R.id.user)
        val baseView: TextView = findViewById(R.id.base)
        val incrementalView: TextView = findViewById(R.id.incremental)
        val sdkView: TextView = findViewById(R.id.sdk)
        val versionCodeView: TextView = findViewById(R.id.versionCode)
        val displayView: TextView = findViewById(R.id.display)

        manufView.text = getString(
            R.string.manufacturer,
            android.os.Build.MANUFACTURER
        )
        modelView.text = getString(
            R.string.model,
            android.os.Build.MODEL
        )
        brandView.text = getString(
            R.string.brand,
            android.os.Build.BRAND
        )
        typeView.text = getString(
            R.string.type,
            android.os.Build.TYPE
        )
        userView.text = getString(
            R.string.user,
            android.os.Build.USER
        )
        baseView.text = getString(
            R.string.base,
            android.os.Build.VERSION.BASE_OS
        )
        incrementalView.text = getString(
            R.string.incremental,
            android.os.Build.VERSION.INCREMENTAL
        )
        sdkView.text = getString(
            R.string.sdk,
            android.os.Build.VERSION.SDK_INT
        )
        versionCodeView.text = getString(
            R.string.versionCode,
            android.os.Build.VERSION.RELEASE
        )
        displayView.text = getString(
            R.string.display,
            android.os.Build.DISPLAY
        )
    }
}