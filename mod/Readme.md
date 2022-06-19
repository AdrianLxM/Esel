# How to patch ES
## Prerequisits
You need to have the following tools available. On your computer:
* Android debugger [Adb](https://developer.android.com/studio/command-line/adb)
* A Tool to decompile Apk apps to smali code, e.g. [Apktool](https://ibotpeaches.github.io/Apktool/install)

On your Android smartphone:
* Enable Developer options on your Android phone, e.g. by following this [User Guide](https://developer.android.com/studio/debug/dev-options)
* Enable USB-Debugging in the Developer options on your Android phone.


## Get the sources
1. Install the official EversenseApp from the Playstore
2. find the app with
 
        adb shell pm list packages

    should be com.senseonics.gen12androidapp
3. get the full path name:

        adb shell pm path com.senseonics.gen12androidapp

    should give you
    > package:/data/app/com.senseonics.gen12androidapp-nU4XaDnFpSSira9EH02H9Q==/base.apk
    > package:/data/app/com.senseonics.gen12androidapp-nU4XaDnFpSSira9EH02H9Q==/split_config.de.apk
    > package:/data/app/com.senseonics.gen12androidapp-nU4XaDnFpSSira9EH02H9Q==/split_config.xxhdpi.apk

    Of course the language file (the second one) can be different according to your localization.
4. pull to computer:

        adb pull /data/app/com.senseonics.gen12androidapp-nU4XaDnFpSSira9EH02H9Q==/base.apk .\org\
        adb pull /data/app/com.senseonics.gen12androidapp-nU4XaDnFpSSira9EH02H9Q==/split_config.de.apk .\org\
        adb pull /data/app/com.senseonics.gen12androidapp-nU4XaDnFpSSira9EH02H9Q==/split_config.xxhdpi.apk .\org\

## Create the patched files
1. Decompile the APK to .smali code (e.g. with Apktool)
   
        apktool.bat d .\org\base.apk
2. In the decompiled app, navigate to
    > base\smali_classes2\com\senseonics\db
3. Copy/duplicate 
    > ConnectedTransmitterContentProvider.smali
    
    and name the new file to 
    > GlucoseContentProvider.smali
    
    (see the file GlucoseContentProvider.smali in this repo for references)
4. Edit 
    > GlucoseContentProvider.smali
    
    and rename every instance of

        ConnectedTransmitterContentProvider

    to 

        GlucoseContentProvider 

    and also rename

        connectedTransmitters 

    to 

        glucosereadings

    (in the SQL statements) - except of line 25: for security reasons, rename the string in

        performDelete
        
    to something like 
    
        deletenotsupported

5.  Modify 
    > com.senseonics.gen12androidapp\AndroidManifest.xml
    
    Replace line 74
    
        <provider android:authorities="com.senseonics.gen12androidapp.transmitter"...)

    with:

        <provider android:authorities="com.senseonics.gen12androidapp.transmitter" android:grantUriPermissions="true" android:exported="true" android:name="com.senseonics.db.ConnectedTransmitterContentProvider"/> <provider android:authorities="com.senseonics.gen12androidapp.glucose" android:grantUriPermissions="true" android:exported="true" android:name="com.senseonics.db.GlucoseContentProvider"/>
    
    and delete the declaration

        android:localeConfig="@xml/locales_config"
    
    (see the file AndroidManifest.xml in this repo for references). Modify in 

    > res\values\strings.xml:630 and 631: © 2016 Senseonics, Inc.
    
    and add
    > patched

    delete file 
    
    > \res\xml\locales_config.xml

    open file 
    > \res\values\public.xml
    
    and delete line 

        <public type="xml" name="locales_config" id="0x7f110003" />

    (appears 2 times). Instead, insert line

        <public type="attr" name="textLocale" id="0x7f0402a5" />

6.  Compile the code according the documentation of your tool you have used to decompile it
   
        apktool.bat b .\base\

## Install the patched files
1.  Sign the compiled APK (See the Android Documentation for help). First aligne it:
   
        zipalign.exe -v -p -f 4 .\base\dist\base.apk .\base\dist\base-aligned.apk

    next, sign it. Here an example with an keyfile called my-release-key_123456.jks and pwd 123456:

        apksigner.bat sign --ks my-release-key_123456.jks --out .\base-patched.apk .\base\dist\base-aligned.apk
    > 123456
2.  Do the same (without any modification) with the other apk files, language file:

        apksigner.bat sign --ks my-release-key_123456.jks --out .\split_config.de.apk .\org\split_config.de.apk

    and common file:

        apksigner.bat sign --ks my-release-key_123456.jks --out .\split_config.xxhdpi.apk .\org\split_config.xxhdpi.apk
3.  Uninstall the original Eversense App on your phone (Warning: the local history of your CGM readings in your Eversense App will get lost) and install your new APK. The new App will behave just like the original one - except of the difference that the CGM reading can be accessed from other Apps e.g. by ESEL

        adb install-multiple base.apk split_config.de.apk split_config.xxhdpi.apk



There is also a script available following these steps and running on Bitbucket in Docker:
https://bitbucket.org/norbert_bitbucket1/hackingeversense/src/master/
