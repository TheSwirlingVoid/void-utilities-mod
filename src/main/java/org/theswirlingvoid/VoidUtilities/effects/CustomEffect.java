package org.theswirlingvoid.VoidUtilities.effects;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class CustomEffect extends Effect {
	protected CustomEffect(final EffectType effectType, final int liquidColor) {
		super(effectType, liquidColor);
	}

	public CustomEffect(final EffectType effectType, final int liquidR, final int liquidG, final int liquidB) {
		this(effectType, new Color(liquidR, liquidG, liquidB).getRGB());
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier,
			double health) {
		// TODO Auto-generated method stub
		
		super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
	}
}
