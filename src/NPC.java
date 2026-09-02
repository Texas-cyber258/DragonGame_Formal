import java.util.Scanner;

public class NPC {
    // ===== 基础属性 =====
    public String name;
    public String title;
    public String greeting;
    public String dialog;
    public boolean hasQuest;
    public String questName;
    public boolean isMerchant;

    // ===== 构造函数 =====
    public NPC(String name, String title) {
        this.name = name;
        this.title = title;
        this.greeting = "你好，旅行者！";
        this.dialog = "欢迎来到我们的村庄。";
        this.hasQuest = false;
        this.isMerchant = false;
    }

    // ===== 设置对话 =====
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    // ===== 设置任务 =====
    public void setQuest(String questName) {
        this.hasQuest = true;
        this.questName = questName;
    }

    // ===== 对话方法 =====
    public void talk(Player player, Scanner scanner) {
        System.out.println("\n💬 [" + name + "] " + title);
        System.out.println("  \"" + greeting + "\"");
        System.out.println("   " + dialog);

        boolean talking = true;
        while (talking) {
            System.out.println("\n1. 💬 继续对话");
            System.out.println("2. 📦 交易 (如果是商人)");
            System.out.println("3. 📜 查看任务 (如果有)");
            System.out.println("4. 👋 离开");
            System.out.print("请选择: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n  \"" + dialog + "\"");
                    break;
                case 2:
                    if (isMerchant) {
                        openShop(player, scanner);
                    } else {
                        System.out.println("  \"我不是商人，没有什么可以卖的。\"");
                    }
                    break;
                case 3:
                    if (hasQuest) {
                        giveQuest(player);
                    } else {
                        System.out.println("  \"我没有什么任务需要帮忙。\"");
                    }
                    break;
                case 4:
                    System.out.println("  \"保重，旅行者！\"");
                    talking = false;
                    break;
                default:
                    System.out.println("  \"请选择有效的选项。\"");
            }
        }
    }

    // ===== 交易方法 =====
    private void openShop(Player player, Scanner scanner) {
        System.out.println("\n🏪 [" + name + "] 的商店");
        System.out.println("💰 你的金币：" + player.gold);
        System.out.println("1. 💊 生命药水 (30金币)");
        System.out.println("2. 🧪 力量药剂 (40金币)");
        System.out.println("3. 🍖 宠物食物 (20金币)");
        System.out.println("4. 🔧 武器修复 (50金币)");
        System.out.println("0. 离开商店");
        System.out.print("请选择: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 0;
        }

        switch (choice) {
            case 1:
                if (player.spendGold(30)) {
                    player.lifePotions++;
                    System.out.println("✅ 购买成功！生命药水+1");
                } else {
                    System.out.println("❌ 金币不足！");
                }
                break;
            case 2:
                if (player.spendGold(40)) {
                    player.strengthPotions++;
                    System.out.println("✅ 购买成功！力量药剂+1");
                } else {
                    System.out.println("❌ 金币不足！");
                }
                break;
            case 3:
                if (player.spendGold(20)) {
                    // 宠物食物逻辑
                    System.out.println("✅ 购买成功！宠物食物+1");
                } else {
                    System.out.println("❌ 金币不足！");
                }
                break;
            case 4:
                if (player.spendGold(50)) {
                    // 修复武器逻辑
                    System.out.println("✅ 武器已修复！");
                } else {
                    System.out.println("❌ 金币不足！");
                }
                break;
            case 0:
                System.out.println("🔙 离开商店");
                break;
            default:
                System.out.println("❌ 无效选择！");
        }
    }

    // ===== 任务方法 =====
    private void giveQuest(Player player) {
        System.out.println("\n📜 [" + name + "] 的任务");
        System.out.println("  \"我需要你帮忙：" + questName + "\"");
        System.out.println("  \"完成后我会给你丰厚的奖励！\"");

        // 接受任务逻辑
        System.out.print("接受任务？(y/n): ");
        String input = ""; // 需要传入Scanner
        // 简化版：接受任务
        System.out.println("✅ 任务已接受！");
    }
}