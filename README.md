![Build Status](https://travis-ci.com/NalediMadlopha/flight.svg?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/c63aa43b5345479c855f39404b63927b)](https://www.codacy.com/app/NalediMadlopha/flight?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=NalediMadlopha/flight&amp;utm_campaign=Badge_Grade)
# Flight

Flight ia an app that displays the  airports based on a user's current location and when a location is clicked on it will display the departure flights for that airport.

The app make use of the Aviation Edge's api service for airport locations and flight details <https://aviation-edge.com/premium-api/>

To run the app from Android Studio:

1. Navigate to the location of the local `gradle.properties` file which is usually in the home directory in the `.gradle` folder. 
`cd ~` navigate you to your home directory if you are using a Unix based machine. Alternatively use `cd ~/.gradle/` to land directly in the gradle directory

2. Use this command to open the file using the vim application `vim gradle.properties`. You can use any preferred text editing application

3. Add the following line in the file `FLIGHT_AVIATION_EDGE_API_KEY="ENTER_YOUR_API_KEY_HERE"`.Replace the text `ENTER_YOUR_API_KEY_HERE` with the API key you would have acquired from <https://aviation-edge.com/premium-api/>

4. Save the file and build/run the app

That's it!
