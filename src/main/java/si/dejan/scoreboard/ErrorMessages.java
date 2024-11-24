package si.dejan.scoreboard;

public enum ErrorMessages {
    MATCH_NOT_SET("Match must be set!"),
    TEAM_ALREADY_PLAYING("Invalid match: team %s is already playing in another match!"),
    NEGATIVE_SCORE("Score must be positive!"),
    UPDATE_NOT_LIVE_MATCH("Only live matches can be updated!"),
    END_NOT_LIVE_MATCH("Only live matches can be ended!"),
    NO_LIVE_MATCHES("No live matches."),
    MISSING_HOME_AWAY_TEAMS("Home and away teams must be set!"),
    MISSING_TEAM_NAME("Name must be set!"),
    INVALID_TEAM_NAME("Invalid team name!");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
