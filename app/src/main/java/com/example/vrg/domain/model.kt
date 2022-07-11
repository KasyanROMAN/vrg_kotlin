package com.example.vrg.domain

import android.os.Parcel
import android.os.Parcelable
import com.example.kedditappexample.commons.adapter.AdapterContants
import com.example.kedditappexample.commons.adapter.ViewType

data class RedditNewsItem(
    val author: String,
    val title: String,
    val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String
) : ViewType, Parcelable {
    override fun getViewType() = AdapterContants.NEW

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { RedditNewsItem(it) }
    }

    protected constructor(parcelIn: Parcel) : this(
        parcelIn.readString(),
        parcelIn.readString(),
        parcelIn.readInt(),
        parcelIn.readLong(),
        parcelIn.readString(),
        parcelIn.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(author)
        dest.writeString(title)
        dest.writeInt(numComments)
        dest.writeLong(created)
        dest.writeString(thumbnail)
        dest.writeString(url)
    }

    override fun describeContents() = 0
}

data class RedditNews(
    val after: String,
    val before: String,
    val news: List<RedditNewsItem>) : Parcelable {

    companion object {
        @Suppress("unused")
        @JvmField val CREATOR: Parcelable.Creator<RedditNews> = object : Parcelable.Creator<RedditNews> {
            override fun createFromParcel(source: Parcel): RedditNews = RedditNews(source)
            override fun newArray(size: Int): Array<RedditNews?> = arrayOfNulls(size)
        }
    }

    protected constructor(parcelIn: Parcel) : this(
        parcelIn.readString(),
        parcelIn.readString(),
        parcelIn.createTypedArrayList(RedditNewsItem.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(after)
        dest.writeString(before)
        dest.writeTypedList(news)
    }

    override fun describeContents() = 0
}