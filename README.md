# Trading Bot Application
### (A Spring Boot Application)

## Intro
A very basic Trading Bot that tracks the price of a certain product and will execute a pre-defined trade in the said product when it reaches a given price. After the product has been bought the Trading Bot should keep on tracking the prices and execute a sell order when a certain price is hit. In the input, there should be an "upper limit" price and a "lower limit" price.

## Importing the project
### (Only applicable if you are uploading via an archive file)

* Extract contents of BUXTradingBot.zip file and import it it as a gradle project in your IDE.
* After importing please refresh your gradle project by right clicking on the BUXTradingBot project and selecting `Gradle -> Refresh Gradle Project`
	
## Running the application
	
- Main class: `BuxTradingBotApplication.java`
- Bot Configurations: `bot.properties`
- Port/Log Configurations: `application.properties`

* BUX Trading Bot runs a Spring Boot Application
	* `BotController.java` is the ONLY Controller class that handles the incoming requests
	
	
* Option 1:	(via IDE & Postman)  
	* You can start the application by running the application as a standard Java application
	* Tomcat will start the application on port 8081 (configured in `application.properties` file)
	* Using POSTMAN to send HTTP POST API call:
		- (POST) http://localhost:8081/api/getbux/trade
		- Request: {"productId":"sb26493","buyPrice":15834,"lowerLimit":15833.5,"upperLimit":15834.5}
		- buyPrice (optional) - Non String value
		- lowerLimit (optional) - Non String value
		- upperLimit (optional) - Non String value
	
	
* Option 2:	(via Command line & cURL)  
	* Got the root of the project and run `./gradlew bootRun -b build.gradle`
	* Tomcat will start the application on port 8081 (configured in `application.properties` file)
	* Using cURL:
		- `curl -XPOST -H "Content-Type: application/json" http://localhost:8081/api/getbux/trade -d '{"productId":"sb26493","buyPrice":null,"lowerLimit":null,"upperLimit":null}'`
		- Response: {"result":"Auto trading request submitted successfully.","status":"Success","ok":true
		- The above response will be visible if initial validation is successful and the trading request is successfully submitted.

## Limitations of the application

* The application does not run multiple trading requests in parallel.If you have to submit another trading request then please let the current request complete or cancel the current request.
* The application will try to connect to websocket URL only once. No support for a re-try.
* The application will run forever until the buy price is reached. No support for timeout at this moment
* The application will run forever until either of the lower or upper limit is reached. No support for timeout at this moment.
* The application will quit and stop processing the job as soon as it encounters an exception while submitting a buy order or sell order request. No support for a re-try.
* The application defaults to a fixed margin for lower and upper selling price. 

##### All the above mentioned limitations can be added as an enhancement and will make the application more robust.

## 3rd Party Jars

- javax.websocket client - v1.1
- tyrus-client - v2.0.0
- httpclient - v4.5.13
- jackson - v2.12.3
- slf4j - v1.7.30
- logback - v1.2.3
- apache commons-collections4 - v4.4
- apache commons-lang3 - v3.12.0
- junit - v4.12
