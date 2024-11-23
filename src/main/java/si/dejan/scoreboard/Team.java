package si.dejan.scoreboard;

public class Team {
    private String name;
    private int score;
    
    public Team(String name) {
        if (name == null) {
            throw new NullPointerException("Invalid team name!");        
        }
        // check if name contains only letters
        if (!name.matches("[\\p{L}]+")) {
            throw new IllegalArgumentException("Invalid team name!");
        }
        this.name = name;        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }   
}
