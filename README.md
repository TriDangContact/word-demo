# Overview

A simple Spring Boot application that provides RESTful endpoints to find subwords from a given word.

## Assumptions

- We can either use a locally defined dictionary or an external dictionary to define and verify that the input is a valid word in the English dictionary.
  - For this demo, we are using the [Merriam Dictionary API](https://www.dictionaryapi.com/products/api-collegiate-dictionary).
  - If the Merriam Dictionary API returns a short definition for the word, we will assume that this word is valid.
  - If the Merriam Dictionary API returns nothing, we will assume that this word is not a valid word.
  - If the Merriam Dictionary API returns an array of strings, we will assume that this word is not a valid word and return the array to the caller as suggestions for valid words.
- Valid inputs must contain only English alphabet characters [A-Za-z].
- Valid input can only be a sequence of alphabet characters, even if they do not make up a valid word in an English dictionary (e.g. "abc" is a valid input, but B.C. is not since it contains period character).
- Valid inputs are those that contain a single word and are not delimited by a deliminator (e.g. "lady bug" is not a single word).
- Valid inputs are case-insensitive, and all words will be stored as lowercase.
- Valid inputs must contain less than 10 characters.
- Valid inputs must not be null or be an empty string.

## Compile

- Run command in terminal:
  - `mvn clean install`
- This will clean the project, install any dependencies, and create a `/target` directory with a `.jar` file of the program.

## Start Server

- Navigate to the /target directory and run command in terminal:
  - `java -jar <name of .jar file>`
- Or navigate to the root project directory and run command in terminal:
  - `./mvnw spring-boot:run`

## Endpoints

Each endpoint expects an API Key in the header called `key`. For the purpose of this demo, the value of the key will a simple value in `application.properties` under `auth.apiKeyHeaderValue`, rather than dynamically generated.

- GET: demo/
  - Returns a simple welcome message.
- POST: demo/add-word/{word}
  - Returns a message indicating whether the word was added to storage or not.
  - If the input was valid, but is not a word according to Merriam Dictionary, returns a list of suggested words based on response from Merriam Dictionary API.
- DELETE: demo/delete-word/{word}
  - Returns a message indicating whether the word was deleted or not.
- GET: demo/list-all-words/
  - Returns a list of all the words currently in storage.
- GET: demo/find-sub-words/{word}
  - Returns a list of all the words currently in storage that are sub words of the input.

## Points of Improvements

- Attach request ID to each request for tracking and easier debugging
- More extensive logging to allow easier debugging
- Insert each API request/response into database for auditing purposes
- Words can be stored in an external database rather than in-memory
- A different endpoint can be created to add a list of words all at once, rather than add individual words

## Demo

### Adding valid word "bug" to storage
![Screenshot1](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot1")
### Adding valid word "lady" to storage
![Screenshot2](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot2")
### Adding invalid word "awdawd" to storage
![Screenshot3](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot3")
### Listing all words currently in storage
![Screenshot4](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot4")
### Finding subwords of "ladybug"
![Screenshot5](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot5")
### Finding subwords of "buggy"
![Screenshot6](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot6")
### Deleting word "lady" from storage
![Screenshot7](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot7")
### Listing all words currently in storage
![Screenshot8](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot8")
### Deleting word currently not in storage
![Screenshot9](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot9")
### Adding word already in storage
![Screenshot10](/src/main/resources/screenshots/Screenshot1.png?raw=true "Screenshot10")
