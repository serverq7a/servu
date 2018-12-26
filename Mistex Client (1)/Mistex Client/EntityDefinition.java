

public final class EntityDefinition {

	public static EntityDefinition forID(int i) {
		for(int j = 0; j < 20; j++)
			if(cache[j].type == (long)i)
				return cache[j];
		anInt56 = (anInt56 + 1) % 20;
		EntityDefinition entityDef = cache[anInt56] = new EntityDefinition();
		stream.currentOffset = streamIndices[i];
		entityDef.type = i;
		entityDef.readValues(stream);
		if (i == 2579) { 
            entityDef.name = "Scarlet"; 
            entityDef.description = "Her bank is 50x the wealth of yours...".getBytes(); 
            entityDef.actions = new String[5];
            entityDef.combatLevel = 138; 
            entityDef.actions[0] = "Talk-to"; 
            entityDef.actions[2] = "Follow"; 
            entityDef.actions[3] = "Trade";
            entityDef.standAnim = 898; 
        }
		if(i == 2745) {
			entityDef.name = "TzTok-Jad";
			entityDef.aByte68 = 5;
			entityDef.standAnim = 9274;
			entityDef.walkAnim = 9273;
			entityDef.actions = new String[] {null, "Attack", null, null, null};
            entityDef.anInt86 = 110;
            entityDef.anInt91 = 110;
            entityDef.description = "This is going to hurt...".getBytes();
		}
        if (i == 6222) {
            entityDef.name = "Kree'arra";
            entityDef.aByte68 = 5;
            entityDef.standAnim = 6972;
            entityDef.walkAnim = 6973;
            entityDef.actions = new String[] {null, "Attack", null, null, null};
            entityDef.anInt86 = 110;
            entityDef.anInt91 = 110;
        }
		if(i == 15976) {
			entityDef.name = "Max";
			entityDef.anIntArray94 = new int[]{65291, 62746, 62743, 53327, 13307, 9642, 529, 65300, 506, 252};
			entityDef.standAnim = 813;
			entityDef.walkAnim = 1205;
			entityDef.actions = new String[]{"Talk-to", null, null, "Trade", null};
			entityDef.anIntArray70 = new int[]{-8256, -11353, 6400, 43934, 43934, 6400};
			entityDef.anIntArray76 = new int[]{796, 920, 65214, 65200, 65186, 62995};
			entityDef.description = "He's mastered the many skills of Mistex.".getBytes();
			entityDef.combatLevel = 138;
			entityDef.aBoolean93 = true;
		}
        if (i == 940) {
            entityDef.description = "A beast looking mother-****er".getBytes();
            entityDef.name = "Echned Zekin";
            entityDef.combatLevel = 0;
        }
        switch (i) {
        case 5964:
            entityDef.name = "Ed Wood";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            entityDef.actions[2] = "Give items";
        	break;
        case 300:
            entityDef.name = "Dr. Decant";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            break;
        case 2581:
            entityDef.name = "Ellamaria";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Talk-to";
            entityDef.actions[2] = "Trade";
            entityDef.actions[3] = "Prestige";
            break;
        case 3299:
            entityDef.name = "Farming Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 5113:
            entityDef.name = "Hunting Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 519:
            entityDef.name = "Skilling Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 516:
            entityDef.name = "Food & Pots Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 2538:
            entityDef.name = "Weapons Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 2537:
            entityDef.name = "Armour Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 2536:
            entityDef.name = "Pure Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 461:
            entityDef.name = "Magic Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 682:
            entityDef.name = "Range Supplies";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        case 2590:
            entityDef.name = "Donator's Shop";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            break;
        }
        if (i == 6525) {
            entityDef.description = "His name is Murky Matt, don't you forget it!".getBytes();
            entityDef.name = "Murkey Matt";
            entityDef.actions = new String[5];
            entityDef.actions[0] = "Trade";
            entityDef.actions[2] = "Trade 2";
        }
        if (i == 6891) {
            entityDef.description = "The keeper of votes!".getBytes();
            entityDef.name = "Vote Keeper";
        }
        if (i == 8541) {
            entityDef.description = "The donation handler!".getBytes();
            entityDef.name = "Donator Imp";
        }
        if (entityDef.anIntArray70 != null) {
			for (int i2 = 0; i2 < entityDef.anIntArray70.length; i2++) {
				if (entityDef.anIntArray70[i2] == 0) {
					entityDef.anIntArray70[i2] = 1;
				}
			}
		}
		return entityDef;
	}

	public Model method160()
	{
		if(childrenIDs != null)
		{
			EntityDefinition entityDef = method161();
			if(entityDef == null)
				return null;
			else
				return entityDef.method160();
		}
		if(anIntArray73 == null)
			return null;
		boolean flag1 = false;
		for(int i = 0; i < anIntArray73.length; i++)
			if(!Model.method463(anIntArray73[i]))
				flag1 = true;

		if(flag1)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray73.length];
		for(int j = 0; j < anIntArray73.length; j++)
			aclass30_sub2_sub4_sub6s[j] = Model.method462(anIntArray73[j]);

		Model model;
		if(aclass30_sub2_sub4_sub6s.length == 1)
			model = aclass30_sub2_sub4_sub6s[0];
		else
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		if(anIntArray76 != null)
		{
			for(int k = 0; k < anIntArray76.length; k++)
				model.recolour(anIntArray76[k], anIntArray70[k]);

		}
		return model;
	}

	public EntityDefinition method161() {
		int j = -1;
		if(anInt57 != -1 && anInt57 < 4056)
		{
			VarBit varBit = VarBit.cache[anInt57];
			int k = varBit.anInt648;
			int l = varBit.anInt649;
			int i1 = varBit.anInt650;
			int j1 = Client.anIntArray1232[i1 - l];
			j = clientInstance.variousSettings[k] >> l & j1;
		} else
		if(anInt59 != -1)
			j = clientInstance.variousSettings[anInt59];
		if(j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1)
			return null;
		else
			return forID(childrenIDs[j]);
	}

	public static void unpackConfig(Archive streamLoader)
	{
		stream = new Buffer(streamLoader.getDataForName("npc.dat"));
		Buffer stream2 = new Buffer(streamLoader.getDataForName("npc.idx"));
		int totalNPCs = stream2.readUnsignedWord();
		streamIndices = new int[totalNPCs + 10000];
		System.out.println("Npcs Loaded: "+totalNPCs+"");
		int i = 2;
		for(int j = 0; j < totalNPCs; j++) {
			streamIndices[j] = i;
			i += stream2.readUnsignedWord();
		}

		cache = new EntityDefinition[20];
		for(int k = 0; k < 20; k++)
			cache[k] = new EntityDefinition();

	}

	public static void nullLoader()
	{
		mruNodes = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}
	
	 public Model method164(int j, int currAnim, int nextAnim, int currCycle, int nextCycle, int ai[]) {
	        if (childrenIDs != null) {
	            final EntityDefinition type = method161();
	            if (type == null) {
	                return null;
	            } else {
	                return type.method164(j, currAnim, ai);
	            }
	        }
	        Model model = (Model) mruNodes.insertFromCache(type);
	        if (model == null) {
	            boolean flag = false;
	            for (int i1 = 0; i1 < anIntArray94.length; i1++) {
	                if (!Model.method463(anIntArray94[i1])) {
	                    flag = true;
	                }
	            }
	            if (flag) {
	                return null;
	            }
	            final Model[] parts = new Model[anIntArray94.length];
	            for (int j1 = 0; j1 < anIntArray94.length; j1++) {
	                parts[j1] = Model.method462(anIntArray94[j1]);
	            }
	            if (parts.length == 1) {
	                model = parts[0];
	            } else {
	                model = new Model(parts.length, parts);
	            }
	            if (anIntArray76 != null) {
	                for (int k1 = 0; k1 < anIntArray70.length; k1++)
	                    model.recolour(anIntArray70[k1], anIntArray76[k1]);
	            }
	            model.createBones();
	            model.method479(84 + anInt85, 1000 + anInt92, -90, -580, -90, true);
	            mruNodes.removeFromCache(model, type);
	        }
	        final Model model_1 = Model.aModel_1621;
	        model_1.method464(model, Class36.method532(currAnim) & Class36.method532(j));
	        if (currAnim != -1 && j != -1) {
	            model_1.scale(anInt91, anInt91, anInt86);
	        } else if (currAnim != -1) {
	            if (Client.enableTweening) {
	                model_1.applyAnimation(currAnim, nextAnim, nextCycle, currCycle);
	            } else {
	                model_1.applyAnimation(currAnim);
	            }
	        }
	        if (anInt91 != 128 || anInt86 != 128) {
	            model_1.scale(anInt91, anInt86, anInt91);
	        }
	        model_1.method466();
	        model_1.triangleSkin = null;
	        model_1.vertexSkin = null;
	        if (aByte68 == 1) {
	            model_1.singleSquare = true;
	        }
	        return model_1;
	    }

	 public Model method164(int j, int k, int ai[]) {
	        if (childrenIDs != null) {
	            EntityDefinition entityDef = method161();
	            if (entityDef == null)
	                return null;
	            else
	                return entityDef.method164(j, k, ai);
	        }
	        Model model = (Model) mruNodes.insertFromCache(type);
	        if (model == null) {
	            boolean flag = false;
	            for (int i1 = 0; i1 < anIntArray94.length; i1++)
	                if (!Model.method463(anIntArray94[i1]))
	                    flag = true;

	            if (flag)
	                return null;
	            Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray94.length];
	            for (int j1 = 0; j1 < anIntArray94.length; j1++)
	                aclass30_sub2_sub4_sub6s[j1] = Model.method462(anIntArray94[j1]);
	            if (aclass30_sub2_sub4_sub6s.length == 1)
	                model = aclass30_sub2_sub4_sub6s[0];
	            else
	                model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
	            if (anIntArray76 != null) {
	                for (int k1 = 0; k1 < anIntArray70.length; k1++)
	                    model.recolour(anIntArray70[k1], anIntArray76[k1]);
	            }
	            model.createBones();
	            model.method479(84 + anInt85, 1000 + anInt92, -90, -580, -90, true);
	            mruNodes.removeFromCache(model, type);
	        }
	        Model model_1 = Model.aModel_1621;
	        model_1.method464(model, Class36.method532(k) & Class36.method532(j));
	        if (k != -1 && j != -1)
	            model_1.mixAnimationFrames(ai, j, k);
	        else if (k != -1)
	            model_1.applyAnimation(k);
	        if (anInt91 != 128 || anInt86 != 128)
	            model_1.scale(anInt91, anInt91, anInt86);
	        model_1.method466();
	        model_1.triangleSkin = null;
	        model_1.vertexSkin = null;
	        if (aByte68 == 1)
	            model_1.singleSquare = true;
	        return model_1;
	    }

	private void readValues(Buffer stream)
	{
		do
		{
			int i = stream.readUnsignedByte();
			if(i == 0)
				return;
			if(i == 1)
			{
				int j = stream.readUnsignedByte();
				anIntArray94 = new int[j];
				for(int j1 = 0; j1 < j; j1++)
					anIntArray94[j1] = stream.readUnsignedWord();

			} else
			if(i == 2)
				name = stream.readString();
			else
			if(i == 3)
				description = stream.readBytes();
			else
			if(i == 12)
				aByte68 = stream.readSignedByte();
			else
			if(i == 13)
				standAnim = stream.readUnsignedWord();
			else
			if(i == 14)
				walkAnim = stream.readUnsignedWord();
			else
			if(i == 17)
			{
				walkAnim = stream.readUnsignedWord();
				anInt58 = stream.readUnsignedWord();
				anInt83 = stream.readUnsignedWord();
				anInt55 = stream.readUnsignedWord();
				anInt58 = walkAnim;
				anInt83 = walkAnim;
				anInt55 = walkAnim;
			} else
			if(i >= 30 && i < 40)
			{
				if(actions == null)
					actions = new String[5];
				actions[i - 30] = stream.readString();
				if(actions[i - 30].equalsIgnoreCase("hidden"))
					actions[i - 30] = null;
			} else
			if(i == 40)
			{
                int k = stream.readUnsignedByte();
                anIntArray76 = new int[k];
                anIntArray70 = new int[k];
                for (int k1 = 0; k1 < k; k1++) {
                    anIntArray70[k1] = stream.readUnsignedWord();
                    anIntArray76[k1] = stream.readUnsignedWord();
                }

			} else
			if(i == 60)
			{
				int l = stream.readUnsignedByte();
				anIntArray73 = new int[l];
				for(int l1 = 0; l1 < l; l1++)
					anIntArray73[l1] = stream.readUnsignedWord();

			} else
			if(i == 90)
				stream.readUnsignedWord();
			else
			if(i == 91)
				stream.readUnsignedWord();
			else
			if(i == 92)
				stream.readUnsignedWord();
			else
			if(i == 93)
				aBoolean87 = false;
			else
			if(i == 95)
				combatLevel = stream.readUnsignedWord();
			else
			if(i == 97)
				anInt91 = stream.readUnsignedWord();
			else
			if(i == 98)
				anInt86 = stream.readUnsignedWord();
			else
			if(i == 99)
				aBoolean93 = true;
			else
			if(i == 100)
				anInt85 = stream.readSignedByte();
			else
			if(i == 101)
				anInt92 = stream.readSignedByte() * 5;
			else
			if(i == 102)
				anInt75 = stream.readUnsignedWord();
			else
			if(i == 103)
				anInt79 = stream.readUnsignedWord();
			else
			if(i == 106)
			{
				anInt57 = stream.readUnsignedWord();
				if(anInt57 == 65535)
					anInt57 = -1;
				anInt59 = stream.readUnsignedWord();
				if(anInt59 == 65535)
					anInt59 = -1;
				int i1 = stream.readUnsignedByte();
				childrenIDs = new int[i1 + 1];
				for(int i2 = 0; i2 <= i1; i2++)
				{
					childrenIDs[i2] = stream.readUnsignedWord();
					if(childrenIDs[i2] == 65535)
						childrenIDs[i2] = -1;
				}

			} else
			if(i == 107)
				aBoolean84 = false;
		} while(true);
	}

	private EntityDefinition()
	{
		anInt55 = -1;
		anInt57 = -1;
		anInt58 = -1;
		anInt59 = -1;
		combatLevel = -1;
		walkAnim = -1;
		aByte68 = 1;
		anInt75 = -1;
		standAnim = -1;
		type = -1L;
		anInt79 = 32;
		anInt83 = -1;
		aBoolean84 = true;
		anInt86 = 128;
		aBoolean87 = true;
		anInt91 = 128;
		aBoolean93 = false;
	}

	public int anInt55;
	private static int anInt56;
	private int anInt57;
	public int anInt58;
	private int anInt59;
	private static Buffer stream;
	public int combatLevel;
	public String name;
	public String actions[];
	public int walkAnim;
	public byte aByte68;
	private int[] anIntArray70;
	private static int[] streamIndices;
	private int[] anIntArray73;
	public int anInt75;
	private int[] anIntArray76;
	public int standAnim;
	public long type;
	public int anInt79;
	private static EntityDefinition[] cache;
	public static Client clientInstance;
	public int anInt83;
	public boolean aBoolean84;
	private int anInt85;
	private int anInt86;
	public boolean aBoolean87;
	public int childrenIDs[];
	public byte description[];
	private int anInt91;
	private int anInt92;
	public boolean aBoolean93;
	int[] anIntArray94;
	public static MRUNodes mruNodes = new MRUNodes(30);

}
