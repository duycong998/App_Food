package com.sunasterisk.appfood.screen.login

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.TransitionInflater
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.screen.logup.FragmentRegistration
import com.sunasterisk.appfood.screen.main.tabLayout.MainPageFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSignUp.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, FragmentRegistration())
                .addSharedElement(imageAnime, imageAnime.transitionName)
                .addSharedElement(textView, textView.transitionName)
                .addSharedElement(edittextUserName, edittextUserName.transitionName)
                .addSharedElement(edittextPassWord, edittextPassWord.transitionName)
                .addSharedElement(buttonGo, buttonGo.transitionName)
                .addSharedElement(buttonSignUp, buttonSignUp.transitionName)
            fragmentTransaction.commit()
        }

        buttonGo.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, MainPageFragment.newInstance())
            fragmentTransaction.commit()
        }
    }
}
