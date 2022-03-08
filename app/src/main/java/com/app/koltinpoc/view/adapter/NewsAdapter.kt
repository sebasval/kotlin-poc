package com.app.koltinpoc.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.koltinpoc.R
import com.app.koltinpoc.databinding.AdapterNewsItemBinding
import com.app.koltinpoc.model.Article
import com.app.koltinpoc.model.RedditListInfo
import com.app.koltinpoc.utils.loadImageFromGlide
import javax.inject.Inject

class NewsAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<RedditListInfo>() {
        override fun areItemsTheSame(oldItem: RedditListInfo, newItem: RedditListInfo): Boolean {
            return oldItem.data.title == newItem.data.title
        }

        override fun areContentsTheSame(oldItem: RedditListInfo, newItem: RedditListInfo): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            ivArticle.loadImageFromGlide(article.data.thumbnail)
            tvTitle.text = article.data.title
            tvDescription.text = article.data.subreddit
            val comments = "${article.data.commentsCount} Commented this post"
            tvPublished.text = comments
            if (article.data.readStatus) {
                tvSource.text = context.getString(R.string.read_post)
            }
        }

        holder.itemView.setOnClickListener {
            setArticleClickListener?.let {
                it(article)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var setArticleClickListener: ((article: RedditListInfo) -> Unit)? = null

    fun onArticleClicked(listener: (RedditListInfo) -> Unit) {
        setArticleClickListener = listener
    }
}