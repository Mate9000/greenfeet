import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MovableActor here.
 * 
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 01-01-2017
 */
public class MovableActor extends Actor
{
    public static final int NORTH   = 0;
    public static final int EAST    = 1;
    public static final int SOUTH   = 2;
    public static final int WEST    = 3;

    public void step( int direction ) {
        if ( direction == NORTH ) {
            setLocation( getX(), getY() - 1 );
        } else if ( direction == EAST ) {
             setLocation(getX() + 1, getY() );
        } else if ( direction == SOUTH ) {
            setLocation( getX(), getY() + 1 );
        } else if ( direction == WEST ) {
            setLocation( getX() - 1, getY() );
        }
    }

    public boolean borderAhead ( int direction ) {
        if ( direction == NORTH ) {
            return getY() == 0;
        } else if ( direction == EAST ) {
            return getX() == getWorld().getWidth()  - 1;
        } else if ( direction == SOUTH ) {
            return getY() == getWorld().getHeight() - 1;
        } else if ( direction == WEST ) {
            return getX() == 0;
        } else {
            showError ( "unknown direction" );
            return false;
        }
    }

    protected <E extends Actor> E getActorAhead( int direction, Class<E> cls){
        if ( direction == NORTH ) {
            return (E) getOneObjectAtOffset(0,-1, cls);
        } else if ( direction == EAST ) {
            return (E) getOneObjectAtOffset(1, 0, cls);
        } else if ( direction == SOUTH ) {
            return (E) getOneObjectAtOffset(0, 1, cls);
        } else if ( direction == WEST ) {
            return (E) getOneObjectAtOffset(-1,0, cls);
        } else {
            return null;
        }
    }

    protected <E extends Actor> E getActor(Class<E> cls){
        return (E) getOneObjectAtOffset(0, 0, cls);
    }

    protected void showError ( String err_msg ) {
        Message.showMessage(  new Alert( err_msg ), getWorld() );
    }


}
