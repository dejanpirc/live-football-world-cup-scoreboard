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

    private boolean validateMatch(Match match) throws InvalidParameterException {
        if (match == null) {
            throw new NullPointerException(ErrorMessages.MATCH_NOT_SET.getMessage());
        }
        // check if one of the teams is already playing in another match
        return checkIfTeamIsPlayingInOtherMatch(match.getHomeTeam())
                || checkIfTeamIsPlayingInOtherMatch(match.getAwayTeam());
    }

    private boolean checkIfTeamIsPlayingInOtherMatch(Team team) throws InvalidParameterException {
        for (Match match : liveMatches) {
            if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                throw new InvalidParameterException(
                        String.format(ErrorMessages.TEAM_ALREADY_PLAYING.getMessage(), team.getName()));
            }
        }
        return false;
    }

    public void startMatch(Match match) {
        // validate
        validateMatch(match);

        match.getHomeTeam().setScore(0);
        match.getAwayTeam().setScore(0);

        liveMatches.add(match);
    }

    public void updateScore(Match match, int homeTeamScore, int awayTeamScore) {
        if (match == null) {
            throw new NullPointerException(ErrorMessages.MATCH_NOT_SET.getMessage());
        }
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new InvalidParameterException(ErrorMessages.NEGATIVE_SCORE.getMessage());
        }
        if (!liveMatches.contains(match)) {
            throw new InvalidParameterException(ErrorMessages.UPDATE_NOT_LIVE_MATCH.getMessage());
        }

        match.getHomeTeam().setScore(homeTeamScore);
        match.getAwayTeam().setScore(awayTeamScore);
    }

    public void endMatch(Match match) {
        if (match == null) {
            throw new NullPointerException(ErrorMessages.MATCH_NOT_SET.getMessage());
        }
        if (!liveMatches.contains(match)) {
            throw new InvalidParameterException(ErrorMessages.END_NOT_LIVE_MATCH.getMessage());
        }

        liveMatches.remove(match);
    }

    public boolean isMatchLive(Match match) {
        return liveMatches.contains(match);
    }

    public String getSummary() {
        StringBuilder summary = new StringBuilder();

        if (!liveMatches.isEmpty()) {
            // sort live matches
            liveMatches.sort(totalScoreComparator);

            // build summary
            for (int i = 0; i < liveMatches.size(); i++) {
                Match match = liveMatches.get(i);
                summary.append(String.format("%d. %s %d - %s %d",
                        i + 1,
                        match.getHomeTeam().getName(),
                        match.getHomeTeam().getScore(),
                        match.getAwayTeam().getName(),
                        match.getAwayTeam().getScore()));

                if (i < liveMatches.size() - 1) {
                    summary.append("\n");
                }
            }
        } else {
            summary.append(ErrorMessages.NO_LIVE_MATCHES.getMessage());
        }

        return summary.toString();
    }
}
