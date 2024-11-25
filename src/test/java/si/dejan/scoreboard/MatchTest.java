package si.dejan.scoreboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class MatchTest {

    private void assertNoHomeAwayTeam(Team homeTeam, Team awayTeam) {
        Exception exception = assertThrows(NullPointerException.class, () -> new Match(homeTeam, awayTeam));

        assertEquals("Home and away teams must be set!", exception.getMessage());
    }

    @Test
    public void newMatch_onlyHomeTeamIsSet_throwsException() {
        Team homeTeam = new Team("Mexico");

        assertNoHomeAwayTeam(homeTeam, null);
    }

    @Test
    public void newMatch_onlyAwayTeamIsSet_throwsException() {
        Team awayTeam = new Team("Canada");

        assertNoHomeAwayTeam(null, awayTeam);
    }

    @Test
    public void newMatch_noTeamIsSet_throwsException() {
        assertNoHomeAwayTeam(null, null);
    }
}
