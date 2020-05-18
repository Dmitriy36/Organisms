public class Organism {
    private int size = 6;
    private int positionX = 0;
    private int positionY = 0;
    private boolean isInfected = false;

    public Organism(int size, int posX, int posY) {
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

    public int getSize() {
        return size;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

}
