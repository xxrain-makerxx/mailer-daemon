package com.mailerdaemon.app.notices

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
// import com.mailerdaemon.app.Notices.NoticeViewPagerAdapter

import com.mailerdaemon.app.R
import kotlinx.android.synthetic.main.item_posts.view.*
import kotlinx.android.synthetic.main.notice_viewpager_item.view.*
import kotlinx.android.synthetic.main.rv_notices.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class NoticeAdapter(var list: List<PostModel>, val context: Context, val mListener: (PostModel) -> Unit) :
    RecyclerView.Adapter<NoticeAdapter.Holder>() {
    private val p = PrettyTime()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.rv_notices, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val dateString = list[position].created_time
        val convertedDate = dateFormat.parse(dateString)
        val str = list[position]

        holder.itemView.let {

            it.notice_detail.text = list[position].message
            it.time.text = p.format(convertedDate)
            if (str.full_picture.isNullOrBlank() || str.photo.isNotEmpty()) {
                it.full_image.visibility = View.GONE
            } else {
                it.full_image.visibility = View.VISIBLE
                it.full_image.setImageURI(str.full_picture)
            }
            if (str.photo.isNotEmpty()) {
                val adapter = NoticeViewPagerAdapter()
                it.notice_viewpager.visibility = View.VISIBLE
                adapter.list = str.photo
                it.notice_viewpager.adapter = adapter
            } else {
                it.notice_viewpager.visibility = View.GONE
            }

            it.ivFacebook.setOnClickListener {
                val facebookIntent = Intent(Intent.ACTION_VIEW)
                facebookIntent.data = Uri.parse(str.permalink_url)
                context.startActivity(facebookIntent)
            }
            it.ivShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, str.permalink_url)
                context.startActivity(Intent.createChooser(shareIntent, "Share..."))
            }
        }

        holder.itemView.relativeLayout.setOnClickListener {
            list.get(position).let { it1 -> mListener.invoke(it1) }
        }
    }
}
