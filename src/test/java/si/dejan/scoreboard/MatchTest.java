package si.dejan.scoreboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class MatchTest {
@Test
    public void newMatch_onlyHomeTeamIsSet_throwsException(){
        Team homeTeam = new Team("Mexico");
        
        Exception exception = assertThrows(Exception.class, () -> {Match match = new Match(homeTeam, null);});
        
        assertEquals("Home and away teams must be set!", exception.getMessage());
    }

    @Test
    public void newMatch_onlyAwayTeamIsSet_throwsException(){
        Team awayTeam = new Team("Canada");
        
        Exception exception = assertThrows(Exception.class, () -> {Match match = new Match(null, awayTeam);});
        
        assertEquals("Home and away teams must be set!", exception.getMessage());
    }

    @Test
    public void newMatch_noTeamIsSet_throwsException(){
        Exception exception = assertThrows(Exception.class, () -> {
            Match match = new Match(null, null);
        });

        assertEquals("Home and away teams must be set!", exception.getMessage());
    }
}
