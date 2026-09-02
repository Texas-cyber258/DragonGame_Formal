import java.util.Scanner;

public class Game {
    private Player player;
    private BattleSystem battleSystem;
    private World world;
    private Scanner scanner;
    private int currentBossIndex;
    private boolean hasChosenTalent;

    public Game() {
        this.player = new Player();
        this.scanner = new Scanner(System.in);
        this.currentBossIndex = 0;
        this.hasChosenTalent = false;
        this.world = new World();
        this.battleSystem = new BattleSystem(player);
    }

    public void start() {
        System.out.println("═══════════════════════════════════");
        System.out.println("        ⚔️ 勇者传说 ⚔️");
        System.out.println("═══════════════════════════════════");

        // 选择天赋
        if (!hasChosenTalent) {
            chooseTalent();
            hasChosenTalent = true;
        }

        // 游戏主循环
        gameLoop();

        scanner.close();
        System.out.println("═══════════════════════════════════");
        System.out.println("       感谢游玩！");
        System.out.println("═══════════════════════════════════");
    }

    private void gameLoop() {
        boolean playing = true;

        while (playing) {
            // 显示当前场景
            world.currentScene.display();

            // 显示可用操作
            System.out.println("\n📋 选择行动：");
            System.out.println("1. 🗺️ 探索区域");
            System.out.println("2. ⚔️ 寻找怪物");
            System.out.println("3. 💬 与NPC对话");
            System.out.println("4. 🏃 前往其他区域");
            System.out.println("5. 📊 查看状态");
            System.out.println("6. 📦 背包");
            System.out.println("7. 🐾 宠物管理");  // 新增
            System.out.println("8. 💾 保存游戏");
            System.out.println("9. ❌ 退出游戏");
            System.out.print("请选择 (1-9): ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            switch (choice) {
                case 1: exploreScene(); break;
                case 2: findMonster(); break;
                case 3: talkToNPC(); break;
                case 4: travelMenu(); break;
                case 5: showStatus(); break;
                case 6: showInventory(); break;
                case 7: petManagement(); break;
                case 8: saveGame(); break;
                case 9:
                    playing = false;
                    System.out.println("👋 再见，勇者！");
                    break;
                default:
                    System.out.println("❌ 无效选择！");
            }
        }
    }
    private void petManagement() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n🐾 ===== 宠物管理 =====");
            player.displayPets();

            if (!player.pets.isEmpty()) {
                System.out.println("\n1. 🐾 切换宠物");
                System.out.println("2. 💚 治疗所有宠物 (20金币)");
                System.out.println("3. ❤️ 喂食宠物 (增加忠诚度)");
            }
            System.out.println("0. 🔙 返回");
            System.out.print("请选择: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice == 0) {
                managing = false;
                System.out.println("🔙 返回");
            } else if (choice == 1 && !player.pets.isEmpty()) {
                switchPet();
            } else if (choice == 2 && !player.pets.isEmpty()) {
                if (player.spendGold(20)) {
                    player.healAllPets();
                } else {
                    System.out.println("❌ 金币不足！需要20金币。");
                }
            } else if (choice == 3 && !player.pets.isEmpty()) {
                feedPet();
            } else {
                System.out.println("❌ 无效选择！");
            }
        }
    }

    // ===== 切换宠物 =====
    private void switchPet() {
        System.out.println("🐾 选择宠物 (输入编号): ");
        for (int i = 0; i < player.pets.size(); i++) {
            Pet pet = player.pets.get(i);
            String active = (pet == player.activePet) ? " ✅ 当前" : "";
            System.out.println("  " + (i + 1) + ". " + pet.emoji + " " + pet.name +
                    " (Lv." + pet.level + ")" + active);
        }
        System.out.print("请选择: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            choice = -1;
        }
        player.switchPet(choice);
    }

    // ===== 喂食宠物 =====
    private void feedPet() {
        if (player.activePet == null) {
            System.out.println("❌ 没有当前宠物！");
            return;
        }
        player.activePet.increaseLoyalty(10);
        System.out.println("❤️ " + player.activePet.name + " 的忠诚度 +10！");
        System.out.println("😊 当前忠诚度: " + player.activePet.loyalty + "%");
    }

    private void exploreScene() {
        System.out.println("\n🔍 你开始探索 " + world.currentScene.name + "...");

        // 随机事件
        int event = new java.util.Random().nextInt(100);
        if (event < 30) {
            // 发现物品
            System.out.println("🎁 你发现了一个宝箱！");
            int goldFound = 10 + new java.util.Random().nextInt(20);
            player.addGold(goldFound);
            System.out.println("💰 获得 " + goldFound + " 金币！");
        } else if (event < 60) {
            // 遇到怪物
            System.out.println("⚔️ 你遇到了怪物！");
            findMonster();
        } else if (event < 80) {
            // 发现线索
            System.out.println("📜 你发现了一些古老的文字...");
            System.out.println("  \"只有真正的勇者才能登上火山之巅\"");
        } else {
            System.out.println("🌿 这里没有什么特别的发现。");
        }
    }

    private void findMonster() {
        if (world.currentScene.isSafeZone) {
            System.out.println("🛡️ 这里是安全区域，不会有怪物。");
            return;
        }

        Monster monster = MonsterFactory.createRandomMonster(world.currentScene.name);
        battleSystem.startBattle(monster);
    }

    private void talkToNPC() {
        if (world.currentScene.npcs.isEmpty()) {
            System.out.println("💬 这里没有可以对话的人。");
            return;
        }

        System.out.println("\n💬 选择要对话的NPC：");
        for (int i = 0; i < world.currentScene.npcs.size(); i++) {
            NPC npc = world.currentScene.npcs.get(i);
            System.out.println("  " + (i + 1) + ". " + npc.name + " (" + npc.title + ")");
        }
        System.out.println("  0. 取消");
        System.out.print("请选择: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 0;
        }

        if (choice > 0 && choice <= world.currentScene.npcs.size()) {
            NPC npc = world.currentScene.npcs.get(choice - 1);
            npc.talk(player, scanner);
        }
    }

    private void travelMenu() {
        System.out.println("\n🗺️ 当前位置：" + world.currentScene.name);
        world.displayConnections();
        System.out.print("请选择: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 0;
        }

        if (choice > 0 && choice <= world.currentScene.connections.size()) {
            Scene target = world.currentScene.connections.get(choice - 1);

            // 路上可能遇敌
            if (!target.isSafeZone && new java.util.Random().nextInt(100) < 40) {
                System.out.println("⚔️ 路上遇到了怪物！");
                Monster monster = MonsterFactory.createRandomMonster(target.name);
                battleSystem.startBattle(monster);

                // 检查是否死亡
                if (!player.isAlive()) {
                    System.out.println("💀 你倒在了路上...");
                    return;
                }
            }

            world.moveTo(target);
        }
    }

    private void showStatus() {
        System.out.println("\n╔═══════════ 角色状态 ═══════════╗");
        System.out.println("║ 姓名: " + padRight("勇者", 15) + "║");
        System.out.println("║ 等级: " + padRight("" + player.level, 15) + "║");
        System.out.println("║ 生命: " + padRight(player.hp + "/" + player.maxHp, 15) + "║");
        System.out.println("║ 攻击: " + padRight("" + (player.attack + player.weaponBonus), 15) + "║");
        System.out.println("║ 防御: " + padRight("" + (player.defense + player.armorBonus), 15) + "║");
        System.out.println("║ 金币: " + padRight("" + player.gold, 15) + "║");
        System.out.println("║ 经验: " + padRight(player.exp + "/" + player.expToNextLevel, 15) + "║");
        System.out.println("║ 位置: " + padRight(world.currentScene.name, 15) + "║");
        System.out.println("║ 天赋: " + padRight(player.talent, 15) + "║");
        System.out.println("╚═══════════════════════════════════╝");

        if (player.critRate > 0) {
            System.out.println("💥 暴击率：" + player.critRate + "%");
        }
        if (player.lifesteal > 0) {
            System.out.println("🩸 吸血率：" + player.lifesteal + "%");
        }
        if (player.dodgeRate > 0) {
            System.out.println("💨 闪避率：" + player.dodgeRate + "%");
        }
    }

    private void showInventory() {
        System.out.println("\n📦 ===== 背包 =====");
        System.out.println("💰 金币：" + player.gold);
        System.out.println("💊 生命药水：" + player.lifePotions + "瓶");
        System.out.println("🧪 力量药剂：" + player.strengthPotions + "瓶");
        System.out.println("⚔️ 武器：" + player.weaponName + " (+" + player.weaponBonus + ")");
        System.out.println("🛡️ 防具：" + player.armorName + " (+" + player.armorBonus + ")");

        if (player.hasSteelSword) {
            System.out.println("  ✅ 钢剑 (攻击+5)");
        }
        if (player.hasDragonSword) {
            System.out.println("  ✅ 龙牙剑 (攻击+12)");
        }
        if (player.hasChainMail) {
            System.out.println("  ✅ 锁甲 (防御+3)");
        }
        if (player.hasDragonArmor) {
            System.out.println("  ✅ 龙鳞甲 (防御+8)");
        }
    }

    private void chooseTalent() {
        System.out.println("\n🌟 ===== 选择你的天赋 ===== 🌟");
        System.out.println("1. ❤️ 生命之路 - 生命+30");
        System.out.println("2. ⚔️ 力量之路 - 攻击+5");
        System.out.println("3. 🛡️ 防御之路 - 防御+3");
        System.out.println("4. 💥 暴击之路 - 暴击率+15%");
        System.out.println("5. 🩸 吸血之路 - 吸血10%");
        System.out.println("6. 🔥 火焰之路 - 火焰伤害5");
        System.out.println("7. 💨 敏捷之路 - 闪避10%");
        System.out.println("8. 🍀 幸运之路 - 经验+20%");
        System.out.print("请选择 (1-8): ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 1;
        }

        applyTalent(choice);
        System.out.println("🌟 天赋已确定！开始冒险吧！\n");    }
    private void applyTalent(int choice) {
        switch (choice) {
            case 1:
                player.talent = "❤️ 生命之路";
                player.maxHp += 30;
                player.hp = player.maxHp;
                System.out.println("❤️ 选择【生命之路】！生命上限+30！");
                break;
            case 2:
                player.talent = "⚔️ 力量之路";
                player.attack += 5;
                System.out.println("⚔️ 选择【力量之路】！攻击力+5！");
                break;
            case 3:
                player.talent = "🛡️ 防御之路";
                player.defense += 3;
                System.out.println("🛡️ 选择【防御之路】！防御力+3！");
                break;
            case 4:
                player.talent = "💥 暴击之路";
                player.critRate = 15;
                System.out.println("💥 选择【暴击之路】！暴击率+15%！");
                break;
            case 5:
                player.talent = "🩸 吸血之路";
                player.lifesteal = 10;
                System.out.println("🩸 选择【吸血之路】！吸血10%！");
                break;
            case 6:
                player.talent = "🔥 火焰之路";
                player.fireDamage = 5;
                System.out.println("🔥 选择【火焰之路】！火焰伤害+5！");
                break;
            case 7:
                player.talent = "💨 敏捷之路";
                player.dodgeRate = 10;
                System.out.println("💨 选择【敏捷之路】！闪避率+10%！");
                break;
            case 8:
                player.talent = "🍀 幸运之路";
                player.expBonus = 20;
                System.out.println("🍀 选择【幸运之路】！经验获取+20%！");
                break;
            default:
                player.talent = "❤️ 生命之路";
                player.maxHp += 30;
                player.hp = player.maxHp;
                System.out.println("❤️ 自动选择【生命之路】！生命上限+30！");
                break;
        }
    }

    private void saveGame() {
        System.out.println("💾 游戏保存功能 (待实现)");
    }

    private String padRight(String s, int length) {
        if (s.length() >= length) return s.substring(0, length);
        return s + " ".repeat(length - s.length());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

