package com.example.vrg.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kedditappexample.R
import com.example.kedditappexample.commons.InfiniteScrollListener
import com.example.kedditappexample.commons.RedditNews
import com.example.kedditappexample.commons.RxBaseFragment
import com.example.kedditappexample.commons.inflate
import com.example.kedditappexample.features.news.adapter.NewAdapter
import kotlinx.android.synthetic.main.fragment_new.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewFragment : RxBaseFragment() {
    companion object {
        private val KEY_REDDIT_NEWS = "redditNews"
    }

    private var redditNews: RedditNews? = null
    private val newsManager by lazy {
        NewsManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_new)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        rvNews.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))

        }
        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            (rvNews.adapter as NewAdapter).clearAndAddNews(redditNews!!.news)
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (rvNews.adapter as NewAdapter).getNews()
        if (redditNews != null && news.size > 0) {
            outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews(redditNews?.after ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { retrievedNews ->

                    redditNews = retrievedNews
                    (rvNews.adapter as NewAdapter).addNews(retrievedNews.news)
                },
                { e ->
                    Snackbar.make(rvNews, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            )


        subcriptions.add(subscription)
    }

    private fun initAdapter() {
        if (rvNews.adapter == null) {
            rvNews.adapter = NewAdapter()
        }
    }
}
