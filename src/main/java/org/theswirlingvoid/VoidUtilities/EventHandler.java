package org.theswirlingvoid.VoidUtilities;

import java.util.ArrayList;
import java.util.Collection;

import org.theswirlingvoid.VoidUtilities.effects.EffectHandler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber
public class EventHandler {
@SubscribeEvent
public static void onLivingKB(LivingKnockBackEvent event) {
	LivingEntity entity=event.getEntityLiving();
	double val=computeValue(entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE));
	if (val<0) {
		event.setStrength((float)(event.getStrength()*(1-val)));
	}
}
@SubscribeEvent
public static void onLivingHurt(LivingHurtEvent event) {
	if (event.getEntityLiving().getActivePotionEffect(EffectHandler.fragile)!=null) {
		float dmgmodifier=event.getEntityLiving().getActivePotionEffect(EffectHandler.fragile).getAmplifier()*0.5f+1.5f;
		event.setAmount(dmgmodifier*event.getAmount());
	}
}
private static double computeValue(IAttributeInstance attr) {
    double d0 = attr.getBaseValue();
    Collection<AttributeModifier> modifiers=attr.getModifiers();
    ArrayList<AttributeModifier> modifiers0=new ArrayList<AttributeModifier>();
    ArrayList<AttributeModifier> modifiers1=new ArrayList<AttributeModifier>();
    ArrayList<AttributeModifier> modifiers2=new ArrayList<AttributeModifier>();
    modifiers.forEach(modif->{
    	if (modif.getOperation().equals(Operation.ADDITION)) {
    		modifiers0.add(modif);
    	}
    	if (modif.getOperation().equals(Operation.MULTIPLY_BASE)) {
    		modifiers1.add(modif);
    	}
    	if (modif.getOperation().equals(Operation.MULTIPLY_TOTAL)) {
    		modifiers2.add(modif);
    	}
    });

    for(AttributeModifier attributemodifier : modifiers0) {
       d0 += attributemodifier.getAmount();
    }

    double d1 = d0;

    for(AttributeModifier attributemodifier1 : modifiers1) {
       d1 += d0 * attributemodifier1.getAmount();
    }

    for(AttributeModifier attributemodifier2 : modifiers2) {
       d1 *= 1.0D + attributemodifier2.getAmount();
    }
    return d1;
}

}
