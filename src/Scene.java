import java.util.ArrayList;
import java.util.List;

public class Scene {
    // ===== 基础属性 =====
    public String name;           // 场景名称
    public String description;    // 场景描述
    public SceneType type;        // 场景类型
    public List<String> monsters; // 可遇到的怪物名称
    public List<NPC> npcs;        // 场景中的NPC
    public List<Scene> connections; // 连接的其他场景
    public boolean isSafeZone;    // 是否安全区（无战斗）

    // ===== 构造函数 =====
    public Scene(String name, String description, SceneType type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.monsters = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.isSafeZone = false;
    }

    // ===== 添加怪物 =====
    public void addMonster(String monsterName) {
        this.monsters.add(monsterName);
    }

    // ===== 添加NPC =====
    public void addNPC(NPC npc) {
        this.npcs.add(npc);
    }

    // ===== 添加连接场景 =====
    public void addConnection(Scene scene) {
        this.connections.add(scene);
    }

    // ===== 获取随机怪物 =====
    public String getRandomMonster() {
        if (monsters.isEmpty()) return null;
        java.util.Random random = new java.util.Random();
        return monsters.get(random.nextInt(monsters.size()));
    }

    // ===== 显示场景信息 =====
    public void display() {
        System.out.println("📍 ===== " + name + " =====");
        System.out.println(description);
        if (isSafeZone) {
            System.out.println("🛡️ 安全区域 - 不会遭遇怪物");
        }
        if (!npcs.isEmpty()) {
            System.out.println("💬 这里有 " + npcs.size() + " 位NPC");
        }
        if (!monsters.isEmpty()) {
            System.out.println("⚔️ 这里有 " + monsters.size() + " 种怪物出没");
        }
    }
}

// ===== 场景类型枚举 =====
enum SceneType {
    CITY,       // 城市
    FOREST,     // 森林
    CAVE,       // 洞穴
    DESERT,     // 沙漠
    VOLCANO,    // 火山
    MOUNTAIN,   // 山地
    RUIN,       // 废墟
    LAKE,       // 湖泊
    TEMPLE      // 神殿
}
