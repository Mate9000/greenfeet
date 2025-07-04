import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    private int score = 0;
    private int steps = 0;

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
            steps++;
            lowerSteps();
        }
        else {
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
        if ( borderAhead() || fenceAhead() || Mauritius.MAXSTEPS <= steps){
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
            Egg egg = pickUpEgg();
            myNrOfEggsHatched++;
            score += egg.getValue();
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
    /**
     * verytime it picks up grains it also prints where it was found
     */
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
    /**
     * dodo will start to look backwards
     */
    public void turn180( ){
        turnRight();
        turnRight();
    }
    /**
     * goes around the fence by turning alot of ways
     */
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
    /**
     * moves forward chcks for grain and goes back
     */
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
    /**
     * turns 180 moves and turns 180 again
    */
    public void stepOneCellBackwards(){
        turn180();
        move();
        turn180();
    }
    /**
     * just checks if doo is on the egg or not
     */
    public boolean eggFinder(){
        if (onEgg()){
            return true;
        }else{
            return false;
        }
    }
    /**
     * this picks up all the egg objects in the world
     */
    public Egg findEgg() {
        return getWorld().getObjects(Egg.class).getFirst();
    }

    /**
     * this is the finished product where it picks up all the eggs (since the moving limit is 20 it will pick up the nearest eggs)
     */
    public void collectAllEggs() {

        Egg closestEgg = getClosestEgg();

        while (closestEgg != null){

            gotoLocation(closestEgg.getX(), closestEgg.getY());

            if (steps >= Mauritius.MAXSTEPS) {
                break;
            }
            hatchEgg();
            closestEgg = getClosestEgg();
        }
    }
    /**
     * with this i'm able to check for the nearest eggs from dodo to egg
     */
    public Egg getClosestEgg(){

        Egg closestEgg = null;
        int closest = Integer.MAX_VALUE;

        for (Egg egg : getAllEggs()) {

            int eggX = Math.abs(egg.getX() - getX());
            int eggY = Math.abs(egg.getY() - getY());
            int distance = eggX + eggY;

            if (distance < closest) {

                closest = distance;
                closestEgg = egg;
            }
        }

        return closestEgg;
    }
    /**
     *  this is so the amount of steps left are lowered everytime it moves
     */
    public void lowerSteps() {
        Mauritius world = getWorldOfType(Mauritius.class);
        world.updateScore(Mauritius.MAXSTEPS - steps, score);
    }
    /**
     * gotolocation asks first for x input and y input and if you put those 2 in the it goes to that coordinates
     */
    public void gotoLocation(int coordX, int coordY) {
        while (getX() < coordX && steps < Mauritius.MAXSTEPS) {
            faceDirection(EAST);
            move();
        }
        while (getX() > coordX && steps < Mauritius.MAXSTEPS) {
            faceDirection(WEST);
            move();
        }
        while (getY() < coordY && steps < Mauritius.MAXSTEPS) {
            faceDirection(SOUTH);
            move();
        }
        while (getY() > coordY && steps < Mauritius.MAXSTEPS) {
            faceDirection(NORTH);
            move();
        }
    }
    /**
     * turns left
     */
    public void turnLeftDodo() {
        turnLeft();
    }
    /**
     * turns right
     */
    public void turnRightDodo() {
        turnRight();
    } 
        /**
     * starts turning until its facing east then it stops
     */
    public void faceEast(){
        while (! facingEast()){
            turnLeft();
        }
    }
    /**
     * 
     */
    public void walkingArounFencedArea(){
        while (!onEgg()) {
            move();
            turnRight();
            while (fenceAhead()){
                turnLeft();
            }
        }
    }
        /**
     *  walks in a straight line and lays eggs if hes on a nest that doesnt have a egg in it
     */
    public void NestFiller(){
        while(!borderAhead()){
            move();
            if (onNest() && !onEgg()){
                layEgg(); 
            }
        }
    }
    /**
     * just like nest filler but if there is a fence ahead then it hops over it
     */
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
    /**
     * follows the eggs towards the nest
     */
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
    /**
     * in this code it tries to solve a maze untill it reaches the nest
     */
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
    /**
     * picks up egg objects in the world
     */
    public int amountOfEggs() {
        return getWorld().getObjects(Egg.class).size();
    }
    /**
     * picks up just the blue egg objects
     */
    public void amountOfBlueEggs() {
        int eggCount = getWorld().getObjects(BlueEgg.class).size();
        System.out.println("Number of BlueEggs on the map: " + eggCount);
    }
    /**
     * turns left until it looks the way you want it to look (the one you input like South)
     */
    public void faceDirection(int direction){
        while (getDirection() != direction){
            turnLeft();
        }
    }
        /**
     * turns left until its looking down then moves and looks back towards east
     */
    public void goDown() {
        faceDirection(SOUTH);
        move();
        faceDirection(EAST);
    }
    /**
     * goes to 0, 0 and starts making a staircase
     */
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
    /**
     * starts making a staircase multiplicitifly so if it places 1 then next row 2 then 4 etc
     */
    public void monumentOfEggs2(){
        int amountOfEggs = 1;
        for (int i = 0; i < getWorld().getHeight(); i++) {
            gotoLocation(0, i);
            faceDirection(EAST);
            layTrailOfEggs(amountOfEggs);
            amountOfEggs*=2;
        } 
    }
    /**
     * goes up somewhere in the middle and starts to make a pyramid until it cant
     */
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