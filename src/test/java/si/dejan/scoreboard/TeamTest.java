package si.dejan.scoreboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TeamTest {
    
    private void assertTeamName(String name) {
        Exception exception = assertThrows(Exception.class, () -> { Team team = new Team(name); });
        
        assertEquals("Invalid team name!", exception.getMessage());
    }

    @Test
    public void newTeam_teamNameIsNull_throwsException(){
        assertTeamName(null);
    }

    @Test
    public void newTeam_teamNameContainsNumber_throwsException(){
        assertTeamName("Team1");
    }

    @Test
    public void newTeam_teamNameContainsSymbol_throwsException(){
        assertTeamName("Team!");
    }
}
