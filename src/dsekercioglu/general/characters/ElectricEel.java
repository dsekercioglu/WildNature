package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class ElectricEel extends Swimmer {

    private int energyTime;

    public ElectricEel(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = ELECTRIC_EEL_LENGTH;
        this.weight = ELECTRIC_EEL_WEIGHT;
        this.velocity = ELECTRIC_EEL_SPEED;
        this.passiveAbilityPower = ELECTRIC_EEL_PASSIVE_ABILITY;
        this.maxEnergy = ELECTRIC_EEL_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = ELECTRIC_EEL_ENERGY_INCREASE;
        this.maxTurn = ELECTRIC_EEL_TURN;
        this.health = ELECTRIC_EEL_MAX_HEALTH;
        this.maxHealth = ELECTRIC_EEL_MAX_HEALTH;
        this.damage = ELECTRIC_EEL_DAMAGE;
        this.ability = BLEED;

        this.type = "ElectricEel";
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
        }
        this.energy = Math.min(this.energy + this.energyIncrease, this.maxEnergy);
        if ((mousePressed) && (this.energy >= 1.0F) && (this.energyTime == 0)) {
            this.energy -= 1.0F;
            this.velocity *= 6.0F;
            this.energyTime = 150;
        }
        this.energyTime = Math.max(this.energyTime - 1, 0);
        if (this.energyTime == 0) {
            this.velocity = ELECTRIC_EEL_SPEED;
            this.maxTurn = ELECTRIC_EEL_TURN;
        }
        this.health = Math.min(this.health + ELECTRIC_EEL_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public boolean isAlive() {
        return this.health > 0.0F;
    }

    @Override
    public int getWidth() {
        return (int) ELECTRIC_EEL_LENGTH;
    }

    @Override
    public int getHeight() {
        return 18;
    }
}