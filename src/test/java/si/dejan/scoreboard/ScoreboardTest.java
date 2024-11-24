package si.dejan.scoreboard;

import static org.junit.Assert.*;

import org.junit.Test;

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

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(null));
        assertEquals("Match must be set!", exception.getMessage());
    }

    @Test
    public void startMatch_teamIsAlreadyPlayingInAnotherMatch_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(match));
        assertEquals("Invalid match: team Mexico is already playing in another match!", exception.getMessage());
    }

    @Test
    public void startMatch_homeTeamWithTheSameNameIsAlreadyPlayingInAnotherMatch_throwsException() {
        // match 1
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        // match 2
        Team homeTeam2 = new Team("Mexico");
        Team awayTeam2 = new Team("Slovenia");
        Match match2 = new Match(homeTeam2, awayTeam2);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(match2));
        assertEquals("Invalid match: team Mexico is already playing in another match!", exception.getMessage());
    }

    @Test
    public void startMatch_awayTeamWithTheSameNameIsAlreadyPlayingInAnotherMatch_throwsException() {
        // match 1
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        // match 2
        Team homeTeam2 = new Team("Germany");
        Team awayTeam2 = new Team("Canada");
        Match match2 = new Match(homeTeam2, awayTeam2);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(match2));
        assertEquals("Invalid match: team Canada is already playing in another match!", exception.getMessage());
    }

    @Test
    public void startMatch_sameHomeTeamNameWithDifferentTextCase_throwsException() {
        // match 1
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        // match 2
        Team homeTeam2 = new Team("mexico");
        Team awayTeam2 = new Team("Slovenia");
        Match match2 = new Match(homeTeam2, awayTeam2);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(match2));
        assertEquals("Invalid match: team mexico is already playing in another match!", exception.getMessage());
    }

    @Test
    public void startMatch_sameAwayTeamNameWithDifferentTextCase_throwsException() {
        // match 1
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        // match 2
        Team homeTeam2 = new Team("Croatia");
        Team awayTeam2 = new Team("canada");
        Match match2 = new Match(homeTeam2, awayTeam2);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(match2));
        assertEquals("Invalid match: team canada is already playing in another match!", exception.getMessage());
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

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, 1, 2));
        assertEquals("Only live matches can be updated!", exception.getMessage());
    }

    @Test
    public void updateScore_oneOfTheTeamsScoreIsNegative_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, -1, 2));
        assertEquals("Score must be positive!", exception.getMessage());
    }

    @Test
    public void updateScore_homeAndAwayScoresAreNegative_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, -1, -2));
        assertEquals("Score must be positive!", exception.getMessage());
    }

    @Test
    public void updateScore_matchHasAlreadyEnded_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);
        scoreboard.endMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, 1, 2));
        assertEquals("Only live matches can be updated!", exception.getMessage());
    }

    @Test
    public void updateScore_matchIsNotSet_throwsException() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(null, 1, 2));
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

        assertEquals(false, scoreboard.isMatchLive(match));
    }

    @Test
    public void endMatch_matchIsNotSet_throwsException() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(Exception.class, () -> scoreboard.endMatch(null));

        assertEquals("Match must be set!", exception.getMessage());
    }

    @Test
    public void endMatch_matchIsNotLive_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(Exception.class, () -> scoreboard.endMatch(match));

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

        Exception exception = assertThrows(Exception.class, () -> scoreboard.endMatch(match));

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

    private void startMatchAndUpdateScore(Scoreboard scoreboard, String homeTeamName, String awayTeamName, int homeScore, int awayScore) {
        Match match = new Match(new Team(homeTeamName), new Team(awayTeamName));
        scoreboard.startMatch(match);
        scoreboard.updateScore(match, homeScore, awayScore);
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
}
