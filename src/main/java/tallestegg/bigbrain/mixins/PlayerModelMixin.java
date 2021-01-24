package tallestegg.bigbrain.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.bigbrain.entity.IBucklerUser;
import tallestegg.bigbrain.items.BucklerItem;

@Mixin(PlayerModel.class)
public class PlayerModelMixin<T extends LivingEntity> extends BipedModel<T> {
    public PlayerModelMixin(float p_i1148_1_) {
        super(p_i1148_1_);
    }

    @Shadow
    @Final
    public ModelRenderer bipedLeftArmwear;

    @Shadow
    @Final
    public ModelRenderer bipedRightArmwear;

    @Inject(at = @At("TAIL"), method = "setRotationAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V")
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        if (entityIn instanceof IBucklerUser) {
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.bucklerAnimationsRightArm(Hand.MAIN_HAND, entityIn);
                this.bucklerAnimationsLeftArm(Hand.OFF_HAND, entityIn);
            } else {
                this.bucklerAnimationsLeftArm(Hand.MAIN_HAND, entityIn);
                this.bucklerAnimationsRightArm(Hand.OFF_HAND, entityIn);
            }
        }
    }

    public void bucklerAnimationsLeftArm(Hand hand, T entityIn) {
        if (entityIn.getActiveHand() == hand && entityIn.getHeldItem(entityIn.getActiveHand()).getItem() instanceof BucklerItem) {
            float useDuration = (float) entityIn.getHeldItem(entityIn.getActiveHand()).getUseDuration();
            float useDurationClamped = MathHelper.clamp((float) entityIn.getItemInUseMaxCount(), 0.0F, useDuration);
            float result = useDurationClamped / useDuration;
            bipedLeftArm.rotateAngleY = MathHelper.lerp(result, bipedLeftArm.rotateAngleY, (float) Math.PI / 3F);
            bipedLeftArm.rotateAngleX = MathHelper.lerp(result, bipedLeftArm.rotateAngleX, this.bipedLeftArm.rotateAngleX * 0.1F - 1.5F);
        }
        if (((IBucklerUser) entityIn).isCharging() && BucklerItem.isReady(entityIn.getHeldItem(hand))) {
            if (!(entityIn instanceof AbstractPiglinEntity)) {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - (float) Math.PI;
                this.bipedRightArm.rotateAngleY = 0.0F;
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedRightArm.rotateAngleY = ((float) Math.PI / 6F);
            }
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.1F - 1.5F;
            this.bipedLeftArm.rotateAngleY = ((float) Math.PI / 3F);
        }
    }

    public void bucklerAnimationsRightArm(Hand hand, T entityIn) {
        if (entityIn.getActiveHand() == hand && entityIn.getHeldItem(entityIn.getActiveHand()).getItem() instanceof BucklerItem) {
            float useDuration = (float) entityIn.getHeldItem(entityIn.getActiveHand()).getUseDuration();
            float useDurationClamped = MathHelper.clamp((float) entityIn.getItemInUseMaxCount(), 0.0F, useDuration);
            float result = useDurationClamped / useDuration;
            bipedRightArm.rotateAngleY = MathHelper.lerp(result, bipedRightArm.rotateAngleY, -(float) Math.PI / 3F);
            bipedRightArm.rotateAngleX = MathHelper.lerp(result, bipedRightArm.rotateAngleX, this.bipedRightArm.rotateAngleX * 0.1F - 1.5F);
        }
        if (((IBucklerUser) entityIn).isCharging() && BucklerItem.isReady(entityIn.getHeldItem(hand))) {
            if (!(entityIn instanceof AbstractPiglinEntity)) {
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - (float) Math.PI;
                this.bipedLeftArm.rotateAngleY = 0.0F;
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedLeftArm.rotateAngleY = -(float) Math.PI / 6F;
            }
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.1F - 1.5F;
            this.bipedRightArm.rotateAngleY = -(float) Math.PI / 3F;
        }
    }
}
