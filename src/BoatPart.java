public class BoatPart {
    private int [] pos;
    private char [] partState;
    private Boat boat;
    public BoatPart(int [] pos, Boat boat){
        this.pos = pos;
        this.boat = boat;
    }

    //GETTER
    public int[] getPos() {
        return pos;
    }

    public char[] getPartState() {
        return partState;
    }

    // SETTER
    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public void setPartState(char[] partState) {
        this.partState = partState;
    }
}
