import java.util.Random;
import java.util.Scanner;

public class BattleSystem {
    private Player player;
    private Monster monster;
    private Random random;
    private boolean isShielding;
    private int strengthBoost;
    private CaptureSystem captureSystem;

    public BattleSystem(Player player) {
        this.player = player;
        this.random = new Random();
        this.isShielding = false;
        this.strengthBoost = 0;
        this.captureSystem = new CaptureSystem();
    }

    public void startBattle(Monster monster) {
        this.monster = monster;
        this.monster.reset();
        this.isShielding = false;
        this.strengthBoost = 0;

        System.out.println("\n⚔️ 你遇到了 " + monster.name + "！");
        System.out.println("💢 " + monster.name + " HP: " + monster.hp + "/" + monster.maxHp);

        if (player.activePet != null && player.activePet.isAlive()) {
            System.out.println("🐾 当前宠物：" + player.activePet.emoji + " " + player.activePet.name + " 参战！");
            player.activePet.reduceCooldown();
        }

        while (player.isAlive() && monster.isAlive()) {
            displayStatus();
            int choice = getPlayerAction();
            executeAction(choice);
        }

        if (player.isAlive()) {
            victory();
        } else {
            defeat();
        }
    }

    public void startRandomBattle(Scene scene) {
        String monsterName = scene.getRandomMonster();
        if (monsterName == null) {
            System.out.println("🌿 这里没有怪物。");
            return;
        }
        Monster monster = MonsterFactory.createMonster(monsterName);
        startBattle(monster);
    }

    private void displayStatus() {
        System.out.println("----------------------------------");
        System.out.println("❤️ 你的血量：" + player.hp + "/" + player.maxHp);
        System.out.println("💢 " + monster.name + " HP: " + monster.hp + "/" + monster.maxHp);

        if (player.activePet != null && player.activePet.isAlive()) {
            System.out.println("🐾 " + player.activePet.emoji + " " + player.activePet.name +
                    " (Lv." + player.activePet.level + ") HP: " + player.activePet.hp + "/" + player.activePet.maxHp);
        }

        System.out.println("📊 等级：Lv." + player.level + " | 经验：" + player.exp + "/" + player.expToNextLevel);
        System.out.println("💰 金币：" + player.gold);
        System.out.println("⚔️ 装备：" + player.weaponName + "(+" + player.weaponBonus + ")");
        System.out.println("🛡️ 防具：" + player.armorName + "(+" + player.armorBonus + ")");
        System.out.println("🎯 天赋：" + player.talent);
        System.out.println("1. ⚔️ 攻击");
        System.out.println("2. 🏃 逃跑");
        if (player.skillCooldown == 0) {
            System.out.println("3. ⚡ 英勇一击");
        } else {
            System.out.println("3. ⚡ 英勇一击（冷却" + player.skillCooldown + "回合）");
        }
        if (player.shieldCooldown == 0) {
            System.out.println("4. 🛡️ 防御壁垒");
        } else {
            System.out.println("4. 🛡️ 防御壁垒（冷却" + player.shieldCooldown + "回合）");
        }
        System.out.println("5. 💊 生命药水 (" + player.lifePotions + "瓶)");
        if (player.activePet != null && player.activePet.isAlive()) {
            System.out.println("6. 🐾 宠物攻击");
            if (player.activePet.skillCooldown == 0) {
                System.out.println("7. ✨ 宠物技能 (" + player.activePet.skillName + ")");
            } else {
                System.out.println("7. ✨ 宠物技能（冷却" + player.activePet.skillCooldown + "回合）");
            }
        }
        if (monster.canCapture() && player.pets.size() < player.maxPets) {
            System.out.println("8. 🎯 捕捉宠物");
        }
        System.out.print("请选择: ");
    }

    private int getPlayerAction() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 0;
        }
        return choice;
    }

    private void executeAction(int choice) {
        if (player.skillCooldown > 0) player.skillCooldown--;
        if (player.shieldCooldown > 0) player.shieldCooldown--;
        if (strengthBoost > 0) strengthBoost--;

        switch (choice) {
            case 1: playerAttack(); break;
            case 2: playerEscape(); break;
            case 3: playerSkill(); break;
            case 4: playerShield(); break;
            case 5: playerUsePotion(); break;
            case 6: petAttack(); break;
            case 7: petSkill(); break;
            case 8: captureMonster(); break;
            default: playerDaze(); break;
        }

        if (player.activePet != null) {
            player.activePet.reduceCooldown();
        }

        if (choice != 2 && choice != 8 && monster.isAlive()) {
            monsterAttack();
        }
    }

    private void playerAttack() {
        int damage = random.nextInt(11) + 15;
        damage += player.weaponBonus + player.attack;

        if (player.critRate > 0 && random.nextInt(100) < player.critRate) {
            damage = damage * (100 + player.critDamage) / 100;
            System.out.println("💥 暴击！伤害提升" + player.critDamage + "%！");
        }

        if (player.fireDamage > 0) {
            damage += player.fireDamage;
            System.out.println("🔥 火焰伤害 +" + player.fireDamage + "！");
        }

        if (strengthBoost > 0) {
            damage += 10;
            System.out.println("💪 力量药剂生效！");
            strengthBoost = 0;
        }

        monster.takeDamage(damage);
        System.out.println("⚔️ 你造成了 " + damage + " 点伤害！");

        if (player.lifesteal > 0) {
            int heal = damage * player.lifesteal / 100;
            if (heal > 0) {
                player.heal(heal);
                System.out.println("🩸 吸血恢复 " + heal + " 点HP！");
            }
        }
    }

    private void playerEscape() {
        int escapeChance = 20;
        if (player.talent.equals("💨 敏捷之路")) {
            escapeChance += 30;
        }
        if (random.nextInt(100) < escapeChance) {
            System.out.println("💨 你成功逃脱了！");
            player.hp = 0;
            return;
        }
        System.out.println("🏃 逃跑失败！");
    }

    private void playerSkill() {
        if (player.skillCooldown > 0) {
            System.out.println("⏳ 技能冷却中！");
            return;
        }
        int damage = random.nextInt(11) + 25;
        damage += player.weaponBonus + player.attack;
        player.skillCooldown = 3;

        if (player.critRate > 0 && random.nextInt(100) < player.critRate) {
            damage = damage * (100 + player.critDamage) / 100;
            System.out.println("💥 暴击！");
        }

        monster.takeDamage(damage);
        System.out.println("🗡️ 英勇一击！造成 " + damage + " 点伤害！");
        System.out.println("⚡ 冷却3回合");
    }

    private void playerShield() {
        if (player.shieldCooldown > 0) {
            System.out.println("⏳ 防御壁垒冷却中！");
            return;
        }
        isShielding = true;
        player.shieldCooldown = 2;
        System.out.println("🛡️ 防御壁垒开启！伤害减半！");
    }

    private void playerUsePotion() {
        if (player.lifePotions <= 0) {
            System.out.println("😰 没有生命药水了！");
            return;
        }
        player.lifePotions--;
        player.heal(30);
        System.out.println("💊 恢复30HP！剩余" + player.lifePotions + "瓶");
    }

    private void petAttack() {
        if (player.activePet == null || !player.activePet.isAlive()) {
            System.out.println("❌ 没有可用的宠物！");
            return;
        }

        int damage = player.activePet.attack();
        monster.takeDamage(damage);
        System.out.println("🐾 " + player.activePet.emoji + " " + player.activePet.name + " 攻击造成 " + damage + " 点伤害！");
        player.activePet.addExp(2);
    }

    private void petSkill() {
        if (player.activePet == null || !player.activePet.isAlive()) {
            System.out.println("❌ 没有可用的宠物！");
            return;
        }

        int damage = player.activePet.useSkill();
        monster.takeDamage(damage);
        System.out.println("✨ " + player.activePet.emoji + " " + player.activePet.name +
                " 使用 " + player.activePet.skillName + " 造成 " + damage + " 点伤害！");
        player.activePet.addExp(4);
    }

    private void captureMonster() {
        if (!monster.canCapture()) {
            System.out.println("❌ 这个怪物无法被捕捉！");
            return;
        }
        captureSystem.attemptCapture(player, monster, new Scanner(System.in));
    }

    private void playerDaze() {
        System.out.println("🤔 你在发呆...");
    }

    private void monsterAttack() {
        int damage = monster.getAttackDamage();

        if (player.dodgeRate > 0 && random.nextInt(100) < player.dodgeRate) {
            System.out.println("💨 你闪避了攻击！");
            return;
        }

        if (isShielding) {
            damage = damage / 2;
            isShielding = false;
            System.out.println("🛡️ 防御壁垒减半伤害！");
        }

        if (player.armorBonus + player.defense > 0) {
            damage = Math.max(damage - (player.armorBonus + player.defense), 1);
        }

        player.takeDamage(damage);
        System.out.println("🐉 " + monster.name + " 攻击造成 " + damage + " 点伤害！");
    }

    private void victory() {
        System.out.println("\n🎉 胜利！击败了 " + monster.name + "！");

        int goldReward = monster.goldReward + random.nextInt(15);
        player.addGold(goldReward);
        System.out.println("💰 获得 " + goldReward + " 金币！");

        int expReward = monster.expReward + random.nextInt(15);
        if (player.expBonus > 0) {
            expReward += expReward * player.expBonus / 100;
        }
        player.addExp(expReward);
        System.out.println("✨ 获得 " + expReward + " 经验！");

        if (player.activePet != null && player.activePet.isAlive()) {
            int petExp = expReward / 2;
            player.activePet.addExp(petExp);
            System.out.println("🐾 " + player.activePet.name + " 获得 " + petExp + " 经验！");
        }
    }

    private void defeat() {
        System.out.println("\n💀 你被击败了...");
    }
}