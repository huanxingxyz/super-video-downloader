package com.myAllVideoBrowser.ui.main.home.browser.homeTab

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myAllVideoBrowser.data.local.model.Suggestion
import com.myAllVideoBrowser.data.local.room.entity.PageInfo
import com.myAllVideoBrowser.ui.compose.HomeTabScreen
import com.myAllVideoBrowser.ui.main.home.MainViewModel
import com.myAllVideoBrowser.ui.main.home.browser.BrowserListener
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.myAllVideoBrowser.ui.main.home.browser.BrowserViewModel
import com.myAllVideoBrowser.ui.main.home.browser.TabManagerProvider
import com.myAllVideoBrowser.ui.main.home.browser.BaseWebTabFragment
import com.myAllVideoBrowser.ui.main.home.browser.webTab.WebTabFactory

interface BrowserHomeListener : BrowserListener {

    override fun onBrowserReloadClicked() {
    }

    override fun onTabCloseClicked() {
    }

    override fun onBrowserStopClicked() {
    }

    override fun onBrowserBackClicked() {
    }

    override fun onBrowserForwardClicked() {
    }
}

class BrowserHomeFragment : BaseWebTabFragment() {

    companion object {
        fun newInstance() = BrowserHomeFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: BrowserHomeViewModel

    private lateinit var mainViewModel: MainViewModel

    private lateinit var browserViewModel: BrowserViewModel

    private lateinit var openPageIProvider: TabManagerProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel = mainActivity.mainViewModel
        homeViewModel = ViewModelProvider(this, viewModelFactory)[BrowserHomeViewModel::class.java]
        browserViewModel = ViewModelProvider(requireParentFragment(), viewModelFactory)[BrowserViewModel::class.java]
        openPageIProvider = mainActivity.mainViewModel.browserServicesProvider!!

        return androidx.compose.ui.platform.ComposeView(requireContext()).apply {
            buildWebTabMenu(this, true)
            setContent {
                var tabsCount by androidx.compose.runtime.remember { 
                    androidx.compose.runtime.mutableIntStateOf(browserViewModel.tabs.get()?.size ?: 0) 
                }
                
                androidx.compose.runtime.DisposableEffect(browserViewModel.tabs) {
                    val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
                        override fun onPropertyChanged(sender: androidx.databinding.Observable?, propertyId: Int) {
                            tabsCount = browserViewModel.tabs.get()?.size ?: 0
                        }
                    }
                    browserViewModel.tabs.addOnPropertyChangedCallback(callback)
                    onDispose {
                        browserViewModel.tabs.removeOnPropertyChangedCallback(callback)
                    }
                }

                HomeTabScreen(
                    tabCount = tabsCount,
                    onSearch = { input ->
                        openNewTab(input)
                    },
                    onSiteClick = { url ->
                        openNewTab(url)
                    },
                    onHelpClick = {
                        navigateToHelp()
                    },
                    onSettingsClick = {
                        // TODO: Navigate to settings
                    },
                    onMenuClick = {
                        showPopupMenu()
                    },
                    onTabCountClick = {
                        mainViewModel.openNavDrawerEvent.call()
                    },
                    onDownloadMethodClick = {
                        navigateToHelp()
                    }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleFirstStartGuide()

        homeViewModel.start()
        val openingUrl = mainViewModel.openedUrl.get()
        val openingText = mainViewModel.openedText.get()

        if (openingUrl != null) {
            openNewTab(openingUrl)
            mainViewModel.openedUrl.set(null)
        }

        if (openingText != null) {
            openNewTab(openingText)
            mainViewModel.openedText.set(null)
        }
    }

    // Bug fix for not updating home page grid after adding new bookmark
    override fun onResume() {
        super.onResume()
        val bookmarksList = mainViewModel.bookmarksList.get()?.toMutableList()
        mainViewModel.bookmarksList.set(bookmarksList)
    }

    private fun openNewTab(input: String) {
        if (input.isNotEmpty()) {
            openPageIProvider.getOpenTabEvent().value = WebTabFactory.createWebTabFromInput(input)
        }
    }

    private fun handleFirstStartGuide() {
        if (mainActivity.sharedPrefHelper.getIsFirstStart()) {
            mainActivity.settingsViewModel.setIsFirstStart(false)
            navigateToHelp()
        }
    }

    override fun shareWebLink() {}

    override fun bookmarkCurrentUrl() {}
}
