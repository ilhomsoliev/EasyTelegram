package com.ilhomsoliev.tgcore

import androidx.compose.runtime.mutableStateOf
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

val newUpdateFromTdApi = mutableStateOf<Boolean?>(null)

object AppDataState {
    val users: ConcurrentMap<Long, TdApi.User> = ConcurrentHashMap()
    val basicGroups: ConcurrentMap<Long, BasicGroup> = ConcurrentHashMap()
    val supergroups: ConcurrentMap<Long, Supergroup> = ConcurrentHashMap()
    val secretChats: ConcurrentMap<Int, SecretChat> = ConcurrentHashMap()
    val chats: ConcurrentMap<Long, TdApi.Chat> = ConcurrentHashMap()
    val usersFullInfo: ConcurrentMap<Long, UserFullInfo> = ConcurrentHashMap()
    val basicGroupsFullInfo: ConcurrentMap<Long, BasicGroupFullInfo> = ConcurrentHashMap()
    val supergroupsFullInfo: ConcurrentMap<Long, SupergroupFullInfo> = ConcurrentHashMap()
    val mainChatList: NavigableSet<OrderedChat> = TreeSet<OrderedChat>()

    fun putUser(key: Long, value: TdApi.User) {
        users[key] = value
    }

    class OrderedChat internal constructor(
        private val chatId: Long,
        val position: ChatPosition
    ) : Comparable<OrderedChat?> {

        override operator fun compareTo(o: OrderedChat?): Int {
            if (o == null) return 0; // TODO
            if (position.order != o.position.order) {
                return if (o.position.order < position.order) -1 else 1
            }
            return if (chatId != o.chatId) {
                if (o.chatId < chatId) -1 else 1
            } else 0
        }

        override fun equals(obj: Any?): Boolean {
            val o = obj as OrderedChat?
            return chatId == o!!.chatId && position.order == o.position.order
        }
    }

}