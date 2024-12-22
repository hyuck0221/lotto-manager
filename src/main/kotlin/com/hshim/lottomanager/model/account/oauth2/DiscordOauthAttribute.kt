package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.database.account.DiscordUser
import com.hshim.lottomanager.enums.account.DiscordPremiumType

class DiscordOauthAttribute(
    val id: String,
    val username: String,
    val avatar: String?,
    val discriminator: String,
    val publicFlags: Int,
    val flags: Int,
    val banner: String?,
    val accentColor: String?,
    val globalName: String?,
    val avatarDecorationData: String?,
    val bannerColor: String?,
    val clan: String?,
    val primaryGuild: String?,
    val mfaEnabled: Boolean,
    val locale: String,
    val premiumType: Int,
) {
    val premiumTypeEnum = DiscordPremiumType.getByValue(premiumType)

    constructor(attributeMap: Map<String, Any>) : this(
        id = attributeMap["id"] as String,
        username = attributeMap["username"] as String,
        avatar = attributeMap["avatar"] as String?,
        discriminator = attributeMap["discriminator"] as String,
        publicFlags = attributeMap["public_flags"] as Int,
        flags = attributeMap["flags"] as Int,
        banner = attributeMap["banner"] as String?,
        accentColor = attributeMap["accent_color"] as String?,
        globalName = attributeMap["global_name"] as String?,
        avatarDecorationData = attributeMap["avatar_decoration_data"] as String?,
        bannerColor = attributeMap["banner_color"] as String?,
        clan = attributeMap["clan"] as String?,
        primaryGuild = attributeMap["primary_guild"] as String?,
        mfaEnabled = attributeMap["mfa_enabled"] as Boolean,
        locale = attributeMap["locale"] as String,
        premiumType = attributeMap["premium_type"] as Int,
    )

    fun toEntity() = DiscordUser(
        discordId = this.id,
        username = this.username,
        globalName = this.globalName ?: this.username,
        avatar = this.avatar,
        banner = this.banner,
        premiumType = this.premiumTypeEnum,
    )

    fun updateTo(discordUser: DiscordUser) {
        discordUser.username = this.username
        discordUser.globalName = this.globalName ?: this.username
        discordUser.avatar = this.avatar
        discordUser.banner = this.banner
        discordUser.premiumType = this.premiumTypeEnum
        discordUser.displayName = this.globalName ?: this.username
        discordUser.profileUrl = this.avatar?.let { "https://cdn.discordapp.com/avatars/$id/$it" }
    }
}