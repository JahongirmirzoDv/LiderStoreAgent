package com.mobile.liderstoreagent.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.mobile.liderstoreagent.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var graph: NavGraph
    private lateinit var host: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host = myNavHostFragment as NavHostFragment
        graph = host.navController.navInflater.inflate(R.navigation.navigation)
        graph.startDestination = R.id.splashFragment
        host.navController.graph = graph

    }

    override fun onBackPressed() {

        when(NavHostFragment.findNavController(host).currentDestination?.id) {
            R.id.mainFragment-> {
                AlertDialog.Builder(this)
                    .setTitle("Диққат!")
                    .setMessage("Ҳақиқатдан тизимдан чиқмоқчимисиз!")
                    .setNegativeButton("Йўқ") { dialog, _ ->
                        dialog.cancel()
                    }
                    .setPositiveButton("Ҳа") { dialog, _ ->
                        super.onBackPressed()
                        dialog.cancel()
                    }.show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }


    /*



     */


}
