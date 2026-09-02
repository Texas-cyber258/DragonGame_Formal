public class BossData {
    public static Monster[] BOSSES = {
            new Monster("🐉 幼龙", 100, 10, 15, 25, 20, true),
            new Monster("🐲 成年龙", 180, 15, 22, 40, 35, true),
            new Monster("🐉 远古巨龙", 300, 25, 35, 60, 60, true)
    };

    public static String[] BOSS_NAMES = {"🐉 幼龙", "🐲 成年龙", "🐉 远古巨龙"};
    public static int[] BOSS_HP = {100, 180, 300};
    public static int[] BOSS_MIN_ATTACK = {10, 15, 25};
    public static int[] BOSS_MAX_ATTACK = {15, 22, 35};
    public static int[] BOSS_GOLD = {20, 35, 60};
    public static int[] BOSS_EXP = {25, 40, 60};
}