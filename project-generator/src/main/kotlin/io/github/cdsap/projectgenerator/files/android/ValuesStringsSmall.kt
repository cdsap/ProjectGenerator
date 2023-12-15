package io.github.cdsap.projectgenerator.files.android

class ValuesStringsSmall {

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

        </resources>

    """.trimIndent()
}
