public final class Player {
    private final String id; 
    private int position; 
    Player(String id){
        this.id = id;
        position = 1; 
    }
    public String getId() {
        return id;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    
}
