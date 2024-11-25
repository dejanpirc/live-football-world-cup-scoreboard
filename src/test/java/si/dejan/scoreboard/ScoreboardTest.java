package si.dejan.scoreboard;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreboardTest {

    @Test
    public void startMatch_startNewMatch_scoreIsZero() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch(match);

        assertEquals(0, match.getHomeTeam().getScore());
        assertEquals(0, match.getAwayTeam().getScore());
    }

    @Test
    public void startMatch_matchIsNotSet_throwsException() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(NullPointerException.class, () -> scoreboard.startMatch(null));
        assertEquals("Match must be set!", exception.getMessage());
    }

    @Test
    public void startMatch_teamIsAlreadyPlayingInAnotherMatch_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(match));
        assertEquals("Invalid match: team Mexico is already playing in another match!", exception.getMessage());
    }

    @Test
    public void startMatch_homeTeamWithTheSameNameIsAlreadyPlayingInAnotherMatch_throwsException() {
        assertTeamAlreadyPlayingInAnotherMatch("Mexico", "Canada", "Mexico", "Slovenia", "Invalid match: team Mexico is already playing in another match!");
    }

    @Test
    public void startMatch_awayTeamWithTheSameNameIsAlreadyPlayingInAnotherMatch_throwsException() {
        assertTeamAlreadyPlayingInAnotherMatch("Mexico", "Canada", "Germany", "Canada", "Invalid match: team Canada is already playing in another match!");
    }

    @Test
    public void startMatch_sameHomeTeamNameWithDifferentTextCase_throwsException() {
        assertTeamAlreadyPlayingInAnotherMatch("Mexico", "Canada", "mexico", "Slovenia", "Invalid match: team mexico is already playing in another match!");
    }

    @Test
    public void startMatch_sameAwayTeamNameWithDifferentTextCase_throwsException() {
        assertTeamAlreadyPlayingInAnotherMatch("Mexico", "Canada", "Croatia", "canada", "Invalid match: team canada is already playing in another match!");
    }

    @Test
    public void updateScore_liveMatch_matchScoreIsUpdated() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        scoreboard.updateScore(match, 1, 2);

        assertEquals(1, match.getHomeTeam().getScore());
        assertEquals(2, match.getAwayTeam().getScore());
    }

    @Test
    public void updateScore_matchIsNotLive_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(match, 1, 2));
        assertEquals("Only live matches can be updated!", exception.getMessage());
    }

    @Test
    public void updateScore_homeTeamScoreIsNegative_throwsException() {
        assertThrowsInvalidScoreException(-1, 2);
    }

    @Test
    public void updateScore_awayTeamScoreIsNegative_throwsException() {
        assertThrowsInvalidScoreException(1, -2);
    }

    @Test
    public void updateScore_homeAndAwayScoresAreNegative_throwsException() {
        assertThrowsInvalidScoreException(-1, -2);
    }

    @Test
    public void updateScore_matchHasAlreadyEnded_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        scoreboard.endMatch(match);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(match, 1, 2));
        assertEquals("Only live matches can be updated!", exception.getMessage());
    }

    @Test
    public void updateScore_matchIsNotSet_throwsException() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(NullPointerException.class, () -> scoreboard.updateScore(null, 1, 2));
        assertEquals("Match must be set!", exception.getMessage());
    }

    @Test
    public void endMatch_matchIsLive_removesMatchFromLiveMatches() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        scoreboard.endMatch(match);

        assertFalse(scoreboard.isMatchLive(match));
    }

    @Test
    public void endMatch_matchIsNotSet_throwsException() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(NullPointerException.class, () -> scoreboard.endMatch(null));

        assertEquals("Match must be set!", exception.getMessage());
    }

    @Test
    public void endMatch_matchIsNotLive_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.endMatch(match));

        assertEquals("Only live matches can be ended!", exception.getMessage());
    }

    @Test
    public void endMatch_matchHasAlreadyEnded_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        scoreboard.endMatch(match);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.endMatch(match));

        assertEquals("Only live matches can be ended!", exception.getMessage());
    }

    @Test
    public void getSummary_oneLiveMatch_returnsSummaryOfLiveMathes() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        scoreboard.updateScore(match, 1, 2);

        String summary = scoreboard.getSummary();

        assertEquals("1. Mexico 1 - Canada 2", summary);
    }

    @Test
    public void getSummary_oneLiveMatchWithNoGoals_returnsSummaryOfLiveMathes() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        String summary = scoreboard.getSummary();

        assertEquals("1. Mexico 0 - Canada 0", summary);
    }

    @Test
    public void getSummary_noLiveMatches_returnsMessageForNoLiveMatches() {
        Scoreboard scoreboard = new Scoreboard();

        String summary = scoreboard.getSummary();

        assertEquals("No live matches.", summary);
    }

    @Test
    public void getSummary_multipleLiveMatches_returnsSummaryOfLiveMathesOrderedByMatchTotalScoreAndRecentlyStarted() {
        Scoreboard scoreboard = new Scoreboard();
        startMatchAndUpdateScore(scoreboard, "Mexico", "Canada", 0, 5);
        startMatchAndUpdateScore(scoreboard, "Spain", "Brazil", 10, 2);
        startMatchAndUpdateScore(scoreboard, "Germany", "France", 2, 2);
        startMatchAndUpdateScore(scoreboard, "Uruguay", "Italy", 6, 6);
        startMatchAndUpdateScore(scoreboard, "Argentina", "Australia", 3, 1);

        String summary = scoreboard.getSummary();

        assertEquals("1. Uruguay 6 - Italy 6\n" +
                "2. Spain 10 - Brazil 2\n" +
                "3. Mexico 0 - Canada 5\n" +
                "4. Argentina 3 - Australia 1\n" +
                "5. Germany 2 - France 2", summary);
    }

    private void startMatchAndUpdateScore(Scoreboard scoreboard, String homeTeamName, String awayTeamName, int homeScore, int awayScore) {
        Match match = new Match(new Team(homeTeamName), new Team(awayTeamName));
        scoreboard.startMatch(match);
        scoreboard.updateScore(match, homeScore, awayScore);
    }

    private void assertTeamAlreadyPlayingInAnotherMatch(String match1HomeTeam, String match1AwayTeam, String match2HomeTeam, String match2AwayTeam, String errorMessage) {
        // match 1
        Team homeTeam = new Team(match1HomeTeam);
        Team awayTeam = new Team(match1AwayTeam);
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        // match 2
        Team homeTeam2 = new Team(match2HomeTeam);
        Team awayTeam2 = new Team(match2AwayTeam);
        Match match2 = new Match(homeTeam2, awayTeam2);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(match2));
        assertEquals(errorMessage, exception.getMessage());
    }

    private void assertThrowsInvalidScoreException(int homeScore, int awayScore) {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, homeScore, awayScore));
        assertEquals("Score must be positive!", exception.getMessage());
    }
}
