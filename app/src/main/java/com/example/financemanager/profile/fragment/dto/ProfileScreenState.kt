package com.example.financemanager.profile.fragment.dto

import com.example.financemanager.profile.card.dto.ProfileCardItem

data class ProfileScreenState(
  val title: String = "",
  val achievements: List<ProfileCardItem> = emptyList(),
  val documents: List<ProfileCardItem> = emptyList()
) {

  companion object
}