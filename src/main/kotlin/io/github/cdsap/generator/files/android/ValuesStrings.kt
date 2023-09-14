package io.github.cdsap.generator.files.android

class ValuesStrings {
    fun get() = """
        <resources>
            <string name="app_name">My Application</string>
                <!-- Content description (not visible, for screen readers etc.): "Three dot" menu button. -->
                <string name="content_description_menu">More options</string>
                <!-- Content description (not visible, for screen readers etc.): "Private Browsing" menu button. -->
                <string name="content_description_private_browsing_button">Enable private browsing</string>
                <!-- Content description (not visible, for screen readers etc.): "Private Browsing" menu button. -->
                <string name="content_description_disable_private_browsing_button">Disable private browsing</string>
                <!-- Placeholder text shown in the search bar before a user enters text -->
                <string name="search_hint">Search or enter address</string>
                <!-- Placeholder text shown in search bar when using history search -->
                <string name="history_search_hint">Search history</string>
                <!-- Placeholder text shown in search bar when using bookmarks search -->
                <string name="bookmark_search_hint">Search bookmarks</string>
                <!-- Placeholder text shown in search bar when using tabs search -->
                <string name="tab_search_hint">Search tabs</string>
                <!-- Placeholder text shown in the search bar when using application search engines -->
                <string name="application_search_hint">Enter search terms</string>
                <!-- No Open Tabs Message Description -->
                <string name="no_open_tabs_description">Your open tabs will be shown here.</string>
                <!-- No Private Tabs Message Description -->
                <string name="no_private_tabs_description">Your private tabs will be shown here.</string>
                <!-- Tab tray multi select title in app bar. The first parameter is the number of tabs selected -->
                <string name="tab_tray_multi_select_title">%1b selected</string>
                <!-- Label of button in create collection dialog for creating a new collection  -->
                <string name="tab_tray_add_new_collection">Add new collection</string>
                <!-- Label of editable text in create collection dialog for naming a new collection  -->
                <string name="tab_tray_add_new_collection_name">Name</string>
                <!-- Label of button in save to collection dialog for selecting a current collection  -->
                <string name="tab_tray_select_collection">Select collection</string>
                <!-- Content description for close button while in multiselect mode in tab tray -->
                <string name="tab_tray_close_multiselect_content_description">Exit multiselect mode</string>
                <!-- Content description for save to collection button while in multiselect mode in tab tray -->
                <string name="tab_tray_collection_button_multiselect_content_description">Save selected tabs to collection</string>
                <!-- Content description on checkmark while tab is selected in multiselect mode in tab tray -->
                <string name="tab_tray_multiselect_selected_content_description">Selected</string>

                <!-- Home - Recently saved bookmarks -->
                <!-- Title for the home screen section with recently saved bookmarks. -->
                <string name="recently_saved_title">Recently saved</string>
                <!-- Content description for the button which navigates the user to show all of their saved bookmarks. -->
                <string name="recently_saved_show_all_content_description_2">Show all saved bookmarks</string>
                <!-- Text for the menu button to remove a recently saved bookmark from the user's home screen -->
                <string name="recently_saved_menu_item_remove">Remove</string>

                <!-- About content. The first parameter is the name of the application. (For example: Fenix) -->
                <string name="about_content">%1a is produced by Mozilla.</string>

                <!-- Private Browsing -->
                <!-- Explanation for private browsing displayed to users on home view when they first enable private mode
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="private_browsing_placeholder_description_2">
                    %1a clears your search and browsing history from private tabs when you close them or quit the app. While this doesn’t make you anonymous to websites or your internet service provider, it makes it easier to keep what you do online private from anyone else who uses this device.</string>
                <string name="private_browsing_common_myths">
                   Common myths about private browsing
                </string>

                <!-- Private mode shortcut "contextual feature recommendation" (CFR) -->
                <!-- Text for the main message -->
                <string name="cfr_message" >Add a shortcut to open private tabs from your Home screen.</string>
                <!-- Text for the Private mode shortcut CFR message for adding a private mode shortcut to open private tabs from the Home screen -->
                <string name="private_mode_cfr_message">Launch next private tab in one tap.</string>
                <!-- Text for the positive button -->
                <string name="cfr_pos_button_text" >Add shortcut</string>
                <!-- Text for the positive button to accept adding a Private Browsing shortcut to the Home screen -->
                <string name="private_mode_cfr_pos_button_text">Add to Home screen</string>
                <!-- Text for the negative button to decline adding a Private Browsing shortcut to the Home screen -->
                <string name="cfr_neg_button_text">No thanks</string>

                <!-- Open in App "contextual feature recommendation" (CFR) -->
                <!-- Text for the info message. The first parameter is the name of the application.-->
                <string name="open_in_app_cfr_info_message_2">You can set %1a to automatically open links in apps.</string>
                <!-- Text for the positive action button -->
                <string name="open_in_app_cfr_positive_button_text">Go to settings</string>
                <!-- Text for the negative action button -->
                <string name="open_in_app_cfr_negative_button_text">Dismiss</string>

                <!-- Content description for close button used in "contextual feature recommendation" (CFR) popups -->
                <string name="cfr_dismiss_button_default_content_description" >Dismiss</string>

                <!-- Total cookie protection "contextual feature recommendation" (CFR) -->
                <!-- Text for the message displayed in the contextual feature recommendation popup promoting the total cookie protection feature. -->
                <string name="tcp_cfr_message">Our most powerful privacy feature yet isolates cross-site trackers.</string>
                <!-- Text displayed that links to website containing documentation about the "Total cookie protection" feature. -->
                <string name="tcp_cfr_learn_more">Learn about Total Cookie Protection</string>

                <!-- Text for the info dialog when camera permissions have been denied but user tries to access a camera feature. -->
                <string name="camera_permissions_needed_message">Camera access needed. Go to Android settings, tap permissions, and tap allow.</string>
                <!-- Text for the positive action button to go to Android Settings to grant permissions. -->
                <string name="camera_permissions_needed_positive_button_text">Go to settings</string>
                <!-- Text for the negative action button to dismiss the dialog. -->
                <string name="camera_permissions_needed_negative_button_text">Dismiss</string>

                <!-- Text for the banner message to tell users about our auto close feature. -->
                <string name="tab_tray_close_tabs_banner_message">Set open tabs to close automatically that haven’t been viewed in the past day, week, or month.</string>
                <!-- Text for the positive action button to go to Settings for auto close tabs. -->
                <string name="tab_tray_close_tabs_banner_positive_button_text">View options</string>
                <!-- Text for the negative action button to dismiss the Close Tabs Banner. -->
                <string name="tab_tray_close_tabs_banner_negative_button_text">Dismiss</string>
                <!-- Text for the banner message to tell users about our inactive tabs feature. -->
                <string name="tab_tray_inactive_onboarding_message">Tabs you haven’t viewed for two weeks get moved here.</string>
                <!-- Text for the action link to go to Settings for inactive tabs. -->
                <string name="tab_tray_inactive_onboarding_button_text">Turn off in settings</string>
                <!-- Text for title for the auto-close dialog of the inactive tabs. -->
                <string name="tab_tray_inactive_auto_close_title">Auto-close after one month?</string>
                <!-- Text for the body for the auto-close dialog of the inactive tabs.
                    The first parameter is the name of the application.-->
                <string name="tab_tray_inactive_auto_close_body_2">%1a can close tabs you haven’t viewed over the past month.</string>
                <!-- Content description for close button in the auto-close dialog of the inactive tabs. -->
                <string name="tab_tray_inactive_auto_close_button_content_description">Close</string>

                <!-- Text for turn on auto close tabs button in the auto-close dialog of the inactive tabs. -->
                <string name="tab_tray_inactive_turn_on_auto_close_button_2">Turn on auto-close</string>


                <!-- Home screen icons - Long press shortcuts -->
                <!-- Shortcut action to open new tab -->
                <string name="home_screen_shortcut_open_new_tab_2">New tab</string>
                <!-- Shortcut action to open new private tab -->
                <string name="home_screen_shortcut_open_new_private_tab_2">New private tab</string>

                <!-- Recent Tabs -->
                <!-- Header text for jumping back into the recent tab in the home screen -->
                <string name="recent_tabs_header">Jump back in</string>
                <!-- Button text for showing all the tabs in the tabs tray -->
                <string name="recent_tabs_show_all">Show all</string>
                <!-- Content description for the button which navigates the user to show all recent tabs in the tabs tray. -->
                <string name="recent_tabs_show_all_content_description_2">Show all recent tabs button</string>
                <!-- Text for button in synced tab card that opens synced tabs tray -->
                <string name="recent_tabs_see_all_synced_tabs_button_text">See all synced tabs</string>
                <!-- Accessibility description for device icon used for recent synced tab -->
                <string name="recent_tabs_synced_device_icon_content_description">Synced device</string>
                <!-- Text for the dropdown menu to remove a recent synced tab from the homescreen -->
                <string name="recent_synced_tab_menu_item_remove">Remove</string>
                <!-- Text for the menu button to remove a grouped highlight from the user's browsing history
                     in the Recently visited section -->
                <string name="recent_tab_menu_item_remove">Remove</string>

                <!-- History Metadata -->
                <!-- Header text for a section on the home screen that displays grouped highlights from the
                     user's browsing history, such as topics they have researched or explored on the web -->
                <string name="history_metadata_header_2">Recently visited</string>
                <!-- Text for the menu button to remove a grouped highlight from the user's browsing history
                     in the Recently visited section -->
                <string name="recently_visited_menu_item_remove">Remove</string>
                <!-- Content description for the button which navigates the user to show all of their history. -->
                <string name="past_explorations_show_all_content_description_2">Show all past explorations</string>

                <!-- Browser Fragment -->
                <!-- Content description (not visible, for screen readers etc.): Navigate backward (browsing history) -->
                <string name="browser_menu_back">Back</string>
                <!-- Content description (not visible, for screen readers etc.): Navigate forward (browsing history) -->
                <string name="browser_menu_forward">Forward</string>
                <!-- Content description (not visible, for screen readers etc.): Refresh current website -->
                <string name="browser_menu_refresh">Refresh</string>
                <!-- Content description (not visible, for screen readers etc.): Stop loading current website -->
                <string name="browser_menu_stop">Stop</string>
                <!-- Browser menu button that opens the addon manager -->
                <string name="browser_menu_add_ons">Add-ons</string>
                <!-- Browser menu button that opens account settings -->
                <string name="browser_menu_account_settings">Account info</string>
                <!-- Text displayed when there are no add-ons to be shown -->
                <string name="no_add_ons">No add-ons here</string>
                <!-- Browser menu button that sends a user to help articles -->
                <string name="browser_menu_help">Help</string>
                <!-- Browser menu button that sends a to a the what's new article -->
                <string name="browser_menu_whats_new">What’s new</string>
                <!-- Browser menu button that opens the settings menu -->
                <string name="browser_menu_settings">Settings</string>
                <!-- Browser menu button that opens a user's library -->
                <string name="browser_menu_library">Library</string>
                <!-- Browser menu toggle that requests a desktop site -->
                <string name="browser_menu_desktop_site">Desktop site</string>
                <!-- Browser menu toggle that adds a shortcut to the site on the device home screen. -->
                <string name="browser_menu_add_to_homescreen">Add to Home screen</string>
                <!-- Browser menu toggle that installs a Progressive Web App shortcut to the site on the device home screen. -->
                <string name="browser_menu_install_on_homescreen">Install</string>
                <!-- Content description (not visible, for screen readers etc.) for the Resync tabs button -->
                <string name="resync_button_content_description">Resync</string>
                <!-- Browser menu button that opens the find in page menu -->
                <string name="browser_menu_find_in_page">Find in page</string>
                <!-- Browser menu button that saves the current tab to a collection -->
                <string name="browser_menu_save_to_collection_2">Save to collection</string>
                <!-- Browser menu button that open a share menu to share the current site -->
                <string name="browser_menu_share">Share</string>
                <!-- Browser menu button shown in custom tabs that opens the current tab in Fenix
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="browser_menu_open_in_fenix">Open in %1a</string>
                <!-- Browser menu text shown in custom tabs to indicate this is a Fenix tab
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="browser_menu_powered_by">POWERED BY %1a</string>
                <!-- Browser menu text shown in custom tabs to indicate this is a Fenix tab
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="browser_menu_powered_by2">Powered by %1a</string>
                <!-- Browser menu button to put the current page in reader mode -->
                <string name="browser_menu_read">Reader view</string>
                <!-- Browser menu button content description to close reader mode and return the user to the regular browser -->
                <string name="browser_menu_read_close">Close reader view</string>
                <!-- Browser menu button to open the current page in an external app -->
                <string name="browser_menu_open_app_link">Open in app</string>
                <!-- Browser menu button to show reader view appearance controls e.g. the used font type and size -->
                <string name="browser_menu_customize_reader_view">Customize reader view</string>
                <!-- Browser menu label for adding a bookmark -->
                <string name="browser_menu_add">Add</string>
                <!-- Browser menu label for editing a bookmark -->
                <string name="browser_menu_edit">Edit</string>
                <!-- Button shown on the home page that opens the Customize home settings -->
                <string name="browser_menu_customize_home_1">Customize homepage</string>
                <!-- Browser Toolbar -->
                <!-- Content description for the Home screen button on the browser toolbar -->
                <string name="browser_toolbar_home">Home screen</string>

                <!-- Locale Settings Fragment -->
                <!-- Content description for tick mark on selected language -->
                <string name="a11y_selected_locale_content_description">Selected language</string>
                <!-- Text for default locale item -->
                <string name="default_locale_text">Follow device language</string>
                <!-- Placeholder text shown in the search bar before a user enters text -->
                <string name="locale_search_hint">Search language</string>

                <!-- Search Fragment -->
                <!-- Button in the search view that lets a user search by scanning a QR code -->
                <string name="search_scan_button">Scan</string>
                <!-- Button in the search view that lets a user change their search engine -->
                <string name="search_engine_button">Search engine</string>
                <!-- Button in the search view when shortcuts are displayed that takes a user to the search engine settings -->
                <string name="search_shortcuts_engine_settings">Search engine settings</string>
                <!-- Button in the search view that lets a user navigate to the site in their clipboard -->
                <string name="awesomebar_clipboard_title">Fill link from clipboard</string>
                <!-- Button in the search suggestions onboarding that allows search suggestions in private sessions -->
                <string name="search_suggestions_onboarding_allow_button">Allow</string>
                <!-- Button in the search suggestions onboarding that does not allow search suggestions in private sessions -->
                <string name="search_suggestions_onboarding_do_not_allow_button">Don’t allow</string>
                <!-- Search suggestion onboarding hint title text -->
                <string name="search_suggestions_onboarding_title">Allow search suggestions in private sessions?</string>
                <!-- Search suggestion onboarding hint description text, first parameter is the name of the app defined in app_name (for example: Fenix)-->
                <string name="search_suggestions_onboarding_text">%s will share everything you type in the address bar with your default search engine.</string>
                <!-- Search engine suggestion title text. The first parameter is the name of teh suggested engine-->
                <string name="search_engine_suggestions_title">Search %s</string>
                <!-- Search engine suggestion description text -->
                <string name="search_engine_suggestions_description">Search directly from the address bar</string>
                <!-- Menu option in the search selector menu to open the search settings -->
                <string name="search_settings_menu_item">Search settings</string>
                <!-- Header text for the search selector menu -->
                <string name="search_header_menu_item" >This time search:</string>
                <!-- Header text for the search selector menu -->
                <string name="search_header_menu_item_2">This time search in:</string>

                <!-- Home onboarding -->
                <!-- Onboarding home screen popup dialog, shown on top of the Jump back in section. -->
                <string name="onboarding_home_screen_jump_back_contextual_hint_2">Meet your personalized homepage. Recent tabs, bookmarks, and search results will appear here.</string>
                <!-- Home onboarding dialog welcome screen title text. -->
                <string name="onboarding_home_welcome_title_2">Welcome to a more personal internet</string>
                <!-- Home onboarding dialog welcome screen description text. -->
                <string name="onboarding_home_welcome_description">More colors. Better privacy. Same commitment to people over profits.</string>
                <!-- Home onboarding dialog sign into sync screen title text. -->
                <string name="onboarding_home_sync_title_3">Switching screens is easier than ever</string>
                <!-- Home onboarding dialog sign into sync screen description text. -->
                <string name="onboarding_home_sync_description">Pick up where you left off with tabs from other devices now on your homepage.</string>
                <!-- Text for the button to continue the onboarding on the home onboarding dialog. -->
                <string name="onboarding_home_get_started_button">Get started</string>
                <!-- Text for the button to navigate to the sync sign in screen on the home onboarding dialog. -->
                <string name="onboarding_home_sign_in_button">Sign in</string>
                <!-- Text for the button to skip the onboarding on the home onboarding dialog. -->
                <string name="onboarding_home_skip_button">Skip</string>
                <!-- Onboarding home screen sync popup dialog message, shown on top of Recent Synced Tabs in the Jump back in section. -->
                <string name="sync_cfr_message">Your tabs are syncing! Pick up where you left off on your other device.</string>
                <!-- Content description (not visible, for screen readers etc.): Close button for the home onboarding dialog -->
                <string name="onboarding_home_content_description_close_button">Close</string>

                <!-- Notification pre-permission dialog -->
                <!-- Enable notification pre permission dialog title
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="onboarding_home_enable_notifications_title">Notifications help you do more with %s</string>
                <!-- Enable notification pre permission dialog description with rationale
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="onboarding_home_enable_notifications_description">Sync your tabs between devices, manage downloads, get tips about making the most of %s’s privacy protection, and more.</string>
                <!-- Text for the button to request notification permission on the device -->
                <string name="onboarding_home_enable_notifications_positive_button">Continue</string>
                <!-- Text for the button to not request notification permission on the device and dismiss the dialog -->
                <string name="onboarding_home_enable_notifications_negative_button">Not now</string>

                <!-- Search Widget -->
                <!-- Content description for searching with a widget. The first parameter is the name of the application.-->
                <string name="search_widget_content_description_2">Open a new %1a tab</string>
                <!-- Text preview for smaller sized widgets -->
                <string name="search_widget_text_short">Search</string>
                <!-- Text preview for larger sized widgets -->
                <string name="search_widget_text_long">Search the web</string>
                <!-- Content description (not visible, for screen readers etc.): Voice search -->
                <string name="search_widget_voice">Voice search</string>

                <!-- Preferences -->
                <!-- Title for the settings page-->
                <string name="settings">Settings</string>
                <!-- Preference category for general settings -->
                <string name="preferences_category_general">General</string>
                <!-- Preference category for all links about Fenix -->
                <string name="preferences_category_about">About</string>
                <!-- Preference for settings related to changing the default search engine -->
                <string name="preferences_default_search_engine">Default search engine</string>
                <!-- Preference for settings related to Search -->
                <string name="preferences_search">Search</string>
                <!-- Preference for settings related to Search address bar -->
                <string name="preferences_search_address_bar">Address bar</string>
                <!-- Preference link to rating Fenix on the Play Store -->
                <string name="preferences_rate">Rate on Google Play</string>
                <!-- Preference linking to about page for Fenix
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="preferences_about">About %1a</string>
                <!-- Preference for settings related to changing the default browser -->
                <string name="preferences_set_as_default_browser">Set as default browser</string>
                <!-- Preference category for advanced settings -->
                <string name="preferences_category_advanced">Advanced</string>
                <!-- Preference category for privacy and security settings -->
                <string name="preferences_category_privacy_security">Privacy and security</string>
                <!-- Preference for advanced site permissions -->
                <string name="preferences_site_permissions">Site permissions</string>
                <!-- Preference for private browsing options -->
                <string name="preferences_private_browsing_options">Private browsing</string>
                <!-- Preference for opening links in a private tab-->
                <string name="preferences_open_links_in_a_private_tab">Open links in a private tab</string>
                <!-- Preference for allowing screenshots to be taken while in a private tab-->
                <string name="preferences_allow_screenshots_in_private_mode">Allow screenshots in private browsing</string>
                <!-- Will inform the user of the risk of activating Allow screenshots in private browsing option -->
                <string name="preferences_screenshots_in_private_mode_disclaimer">If allowed, private tabs will also be visible when multiple apps are open</string>
                <!-- Preference for adding private browsing shortcut -->
                <string name="preferences_add_private_browsing_shortcut">Add private browsing shortcut</string>
                <!-- Preference for enabling "HTTPS-Only" mode -->
                <string name="preferences_https_only_title">HTTPS-Only Mode</string>

                <!-- Preference for removing cookie/consent banners from sites automatically. See reduce_cookie_banner_summary for additional context. -->
                <string name="preferences_cookie_banner_reduction">Cookie Banner Reduction</string>
                <!-- Preference for rejecting or removing as many cookie/consent banners as possible on sites. See reduce_cookie_banner_summary for additional context. -->
                <string name="reduce_cookie_banner_option">Reduce cookie banners</string>
                <!-- Summary of cookie banner handling preference if the setting disabled is set to off -->
                <string name="reduce_cookie_banner_option_off">Off</string>
                <!-- Summary of cookie banner handling preference if the setting enabled is set to on -->
                <string name="reduce_cookie_banner_option_on">On</string>
                <!-- Summary for the preference for rejecting all cookies whenever possible. -->
                <string name="reduce_cookie_banner_summary" >Firefox automatically tries to reject cookie requests on cookie banners. If a reject option isn’t available, Firefox may accept all cookies to dismiss the banner.</string>
                <!-- Summary for the preference for rejecting all cookies whenever possible. The first parameter is the application name -->
                <string name="reduce_cookie_banner_summary_1">%1a automatically tries to reject cookie requests on cookie banners.</string>
                <!-- Text for indicating cookie banner handling is off this site, this is shown as part of the protections panel with the tracking protection toggle -->
                <string name="reduce_cookie_banner_off_for_site">Off for this site</string>
                <!-- Text for indicating cookie banner handling is on this site, this is shown as part of the protections panel with the tracking protection toggle -->
                <string name="reduce_cookie_banner_on_for_site">On for this site</string>
                <!-- Text for indicating cookie banner handling is currently not supported for this site, this is shown as part of the protections panel with the tracking protection toggle -->
                <string name="reduce_cookie_banner_unsupported_site" >Site currently not supported</string>
                <!-- Title text for a detail explanation indicating cookie banner handling is on this site, this is shown as part of the cookie banner panel in the toolbar. The first parameter is a shortened URL of the current site-->
                <string name="reduce_cookie_banner_details_panel_title_on_for_site">Turn on Cookie Banner Reduction for %1a?</string>
                <!-- Title text for a detail explanation indicating cookie banner handling is off this site, this is shown as part of the cookie banner panel in the toolbar. The first parameter is a shortened URL of the current site-->
                <string name="reduce_cookie_banner_details_panel_title_off_for_site">Turn off Cookie Banner Reduction for %1a?</string>
                <!-- Long text for a detail explanation indicating what will happen if cookie banner handling is off for a site, this is shown as part of the cookie banner panel in the toolbar. The first parameter is the application name -->
                <string name="reduce_cookie_banner_details_panel_description_off_for_site">%1a will clear this site’s cookies and refresh the page. Clearing all cookies may sign you out or empty shopping carts.</string>
                <!-- Long text for a detail explanation indicating what will happen if cookie banner handling is on for a site, this is shown as part of the cookie banner panel in the toolbar. The first and second parameter are the application name -->
                <string name="reduce_cookie_banner_details_panel_description_on_for_site" >%1a can try to automatically reject cookie requests. If a reject option isn’t available, %2a may accept all cookies to dismiss the banner.</string>
                <!-- Long text for a detail explanation indicating what will happen if cookie banner handling is on for a site, this is shown as part of the cookie banner panel in the toolbar. The first parameter are the application name -->
                <string name="reduce_cookie_banner_details_panel_description_on_for_site_1"  >%1a can try to automatically reject cookie requests.</string>
                <!-- Long text for a detail explanation indicating what will happen if cookie banner handling is on for a site, this is shown as part of the cookie banner panel in the toolbar. The first parameter is the application name -->
                <string name="reduce_cookie_banner_details_panel_description_on_for_site_2">%1a tries to automatically reject all cookie requests on supported sites.</string>
                <!-- Title text for the dialog use on the control branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_control_experiment_dialog_title">Cookie banners begone!</string>
                <!-- Body text for the dialog use on the control branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_control_experiment_dialog_body" >Automatically reject cookie requests, when possible. Otherwise, accept all cookies to dismiss cookie banners.</string>
                <!-- Body text for the dialog use on the control branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_control_experiment_dialog_body_1" >Automatically reject cookie requests, when possible.</string>
                <!-- Body text for the dialog use on the control branch of the experiment to determine which context users engaged the most.The first parameter is the application name -->
                <string name="reduce_cookie_banner_control_experiment_dialog_body_2">Allow %1a to automatically reject cookie requests when possible?</string>
                <!-- Remind me later text button for the onboarding dialog -->
                <string name="reduce_cookie_banner_dialog_not_now_button">Not Now</string>
                <!-- Change setting text button, for the dialog use on the control branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_control_experiment_dialog_change_setting_button">Dismiss banners</string>
                <!-- Snack text for the cookie banner dialog, after user hit the dismiss banner button -->
                <string name="reduce_cookie_banner_dialog_snackbar_text">You’ll see fewer cookie requests</string>
                <!-- Title text for the dialog use on the variant 1 branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_variant_1_experiment_dialog_title">See fewer cookie pop-ups</string>
                <!-- Body text for the dialog use on the variant 1 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="reduce_cookie_banner_variant_1_experiment_dialog_body" >Automatically answer cookie pop-ups for distraction-free browsing. %1a will reject all requests if possible, or accept all if not.</string>
                <!-- Body text for the dialog use on the variant 1 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="reduce_cookie_banner_variant_1_experiment_dialog_body_1">Automatically answer cookie pop-ups for distraction-free browsing. %1a will reject all requests if possible.</string>
                <!-- Change setting text button, for the onboarding dialog use on the variant 1 branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_variant_1_experiment_dialog_change_setting_button">Dismiss Pop-ups</string>
                <!-- Title text for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_variant_2_experiment_dialog_title">Cookie Banner Reduction</string>
                <!-- Body text for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="reduce_cookie_banner_variant_2_experiment_dialog_body" >Allow %1a to decline a site’s cookie consent request if possible or accept cookie access when not possible?</string>
                <!-- Body text for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="reduce_cookie_banner_variant_2_experiment_dialog_body_1">Allow %1a to decline a site’s cookie consent request if possible?</string>
                <!-- Change setting text button, for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most -->
                <string name="reduce_cookie_banner_variant_2_experiment_dialog_change_setting_button">Allow</string>

                <!-- Description of the preference to enable "HTTPS-Only" mode. -->
                <string name="preferences_https_only_summary">Automatically attempts to connect to sites using HTTPS encryption protocol for increased security.</string>
                <!-- Summary of tracking protection preference if tracking protection is set to on -->
                <string name="preferences_https_only_on" >On</string>
                <!-- Summary of https only preference if https only is set to off -->
                <string name="preferences_https_only_off">Off</string>
                <!-- Summary of https only preference if https only is set to on in all tabs -->
                <string name="preferences_https_only_on_all">On in all tabs</string>
                <!-- Summary of https only preference if https only is set to on in private tabs only -->
                <string name="preferences_https_only_on_private">On in private tabs</string>
                <!-- Text displayed that links to website containing documentation about "HTTPS-Only" mode -->
                <string name="preferences_http_only_learn_more">Learn more</string>
                <!-- Option for the https only setting -->
                <string name="preferences_https_only_in_all_tabs">Enable in all tabs</string>
                <!-- Option for the https only setting -->
                <string name="preferences_https_only_in_private_tabs">Enable only in private tabs</string>
                <!-- Title shown in the error page for when trying to access a http website while https only mode is enabled. -->
                <string name="errorpage_httpsonly_title">Secure site not available</string>
                <!-- Message shown in the error page for when trying to access a http website while https only mode is enabled. The message has two paragraphs. This is the first. -->
                <string name="errorpage_httpsonly_message_title">Most likely, the website simply does not support HTTPS.</string>
                <!-- Message shown in the error page for when trying to access a http website while https only mode is enabled. The message has two paragraphs. This is the second. -->
                <string name="errorpage_httpsonly_message_summary">However, it’s also possible that an attacker is involved. If you continue to the website, you should not enter any sensitive info. If you continue, HTTPS-Only mode will be turned off temporarily for the site.</string>
                <!-- Preference for accessibility -->
                <string name="preferences_accessibility">Accessibility</string>
                <!-- Preference to override the Firefox Account server -->
                <string name="preferences_override_fxa_server">Custom Firefox Account server</string>
                <!-- Preference to override the Sync token server -->
                <string name="preferences_override_sync_tokenserver">Custom Sync server</string>
                <!-- Toast shown after updating the FxA/Sync server override preferences -->
                <string name="toast_override_fxa_sync_server_done">Firefox Account/Sync server modified. Quitting the application to apply changes…</string>
                <!-- Preference category for account information -->
                <string name="preferences_category_account">Account</string>
                <!-- Preference for changing where the toolbar is positioned -->
                <string name="preferences_toolbar">Toolbar</string>
                <!-- Preference for changing default theme to dark or light mode -->
                <string name="preferences_theme">Theme</string>
                <!-- Preference for customizing the home screen -->
                <string name="preferences_home_2">Homepage</string>
                <!-- Preference for gestures based actions -->
                <string name="preferences_gestures">Gestures</string>
                <!-- Preference for settings related to visual options -->
                <string name="preferences_customize">Customize</string>
                <!-- Preference description for banner about signing in -->
                <string name="preferences_sign_in_description_2">Sign in to sync tabs, bookmarks, passwords, and more.</string>
                <!-- Preference shown instead of account display name while account profile information isn't available yet. -->
                <string name="preferences_account_default_name">Firefox Account</string>
                <!-- Preference text for account title when there was an error syncing FxA -->
                <string name="preferences_account_sync_error">Reconnect to resume syncing</string>
                <!-- Preference for language -->
                <string name="preferences_language">Language</string>
                <!-- Preference for data choices -->
                <string name="preferences_data_choices">Data choices</string>
                <!-- Preference for data collection -->
                <string name="preferences_data_collection">Data collection</string>
                <!-- Preference for developers -->
                <string name="preferences_remote_debugging">Remote debugging via USB</string>
                <!-- Preference title for switch preference to show search engines -->
                <string name="preferences_show_search_engines">Show search engines</string>
                <!-- Preference title for switch preference to show search suggestions -->
                <string name="preferences_show_search_suggestions">Show search suggestions</string>
                <!-- Preference title for switch preference to show voice search button -->
                <string name="preferences_show_voice_search">Show voice search</string>
                <!-- Preference title for switch preference to show search suggestions also in private mode -->
                <string name="preferences_show_search_suggestions_in_private">Show in private sessions</string>
                <!-- Preference title for switch preference to show a clipboard suggestion when searching -->
                <string name="preferences_show_clipboard_suggestions">Show clipboard suggestions</string>
                <!-- Preference title for switch preference to suggest browsing history when searching -->
                <string name="preferences_search_browsing_history">Search browsing history</string>
                <!-- Preference title for switch preference to suggest bookmarks when searching -->
                <string name="preferences_search_bookmarks">Search bookmarks</string>
                <!-- Preference title for switch preference to suggest synced tabs when searching -->
                <string name="preferences_search_synced_tabs">Search synced tabs</string>
                <!-- Preference for account settings -->
                <string name="preferences_account_settings">Account settings</string>
                <!-- Preference for enabling url autocomplete-->
                <string name="preferences_enable_autocomplete_urls">Autocomplete URLs</string>
                <!-- Preference for open links in third party apps -->
                <string name="preferences_open_links_in_apps">Open links in apps</string>
                <!-- Preference for open download with an external download manager app -->
                <string name="preferences_external_download_manager">External download manager</string>
                <!-- Preference for add_ons -->
                <string name="preferences_addons">Add-ons</string>
                <!-- Preference for notifications -->
                <string name="preferences_notifications">Notifications</string>
                <!-- Summary for notification preference indicating notifications are allowed -->
                <string name="notifications_allowed_summary">Allowed</string>
                <!-- Summary for notification preference indicating notifications are not allowed -->
                <string name="notifications_not_allowed_summary">Not allowed</string>

                <!-- Add-on Preferences -->
                <!-- Preference to customize the configured AMO (addons.mozilla.org) collection -->
                <string name="preferences_customize_amo_collection">Custom Add-on collection</string>
                <!-- Button caption to confirm the add-on collection configuration -->
                <string name="customize_addon_collection_ok">OK</string>
                <!-- Button caption to abort the add-on collection configuration -->
                <string name="customize_addon_collection_cancel">Cancel</string>
                <!-- Hint displayed on input field for custom collection name -->
                <string name="customize_addon_collection_hint">Collection name</string>
                <!-- Hint displayed on input field for custom collection user ID-->
                <string name="customize_addon_collection_user_hint">Collection owner (User ID)</string>
                <!-- Toast shown after confirming the custom add-on collection configuration -->
                <string name="toast_customize_addon_collection_done">Add-on collection modified. Quitting the application to apply changes…</string>

                <!-- Customize Home -->
                <!-- Header text for jumping back into the recent tab in customize the home screen -->
                <string name="customize_toggle_jump_back_in">Jump back in</string>
                <!-- Title for the customize home screen section with recently saved bookmarks. -->
                <string name="customize_toggle_recent_bookmarks">Recent bookmarks</string>
                <!-- Title for the customize home screen section with recently visited. Recently visited is
                a section where users see a list of tabs that they have visited in the past few days -->
                <string name="customize_toggle_recently_visited">Recently visited</string>
                <!-- Title for the customize home screen section with Pocket. -->
                <string name="customize_toggle_pocket_2">Thought-provoking stories</string>
                <!-- Summary for the customize home screen section with Pocket. The first parameter is product name Pocket -->
                <string name="customize_toggle_pocket_summary">Articles powered by %s</string>
                <!-- Title for the customize home screen section with sponsored Pocket stories. -->
                <string name="customize_toggle_pocket_sponsored">Sponsored stories</string>
                <!-- Title for the opening wallpaper settings screen -->
                <string name="customize_wallpapers">Wallpapers</string>
                <!-- Title for the customize home screen section with sponsored shortcuts. -->
                <string name="customize_toggle_contile">Sponsored shortcuts</string>

                <!-- Wallpapers -->
                <!-- Content description for various wallpapers. The first parameter is the name of the wallpaper -->
                <string name="wallpapers_item_name_content_description">Wallpaper Item: %1a</string>
                <!-- Snackbar message for when wallpaper is selected -->
                <string name="wallpaper_updated_snackbar_message">Wallpaper updated!</string>
                <!-- Snackbar label for action to view selected wallpaper -->
                <string name="wallpaper_updated_snackbar_action">View</string>
                <!-- Snackbar message for when wallpaper couldn't be downloaded -->
                <string name="wallpaper_download_error_snackbar_message">Couldn’t download wallpaper</string>
                <!-- Snackbar label for action to retry downloading the wallpaper -->
                <string name="wallpaper_download_error_snackbar_action">Try again</string>
                <!-- Snackbar message for when wallpaper couldn't be selected because of the disk error -->
                <string name="wallpaper_select_error_snackbar_message">Couldn’t change wallpaper</string>
                <!-- Text displayed that links to website containing documentation about the "Limited Edition" wallpapers. -->
                <string name="wallpaper_learn_more">Learn more</string>
                <!-- Text for classic wallpapers title. The first parameter is the Firefox name. -->
                <string name="wallpaper_classic_title">Classic %s</string>
                <!-- Text for limited edition wallpapers title. -->
                <string name="wallpaper_limited_edition_title">Limited Edition</string>
                <!-- Description text for the limited edition wallpapers with learn more link. The first parameter is the learn more string defined in wallpaper_learn_more-->
                <string name="wallpaper_limited_edition_description_with_learn_more">The new Independent Voices collection. %s</string>
                <!-- Description text for the limited edition wallpapers. -->
                <string name="wallpaper_limited_edition_description">The new Independent Voices collection.</string>
                <!-- Wallpaper onboarding dialog header text. -->
                <string name="wallpapers_onboarding_dialog_title_text">Try a splash of color</string>
                <!-- Wallpaper onboarding dialog body text. -->
                <string name="wallpapers_onboarding_dialog_body_text">Choose a wallpaper that speaks to you.</string>
                <!-- Wallpaper onboarding dialog learn more button text. The button navigates to the wallpaper settings screen. -->
                <string name="wallpapers_onboarding_dialog_explore_more_button_text">Explore more wallpapers</string>

                <!-- Add-on Installation from AMO-->
                <!-- Error displayed when user attempts to install an add-on from AMO (addons.mozilla.org) that is not supported -->
                <string name="addon_not_supported_error">Add-on is not supported</string>
                <!-- Error displayed when user attempts to install an add-on from AMO (addons.mozilla.org) that is already installed -->
                <string name="addon_already_installed">Add-on is already installed</string>

                <!-- Account Preferences -->
                <!-- Preference for triggering sync -->
                <string name="preferences_sync_now">Sync now</string>
                <!-- Preference category for sync -->
                <string name="preferences_sync_category">Choose what to sync</string>
                <!-- Preference for syncing history -->
                <string name="preferences_sync_history">History</string>
                <!-- Preference for syncing bookmarks -->
                <string name="preferences_sync_bookmarks">Bookmarks</string>
                <!-- Preference for syncing logins -->
                <string name="preferences_sync_logins">Logins</string>
                <!-- Preference for syncing tabs -->
                <string name="preferences_sync_tabs_2">Open tabs</string>
                <!-- Preference for signing out -->
                <string name="preferences_sign_out">Sign out</string>
                <!-- Preference displays and allows changing current FxA device name -->
                <string name="preferences_sync_device_name">Device name</string>
                <!-- Text shown when user enters empty device name -->
                <string name="empty_device_name_error">Device name cannot be empty.</string>
                <!-- Label indicating that sync is in progress -->
                <string name="sync_syncing_in_progress">Syncing…</string>
                <!-- Label summary indicating that sync failed. The first parameter is the date stamp showing last time it succeeded -->
                <string name="sync_failed_summary">Sync failed. Last success: %s</string>
                <!-- Label summary showing never synced -->
                <string name="sync_failed_never_synced_summary">Sync failed. Last synced: never</string>
                <!-- Label summary the date we last synced. The first parameter is date stamp showing last time synced -->
                <string name="sync_last_synced_summary">Last synced: %s</string>
                <!-- Label summary showing never synced -->
                <string name="sync_never_synced_summary">Last synced: never</string>
                <!-- Text for displaying the default device name.
                    The first parameter is the application name, the second is the device manufacturer name
                    and the third is the device model. -->
                <string name="default_device_name_2">%1a on %2a %3a</string>
                <!-- Preference for syncing credit cards -->
                <string name="preferences_sync_credit_cards">Credit cards</string>
                <!-- Preference for syncing addresses -->
                <string name="preferences_sync_address">Addresses</string>

                <!-- Send Tab -->
                <!-- Name of the "receive tabs" notification channel. Displayed in the "App notifications" system settings for the app -->
                <string name="fxa_received_tab_channel_name">Received tabs</string>
                <!-- Description of the "receive tabs" notification channel. Displayed in the "App notifications" system settings for the app -->
                <string name="fxa_received_tab_channel_description">Notifications for tabs received from other Firefox devices.</string>
                <!--  The body for these is the URL of the tab received  -->
                <string name="fxa_tab_received_notification_name">Tab Received</string>
                <!-- %s is the device name -->
                <string name="fxa_tab_received_from_notification_name">Tab from %s</string>

                <!-- Advanced Preferences -->
                <!-- Preference for tracking protection exceptions -->
                <string name="preferences_tracking_protection_exceptions">Exceptions</string>
                <!-- Button in Exceptions Preference to turn on tracking protection for all sites (remove all exceptions) -->
                <string name="preferences_tracking_protection_exceptions_turn_on_for_all">Turn on for all sites</string>
                <!-- Text displayed when there are no exceptions -->
                <string name="exceptions_empty_message_description">Exceptions let you disable tracking protection for selected sites.</string>
                <!-- Text displayed when there are no exceptions, with learn more link that brings users to a tracking protection SUMO page -->
                <string name="exceptions_empty_message_learn_more_link">Learn more</string>

                <!-- Preference switch for usage and technical data collection -->
                <string name="preference_usage_data">Usage and technical data</string>
                <!-- Preference description for usage and technical data collection -->
                <string name="preferences_usage_data_description">Shares performance, usage, hardware and customization data about your browser with Mozilla to help us make %1a better</string>
                <!-- Preference switch for marketing data collection -->
                <string name="preferences_marketing_data">Marketing data</string>
                <!-- Preference description for marketing data collection -->
                <string name="preferences_marketing_data_description2">Shares basic usage data with Adjust, our mobile marketing vendor</string>
                <!-- Title for studies preferences -->
                <string name="preference_experiments_2">Studies</string>
                <!-- Summary for studies preferences -->
                <string name="preference_experiments_summary_2">Allows Mozilla to install and run studies</string>

                <!-- Turn On Sync Preferences -->
                <!-- Header of the Sync and save your data preference view -->
                <string name="preferences_sync_2">Sync and save your data</string>
                <!-- Preference for reconnecting to FxA sync -->
                <string name="preferences_sync_sign_in_to_reconnect">Sign in to reconnect</string>
                <!-- Preference for removing FxA account -->
                <string name="preferences_sync_remove_account">Remove account</string>

                <!-- Pairing Feature strings -->
                <!-- Instructions on how to access pairing -->
                <string name="pair_instructions_2"><![CDATA[Scan the QR code shown at <b>firefox.com/pair</b>]]></string>

                <!-- Toolbar Preferences -->
                <!-- Preference for using top toolbar -->
                <string name="preference_top_toolbar">Top</string>
                <!-- Preference for using bottom toolbar -->
                <string name="preference_bottom_toolbar">Bottom</string>

                <!-- Theme Preferences -->
                <!-- Preference for using light theme -->
                <string name="preference_light_theme">Light</string>
                <!-- Preference for using dark theme -->
                <string name="preference_dark_theme">Dark</string>
                <!-- Preference for using using dark or light theme automatically set by battery -->
                <string name="preference_auto_battery_theme">Set by Battery Saver</string>
                <!-- Preference for using following device theme -->
                <string name="preference_follow_device_theme">Follow device theme</string>

                <!-- Gestures Preferences-->
                <!-- Preferences for using pull to refresh in a webpage -->
                <string name="preference_gestures_website_pull_to_refresh">Pull to refresh</string>
                <!-- Preference for using the dynamic toolbar -->
                <string name="preference_gestures_dynamic_toolbar">Scroll to hide toolbar</string>
                <!-- Preference for switching tabs by swiping horizontally on the toolbar -->
                <string name="preference_gestures_swipe_toolbar_switch_tabs">Swipe toolbar sideways to switch tabs</string>
                <!-- Preference for showing the opened tabs by swiping up on the toolbar-->
                <string name="preference_gestures_swipe_toolbar_show_tabs">Swipe toolbar up to open tabs</string>

                <!-- Library -->
                <!-- Option in Library to open Downloads page -->
                <string name="library_downloads">Downloads</string>
                <!-- Option in library to open Bookmarks page -->
                <string name="library_bookmarks">Bookmarks</string>
                <!-- Option in library to open Desktop Bookmarks root page -->
                <string name="library_desktop_bookmarks_root">Desktop Bookmarks</string>
                <!-- Option in library to open Desktop Bookmarks "menu" page -->
                <string name="library_desktop_bookmarks_menu">Bookmarks Menu</string>
                <!-- Option in library to open Desktop Bookmarks "toolbar" page -->
                <string name="library_desktop_bookmarks_toolbar">Bookmarks Toolbar</string>
                <!-- Option in library to open Desktop Bookmarks "unfiled" page -->
                <string name="library_desktop_bookmarks_unfiled">Other Bookmarks</string>
                <!-- Option in Library to open History page -->
                <string name="library_history">History</string>
                <!-- Option in Library to open a new tab -->
                <string name="library_new_tab">New tab</string>
                <!-- Settings Page Title -->
                <string name="settings_title">Settings</string>
                <!-- Content description (not visible, for screen readers etc.): "Close button for library settings" -->
                <string name="content_description_close_button">Close</string>

                <!-- Title to show in alert when a lot of tabs are to be opened
                %d is a placeholder for the number of tabs that will be opened -->
                <string name="open_all_warning_title">Open %d tabs?</string>
                <!-- Message to warn users that a large number of tabs will be opened
                %s will be replaced by app name. -->
                <string name="open_all_warning_message">Opening this many tabs may slow down %s while the pages are loading. Are you sure you want to continue?</string>
                <!-- Dialog button text for confirming open all tabs -->
                <string name="open_all_warning_confirm">Open tabs</string>
                <!-- Dialog button text for canceling open all tabs -->
                <string name="open_all_warning_cancel">Cancel</string>

                <!-- Text to show users they have one site in the history group section of the History fragment.
                %d is a placeholder for the number of sites in the group. -->
                <string name="history_search_group_site" >%d site</string>
                <!-- Text to show users they have one page in the history group section of the History fragment.
                %d is a placeholder for the number of pages in the group. -->
                <string name="history_search_group_site_1">%d page</string>
                <!-- Text to show users they have multiple sites in the history group section of the History fragment.
                %d is a placeholder for the number of sites in the group. -->
                <string name="history_search_group_sites" >%d sites</string>
                <!-- Text to show users they have multiple pages in the history group section of the History fragment.
                %d is a placeholder for the number of pages in the group. -->
                <string name="history_search_group_sites_1">%d pages</string>

                <!-- Option in library for Recently Closed Tabs -->
                <string name="library_recently_closed_tabs">Recently closed tabs</string>
                <!-- Option in library to open Recently Closed Tabs page -->
                <string name="recently_closed_show_full_history">Show full history</string>
                <!-- Text to show users they have multiple tabs saved in the Recently Closed Tabs section of history.
                %d is a placeholder for the number of tabs selected. -->
                <string name="recently_closed_tabs">%d tabs</string>
                <!-- Text to show users they have one tab saved in the Recently Closed Tabs section of history.
                %d is a placeholder for the number of tabs selected. -->
                <string name="recently_closed_tab">%d tab</string>
                <!-- Recently closed tabs screen message when there are no recently closed tabs -->
                <string name="recently_closed_empty_message">No recently closed tabs here</string>

                <!-- Tab Management -->
                <!-- Title of preference for tabs management -->
                <string name="preferences_tabs">Tabs</string>
                <!-- Title of preference that allows a user to specify the tab view -->
                <string name="preferences_tab_view">Tab view</string>
                <!-- Option for a list tab view -->
                <string name="tab_view_list">List</string>
                <!-- Option for a grid tab view -->
                <string name="tab_view_grid">Grid</string>
                <!-- Title of preference that allows a user to auto close tabs after a specified amount of time -->
                <string name="preferences_close_tabs">Close tabs</string>
                <!-- Option for auto closing tabs that will never auto close tabs, always allows user to manually close tabs -->
                <string name="close_tabs_manually">Never</string>
                <!-- Option for auto closing tabs that will auto close tabs after one day -->
                <string name="close_tabs_after_one_day">After one day</string>
                <!-- Option for auto closing tabs that will auto close tabs after one week -->
                <string name="close_tabs_after_one_week">After one week</string>
                <!-- Option for auto closing tabs that will auto close tabs after one month -->
                <string name="close_tabs_after_one_month">After one month</string>
                <!-- Title of preference that allows a user to specify the auto-close settings for open tabs -->
                <string name="preference_auto_close_tabs" >Auto-close open tabs</string>

                <!-- Opening screen -->
                <!-- Title of a preference that allows a user to choose what screen to show after opening the app -->
                <string name="preferences_opening_screen">Opening screen</string>
                <!-- Option for always opening the homepage when re-opening the app -->
                <string name="opening_screen_homepage">Homepage</string>
                <!-- Option for always opening the user's last-open tab when re-opening the app -->
                <string name="opening_screen_last_tab">Last tab</string>
                <!-- Option for always opening the homepage when re-opening the app after four hours of inactivity -->
                <string name="opening_screen_after_four_hours_of_inactivity">Homepage after four hours of inactivity</string>
                <!-- Summary for tabs preference when auto closing tabs setting is set to manual close-->
                <string name="close_tabs_manually_summary">Close manually</string>
                <!-- Summary for tabs preference when auto closing tabs setting is set to auto close tabs after one day-->
                <string name="close_tabs_after_one_day_summary">Close after one day</string>
                <!-- Summary for tabs preference when auto closing tabs setting is set to auto close tabs after one week-->
                <string name="close_tabs_after_one_week_summary">Close after one week</string>
                <!-- Summary for tabs preference when auto closing tabs setting is set to auto close tabs after one month-->
                <string name="close_tabs_after_one_month_summary">Close after one month</string>
                <!-- Summary for homepage preference indicating always opening the homepage when re-opening the app -->
                <string name="opening_screen_homepage_summary">Open on homepage</string>
                <!-- Summary for homepage preference indicating always opening the last-open tab when re-opening the app -->
                <string name="opening_screen_last_tab_summary">Open on last tab</string>
                <!-- Summary for homepage preference indicating opening the homepage when re-opening the app after four hours of inactivity -->
                <string name="opening_screen_after_four_hours_of_inactivity_summary">Open on homepage after four hours</string>

                <!-- Inactive tabs -->
                <!-- Category header of a preference that allows a user to enable or disable the inactive tabs feature -->
                <string name="preferences_inactive_tabs">Move old tabs to inactive</string>
                <!-- Title of inactive tabs preference -->
                <string name="preferences_inactive_tabs_title">Tabs you haven’t viewed for two weeks get moved to the inactive section.</string>

                <!-- Studies -->
                <!-- Title of the remove studies button -->
                <string name="studies_remove">Remove</string>
                <!-- Title of the active section on the studies list -->
                <string name="studies_active">Active</string>
                <!-- Description for studies, it indicates why Firefox use studies. The first parameter is the name of the application. -->
                <string name="studies_description_2">%1a may install and run studies from time to time.</string>
                <!-- Learn more link for studies, links to an article for more information about studies. -->
                <string name="studies_learn_more">Learn more</string>
                <!-- Dialog message shown after removing a study -->
                <string name="studies_restart_app">The application will quit to apply changes</string>
                <!-- Dialog button to confirm the removing a study. -->
                <string name="studies_restart_dialog_ok">OK</string>
                <!-- Dialog button text for canceling removing a study. -->
                <string name="studies_restart_dialog_cancel">Cancel</string>
                <!-- Toast shown after turning on/off studies preferences -->
                <string name="studies_toast_quit_application" >Quitting the application to apply changes…</string>

                <!-- Sessions -->
                <!-- Title for the list of tabs -->
                <string name="tab_header_label">Open tabs</string>
                <!-- Title for the list of tabs in the current private session -->
                <string name="tabs_header_private_tabs_title">Private tabs</string>
                <!-- Title for the list of tabs in the synced tabs -->
                <string name="tabs_header_synced_tabs_title">Synced tabs</string>
                <!-- Content description (not visible, for screen readers etc.): Add tab button. Adds a news tab when pressed -->
                <string name="add_tab">Add tab</string>
                <!-- Content description (not visible, for screen readers etc.): Add tab button. Adds a news tab when pressed -->
                <string name="add_private_tab">Add private tab</string>
                <!-- Text for the new tab button to indicate adding a new private tab in the tab -->
                <string name="tab_drawer_fab_content">Private</string>
                <!-- Text for the new tab button to indicate syncing command on the synced tabs page -->
                <string name="tab_drawer_fab_sync">Sync</string>
                <!-- Text shown in the menu for sharing all tabs -->
                <string name="tab_tray_menu_item_share">Share all tabs</string>
                <!-- Text shown in the menu to view recently closed tabs -->
                <string name="tab_tray_menu_recently_closed">Recently closed tabs</string>
                <!-- Text shown in the tabs tray inactive tabs section -->
                <string name="tab_tray_inactive_recently_closed" >Recently closed</string>
                <!-- Text shown in the menu to view account settings -->
                <string name="tab_tray_menu_account_settings">Account settings</string>
                <!-- Text shown in the menu to view tab settings -->
                <string name="tab_tray_menu_tab_settings">Tab settings</string>
                <!-- Text shown in the menu for closing all tabs -->
                <string name="tab_tray_menu_item_close">Close all tabs</string>
                <!-- Text shown in the multiselect menu for bookmarking selected tabs. -->
                <string name="tab_tray_multiselect_menu_item_bookmark">Bookmark</string>
                <!-- Text shown in the multiselect menu for closing selected tabs. -->
                <string name="tab_tray_multiselect_menu_item_close">Close</string>
                <!-- Content description for tabs tray multiselect share button -->
                <string name="tab_tray_multiselect_share_content_description">Share selected tabs</string>
                <!-- Content description for tabs tray multiselect menu -->
                <string name="tab_tray_multiselect_menu_content_description">Selected tabs menu</string>
                <!-- Content description (not visible, for screen readers etc.): Removes tab from collection button. Removes the selected tab from collection when pressed -->
                <string name="remove_tab_from_collection">Remove tab from collection</string>
                <!-- Text for button to enter multiselect mode in tabs tray -->
                <string name="tabs_tray_select_tabs">Select tabs</string>
                <!-- Content description (not visible, for screen readers etc.): Close tab button. Closes the current session when pressed -->
                <string name="close_tab">Close tab</string>
                <!-- Content description (not visible, for screen readers etc.): Close tab <title> button. First parameter is tab title  -->
                <string name="close_tab_title">Close tab %s</string>
                <!-- Content description (not visible, for screen readers etc.): Opens the open tabs menu when pressed -->
                <string name="open_tabs_menu">Open tabs menu</string>
                <!-- Open tabs menu item to save tabs to collection -->
                <string name="tabs_menu_save_to_collection1">Save tabs to collection</string>
                <!-- Text for the menu button to delete a collection -->
                <string name="collection_delete">Delete collection</string>
                <!-- Text for the menu button to rename a collection -->
                <string name="collection_rename">Rename collection</string>
                <!-- Text for the button to open tabs of the selected collection -->
                <string name="collection_open_tabs">Open tabs</string>
                <!-- Hint for adding name of a collection -->
                <string name="collection_name_hint">Collection name</string>
            	<!-- Text for the menu button to rename a top site -->
            	<string name="rename_top_site">Rename</string>
            	<!-- Text for the menu button to remove a top site -->
            	<string name="remove_top_site">Remove</string>
                <!-- Text for the menu button to delete a top site from history -->
                <string name="delete_from_history">Delete from history</string>
                <!-- Postfix for private WebApp titles, placeholder is replaced with app name -->
                <string name="pwa_site_controls_title_private">%1a (Private Mode)</string>

                <!-- History -->
                <!-- Text for the button to search all history -->
                <string name="history_search_1">Enter search terms</string>
                <!-- Text for the button to clear all history -->
                <string name="history_delete_all">Delete history</string>
                <!-- Text for the snackbar to confirm that multiple browsing history items has been deleted -->
                <string name="history_delete_multiple_items_snackbar">History Deleted</string>
                <!-- Text for the snackbar to confirm that a single browsing history item has been deleted. The first parameter is the shortened URL of the deleted history item. -->
                <string name="history_delete_single_item_snackbar">Deleted %1a</string>
                <!-- Context description text for the button to delete a single history item -->
                <string name="history_delete_item">Delete</string>
                <!-- History multi select title in app bar
                The first parameter is the number of bookmarks selected -->
                <string name="history_multi_select_title">%1b selected</string>
                <!-- Text for the header that groups the history for today -->
                <string name="history_today">Today</string>
                <!-- Text for the header that groups the history for yesterday -->
                <string name="history_yesterday">Yesterday</string>
                <!-- Text for the header that groups the history the past 7 days -->
                <string name="history_7_days">Last 7 days</string>
                <!-- Text for the header that groups the history the past 30 days -->
                <string name="history_30_days">Last 30 days</string>
                <!-- Text for the header that groups the history older than the last month -->
                <string name="history_older">Older</string>
                <!-- Text shown when no history exists -->
                <string name="history_empty_message">No history here</string>

                <!-- Downloads -->
                <!-- Text for the snackbar to confirm that multiple downloads items have been removed -->
                <string name="download_delete_multiple_items_snackbar_1">Downloads Removed</string>
                <!-- Text for the snackbar to confirm that a single download item has been removed. The first parameter is the name of the download item. -->
                <string name="download_delete_single_item_snackbar">Removed %1a</string>
                <!-- Text shown when no download exists -->
                <string name="download_empty_message_1">No downloaded files</string>
                <!-- History multi select title in app bar
                The first parameter is the number of downloads selected -->
                <string name="download_multi_select_title">%1b selected</string>
                <!-- Text for the button to remove a single download item -->
                <string name="download_delete_item_1">Remove</string>


                <!-- Crashes -->
                <!-- Title text displayed on the tab crash page. This first parameter is the name of the application (For example: Fenix) -->
                <string name="tab_crash_title_2">Sorry. %1a can’t load that page.</string>
                <!-- Send crash report checkbox text on the tab crash page -->
                <string name="tab_crash_send_report">Send crash report to Mozilla</string>
                <!-- Close tab button text on the tab crash page -->
                <string name="tab_crash_close">Close tab</string>
                <!-- Restore tab button text on the tab crash page -->
                <string name="tab_crash_restore">Restore tab</string>

                <!-- Bookmarks -->
                <!-- Confirmation message for a dialog confirming if the user wants to delete the selected folder -->
                <string name="bookmark_delete_folder_confirmation_dialog">Are you sure you want to delete this folder?</string>
                <!-- Confirmation message for a dialog confirming if the user wants to delete multiple items including folders. Parameter will be replaced by app name. -->
                <string name="bookmark_delete_multiple_folders_confirmation_dialog">%s will delete the selected items.</string>
                <!-- Text for the cancel button on delete bookmark dialog -->
                <string name="bookmark_delete_negative">Cancel</string>
                <!-- Screen title for adding a bookmarks folder -->
                <string name="bookmark_add_folder">Add folder</string>
                <!-- Snackbar title shown after a bookmark has been created. -->
                <string name="bookmark_saved_snackbar">Bookmark saved!</string>
                <!-- Snackbar edit button shown after a bookmark has been created. -->
                <string name="edit_bookmark_snackbar_action">EDIT</string>
                <!-- Bookmark overflow menu edit button -->
                <string name="bookmark_menu_edit_button">Edit</string>
                <!-- Bookmark overflow menu copy button -->
                <string name="bookmark_menu_copy_button">Copy</string>
                <!-- Bookmark overflow menu share button -->
                <string name="bookmark_menu_share_button">Share</string>
                <!-- Bookmark overflow menu open in new tab button -->
                <string name="bookmark_menu_open_in_new_tab_button">Open in new tab</string>
                <!-- Bookmark overflow menu open in private tab button -->
                <string name="bookmark_menu_open_in_private_tab_button">Open in private tab</string>
                <!-- Bookmark overflow menu open all in tabs button -->
                <string name="bookmark_menu_open_all_in_tabs_button">Open all in new tabs</string>
                <!-- Bookmark overflow menu open all in private tabs button -->
                <string name="bookmark_menu_open_all_in_private_tabs_button">Open all in private tabs</string>
                <!-- Bookmark overflow menu delete button -->
                <string name="bookmark_menu_delete_button">Delete</string>
                <!--Bookmark overflow menu save button -->
                <string name="bookmark_menu_save_button">Save</string>
                <!-- Bookmark multi select title in app bar
                 The first parameter is the number of bookmarks selected -->
                <string name="bookmarks_multi_select_title">%1b selected</string>
                <!-- Bookmark editing screen title -->
                <string name="edit_bookmark_fragment_title">Edit bookmark</string>
                <!-- Bookmark folder editing screen title -->
                <string name="edit_bookmark_folder_fragment_title">Edit folder</string>
                <!-- Bookmark sign in button message -->
                <string name="bookmark_sign_in_button">Sign in to see synced bookmarks</string>
                <!-- Bookmark URL editing field label -->
                <string name="bookmark_url_label">URL</string>
                <!-- Bookmark FOLDER editing field label -->
                <string name="bookmark_folder_label">FOLDER</string>
                <!-- Bookmark NAME editing field label -->
                <string name="bookmark_name_label">NAME</string>
                <!-- Bookmark add folder screen title -->
                <string name="bookmark_add_folder_fragment_label">Add folder</string>
                <!-- Bookmark select folder screen title -->
                <string name="bookmark_select_folder_fragment_label">Select folder</string>
                <!-- Bookmark editing error missing title -->
                <string name="bookmark_empty_title_error">Must have a title</string>
                <!-- Bookmark editing error missing or improper URL -->
                <string name="bookmark_invalid_url_error">Invalid URL</string>
                <!-- Bookmark screen message for empty bookmarks folder -->
                <string name="bookmarks_empty_message">No bookmarks here</string>
                <!-- Bookmark snackbar message on deletion
                 The first parameter is the host part of the URL of the bookmark deleted, if any -->
                <string name="bookmark_deletion_snackbar_message">Deleted %1a</string>
                <!-- Bookmark snackbar message on deleting multiple bookmarks not including folders-->
                <string name="bookmark_deletion_multiple_snackbar_message_2">Bookmarks deleted</string>
                <!-- Bookmark snackbar message on deleting multiple bookmarks including folders-->
                <string name="bookmark_deletion_multiple_snackbar_message_3">Deleting selected folders</string>
                <!-- Bookmark undo button for deletion snackbar action -->
                <string name="bookmark_undo_deletion">UNDO</string>
                <!-- Text for the button to search all bookmarks -->
                <string name="bookmark_search">Enter search terms</string>

                <!-- Site Permissions -->
                <!-- Button label that take the user to the Android App setting -->
                <string name="phone_feature_go_to_settings">Go to Settings</string>
                <!-- Content description (not visible, for screen readers etc.): Quick settings sheet
                    to give users access to site specific information / settings. For example:
                    Secure settings status and a button to modify site permissions -->
                <string name="quick_settings_sheet">Quick settings sheet</string>
                <!-- Label that indicates that this option it the recommended one -->
                <string name="phone_feature_recommended">Recommended</string>
                <!-- Button label for clearing all the information of site permissions-->
                <string name="clear_permissions">Clear permissions</string>
                <!-- Text for the OK button on Clear permissions dialog -->
                <string name="clear_permissions_positive">OK</string>
                <!-- Text for the cancel button on Clear permissions dialog -->
                <string name="clear_permissions_negative">Cancel</string>
                <!-- Button label for clearing a site permission-->
                <string name="clear_permission">Clear permission</string>
                <!-- Text for the OK button on Clear permission dialog -->
                <string name="clear_permission_positive">OK</string>
                <!-- Text for the cancel button on Clear permission dialog -->
                <string name="clear_permission_negative">Cancel</string>
                <!-- Button label for clearing all the information on all sites-->
                <string name="clear_permissions_on_all_sites">Clear permissions on all sites</string>
                <!-- Preference for altering video and audio autoplay for all websites -->
                <string name="preference_browser_feature_autoplay">Autoplay</string>
                <!-- Preference for altering the camera access for all websites -->
                <string name="preference_phone_feature_camera">Camera</string>
                <!-- Preference for altering the microphone access for all websites -->
                <string name="preference_phone_feature_microphone">Microphone</string>
                <!-- Preference for altering the location access for all websites -->
                <string name="preference_phone_feature_location">Location</string>
                <!-- Preference for altering the notification access for all websites -->
                <string name="preference_phone_feature_notification">Notification</string>
                <!-- Preference for altering the persistent storage access for all websites -->
                <string name="preference_phone_feature_persistent_storage">Persistent Storage</string>
                <!-- Preference for altering the storage access setting for all websites -->
                <string name="preference_phone_feature_cross_origin_storage_access">Cross-site cookies</string>
                <!-- Preference for altering the EME access for all websites -->
                <string name="preference_phone_feature_media_key_system_access">DRM-controlled content</string>
                <!-- Label that indicates that a permission must be asked always -->
                <string name="preference_option_phone_feature_ask_to_allow">Ask to allow</string>
                <!-- Label that indicates that a permission must be blocked -->
                <string name="preference_option_phone_feature_blocked">Blocked</string>
                <!-- Label that indicates that a permission must be allowed -->
                <string name="preference_option_phone_feature_allowed">Allowed</string>
                <!--Label that indicates a permission is by the Android OS-->
                <string name="phone_feature_blocked_by_android">Blocked by Android</string>
                <!-- Preference for showing a list of websites that the default configurations won't apply to them -->
                <string name="preference_exceptions">Exceptions</string>
                <!-- Summary of tracking protection preference if tracking protection is set to on -->
                <string name="tracking_protection_on" >On</string>
                <!-- Summary of tracking protection preference if tracking protection is set to off -->
                <string name="tracking_protection_off">Off</string>
                <!-- Summary of tracking protection preference if tracking protection is set to standard -->
                <string name="tracking_protection_standard">Standard</string>
                <!-- Summary of tracking protection preference if tracking protection is set to strict -->
                <string name="tracking_protection_strict">Strict</string>
                <!-- Summary of tracking protection preference if tracking protection is set to custom -->
                <string name="tracking_protection_custom">Custom</string>
                <!-- Label for global setting that indicates that all video and audio autoplay is allowed -->
                <string name="preference_option_autoplay_allowed2">Allow audio and video</string>
                <!-- Label for site specific setting that indicates that all video and audio autoplay is allowed -->
                <string name="quick_setting_option_autoplay_allowed">Allow audio and video</string>
                <!-- Label that indicates that video and audio autoplay is only allowed over Wi-Fi -->
                <string name="preference_option_autoplay_allowed_wifi_only2">Block audio and video on cellular data only</string>
                <!-- Subtext that explains 'autoplay on Wi-Fi only' option -->
                <string name="preference_option_autoplay_allowed_wifi_subtext">Audio and video will play on Wi-Fi</string>
                <!-- Label for global setting that indicates that video autoplay is allowed, but audio autoplay is blocked -->
                <string name="preference_option_autoplay_block_audio2">Block audio only</string>
                <!-- Label for site specific setting that indicates that video autoplay is allowed, but audio autoplay is blocked -->
                <string name="quick_setting_option_autoplay_block_audio">Block audio only</string>
                <!-- Label for global setting that indicates that all video and audio autoplay is blocked -->
                <string name="preference_option_autoplay_blocked3">Block audio and video</string>
                <!-- Label for site specific setting that indicates that all video and audio autoplay is blocked -->
                <string name="quick_setting_option_autoplay_blocked">Block audio and video</string>
                <!-- Summary of delete browsing data on quit preference if it is set to on -->
                <string name="delete_browsing_data_quit_on">On</string>
                <!-- Summary of delete browsing data on quit preference if it is set to off -->
                <string name="delete_browsing_data_quit_off">Off</string>
                <!-- Summary of studies preference if it is set to on -->
                <string name="studies_on">On</string>
                <!-- Summary of studies data on quit preference if it is set to off -->
                <string name="studies_off">Off</string>

                <!-- Collections -->
                <!-- Collections header on home fragment -->
                <string name="collections_header">Collections</string>
                <!-- Content description (not visible, for screen readers etc.): Opens the collection menu when pressed -->
                <string name="collection_menu_button_content_description">Collection menu</string>
                <!-- Label to describe what collections are to a new user without any collections -->
                <string name="no_collections_description2">Collect the things that matter to you.\nGroup together similar searches, sites, and tabs for quick access later.</string>
                <!-- Title for the "select tabs" step of the collection creator -->
                <string name="create_collection_select_tabs">Select Tabs</string>
                <!-- Title for the "select collection" step of the collection creator -->
                <string name="create_collection_select_collection">Select collection</string>
                <!-- Title for the "name collection" step of the collection creator -->
                <string name="create_collection_name_collection">Name collection</string>
                <!-- Button to add new collection for the "select collection" step of the collection creator -->
                <string name="create_collection_add_new_collection">Add new collection</string>
                <!-- Button to select all tabs in the "select tabs" step of the collection creator -->
                <string name="create_collection_select_all">Select all</string>
                <!-- Button to deselect all tabs in the "select tabs" step of the collection creator -->
                <string name="create_collection_deselect_all">Deselect all</string>
                <!-- Text to prompt users to select the tabs to save in the "select tabs" step of the collection creator -->
                <string name="create_collection_save_to_collection_empty">Select tabs to save</string>
                <!-- Text to show users how many tabs they have selected in the "select tabs" step of the collection creator.
                 %d is a placeholder for the number of tabs selected. -->
                <string name="create_collection_save_to_collection_tabs_selected">%d tabs selected</string>
                <!-- Text to show users they have one tab selected in the "select tabs" step of the collection creator.
                %d is a placeholder for the number of tabs selected. -->
                <string name="create_collection_save_to_collection_tab_selected">%d tab selected</string>
                <!-- Text shown in snackbar when multiple tabs have been saved in a collection -->
                <string name="create_collection_tabs_saved">Tabs saved!</string>
                <!-- Text shown in snackbar when one or multiple tabs have been saved in a new collection -->
                <string name="create_collection_tabs_saved_new_collection">Collection saved!</string>
                <!-- Text shown in snackbar when one tab has been saved in a collection -->
                <string name="create_collection_tab_saved">Tab saved!</string>
                <!-- Content description (not visible, for screen readers etc.): button to close the collection creator -->
                <string name="create_collection_close">Close</string>
                <!-- Button to save currently selected tabs in the "select tabs" step of the collection creator-->
                <string name="create_collection_save">Save</string>
                <!-- Snackbar action to view the collection the user just created or updated -->
                <string name="create_collection_view">View</string>
                <!-- Text for the OK button from collection dialogs -->
                <string name="create_collection_positive">OK</string>
                <!-- Text for the cancel button from collection dialogs -->
                <string name="create_collection_negative">Cancel</string>

                <!-- Default name for a new collection in "name new collection" step of the collection creator. %d is a placeholder for the number of collections-->
                <string name="create_collection_default_name">Collection %d</string>

                <!-- Share -->
                <!-- Share screen header -->
                <string name="share_header_2">Share</string>
                <!-- Content description (not visible, for screen readers etc.):
                    "Share" button. Opens the share menu when pressed. -->
                <string name="share_button_content_description">Share</string>
                <!-- Text for the Save to PDF feature in the share menu -->
                <string name="share_save_to_pdf">Save as PDF</string>
                <!-- Text for error message when generating a PDF file Text for error message when generating a PDF file. -->
                <string name="unable_to_save_to_pdf_error">Unable to generate PDF</string>
                <!-- Sub-header in the dialog to share a link to another sync device -->
                <string name="share_device_subheader">Send to device</string>
                <!-- Sub-header in the dialog to share a link to an app from the full list -->
                <string name="share_link_all_apps_subheader">All actions</string>
                <!-- Sub-header in the dialog to share a link to an app from the most-recent sorted list -->
                <string name="share_link_recent_apps_subheader">Recently used</string>
                <!-- Text for the copy link action in the share screen. -->
                <string name="share_copy_link_to_clipboard">Copy to clipboard</string>
                <!-- Toast shown after copying link to clipboard -->
                <string name="toast_copy_link_to_clipboard">Copied to clipboard</string>
                <!-- An option from the share dialog to sign into sync -->
                <string name="sync_sign_in">Sign in to Sync</string>
                 <!-- An option from the three dot menu to sync and save data -->
                <string name="sync_menu_sync_and_save_data">Sync and save data</string>
                <!-- An option from the share dialog to send link to all other sync devices -->
                <string name="sync_send_to_all">Send to all devices</string>
                <!-- An option from the share dialog to reconnect to sync -->
                <string name="sync_reconnect">Reconnect to Sync</string>
                <!-- Text displayed when sync is offline and cannot be accessed -->
                <string name="sync_offline">Offline</string>
                <!-- An option to connect additional devices -->
                <string name="sync_connect_device">Connect another device</string>
                <!-- The dialog text shown when additional devices are not available -->
                <string name="sync_connect_device_dialog">To send a tab, sign in to Firefox on at least one other device.</string>
                <!-- Confirmation dialog button -->
                <string name="sync_confirmation_button">Got it</string>
                <!-- Share error message -->
                <string name="share_error_snackbar">Cannot share to this app</string>
                <!-- Add new device screen title -->
                <string name="sync_add_new_device_title">Send to device</string>
                <!-- Text for the warning message on the Add new device screen -->
                <string name="sync_add_new_device_message">No Devices Connected</string>
                <!-- Text for the button to learn about sending tabs -->
                <string name="sync_add_new_device_learn_button">Learn About Sending Tabs…</string>
                <!-- Text for the button to connect another device -->
                <string name="sync_add_new_device_connect_button">Connect Another Device…</string>

                <!-- Notifications -->
                <!-- Text shown in the notification that pops up to remind the user that a private browsing session is active. -->
                <string name="notification_pbm_delete_text_2">Close private tabs</string>
                <!-- Name of the marketing notification channel. Displayed in the "App notifications" system settings for the app -->
                <string name="notification_marketing_channel_name">Marketing</string>
                <!-- Title shown in the notification that pops up to remind the user to set fenix as default browser.
                %1a is a placeholder that will be replaced by the app name (Fenix). -->
                <string name="notification_default_browser_title">%1a is fast and private</string>
                <!-- Text shown in the notification that pops up to remind the user to set fenix as default browser.
                %1a is a placeholder that will be replaced by the app name (Fenix). -->
                <string name="notification_default_browser_text">Make %1a your default browser</string>
                <!-- Title shown in the notification that pops up to re-engage the user -->
                <string name="notification_re_engagement_title">Try private browsing</string>
                <!-- Text shown in the notification that pops up to re-engage the user.
                %1a is a placeholder that will be replaced by the app name. -->
                <string name="notification_re_engagement_text">Browse with no saved cookies or history in %1a</string>

                <!-- Snackbar -->
                <!-- Text shown in snackbar when user deletes a collection -->
                <string name="snackbar_collection_deleted">Collection deleted</string>
                <!-- Text shown in snackbar when user renames a collection -->
                <string name="snackbar_collection_renamed">Collection renamed</string>
                <!-- Text shown in snackbar when user closes a tab -->
                <string name="snackbar_tab_closed">Tab closed</string>
                <!-- Text shown in snackbar when user closes all tabs -->
                <string name="snackbar_tabs_closed">Tabs closed</string>
                <!-- Text shown in snackbar when user bookmarks a list of tabs -->
                <string name="snackbar_message_bookmarks_saved">Bookmarks saved!</string>
                <!-- Text shown in snackbar when user adds a site to shortcuts -->
                <string name="snackbar_added_to_shortcuts">Added to shortcuts!</string>
                <!-- Text shown in snackbar when user closes a private tab -->
                <string name="snackbar_private_tab_closed">Private tab closed</string>
                <!-- Text shown in snackbar when user closes all private tabs -->
                <string name="snackbar_private_tabs_closed">Private tabs closed</string>
                <!-- Text shown in snackbar to undo deleting a tab, top site or collection -->
                <string name="snackbar_deleted_undo">UNDO</string>
                <!-- Text shown in snackbar when user removes a top site -->
                <string name="snackbar_top_site_removed">Site removed</string>
                <!-- QR code scanner prompt which appears after scanning a code, but before navigating to it
                    First parameter is the name of the app, second parameter is the URL or text scanned-->
                <string name="qr_scanner_confirmation_dialog_message">Allow %1a to open %2a</string>
                <!-- QR code scanner prompt dialog positive option to allow navigation to scanned link -->
                <string name="qr_scanner_dialog_positive">ALLOW</string>
                <!-- QR code scanner prompt dialog positive option to deny navigation to scanned link -->
                <string name="qr_scanner_dialog_negative">DENY</string>
                <!-- QR code scanner prompt dialog error message shown when a hostname does not contain http or https. -->
                <string name="qr_scanner_dialog_invalid">Web address not valid.</string>
                <!-- QR code scanner prompt dialog positive option when there is an error -->
                <string name="qr_scanner_dialog_invalid_ok">OK</string>
                <!-- Tab collection deletion prompt dialog message. Placeholder will be replaced with the collection name -->
                <string name="tab_collection_dialog_message">Are you sure you want to delete %1a?</string>
                <!-- Collection and tab deletion prompt dialog message. This will show when the last tab from a collection is deleted -->
                <string name="delete_tab_and_collection_dialog_message">Deleting this tab will delete the entire collection. You can create new collections at any time.</string>
                <!-- Collection and tab deletion prompt dialog title. Placeholder will be replaced with the collection name. This will show when the last tab from a collection is deleted -->
                <string name="delete_tab_and_collection_dialog_title">Delete %1a?</string>
                <!-- Tab collection deletion prompt dialog option to delete the collection -->
                <string name="tab_collection_dialog_positive">Delete</string>
                <!-- Text displayed in a notification when the user enters full screen mode -->
                <string name="full_screen_notification">Entering full screen mode</string>
                <!-- Message for copying the URL via long press on the toolbar -->
                <string name="url_copied">URL copied</string>
                <!-- Sample text for accessibility font size -->
                <string name="accessibility_text_size_sample_text_1">This is sample text. It is here to show how text will appear when you increase or decrease the size with this setting.</string>
                <!-- Summary for Accessibility Text Size Scaling Preference -->
                <string name="preference_accessibility_text_size_summary">Make text on websites larger or smaller</string>
                <!-- Title for Accessibility Text Size Scaling Preference -->
                <string name="preference_accessibility_font_size_title">Font Size</string>

                <!-- Title for Accessibility Text Automatic Size Scaling Preference -->
                <string name="preference_accessibility_auto_size_2">Automatic font sizing</string>
                <!-- Summary for Accessibility Text Automatic Size Scaling Preference -->
                <string name="preference_accessibility_auto_size_summary">Font size will match your Android settings. Disable to manage font size here.</string>

                <!-- Title for the Delete browsing data preference -->
                <string name="preferences_delete_browsing_data">Delete browsing data</string>
                <!-- Title for the tabs item in Delete browsing data -->
                <string name="preferences_delete_browsing_data_tabs_title_2">Open tabs</string>
                <!-- Subtitle for the tabs item in Delete browsing data, parameter will be replaced with the number of open tabs -->
                <string name="preferences_delete_browsing_data_tabs_subtitle">%d tabs</string>
                <!-- Title for the data and history items in Delete browsing data -->
                <string name="preferences_delete_browsing_data_browsing_data_title">Browsing history and site data</string>
                <!-- Subtitle for the data and history items in delete browsing data, parameter will be replaced with the
                    number of history items the user has -->
                <string name="preferences_delete_browsing_data_browsing_data_subtitle">%d addresses</string>
                <!-- Title for the cookies item in Delete browsing data -->
                <string name="preferences_delete_browsing_data_cookies">Cookies</string>
                <!-- Subtitle for the cookies item in Delete browsing data -->
                <string name="preferences_delete_browsing_data_cookies_subtitle">You’ll be logged out of most sites</string>
                <!-- Title for the cached images and files item in Delete browsing data -->
                <string name="preferences_delete_browsing_data_cached_files">Cached images and files</string>
                <!-- Subtitle for the cached images and files item in Delete browsing data -->
                <string name="preferences_delete_browsing_data_cached_files_subtitle">Frees up storage space</string>
                <!-- Title for the site permissions item in Delete browsing data -->
                <string name="preferences_delete_browsing_data_site_permissions">Site permissions</string>
                <!-- Title for the downloads item in Delete browsing data -->
                <string name="preferences_delete_browsing_data_downloads">Downloads</string>
                <!-- Text for the button to delete browsing data -->
                <string name="preferences_delete_browsing_data_button">Delete browsing data</string>
                <!-- Title for the Delete browsing data on quit preference -->
                <string name="preferences_delete_browsing_data_on_quit">Delete browsing data on quit</string>
                <!-- Summary for the Delete browsing data on quit preference. "Quit" translation should match delete_browsing_data_on_quit_action translation. -->
                <string name="preference_summary_delete_browsing_data_on_quit_2">Automatically deletes browsing data when you select \"Quit\" from the main menu</string>
                <!-- Action item in menu for the Delete browsing data on quit feature -->
                <string name="delete_browsing_data_on_quit_action">Quit</string>

                <!-- Title text of a delete browsing data dialog. -->
                <string name="delete_history_prompt_title">Time range to delete</string>
                <!-- Body text of a delete browsing data dialog. -->
                <string name="delete_history_prompt_body">Removes history (including history synced from other devices), cookies and other browsing data.</string>
                <!-- Radio button in the delete browsing data dialog to delete history items for the last hour. -->
                <string name="delete_history_prompt_button_last_hour">Last hour</string>
                <!-- Radio button in the delete browsing data dialog to delete history items for today and yesterday. -->
                <string name="delete_history_prompt_button_today_and_yesterday">Today and yesterday</string>
                <!-- Radio button in the delete browsing data dialog to delete all history. -->
                <string name="delete_history_prompt_button_everything">Everything</string>

                <!-- Dialog message to the user asking to delete browsing data. Parameter will be replaced by app name. -->
                <string name="delete_browsing_data_prompt_message_3">%s will delete the selected browsing data.</string>
                <!-- Text for the cancel button for the data deletion dialog -->
                <string name="delete_browsing_data_prompt_cancel">Cancel</string>
                <!-- Text for the allow button for the data deletion dialog -->
                <string name="delete_browsing_data_prompt_allow">Delete</string>
                <!-- Text for the snackbar confirmation that the data was deleted -->
                <string name="preferences_delete_browsing_data_snackbar">Browsing data deleted</string>
                <!-- Text for the snackbar to show the user that the deletion of browsing data is in progress -->
                <string name="deleting_browsing_data_in_progress">Deleting browsing data&#8230;</string>

                <!-- Dialog message to the user asking to delete all history items inside the opened group. Parameter will be replaced by a history group name. -->
                <string name="delete_all_history_group_prompt_message">Delete all sites in “%s”</string>
                <!-- Text for the cancel button for the history group deletion dialog -->
                <string name="delete_history_group_prompt_cancel">Cancel</string>
                <!-- Text for the allow button for the history group dialog -->
                <string name="delete_history_group_prompt_allow">Delete</string>
                <!-- Text for the snackbar confirmation that the history group was deleted -->
                <string name="delete_history_group_snackbar">Group deleted</string>

                <!-- Onboarding -->
                <!-- Text for onboarding welcome header. -->
                <string name="onboarding_header_2">Welcome to a better internet</string>
                <!-- Text for the onboarding welcome message. -->
                <string name="onboarding_message">A browser built for people, not profits.</string>
                <!-- Text for the Firefox account onboarding sign in card header. -->
                <string name="onboarding_account_sign_in_header">Pick up where you left off</string>
                <!-- Text for the button to learn more about signing in to your Firefox account. -->
                <string name="onboarding_manual_sign_in_description">Sync tabs and passwords across devices for seamless screen-switching.</string>
                <!-- Text for the button to manually sign into Firefox account. -->
                <string name="onboarding_firefox_account_sign_in">Sign in</string>
                <!-- text to display in the snackbar once account is signed-in -->
                <string name="onboarding_firefox_account_sync_is_on">Sync is on</string>
                <!-- Text for the tracking protection onboarding card header -->
                <string name="onboarding_tracking_protection_header">Privacy protection by default</string>
                <!-- Text for the tracking protection card description. -->
                <string name="onboarding_tracking_protection_description">Featuring Total Cookie Protection to stop trackers from using cookies to stalk you across sites.</string>
                <!-- text for tracking protection radio button option for standard level of blocking -->
                <string name="onboarding_tracking_protection_standard_button_2">Standard (default)</string>
                <!-- text for standard blocking option button description -->
                <string name="onboarding_tracking_protection_standard_button_description_3">Balanced for privacy and performance. Pages load normally.</string>
                <!-- text for tracking protection radio button option for strict level of blocking -->
                <string name="onboarding_tracking_protection_strict_option">Strict</string>
                <!-- text for strict blocking option button description -->
                <string name="onboarding_tracking_protection_strict_button_description_3">Blocks more trackers so pages load faster, but some on-page functionality may break.</string>
                <!-- text for the toolbar position card header  -->
                <string name="onboarding_toolbar_placement_header_1">Pick your toolbar placement</string>
                <!-- Text for the toolbar position card description -->
                <string name="onboarding_toolbar_placement_description">Keep it on the bottom, or move it to the top.</string>
                <!-- Text for the privacy notice onboarding card header -->
                <string name="onboarding_privacy_notice_header_1">You control your data</string>
                <!-- Text for the privacy notice onboarding card description. -->
                <string name="onboarding_privacy_notice_description">Firefox gives you control over what you share online and what you share with us.</string>
                <!-- Text for the button to read the privacy notice -->
                <string name="onboarding_privacy_notice_read_button">Read our privacy notice</string>
                <!-- Text for the conclusion onboarding message -->
                <string name="onboarding_conclusion_header">Ready to open up an amazing internet?</string>
                <!-- text for the button to finish onboarding -->
                <string name="onboarding_finish">Start browsing</string>

                <!-- Onboarding theme -->
                <!-- text for the theme picker onboarding card header -->
                <string name="onboarding_theme_picker_header">Choose your theme</string>
                <!-- text for the theme picker onboarding card description -->
                <string name="onboarding_theme_picker_description_2">Save some battery and your eyesight with dark mode.</string>
                <!-- Automatic theme setting (will follow device setting) -->
                <string name="onboarding_theme_automatic_title">Automatic</string>
                <!-- Summary of automatic theme setting (will follow device setting) -->
                <string name="onboarding_theme_automatic_summary">Adapts to your device settings</string>
                <!-- Theme setting for dark mode -->
                <string name="onboarding_theme_dark_title">Dark theme</string>
                <!-- Theme setting for light mode -->
                <string name="onboarding_theme_light_title">Light theme</string>

                <!-- Text shown in snackbar when multiple tabs have been sent to device -->
                <string name="sync_sent_tabs_snackbar">Tabs sent!</string>
                <!-- Text shown in snackbar when one tab has been sent to device  -->
                <string name="sync_sent_tab_snackbar">Tab sent!</string>
                <!-- Text shown in snackbar when sharing tabs failed  -->
                <string name="sync_sent_tab_error_snackbar">Unable to send</string>
                <!-- Text shown in snackbar for the "retry" action that the user has after sharing tabs failed -->
                <string name="sync_sent_tab_error_snackbar_action">RETRY</string>
                <!-- Title of QR Pairing Fragment -->
                <string name="sync_scan_code">Scan the code</string>
                <!-- Instructions on how to access pairing -->
                <string name="sign_in_instructions"><![CDATA[On your computer open Firefox and go to <b>https://firefox.com/pair</b>]]></string>
                <!-- Text shown for sign in pairing when ready -->
                <string name="sign_in_ready_for_scan">Ready to scan</string>
                <!-- Text shown for settings option for sign with pairing -->
                <string name="sign_in_with_camera">Sign in with your camera</string>
                <!-- Text shown for settings option for sign with email -->
                <string name="sign_in_with_email">Use email instead</string>
                <!-- Text shown for settings option for create new account text.'Firefox' intentionally hardcoded here.-->
                <string name="sign_in_create_account_text"><![CDATA[No account? <u>Create one</u> to sync Firefox between devices.]]></string>
                <!-- Text shown in confirmation dialog to sign out of account. The first parameter is the name of the app (e.g. Firefox Preview) -->
                <string name="sign_out_confirmation_message_2">%s will stop syncing with your account, but won’t delete any of your browsing data on this device.</string>
                <!-- Option to continue signing out of account shown in confirmation dialog to sign out of account -->
                <string name="sign_out_disconnect">Disconnect</string>
                <!-- Option to cancel signing out shown in confirmation dialog to sign out of account -->
                <string name="sign_out_cancel">Cancel</string>
                <!-- Error message snackbar shown after the user tried to select a default folder which cannot be altered -->
                <string name="bookmark_cannot_edit_root">Can’t edit default folders</string>

                <!-- Enhanced Tracking Protection -->
                <!-- Link displayed in enhanced tracking protection panel to access tracking protection settings -->
                <string name="etp_settings">Protection Settings</string>
                <!-- Preference title for enhanced tracking protection settings -->
                <string name="preference_enhanced_tracking_protection">Enhanced Tracking Protection</string>
                <!-- Title for the description of enhanced tracking protection -->
                <string name="preference_enhanced_tracking_protection_explanation_title">Browse without being followed</string>
                <!-- Description of enhanced tracking protection. The first parameter is the name of the application (For example: Fenix) -->
                <string name="preference_enhanced_tracking_protection_explanation">Keep your data to yourself. %s protects you from many of the most common trackers that follow what you do online.</string>
                <!-- Text displayed that links to website about enhanced tracking protection -->
                <string name="preference_enhanced_tracking_protection_explanation_learn_more">Learn more</string>
                <!-- Preference for enhanced tracking protection for the standard protection settings -->
                <string name="preference_enhanced_tracking_protection_standard_default_1">Standard (default)</string>
                <!-- Preference description for enhanced tracking protection for the standard protection settings -->
                <string name="preference_enhanced_tracking_protection_standard_description_4">Balanced for privacy and performance. Pages load normally.</string>
                <!--  Accessibility text for the Standard protection information icon  -->
                <string name="preference_enhanced_tracking_protection_standard_info_button">What’s blocked by standard tracking protection</string>
                <!-- Preference for enhanced tracking protection for the strict protection settings -->
                <string name="preference_enhanced_tracking_protection_strict">Strict</string>
                <!-- Preference description for enhanced tracking protection for the strict protection settings -->
                <string name="preference_enhanced_tracking_protection_strict_description_3">Blocks more trackers so pages load faster, but some on-page functionality may break.</string>
                <!--  Accessibility text for the Strict protection information icon  -->
                <string name="preference_enhanced_tracking_protection_strict_info_button">What’s blocked by strict tracking protection</string>
                <!-- Preference for enhanced tracking protection for the custom protection settings -->
                <string name="preference_enhanced_tracking_protection_custom">Custom</string>
                <!-- Preference description for enhanced tracking protection for the strict protection settings -->
                <string name="preference_enhanced_tracking_protection_custom_description_2">Choose which trackers and scripts to block.</string>
                <!--  Accessibility text for the Strict protection information icon  -->
                <string name="preference_enhanced_tracking_protection_custom_info_button">What’s blocked by custom tracking protection</string>
                <!-- Header for categories that are being blocked by current Enhanced Tracking Protection settings -->
                <!-- Preference for enhanced tracking protection for the custom protection settings for cookies-->
                <string name="preference_enhanced_tracking_protection_custom_cookies">Cookies</string>
                <!-- Option for enhanced tracking protection for the custom protection settings for cookies-->
                <string name="preference_enhanced_tracking_protection_custom_cookies_1">Cross-site and social media trackers</string>
                <!-- Option for enhanced tracking protection for the custom protection settings for cookies-->
                <string name="preference_enhanced_tracking_protection_custom_cookies_2">Cookies from unvisited sites</string>
                <!-- Option for enhanced tracking protection for the custom protection settings for cookies-->
                <string name="preference_enhanced_tracking_protection_custom_cookies_3">All third-party cookies (may cause websites to break)</string>
                <!-- Option for enhanced tracking protection for the custom protection settings for cookies-->
                <string name="preference_enhanced_tracking_protection_custom_cookies_4">All cookies (will cause websites to break)</string>
                <!-- Option for enhanced tracking protection for the custom protection settings for cookies-->
                <string name="preference_enhanced_tracking_protection_custom_cookies_5">Isolate cross-site cookies</string>
                <!-- Preference for enhanced tracking protection for the custom protection settings for tracking content -->
                <string name="preference_enhanced_tracking_protection_custom_tracking_content">Tracking content</string>
                <!-- Option for enhanced tracking protection for the custom protection settings for tracking content-->
                <string name="preference_enhanced_tracking_protection_custom_tracking_content_1">In all tabs</string>
                <!-- Option for enhanced tracking protection for the custom protection settings for tracking content-->
                <string name="preference_enhanced_tracking_protection_custom_tracking_content_2">Only in Private tabs</string>
                <!-- Preference for enhanced tracking protection for the custom protection settings -->
                <string name="preference_enhanced_tracking_protection_custom_cryptominers">Cryptominers</string>
                <!-- Preference for enhanced tracking protection for the custom protection settings -->
                <string name="preference_enhanced_tracking_protection_custom_fingerprinters">Fingerprinters</string>
                <!-- Button label for navigating to the Enhanced Tracking Protection details -->
                <string name="enhanced_tracking_protection_details">Details</string>
                <!-- Header for categories that are being being blocked by current Enhanced Tracking Protection settings -->
                <string name="enhanced_tracking_protection_blocked">Blocked</string>
                <!-- Header for categories that are being not being blocked by current Enhanced Tracking Protection settings -->
                <string name="enhanced_tracking_protection_allowed">Allowed</string>
                <!-- Category of trackers (social media trackers) that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_social_media_trackers_title">Social Media Trackers</string>
                <!-- Description of social media trackers that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_social_media_trackers_description">Limits the ability of social networks to track your browsing activity around the web.</string>
                <!-- Category of trackers (cross-site tracking cookies) that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_cookies_title">Cross-Site Tracking Cookies</string>
                <!-- Category of trackers (cross-site tracking cookies) that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_cookies_title_2">Cross-Site Cookies</string>
                <!-- Description of cross-site tracking cookies that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_cookies_description">Blocks cookies that ad networks and analytics companies use to compile your browsing data across many sites.</string>
                <!-- Description of cross-site tracking cookies that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_cookies_description_2">Total Cookie Protection isolates cookies to the site you’re on so trackers like ad networks can’t use them to follow you across sites.</string>
                <!-- Category of trackers (cryptominers) that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_cryptominers_title">Cryptominers</string>
                <!-- Description of cryptominers that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_cryptominers_description">Prevents malicious scripts gaining access to your device to mine digital currency.</string>
                <!-- Category of trackers (fingerprinters) that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_fingerprinters_title">Fingerprinters</string>
                <!-- Description of fingerprinters that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_fingerprinters_description">Stops uniquely identifiable data from being collected about your device that can be used for tracking purposes.</string>
                <!-- Category of trackers (tracking content) that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_tracking_content_title">Tracking Content</string>
                <!-- Description of tracking content that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_tracking_content_description">Stops outside ads, videos, and other content from loading that contains tracking code. May affect some website functionality.</string>
                <!-- Enhanced Tracking Protection message that protection is currently on for this site -->
                <string name="etp_panel_on">Protections are ON for this site</string>
                <!-- Enhanced Tracking Protection message that protection is currently off for this site -->
                <string name="etp_panel_off">Protections are OFF for this site</string>
                <!-- Header for exceptions list for which sites enhanced tracking protection is always off -->
                <string name="enhanced_tracking_protection_exceptions">Enhanced Tracking Protection is off for these websites</string>
                <!-- Content description (not visible, for screen readers etc.): Navigate
                back from ETP details (Ex: Tracking content) -->
                <string name="etp_back_button_content_description">Navigate back</string>
                <!-- About page link text to open what's new link -->
                <string name="about_whats_new">What’s new in %s</string>
                <!-- Open source licenses page title
                The first parameter is the app name -->
                <string name="open_source_licenses_title">%s | OSS Libraries</string>
                <!-- Category of trackers (redirect trackers) that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_redirect_trackers_title">Redirect Trackers</string>
                <!-- Description of redirect tracker cookies that can be blocked by Enhanced Tracking Protection -->
                <string name="etp_redirect_trackers_description">Clears cookies set by redirects to known tracking websites.</string>
                <!-- Description of the SmartBlock Enhanced Tracking Protection feature. The * symbol is intentionally hardcoded here,
                     as we use it on the UI to indicate which trackers have been partially unblocked.  -->
                <string name="preference_etp_smartblock_description">Some trackers marked below have been partially unblocked on this page because you interacted with them *.</string>
                <!-- Text displayed that links to website about enhanced tracking protection SmartBlock -->
                <string name="preference_etp_smartblock_learn_more">Learn more</string>

                <!-- About page link text to open support link -->
                <string name="about_support">Support</string>
                <!-- About page link text to list of past crashes (like about:crashes on desktop) -->
                <string name="about_crashes">Crashes</string>
                <!-- About page link text to open privacy notice link -->
                <string name="about_privacy_notice">Privacy notice</string>
                <!-- About page link text to open know your rights link -->
                <string name="about_know_your_rights">Know your rights</string>
                <!-- About page link text to open licensing information link -->
                <string name="about_licensing_information">Licensing information</string>
                <!-- About page link text to open a screen with libraries that are used -->
                <string name="about_other_open_source_libraries">Libraries that we use</string>
                <!-- Toast shown to the user when they are activating the secret dev menu
                    The first parameter is number of long clicks left to enable the menu -->
                <string name="about_debug_menu_toast_progress">Debug menu: %1b click(s) left to enable</string>
                <string name="about_debug_menu_toast_done">Debug menu enabled</string>

                <!-- Browser long press popup menu -->
                <!-- Copy the current url -->
                <string name="browser_toolbar_long_press_popup_copy">Copy</string>
                <!-- Paste & go the text in the clipboard. '&amp;' is replaced with the ampersand symbol: & -->
                <string name="browser_toolbar_long_press_popup_paste_and_go">Paste &amp; Go</string>
                <!-- Paste the text in the clipboard -->
                <string name="browser_toolbar_long_press_popup_paste">Paste</string>
                <!-- Snackbar message shown after an URL has been copied to clipboard. -->
                <string name="browser_toolbar_url_copied_to_clipboard_snackbar">URL copied to clipboard</string>

                <!-- Title text for the Add To Homescreen dialog -->
                <string name="add_to_homescreen_title">Add to Home screen</string>
                <!-- Cancel button text for the Add to Homescreen dialog -->
                <string name="add_to_homescreen_cancel">Cancel</string>
                <!-- Add button text for the Add to Homescreen dialog -->
                <string name="add_to_homescreen_add">Add</string>
                <!-- Continue to website button text for the first-time Add to Homescreen dialog -->
                <string name="add_to_homescreen_continue">Continue to website</string>
                <!-- Placeholder text for the TextView in the Add to Homescreen dialog -->
                <string name="add_to_homescreen_text_placeholder">Shortcut name</string>
                <!-- Describes the add to homescreen functionality -->
                <string name="add_to_homescreen_description_2">You can easily add this website to your device’s Home screen to have instant access and browse faster with an app-like experience.</string>

                <!-- Preference for managing the settings for logins and passwords in Fenix -->
                <string name="preferences_passwords_logins_and_passwords">Logins and passwords</string>
                <!-- Preference for managing the saving of logins and passwords in Fenix -->
                <string name="preferences_passwords_save_logins">Save logins and passwords</string>
                <!-- Preference option for asking to save passwords in Fenix -->
                <string name="preferences_passwords_save_logins_ask_to_save">Ask to save</string>
                <!-- Preference option for never saving passwords in Fenix -->
                <string name="preferences_passwords_save_logins_never_save">Never save</string>
                <!-- Preference for autofilling saved logins in Firefox (in web content), %1a will be replaced with the app name -->
                <string name="preferences_passwords_autofill2">Autofill in %1a</string>
                <!-- Description for the preference for autofilling saved logins in Firefox (in web content), %1a will be replaced with the app name -->
                <string name="preferences_passwords_autofill_description">Fill and save usernames and passwords in websites while using %1a.</string>
                <!-- Preference for autofilling logins from Fenix in other apps (e.g. autofilling the Twitter app) -->
                <string name="preferences_android_autofill">Autofill in other apps</string>
                <!-- Description for the preference for autofilling logins from Fenix in other apps (e.g. autofilling the Twitter app) -->
                <string name="preferences_android_autofill_description">Fill usernames and passwords in other apps on your device.</string>
                <!-- Preference option for adding a login -->
                <string name="preferences_logins_add_login">Add login</string>

                <!-- Preference for syncing saved logins in Fenix -->
                <string name="preferences_passwords_sync_logins">Sync logins</string>
                <!-- Preference for syncing saved logins in Fenix, when not signed in-->
                <string name="preferences_passwords_sync_logins_across_devices">Sync logins across devices</string>
                <!-- Preference to access list of saved logins -->
                <string name="preferences_passwords_saved_logins">Saved logins</string>
                <!-- Description of empty list of saved passwords. Placeholder is replaced with app name.  -->
                <string name="preferences_passwords_saved_logins_description_empty_text">The logins you save or sync to %s will show up here.</string>
                <!-- Preference to access list of saved logins -->
                <string name="preferences_passwords_saved_logins_description_empty_learn_more_link">Learn more about Sync.</string>
                <!-- Preference to access list of login exceptions that we never save logins for -->
                <string name="preferences_passwords_exceptions">Exceptions</string>
                <!-- Empty description of list of login exceptions that we never save logins for -->
                <string name="preferences_passwords_exceptions_description_empty">Logins and passwords that are not saved will be shown here.</string>
                <!-- Description of list of login exceptions that we never save logins for -->
                <string name="preferences_passwords_exceptions_description">Logins and passwords will not be saved for these sites.</string>
                <!-- Text on button to remove all saved login exceptions -->
                <string name="preferences_passwords_exceptions_remove_all">Delete all exceptions</string>
                <!-- Hint for search box in logins list -->
                <string name="preferences_passwords_saved_logins_search">Search logins</string>
                <!-- The header for the site that a login is for -->
                <string name="preferences_passwords_saved_logins_site">Site</string>
                <!-- The header for the username for a login -->
                <string name="preferences_passwords_saved_logins_username">Username</string>
                <!-- The header for the password for a login -->
                <string name="preferences_passwords_saved_logins_password">Password</string>
                <!-- Shown in snackbar to tell user that the password has been copied -->
                <string name="logins_password_copied">Password copied to clipboard</string>
                <!-- Shown in snackbar to tell user that the username has been copied -->
                <string name="logins_username_copied">Username copied to clipboard</string>
                <!-- Content Description (for screenreaders etc) read for the button to copy a password in logins-->
                <string name="saved_logins_copy_password">Copy password</string>
                <!-- Content Description (for screenreaders etc) read for the button to clear a password while editing a login-->
                <string name="saved_logins_clear_password">Clear password</string>
                <!-- Content Description (for screenreaders etc) read for the button to copy a username in logins -->
                <string name="saved_login_copy_username">Copy username</string>
                <!-- Content Description (for screenreaders etc) read for the button to clear a username while editing a login -->
                <string name="saved_login_clear_username">Clear username</string>
                <!-- Content Description (for screenreaders etc) read for the button to clear the hostname field while creating a login -->
                <string name="saved_login_clear_hostname">Clear hostname</string>
                <!-- Content Description (for screenreaders etc) read for the button to open a site in logins -->
                <string name="saved_login_open_site">Open site in browser</string>
                <!-- Content Description (for screenreaders etc) read for the button to reveal a password in logins -->
                <string name="saved_login_reveal_password">Show password</string>
                <!-- Content Description (for screenreaders etc) read for the button to hide a password in logins -->
                <string name="saved_login_hide_password">Hide password</string>
                <!-- Message displayed in biometric prompt displayed for authentication before allowing users to view their logins -->
                <string name="logins_biometric_prompt_message">Unlock to view your saved logins</string>
                <!-- Title of warning dialog if users have no device authentication set up -->
                <string name="logins_warning_dialog_title">Secure your logins and passwords</string>
                <!-- Message of warning dialog if users have no device authentication set up -->
                <string name="logins_warning_dialog_message">Set up a device lock pattern, PIN, or password to protect your saved logins and passwords from being accessed if someone else has your device.</string>
                <!-- Negative button to ignore warning dialog if users have no device authentication set up -->
                <string name="logins_warning_dialog_later">Later</string>
                <!-- Positive button to send users to set up a pin of warning dialog if users have no device authentication set up -->
                <string name="logins_warning_dialog_set_up_now">Set up now</string>
                <!-- Title of PIN verification dialog to direct users to re-enter their device credentials to access their logins -->
                <string name="logins_biometric_prompt_message_pin">Unlock your device</string>
                <!-- Title for Accessibility Force Enable Zoom Preference -->
                <string name="preference_accessibility_force_enable_zoom">Zoom on all websites</string>
                <!-- Summary for Accessibility Force Enable Zoom Preference -->
                <string name="preference_accessibility_force_enable_zoom_summary">Enable to allow pinch and zoom, even on websites that prevent this gesture.</string>
                <!-- Saved logins sorting strategy menu item -by name- (if selected, it will sort saved logins alphabetically) -->
                <string name="saved_logins_sort_strategy_alphabetically">Name (A-Z)</string>
                <!-- Saved logins sorting strategy menu item -by last used- (if selected, it will sort saved logins by last used) -->
                <string name="saved_logins_sort_strategy_last_used">Last used</string>
                <!-- Content description (not visible, for screen readers etc.): Sort saved logins dropdown menu chevron icon -->
                <string name="saved_logins_menu_dropdown_chevron_icon_content_description">Sort logins menu</string>

                <!-- Autofill -->
                <!-- Preference and title for managing the autofill settings -->
                <string name="preferences_autofill">Autofill</string>
                <!-- Preference and title for managing the settings for addresses -->
                <string name="preferences_addresses">Addresses</string>
                <!-- Preference and title for managing the settings for credit cards -->
                <string name="preferences_credit_cards">Credit cards</string>
                <!-- Preference for saving and autofilling credit cards -->
                <string name="preferences_credit_cards_save_and_autofill_cards">Save and autofill cards</string>
                <!-- Preference summary for saving and autofilling credit card data -->
                <string name="preferences_credit_cards_save_and_autofill_cards_summary">Data is encrypted</string>
                <!-- Preference option for syncing credit cards across devices. This is displayed when the user is not signed into sync -->
                <string name="preferences_credit_cards_sync_cards_across_devices">Sync cards across devices</string>
                <!-- Preference option for syncing credit cards across devices. This is displayed when the user is signed into sync -->
                <string name="preferences_credit_cards_sync_cards">Sync cards</string>
                <!-- Preference option for adding a credit card -->
                <string name="preferences_credit_cards_add_credit_card">Add credit card</string>
                <!-- Preference option for managing saved credit cards -->
                <string name="preferences_credit_cards_manage_saved_cards">Manage saved cards</string>
                <!-- Preference option for adding an address -->
                <string name="preferences_addresses_add_address">Add address</string>
                <!-- Preference option for managing saved addresses -->
                <string name="preferences_addresses_manage_addresses">Manage addresses</string>
                <!-- Preference for saving and autofilling addresses -->
                <string name="preferences_addresses_save_and_autofill_addresses">Save and autofill addresses</string>
                <!-- Preference summary for saving and autofilling address data -->
                <string name="preferences_addresses_save_and_autofill_addresses_summary">Include information like numbers, email and shipping addresses</string>

                <!-- Title of the "Add card" screen -->
                <string name="credit_cards_add_card">Add card</string>
                <!-- Title of the "Edit card" screen -->
                <string name="credit_cards_edit_card">Edit card</string>
                <!-- The header for the card number of a credit card -->
                <string name="credit_cards_card_number">Card Number</string>
                <!-- The header for the expiration date of a credit card -->
                <string name="credit_cards_expiration_date">Expiration Date</string>
                <!-- The label for the expiration date month of a credit card to be used by a11y services-->
                <string name="credit_cards_expiration_date_month">Expiration Date Month</string>
                <!-- The label for the expiration date year of a credit card to be used by a11y services-->
                <string name="credit_cards_expiration_date_year">Expiration Date Year</string>
                <!-- The header for the name on the credit card -->
                <string name="credit_cards_name_on_card">Name on Card</string>
                <!-- The text for the "Delete card" menu item for deleting a credit card -->
                <string name="credit_cards_menu_delete_card">Delete card</string>
                <!-- The text for the "Delete card" button for deleting a credit card -->
                <string name="credit_cards_delete_card_button">Delete card</string>
                <!-- The text for the confirmation message of "Delete card" dialog -->
                <string name="credit_cards_delete_dialog_confirmation">Are you sure you want to delete this credit card?</string>
                <!-- The text for the positive button on "Delete card" dialog -->
                <string name="credit_cards_delete_dialog_button">Delete</string>
                <!-- The title for the "Save" menu item for saving a credit card -->
                <string name="credit_cards_menu_save">Save</string>
                <!-- The text for the "Save" button for saving a credit card -->
                <string name="credit_cards_save_button">Save</string>
                <!-- The text for the "Cancel" button for cancelling adding, updating or deleting a credit card -->
                <string name="credit_cards_cancel_button">Cancel</string>
                <!-- Title of the "Saved cards" screen -->
                <string name="credit_cards_saved_cards">Saved cards</string>
                <!-- Error message for credit card number validation -->
                <string name="credit_cards_number_validation_error_message">Please enter a valid credit card number</string>
                <!-- Error message for credit card name on card validation -->
                <string name="credit_cards_name_on_card_validation_error_message">Please fill out this field</string>
                <!-- Message displayed in biometric prompt displayed for authentication before allowing users to view their saved credit cards -->
                <string name="credit_cards_biometric_prompt_message">Unlock to view your saved cards</string>
                <!-- Title of warning dialog if users have no device authentication set up -->
                <string name="credit_cards_warning_dialog_title">Secure your credit cards</string>
                <!-- Message of warning dialog if users have no device authentication set up -->
                <string name="credit_cards_warning_dialog_message">Set up a device lock pattern, PIN, or password to protect your saved credit cards from being accessed if someone else has your device.</string>
                <!-- Positive button to send users to set up a pin of warning dialog if users have no device authentication set up -->
                <string name="credit_cards_warning_dialog_set_up_now">Set up now</string>
                <!-- Negative button to ignore warning dialog if users have no device authentication set up -->
                <string name="credit_cards_warning_dialog_later">Later</string>
                <!-- Title of PIN verification dialog to direct users to re-enter their device credentials to access their credit cards -->
                <string name="credit_cards_biometric_prompt_message_pin">Unlock your device</string>
                <!-- Message displayed in biometric prompt for authentication, before allowing users to use their stored credit card information -->
                <string name="credit_cards_biometric_prompt_unlock_message">Unlock to use stored credit card information</string>
                <!-- Title of the "Add address" screen -->
                <string name="addresses_add_address">Add address</string>
                <!-- Title of the "Edit address" screen -->
                <string name="addresses_edit_address">Edit address</string>
                <!-- Title of the "Manage addresses" screen -->
                <string name="addresses_manage_addresses">Manage addresses</string>
                <!-- The header for the first name of an address -->
                <string name="addresses_first_name">First Name</string>
                <!-- The header for the middle name of an address -->
                <string name="addresses_middle_name">Middle Name</string>
                <!-- The header for the last name of an address -->
                <string name="addresses_last_name">Last Name</string>
                <!-- The header for the street address of an address -->
                <string name="addresses_street_address">Street Address</string>
                <!-- The header for the city of an address -->
                <string name="addresses_city">City</string>
                <!-- The header for the subregion of an address when "state" should be used -->
                <string name="addresses_state">State</string>
                <!-- The header for the subregion of an address when "province" should be used -->
                <string name="addresses_province">Province</string>
                <!-- The header for the zip code of an address -->
                <string name="addresses_zip">Zip</string>
                <!-- The header for the country or region of an address -->
                <string name="addresses_country">Country or region</string>
                <!-- The header for the phone number of an address -->
                <string name="addresses_phone">Phone</string>
                <!-- The header for the email of an address -->
                <string name="addresses_email">Email</string>
                <!-- The text for the "Save" button for saving an address -->
                <string name="addresses_save_button">Save</string>
                <!-- The text for the "Cancel" button for cancelling adding, updating or deleting an address -->
                <string name="addresses_cancel_button">Cancel</string>
                <!-- The text for the "Delete address" button for deleting an address -->
                <string name="addressess_delete_address_button">Delete address</string>
                <!-- The title for the "Delete address" confirmation dialog -->
                <string name="addressess_confirm_dialog_message">Are you sure you want to delete this address?</string>
                <!-- The text for the positive button on "Delete address" dialog -->
                <string name="addressess_confirm_dialog_ok_button">Delete</string>
                <!-- The text for the negative button on "Delete address" dialog -->
                <string name="addressess_confirm_dialog_cancel_button">Cancel</string>
                <!-- The text for the "Save address" menu item for saving an address -->
                <string name="address_menu_save_address">Save address</string>
                <!-- The text for the "Delete address" menu item for deleting an address -->
                <string name="address_menu_delete_address">Delete address</string>

                <!-- Title of the Add search engine screen -->
                <string name="search_engine_add_custom_search_engine_title">Add search engine</string>
                <!-- Title of the Edit search engine screen -->
                <string name="search_engine_edit_custom_search_engine_title">Edit search engine</string>
                <!-- Content description (not visible, for screen readers etc.): Title for the button to add a search engine in the action bar -->
                <string name="search_engine_add_button_content_description">Add</string>
                <!-- Content description (not visible, for screen readers etc.): Title for the button to save a search engine in the action bar -->
                <string name="search_engine_add_custom_search_engine_edit_button_content_description">Save</string>
                <!-- Text for the menu button to edit a search engine -->
                <string name="search_engine_edit">Edit</string>
                <!-- Text for the menu button to delete a search engine -->
                <string name="search_engine_delete">Delete</string>

                <!-- Text for the button to create a custom search engine on the Add search engine screen -->
                <string name="search_add_custom_engine_label_other">Other</string>
                <!-- Placeholder text shown in the Search Engine Name TextField before a user enters text -->
                <string name="search_add_custom_engine_name_hint">Name</string>
                <!-- Placeholder text shown in the Search String TextField before a user enters text -->
                <string name="search_add_custom_engine_search_string_hint">Search string to use</string>
                <!-- Description text for the Search String TextField. The %s is part of the string -->
                <string name="search_add_custom_engine_search_string_example" formatted="false">Replace query with “%s”. Example:\nhttps://www.google.com/search?q=%s</string>
                <!-- Accessibility description for the form in which details about the custom search engine are entered -->
                <string name="search_add_custom_engine_form_description">Custom search engine details</string>

                <!-- Text shown when a user leaves the name field empty -->
                <string name="search_add_custom_engine_error_empty_name">Enter search engine name</string>
                <!-- Text shown when a user leaves the search string field empty -->
                <string name="search_add_custom_engine_error_empty_search_string">Enter a search string</string>
                <!-- Text shown when a user leaves out the required template string -->
                <string name="search_add_custom_engine_error_missing_template">Check that search string matches Example format</string>
                <!-- Text shown when we aren't able to validate the custom search query. The first parameter is the url of the custom search engine -->
                <string name="search_add_custom_engine_error_cannot_reach">Error connecting to “%s”</string>
                <!-- Text shown when a user creates a new search engine -->
                <string name="search_add_custom_engine_success_message">Created %s</string>
                <!-- Text shown when a user successfully edits a custom search engine -->
                <string name="search_edit_custom_engine_success_message">Saved %s</string>
                <!-- Text shown when a user successfully deletes a custom search engine -->
                <string name="search_delete_search_engine_success_message">Deleted %s</string>

                <!-- Heading for the instructions to allow a permission -->
                <string name="phone_feature_blocked_intro">To allow it:</string>
                <!-- First step for the allowing a permission -->
                <string name="phone_feature_blocked_step_settings">1. Go to Android Settings</string>
                <!-- Second step for the allowing a permission -->
                <string name="phone_feature_blocked_step_permissions"><![CDATA[2. Tap <b>Permissions</b>]]></string>
                <!-- Third step for the allowing a permission (Fore example: Camera) -->
                <string name="phone_feature_blocked_step_feature"><![CDATA[3. Toggle <b>%1a</b> to ON]]></string>

                <!-- Label that indicates a site is using a secure connection -->
                <string name="quick_settings_sheet_secure_connection_2">Connection is secure</string>
                <!-- Label that indicates a site is using a insecure connection -->
                <string name="quick_settings_sheet_insecure_connection_2">Connection is not secure</string>
                <!-- Label to clear site data -->
                <string name="clear_site_data">Clear cookies and site data</string>
                <!-- Confirmation message for a dialog confirming if the user wants to delete all data for current site -->
                <string name="confirm_clear_site_data"><![CDATA[Are you sure that you want to clear all the cookies and data for the site <b>%s</b>?]]></string>
                <!-- Confirmation message for a dialog confirming if the user wants to delete all the permissions for all sites-->
                <string name="confirm_clear_permissions_on_all_sites">Are you sure that you want to clear all the permissions on all sites?</string>
                <!-- Confirmation message for a dialog confirming if the user wants to delete all the permissions for a site-->
                <string name="confirm_clear_permissions_site">Are you sure that you want to clear all the permissions for this site?</string>
                <!-- Confirmation message for a dialog confirming if the user wants to set default value a permission for a site-->
                <string name="confirm_clear_permission_site">Are you sure that you want to clear this permission for this site?</string>
                <!-- label shown when there are not site exceptions to show in the site exception settings -->
                <string name="no_site_exceptions">No site exceptions</string>
                <!-- Bookmark deletion confirmation -->
                <string name="bookmark_deletion_confirmation">Are you sure you want to delete this bookmark?</string>
                <!-- Browser menu button that adds a shortcut to the home fragment -->
                <string name="browser_menu_add_to_shortcuts">Add to shortcuts</string>
                <!-- Browser menu button that removes a shortcut from the home fragment -->
                <string name="browser_menu_remove_from_shortcuts">Remove from shortcuts</string>
                <!-- text shown before the issuer name to indicate who its verified by, parameter is the name of
                 the certificate authority that verified the ticket-->
                <string name="certificate_info_verified_by">Verified By: %1a </string>
                <!-- Login overflow menu delete button -->
                <string name="login_menu_delete_button">Delete</string>
                <!-- Login overflow menu edit button -->
                <string name="login_menu_edit_button">Edit</string>
                <!-- Message in delete confirmation dialog for logins -->
                <string name="login_deletion_confirmation">Are you sure you want to delete this login?</string>
                <!-- Positive action of a dialog asking to delete  -->
                <string name="dialog_delete_positive">Delete</string>
                <!-- Negative action of a dialog asking to delete login -->
                <string name="dialog_delete_negative">Cancel</string>
                <!--  The saved login options menu description. -->
                <string name="login_options_menu">Login options</string>
                <!--  The editable text field for a login's web address. -->
                <string name="saved_login_hostname_description">The editable text field for the web address of the login.</string>
                <!--  The editable text field for a login's username. -->
                <string name="saved_login_username_description">The editable text field for the username of the login.</string>
                <!--  The editable text field for a login's password. -->
                <string name="saved_login_password_description">The editable text field for the password of the login.</string>
                <!--  The button description to save changes to an edited login. -->
                <string name="save_changes_to_login">Save changes to login.</string>
                <!--  The page title for editing a saved login. -->
                <string name="edit">Edit</string>
                <!--  The page title for adding new login. -->
                <string name="add_login">Add new login</string>
                <!--  The error message in add/edit login view when password field is blank. -->
                <string name="saved_login_password_required">Password required</string>
                <!--  The error message in add login view when username field is blank. -->
                <string name="saved_login_username_required">Username required</string>
                <!--  The error message in add login view when hostname field is blank. -->
                <string name="saved_login_hostname_required" >Hostname required</string>
                <!-- Voice search button content description  -->
                <string name="voice_search_content_description">Voice search</string>
                <!-- Voice search prompt description displayed after the user presses the voice search button -->
                <string name="voice_search_explainer">Speak now</string>
                <!--  The error message in edit login view when a duplicate username exists. -->
                <string name="saved_login_duplicate">A login with that username already exists</string>
                <!-- This is the hint text that is shown inline on the hostname field of the create new login page. 'https://www.example.com' intentionally hardcoded here -->
                <string name="add_login_hostname_hint_text">https://www.example.com</string>
                <!-- This is an error message shown below the hostname field of the add login page when a hostname does not contain http or https. -->
                <string name="add_login_hostname_invalid_text_3">Web address must contain "https://" or "http://"</string>
                <!-- This is an error message shown below the hostname field of the add login page when a hostname is invalid. -->
                <string name="add_login_hostname_invalid_text_2">Valid hostname required</string>

                <!-- Synced Tabs -->
                <!-- Text displayed to ask user to connect another device as no devices found with account -->
                <string name="synced_tabs_connect_another_device">Connect another device.</string>
                <!-- Text displayed asking user to re-authenticate -->
                <string name="synced_tabs_reauth">Please re-authenticate.</string>
                <!-- Text displayed when user has disabled tab syncing in Firefox Sync Account -->
                <string name="synced_tabs_enable_tab_syncing">Please enable tab syncing.</string>
                <!-- Text displayed when user has no tabs that have been synced -->
                <string name="synced_tabs_no_tabs">You don’t have any tabs open in Firefox on your other devices.</string>
                <!-- Text displayed in the synced tabs screen when a user is not signed in to Firefox Sync describing Synced Tabs -->
                <string name="synced_tabs_sign_in_message">View a list of tabs from your other devices.</string>
                <!-- Text displayed on a button in the synced tabs screen to link users to sign in when a user is not signed in to Firefox Sync -->
                <string name="synced_tabs_sign_in_button">Sign in to sync</string>
                <!-- The text displayed when a synced device has no tabs to show in the list of Synced Tabs. -->
                <string name="synced_tabs_no_open_tabs">No open tabs</string>
                <!-- Content description for expanding a group of synced tabs. -->
                <string name="synced_tabs_expand_group">Expand group of synced tabs</string>
                <!-- Content description for collapsing a group of synced tabs. -->
                <string name="synced_tabs_collapse_group">Collapse group of synced tabs</string>

                <!-- Top Sites -->
                <!-- Title text displayed in the dialog when shortcuts limit is reached. -->
                <string name="shortcut_max_limit_title">Shortcut limit reached</string>
                <!-- Content description text displayed in the dialog when shortcut limit is reached. -->
                <string name="shortcut_max_limit_content">To add a new shortcut, remove one. Touch and hold the site and select remove.</string>
                <!-- Confirmation dialog button text when top sites limit is reached. -->
                <string name="top_sites_max_limit_confirmation_button">OK, Got It</string>
                <!-- Label for the preference to show the shortcuts for the most visited top sites on the homepage -->
                <string name="top_sites_toggle_top_recent_sites_4">Shortcuts</string>
            	<!-- Title text displayed in the rename top site dialog. -->
            	<string name="top_sites_rename_dialog_title">Name</string>
                <!-- Hint for renaming title of a shortcut -->
                <string name="shortcut_name_hint">Shortcut name</string>
            	<!-- Button caption to confirm the renaming of the top site. -->
            	<string name="top_sites_rename_dialog_ok">OK</string>
            	<!-- Dialog button text for canceling the rename top site prompt. -->
            	<string name="top_sites_rename_dialog_cancel">Cancel</string>
                <!-- Text for the menu button to open the homepage settings. -->
                <string name="top_sites_menu_settings">Settings</string>
                <!-- Text for the menu button to navigate to sponsors and privacy support articles. '&amp;' is replaced with the ampersand symbol: & -->
                <string name="top_sites_menu_sponsor_privacy">Our sponsors &amp; your privacy</string>
                <!-- Label text displayed for a sponsored top site. -->
                <string name="top_sites_sponsored_label">Sponsored</string>

                <!-- Inactive tabs in the tabs tray -->
                <!-- Title text displayed in the tabs tray when a tab has been unused for 14 days. -->
                <string name="inactive_tabs_title">Inactive tabs</string>
                <!-- Content description for closing all inactive tabs -->
                <string name="inactive_tabs_delete_all">Close all inactive tabs</string>
                <!-- Content description for expanding the inactive tabs section. -->
                <string name="inactive_tabs_expand_content_description">Expand inactive tabs</string>
                <!-- Content description for collapsing the inactive tabs section. -->
                <string name="inactive_tabs_collapse_content_description">Collapse inactive tabs</string>

                <!-- Inactive tabs auto-close message in the tabs tray -->
                <!-- The header text of the auto-close message when the user is asked if they want to turn on the auto-closing of inactive tabs. -->
                <string name="inactive_tabs_auto_close_message_header" >Auto-close after one month?</string>
                <!-- A description below the header to notify the user what the inactive tabs auto-close feature is. -->
                <string name="inactive_tabs_auto_close_message_description" >Firefox can close tabs you haven’t viewed over the past month.</string>
                <!-- A call to action below the description to allow the user to turn on the auto closing of inactive tabs. -->
                <string name="inactive_tabs_auto_close_message_action" >TURN ON AUTO CLOSE</string>
                <!-- Text for the snackbar to confirm auto-close is enabled for inactive tabs -->
                <string name="inactive_tabs_auto_close_message_snackbar">Auto-close enabled</string>

                <!-- Awesome bar suggestion's headers -->
                <!-- Search suggestions title for Firefox Suggest. -->
                <string name="firefox_suggest_header">Firefox Suggest</string>
                <!-- Title for search suggestions when Google is the default search suggestion engine. -->
                <string name="google_search_engine_suggestion_header">Google Search</string>
                <!-- Title for search suggestions when the default search suggestion engine is anything other than Google. The first parameter is default search engine name. -->
                <string name="other_default_search_engine_suggestion_header">%s search</string>

                <!-- Default browser experiment -->
                <string name="default_browser_experiment_card_text">Set links from websites, emails, and messages to open automatically in Firefox.</string>

                <!-- Content description for close button in collection placeholder. -->
                <string name="remove_home_collection_placeholder_content_description">Remove</string>

                <!-- Content description radio buttons with a link to more information -->
                <string name="radio_preference_info_content_description">Click for more details</string>

                <!-- Content description for the action bar "up" button -->
                <string name="action_bar_up_description">Navigate up</string>

                <!-- Content description for privacy content close button -->
                <string name="privacy_content_close_button_content_description">Close</string>

                <!-- Pocket recommended stories -->
                <!-- Header text for a section on the home screen. -->
                <string name="pocket_stories_header_1">Thought-provoking stories</string>
                <!-- Header text for a section on the home screen. -->
                <string name="pocket_stories_categories_header">Stories by topic</string>
                <!-- Text of a button allowing users to access an external url for more Pocket recommendations. -->
                <string name="pocket_stories_placeholder_text">Discover more</string>
                <!-- Title of an app feature. Smaller than a heading. The first parameter is product name Pocket -->
                <string name="pocket_stories_feature_title_2">Powered by %s.</string>
                <!-- Caption for describing a certain feature. The placeholder is for a clickable text (eg: Learn more) which will load an url in a new tab when clicked.  -->
                <string name="pocket_stories_feature_caption">Part of the Firefox family. %s</string>
                <!-- Clickable text for opening an external link for more information about Pocket. -->
                <string name="pocket_stories_feature_learn_more">Learn more</string>
                <!-- Text indicating that the Pocket story that also displays this text is a sponsored story by other 3rd party entity. -->
                <string name="pocket_stories_sponsor_indication">Sponsored</string>

                <!-- Snackbar message for enrolling in a Nimbus experiment from the secret settings when Studies preference is Off.-->
                <string name="experiments_snackbar">Enable telemetry to send data.</string>
                <!-- Snackbar button text to navigate to telemetry settings.-->
                <string name="experiments_snackbar_button">Go to settings</string>

                <!-- Accessibility services actions labels. These will be appended to accessibility actions like "Double tap to.." but not by or applications but by services like Talkback. -->
                <!-- Action label for elements that can be collapsed if interacting with them. Talkback will append this to say "Double tap to collapse". -->
                <string name="a11y_action_label_collapse">collapse</string>
                <!-- Action label for elements that can be expanded if interacting with them. Talkback will append this to say "Double tap to expand". -->
                <string name="a11y_action_label_expand">expand</string>
                <!-- Action label for links to a website containing documentation about a wallpaper collection. Talkback will append this to say "Double tap to open link to learn more about this collection". -->
                <string name="a11y_action_label_wallpaper_collection_learn_more">open link to learn more about this collection</string>
                <!-- Action label for links that point to an article. Talkback will append this to say "Double tap to read the article". -->
                <string name="a11y_action_label_read_article">read the article</string>
        </resources>

    """.trimIndent()

    fun getLib(module: String) = """
        <resources>
            <string name="${module}_string">$module - My lib</string>

                <!-- Recent Tabs -->
                <!-- Header text for jumping back into the recent tab in the home screen -->
                <string name="${module}_recent_tabs_header">Jump back in</string>
                <!-- Button text for showing all the tabs in the tabs tray -->
                <string name="${module}_recent_tabs_show_all">Show all</string>
                <!-- Content description for the button which navigates the user to show all recent tabs in the tabs tray. -->
                <string name="${module}_recent_tabs_show_all_content_description_2">Show all recent tabs button</string>
                <!-- Text for button in synced tab card that opens synced tabs tray -->
                <string name="${module}_recent_tabs_see_all_synced_tabs_button_text">See all synced tabs</string>
                <!-- Accessibility description for device icon used for recent synced tab -->
                <string name="${module}_recent_tabs_synced_device_icon_content_description">Synced device</string>
                <!-- Text for the dropdown menu to remove a recent synced tab from the homescreen -->
                <string name="${module}_recent_synced_tab_menu_item_remove">Remove</string>
                <!-- Text for the menu button to remove a grouped highlight from the user's browsing history
                     in the Recently visited section -->
                <string name="${module}_recent_tab_menu_item_remove">Remove</string>

                <!-- History Metadata -->
                <!-- Header text for a section on the home screen that displays grouped highlights from the
                     user's browsing history, such as topics they have researched or explored on the web -->
                <string name="${module}_history_metadata_header_2">Recently visited</string>
                <!-- Text for the menu button to remove a grouped highlight from the user's browsing history
                     in the Recently visited section -->
                <string name="${module}_recently_visited_menu_item_remove">Remove</string>
                <!-- Content description for the button which navigates the user to show all of their history. -->
                <string name="${module}_past_explorations_show_all_content_description_2">Show all past explorations</string>

                <!-- Browser Fragment -->
                <!-- Content description (not visible, for screen readers etc.): Navigate backward (browsing history) -->
                <string name="${module}_browser_menu_back">Back</string>
                <!-- Content description (not visible, for screen readers etc.): Navigate forward (browsing history) -->
                <string name="${module}_browser_menu_forward">Forward</string>
                <!-- Content description (not visible, for screen readers etc.): Refresh current website -->
                <string name="${module}_browser_menu_refresh">Refresh</string>
                <!-- Content description (not visible, for screen readers etc.): Stop loading current website -->
                <string name="${module}_browser_menu_stop">Stop</string>
                <!-- Browser menu button that opens the addon manager -->
                <string name="${module}_browser_menu_add_ons">Add-ons</string>
                <!-- Browser menu button that opens account settings -->
                <string name="${module}_browser_menu_account_settings">Account info</string>
                <!-- Text displayed when there are no add-ons to be shown -->
                <string name="${module}_no_add_ons">No add-ons here</string>
                <!-- Browser menu button that sends a user to help articles -->
                <string name="${module}_browser_menu_help">Help</string>
                <!-- Browser menu button that sends a to a the what's new article -->
                <string name="${module}_browser_menu_whats_new">What’s new</string>
                <!-- Browser menu button that opens the settings menu -->
                <string name="${module}_browser_menu_settings">Settings</string>
                <!-- Browser menu button that opens a user's library -->
                <string name="${module}_browser_menu_library">Library</string>
                <!-- Browser menu toggle that requests a desktop site -->
                <string name="${module}_browser_menu_desktop_site">Desktop site</string>
                <!-- Browser menu toggle that adds a shortcut to the site on the device home screen. -->
                <string name="${module}_browser_menu_add_to_homescreen">Add to Home screen</string>
                <!-- Browser menu toggle that installs a Progressive Web App shortcut to the site on the device home screen. -->
                <string name="${module}_browser_menu_install_on_homescreen">Install</string>
                <!-- Content description (not visible, for screen readers etc.) for the Resync tabs button -->
                <string name="${module}_resync_button_content_description">Resync</string>
                <!-- Browser menu button that opens the find in page menu -->
                <string name="${module}_browser_menu_find_in_page">Find in page</string>
                <!-- Browser menu button that saves the current tab to a collection -->
                <string name="${module}_browser_menu_save_to_collection_2">Save to collection</string>
                <!-- Browser menu button that open a share menu to share the current site -->
                <string name="${module}_browser_menu_share">Share</string>
                <!-- Browser menu button shown in custom tabs that opens the current tab in Fenix
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="${module}_browser_menu_open_in_fenix">Open in %1\${'$'}s</string>
                <!-- Browser menu text shown in custom tabs to indicate this is a Fenix tab
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="${module}_browser_menu_powered_by">POWERED BY %1\${'$'}s</string>
                <!-- Browser menu text shown in custom tabs to indicate this is a Fenix tab
                    The first parameter is the name of the app defined in app_name (for example: Fenix) -->
                <string name="${module}_browser_menu_powered_by2">Powered by %1\${'$'}s</string>
                <!-- Browser menu button to put the current page in reader mode -->
                <string name="${module}_browser_menu_read">Reader view</string>
                <!-- Browser menu button content description to close reader mode and return the user to the regular browser -->
                <string name="${module}_browser_menu_read_close">Close reader view</string>
                <!-- Browser menu button to open the current page in an external app -->
                <string name="${module}_browser_menu_open_app_link">Open in app</string>
                <!-- Browser menu button to show reader view appearance controls e.g. the used font type and size -->
                <string name="${module}_browser_menu_customize_reader_view">Customize reader view</string>
                <!-- Browser menu label for adding a bookmark -->
                <string name="${module}_browser_menu_add">Add</string>
                <!-- Browser menu label for editing a bookmark -->
                <string name="${module}_browser_menu_edit">Edit</string>
                <!-- Button shown on the home page that opens the Customize home settings -->
                <string name="${module}_browser_menu_customize_home_1">Customize homepage</string>
                <!-- Browser Toolbar -->
                <!-- Content description for the Home screen button on the browser toolbar -->
                <string name="${module}_browser_toolbar_home">Home screen</string>

                <!-- Locale Settings Fragment -->
                <!-- Content description for tick mark on selected language -->
                <string name="${module}_a11y_selected_locale_content_description">Selected language</string>
                <!-- Text for default locale item -->
                <string name="${module}_default_locale_text">Follow device language</string>
                <!-- Placeholder text shown in the search bar before a user enters text -->
                <string name="${module}_locale_search_hint">Search language</string>

                <!-- Search Fragment -->
                <!-- Button in the search view that lets a user search by scanning a QR code -->
                <string name="${module}_search_scan_button">Scan</string>
                <!-- Button in the search view that lets a user change their search engine -->
                <string name="${module}_search_engine_button">Search engine</string>
                <!-- Button in the search view when shortcuts are displayed that takes a user to the search engine settings -->
                <string name="${module}_search_shortcuts_engine_settings">Search engine settings</string>
                <!-- Button in the search view that lets a user navigate to the site in their clipboard -->
                <string name="${module}_awesomebar_clipboard_title">Fill link from clipboard</string>
                <!-- Button in the search suggestions onboarding that allows search suggestions in private sessions -->
                <string name="${module}_search_suggestions_onboarding_allow_button">Allow</string>
                <!-- Button in the search suggestions onboarding that does not allow search suggestions in private sessions -->
                <string name="${module}_search_suggestions_onboarding_do_not_allow_button">Don’t allow</string>
                <!-- Search suggestion onboarding hint title text -->
                <string name="${module}_search_suggestions_onboarding_title">Allow search suggestions in private sessions?</string>
                <!-- Search suggestion onboarding hint description text, first parameter is the name of the app defined in app_name (for example: Fenix)-->
                <string name="${module}_search_suggestions_onboarding_text">%s will share everything you type in the address bar with your default search engine.</string>
                <!-- Search engine suggestion title text. The first parameter is the name of teh suggested engine-->
                <string name="${module}_search_engine_suggestions_title">Search %s</string>
                <!-- Search engine suggestion description text -->
                <string name="${module}_search_engine_suggestions_description">Search directly from the address bar</string>
                <!-- Menu option in the search selector menu to open the search settings -->
                <string name="${module}_search_settings_menu_item">Search settings</string>
                <!-- Header text for the search selector menu -->
                <string name="${module}_search_header_menu_item" >This time search:</string>
                <!-- Header text for the search selector menu -->
                <string name="${module}_search_header_menu_item_2">This time search in:</string>

                <!-- Home onboarding -->
                <!-- Onboarding home screen popup dialog, shown on top of the Jump back in section. -->
                <string name="${module}_onboarding_home_screen_jump_back_contextual_hint_2">Meet your personalized homepage. Recent tabs, bookmarks, and search results will appear here.</string>
                <!-- Home onboarding dialog welcome screen title text. -->
                <string name="${module}_onboarding_home_welcome_title_2">Welcome to a more personal internet</string>
                <!-- Home onboarding dialog welcome screen description text. -->
                <string name="${module}_onboarding_home_welcome_description">More colors. Better privacy. Same commitment to people over profits.</string>
                <!-- Home onboarding dialog sign into sync screen title text. -->
                <string name="${module}_onboarding_home_sync_title_3">Switching screens is easier than ever</string>
                <!-- Home onboarding dialog sign into sync screen description text. -->
                <string name="${module}_onboarding_home_sync_description">Pick up where you left off with tabs from other devices now on your homepage.</string>
                <!-- Text for the button to continue the onboarding on the home onboarding dialog. -->
                <string name="${module}_onboarding_home_get_started_button">Get started</string>
                <!-- Text for the button to navigate to the sync sign in screen on the home onboarding dialog. -->
                <string name="${module}_onboarding_home_sign_in_button">Sign in</string>
                <!-- Text for the button to skip the onboarding on the home onboarding dialog. -->
                <string name="${module}_onboarding_home_skip_button">Skip</string>
                <!-- Onboarding home screen sync popup dialog message, shown on top of Recent Synced Tabs in the Jump back in section. -->
                <string name="${module}_sync_cfr_message">Your tabs are syncing! Pick up where you left off on your other device.</string>
                <!-- Content description (not visible, for screen readers etc.): Close button for the home onboarding dialog -->
                <string name="${module}_onboarding_home_content_description_close_button">Close</string>
                <string name="${module}_reduce_cookie_banner_control_experiment_dialog_body_2">Allow %1\${'$'}s to automatically reject cookie requests when possible?</string>
                <!-- Remind me later text button for the onboarding dialog -->
                <string name="${module}_reduce_cookie_banner_dialog_not_now_button">Not Now</string>
                <!-- Change setting text button, for the dialog use on the control branch of the experiment to determine which context users engaged the most -->
                <string name="${module}_reduce_cookie_banner_control_experiment_dialog_change_setting_button">Dismiss banners</string>
                <!-- Snack text for the cookie banner dialog, after user hit the dismiss banner button -->
                <string name="${module}_reduce_cookie_banner_dialog_snackbar_text">You’ll see fewer cookie requests</string>
                <!-- Title text for the dialog use on the variant 1 branch of the experiment to determine which context users engaged the most -->
                <string name="${module}_reduce_cookie_banner_variant_1_experiment_dialog_title">See fewer cookie pop-ups</string>
                <!-- Body text for the dialog use on the variant 1 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="${module}_reduce_cookie_banner_variant_1_experiment_dialog_body" >Automatically answer cookie pop-ups for distraction-free browsing. %1\${'$'}s will reject all requests if possible, or accept all if not.</string>
                <!-- Body text for the dialog use on the variant 1 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="${module}_reduce_cookie_banner_variant_1_experiment_dialog_body_1">Automatically answer cookie pop-ups for distraction-free browsing. %1\${'$'}s will reject all requests if possible.</string>
                <!-- Change setting text button, for the onboarding dialog use on the variant 1 branch of the experiment to determine which context users engaged the most -->
                <string name="${module}_reduce_cookie_banner_variant_1_experiment_dialog_change_setting_button">Dismiss Pop-ups</string>
                <!-- Title text for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most -->
                <string name="${module}_reduce_cookie_banner_variant_2_experiment_dialog_title">Cookie Banner Reduction</string>
                <!-- Body text for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="${module}_reduce_cookie_banner_variant_2_experiment_dialog_body" >Allow %1\${'$'}s to decline a site’s cookie consent request if possible or accept cookie access when not possible?</string>
                <!-- Body text for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most. The first parameter is the application name. -->
                <string name="${module}_reduce_cookie_banner_variant_2_experiment_dialog_body_1">Allow %1\${'$'}s to decline a site’s cookie consent request if possible?</string>
                <!-- Change setting text button, for the dialog use on the variant 2 branch of the experiment to determine which context users engaged the most -->
                <string name="${module}_reduce_cookie_banner_variant_2_experiment_dialog_change_setting_button">Allow</string>

                <!-- Description of the preference to enable "HTTPS-Only" mode. -->
                <string name="${module}_preferences_https_only_summary">Automatically attempts to connect to sites using HTTPS encryption protocol for increased security.</string>
                <!-- Summary of tracking protection preference if tracking protection is set to on -->
                <string name="${module}_preferences_https_only_on" >On</string>
                <!-- Summary of https only preference if https only is set to off -->
                <string name="${module}_preferences_https_only_off">Off</string>
                <!-- Summary of https only preference if https only is set to on in all tabs -->
                <string name="${module}_preferences_https_only_on_all">On in all tabs</string>
                <!-- Summary of https only preference if https only is set to on in private tabs only -->
                <string name="${module}_preferences_https_only_on_private">On in private tabs</string>
                <!-- Text displayed that links to website containing documentation about "HTTPS-Only" mode -->
                <string name="${module}_preferences_http_only_learn_more">Learn more</string>
                <!-- Option for the https only setting -->
                <string name="${module}_preferences_https_only_in_all_tabs">Enable in all tabs</string>
                <!-- Option for the https only setting -->
                <string name="${module}_preferences_https_only_in_private_tabs">Enable only in private tabs</string>
                <!-- Title shown in the error page for when trying to access a http website while https only mode is enabled. -->
                <string name="${module}_errorpage_httpsonly_title">Secure site not available</string>
                <!-- Message shown in the error page for when trying to access a http website while https only mode is enabled. The message has two paragraphs. This is the first. -->
                <string name="${module}_errorpage_httpsonly_message_title">Most likely, the website simply does not support HTTPS.</string>
                <!-- Message shown in the error page for when trying to access a http website while https only mode is enabled. The message has two paragraphs. This is the second. -->
                <string name="${module}_errorpage_httpsonly_message_summary">However, it’s also possible that an attacker is involved. If you continue to the website, you should not enter any sensitive info. If you continue, HTTPS-Only mode will be turned off temporarily for the site.</string>
                <!-- Preference for accessibility -->
                <string name="${module}_preferences_accessibility">Accessibility</string>
                <!-- Preference to override the Firefox Account server -->
                <string name="${module}_preferences_override_fxa_server">Custom Firefox Account server</string>
                <!-- Preference to override the Sync token server -->
                <string name="${module}_preferences_override_sync_tokenserver">Custom Sync server</string>
                <!-- Toast shown after updating the FxA/Sync server override preferences -->
                <string name="${module}_toast_override_fxa_sync_server_done">Firefox Account/Sync server modified. Quitting the application to apply changes…</string>
                <!-- Preference category for account information -->
                <string name="${module}_preferences_category_account">Account</string>
                <!-- Preference for changing where the toolbar is positioned -->
                <string name="${module}_preferences_toolbar">Toolbar</string>
                <!-- Preference for changing default theme to dark or light mode -->
                <string name="${module}_preferences_theme">Theme</string>
                <!-- Preference for customizing the home screen -->
                <string name="${module}_preferences_home_2">Homepage</string>
                <!-- Preference for gestures based actions -->
                <string name="${module}_preferences_gestures">Gestures</string>
                <!-- Preference for settings related to visual options -->
                <string name="${module}_preferences_customize">Customize</string>
                <!-- Preference description for banner about signing in -->
                <string name="${module}_preferences_sign_in_description_2">Sign in to sync tabs, bookmarks, passwords, and more.</string>
                <!-- Preference shown instead of account display name while account profile information isn't available yet. -->
                <string name="${module}_preferences_account_default_name">Firefox Account</string>
                <!-- Preference text for account title when there was an error syncing FxA -->
                <string name="${module}_preferences_account_sync_error">Reconnect to resume syncing</string>


                <!-- Accessibility services actions labels. These will be appended to accessibility actions like "Double tap to.." but not by or applications but by services like Talkback. -->
                <!-- Action label for elements that can be collapsed if interacting with them. Talkback will append this to say "Double tap to collapse". -->
                <string name="${module}_a11y_action_label_collapse">collapse</string>
                <!-- Action label for elements that can be expanded if interacting with them. Talkback will append this to say "Double tap to expand". -->
                <string name="${module}_a11y_action_label_expand">expand</string>
                <!-- Action label for links to a website containing documentation about a wallpaper collection. Talkback will append this to say "Double tap to open link to learn more about this collection". -->
                <string name="${module}_a11y_action_label_wallpaper_collection_learn_more">open link to learn more about this collection</string>
                <!-- Action label for links that point to an article. Talkback will append this to say "Double tap to read the article". -->
                <string name="${module}_a11y_action_label_read_article">read the article</string>
        </resources>

    """.trimIndent()
}
