# Eversense 

1. Uninstall the Eversense App (Warning: your local historical data (older than 1 week) will be lost!)
2. Install the patched Eversense app (mod_com.senseonics.gen12androidapp-release.apk) and use it as described by the vendor
   * You need to enable installation of Apps from unknown sources
   * Start the Eversense App, login, connect to your transmitter and use it just like the normal app.
3. Install esel.apk on your phone.
4. Configuration:
   * Allow ESEL to run in the background (it will ask for it)
   * Upload to Nightscout: Activate "Send to NightScout" in the preferences. It needs a configured AndroidAPS with internal NSClient or NSClient itself installed on the same phone
   * Inter-App-Broadcasts: Activate "Send to AAPS and xDrip". In xDrip and/or AndroidAPS activate the input method "640g/Eversense".
   * "Smooth Data" applies a smoothing algorithm to the raw values and provides these smoothed values instead of the raw readings. Smoothing is per default disabled.
5. Install xDrip: https://jamorham.github.io/#xdrip-plus (Download latest APK)
   * Use as Datasource 640G / EverSense

If you run esel with a fresh installation of Eversense for the first time, it can take up to 15min until your first values appear in xDrip!


