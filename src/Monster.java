import java.util.Random;

public class Monster {
    String name;
    int hp;
    int maxHp;
    int minAttack;
    int maxAttack;
    int defense;
    int expReward;
    int goldReward;

    // ===== Boss专属 =====
    boolean isBoss;
    int bossLevel;

    public boolean isTamable = false;
    public String petName;
    public int captureRate = 30;

    // ===== 普通怪物 =====
    public Monster(String name, int hp, int minAttack, int maxAttack, int expReward, int goldReward) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.defense = 0;
        this.expReward = expReward;
        this.goldReward = goldReward;
        this.isBoss = false;
        this.isTamable = true;
        this.petName = name;
        this.captureRate = 20 + (int)(Math.random() * 20);
    }


    // ===== Boss构造函数 =====
    public Monster(String name, int hp, int minAttack, int maxAttack, int expReward, int goldReward, boolean isBoss) {
        this(name, hp, minAttack, maxAttack, expReward, goldReward);
        this.isBoss = isBoss;
        this.bossLevel = 1;
        this.isTamable = false;  // Boss不可捕捉
        this.captureRate = 0;
    }


    // ===== 设置可捕捉 =====
    public void setTamable(boolean tamable, String petName) {
        this.isTamable = tamable;
        this.petName = petName;
    }

    public boolean canCapture() {
        return isTamable && !isBoss;
    }

    // ===== 获取捕捉率（根据血量调整） =====
    public int getCurrentCaptureRate() {
        if (!canCapture()) return 0;
        double hpRatio = (double)hp / maxHp;
        // 血量越低，捕捉率越高
        int bonus = (int)((1 - hpRatio) * 30);
        return Math.min(captureRate + bonus, 80);
    }


    public int getAttackDamage() {
        Random random = new Random();
        return random.nextInt(maxAttack - minAttack + 1) + minAttack;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void reset() {
        this.hp = this.maxHp;
    }

    @Override
    public String toString() {
        return this.name + " (HP: " + this.hp + "/" + this.maxHp + ")";
    }
}