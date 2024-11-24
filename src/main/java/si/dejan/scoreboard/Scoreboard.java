package si.dejan.scoreboard;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scoreboard {

    private List<Match> liveMatches = new ArrayList<>();

    // sort live matches by total score and recently started
    Comparator<Match> totalScoreComparator = (match1, match2) -> {
        // sorty by total score
        int totalScore1 = match1.getHomeTeam().getScore() + match1.getAwayTeam().getScore();
        int totalScore2 = match2.getHomeTeam().getScore() + match2.getAwayTeam().getScore();

        // compare total scores in descending order
        int scoreComparison = Integer.compare(totalScore2, totalScore1);

        if (scoreComparison != 0) {
            // if scores are different, sort by score
            return scoreComparison;
        } else {
            // sort by recently started
            int match1Index = liveMatches.indexOf(match1);
            int match2Index = liveMatches.indexOf(match2);
            
            // compare insertion order in descending order
            return Integer.compare(match2Index, match1Index);
        }
    };

    public Scoreboard() {
    }

    private boolean validateMatch(Match match) throws InvalidParameterException {
        if (match == null) {
            throw new NullPointerException("Match must be set!");
        }
        // check if one of the teams is already playing in another match
        return checkIfTeamIsPlayingInOtherMatch(match.getHomeTeam())
                || checkIfTeamIsPlayingInOtherMatch(match.getAwayTeam());
    }

    private boolean checkIfTeamIsPlayingInOtherMatch(Team team) throws InvalidParameterException {
        for (Match match : liveMatches) {
            if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                throw new InvalidParameterException(
                        "Invalid match: team " + team.getName() + " is already playing in another match!");
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

    public void updateScore(Match match, int homeTeamScore, int awayTeamScore) {
        if (match == null) {
            throw new NullPointerException("Match must be set!");
        }
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new InvalidParameterException("Score must be positive!");
        }
        if (!liveMatches.contains(match)) {
            throw new InvalidParameterException("Only live matches can be updated!");
        }

        match.getHomeTeam().setScore(homeTeamScore);
        match.getAwayTeam().setScore(awayTeamScore);
    }

    public void endMatch(Match match) {
        if (match == null) {
            throw new NullPointerException("Match must be set!");
        }
        if (!liveMatches.contains(match)) {
            throw new InvalidParameterException("Only live matches can be ended!");
        }

        liveMatches.remove(match);
    }

    public boolean isMatchLive(Match match) {
        return liveMatches.contains(match);
    }

    public String getSummary() {
        String summary = "";

        if (!liveMatches.isEmpty()) {
            // sort live matches
            liveMatches.sort(totalScoreComparator);

            // build summary
            for (int i = 0; i < liveMatches.size(); i++) {
                Match match = liveMatches.get(i);
                summary += String.format("%d. %s %d - %s %d",
                        i + 1,
                        match.getHomeTeam().getName(),
                        match.getHomeTeam().getScore(),
                        match.getAwayTeam().getName(),
                        match.getAwayTeam().getScore());

                if (i < liveMatches.size() - 1) {
                    summary += "\n";
                }
            }
        } else {
            summary = "No live matches.";
        }

        return summary;
    }
}
