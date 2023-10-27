package re.working.reworking;

public class Coin {
    private final int HEADS = 0;
    private int face;
    public int getFace() { return face; }
    public Coin() { flip(); }
    public void flip() { face = (int) (Math.random() * 2); }
    public boolean isHeads() { return (face == HEADS); }
    public String toString() {
        return (face == HEADS) ? "Heads" : "Tails";
    }
}
