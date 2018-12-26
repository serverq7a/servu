
public final class ObjectDefinition {

	public static ObjectDefinition forID(int i) {
		for(int j = 0; j < 20; j++)
			if(cache[j].type == i)
				return cache[j];
		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDefinition objectDefinition = cache[cacheIndex];
		try {
			buffer.currentOffset = streamIndices[i];
		} catch (Exception exception) {	
		}
		objectDefinition.type = i;
		objectDefinition.setDefaults();
		objectDefinition.readValues(buffer);
		      if (objectDefinition.name != null)
			if (objectDefinition.name.equalsIgnoreCase("Curtain") || objectDefinition.name.equalsIgnoreCase("Door") || objectDefinition.name.equalsIgnoreCase("Large door") || objectDefinition.name.equalsIgnoreCase("Gate")) {
				for (int i4 = 0; i4 < objectDefinition.anIntArray773.length; i4++)
					objectDefinition.anIntArray773[i4] = 0;
					objectDefinition.aBoolean767 = false;
				return objectDefinition;
			}
		      
		      if(i == 4019) {
		    	objectDefinition.name = "Summoning Obelisk";		
		  		objectDefinition.description = "An obelisk filled with mystical energy.".getBytes();	
		  		objectDefinition.anInt744 = 4;					
		  		objectDefinition.anInt761 = 4;					
		  		objectDefinition.anIntArray773 = new int[1];
		  		objectDefinition.anIntArray773[0] = 62588;	
				objectDefinition.actions = new String[5];
				objectDefinition.actions[0] = "Infuse-Pouches";
				objectDefinition.actions[1] = "Infuse-Scrolls";
		  		objectDefinition.hasActions = true;   				
		        objectDefinition.aBoolean767 = true;   				
		        objectDefinition.aBoolean762 = false;				
		        objectDefinition.aBoolean769 = false;				
		  	}
		      if(i == 4020) {
			    	objectDefinition.name = "Small Obelisk";		
			  		objectDefinition.description = "An obelisk filled with mystical energy.".getBytes();	
			  		objectDefinition.anInt744 = 4;					
			  		objectDefinition.anInt761 = 4;					
			  		objectDefinition.anIntArray773 = new int[1];
			  		objectDefinition.anIntArray773[0] = 62589;	
					objectDefinition.actions = new String[5];
					objectDefinition.actions[0] = "Recharge Points";
			  		objectDefinition.hasActions = true;   				
			        objectDefinition.aBoolean767 = true;   				
			        objectDefinition.aBoolean762 = false;				
			        objectDefinition.aBoolean769 = false;				
			  	}
		      if(i == 3515) {
		    	  objectDefinition.name = "Mysterious cape";
				objectDefinition.description = "Mysterious cape is hanging here.".getBytes();
				objectDefinition.anInt744 = 2;
				objectDefinition.anInt761 = 1;
				objectDefinition.anIntArray773 = new int[1];
				objectDefinition.anIntArray773[0] = 65274;
				objectDefinition.actions = new String[5];
				objectDefinition.actions[0] = "Investigate";
				objectDefinition.hasActions = true;
				objectDefinition.aBoolean767 = true;
				objectDefinition.aBoolean762 = false;
				objectDefinition.aBoolean769 = false;
			}
			if (i == 438) {
				objectDefinition.name = "Rock";
				objectDefinition.actions = new String[5];
				objectDefinition.actions[0] = "Move";
				objectDefinition.description = "A loose rock.".getBytes();
			}
		if (i == 563) {
			objectDefinition.name = "PvP Statue";
			objectDefinition.actions = new String[5];
			objectDefinition.actions[0] = "Inspect";
			objectDefinition.description = "A mysterious looking statue.".getBytes();
		}
		if (i == 2006) {
			objectDefinition.name = "Highscore Statue";
			objectDefinition.actions = new String[5];
			objectDefinition.actions[0] = "Inspect";
			objectDefinition.description = "A mysterious looking statue.".getBytes();
		}
		if (i == 575) {
			objectDefinition.name = "Highscores Statue";
			objectDefinition.actions = new String[5];
			objectDefinition.actions[0] = "Inspect";
			objectDefinition.description = "Check out the highscores.".getBytes();
		}
		if (i == 16541) {
//			objectDefinition.method580();
		}
		if (i == 162) {
			objectDefinition.name = "Lottery Machine";
			objectDefinition.actions = new String[5];
			objectDefinition.actions[0] = "Inspect";
			objectDefinition.description = "Take a risk!".getBytes();
		}
		if (i == 1503) {
			objectDefinition.anIntArray773 = new int[1];
			objectDefinition.anIntArray773[0] = 28124;
			objectDefinition.hasActions = false;
		}
		if (i == 26392) {
			objectDefinition.anIntArray773 = new int[2];
			objectDefinition.anIntArray773[0] = 27816;
			objectDefinition.anIntArray773[1] = 27836;
			objectDefinition.hasActions = false;
		}
		if (i == 6788) {
			objectDefinition.anIntArray773 = new int[1];
			objectDefinition.anIntArray773[0] = 28124;
			objectDefinition.hasActions = false;
		}
		if (i == 8935) {
			objectDefinition.anIntArray773 = new int[1];
			objectDefinition.anIntArray773[0] = 28124;
			objectDefinition.hasActions = false;
		}
		if (i == 1460) {
			objectDefinition.anIntArray773 = new int[1];
			objectDefinition.anIntArray773[0] = 28124;
			objectDefinition.hasActions = false;
		}
		if (i == 9383) {
			objectDefinition.anIntArray773 = new int[1];
			objectDefinition.anIntArray773[0] = 28124;
			objectDefinition.hasActions = false;
		}
		if (i == 1434) {
			objectDefinition.anIntArray773 = new int[1];
			objectDefinition.anIntArray773[0] = 28124;
			objectDefinition.hasActions = false;
		}
		if (i == 6282) {
			objectDefinition.name = "Member Portal";
			objectDefinition.description = "This portal will bring you to the member zone.".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		if (i == 13617) {
			objectDefinition.name = "Skilling Portal";
			objectDefinition.description = "This portal will bring you to specified skilling areas.".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		if (i == 13618) {
			objectDefinition.name = "Training Portal";
			objectDefinition.description = "This portal will bring you to the specified trainning areas.".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		if (i == 2732) {
			objectDefinition.actions = new String[5];
			objectDefinition.actions[1] = "Add-Logs";
		}
		if (i == 13619) {
			objectDefinition.name = "Boss Portal";
			objectDefinition.description = "This portal will bring you to the specified boss areas.".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		if (i == 13620) {
			objectDefinition.name = "Minigame Portal";
			objectDefinition.description = "This portal will bring you to the specified minigame areas.".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		if (i == 13621) {
			objectDefinition.name = "Free For All Portal";
			objectDefinition.description = "Enter this portal to play".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		if (i == 13623) {
			objectDefinition.name = "Player killing Portal";
			objectDefinition.description = "Enter this portal to Pk!".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		
		if (i == 13624) {
			objectDefinition.name = "Solo Dung";
			objectDefinition.description = "Enter this portal for solo dungeoneering.".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		if (i == 13625) {
			objectDefinition.name = "Co-Op Dung";
			objectDefinition.description = "Enter this portal for co-op dungeoneering.".getBytes();
			objectDefinition.actions = new String[] { "Enter", null, null, null, null };
		}
		
    	if (objectDefinition.originalModelColors != null) {
			int[] oldc = objectDefinition.originalModelColors;
			int[] newc = objectDefinition.modifiedModelColors;
			objectDefinition.originalModelColors = new int[oldc.length + 1];
			objectDefinition.modifiedModelColors = new int[newc.length + 1];
			for (int index = 0; index < objectDefinition.originalModelColors.length; index++) {
				if (index < objectDefinition.originalModelColors.length - 1) {
					objectDefinition.originalModelColors[index] = oldc[index];
					objectDefinition.modifiedModelColors[index] = newc[index];
				} else {
					objectDefinition.originalModelColors[index] = 0;
					objectDefinition.modifiedModelColors[index] = 1;
				}
			}
		} else {
			objectDefinition.originalModelColors = new int[1];
			objectDefinition.modifiedModelColors = new int[1];
			objectDefinition.originalModelColors[0] = 0;
			objectDefinition.modifiedModelColors[0] = 1;
		}
		/*if (Config.debugMode) {
			
		if (objectDefinition.name == null || objectDefinition.name.equalsIgnoreCase("null"))
			objectDefinition.name = "test";
		
					objectDefinition.hasActions = true;
		}*/
		/*objectDefinition.hasActions = true;
	       objectDefinition.actions = new String[5];
	        objectDefinition.actions[0] = "ObjectID: " + i;
	        if(objectDefinition.anIntArray773 != null)
	        	objectDefinition.actions[1] = "Model ID: " + objectDefinition.anIntArray773[0];*/
		return objectDefinition;
	}

	private void setDefaults()
	{
		anIntArray773 = null;
		anIntArray776 = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		anInt744 = 1;
		anInt761 = 1;
		aBoolean767 = true;
		aBoolean757 = true;
		hasActions = false;
		aBoolean762 = false;
		aBoolean769 = false;
		aBoolean764 = false;
		anInt781 = -1;
		anInt775 = 16;
		aByte737 = 0;
		aByte742 = 0;
		actions = null;
		anInt746 = -1;
		anInt758 = -1;
		aBoolean751 = false;
		aBoolean779 = true;
		anInt748 = 128;
		anInt772 = 128;
		anInt740 = 128;
		anInt768 = 0;
		anInt738 = 0;
		anInt745 = 0;
		anInt783 = 0;
		aBoolean736 = false;
		aBoolean766 = false;
		anInt760 = -1;
		anInt774 = -1;
		anInt749 = -1;
		childrenIDs = null;
	}

	public void method574(OnDemandFetcher class42_sub1)
	{
		if(anIntArray773 == null)
			return;
		for(int j = 0; j < anIntArray773.length; j++)
			class42_sub1.method560(anIntArray773[j] & 0xffff, 0);
	}

	public static void nullLoader()
	{
		mruNodes1 = null;
		mruNodes2 = null;
		streamIndices = null;
		cache = null;
		buffer = null;
	}
	
	public static void unpackConfig(Archive jagexArchive)
	{
		buffer = new Buffer(jagexArchive.getDataForName("loc.dat"));
		Buffer buffer = new Buffer(jagexArchive.getDataForName("loc.idx"));
		int totalObjects = buffer.readUnsignedWord();
		streamIndices = new int[totalObjects + 10000];
		int i = 2;
		for(int j = 0; j < totalObjects; j++)
		{
			streamIndices[j] = i;
			i += buffer.readUnsignedWord();
		}
		cache = new ObjectDefinition[20];
		for(int k = 0; k < 20; k++)
			cache[k] = new ObjectDefinition();
	}

	public boolean method577(int i)
	{
		if(anIntArray776 == null)
		{
			if(anIntArray773 == null)
				return true;
			if(i != 10)
				return true;
			boolean flag1 = true;
			for(int k = 0; k < anIntArray773.length; k++)
				flag1 &= Model.method463(anIntArray773[k] & 0xffff);

			return flag1;
		}
		for(int j = 0; j < anIntArray776.length; j++)
			if(anIntArray776[j] == i)
				return Model.method463(anIntArray773[j] & 0xffff);

		return true;
	}

	public Model method578(int i, int j, int k, int l, int i1, int j1, int currAnim, int nextAnim, int end, int cycle) {
		Model model = method581(i, currAnim, nextAnim, end, cycle, j);
		if(model == null)
			return null;
		if(aBoolean762 || aBoolean769)
			model = new Model(aBoolean762, aBoolean769, model);
		if(aBoolean762)
		{
			int l1 = (k + l + i1 + j1) / 4;
			for(int i2 = 0; i2 < model.vertex_count; i2++)
			{
				int j2 = model.vertex_x[i2];
				int k2 = model.vertex_z[i2];
				int l2 = k + ((l - k) * (j2 + 64)) / 128;
				int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
				int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
				model.vertex_y[i2] += j3 - l1;
			}

			model.method467();
		}
		return model;
	}

	public boolean method579()
	{
		if(anIntArray773 == null)
			return true;
		boolean flag1 = true;
		for(int i = 0; i < anIntArray773.length; i++)
			flag1 &= Model.method463(anIntArray773[i] & 0xffff);
			return flag1;
	}

	public ObjectDefinition method580()
	{
		int i = -1;
		if(anInt774 != -1)
		{
			VarBit varBit = VarBit.cache[anInt774];
			int j = varBit.anInt648;
			int k = varBit.anInt649;
			int l = varBit.anInt650;
			int i1 = Client.anIntArray1232[l - k];
//			System.out.print(j + ", " + k + ", " + l + ", " + i1 + ", " + anInt774 + ";\n");
			i = clientInstance.variousSettings[j] >> k & i1;
		} else
		if(anInt749 != -1) {
			i = clientInstance.variousSettings[anInt749];
		}
		if(i < 0 || i >= childrenIDs.length || childrenIDs[i] == -1)
			return null;
		else
			return forID(childrenIDs[i]);
	}

	private Model method581(int j, int currAnim, int nextAnim, int end, int cycle, int l) {
		Model model = null;
		long l1;
		if(anIntArray776 == null)
		{
			if(j != 10)
				return null;
			l1 = (long)((type << 8) + l) + ((long)(currAnim + 1) << 32);
			Model model_1 = (Model) mruNodes2.insertFromCache(l1);
			if(model_1 != null)
				return model_1;
			if(anIntArray773 == null)
				return null;
			boolean flag1 = aBoolean751 ^ (l > 3);
			int k1 = anIntArray773.length;
			for(int i2 = 0; i2 < k1; i2++)
			{
				int l2 = anIntArray773[i2];
				if(flag1)
					l2 += 0x10000;
				model = (Model) mruNodes1.insertFromCache(l2);
				if(model == null)
				{
					model = Model.method462(l2 & 0xffff);
					if(model == null)
						return null;
					if(flag1)
						model.mirrorModel();
					mruNodes1.removeFromCache(model, l2);
				}
				if(k1 > 1)
					aModelArray741s[i2] = model;
			}

			if(k1 > 1)
				model = new Model(k1, aModelArray741s);
		} else
		{
			int i1 = -1;
			for(int j1 = 0; j1 < anIntArray776.length; j1++)
			{
				if(anIntArray776[j1] != j)
					continue;
				i1 = j1;
				break;
			}

			if(i1 == -1)
				return null;
			l1 = (long)((type << 8) + (i1 << 3) + l) + ((long)(currAnim + 1) << 32);
			Model model_2 = (Model) mruNodes2.insertFromCache(l1);
			if(model_2 != null)
				return model_2;
			int j2 = anIntArray773[i1];
			boolean flag3 = aBoolean751 ^ (l > 3);
			if(flag3)
				j2 += 0x10000;
			model = (Model) mruNodes1.insertFromCache(j2);
			if(model == null)
			{
				model = Model.method462(j2 & 0xffff);
				if(model == null)
					return null;
				if(flag3)
					model.mirrorModel();
				mruNodes1.removeFromCache(model, j2);
			}
		}
		boolean flag;
		flag = anInt748 != 128 || anInt772 != 128 || anInt740 != 128;
		boolean flag2;
		flag2 = anInt738 != 0 || anInt745 != 0 || anInt783 != 0;
		Model model_3 = new Model(modifiedModelColors == null, Class36.method532(currAnim), l == 0 && currAnim == -1 && !flag && !flag2, model);
		if(currAnim != -1) {
			model_3.createBones();
			model_3.applyAnimation(currAnim);
			model_3.triangleSkin = null;
			model_3.vertexSkin = null;
		}
		while(l-- > 0) 
			model_3.method473();
		if(modifiedModelColors != null)
		{
			for(int k2 = 0; k2 < modifiedModelColors.length; k2++)
				model_3.recolour(modifiedModelColors[k2], originalModelColors[k2]);

		}
		if(flag)
			model_3.scale(anInt748, anInt740, anInt772);
		if(flag2)
			model_3.translate(anInt738, anInt745, anInt783);
			model_3.method479(74, 1000, -90, -580, -90, !aBoolean769);
		
		if(anInt760 == 1)
			model_3.anInt1654 = model_3.modelHeight;
		mruNodes2.removeFromCache(model_3, l1);
		return model_3;
	}
	
	public void readValues(Buffer stream) {
		int flag = -1;
		do {
			int type = stream.readUnsignedByte();
			if (type == 0)
				break;
			if (type == 1) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (anIntArray773 == null || lowMem) {
						anIntArray776 = new int[len];
						anIntArray773 = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							anIntArray773[k1] = stream.readUnsignedWord();
							anIntArray776[k1] = stream.readUnsignedByte();
						}
					} else {
						stream.currentOffset += len * 3;
					}
				}
			} else if (type == 2)
				name = stream.readNewString();
			else if (type == 3)
				description = stream.readBytes();
			else if (type == 5) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (anIntArray773 == null || lowMem) {
						anIntArray776 = null;
						anIntArray773 = new int[len];
						for (int l1 = 0; l1 < len; l1++)
							anIntArray773[l1] = stream.readUnsignedWord();
					} else {
						stream.currentOffset += len * 2;
					}
				}
			} else if (type == 14)
				anInt744 = stream.readUnsignedByte();
			else if (type == 15)
				anInt761 = stream.readUnsignedByte();
			else if (type == 17)
				aBoolean767 = false;
			else if (type == 18)
				aBoolean757 = false;
			else if (type == 19)
				hasActions = (stream.readUnsignedByte() == 1);
			else if (type == 21)
				aBoolean762 = true;
			else if (type == 22)
				aBoolean769 = true;
			else if (type == 23)
				aBoolean764 = true;
			else if (type == 24) {
				anInt781 = stream.readUnsignedWord();
				if (anInt781 == 65535)
					anInt781 = -1;
			} else if (type == 28)
				anInt775 = stream.readUnsignedByte();
			else if (type == 29)
				aByte737 = stream.readSignedByte();
			else if (type == 39)
				aByte742 = stream.readSignedByte();
			else if (type >= 30 && type < 39) {
				if (actions == null)
					actions = new String[5];
				actions[type - 30] = stream.readNewString();
				if (actions[type - 30].equalsIgnoreCase("hidden"))
					actions[type - 30] = null;
			} else if (type == 40) {
				int i1 = stream.readUnsignedByte();
				modifiedModelColors = new int[i1];
				originalModelColors = new int[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					modifiedModelColors[i2] = stream.readUnsignedWord();
					originalModelColors[i2] = stream.readUnsignedWord();
				}

			} else if (type == 60)
				anInt746 = stream.readUnsignedWord();
			else if (type == 62)
				aBoolean751 = true;
			else if (type == 64)
				aBoolean779 = false;
			else if (type == 65)
				anInt748 = stream.readUnsignedWord();
			else if (type == 66)
				anInt772 = stream.readUnsignedWord();
			else if (type == 67)
				anInt740 = stream.readUnsignedWord();
			else if (type == 68)
				anInt758 = stream.readUnsignedWord();
			else if (type == 69)
				anInt768 = stream.readUnsignedByte();
			else if (type == 70)
				anInt738 = stream.readSignedWord();
			else if (type == 71)
				anInt745 = stream.readSignedWord();
			else if (type == 72)
				anInt783 = stream.readSignedWord();
			else if (type == 73)
				aBoolean736 = true;
			else if (type == 74)
				aBoolean766 = true;
			else if (type == 75)
				anInt760 = stream.readUnsignedByte();
			else if (type == 77) {
				anInt774 = stream.readUnsignedWord();
				if (anInt774 == 65535)
					anInt774 = -1;
				anInt749 = stream.readUnsignedWord();
				if (anInt749 == 65535)
					anInt749 = -1;
				int j1 = stream.readUnsignedByte();
				childrenIDs = new int[j1 + 1];
				for (int j2 = 0; j2 <= j1; j2++) {
					childrenIDs[j2] = stream.readUnsignedWord();
					if (childrenIDs[j2] == 65535)
						childrenIDs[j2] = -1;
				}
			}
		} while (true);
		if (flag == -1  && name != "null" && name != null) {
			hasActions = anIntArray773 != null
			&& (anIntArray776 == null || anIntArray776[0] == 10);
			if (actions != null)
				hasActions = true;
		}
		if (aBoolean766) {
			aBoolean767 = false;
			aBoolean757 = false;
		}
		if (anInt760 == -1)
			anInt760 = aBoolean767 ? 1 : 0;
	}

	private ObjectDefinition()
	{
		type = -1;
	}

	public boolean aBoolean736;
	public byte aByte737;
	private int anInt738;
	public String name;
	private int anInt740;
	private static final Model[] aModelArray741s = new Model[4];
	public byte aByte742;
	public int anInt744;
	private int anInt745;
	public int anInt746;
	private int[] originalModelColors;
	private int anInt748;
	public int anInt749;
	private boolean aBoolean751;
	public static boolean lowMem;
	private static Buffer buffer;
	public int type;
	private static int[] streamIndices;
	public boolean aBoolean757;
	public int anInt758;
	public int childrenIDs[];
	private int anInt760;
	public int anInt761;
	public boolean aBoolean762;
	public boolean aBoolean764;
	public static Client clientInstance;
	private boolean aBoolean766;
	public boolean aBoolean767;
	public int anInt768;
	private boolean aBoolean769;
	private static int cacheIndex;
	private int anInt772;
	int[] anIntArray773;
	public int anInt774;
	public int anInt775;
	private int[] anIntArray776;
	public byte description[];
	public boolean hasActions;
	public boolean aBoolean779;
	public static MRUNodes mruNodes2 = new MRUNodes(30);
	public int anInt781;
	private static ObjectDefinition[] cache;
	private int anInt783;
	private int[] modifiedModelColors;
	public static MRUNodes mruNodes1 = new MRUNodes(500);
	public String actions[];

}

