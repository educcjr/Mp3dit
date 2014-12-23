package model;

public class Reference
{
    private char character;
    private int place = 1;
    private boolean before = false;

    public Reference(char character, int place, boolean before)
    {
        this.character = character;
        this.place = place;
        this.before = before;
    }

    public Reference(char character, boolean before)
    {
        this.character = character;
        this.before = before;
    }

    public Reference(char character, int place)
    {
        this.character = character;
        this.place = place;
    }

    public Reference(char character)
    {
        this.character = character;
    }

    public boolean isBefore()
    {
        return before;
    }

    public char getCharacter()
    {
        return character;
    }

    public int getPlace()
    {
        return place;
    }
}
