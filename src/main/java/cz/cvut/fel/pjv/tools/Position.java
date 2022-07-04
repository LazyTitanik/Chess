package cz.cvut.fel.pjv.tools;

public class Position {
    private char vertical;
    private int horizontal;

    /**
     * Creates new instance. In case wrong parameters were entered, sets
     * variable the way that isNormal() returns false
    */
    public Position(char v, int h){
        if (v < 'a' || v > 'h' || h < 0 || h > 7){
            vertical = 'i';
            horizontal = 8;
        }
        else {
            this.vertical = v;
            this.horizontal = h;
        }
    }

    /**
     * @return true if the position is within a checker
     */
    public boolean isNormal(){
        return(this.vertical >= 'a' && this.vertical <= 'h' &&
                this.horizontal >= 0 && this.horizontal <= 7);
    }
    /**
     * Creates new instance. In case wrong parameters were entered, sets
     * variable the way that isNormal() returns false
     */
    public Position(int v, int h){
        if (v < 0 || v > 7 || h < 0 || h > 7){
            vertical = 'i';
            horizontal = 8;
        }
        else {
            this.vertical = (char)('a' + v);
            this.horizontal = h;
        }
    }
    /**
     * Creates new instance, that is the exact copy of
     * entered position
     */
    public Position(Position position){
        this.vertical = position.vertical;
        this.horizontal = position.horizontal;
    }

    /**
     * Changes the position
     * @return true if parameters are in range of checker
     */
    public boolean setPosition(int v, int h){
        if (v < 0 || v > 7 || h < 0 || h > 7){
            vertical = 'i';                        // it is needed here to enable storing invalid positions
            horizontal = 8;
            return false;
        }
        else {
            this.vertical = (char)('a' + v);
            this.horizontal = h;
            return true;
        }
    }

    /**
     * Changes the position
     * @return true if given position isNormal()
     */
    public boolean setPosition(Position position){
        return setPosition(position.getVertical(), position.getHorizontal());
    }

    public int getVertical(){
        return (vertical - 'a');
    }

    public int getHorizontal(){
        return horizontal;
    }

    public boolean equalsTo(Position position){
        hashCode();
        return (this.getVertical() == position.getVertical() &&
                this.getHorizontal() == position.getHorizontal());
    }


}
