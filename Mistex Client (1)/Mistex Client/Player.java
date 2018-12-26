public final class Player extends Entity {

	public Model getRotatedModel() {
		if(!visible)
			return null;
		Model model = method452();
		if(model == null)
			return null;
		super.height = model.modelHeight;
		model.singleSquare = true;
		if(aBoolean1699)
			return model;
		if(super.anInt1520 != -1 && super.anInt1521 != -1)
		{
			SpotAnimDefinition spotAnimDefinition = SpotAnimDefinition.cache[super.anInt1520];
			Model model_2 = spotAnimDefinition.getModel();
			if(model_2 != null)
			{
				Model model_3 = new Model(true, Class36.method532(super.anInt1521), false, model_2);
				model_3.translate(0, -super.anInt1524, 0);
				model_3.createBones();
				model_3.applyAnimation(spotAnimDefinition.aAnimation_407.anIntArray353[super.anInt1521]);
				model_3.triangleSkin = null;
				model_3.vertexSkin = null;
				if(spotAnimDefinition.anInt410 != 128 || spotAnimDefinition.anInt411 != 128)
					model_3.scale(spotAnimDefinition.anInt410, spotAnimDefinition.anInt410, spotAnimDefinition.anInt411);
				model_3.method479(84 + spotAnimDefinition.anInt413, 1550 + spotAnimDefinition.anInt414, -50, -110, -50, true);
				Model aclass30_sub2_sub4_sub6_1s[] = {
						model, model_3
				};
				model = new Model(aclass30_sub2_sub4_sub6_1s);
			}
		}
		if(aModel_1714 != null)
		{
			if(Client.loopCycle >= anInt1708)
				aModel_1714 = null;
			if(Client.loopCycle >= anInt1707 && Client.loopCycle < anInt1708)
			{
				Model model_1 = aModel_1714;
				model_1.translate(anInt1711 - super.x, anInt1712 - anInt1709, anInt1713 - super.y);
				if(super.turnDirection == 512)
				{
					model_1.method473();
					model_1.method473();
					model_1.method473();
				} else
				if(super.turnDirection == 1024)
				{
					model_1.method473();
					model_1.method473();
				} else
				if(super.turnDirection == 1536)
					model_1.method473();
				Model aclass30_sub2_sub4_sub6s[] = {
						model, model_1
				};
				model = new Model(aclass30_sub2_sub4_sub6s);
				if(super.turnDirection == 512)
					model_1.method473();
				else
				if(super.turnDirection == 1024)
				{
					model_1.method473();
					model_1.method473();
				} else
				if(super.turnDirection == 1536)
				{
					model_1.method473();
					model_1.method473();
					model_1.method473();
				}
				model_1.translate(super.x - anInt1711, anInt1709 - anInt1712, super.y - anInt1713);
			}
		}
		model.singleSquare = true;
		return model;
	}

	public void updatePlayer(Buffer buffer)
	{
		buffer.currentOffset = 0;
		anInt1702 = buffer.readUnsignedByte();
		headIcon = buffer.readUnsignedByte();
		skullIcon = buffer.readUnsignedByte();
		//hintIcon = buffer.readUnsignedByte();
		desc = null;
		team = 0;
		for(int j = 0; j < 12; j++)
		{
			int k = buffer.readUnsignedByte();
			if(k == 0)
			{
				equipment[j] = 0;
				continue;
			}
			int i1 = buffer.readUnsignedByte();
			equipment[j] = (k << 8) + i1;
			if(j == 0 && equipment[0] == 65535)
			{
				desc = EntityDefinition.forID(buffer.readUnsignedWord());
				break;
			}
			if(equipment[j] >= 512 && equipment[j] - 512 < ItemDefinition.totalItems)
			{
				int l1 = ItemDefinition.forID(equipment[j] - 512).team;
				if(l1 != 0)
					team = l1;
			}
		}

		for(int l = 0; l < 5; l++)
		{
			int j1 = buffer.readUnsignedByte();
			if(j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length)
				j1 = 0;
			anIntArray1700[l] = j1;
		}

		super.anInt1511 = buffer.readUnsignedWord();
		if(super.anInt1511 == 65535)
			super.anInt1511 = -1;
		super.anInt1512 = buffer.readUnsignedWord();
		if(super.anInt1512 == 65535)
			super.anInt1512 = -1;
		super.anInt1554 = buffer.readUnsignedWord();
		if(super.anInt1554 == 65535)
			super.anInt1554 = -1;
		super.anInt1555 = buffer.readUnsignedWord();
		if(super.anInt1555 == 65535)
			super.anInt1555 = -1;
		super.anInt1556 = buffer.readUnsignedWord();
		if(super.anInt1556 == 65535)
			super.anInt1556 = -1;
		super.anInt1557 = buffer.readUnsignedWord();
		if(super.anInt1557 == 65535)
			super.anInt1557 = -1;
		super.anInt1505 = buffer.readUnsignedWord();
		if(super.anInt1505 == 65535)
			super.anInt1505 = -1;
		name = TextClass.fixName(TextClass.nameForLong(buffer.readQWord()));
		combatLevel = buffer.readUnsignedByte();
		skill = buffer.readUnsignedWord();
		visible = true;
		aLong1718 = 0L;
		for(int k1 = 0; k1 < 12; k1++)
		{
			aLong1718 <<= 4;
			if(equipment[k1] >= 256)
				aLong1718 += equipment[k1] - 256;
		}

		if(equipment[0] >= 256)
			aLong1718 += equipment[0] - 256 >> 4;
		if(equipment[1] >= 256)
			aLong1718 += equipment[1] - 256 >> 8;
		for(int i2 = 0; i2 < 5; i2++)
		{
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += anInt1702;
	}

	public Model method452() {
		int currAnim = -1;
		int nextAnim = -1;
		int currCycle = -1;
		int nextCycle = -1;
		if(desc != null) {
			if(super.anim >= 0 && super.anInt1529 == 0) {
				final Animation seq = Animation.anims[super.anim];
				currAnim = seq.anIntArray353[super.anInt1527];
				if (Client.enableTweening && super.nextAnimFrame != -1) {
					nextAnim = seq.anIntArray353[super.anInt1527];
					currCycle = seq.anIntArray355[super.anInt1527];
					nextCycle = super.anInt1528;
				}
			} else if(super.anInt1517 >= 0) {
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
		long l = aLong1718;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if(super.anim >= 0 && super.anInt1529 == 0) {
			final Animation animation = Animation.anims[super.anim];
			currAnim = animation.anIntArray353[super.anInt1527];
			if (Client.enableTweening && super.nextAnimFrame != -1) {
				nextAnim = animation.anIntArray353[super.nextAnimFrame];
				currCycle = animation.anIntArray355[super.anInt1527];
				nextCycle = super.anInt1528;
			}
			if(super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511) {
				i1 = Animation.anims[super.anInt1517].anIntArray353[super.anInt1518];
			}
			if(animation.anInt360 >= 0) {
				j1 = animation.anInt360;
				l += j1 - equipment[5] << 40;
			}
			if(animation.anInt361 >= 0) {
				k1 = animation.anInt361;
				l += k1 - equipment[3] << 48;
			}
		} else if(super.anInt1517 >= 0) {
			Animation seq = Animation.anims[super.anInt1517];
			currAnim = seq.anIntArray353[super.anInt1518];
			if (Client.enableTweening && super.nextIdleAnimFrame != -1) {
				nextAnim = seq.anIntArray353[super.nextIdleAnimFrame];
				currCycle = seq.anIntArray355[super.anInt1518];
				nextCycle = super.anInt1519;
			}
		}
		Model model_1 = (Model) mruNodes.insertFromCache(l);
		if(model_1 == null) {
			boolean flag = false;
			for(int i2 = 0; i2 < 12; i2++) {
				int k2 = equipment[i2];
				if(k1 >= 0 && i2 == 3) {
					k2 = k1;
				}
				if(j1 >= 0 && i2 == 5) {
					k2 = j1;
				}
				if(k2 >= 256 && k2 < 512 && !IdentityKitDefinition.cache[k2 - 256].method537()) {
					flag = true;
				}
				if(k2 >= 512 && !ItemDefinition.forID(k2 - 512).method195(anInt1702)) {
					flag = true;
				}
			}
			if(flag) {
				if(aLong1697 != -1L) {
					model_1 = (Model) mruNodes.insertFromCache(aLong1697);
				}
				if(model_1 == null) {
					return null;
				}
			}
		}
		if(model_1 == null) {
			final Model[] models = new Model[12];//13
			int j2 = 0;
			for(int l2 = 0; l2 < 12; l2++) {
				int i3 = equipment[l2];
				if(k1 >= 0 && l2 == 3) {
					i3 = k1;
				}
				if(j1 >= 0 && l2 == 5) {
					i3 = j1;
				}
				if(i3 >= 256 && i3 < 512) {
					final Model model_3 = IdentityKitDefinition.cache[i3 - 256].method538();
					if(model_3 != null) {
						models[j2++] = model_3;
					}
				}
				if(i3 >= 512) {
					final Model model_4 = ItemDefinition.forID(i3 - 512).method196(anInt1702);
					if(model_4 != null) {
						models[j2++] = model_4;
					}
				}
			}
			//models[j2++] = Model.method462(16314);
			model_1 = new Model(j2, models);
			for(int j3 = 0; j3 < 5; j3++) {
				if(anIntArray1700[j3] != 0) {
					model_1.recolour(Client.anIntArrayArray1003[j3][0], Client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if(j3 == 1) {
						model_1.recolour(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j3]]);
					}
				}
			}
			model_1.createBones();
			model_1.scale(132, 132, 132);
			model_1.method479(84, 1000, -90, -580, -90, true);
			mruNodes.removeFromCache(model_1, l);
			aLong1697 = l;
		}
		if(aBoolean1699) {
			return model_1;
		}
		final Model model_2 = Model.aModel_1621;
		model_2.method464(model_1, Class36.method532(currAnim) & Class36.method532(i1));
		if (currAnim != -1 && i1 != -1) {
			model_2.mixAnimationFrames(Animation.anims[super.anim].anIntArray357, i1, currAnim);
		} else if (currAnim != -1) {
			if (Client.enableTweening) {
				model_2.applyAnimation(currAnim, nextAnim, nextCycle, currCycle);
			} else {
				model_2.applyAnimation(currAnim);
			}
		}
		model_2.method466();
		model_2.triangleSkin = null;
		model_2.vertexSkin = null;
		return model_2;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public int privelage;
	public Model method453()
	{
		if(!visible)
			return null;
		if(desc != null)
			return desc.method160();
		boolean flag = false;
		for(int i = 0; i < 12; i++)
		{
			int j = equipment[i];
			if(j >= 256 && j < 512 && !IdentityKitDefinition.cache[j - 256].method539())
				flag = true;
			if(j >= 512 && !ItemDefinition.forID(j - 512).method192(anInt1702))
				flag = true;
		}

		if(flag)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[12];
		int k = 0;
		for(int l = 0; l < 12; l++)
		{
			int i1 = equipment[l];
			if(i1 >= 256 && i1 < 512)
			{
				Model model_1 = IdentityKitDefinition.cache[i1 - 256].method540();
				if(model_1 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_1;
			}
			if(i1 >= 512)
			{
				Model model_2 = ItemDefinition.forID(i1 - 512).method194(anInt1702);
				if(model_2 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_2;
			}
		}

		Model model = new Model(k, aclass30_sub2_sub4_sub6s);
		for(int j1 = 0; j1 < 5; j1++)
			if(anIntArray1700[j1] != 0)
			{
				model.recolour(Client.anIntArrayArray1003[j1][0], Client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if(j1 == 1)
					model.recolour(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j1]]);
			}

		return model;
	}

	public Player()
	{
		aLong1697 = -1L;
		aBoolean1699 = false;
		anIntArray1700 = new int[5];
		visible = false;
		equipment = new int[12];
	}

	private long aLong1697;
	public EntityDefinition desc;
	public boolean aBoolean1699;
	public final int[] anIntArray1700;
	public int team;
	private int anInt1702;
	public String name;
	public static MRUNodes mruNodes = new MRUNodes(260);
	public int combatLevel;
	public int headIcon;
	public int skullIcon;
	public int hintIcon;
	public int anInt1707;
	public int anInt1708;
	public int anInt1709;
	public boolean visible;
	public int anInt1711;
	public int anInt1712;
	public int anInt1713;
	public Model aModel_1714;
	public final int[] equipment;
	private long aLong1718;
	public int anInt1719;
	public int anInt1720;
	public int anInt1721;
	public int anInt1722;
	public int skill;

}
