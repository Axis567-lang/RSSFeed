package io.github.axis567_lang.rssfeed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ApplicationsAdapter(context: Context, feedEntries: ArrayList<FeedEntry>): RecyclerView.Adapter<ApplicationsAdapter.ViewHolder>() {
    private var localContext: Context? = null
    private var localFeedentries: ArrayList<FeedEntry>? = null

    init {
        localContext = context
        localFeedentries = feedEntries
    }

    // inflar layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationsAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(localContext)  // Referencia a MainActivity
        val view: View = layoutInflater.inflate(R.layout.entry_view, parent, false)
        return ViewHolder(view)
    }

    // asignar valores a las filas basadas en la posición de la lista
    override fun onBindViewHolder(holder: ApplicationsAdapter.ViewHolder, position: Int) {
        val currentFeedEntry: FeedEntry = localFeedentries!![position]
        holder.textName.text = currentFeedEntry.name
        holder.textArtist.text = currentFeedEntry.artist
        holder.textSummary.text = currentFeedEntry.summary.take(250).plus("...")
    }

    override fun getItemCount(): Int {
        return localFeedentries?.size ?: 0
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val textName: TextView = v.findViewById(R.id.textViewName)
        val textArtist: TextView = v.findViewById(R.id.textViewArtist)
        val textSummary: TextView = v.findViewById(R.id.textViewSummary)
//        var textName: TextView? = null
//
//        init {
//            textName = v.findViewById(R.id.textViewName);
//        }
    }
}