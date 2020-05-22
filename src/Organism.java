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

    public String getID() {return this.id;}

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public int getPositionX() {
        return positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    public int getPositionY() {
        return positionY;
    }

    public void infect() {
        this.isInfected = true;
    }
    public boolean infectCheck() {
        return this.isInfected;
    }

    public void cure() {
        this.isInfected = false;
    }


}
