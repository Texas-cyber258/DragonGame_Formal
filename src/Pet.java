import java.util.Random;

public class Pet {
    // ===== 基础属性 =====
    public String name;
    public String type;
    public String emoji;
    public int level = 1;
    public int hp;
    public int maxHp;
    public int attack;
    public int defense;
    public int exp = 0;
    public int expToNextLevel = 30;
    public int loyalty = 50; // 忠诚度 0-100
    public String skillName;
    public int skillDamage;
    public int skillCooldown = 0;
    public int maxSkillCooldown = 3;

    // ===== 构造函数 =====
    public Pet(String name, String type, String emoji, int hp, int attack, int defense, String skillName, int skillDamage) {
        this.name = name;
        this.type = type;
        this.emoji = emoji;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.skillName = skillName;
        this.skillDamage = skillDamage;
    }

    // ===== 攻击方法 =====
    public int attack() {
        Random random = new Random();
        int damage = attack + random.nextInt(5);
        return Math.max(damage, 1);
    }

    // ===== 技能攻击 =====
    public int useSkill() {
        if (skillCooldown > 0) {
            System.out.println("⏳ " + name + " 的技能还在冷却中！");
            return attack();
        }
        skillCooldown = maxSkillCooldown;
        Random random = new Random();
        int damage = skillDamage + random.nextInt(10);
        System.out.println("✨ " + name + " 使用了 " + skillName + "！");
        return Math.max(damage, 1);
    }

    // ===== 受到伤害 =====
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    // ===== 恢复 =====
    public void heal(int amount) {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }

    // ===== 增加经验 =====
    public void addExp(int amount) {
        this.exp += amount;
        checkLevelUp();
    }

    // ===== 检查升级 =====
    private void checkLevelUp() {
        while (exp >= expToNextLevel) {
            exp -= expToNextLevel;
            level++;
            maxHp += 8;
            hp = Math.min(hp + 5, maxHp);
            attack += 2;
            defense += 1;
            skillDamage += 2;
            expToNextLevel = (int)(expToNextLevel * 1.5);
            loyalty = Math.min(loyalty + 5, 100);
            System.out.println("🐾 " + name + " 升级了！Lv." + level);
            System.out.println("   ❤️ HP +8 | ⚔️ 攻击 +2 | 🛡️ 防御 +1");
            System.out.println("   ❤️ 当前HP: " + hp + "/" + maxHp);
            System.out.println("   😊 忠诚度 +5 (当前: " + loyalty + ")");
        }
    }

    // ===== 是否存活 =====
    public boolean isAlive() {
        return this.hp > 0;
    }

    // ===== 增加忠诚度 =====
    public void increaseLoyalty(int amount) {
        this.loyalty = Math.min(this.loyalty + amount, 100);
    }

    // ===== 减少冷却 =====
    public void reduceCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }

    // ===== 显示宠物信息 =====
    public void display() {
        System.out.println(emoji + " " + name + " (Lv." + level + ")");
        System.out.println("  ❤️ HP: " + hp + "/" + maxHp);
        System.out.println("  ⚔️ 攻击: " + attack);
        System.out.println("  🛡️ 防御: " + defense);
        System.out.println("  ✨ 技能: " + skillName + " (伤害: " + skillDamage + ")");
        System.out.println("  😊 忠诚度: " + loyalty + "%");
        System.out.println("  📊 经验: " + exp + "/" + expToNextLevel);
    }
}
