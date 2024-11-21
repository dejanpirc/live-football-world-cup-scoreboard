package si.dejan.scoreboard;

import org.junit.Test;

public class ScoreboardTest {

    @Test
    public void startMatch_startNewMatch_scoreIsZero(){
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Canada");
        Match match = new Match(homeTeam, awayTeam);
        Scoreboard scoreboard = new Scoreboard();
        
        scoreboard.startMatch(match);

        assert(match.getHomeTeam().getScore() == 0);
        assert(match.getAwayTeam().getScore() == 0);
    }
}
