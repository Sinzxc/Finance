package com.example.financemanager.profile.card.dto

import androidx.annotation.DrawableRes

data class ProfileCardItem(
  val id: String = "",
  @DrawableRes val iconRes: Int = 0
)