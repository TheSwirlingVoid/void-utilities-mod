package org.theswirlingvoid.VoidUtilities.effects;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class MyopiaEffect extends Effect {
	protected MyopiaEffect(final EffectType effectType, final int liquidColor) {
		super(effectType, liquidColor);
	}

	public MyopiaEffect(final EffectType effectType, final int liquidR, final int liquidG, final int liquidB) {
		this(effectType, new Color(liquidR, liquidG, liquidB).getRGB());
	}

	@Override
	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
		// TODO Auto-generated method stub
		return Math.pow(1+modifier.getAmount(),amplifier+1)-1;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier,
			double health) {
		// TODO Auto-generated method stub
		super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
	}
}
