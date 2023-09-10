# Service Details
Below are the techincal, and non-technical details used to finish the challenge.

## Technical Details
### Stack Used:
* Java 17
* Spring Boot
* Maven
* Junit + Mockito
* InfluxDb
* Value Immutables

### Database Setup:
Follow below instructions to install InfluxDb:
https://docs.influxdata.com/influxdb/v2.0/install/
Note: When creating a bucket, please make sure bucket name, and org has the same value.

### Application Setup:
make sure to enable annotation processing.
Generated files from *Value Immutables* will be in `target` directory.

### Configurations:
**Database**: *application-influx-db-config.yml*
````
influx-db-config:
  host: "localhost"
  port: 8086
  bucket-name: "candlesticks"
  bucket-token: "kx4pKmCkDCHV2oA31wn3P1Yjo5LwflXary6QXuW2jO-Y1IaqU_fc7fPn78cfbFMsuSI6qJXVmfTNR7m5ZWxvZw=="
  ...
````
**Websocket**: *application-ws-client.yml*
````
partner-service:
  port: 8032
  instruments-endpoint: "/instruments"
  quotes-endpoint: "/quotes"
  connection-timeout-millis: 50000
````
**Application**: *application.yml*
````
candlesticks:
  intervalInMins: 1
  priceHistoryInMins: 30
````
*Note: modify above values to control the candlestick interval, and the time range to retrieve the candlesticks.*

----

### Code Structure
Code structure was implemented to achieve isolation of domain layer, the main packages are:
* Application: includes Rest, and Websocket classes.
* Domain: includes business logic of Insturments, Quotes, And Candlesticks.
* Infrastructure: database-related classes (writing, reading).

###  API
by using default settings, api will have the following URL:
`http://localhost:9000/api/v1/candlesticks/{isin}`

## Non-Technical Details
### Assumptions:
* Only price data will be processed in Quotes, the field (type="QUOTE") will not be used in the application when processing quotes websocket.
* Description field of insturment will not be processed when consuming Instruments websocket.
* If an instrument does not exist, or has been deleted, `404` status will be returned.
* If an instrument has no data in the past window (example: 30 mins). status `200` will be returned along with an empty list.
* Given an instrument history in the past 30 mins, the below scenario occurred:
  * at minute 15, instrument was added and price data was pushed database.
  * at minute 18, no data was recieved at all.
  * at minute 20, price data were recieved.
  * after minute 20, no data was recieved.
  * The output of the scenario (default settings): thirty 1m candles will be returned, candle 18 will be filled with data from candle 17 (filling missing data with last filled candle), and candles 21 and above will have the same data as candle 20. Candles before minute 15 will be zero (assuming that we don't need to return data before the instrument was added)

## Inputs & Outputs
### Sample Websocket Message:
```
{
    // The type of the event. ADD if an instrument is ADDED
    // DELETE if an instrument is deleted
    "type": "DELETE"
    {
        //The Payload
        "data": {
            //The Description of the instrument
            "description": "elementum eos accumsan orci constituto antiopam",
            //The ISIN of this instrument
            "isin": "LS342I184454"
        }
    }
}
```
### Sample Output:
````
sample output:
[
    {
        "openTimestamp": "2023-05-28T19:30:00Z",
        "openPrice": 0.0,
        "closePrice": 0.0,
        "highPrice": 0.0,
        "lowPrice": 0.0,
        "closeTimestamp": "2023-05-28T19:31:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:31:00Z",
        "openPrice": 0.0,
        "closePrice": 0.0,
        "highPrice": 0.0,
        "lowPrice": 0.0,
        "closeTimestamp": "2023-05-28T19:32:00Z"
    },
    .
    .
    .
    . Bunch of zero candles
    .
    .
    
    {
        "openTimestamp": "2023-05-28T19:43:00Z",
        "openPrice": 1101.0,
        "closePrice": 1101.0,
        "highPrice": 1101.0,
        "lowPrice": 1101.0,
        "closeTimestamp": "2023-05-28T19:44:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:44:00Z",
        "openPrice": 1184.0,
        "closePrice": 1630.0,
        "highPrice": 1776.0,
        "lowPrice": 1184.0,
        "closeTimestamp": "2023-05-28T19:45:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:45:00Z",
        "openPrice": 1543.0,
        "closePrice": 313.0,
        "highPrice": 1543.0,
        "lowPrice": 313.0,
        "closeTimestamp": "2023-05-28T19:46:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:46:00Z",
        "openPrice": 355.0,
        "closePrice": 702.0,
        "highPrice": 702.0,
        "lowPrice": 355.0,
        "closeTimestamp": "2023-05-28T19:47:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:47:00Z",
        "openPrice": 747.0,
        "closePrice": 1109.0,
        "highPrice": 1109.0,
        "lowPrice": 747.0,
        "closeTimestamp": "2023-05-28T19:48:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:48:00Z",
        "openPrice": 1085.0,
        "closePrice": 995.0,
        "highPrice": 1101.0,
        "lowPrice": 995.0,
        "closeTimestamp": "2023-05-28T19:49:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:49:00Z",
        "openPrice": 957.0,
        "closePrice": 567.0,
        "highPrice": 1547.0,
        "lowPrice": 563.0,
        "closeTimestamp": "2023-05-28T19:50:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:50:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:51:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:51:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:51:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:52:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:52:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:53:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:53:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:54:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:54:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:55:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:55:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:56:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:56:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:57:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:57:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:58:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:58:00Z"
    },
    {
        "openTimestamp": "2023-05-28T19:59:00Z",
        "openPrice": 559.0,
        "closePrice": 562.0,
        "highPrice": 577.0,
        "lowPrice": 559.0,
        "closeTimestamp": "2023-05-28T19:59:00Z"
    }
]
````
*Note: in the output above, the service was stopped manually, and consumption of websockets were commented out, then the service was run again to introduce zero values for the instruments after it was added (to test filling missing data).*

## Testing
Unit tests (using Junit, and Mockito) has been written for the following:
* Candlesticks Service
* Fill Missing Candle Stick Data Strategy 
