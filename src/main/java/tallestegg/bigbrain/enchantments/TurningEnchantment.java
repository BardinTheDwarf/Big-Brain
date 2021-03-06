package tallestegg.bigbrain.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import tallestegg.bigbrain.BigBrainEnchantments;

public class TurningEnchantment extends Enchantment {
    public TurningEnchantment(Enchantment.Rarity rarity, EquipmentSlotType... slots) {
        super(rarity, BigBrainEnchantments.BUCKLER, slots);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public boolean canGenerateInLoot() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return ench instanceof BangEnchantment ? false : super.canApplyTogether(ench);
    }
}
