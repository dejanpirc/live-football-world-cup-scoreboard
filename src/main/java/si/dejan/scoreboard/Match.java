package si.dejan.scoreboard;

public class Match {
    private String id;
    private Team homeTeam;
    private Team awayTeam;

    public Match(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new NullPointerException("Home and away teams must be set!");
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Match) {
            Match otherMatch = (Match) obj;
            return this.id.equals(otherMatch.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
