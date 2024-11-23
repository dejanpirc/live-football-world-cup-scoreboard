package si.dejan.scoreboard;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private List<Match> liveMatches = new ArrayList<Match>();

    public Scoreboard() {
    }

    private boolean validateMatch(Match match) throws InvalidParameterException {
        if (match == null) {
            throw new NullPointerException("Match must be set!");
        }
        // check if one of the teams is already playing in other match
        return checkIfTeamIsPlayingInOtherMatch(match.getHomeTeam()) || checkIfTeamIsPlayingInOtherMatch(match.getAwayTeam());
    }

    private boolean checkIfTeamIsPlayingInOtherMatch(Team team) throws InvalidParameterException {
        for (Match match : liveMatches) {
            if (match.getHomeTeam().getName() == team.getName() || match.getAwayTeam().getName() == team.getName()) {
                throw new InvalidParameterException("Invalid match: team " + team.getName() + " is already playing in other match!");
            }
        }
        return false;
    }

    public void startMatch(Match match) {
        // validate
        validateMatch(match);
        
        liveMatches.add(match);
        // todo optimise this to call method in match to set scores to 0
        match.getHomeTeam().setScore(0);
        match.getAwayTeam().setScore(0);
    }
}
