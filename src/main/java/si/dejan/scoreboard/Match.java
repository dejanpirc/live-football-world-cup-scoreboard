package si.dejan.scoreboard;

public class Match {
    private String id;
    private final Team homeTeam;
    private final Team awayTeam;

    public Match(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new NullPointerException(ErrorMessages.MISSING_HOME_AWAY_TEAMS.getMessage());
        }
        this.id = java.util.UUID.randomUUID().toString();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.id = homeTeam.getName() + "-" + awayTeam.getName();
    }

    public String getId() {
        return id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getTotalScore() {
        return homeTeam.getScore() + awayTeam.getScore();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Match otherMatch) {
            return this.id.equals(otherMatch.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
