# Esel

1. Chose a phone where sqlite3 is available. Easiest solution: Use LineageOS
2. Root your phone (su needs to work - so allow it for this app in the developer options or whatever tool you use to manage access to su)
3. Install the Eversense app and use it as described by the vendor
4. Build https://github.com/AdrianLxM/Esel and install it on your phone.
5. Configuration:
  * Allow ESEL to run in the background (it will ask for it)
  * Don't touch the "DB Path" setting unless you changed the default data path when installing the Eversense app and know what you are doing!
  * Upload to Nightscout: Activate "Send to NightScout" in the preferences. It needs a configured AndroidAPS with internal NSClient or NSClient itself installed on the same phone
  * Inter-App-Broadcasts: Activate "Send to AAPS and xDrip". In xDrip and/or AndroidAPS activate the input method "640g/Eversense".
  * As this app is very experimental, please contact @AdrianLxM for positive/negative feedback and to register for future updates.

