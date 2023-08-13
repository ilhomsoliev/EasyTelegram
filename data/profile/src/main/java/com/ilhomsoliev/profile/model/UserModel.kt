package com.ilhomsoliev.profile.model

import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.File
import org.drinkless.td.libcore.telegram.TdApi.ProfilePhoto

data class UserModel(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber: String,
    val status: UserStatusState,
    val profilePhoto: ProfilePhotoModel?,
)

fun TdApi.User.map() = UserModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
    phoneNumber = phoneNumber,
    status = status.map(),
    profilePhoto = profilePhoto?.map()
)

// TODO add minithumbnail
data class ProfilePhotoModel(
    val id: Long,
    val smallFile: File,
    val bigFile: File,
    val hasAnimation: Boolean,
    val minithumbnail: TdApi.Minithumbnail?,
)

fun ProfilePhoto.map() = ProfilePhotoModel(
    id = id,
    smallFile = small,
    bigFile = big,
    hasAnimation = hasAnimation,
    minithumbnail = minithumbnail,
)