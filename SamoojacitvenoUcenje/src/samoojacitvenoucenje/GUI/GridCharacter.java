/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI;

/**
 *
 * @author Evgen
 */
       
public  class GridCharacter  {

    public static final int HEALTH_MAX = 100;
    public static final int HUNGER_MAX = 100;
    
    private int health = 0;
    private int hunger = 0;
    private int speed = 800;
    private boolean isStopped = false;
    public boolean hasHealthChanged = false;
    public boolean hasHungerChanged = false;

    public boolean isStopped() {
        return isStopped;
    }

    public void setIsStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }

    public GridCharacter() {
        health = HEALTH_MAX;
        hunger = HUNGER_MAX;
    }  
    public GridCharacter(int health) {
        this.health = health;
        this.hunger = HUNGER_MAX;
    }   
    public GridCharacter(int health, int hunger) {
        this.health = health;
        this.hunger = hunger;
    }


    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        if(this.health + health > HEALTH_MAX) {
            this.health = HEALTH_MAX;
        }
        else if(health < 0) {
            this.health = 0;
        }
        else {
            this.health += health;
        }
    }   
    public void addHealth(int health) {
        if(this.health + health > HEALTH_MAX) {
            this.health = HEALTH_MAX;
        }
        else {
            this.health += health;
        }
    }  
    public void removeHealth(int health) {
        if(this.health - health < 0) {
            this.health = 0;
        }
        else {
            this.health -= health;
        }
    }

    public int getHunger() {
        return hunger;
    }
    

    public void setHunger(int hunger) {       
        if(this.hunger + hunger > HUNGER_MAX) {
            this.hunger = HUNGER_MAX;
        }
        else if(hunger < 0) {
            this.hunger = 0;
        }
        else {
            this.hunger += hunger;
        }
    }
    
    public void addHunger(int hunger) {
        if(this.hunger + hunger > HUNGER_MAX) {
            this.hunger = HUNGER_MAX;
        }
        else {
            this.hunger += hunger;
        }
    }  
    public void removeHunger(int hunger) {
        if(this.hunger - hunger < 0) {
            this.hunger = 0;
        }
        else {
            this.hunger -= hunger;
        }
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }
}
