public final class Npc extends Entity
{

	private Model method450()
	{
		int currAnim = -1;
		int nextAnim = -1;
		int currCycle = -1;
		int nextCycle = -1;
		if(super.anim >= 0 && super.anInt1529 == 0) {
			Animation seq = Animation.anims[super.anim];
			if (Client.enableTweening && super.nextAnimFrame != -1) {
				nextAnim = seq.anIntArray353[super.nextAnimFrame];
				currCycle = seq.anIntArray355[super.anInt1527];
				nextCycle = super.anInt1528;
			}
			currAnim = seq.anIntArray353[super.anInt1527];
			int idleAnim = -1;
			if(super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511) {
				idleAnim = Animation.anims[super.anInt1517].anIntArray353[super.anInt1518];
			}
			return desc.method164(idleAnim, currAnim, nextAnim, currCycle, nextCycle, Animation.anims[super.anim].anIntArray357);
		}
		if(super.anInt1517 >= 0) {
			final Animation seq = Animation.anims[super.anInt1517];
			currAnim = seq.anIntArray353[super.anInt1518];
			if (Client.enableTweening && super.nextIdleAnimFrame != -1) {
				nextAnim = seq.anIntArray353[super.nextIdleAnimFrame];
				currCycle = seq.anIntArray355[super.anInt1518];
				nextCycle = super.anInt1519;
			}
		}
		return desc.method164(-1, currAnim, nextAnim, currCycle, nextCycle, null);
	}

	public Model getRotatedModel()
	{
		if(desc == null)
			return null;
		Model model = method450();
		if(model == null)
			return null;
		super.height = model.modelHeight;
		if(super.anInt1520 != -1 && super.anInt1521 != -1)
		{
			SpotAnimDefinition spotAnim = SpotAnimDefinition.cache[super.anInt1520];
			Model model_1 = spotAnim.getModel();
			if(model_1 != null)
			{
				int j = spotAnim.aAnimation_407.anIntArray353[super.anInt1521];
				Model model_2 = new Model(true, Class36.method532(j), false, model_1);
				model_2.translate(0, -super.anInt1524, 0);
				model_2.createBones();
				model_2.applyAnimation(j);
				model_2.triangleSkin = null;
				model_2.vertexSkin = null;
				if(spotAnim.anInt410 != 128 || spotAnim.anInt411 != 128)
					model_2.scale(spotAnim.anInt410, spotAnim.anInt410, spotAnim.anInt411);
				model_2.method479(64 + spotAnim.anInt413, 850 + spotAnim.anInt414, -30, -50, -30, true);
				Model aModel[] = {
						model, model_2
				};
				model = new Model(aModel);
			}
		}
		if(desc.aByte68 == 1)
			model.singleSquare = true;
		return model;
	}

	public boolean isVisible()
	{
		return desc != null;
	}

	public Npc()
	{
	}

	public EntityDefinition desc;
}
