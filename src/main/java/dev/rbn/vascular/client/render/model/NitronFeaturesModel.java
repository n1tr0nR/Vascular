package dev.rbn.vascular.client.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.joml.Quaternionf;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class NitronFeaturesModel extends EntityModel<PlayerEntityRenderState> {
	public final ModelPart Waist;
	public final ModelPart Head;
	public final ModelPart horns;
	public final ModelPart bow;
	public final ModelPart Body;
	public final ModelPart tail;
	public final ModelPart breasts;
	public final ModelPart hood;
	public final ModelPart RightLeg;
	public final ModelPart LeftLeg;
	public NitronFeaturesModel(ModelPart root) {
        super(root);
        this.Waist = root.getChild("Waist");
		this.Head = this.Waist.getChild("Head");
		this.horns = this.Head.getChild("horns");
		this.bow = this.Head.getChild("bow");
		this.Body = this.Waist.getChild("Body");
		this.tail = this.Body.getChild("tail");
		this.breasts = this.Body.getChild("breasts");
		this.hood = this.Body.getChild("hood");
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Waist = modelPartData.addChild("Waist", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 12.0F, 0.0F));

		ModelPartData Head = Waist.addChild("Head", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -12.0F, 0.0F));

		ModelPartData horns = Head.addChild("horns", ModelPartBuilder.create().uv(17, 15).cuboid(-4.0F, -28.0F, 0.0F, 8.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 16.0F, -1.0F));

		ModelPartData bow = Head.addChild("bow", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -5.0F, 4.25F));

		ModelPartData Head_r1 = bow.addChild("Head_r1", ModelPartBuilder.create().uv(0, 26).cuboid(-2.0F, -1.0F, -0.5F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData Head_r2 = bow.addChild("Head_r2", ModelPartBuilder.create().uv(17, 20).mirrored().cuboid(-3.0F, -2.0F, -0.5F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, -2.0F, 0.0F, 0.3631F, -0.3864F, -0.0718F));

		ModelPartData Head_r3 = bow.addChild("Head_r3", ModelPartBuilder.create().uv(17, 20).cuboid(-1.0F, -2.0F, -0.5F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -2.0F, 0.0F, 0.3631F, 0.3864F, 0.0718F));

		ModelPartData Head_r4 = bow.addChild("Head_r4", ModelPartBuilder.create().uv(23, 0).cuboid(-3.0F, -1.0F, 0.5F, 6.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData Body = Waist.addChild("Body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -12.0F, 0.0F));

		ModelPartData tail = Body.addChild("tail", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 12.0F, 2.0F));

		ModelPartData Body_r1 = tail.addChild("Body_r1", ModelPartBuilder.create().uv(0, 15).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3919F, -0.0114F, 0.0013F));

		ModelPartData breasts = Body.addChild("breasts", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 2.75F, -2.0F));

		ModelPartData Body_r2 = breasts.addChild("Body_r2", ModelPartBuilder.create().uv(0, 8).cuboid(-4.0F, -1.5F, -1.5F, 8.0F, 3.0F, 3.0F, new Dilation(-0.002F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData hood = Body.addChild("hood", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 3.0F, 2.0F));

		ModelPartData hood_r1 = hood.addChild("hood_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -2.0F, -1.0F, 8.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ModelPartData RightLeg = modelPartData.addChild("RightLeg", ModelPartBuilder.create(), ModelTransform.origin(-1.9F, 12.0F, 0.0F));

		ModelPartData RightLeg_r1 = RightLeg.addChild("RightLeg_r1", ModelPartBuilder.create().uv(23, 6).cuboid(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 9.0F, -1.75F, 0.1309F, 0.0F, 0.0F));

		ModelPartData LeftLeg = modelPartData.addChild("LeftLeg", ModelPartBuilder.create(), ModelTransform.origin(1.9F, 12.0F, 0.0F));

		ModelPartData LeftLeg_r1 = LeftLeg.addChild("LeftLeg_r1", ModelPartBuilder.create().uv(23, 6).mirrored().cuboid(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 9.0F, -1.75F, 0.1309F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(PlayerEntityRenderState state) {

	}
}