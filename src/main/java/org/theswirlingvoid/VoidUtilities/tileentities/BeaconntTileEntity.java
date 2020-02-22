package org.theswirlingvoid.VoidUtilities.tileentities;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.effects.EffectHandler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LockCode;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BeaconntTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity, ISidedInventory {
   /** List of effects that Beacons can apply */
   public static final Effect[][] EFFECTS_LIST = new Effect[][]{{EffectHandler.light, Effects.SLOWNESS}, {Effects.WEAKNESS, EffectHandler.fragile}, {EffectHandler.myopia}, {Effects.GLOWING}};
   private static final Set<Effect> VALID_EFFECTS = Arrays.stream(EFFECTS_LIST).<Effect>flatMap(Arrays::stream).collect(Collectors.toSet());
   private List<BeaconntTileEntity.BeamSegment> beamSegments = Lists.newArrayList();
   private List<BeaconntTileEntity.BeamSegment> field_213934_g = Lists.newArrayList();
   private int levels = 0;
   private int field_213935_i = -1;
   protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
   /** Primary potion effect given by this beacon */
   @Nullable
   public Effect primaryEffect;
   /** Secondary potion effect given by this beacon. */
   @Nullable
   public Effect secondaryEffect;
   /** The custom name for this beacon. This was unused until 1.14; see https://bugs.mojang.com/browse/MC-124395 */
   @Nullable
   private ITextComponent customName;
   private LockCode field_213936_m = LockCode.EMPTY_CODE;
   private final IIntArray field_213937_n = new IIntArray() {
      public int get(int index) {
         switch(index) {
         case 0:
            return BeaconntTileEntity.this.levels;
         case 1:
            return Effect.getId(BeaconntTileEntity.this.primaryEffect);
         case 2:
            return Effect.getId(BeaconntTileEntity.this.secondaryEffect);
         default:
            return 0;
         }
      }

      public void set(int index, int value) {
         switch(index) {
         case 0:
            BeaconntTileEntity.this.levels = value;
            break;
         case 1:
            if (!BeaconntTileEntity.this.world.isRemote && !BeaconntTileEntity.this.beamSegments.isEmpty()) {
               BeaconntTileEntity.this.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT);
            }

            BeaconntTileEntity.this.primaryEffect = BeaconntTileEntity.isBeaconEffect(value);
            break;
         case 2:
            BeaconntTileEntity.this.secondaryEffect = BeaconntTileEntity.isBeaconEffect(value);
         }

      }

      public int size() {
         return 3;
      }
   };

   public BeaconntTileEntity() {
      super(RegistryEvents.beaconntTE);
   }

   public void tick() {
      int i = this.pos.getX();
      int j = this.pos.getY();
      int k = this.pos.getZ();
      BlockPos blockpos;
      if (this.field_213935_i < j) {
         blockpos = this.pos;
         this.field_213934_g = Lists.newArrayList();
         this.field_213935_i = blockpos.getY() + 1;
      } else {
         blockpos = new BlockPos(i, this.field_213935_i - 1, k);
      }

      BeaconntTileEntity.BeamSegment beacontileentity$beamsegment = this.field_213934_g.isEmpty() ? null : this.field_213934_g.get(this.field_213934_g.size() - 1);
      int l =0;

      for(int i1 = 0; blockpos.getY() >= l; --i1) {
         BlockState blockstate = this.world.getBlockState(blockpos);
         Block block = blockstate.getBlock();
         float[] afloat = blockstate.getBeaconColorMultiplier(this.world, blockpos, getPos());
         if (afloat != null) {
            if (this.field_213934_g.size() <= 1) {
               beacontileentity$beamsegment = new BeaconntTileEntity.BeamSegment(afloat);
               this.field_213934_g.add(beacontileentity$beamsegment);
            } else if (beacontileentity$beamsegment != null) {
               if (Arrays.equals(afloat, beacontileentity$beamsegment.colors)) {
                  beacontileentity$beamsegment.incrementHeight();
               } else {
                  beacontileentity$beamsegment = new BeaconntTileEntity.BeamSegment(new float[]{(beacontileentity$beamsegment.colors[0] + afloat[0]) / 2.0F, (beacontileentity$beamsegment.colors[1] + afloat[1]) / 2.0F, (beacontileentity$beamsegment.colors[2] + afloat[2]) / 2.0F});
                  this.field_213934_g.add(beacontileentity$beamsegment);
               }
            }
         } else {
            if (beacontileentity$beamsegment == null || blockstate.getOpacity(this.world, blockpos) >= 15 && block != Blocks.BEDROCK) {
               this.field_213934_g.clear();
               this.field_213935_i = l;
               break;
            }

            beacontileentity$beamsegment.incrementHeight();
         }

         blockpos = blockpos.down();
         --this.field_213935_i;
      }

      int j1 = this.levels;
      if (this.world.getGameTime() % 80L == 0L) {
         if (!this.beamSegments.isEmpty()) {
            this.func_213927_a(i, j, k);
         }

         if (this.levels > 0 && !this.beamSegments.isEmpty()) {
            this.addEffectsToEntities();
            this.playSound(SoundEvents.BLOCK_BEACON_AMBIENT);
         }
      }

      if (this.field_213935_i >= l) {
         this.field_213935_i = -1;
         boolean flag = j1 > 0;
         this.beamSegments = this.field_213934_g;
         if (!this.world.isRemote) {
            boolean flag1 = this.levels > 0;
            if (!flag && flag1) {
               this.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE);
            } else if (flag && !flag1) {
               this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
            }
         }
      }

   }

   private void func_213927_a(int p_213927_1_, int p_213927_2_, int p_213927_3_) {
      this.levels = 0;

      for(int i = 1; i <= 4; this.levels = i++) {
         int j = p_213927_2_ + i;
         if (j < 0) {
            break;
         }

         boolean flag = true;

         for(int k = p_213927_1_ - i; k <= p_213927_1_ + i && flag; ++k) {
            for(int l = p_213927_3_ - i; l <= p_213927_3_ + i; ++l) {
               if (!this.world.getBlockState(new BlockPos(k, j, l)).isBeaconBase(this.world, new BlockPos(k, j, l), getPos())) {
                  flag = false;
                  break;
               }
            }
         }

         if (!flag) {
            break;
         }
      }

   }

   /**
    * invalidates a tile entity
    */
   public void remove() {
      this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
      super.remove();
   }

   private void addEffectsToEntities() {
      if (!this.world.isRemote && this.primaryEffect != null) {
         double d0 = (double)(this.levels * 10 + 10);
         int i = 0;
         if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
            i = 1;
         }

         int j = (9 + this.levels * 2) * 20;
         AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.pos)).grow(d0).expand(0.0D, (double)this.world.getHeight(), 0.0D);
         List<MonsterEntity> list = this.world.getEntitiesWithinAABB(MonsterEntity.class, axisalignedbb);
         for(MonsterEntity livingentity : list) {
        	 if (this.primaryEffect==Effects.INSTANT_DAMAGE) {
        		 j= 1;
        	 }
            livingentity.addPotionEffect(new EffectInstance(this.primaryEffect, j, i, true, true));
         }

         if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect != null) {
            for(MonsterEntity livingentity1 : list) {
               livingentity1.addPotionEffect(new EffectInstance(this.secondaryEffect, j, 0, true, true));
            }
         }

      }
   }

   public void playSound(SoundEvent p_205736_1_) {
      this.world.playSound((PlayerEntity)null, this.pos, p_205736_1_, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }

   @OnlyIn(Dist.CLIENT)
   public List<BeaconntTileEntity.BeamSegment> getBeamSegments() {
      return (List<BeaconntTileEntity.BeamSegment>)(this.levels == 0 ? ImmutableList.of() : this.beamSegments);
   }

   public int getLevels() {
      return this.levels;
   }

   /**
    * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
    * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
    */
   @Nullable
   public SUpdateTileEntityPacket getUpdatePacket() {
      return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
   }

   /**
    * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
    * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
    */
   public CompoundNBT getUpdateTag() {
      return this.write(new CompoundNBT());
   }

   @OnlyIn(Dist.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 65536.0D;
   }

   @Override
public AxisAlignedBB getRenderBoundingBox() {
	// TODO Auto-generated method stub
	return INFINITE_EXTENT_AABB;
}

@Nullable
   private static Effect isBeaconEffect(int p_184279_0_) {
      Effect effect = Effect.get(p_184279_0_);
      return VALID_EFFECTS.contains(effect) ? effect : null;
   }

   public void read(CompoundNBT compound) {
      super.read(compound);
      this.primaryEffect = isBeaconEffect(compound.getInt("Primary"));
      this.secondaryEffect = isBeaconEffect(compound.getInt("Secondary"));
      if (compound.contains("CustomName", 8)) {
         this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
      }

      this.field_213936_m = LockCode.read(compound);
   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      compound.putInt("Primary", Effect.getId(this.primaryEffect));
      compound.putInt("Secondary", Effect.getId(this.secondaryEffect));
      compound.putInt("Levels", this.levels);
      if (this.customName != null) {
         compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
      }

      this.field_213936_m.write(compound);
      return compound;
   }

   /**
    * Sets the custom name for this beacon.
    */
   public void setCustomName(@Nullable ITextComponent aname) {
      this.customName = aname;
   }

   @Nullable
   public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
//	   return new BeaconntContainer(p_createMenu_1_, p_createMenu_2_);
	   return LockableTileEntity.canUnlock(p_createMenu_3_, this.field_213936_m, this.getDisplayName()) ? new BeaconntContainer(this.pos,p_createMenu_1_, p_createMenu_2_,this, this.field_213937_n, IWorldPosCallable.of(this.world, this.getPos())) : null;
   }

   public ITextComponent getDisplayName() {
      return (ITextComponent)(this.customName != null ? this.customName : new TranslationTextComponent("container.beacon"));
   }

   public static class BeamSegment {
      private final float[] colors;
      private int height;

      public BeamSegment(float[] colorsIn) {
         this.colors = colorsIn;
         this.height = 1;
      }

      protected void incrementHeight() {
         ++this.height;
      }

      /**
       * Returns RGB (0 to 1.0) colors of this beam segment
       */
      @OnlyIn(Dist.CLIENT)
      public float[] getColors() {
         return this.colors;
      }

      @OnlyIn(Dist.CLIENT)
      public int getHeight() {
         return this.height;
      }
   } 
   private ItemStackHandler handler;
   private ItemStackHandler getHandler()
	{
		if (handler == null)
		{
			handler = new ItemStackHandler(items)
					{
						@Override
						public boolean isItemValid(int slot, @Nonnull ItemStack stack)
						{
							return stack.isBeaconPayment();
						}
						@Override
						public ItemStack extractItem(int slot, int amount, boolean simulate) {
							// TODO Auto-generated method stub
							
							return super.extractItem(slot, amount, simulate);
						}
						@Nonnull
						@Override
						public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
						{
							return stack.isBeaconPayment()? stack: super.insertItem(slot, stack, simulate);
							
						}
					};
			
		}
		return handler;
	}
   @Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return LazyOptional.of(() -> (T) getHandler());
		}
		
		// TODO Auto-generated method stub
		return super.getCapability(cap, side);
	}
public int getSizeInventory() {
    return this.items.size();
 }

 public boolean isEmpty() {
    for(ItemStack itemstack : this.items) {
       if (!itemstack.isEmpty()) {
          return false;
       }
    }

    return true;
 }

 /**
  * Returns the stack in the given slot.
  */
 public ItemStack getStackInSlot(int index) {
    return this.items.get(index);
 }

 /**
  * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
  */
 public ItemStack decrStackSize(int index, int count) {
    return ItemStackHelper.getAndSplit(this.items, index, count);
 }

 /**
  * Removes a stack from the given slot and returns it.
  */
 public ItemStack removeStackFromSlot(int index) {
    return ItemStackHelper.getAndRemove(this.items, index);
 }

 /**
  * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
  */
 public void setInventorySlotContents(int index, ItemStack stack) {
    ItemStack itemstack = this.items.get(index);
   this.items.set(index, stack);

 }
 public void fillStackedContents(RecipeItemHelper helper) {
     for(ItemStack itemstack : this.items) {
        helper.accountStack(itemstack);
     }

  }
 /**
  * Don't rename this method to canInteractWith due to conflicts with Container
  */
 public boolean isUsableByPlayer(PlayerEntity player) {
    if (this.world.getTileEntity(this.pos) != this) {
       return false;
    } else {
       return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
 }

 /**
  * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
  * guis use Slot.isItemValid
  */
 public boolean isItemValidForSlot(int index, ItemStack stack) {
    return stack.isBeaconPayment();
 }

 public void clear() {
    this.items.clear();
 }

@Override
public boolean canExtractItem(int arg0, ItemStack arg1, Direction arg2) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean canInsertItem(int arg0, ItemStack arg1, Direction arg2) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public int[] getSlotsForFace(Direction arg0) {
	// TODO Auto-generated method stub
	return null;
}
}