# Eversense 

1. Uninstall the Eversense App (Warning: your local historical data (older than 1 week) will be lost!)
1. Install the patched Eversense app (mod_com.senseonics.gen12androidapp-release.apk) and use it as described by the vendor
  * You need to enable installation of Apps from unknown sources
2. Install esel.apk on your phone.
3. Configuration:
  * Allow ESEL to run in the background (it will ask for it)
  * Upload to Nightscout: Activate "Send to NightScout" in the preferences. It needs a configured AndroidAPS with internal NSClient or NSClient itself installed on the same phone
  * Inter-App-Broadcasts: Activate "Send to AAPS and xDrip". In xDrip activate the input method "640g/Eversense".
4. Install xDrip: https://jamorham.github.io/#xdrip-plus (Download latest APK)
  * Use as Datasource 640G / EverSense


