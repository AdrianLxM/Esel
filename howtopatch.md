# How to modify the Android Eversense App

1. Install the official [EversenseApp](https://play.google.com/store/apps/details?id=com.senseonics.gen12androidapp) from the Playstore
2. Extract the APK of this App (e.g. with [MyAppSharer](https://play.google.com/store/apps/details?id=com.yschi.MyAppSharer))
3. Upload this APK (com.senseonics.gen12androidapp) to your PC
4. Decompile the APK to .smali code (e.g. with [Apktool](https://ibotpeaches.github.io/Apktool/install))
5. In the decompiled app, navigate to com.senseonics.gen12androidapp\smali\com\senseonics\db
6. Copy/duplicate ConnectedTransmitterContentProvider.smali and name the new file GlucoseContentProvider.smali (see the file [GlucoseContentProvider.smali](https://github.com/BernhardRo/Esel/blob/Eversense_mod/mod/GlucoseContentProvider.smali) in this repo for references)
7. Edit GlucoseContentProvider.smali and rename every instance of `ConnectedTransmitterContentProvider` to `GlucoseContentProvider`and `connectedTransmitters` to `glucosereadings` (in the SQL statements) - except of line 29: for security reasons, rename the string in `performDelete` to something like `deletenotsupported`
8. Modify com.senseonics.gen12androidapp\AndroidManifest.xml: Replace line 66 (<provider...) with:
`<provider android:authorities="com.senseonics.gen12androidapp.transmitter" android:grantUriPermissions="true" android:exported="true" android:name="com.senseonics.db.ConnectedTransmitterContentProvider"/>
        <provider android:authorities="com.senseonics.gen12androidapp.glucose" android:grantUriPermissions="true" android:exported="true" android:name="com.senseonics.db.GlucoseContentProvider"/>` (see the file [AndroidManifest.xml](https://github.com/BernhardRo/Esel/blob/Eversense_mod/mod/AndroidManifest.xml) in this repo for references)
9. Compile the code according the documentation of your tool you have used to decompile it
10. Sign the compiled APK (See the Android [Documentation](https://developer.android.com/studio/publish/app-signing#signing-manually) for help)
11. Uninstall the original Eversense App on your phone (Warning: the local history of your CGM readings in your Eversense App will get lost) and install your new APK. The new App will behave just like the original one - except of the difference that the CGM reading can be accessed from other Apps e.g. by ESEL
