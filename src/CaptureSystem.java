import java.util.Random;
import java.util.Scanner;

public class CaptureSystem {
    private Random random;

    public CaptureSystem() {
        this.random = new Random();
    }

    // ===== 尝试捕捉 =====
    public boolean attemptCapture(Player player, Monster monster, Scanner scanner) {
        if (!monster.canCapture()) {
            System.out.println("❌ 这个怪物无法被捕捉！");
            return false;
        }

        if (player.pets.size() >= player.maxPets) {
            System.out.println("❌ 你的宠物已满！");
            return false;
        }

        System.out.println("\n🎯 你试图捕捉 " + monster.name + "...");
        System.out.println("📊 捕捉率: " + monster.getCurrentCaptureRate() + "%");
        System.out.print("确认捕捉？(y/n): ");

        String input = scanner.nextLine().trim().toLowerCase();
        if (!input.equals("y") && !input.equals("yes")) {
            System.out.println("❌ 取消捕捉。");
            return false;
        }

        int rate = monster.getCurrentCaptureRate();
        if (random.nextInt(100) < rate) {
            // 捕捉成功！
            Pet newPet = createPetFromMonster(monster);
            player.addPet(newPet);
            System.out.println("🎉 捕捉成功！" + newPet.emoji + " " + newPet.name + " 加入了你的队伍！");
            return true;
        } else {
            System.out.println("💢 捕捉失败！" + monster.name + " 挣脱了！");
            return false;
        }
    }

    // ===== 从怪物创建宠物 =====
    private Pet createPetFromMonster(Monster monster) {
        // 根据怪物名称确定宠物类型
        String emoji = getPetEmoji(monster.name);
        String type = getPetType(monster.name);
        String skillName = getPetSkill(monster.name);
        int skillDamage = 8 + random.nextInt(8);

        // 属性根据怪物强度调整
        int hp = 25 + random.nextInt(15);
        int attack = 3 + random.nextInt(5);
        int defense = 1 + random.nextInt(4);

        return new Pet(monster.petName, type, emoji, hp, attack, defense, skillName, skillDamage);
    }

    // ===== 获取宠物表情 =====
    private String getPetEmoji(String monsterName) {
        if (monsterName.contains("狼")) return "🐺";
        if (monsterName.contains("鹰")) return "🦅";
        if (monsterName.contains("龙") || monsterName.contains("龙")) return "🔥";
        if (monsterName.contains("蝙蝠")) return "🦇";
        if (monsterName.contains("蜘蛛")) return "🕷️";
        if (monsterName.contains("蛇")) return "🐍";
        if (monsterName.contains("蝎")) return "🦂";
        if (monsterName.contains("幽灵")) return "👻";
        if (monsterName.contains("元素")) return "⚡";
        if (monsterName.contains("石")) return "🪨";
        return "🐾";
    }

    // ===== 获取宠物类型 =====
    private String getPetType(String monsterName) {
        if (monsterName.contains("狼") || monsterName.contains("蛇") || monsterName.contains("蜘蛛")) return "物理";
        if (monsterName.contains("鹰") || monsterName.contains("蝙蝠")) return "敏捷";
        if (monsterName.contains("龙")) return "火焰";
        if (monsterName.contains("幽灵")) return "暗影";
        if (monsterName.contains("元素")) return "雷电";
        if (monsterName.contains("蝎")) return "毒系";
        return "普通";
    }

    // ===== 获取宠物技能 =====
    private String getPetSkill(String monsterName) {
        if (monsterName.contains("狼")) return "撕咬";
        if (monsterName.contains("鹰")) return "俯冲";
        if (monsterName.contains("龙")) return "火焰吐息";
        if (monsterName.contains("蝙蝠")) return "吸血";
        if (monsterName.contains("蜘蛛")) return "毒液";
        if (monsterName.contains("蛇")) return "缠绕";
        if (monsterName.contains("幽灵")) return "暗影打击";
        if (monsterName.contains("元素")) return "闪电打击";
        return "普通攻击";
    }
}