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

        assert (match.getHomeTeam().getScore() == 0);
        assert (match.getAwayTeam().getScore() == 0);
    }

    @Test
    public void startMatch_matchIsNotSet_throwsException() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(null));
        assertEquals("Match must be set!", exception.getMessage());
    }

    @Test
    public void startMatch_teamIsAlreadyPlayingInOtherMatch_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.startMatch(match));
        assertEquals("Invalid match: team Mexico is already playing in other match!", exception.getMessage());
    }

    @Test
    public void updateScore_liveMatch_matchScoreIsUpdated() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        scoreboard.updateScore(match, 1, 2);

        assert (match.getHomeTeam().getScore() == 1);
        assert (match.getAwayTeam().getScore() == 2);
    }

    @Test
    public void updateScore_matchIsNotLive_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, 1, 2));
        assertEquals("Match must be live!", exception.getMessage());
    }

    @Test
    public void updateScore_oneOfTheTeamsScoreIsNegative_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, -1, 2));
        assertEquals("Score cannot be negative!", exception.getMessage());
    }

    @Test
    public void updateScore_homeAndAwayScoresAreNegative_throwsException() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch(match);

        Exception exception = assertThrows(Exception.class, () -> scoreboard.updateScore(match, -1, -2));
        assertEquals("Score cannot be negative!", exception.getMessage());
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
        assertEquals("Match has already ended!", exception.getMessage());
    }
}
