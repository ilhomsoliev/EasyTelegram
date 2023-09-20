package com.ilhomsoliev.tgcore

import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.BasicGroup
import org.drinkless.tdlib.TdApi.BasicGroupFullInfo
import org.drinkless.tdlib.TdApi.ChatPosition
import org.drinkless.tdlib.TdApi.SecretChat
import org.drinkless.tdlib.TdApi.Supergroup
import org.drinkless.tdlib.TdApi.SupergroupFullInfo
import org.drinkless.tdlib.TdApi.UserFullInfo
import java.util.NavigableSet
import java.util.TreeSet
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap


object AppDataState {
    private val users: ConcurrentMap<Long, TdApi.User> = ConcurrentHashMap()
    private val basicGroups: ConcurrentMap<Long, BasicGroup> = ConcurrentHashMap()
    private val supergroups: ConcurrentMap<Long, Supergroup> = ConcurrentHashMap()
    private val secretChats: ConcurrentMap<Int, SecretChat> = ConcurrentHashMap()
    private val chats: ConcurrentMap<Long, TdApi.Chat> = ConcurrentHashMap()
    private val messages: ConcurrentMap<Long, TdApi.Message> = ConcurrentHashMap()
    private val usersFullInfo: ConcurrentMap<Long, UserFullInfo> = ConcurrentHashMap()
    private val basicGroupsFullInfo: ConcurrentMap<Long, BasicGroupFullInfo> = ConcurrentHashMap()
    private val supergroupsFullInfo: ConcurrentMap<Long, SupergroupFullInfo> = ConcurrentHashMap()
    val mainChatList: NavigableSet<OrderedChat> = TreeSet<OrderedChat>() // TODO

    /**
     * Returns chats matching with positions
     */
    fun getChats(): List<TdApi.Chat> {
        val iter: Iterator<OrderedChat> = mainChatList.iterator()
        var i = 0
        val chatsToReturn = mutableListOf<TdApi.Chat>()
        while (i < mainChatList.size) {
            val chatId = iter.next().chatId
            val chat: TdApi.Chat? = chats[chatId]
            synchronized(chat!!) {
                chatsToReturn.add(chat)
            }
            i++
        }
        return chatsToReturn
    }

    /**
     * Returns messages of the given chat
     */
    fun getMessages(): List<TdApi.Message> {
        val messagesToReturn = messages.values.toList().sortedBy { it.date }
        return messagesToReturn
    }

    fun clearMessages() {
        messages.clear()
    }

    fun putUser(key: Long, value: TdApi.User) {
        users[key] = value
    }

    fun getUser(key: Long) = users[key]

    fun putChat(key: Long, value: TdApi.Chat) {
        chats[key] = value
    }

    fun getChat(key: Long) = chats[key]

    fun putMessage(key: Long, value: TdApi.Message) {
        messages[key] = value
    }

    fun getMessage(key: Long) = messages[key]

    class OrderedChat internal constructor(
        internal val chatId: Long,
        val position: ChatPosition
    ) : Comparable<OrderedChat?> {

        override operator fun compareTo(other: OrderedChat?): Int {
            if (other == null) return 0; // TODO
            if (position.order != other.position.order) {
                return if (other.position.order < position.order) -1 else 1
            }
            return if (chatId != other.chatId) {
                if (other.chatId < chatId) -1 else 1
            } else 0
        }

        override fun equals(other: Any?): Boolean {
            val o = other as OrderedChat?
            return chatId == o!!.chatId && position.order == o.position.order
        }
    }

}