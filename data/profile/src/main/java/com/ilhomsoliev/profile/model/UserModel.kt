package com.ilhomsoliev.profile.model

import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.File
import org.drinkless.tdlib.TdApi.ProfilePhoto

data class UserModel(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val usernames: UsernamesModel?,
    val phoneNumber: String,
    val status: UserStatusState,
    val profilePhoto: ProfilePhotoModel?,
    // emojiStatus
    val isContact: Boolean,
    val isMutualContact: Boolean,
    val isCloseFriend: Boolean,
    val isVerified: Boolean,
    val isPremium: Boolean,
    val isSupport: Boolean,
    val restrictionReason: String,
    val isScam: Boolean,
    val isFake: Boolean,
    val hasActiveStories: Boolean,
    val hasUnreadActiveStories: Boolean,
    val haveAccess: Boolean,
    // type
    val languageCode: String,
    val addedToAttachmentMenu: Boolean,
)

data class UsernamesModel(
    val activeUsernames: List<String>,
    val disabledUsernames: List<String>,
    val editableUsername: String,
)


fun TdApi.User.map() = UserModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    usernames = usernames?.map(),
    phoneNumber = phoneNumber,
    status = status.map(),
    profilePhoto = profilePhoto?.map(),
    isContact = isContact,
    isMutualContact = isMutualContact,
    isCloseFriend = isCloseFriend,
    isVerified = isVerified,
    isPremium = isPremium,
    isSupport = isSupport,
    restrictionReason = restrictionReason,
    isScam = isScam,
    isFake = isFake,
    hasActiveStories = hasActiveStories,
    hasUnreadActiveStories = hasUnreadActiveStories,
    haveAccess = haveAccess,
    languageCode = languageCode,
    addedToAttachmentMenu = addedToAttachmentMenu,
)

fun TdApi.Usernames.map() = UsernamesModel(
    activeUsernames = activeUsernames.toList(),
    disabledUsernames = disabledUsernames.toList(),
    editableUsername = editableUsername,
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