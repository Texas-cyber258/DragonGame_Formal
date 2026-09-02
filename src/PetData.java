public class PetData {
    // ===== 预定义宠物列表 =====
    public static Pet[] PETS = {
            new Pet("幼狼", "物理", "🐺", 30, 5, 2, "撕咬", 8),
            new Pet("小鹰", "敏捷", "🦅", 25, 4, 1, "俯冲", 10),
            new Pet("小火龙", "火焰", "🔥", 35, 6, 3, "火焰吐息", 15),
            new Pet("冰精灵", "寒冰", "🧊", 28, 3, 4, "冰冻术", 12),
            new Pet("雷鸟", "雷电", "⚡", 30, 5, 2, "闪电打击", 14),
            new Pet("小石魔", "大地", "🪨", 40, 4, 5, "地震", 10),
            new Pet("水精灵", "水系", "💧", 32, 4, 3, "水柱", 11),
            new Pet("暗影猫", "暗影", "🐱", 27, 6, 1, "暗影突袭", 13)
    };

    // ===== 获取随机宠物 =====
    public static Pet getRandomPet() {
        java.util.Random random = new java.util.Random();
        Pet template = PETS[random.nextInt(PETS.length)];
        return new Pet(template.name, template.type, template.emoji,
                template.maxHp, template.attack, template.defense,
                template.skillName, template.skillDamage);
    }

    // ===== 根据名字获取宠物 =====
    public static Pet getPetByName(String name) {
        for (Pet pet : PETS) {
            if (pet.name.equals(name)) {
                return new Pet(pet.name, pet.type, pet.emoji,
                        pet.maxHp, pet.attack, pet.defense,
                        pet.skillName, pet.skillDamage);
            }
        }
        return null;
    }
}