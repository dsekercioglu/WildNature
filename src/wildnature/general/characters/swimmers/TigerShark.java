package wildnature.general.characters.swimmers;

import wildnature.general.characters.*;
import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.SHORT_GRAB;
import processing.core.PApplet;
import static wildnature.general.characters.Animal.TIGER_SHARK;
import wildnature.server.Environment;

public class TigerShark extends Swimmer {

    public TigerShark(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = TIGER_SHARK_LENGTH;
        this.weight = TIGER_SHARK_WEIGHT;
        this.velocity = TIGER_SHARK_SPEED;
        this.passiveAbilityPower = TIGER_SHARK_PASSIVE_ABILITY;
        this.maxEnergy = TIGER_SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = TIGER_SHARK_HEALTH_REGEN;
        this.energyIncrease = TIGER_SHARK_ENERGY_INCREASE;
        this.maxTurn = TIGER_SHARK_TURN;
        this.health = TIGER_SHARK_MAX_HEALTH;
        this.maxHealth = TIGER_SHARK_MAX_HEALTH;
        this.damage = TIGER_SHARK_DAMAGE;
        this.abilityTime = TIGER_SHARK_ABILITY_TIME;
        this.boostTime = TIGER_SHARK_BOOST_TIME;
        this.armor = TIGER_SHARK_ARMOR;
        this.armorPiercing = TIGER_SHARK_ARMOR_PIERCING;
        this.ability1 = SHORT_GRAB;

        this.type = TIGER_SHARK;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = TIGER_SHARK_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = TIGER_SHARK_SPEED * 5;
        }
        this.move(velocity, control.moveAngle());
        if (control.mousePressed() && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
    }

    @Override
    public int getWidth() {
        return (int) TIGER_SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 45;
    }

}
