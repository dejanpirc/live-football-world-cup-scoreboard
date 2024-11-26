# Live Football World Cup Scoreboard Library

This Java library is designed to provide a simple way to manage the scoreboard for ongoing World Cup matches.

## Features

- **Start a New Match**: Adds a new match to the scoreboard with an initial score of 0-0.
- **Update Match Score**: Allows you to update the current scores of ongoing matches.
- **Finish Match**: Ends a match and removes it from the scoreboard.
- **Match Summary**: Retrieves a summary of all ongoing matches, ordered by total score and, if necessary, by the most recently started match.

## Usage
To use the Scoreboard.java class, follow these steps:

1. Create an instance of the `Scoreboard` class.
2. Start a new match by calling the `startMatch` method and passing in `Match` containing home and away `Team` instances.
3. Update the match score by calling the `updateScore` method and passing in the new scores for each team.
4. Get a summary of all live matches by calling the `getSummary` method.
5. End a match by calling the `endMatch` method.

## Example Code
```java
Scoreboard scoreboard = new Scoreboard();

// Start a new match
Team homeTeam = new Team("Germany");
Team awayTeam = new Team("Mexico");
Match match = new Match(homeTeam, awayTeam);
scoreboard.startMatch(match);

// Update the match score
scoreboard.updateScore(match, 1, 0);

// Get a summary of all live matches
String summary = scoreboard.getSummary();

// End the match
scoreboard.endMatch(match);
```

## Implementation Details
For the sake of simplicity of this library the following implementations were done:
* In `Team` class the team name is considered as an ID.
* A `Match` instance contains an `id` field, which is automatically generated with a UUID value when the Match instance is created. This `id` field is used to identify the match in `startMatch` and `updateScore` operations.