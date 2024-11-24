package si.dejan.scoreboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TeamTest {
    
    private void assertInvalidTeamName(String name) {
        Exception exception = assertThrows(Exception.class, () -> { Team team = new Team(name); });
        
        assertEquals("Invalid team name!", exception.getMessage());
    }

    @Test
    public void newTeam_teamNameIsNull_throwsException(){
        Exception exception = assertThrows(Exception.class, () -> { Team team = new Team(null); });
        
        assertEquals("Name must be set!", exception.getMessage());
    }

    @Test
    public void newTeam_teamNameContainsNumber_throwsException(){
        assertInvalidTeamName("Team1");
    }

    @Test
    public void newTeam_teamNameContainsSymbol_throwsException(){
        assertInvalidTeamName("Team!");
    }

    @Test
    public void newTeam_teamNameContainsForeignLetters_createsTeamClass(){
        String name = "Teamščžüöäëíí";

        Team team = new Team(name);

        assertEquals(name, team.getName());
    }
}
