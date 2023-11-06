package com.example.financemanager.profile.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.financemanager.R
import com.example.financemanager.databinding.FragmentProfileBinding
import com.example.financemanager.profile.card.dto.ProfileCardItem
import com.example.financemanager.profile.fragment.dto.ProfileScreenState

class ProfileFragment : Fragment(R.layout.fragment_profile) {

  private var _viewBinding: FragmentProfileBinding? = null
  private val viewBinding: FragmentProfileBinding
    get() {
      try {
        return _viewBinding!!
      } catch (e: Throwable) {
        Log.d("ProfileFragment", "Attempt to access uninitialized viewBinding!")
        throw e
      }
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return FragmentProfileBinding.inflate(inflater, container, false)
      .also { _viewBinding = it }
      .root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewBinding.fragmentProfileAchievementsView.onItemClick = {
      // do nothing
    }
    viewBinding.fragmentProfileDocumentsView.onItemClick = {
      // do nothing
    }
    viewBinding.fragmentProfileAddDocumentBtn.setOnClickListener {
      // do nothing
    }

    render(ProfileScreenState.stub)
  }

  private fun render(state: ProfileScreenState) {
    viewBinding.fragmentProfileTitleTv.text = state.title
    viewBinding.fragmentProfileAchievementsView.items = state.achievements
    viewBinding.fragmentProfileDocumentsView.items = state.documents
  }
}

private val ProfileScreenState.Companion.stub: ProfileScreenState
  get() = ProfileScreenState(
    title = "NAME",
    achievements = listOf(
      ProfileCardItem(
        id = "0",
        iconRes = R.drawable.ic_ufo_shielded
      ),
      ProfileCardItem(
        id = "1",
        iconRes = R.drawable.ic_gamepad_shielded
      )
    ),
    documents = listOf(
      ProfileCardItem(
        id = "0",
        iconRes = R.drawable.ic_passport
      ),
      ProfileCardItem(
        id = "1",
        iconRes = R.drawable.ic_card
      )
    )
  )