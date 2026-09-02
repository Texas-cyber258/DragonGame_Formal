import java.util.Random;

public class MonsterFactory {
    private static Random random = new Random();

    public static Monster createMonster(String monsterName) {
        switch (monsterName) {
            // ===== 森林怪物 =====
            case "🐺 森林狼":
                return new Monster("🐺 森林狼", 30, 5, 8, 15, 10);
            case "🐗 野猪":
                return new Monster("🐗 野猪", 40, 6, 10, 18, 12);
            case "🦅 巨鹰":
                return new Monster("🦅 巨鹰", 25, 7, 12, 20, 15);

            // ===== 洞穴怪物 =====
            case "🦇 吸血蝙蝠":
                return new Monster("🦇 吸血蝙蝠", 20, 4, 7, 10, 8);
            case "🕷️ 巨型蜘蛛":
                return new Monster("🕷️ 巨型蜘蛛", 35, 5, 9, 15, 12);
            case "🧟 骷髅战士":
                return new Monster("🧟 骷髅战士", 45, 8, 12, 20, 16);

            // ===== 沙漠怪物 =====
            case "🦂 毒蝎":
                return new Monster("🦂 毒蝎", 30, 6, 10, 16, 14);
            case "🐍 沙漠蟒蛇":
                return new Monster("🐍 沙漠蟒蛇", 40, 7, 11, 18, 15);
            case "🏜️ 沙虫":
                return new Monster("🏜️ 沙虫", 50, 8, 14, 22, 20);

            // ===== 矿洞怪物 =====
            case "⛏️ 石巨人":
                return new Monster("⛏️ 石巨人", 60, 9, 13, 25, 22);
            case "🦇 暗影蝙蝠":
                return new Monster("🦇 暗影蝙蝠", 25, 5, 8, 12, 10);

            // ===== 神殿怪物 =====
            case "👻 幽灵":
                return new Monster("👻 幽灵", 30, 6, 10, 18, 14);
            case "⚡ 雷电元素":
                return new Monster("⚡ 雷电元素", 35, 8, 13, 20, 16);

            default:
                // 默认怪物
                return new Monster("🐉 小龙", 50, 5, 10, 20, 15);
        }
    }

    public static Monster createRandomMonster(String sceneName) {
        // 根据场景生成随机怪物
        switch (sceneName) {
            case "🌲 迷雾森林":
                String[] forestMonsters = {"🐺 森林狼", "🐗 野猪", "🦅 巨鹰"};
                return createMonster(forestMonsters[random.nextInt(forestMonsters.length)]);
            case "🏔️ 暗影洞穴":
                String[] caveMonsters = {"🦇 吸血蝙蝠", "🕷️ 巨型蜘蛛", "🧟 骷髅战士"};
                return createMonster(caveMonsters[random.nextInt(caveMonsters.length)]);
            case "🏜️ 沙漠遗迹":
                String[] desertMonsters = {"🦂 毒蝎", "🐍 沙漠蟒蛇", "🏜️ 沙虫"};
                return createMonster(desertMonsters[random.nextInt(desertMonsters.length)]);
            case "⛰️ 废弃矿洞":
                String[] mineMonsters = {"⛏️ 石巨人", "🦇 暗影蝙蝠"};
                return createMonster(mineMonsters[random.nextInt(mineMonsters.length)]);
            case "🏛️ 古老神殿":
                String[] templeMonsters = {"👻 幽灵", "⚡ 雷电元素"};
                return createMonster(templeMonsters[random.nextInt(templeMonsters.length)]);
            default:
                return createMonster("🐺 森林狼");
        }
    }
}