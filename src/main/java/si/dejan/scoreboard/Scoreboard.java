package si.dejan.scoreboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scoreboard {

    private final List<Match> liveMatches = new ArrayList<>();

    // compare live matches by total score and recently started
    Comparator<Match> totalScoreComparator = (match1, match2) -> {
        // sort by total score
        int totalScore1 = match1.getTotalScore();
        int totalScore2 = match2.getTotalScore();

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

    public synchronized void startMatch(Match match) {
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
            throw new IllegalArgumentException(ErrorMessages.NEGATIVE_SCORE.getMessage());
        }
        if (!liveMatches.contains(match)) {
            throw new IllegalArgumentException(ErrorMessages.UPDATE_NOT_LIVE_MATCH.getMessage());
        }

        match.getHomeTeam().setScore(homeTeamScore);
        match.getAwayTeam().setScore(awayTeamScore);
    }

    public void endMatch(Match match) {
        if (match == null) {
            throw new NullPointerException(ErrorMessages.MATCH_NOT_SET.getMessage());
        }
        if (!liveMatches.contains(match)) {
            throw new IllegalArgumentException(ErrorMessages.END_NOT_LIVE_MATCH.getMessage());
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

    private void validateMatch(Match match) throws IllegalArgumentException {
        if (match == null) {
            throw new NullPointerException(ErrorMessages.MATCH_NOT_SET.getMessage());
        }
        // check if one of the teams is already playing in another match
        checkIfTeamIsPlayingInOtherMatch(match.getHomeTeam());
        checkIfTeamIsPlayingInOtherMatch(match.getAwayTeam());
    }

    private void checkIfTeamIsPlayingInOtherMatch(Team team) throws IllegalArgumentException {
        for (Match match : liveMatches) {
            if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                throw new IllegalArgumentException(
                        String.format(ErrorMessages.TEAM_ALREADY_PLAYING.getMessage(), team.getName()));
            }
        }
    }

    public int getLiveMatchesCount() {
        return liveMatches.size();
    }
}
