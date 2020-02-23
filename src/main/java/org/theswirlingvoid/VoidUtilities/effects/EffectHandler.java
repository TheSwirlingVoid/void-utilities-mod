package org.theswirlingvoid.VoidUtilities.effects;

import org.theswirlingvoid.VoidUtilities.Main;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MODID)
public class EffectHandler {
	public static final CustomEffect light = (CustomEffect) new CustomEffect(EffectType.HARMFUL, 255,255,255).setRegistryName("light").addAttributesModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, "7407DE5E-7CE8-4030-940E-514C1F160890", -1, AttributeModifier.Operation.ADDITION);
	public static final CustomEffect fragile = (CustomEffect) new CustomEffect(EffectType.HARMFUL, 255,255,255).setRegistryName("fragile");
	public static final MyopiaEffect myopia = (MyopiaEffect) new MyopiaEffect(EffectType.HARMFUL, 0,0,0).setRegistryName("myopia").addAttributesModifier(SharedMonsterAttributes.FOLLOW_RANGE, "7408DE5E-7CE8-4030-940E-514C1F160890", -0.5, AttributeModifier.Operation.MULTIPLY_BASE);
//	public static final CustomEffect fragile = Main.Null();
//	public static final CustomEffect myopia = Main.Null();
}
