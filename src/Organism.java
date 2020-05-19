public class Organism {
    private String id;
    private int size = 6;
    private int positionX = 0;
    private int positionY = 0;
    private boolean isInfected = false;

    public Organism(int id, int size, int posX, int posY) {
        this.id = String.valueOf(id);
        this.size = size;
        this.positionX = posX;
        this.positionY = posY;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getID() {return this.id;}

    public int getSize() {
        return size;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void infect() {
        this.isInfected = true;
    }

    public void cure() {
        this.isInfected = false;
    }

    public boolean infectCheck() {
        return this.isInfected;
    }

}
