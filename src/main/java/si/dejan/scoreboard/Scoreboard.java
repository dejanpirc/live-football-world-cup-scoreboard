package si.dejan.scoreboard;

public class Scoreboard {

    public Scoreboard() {
    }

    public void startMatch(Match match) {
        // validate
        if (match == null) {
            throw new NullPointerException("Match must be set!");
        }
        
        // todo optimise this to call method in match to set scores to 0
        match.getHomeTeam().setScore(0);
        match.getAwayTeam().setScore(0);
    }

}