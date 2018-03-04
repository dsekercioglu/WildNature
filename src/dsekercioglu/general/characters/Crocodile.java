package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.GRAB;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Crocodile extends Swimmer {

    private int energyTime;

    public Crocodile(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = CROCODILE_LENGTH;
        this.weight = CROCODILE_WEIGHT;
        this.velocity = CROCODILE_SPEED;
        this.passiveAbilityPower = CROCODILE_PASSIVE_ABILITY;
        this.maxEnergy = CROCODILE_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = CROCODILE_ENERGY_INCREASE;
        this.maxTurn = CROCODILE_TURN;
        this.health = CROCODILE_MAX_HEALTH;
        this.maxHealth = CROCODILE_MAX_HEALTH;
        this.damage = CROCODILE_DAMAGE;
        this.ability = GRAB;
        
        this.type = "Crocodile";
    }
    
    private double turn(double newAngle) {
        double dif = newAngle - this.angle;
        while (dif < -3.141592653589793D) {
            dif += 6.283185307179586D;
        }
        while (dif > 3.141592653589793D) {
            dif -= 6.283185307179586D;
        }
        return Math.max(Math.min(dif, this.maxTurn), -this.maxTurn);
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        this.angle = ((float) (this.angle + turn((float) Math.atan2(mouseY - 300, mouseX - 600))));
        if (Point2D.distance(600, 300, mouseX, mouseY) > 100 || energyTime != 0) {
            this.x = ((float) (this.x + Math.cos(this.angle) * this.velocity));
            this.y = ((float) (this.y + Math.sin(this.angle) * this.velocity));
            this.hiding = false;
        } else {
            this.hiding = true;
        }
        this.energy = Math.min(this.energy + this.energyIncrease, this.maxEnergy);
        if ((mousePressed) && (this.energy >= 1.0F) && (this.energyTime == 0)) {
            this.energy -= 1.0F;
            this.velocity *= 15.0F;
            this.maxTurn /= 15.0F;
            this.energyTime = 30;
        }
        this.energyTime = Math.max(this.energyTime - 1, 0);
        if (this.energyTime == 0) {
            this.velocity = CROCODILE_SPEED;
            this.maxTurn = CROCODILE_TURN;
        }
        this.health = Math.min(this.health + CROCODILE_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public boolean isAlive() {
        return this.health > 0.0F;
    }

    @Override
    public int getWidth() {
        return (int) CROCODILE_LENGTH;
    }

    @Override
    public int getHeight() {
        return 59;
    }
    
}