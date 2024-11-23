package si.dejan.scoreboard;

public class Match {
    private Team homeTeam;
    private Team awayTeam;

    public Match(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new NullPointerException("Home and away teams must be set!");
        }
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }   
}
