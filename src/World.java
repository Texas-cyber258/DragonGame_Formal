import java.util.HashMap;
import java.util.Map;

public class World {
    public Map<String, Scene> scenes;
    public Scene currentScene;

    public World() {
        this.scenes = new HashMap<>();
        createWorld();
    }

    private void createWorld() {
        // ===== 创建场景 =====
        Scene homeTown = new Scene("🏠 新手村", "宁静祥和的村庄，冒险者的起点。", SceneType.CITY);
        homeTown.isSafeZone = true;

        Scene forest = new Scene("🌲 迷雾森林", "树木茂密，终年笼罩着迷雾。", SceneType.FOREST);
        Scene cave = new Scene("🏔️ 暗影洞穴", "黑暗的洞穴，隐藏着危险。", SceneType.CAVE);
        Scene desert = new Scene("🏜️ 沙漠遗迹", "远古文明的废墟，黄沙之下埋藏着秘密。", SceneType.DESERT);
        Scene volcano = new Scene("🌋 火山之巅", "恶龙的老巢，炽热的熔岩之巅。", SceneType.VOLCANO);
        Scene mine = new Scene("⛰️ 废弃矿洞", "曾经的富矿，现在被怪物占据。", SceneType.CAVE);
        Scene temple = new Scene("🏛️ 古老神殿", "供奉着古代神明的殿堂。", SceneType.TEMPLE);

        // ===== 添加怪物 =====
        forest.addMonster("🐺 森林狼");
        forest.addMonster("🐗 野猪");
        forest.addMonster("🦅 巨鹰");

        cave.addMonster("🦇 吸血蝙蝠");
        cave.addMonster("🕷️ 巨型蜘蛛");
        cave.addMonster("🧟 骷髅战士");

        desert.addMonster("🦂 毒蝎");
        desert.addMonster("🐍 沙漠蟒蛇");
        desert.addMonster("🏜️ 沙虫");

        mine.addMonster("⛏️ 石巨人");
        mine.addMonster("🦇 暗影蝙蝠");

        temple.addMonster("👻 幽灵");
        temple.addMonster("⚡ 雷电元素");

        // ===== 添加NPC =====
        NPC elder = new NPC("🧙 村长", "新手村之长");
        elder.setGreeting("欢迎来到新手村，年轻的冒险者！");
        elder.setDialog("我听说北方有恶龙作乱，如果你能打败它，就是真正的勇者！");
        elder.setQuest("击败恶龙");

        NPC merchant = new NPC("🧑‍💼 商人", "流动商贩");
        merchant.setGreeting("欢迎光临！我的货物是最好的！");
        merchant.setDialog("这里什么都有，只要你有金币。");
        merchant.isMerchant = true;

        NPC blacksmith = new NPC("🔨 铁匠", "武器大师");
        blacksmith.setGreeting("需要打造武器吗？");
        blacksmith.setDialog("我有上好的钢铁，可以为你打造利剑。");
        blacksmith.isMerchant = true;

        NPC priest = new NPC("🙏 牧师", "神殿祭司");
        priest.setGreeting("愿光明指引你的道路。");
        priest.setDialog("神殿里有古老的传说，关于龙族的秘密。");

        // ===== 添加NPC到场景 =====
        homeTown.addNPC(elder);
        homeTown.addNPC(merchant);
        homeTown.addNPC(blacksmith);
        temple.addNPC(priest);

        // ===== 连接场景 =====
        homeTown.addConnection(forest);
        homeTown.addConnection(mine);

        forest.addConnection(homeTown);
        forest.addConnection(cave);
        forest.addConnection(temple);

        cave.addConnection(forest);
        cave.addConnection(desert);

        desert.addConnection(cave);
        desert.addConnection(volcano);

        mine.addConnection(homeTown);
        temple.addConnection(forest);
        volcano.addConnection(desert);

        // ===== 保存到地图 =====
        scenes.put("新手村", homeTown);
        scenes.put("迷雾森林", forest);
        scenes.put("暗影洞穴", cave);
        scenes.put("沙漠遗迹", desert);
        scenes.put("火山之巅", volcano);
        scenes.put("废弃矿洞", mine);
        scenes.put("古老神殿", temple);

        // ===== 设置当前位置 =====
        currentScene = homeTown;
    }

    public Scene getScene(String name) {
        return scenes.get(name);
    }

    public void moveTo(Scene scene) {
        if (scene != null) {
            this.currentScene = scene;
            System.out.println("📍 你来到了 " + scene.name);
        }
    }

    public void displayConnections() {
        System.out.println("\n🗺️ 可前往的区域：");
        int index = 1;
        for (Scene scene : currentScene.connections) {
            System.out.println("  " + index + ". " + scene.name);
            index++;
        }
        System.out.println("  0. 留在这里");
    }
}