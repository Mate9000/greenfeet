import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    //int eggCount = getWorld().getObjects(BlueEgg.class).size();
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
            System.out.println ("moved " + nrStepsTaken); 
        }
    }

    public void layTrailOfEggs( int distance ) {
        int nrStepsTaken = 0;               
        while ( nrStepsTaken < distance ) { 
            layEgg();
            move();
            nrStepsTaken++;
        }
    }

    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        while( ! borderAhead() ){
            System.out.println (getX() + " " + getY());
            move();
        }
    }

    public void pickUpGrainsAndPrintCoordinates(){
        while(!borderAhead()){
            move();

            if (onGrain()) {
                System.out.println (getX() + " " + getY());
                pickUpGrain();
            }
        }
    }

    public void goBackToStartOfRowAndFaceBack(){
        turn180();
        while( ! borderAhead()){
            move();
        } 
        turn180();
    }

    public void walkToWorldEdgeClimbingOverFences() {
        while (!borderAhead()) {
            if (fenceAhead()) {
                climbOverFence();
            } else if (canMove()) {
                move();
            } 
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
            //ik heb hier gewoon return false toegevoegd
        }else{
            return true;
        }
    }  

    public void turn180( ){
        turnRight();
        turnRight();
    }

    public void climbOverFence ( ){
        if  (fenceAhead() && !borderAhead()){
            turnLeft();
            move();
            turnRight();
            move();
            move();
            turnRight();
            move();
            turnLeft();
        }else{
            System.out.println ("alone at the edge of the universe ;D");
        }

    }

    public boolean grainAhead(){
        move();
        if (onGrain()){
            turn180();
            move();
            turn180();
            return true;
        }else {
            turn180();
            move();
            turn180();
            return false;
        }
    }

    public void stepOneCellBackwards(){
        turn180();
        move();
        turn180();
    }

    public boolean eggFinder(){
        if (onEgg()){
            return true;
        }else{
            return false;
        }
    }

    public Egg findEgg() {
        return getWorld().getObjects(Egg.class).getFirst();
    }

    public void goToEgg() {
        Egg egg = findEgg();
        int eggX = egg.getX();
        int eggY = egg.getY();
        for (Egg eggs : getAllEggs()) {
            while (!onEgg()) {
                gotoLocation(eggX, eggY);
            }

            if (onEgg()){
                pickUpEgg();
            }
        }
    }

    public void gotoLocation(int coordX, int coordY) {
        while (getX() < coordX) {
            faceDirection(EAST);
            move();
        }
        while (getX() > coordX) {
            faceDirection(WEST);
            move();
        }
        while (getY() < coordY) {
            faceDirection(SOUTH);
            move();
        }
        while (getY() > coordY) {
            faceDirection(NORTH);
            move();
        }
    }

    public void turnLeftDodo() {
        turnLeft();
    }

    public void turnRightDodo() {
        turnRight();
    } 

    public void faceEast(){
        while (! facingEast()){
            turnLeft();
        }
    }

    public void walkingArounFencedArea(){
        while (!onEgg()) {
            move();
            turnRight();
            while (fenceAhead()){
                turnLeft();
            }
        }
    }

    public void NestFiller(){
        while(!borderAhead()){
            move();
            if (onNest() && !onEgg()){
                layEgg(); 
            }
        }
    }

    public void nestFillerAvoidingFences(){
        while(!borderAhead()){
            if (onNest() && !onEgg()){
                layEgg(); 
            }
            else if (fenceAhead()) {
                climbOverFence();
            } else if (canMove()) {
                move();
            } 
        }
    }

    public void eggTrailToNest(){
        while (eggAhead()){
            move();
            pickUpEgg();
            while (!eggAhead()){
                turnLeft();
                if(nestAhead()){
                    move();
                    layEgg(); 
                    break;
                }
            }
        }
    }

    public void mazeSolver(){
        while (!onNest()) {
            move();
            turnRight();
            while (fenceAhead()){
                turnLeft();
            }
            if (onNest()){
                System.out.println("MAZE SOLVED");
            }
        }
    }  

    public int amountOfEggs() {
        return getWorld().getObjects(Egg.class).size();
    }

    public void amountOfBlueEggs() {
        int eggCount = getWorld().getObjects(BlueEgg.class).size();
        System.out.println("Number of BlueEggs on the map: " + eggCount);
    }

    public void faceDirection(int direction){
        while (getDirection() != direction){
            turnLeft();
        }
    }

    public void goDown() {
        faceDirection(SOUTH);
        move();
        faceDirection(EAST);
    }

    public void monumentOfEggs(){
        for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(0, i);
            faceDirection(EAST);
            int a = i;
            while (a >= 0){
                layEgg();
                move();
                a--;
            }
        }
    }

    public void monumentOfEggs2(){
        int amountOfEggs = 1;
        for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(0, i);
            faceDirection(EAST);
            layTrailOfEggs(amountOfEggs);
            amountOfEggs*=2;
        } 
    }

    public void monumentOfEggs3(){
        int amountOfEggs = 1;
        int a = getWorld().getWidth();
        int y = a / 2;
        for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(y, i);
            faceDirection(EAST);
            layTrailOfEggs(amountOfEggs);
            amountOfEggs+=2;
            y--;
            if (y == 0){
                break;
            }
        } 
    }
}