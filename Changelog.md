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
