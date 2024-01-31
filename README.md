# Esel
There are two different methods how to access the readings from Eversense: 
* Companion mode, which reads the data from the Eversense notifications (works with the standard Eversense App)
* Patched mode, which requires a patched version of the Eversense App (works completely offline, including backfilling)

First of all, you need to install ESEL:
1. Get the ESEL apk e.g. from https://github.com/BernhardRo/Esel/tree/master/apk
2. Install the apk on your phone
  * You need to enable installation of Apps from unknown sources
3. Configuration:
  * Allow ESEL to run in the background (it will ask for it)
  * Allow ESEL access to the Android Notifications (it will ask for it)
  * Upload to Nightscout: Activate "Send to NightScout" in the preferences. It needs a configured AndroidAPS with internal NSClient or NSClient itself installed on the same phone
  * Inter-App-Broadcasts: Activate "Send to AAPS and xDrip". In xDrip and/or AndroidAPS activate the input method "640g/Eversense".
  * "Smooth Data" applies a smoothing algorithm to the raw values and provides these smoothed values instead of the raw readings. Smoothing is per default disabled.

## Companion Mode
1. Install/use the official Eversense App from the Google Play Store
   * Optional, but required for backfilling: Login to your Eversense account with your login data
   * In Sync, enable Auto synchronization 
3. Configuration of ESEL:
   * Disable the setting "Get data from patched Eversense App"
   * For backfilling: Enable "Fill missing data from eversensedms.com"
   * Provide as Email address and password your Eversense login data

## Patched Eversense App
1. Uninstall the Eversense App (Warning: your local historical data (older than 1 week) will be lost!)
2. Install the patched Eversense app (e.g. get it from https://cr4ck3d3v3r53n53.club) and use it as described by the vendor
   * You need to enable installation of Apps from unknown sources
   * Start the Eversense App, login, connect to your transmitter and use it just like the normal app.
3. Configuration of ESEL:
   * Enable the setting "Get data from patched Eversense App"

If you run esel with a fresh installation of Eversense for the first time, it can take up to 15min until your first values appear in xDrip!