# Version 2.3.0
## New Features
* RAW Data Export: For Debugging purposes RAW Data can be exported to a file in JSON format. The file is stored in the Download folder and contains the Data of the last hours as defined in "Max sync hours"

## Bugfixes
* Smoothing: Internally switched to float values for more precise smoothing
* Smoothing: better handling of first values after gaps in measurements (e.g. because the transmitter had no connection to the sensor or has been charged)

# Version 2.3.1
* Added Code comments
* Fixed spelling in descriptions

# Version 2.3.2
## New Features
* Esel does not send future vaules
* Values can be shifted (by total days) to the future (usefull if phone time is shifted to a date in the past)

# Version 3.0.1
## New Features
* New icon: we are a pirate donkey now!
* Companion Mode: ESEL can read CGM values from the notifications provided by the Eversense app
* Access to [eversensedms.com](https://www.eversensedms.com/) to fill missing data
* Fixes for smoothing and calculation of direction
