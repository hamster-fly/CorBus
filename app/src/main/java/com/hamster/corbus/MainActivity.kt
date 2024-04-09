package com.hamster.corbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hamster.corbus.test.LifecycleTestActivity
import com.hamster.corbus.test.PostTestActivity
import com.hamster.core.api.CorBus
import com.hamster.core.init.CorBusInit
import com.hamster.core.inner.debug.ICorBusFactory

class MainActivity : AppCompatActivity() {

    private val routes = listOf(
        HomeBean("同步演示", PostTestActivity::class.java),
        HomeBean("生命周期演示", LifecycleTestActivity::class.java),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.entryView).apply {
            adapter = HomeAdapter(routes)
        }

        CorBusInit.debugLog(true)
        CorBusInit.debugLog(true,object: ICorBusFactory.ICorBusDebug{
            override fun lod(msg: String) {

            }

            override fun loe(msg: String) {

            }
        })
    }

    inner class HomeAdapter(private val list: List<HomeBean>) :
        RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

        inner class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.targetTextView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_entry_item, parent, false)
            return HomeViewHolder(view)
        }

        override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
            holder.textView.text = list[position].title
            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, list[position].clazz)
                context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int = list.size
    }


    data class HomeBean(val title: String, val clazz: Class<out AppCompatActivity>)

}