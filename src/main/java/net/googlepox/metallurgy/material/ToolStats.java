package net.googlepox.metallurgy.material;

public class ToolStats {

    private final int toolMagic;
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float damage;

    //Attributes
    private double maxHealth;
    private double movementSpeed;
    private double attackDamage;
    private double attackSpeed;
    private double reachDistance;

    public ToolStats(int toolMagic, int harvestLevel, int maxUses, float efficiency, float damage)
    {
        this.toolMagic = toolMagic;
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.damage = damage;
        this.maxHealth = 1.0;
        this.movementSpeed = 1.0;
        this.attackDamage = 1.0;
        this.attackSpeed = 1.0;
        this.reachDistance = 1.0;
    }

    public void setAttributes(double maxHealth, double movementSpeed, double attackDamage, double attackSpeed, double reachDistance)
    {
        this.maxHealth = maxHealth;
        this.movementSpeed = movementSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.reachDistance = reachDistance;
    }

    //Tool Enchantability
    public int getToolMagic()
    {
        return toolMagic;
    }

    public int getHarvestLevel()
    {
        return harvestLevel;
    }

    public int getMaxUses()
    {
        return maxUses;
    }

    public float getEfficiency()
    {
        return efficiency;
    }

    public float getDamage()
    {
        return damage;
    }

    public double getMaxHealth()
    {
        return maxHealth;
    }

    public double getMovementSpeed()
    {
        return movementSpeed;
    }

    public double getAttackDamageAttribute()
    {
        return attackDamage;
    }

    public double getAttackSpeed()
    {
        return attackSpeed;
    }

    public double getReachDistance()
    {
        return reachDistance;
    }

}