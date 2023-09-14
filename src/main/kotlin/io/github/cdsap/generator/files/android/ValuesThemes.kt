package io.github.cdsap.generator.files.android

class ValuesThemes {
    fun get() = """
        <resources xmlns:tools="http://schemas.android.com/tools">
            <!-- Base application theme. -->
            <style name="Theme.MyApplication" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
                <!-- Primary brand color. -->
                <item name="colorPrimary">@color/purple_500</item>
                <item name="colorPrimaryVariant">@color/purple_700</item>
                <item name="colorOnPrimary">@color/white</item>
                <!-- Secondary brand color. -->
                <item name="colorSecondary">@color/teal_200</item>
                <item name="colorSecondaryVariant">@color/teal_700</item>
                <item name="colorOnSecondary">@color/black</item>
                <!-- Status bar color. -->
                <item name="android:statusBarColor">?attr/colorPrimaryVariant</item>
                <!-- Customize your theme here. -->
            </style>
        </resources>
    """.trimIndent()
}
