public final class ItemDefinition {
	
	public static byte femaleOffset;
	public static byte femaleEquipOffsetX;
	public int value;
	public int[] origColor;
	public int ID;
	public static MRUNodes iconcache = new MRUNodes(100);
	public static MRUNodes modelcache = new MRUNodes(50);
	public int[] newColor;
	public boolean membersObject;
	public int anInt162;
	public int certTemplateID;
	public int femaleArm;
	public int maleModel;
	public int anInt166;
	public int scaleX;
	public String groundActions[];
	public int offsetX;
	public String name;
	public static ItemDefinition[] cache;
	public int anInt173;
	public int groundModel;
	public int maleDialogue;
	public boolean stackable;
    public byte description[];
	public int certID;
	public static int cacheIndex;
	public int modelZoom;
	public static boolean isMembers = true;
	public static Buffer buffer;
	public int shading;
	public int anInt185;
	public int maleArm;
	public String actions[];
	public int modelRotationX;
	public int scaleY;
	public int scaleZ;
	public int[] stackIDs;
	public int offsetY;
	public static int[] item_offsets;
	public int lightness;
	public int femaleDialogue;
	public int modelRotationY;
	public int anInt200;
	public int[] stackAmounts;
	public int team;
	public static int totalItems;
	public int scaleInventory;
	public static byte maleOffset;
	public int lendID;
	public int lentItemID;
	
	public void setDefaults() {
		groundModel = 0;
		name = null;
		description = null;
		origColor = null;
		newColor = null;
		modelZoom = 2000;
		modelRotationX = 0;
		modelRotationY = 0;
		scaleInventory = 0;
		offsetX = 0;
		offsetY = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = null;
		actions = null;
		maleModel = -1;
		maleArm = -1;
		maleOffset = 0;
		anInt200 = -1;
		femaleArm = -1;
		femaleEquipOffsetX = 0;
		femaleOffset = 0;
		anInt185 = -1;
		anInt162 = -1;
		maleDialogue = -1;
		anInt166 = -1;
		femaleDialogue = -1;
		anInt173 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		scaleX = 128;
		scaleZ = 128;
		scaleY = 128;
		lightness = 0;
		shading = 0;
		team = 0;
		lendID = -1;
		lentItemID = -1;
	}
	
	public static void unpackConfig(Archive cacheArchive) {
		buffer = new Buffer(cacheArchive.getDataForName("obj.dat"));
		Buffer buffer = new Buffer(cacheArchive.getDataForName("obj.idx"));
		totalItems = buffer.readUnsignedWord();
		item_offsets = new int[totalItems + 20000];
		System.out.println("Items Loaded: "+totalItems+"");
		int offSet = 2;
		for (int j = 0; j < totalItems; j++) {
			item_offsets[j] = offSet;
			offSet += buffer.readUnsignedWord();
		}
		cache = new ItemDefinition[10];
		for (int k = 0; k < 10; k++) {
			cache[k] = new ItemDefinition();
		}
	}

	public static void clearCache() {
		modelcache = null;
		iconcache = null;
		item_offsets = null;
		cache = null;
		buffer = null;
	}

	public boolean method192(int j) {
		int k = maleDialogue;
		int l = anInt166;
		if (j == 1) {
			k = femaleDialogue;
			l = anInt173;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.method463(k))
			flag = false;
		if (l != -1 && !Model.method463(l))
			flag = false;
		return flag;
	}

	public Model method194(int j) {
		int k = maleDialogue;
		int l = anInt166;
		if (j == 1) {
			k = femaleDialogue;
			l = anInt173;
		}
		if (k == -1)
			return null;
		Model model = Model.method462(k);
		if (l != -1) {
			Model model_1 = Model.method462(l);
			Model models[] = { model, model_1 };
			model = new Model(2, models);
		}
		if (origColor != null) {
			for (int i1 = 0; i1 < origColor.length; i1++)
				model.recolour(origColor[i1], newColor[i1]);
		}
		return model;
	}

	public boolean method195(int j) {
		int k = maleModel;
		int l = maleArm;
		int i1 = anInt185;
		if (j == 1) {
			k = anInt200;
			l = femaleArm;
			i1 = anInt162;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.method463(k))
			flag = false;
		if (l != -1 && !Model.method463(l))
			flag = false;
		if (i1 != -1 && !Model.method463(i1))
			flag = false;
		return flag;
	}

	public Model method196(int i) {
		int j = maleModel;
		int k = maleArm;
		int l = anInt185;
		if (i == 1) {
			j = anInt200;
			k = femaleArm;
			l = anInt162;
		}
		if (j == -1)
			return null;
		Model model = Model.method462(j);
		if (k != -1)
			if (l != -1) {
				Model model_1 = Model.method462(k);
				Model model_3 = Model.method462(l);
				Model model_1s[] = { model, model_1, model_3 };
				model = new Model(3, model_1s);
			} else {
				Model model_2 = Model.method462(k);
				Model models[] = { model, model_2 };
				model = new Model(2, models);
			}
		if (i == 0 && maleOffset != 0)
			model.translate(0, maleOffset, 0);
		if (i == 1 && femaleOffset != 0)
			model.translate(0, femaleOffset, 0);
		if(i == 1 && femaleEquipOffsetX != 0)
			model.translate(0, femaleEquipOffsetX, 0);
		if(i == 1 && femaleEquipOffsetX != 0 && femaleOffset != 0)
			model.translate(0, femaleOffset, femaleEquipOffsetX);
		if (origColor != null) {
			for (int i1 = 0; i1 < origColor.length; i1++)
				model.recolour(origColor[i1], newColor[i1]);
		}
		return model;
	}

	public static ItemDefinition forID(int i) {
		for (int j = 0; j < 10; j++)
			if (cache[j].ID == i)
				return cache[j];
		cacheIndex = (cacheIndex + 1) % 10;
		ItemDefinition itemDef = cache[cacheIndex];
		buffer.currentOffset = item_offsets[i];
		itemDef.ID = i;
		itemDef.setDefaults();
		itemDef.readValues(buffer);
		if (itemDef.certTemplateID != -1)
			itemDef.toNote();
		if (itemDef.lentItemID != -1)
			itemDef.toLend();
		if (itemDef.origColor != null) {
            for (int i2 = 0; i2 < itemDef.origColor.length; i2++) {
                if (itemDef.newColor[i2] == 0) {
                    itemDef.newColor[i2] = 1;
                }
            }
        }
		boolean needsOffsetFix = false;
		
		for (String string : itemDef.actions) {
			if (string == null || string.length() == 0) {
				continue;
			}
					
			if (string.equalsIgnoreCase("Wield")) {
				needsOffsetFix = true;
			}
		}
				
		if (needsOffsetFix)  {
			femaleOffset = -4;
			femaleEquipOffsetX = 3;
		}
		
		String name = itemDef.name;
		if(name != null && (name.contains("chestplate") ||
				name.contains("platebody")
				|| name.contains("body") ||
				name.contains("shirt"))) {
			femaleOffset = 0;
			
		}
        if (!isMembers && itemDef.membersObject) {
            itemDef.name = "Members Object";
            itemDef.description = "Login to a members' server to use this object.".getBytes();
            itemDef.groundActions = null;
            itemDef.actions = null;
            itemDef.team = 0;
        }
        	if (itemDef.origColor != null) {
				int[] oldc = itemDef.origColor;
				int[] newc = itemDef.newColor;
				itemDef.origColor = new int[oldc.length + 1];
				itemDef.newColor = new int[newc.length + 1];
				for (int index = 0; index < itemDef.origColor.length; index++) {
					if (index < itemDef.origColor.length - 1) {
						itemDef.origColor[index] = oldc[index];
						itemDef.newColor[index] = newc[index];
					} else {
						itemDef.origColor[index] = 0;
						itemDef.newColor[index] = 1;
					}
				}
			} else {
				itemDef.origColor = new int[1];
				itemDef.newColor = new int[1];
				itemDef.origColor[0] = 0;
				itemDef.newColor[0] = 1;
			}
        	
    		switch(i) {
    		case 24000:
    			itemDef.groundModel = forID(10025).groundModel;
    			itemDef.modelRotationX = forID(10025).modelRotationX;
    			itemDef.modelRotationY = forID(10025).modelRotationY;
    			itemDef.modelZoom = forID(10025).modelZoom;
    			itemDef.offsetX = forID(10025).offsetX;
    			itemDef.offsetY = forID(10025).offsetY;
    			itemDef.name = "Runecrafting robes (yellow)";
    			itemDef.description = "A set of yellow runecrafting robes.".getBytes();
    			itemDef.actions = new String[] { "Claim", null, null, null, null };
    		break;
    		
    		case 24001:
    			itemDef.groundModel = forID(10025).groundModel;
    			itemDef.modelRotationX = forID(10025).modelRotationX;
    			itemDef.modelRotationY = forID(10025).modelRotationY;
    			itemDef.modelZoom = forID(10025).modelZoom;
    			itemDef.offsetX = forID(10025).offsetX;
    			itemDef.offsetY = forID(10025).offsetY;
    			itemDef.name = "Runecrafting robes (green)";
    			itemDef.description = "A set of green runecrafting robes.".getBytes();
    			itemDef.actions = new String[] { "Claim", null, null, null, null };
    			break;
    			
    		case 24002:
    			itemDef.groundModel = forID(10025).groundModel;
    			itemDef.modelRotationX = forID(10025).modelRotationX;
    			itemDef.modelRotationY = forID(10025).modelRotationY;
    			itemDef.modelZoom = forID(10025).modelZoom;
    			itemDef.offsetX = forID(10025).offsetX;
    			itemDef.offsetY = forID(10025).offsetY;
    			itemDef.name = "Runecrafting robes (blue)";
    			itemDef.description = "A set of blue runecrafting robes.".getBytes();
    			itemDef.actions = new String[] { "Claim", null, null, null, null };
    			break;
    			
    		case 24003:
    			itemDef.groundModel = forID(10025).groundModel;
    			itemDef.modelRotationX = forID(10025).modelRotationX;
    			itemDef.modelRotationY = forID(10025).modelRotationY;
    			itemDef.modelZoom = forID(10025).modelZoom;
    			itemDef.offsetX = forID(10025).offsetX;
    			itemDef.offsetY = forID(10025).offsetY;
    			itemDef.name = "Lumberjack clothing";
    			itemDef.description = "A set of lumberjack clothing.".getBytes();
    			itemDef.actions = new String[] { "Claim", null, null, null, null };
    			break;
    			
    		case 24004:
    			itemDef.groundModel = forID(10025).groundModel;
    			itemDef.modelRotationX = forID(10025).modelRotationX;
    			itemDef.modelRotationY = forID(10025).modelRotationY;
    			itemDef.modelZoom = forID(10025).modelZoom;
    			itemDef.offsetX = forID(10025).offsetX;
    			itemDef.offsetY = forID(10025).offsetY;
    			itemDef.name = "Lord marshal set";
    			itemDef.description = "A set of lord marshal clothing.".getBytes();
    			itemDef.actions = new String[] { "Claim", null, null, null, null };
    			break;
    		
    			/* Prestige **/
    		case 18702:
    			itemDef.name = "@or1@Perk Information";
    			itemDef.description = "A book with all the information regarding prestiges.".getBytes();
    		break;
    		case 18937:
    			itemDef.name = "@or1@Ghost";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18938:
    			itemDef.name = "@or1@Light weight";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18939:
    			itemDef.name = "@or1@Danger close";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18940:
    			itemDef.name = "@or1@Commando";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18941:
    			itemDef.name = "@or1@Retriever";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18942:
    			itemDef.name = "@or1@Steady Aim";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18943:
    			itemDef.name = "@or1@Stopping Power";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18944:
    			itemDef.name = "@or1@Scrambler";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18945:
    			itemDef.name = "@or1@Scavenger";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18946:
    			itemDef.name = "@or1@One man army";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18947:
    			itemDef.name = "@or1@Charmer";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		case 18948:
    			itemDef.name = "@or1@Pesticide";
    			itemDef.description = "A prestige perk.".getBytes();
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Activate";
    		break;
    		
    		
    		
    		case 1464:
    			itemDef.name = "@gre@Billion ticket";
    			itemDef.description = "A ticket that is worth 1 billion coins!".getBytes();
    		break;
    		case 24426:
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Wear";
    			itemDef.actions[4] = "Drop"; 
    			itemDef.groundModel = 62576;
    			itemDef.name = "Fishing boots";
    			itemDef.description = "A pair of water-proof boots.".getBytes();
    			itemDef.modelZoom = 900;
    			itemDef.modelRotationY = 165;
    			itemDef.modelRotationX = 99;
    			itemDef.offsetX = 3;
    			itemDef.offsetY = -7;
    			itemDef.maleModel = 62577;
    			itemDef.anInt200 = 62578;
    			break;

    			case 24423:
    			itemDef.name = "Fishing hat";
    			itemDef.description = "A wide-brimmed leather hat adorned with spare hooks and feathers.".getBytes();
    			itemDef.modelZoom = 760;
    			itemDef.modelRotationY = 20;
    			itemDef.modelRotationX = 81;
    			itemDef.offsetX = 1;
    			itemDef.offsetY = -3;
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Wear";
    			itemDef.actions[4] = "Drop";
    			itemDef.groundModel = 62579;
    			itemDef.maleModel = 62580;
    			itemDef.anInt200 = 62581;
    			break;


    			case 24425:
    			itemDef.name = "Fishing waders";
    			itemDef.description = "A baggy pair of well-insulated trousers".getBytes();
    			itemDef.modelZoom = 1750;
    			itemDef.modelRotationY = 186;
    			itemDef.modelRotationX = 350;
                itemDef.offsetX = 5;
                itemDef.offsetY = 11;
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Wear";
    			itemDef.actions[4] = "Drop";
    			itemDef.groundModel = 62582;
    			itemDef.maleModel = 62583;
    			itemDef.anInt200 = 62584;
    			break;

    			case 24424:
    			itemDef.groundModel = 62585;
    			itemDef.name = "Fishing jacket";
    			itemDef.description = "A padded sleeveless jacket with plenty of pockets to store spare tackle.".getBytes();
    			itemDef.modelZoom = 1350;
    			itemDef.modelRotationY = 2042;
    			itemDef.modelRotationX = 473;
    			itemDef.offsetX = 1;
    			itemDef.offsetY = 0;
    			itemDef.maleModel = 62586;
    			itemDef.anInt200 = 62587;
    			itemDef.groundActions = new String[5];
    			itemDef.groundActions[2] = "Take";
    			itemDef.actions = new String[5];
    			itemDef.actions[1] = "Wear";
    			itemDef.actions[4] = "Drop";
    			break;
    			
    		case 23639:
    			itemDef.name = "TokHaar-Kal";
    			itemDef.maleModel = 62575;
    			itemDef.anInt200 = 62582;
    			itemDef.groundActions = new String[5];
    			itemDef.groundActions[2] = "Take";
    			itemDef.offsetX = -4;
    			itemDef.groundModel = 62592;
    			itemDef.description = "A cape made of ancient, enchanted rocks..".getBytes();
            	itemDef.modelZoom = 1616;
            	itemDef.actions = new String[5];
            	itemDef.actions[1] = "Wear";
            	itemDef.actions[4] = "Drop";
            	itemDef.offsetY = 0;
            	itemDef.modelRotationX = 339;
            	itemDef.modelRotationY = 192;
            break;
    		
    		}
    		return itemDef;
    	}
	
	public static String itemModels(int itemID) {
		int inv = forID(itemID).groundModel;
		int male = forID(itemID).maleModel;
		int female = forID(itemID).anInt200;
		String name = forID(itemID).name;
		return "<col=225>"+name+"</col> (<col=800000000>"+itemID+"</col>) - [inv: <col=800000000>"+inv+"</col>] - [male: <col=800000000>"+male+"</col>] - [female: <col=800000000>"+female+"</col>]";
	}
	
	private void readValues(Buffer stream)
	{
		do
		{
			int i = stream.readUnsignedByte();
			if(i == 0)
				return;
			if(i == 1)
				groundModel = stream.readUnsignedWord();
			else
			if(i == 2)
				name = stream.readString();
			else
			if(i == 3)
				description = stream.readBytes();
			else
			if(i == 4)
				modelZoom = stream.readUnsignedWord();
			else
			if(i == 5)
				modelRotationX = stream.readUnsignedWord();
			else
			if(i == 6)
				modelRotationY = stream.readUnsignedWord();
			else
			if(i == 7)
			{
				offsetX = stream.readUnsignedWord();
				if(offsetX > 32767)
					offsetX -= 0x10000;
			} else
			if(i == 8)
			{
				offsetY = stream.readUnsignedWord();
				if(offsetY > 32767)
					offsetY -= 0x10000;
			} else
			if(i == 10)
				stream.readUnsignedWord();
			else
			if(i == 11)
				stackable = true;
			else
			if(i == 12)
				value = stream.readDWord();
			else
			if(i == 16)
				membersObject = true;
			else
			if(i == 23)
			{
				maleModel = stream.readUnsignedWord();
				maleOffset = stream.readSignedByte();
			} else
			if(i == 24)
				maleArm = stream.readUnsignedWord();
			else
			if(i == 25)
			{
				anInt200 = stream.readUnsignedWord();
				femaleOffset = stream.readSignedByte();
			} else
			if(i == 26)
				femaleArm = stream.readUnsignedWord();
			else
			if(i >= 30 && i < 35)
			{
				if(groundActions == null)
					groundActions = new String[5];
				groundActions[i - 30] = stream.readString();
				if(groundActions[i - 30].equalsIgnoreCase("hidden"))
					groundActions[i - 30] = null;
			} else
			if(i >= 35 && i < 40) {
				if(actions == null) {
					actions = new String[5];
				}
				actions[i - 35] = stream.readString();
        } else if(i == 40) {
            int j = buffer.readUnsignedByte();
            origColor = new int[j];
            newColor = new int[j];
            for(int k = 0; k < j; k++) {
                origColor[k] = buffer.readUnsignedWord();
                newColor[k] = buffer.readUnsignedWord();
            }
			} else
			if(i == 78)
				anInt185 = stream.readUnsignedWord();
			else
			if(i == 79)
				anInt162 = stream.readUnsignedWord();
			else
			if(i == 90)
				maleDialogue = stream.readUnsignedWord();
			else
			if(i == 91)
				femaleDialogue = stream.readUnsignedWord();
			else
			if(i == 92)
				anInt166 = stream.readUnsignedWord();
			else
			if(i == 93)
				anInt173 = stream.readUnsignedWord();
			else
			if(i == 95)
				scaleInventory = stream.readUnsignedWord();
			else
			if(i == 97)
				certID = stream.readUnsignedWord();
			else
			if(i == 98)
				certTemplateID = stream.readUnsignedWord();
			else
			if(i >= 100 && i < 110) {
				if(stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}
				stackIDs[i - 100] = stream.readUnsignedWord();
				stackAmounts[i - 100] = stream.readUnsignedWord();
			} else
			if(i == 110)
				scaleX = stream.readUnsignedWord();
			else
			if(i == 111)
				scaleZ = stream.readUnsignedWord();
			else
			if(i == 112)
				scaleY = stream.readUnsignedWord();
			else
			if(i == 113)
				lightness = stream.readSignedByte();
			else
			if(i == 114)
				shading = stream.readSignedByte() * 5;
			else
			if(i == 115)
				team = stream.readUnsignedByte();
			else
			if(i == 121)
				lendID = stream.readUnsignedWord();
			else
			if(i == 122)
				lentItemID = stream.readUnsignedWord();
		} while(true);
	}
	
	public void toNote() {
		ItemDefinition itemDef = forID(certTemplateID);
		groundModel = itemDef.groundModel;
		modelZoom = itemDef.modelZoom;
		modelRotationX = itemDef.modelRotationX;
		modelRotationY = itemDef.modelRotationY;
		scaleInventory = itemDef.scaleInventory;
		offsetX = itemDef.offsetX;
		offsetY = itemDef.offsetY;
		origColor = itemDef.origColor;
		newColor = itemDef.newColor;
		ItemDefinition itemDef_1 = forID(certID);
		name = itemDef_1.name;
		membersObject = itemDef_1.membersObject;
		value = itemDef_1.value;
		String s = "a";
		char c = itemDef_1.name.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}
		description = ("Swap this note at any bank for " + s + " " + itemDef_1.name + ".").getBytes();
		stackable = true;
	}
	
	private void toLend() {
		ItemDefinition itemDef = forID(lentItemID);
		actions = new String[5];
		groundModel = itemDef.groundModel;
		offsetX = itemDef.offsetX;
		modelRotationY = itemDef.modelRotationY;
		offsetY = itemDef.offsetY;
		modelZoom = itemDef.modelZoom;
		modelRotationX = itemDef.modelRotationX;
		scaleInventory = itemDef.scaleInventory;
		value = 0;
		ItemDefinition itemDef_1 = forID(lendID);
		anInt166 = itemDef_1.anInt166;
		origColor = itemDef_1.origColor;
		anInt185 = itemDef_1.anInt185;
		maleArm = itemDef_1.maleArm;
		anInt173 = itemDef_1.anInt173;
		maleDialogue = itemDef_1.maleDialogue;
		groundActions = itemDef_1.groundActions;
		maleModel = itemDef_1.maleModel;
		name = itemDef_1.name;
		anInt200 = itemDef_1.anInt200;
		membersObject = itemDef_1.membersObject;
		femaleDialogue = itemDef_1.femaleDialogue;
		femaleArm = itemDef_1.femaleArm;
		anInt162 = itemDef_1.anInt162;
		newColor = itemDef_1.newColor;
		team = itemDef_1.team;
		if (itemDef_1.actions != null) {
			for (int i_33_ = 0; i_33_ < 4; i_33_++) {
				actions[i_33_] = itemDef_1.actions[i_33_];
			}
		}
		actions[4] = "Discard";
	}

	public static Sprite getSprite(int id, int j, int edgeColor) {
		if (edgeColor == 0) {
			Sprite sprite = (Sprite) iconcache.insertFromCache(id);
			if (sprite != null && sprite.anInt1445 != j && sprite.anInt1445 != -1) {
				sprite.unlink();
				sprite = null;
			}
			if (sprite != null)
				return sprite;
		}
		ItemDefinition itemDef = forID(id);
		if (itemDef.stackIDs == null)
			j = -1;
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];
			if (i1 != -1)
				itemDef = forID(i1);
		}
		Model model = itemDef.getModel(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if (sprite == null)
				return null;
		}
		if (itemDef.lentItemID != -1) {
			sprite = getSprite(itemDef.lendID, 50, 0);
			if (sprite == null)
				return null;
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Rasterizer.centerX;
		int l1 = Rasterizer.centerY;
		int ai[] = Rasterizer.lineOffsets;
		int ai1[] = DrawingArea.canvasRaster;
		float ai2[] = DrawingArea.depthBuffer;
		int i2 = DrawingArea.canvasWidth;
		int j2 = DrawingArea.canvasHeight;
		int k2 = DrawingArea.clipStartX;
		int l2 = DrawingArea.clipEndX;
		int i3 = DrawingArea.clipStartY;
		int j3 = DrawingArea.clipEndY;
		Rasterizer.notTextured = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels, new float[32*32]);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Rasterizer.setDefaultBounds();
		int k3 = itemDef.modelZoom;
		if (edgeColor == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (edgeColor > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Rasterizer.SINE[itemDef.modelRotationX] * k3 >> 16;
		int i4 = Rasterizer.COSINE[itemDef.modelRotationX] * k3 >> 16;
		model.singleRender(itemDef.modelRotationY, itemDef.scaleInventory, itemDef.modelRotationX, itemDef.offsetX, l3 + model.modelHeight / 2 + itemDef.offsetY, i4 + itemDef.offsetY);
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--)
				if (sprite2.myPixels[i5 + j4 * 32] == 0)
					if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
		}
		if (edgeColor > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--)
					if (sprite2.myPixels[j5 + k4 * 32] == 0)
						if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = edgeColor;
						else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = edgeColor;
						else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = edgeColor;
						else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = edgeColor;
			}
		} else if (edgeColor == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--)
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
			}
		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if (itemDef.lentItemID != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if (edgeColor == 0)
			iconcache.removeFromCache(sprite2, id);
		DrawingArea.initDrawingArea(j2, i2, ai1, ai2);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Rasterizer.centerX = k1;
		Rasterizer.centerY = l1;
		Rasterizer.lineOffsets = ai;
		Rasterizer.notTextured = true;
		if (itemDef.stackable)
			sprite2.anInt1444 = 33;
		else
			sprite2.anInt1444 = 32;
		sprite2.anInt1445 = j;
		return sprite2;
	}

	public Model getModel(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];
			if (j != -1)
				return forID(j).getModel(1);
		}
		Model model = (Model) modelcache.insertFromCache(ID);
		if (model != null)
			return model;
		model = Model.method462(groundModel);
		if (model == null)
			return null;
		if (scaleX != 128 || scaleZ != 128 || scaleY != 128)
			model.scale(scaleX, scaleY, scaleZ);
		if (origColor != null) {
			for (int l = 0; l < origColor.length; l++)
				model.recolour(origColor[l], newColor[l]);
		}
		model.method479(64 + lightness, 768 + shading, -50, -10, -50, true);
		model.singleSquare = true;
		modelcache.removeFromCache(model, ID);
		return model;
	}

	public Model method202(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];
			if (j != -1)
				return forID(j).method202(1);
		}
		Model model = Model.method462(groundModel);
		if (model == null)
			return null;
		if (origColor != null) {
			for (int l = 0; l < origColor.length; l++)
				model.recolour(origColor[l], newColor[l]);
		}
		return model;
	}

	public ItemDefinition() {
		ID = -1;
	}
}