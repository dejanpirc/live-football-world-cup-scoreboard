package si.dejan.scoreboard;

public class Team {
    private String name;
    private int score;
    
    public Team(String name) {
        if (name == null) {
            throw new NullPointerException(ErrorMessages.MISSING_TEAM_NAME.getMessage());        
        }
        // check if name contains only letters
        if (!name.matches("[\\p{L}]+")) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_TEAM_NAME.getMessage());
        }
        this.name = name;        
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }   

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Team) {
            Team otherTeam = (Team) obj;
            return this.name.equalsIgnoreCase(otherTeam.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.name.toLowerCase().hashCode();
    }
}
