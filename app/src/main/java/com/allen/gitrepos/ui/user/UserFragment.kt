package com.allen.gitrepos.ui.user

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.allen.gitrepos.AppExectors
import com.allen.gitrepos.di.Injectable
import com.allen.gitrepos.utils.autoCleared
import javax.inject.Inject

class UserFragment:Fragment(),Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExectors: AppExectors




}