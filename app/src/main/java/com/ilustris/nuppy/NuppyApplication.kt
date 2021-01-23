package com.ilustris.nuppy

import android.app.Application
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider


class NuppyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        EmojiManager.install(IosEmojiProvider())

    }
}