import java.util.ArrayList;
import java.util.List;

public class Player {
    // ===== 基础属性 =====
    String name;
    int level = 1;
    int hp;
    int maxHp;
    int attack;
    int defense;
    int gold = 0;
    int exp = 0;
    int expToNextLevel = 50;

    // ===== 装备 =====
    String weaponName = "铁剑";
    String armorName = "布甲";
    int weaponBonus = 0;
    int armorBonus = 0;

    // ===== 天赋 =====
    String talent = "无";
    int critRate = 0;
    int critDamage = 50;
    int lifesteal = 0;
    int fireDamage = 0;
    int dodgeRate = 0;
    int expBonus = 0;

    // ===== 道具 =====
    int lifePotions = 3;
    int strengthPotions = 2;
    int strengthBoost = 0;
    int skillCooldown = 0;
    int shieldCooldown = 0;
    boolean isShielding = false;

    // ===== 装备收集 =====
    boolean hasSteelSword = false;
    boolean hasDragonSword = false;
    boolean hasChainMail = false;
    boolean hasDragonArmor = false;

    // ===== 宠物系统 =====
    public List<Pet> pets = new ArrayList<>();
    public Pet activePet = null;
    public int maxPets = 3;

    // ===== 构造函数 =====
    public Player() {
        this.hp = 150;
        this.maxHp = 150;
        this.attack = 0;
        this.defense = 0;
    }

    // ===== 方法 =====
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    public void heal(int amount) {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void addExp(int amount) {
        this.exp += amount;
        checkLevelUp();
    }

    private void checkLevelUp() {
        while (exp >= expToNextLevel) {
            exp -= expToNextLevel;
            level++;
            maxHp += 15;
            hp = Math.min(hp + 10, maxHp);
            attack += 3;
            defense += 2;
            expToNextLevel = 50 + level * 20;
            System.out.println("🎊 升级！Lv." + level);
            System.out.println("❤️ 生命 +15 | ⚔️ 攻击 +3 | 🛡️ 防御 +2");
        }
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public boolean spendGold(int amount) {
        if (this.gold >= amount) {
            this.gold -= amount;
            return true;
        }
        return false;
    }
    // ===== 添加宠物 =====
    public boolean addPet(Pet pet) {
        if (pets.size() >= maxPets) {
            System.out.println("❌ 宠物已满！最多 " + maxPets + " 只宠物。");
            return false;
        }
        pets.add(pet);
        if (activePet == null) {
            activePet = pet;
            System.out.println("🐾 " + pet.name + " 被设为当前宠物！");
        }
        System.out.println("✅ " + pet.name + " 加入了你的队伍！");
        return true;
    }

    // ===== 切换宠物 =====
    public void switchPet(int index) {
        if (index >= 0 && index < pets.size()) {
            activePet = pets.get(index);
            System.out.println("🐾 切换宠物为：" + activePet.emoji + " " + activePet.name);
        } else {
            System.out.println("❌ 无效的宠物索引！");
        }
    }

    // ===== 治疗所有宠物 =====
    public void healAllPets() {
        for (Pet pet : pets) {
            pet.heal(pet.maxHp);
            pet.increaseLoyalty(2);
        }
        System.out.println("💚 所有宠物已恢复！");
    }

    // ===== 显示宠物列表 =====
    public void displayPets() {
        if (pets.isEmpty()) {
            System.out.println("🐾 你还没有宠物。");
            return;
        }
        System.out.println("\n🐾 ===== 宠物列表 =====");
        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            String active = (pet == activePet) ? " ✅ 当前" : "";
            System.out.println("  " + (i + 1) + ". " + pet.emoji + " " + pet.name +
                    " (Lv." + pet.level + ")" + active);
            System.out.println("     ❤️ " + pet.hp + "/" + pet.maxHp +
                    " | 😊 忠诚度: " + pet.loyalty + "%");
        }
    }
}