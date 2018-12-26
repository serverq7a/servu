public class RSInterface {
	public static final int BANK_CHILDREN_BASE_ID = 50000;

	public void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}
	
	public static void unpack(Sprite[] sprites, Archive jagexArchive, TextDrawingArea textDrawingAreas[], Archive streamLoader_1) {
		fonts = textDrawingAreas;
		aMRUNodes_238 = new MRUNodes(50000);
		Buffer buffer = new Buffer(jagexArchive.getDataForName("data"));
		int i = -1;
		buffer.readUnsignedWord();
		interfaceCache = new RSInterface[80000];
		while (buffer.currentOffset < buffer.buffer.length) {
			int k = buffer.readUnsignedWord();
			if (k == 65535) {
				i = buffer.readUnsignedWord();
				k = buffer.readUnsignedWord();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = buffer.readUnsignedByte();
			rsInterface.atActionType = buffer.readUnsignedByte();
			rsInterface.contentType = buffer.readUnsignedWord();
			rsInterface.width = buffer.readUnsignedWord();
			rsInterface.height = buffer.readUnsignedWord();
			rsInterface.aByte254 = (byte) buffer.readUnsignedByte();
			rsInterface.hoverType = buffer.readUnsignedByte();
			if (rsInterface.hoverType != 0)
				rsInterface.hoverType = (rsInterface.hoverType - 1 << 8) + buffer.readUnsignedByte();
			else
				rsInterface.hoverType = -1;
			int i1 = buffer.readUnsignedByte();
			if (i1 > 0) {
				rsInterface.valueCompareType = new int[i1];
				rsInterface.requiredValues = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.valueCompareType[j1] = buffer.readUnsignedByte();
					rsInterface.requiredValues[j1] = buffer.readUnsignedWord();
				}

			}
			int k1 = buffer.readUnsignedByte();
			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = buffer.readUnsignedWord();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++)
						rsInterface.valueIndexArray[l1][l4] = buffer.readUnsignedWord();

				}

			}
			if (rsInterface.type == 0) {
				rsInterface.drawsTransparent = false;
				rsInterface.scrollMax = buffer.readUnsignedWord();
				rsInterface.isMouseoverTriggered = buffer.readUnsignedByte() == 1;
				int i2 = buffer.readUnsignedWord();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					rsInterface.children[j3] = buffer.readUnsignedWord();
					rsInterface.childX[j3] = buffer.readSignedWord();
					rsInterface.childY[j3] = buffer.readSignedWord();
				}
			}
			if (rsInterface.type == 1) {
				buffer.readUnsignedWord();
				buffer.readUnsignedByte();
			}
			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.aBoolean259 = buffer.readUnsignedByte() == 1;
				rsInterface.isInventoryInterface = buffer.readUnsignedByte() == 1;
				rsInterface.usableItemInterface = buffer.readUnsignedByte() == 1;
				rsInterface.aBoolean235 = buffer.readUnsignedByte() == 1;
				rsInterface.invSpritePadX = buffer.readUnsignedByte();
				rsInterface.invSpritePadY = buffer.readUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = buffer.readUnsignedByte();
					if (k3 == 1) {
						rsInterface.spritesX[j2] = buffer.readSignedWord();
						rsInterface.spritesY[j2] = buffer.readSignedWord();
						String s1 = buffer.readString();
						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
						}
					}
				}
				rsInterface.actions = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = buffer.readString();
					if (rsInterface.actions[l3].length() == 0)
						rsInterface.actions[l3] = null;
					if (rsInterface.id == 3900)
						rsInterface.actions[4] = "Buy 500";
					if (rsInterface.parentID == 3822)
						rsInterface.actions[4] = "Sell 500";
					if (rsInterface.parentID == 1644)
						rsInterface.actions[2] = "Operate";
				}
			}
			if (rsInterface.type == 3)
				rsInterface.aBoolean227 = buffer.readUnsignedByte() == 1;
			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = buffer.readUnsignedByte() == 1;
				int k2 = buffer.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				rsInterface.textShadow = buffer.readUnsignedByte() == 1;
			}
			if (rsInterface.type == 4) {
				rsInterface.message = buffer.readString();
				rsInterface.message = rsInterface.message.replaceAll("RuneScape", "Mistex");
				rsInterface.message = rsInterface.message.replaceAll("Free Quests", "");
				rsInterface.aString228 = buffer.readString();
			}
			if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4)
				rsInterface.textColor = buffer.readDWord();
			if (rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.anInt219 = buffer.readDWord();
				rsInterface.anInt216 = buffer.readDWord();
				rsInterface.anInt239 = buffer.readDWord();
			}
			if (rsInterface.type == 5) {
				rsInterface.drawsTransparent = false;
				String s = buffer.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.sprite1 = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4));
				}
				s = buffer.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.sprite2 = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4));
				}
			}
			if (rsInterface.type == 6) {
				int l = buffer.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt233 = 1;
					rsInterface.mediaID = (l - 1 << 8) + buffer.readUnsignedByte();
				}
				l = buffer.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8) + buffer.readUnsignedByte();
				}
				l = buffer.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt257 = (l - 1 << 8) + buffer.readUnsignedByte();
				else
					rsInterface.anInt257 = -1;
				l = buffer.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt258 = (l - 1 << 8) + buffer.readUnsignedByte();
				else
					rsInterface.anInt258 = -1;
				rsInterface.modelZoom = buffer.readUnsignedWord();
				rsInterface.modelRotation1 = buffer.readUnsignedWord();
				rsInterface.modelRotation2 = buffer.readUnsignedWord();
			}
			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.centerText = buffer.readUnsignedByte() == 1;
				int l2 = buffer.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				rsInterface.textShadow = buffer.readUnsignedByte() == 1;
				rsInterface.textColor = buffer.readDWord();
				rsInterface.invSpritePadX = buffer.readSignedWord();
				rsInterface.invSpritePadY = buffer.readSignedWord();
				rsInterface.isInventoryInterface = buffer.readUnsignedByte() == 1;
				rsInterface.actions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = buffer.readString();
					if (rsInterface.actions[k4].length() == 0)
						rsInterface.actions[k4] = null;
				}

			}
			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = buffer.readString();
				rsInterface.spellName = buffer.readString();
				rsInterface.spellUsableOn = buffer.readUnsignedWord();
			}

			if (rsInterface.type == 8)
				rsInterface.message = buffer.readString();

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
				rsInterface.tooltip = buffer.readString();
				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1)
						rsInterface.tooltip = "Ok";
					if (rsInterface.atActionType == 4)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 5)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 6)
						rsInterface.tooltip = "Continue";
				}
			}
		}
		aClass44 = jagexArchive;
		constructLunar();
		optionTab(textDrawingAreas);
		skillTab602(sprites, textDrawingAreas);
		//Sidebar0(textDrawingAreas);
		configureLunar(textDrawingAreas);
		Bank(textDrawingAreas);
		CustomInterfaces.unpackInterfaces(textDrawingAreas);
		aMRUNodes_238 = null;
	}

	public static RSInterface addBankContainer(int id) {
		RSInterface bank = addInterface(id);
		bank.parentID = id; // Parent id is bank items layer
		bank.type = 2; // Item Layer - [Can hold items]
		bank.contentType = 206; // Bank contentType
		bank.width = 10; // Holds 10 items per row
		bank.height = 35; // Holds 35 columns of 10 rows of items
		bank.sprites = new Sprite[20]; // Dunno why but it needs
		bank.spritesX = new int[20]; // Dunno why but it needs
		bank.spritesY = new int[20]; // Dunno why but it needs
		bank.invSpritePadX = 12; // 24 pixels is added to the length of items
		bank.invSpritePadY = 12; // 24 pixels is added to the height of items
		bank.inv = new int[350]; // Sets the array where item ids are stored
									// [350 items max]
		bank.invStackSizes = new int[350]; // Sets the array where item amounts
											// are stored [350 items max]
		bank.aBoolean259 = true; // Enables swapping items
		bank.actions = new String[] { "Withdraw 1", "Withdraw 5", "Withdraw 10", "Withdraw All", "Withdraw X" };
		return bank;
	}

	public static void addTabItem(int id) {
		RSInterface item = addInterface(id);
		item.parentID = 50000; // Parent id is tab items layer
		item.type = 2; // Item Layer - [Can hold items]
		item.contentType = 206; // Bank contentType
		item.width = 1; // Holds 1 items per row
		item.height = 1; // Holds 1 column of 1 row of items
		item.sprites = new Sprite[20]; // Dunno why but it needs
		item.spritesX = new int[20]; // Dunno why but it needs
		item.spritesY = new int[20]; // Dunno why but it needs
		item.invSpritePadX = 12; // 24 pixels is added to the length of the item
		item.invSpritePadY = 12; // 24 pixels is added to the height of the item
		item.inv = new int[350]; // Sets the array where item ids are stored
									// [350 items max]
		item.invStackSizes = new int[350]; // Sets the array where item amounts
											// are stored [350 items max]
		item.actions = null;
	}

	public static void addTabButton(int parentId, int childId, String spriteLoc, String toolTip, int configFrame) {
		RSInterface TAB = addTabInterface(parentId);
		TAB.type = 0;
		TAB.width = 48;
		TAB.height = 38;
		TAB.totalChildren(childId != 50034 ? 2 : 1);
		RSInterface Tab = addTabInterface(childId);
		Tab.parentID = parentId;
		Tab.id = childId;
		Tab.type = 5;
		Tab.atActionType = 1;
		Tab.contentType = 0;
		Tab.width = 48;
		Tab.height = 38;
		Tab.opacity = 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 0;
		Tab.requiredValues[0] = 1;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(1, spriteLoc);
		Tab.sprite2 = imageLoader(0, spriteLoc);
		Tab.tooltip = toolTip;
		if (childId != 50034) {
			addButton(childId + 200, 0, "", "Collapse", 48, 38);
			setBounds(childId + 200, 0, 0, 0, TAB);
		}
		setBounds(childId, 0, 0, childId != 50034 ? 1 : 0, TAB);
	}

	public static void addTabSprite(int id, int childId, int spriteId, String spriteName, int width, int height) {
		RSInterface TAB = addInterface(id);
		TAB.width = width;
		TAB.height = height;
		TAB.totalChildren(1);
		RSInterface tab = addInterface(childId);
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = width;
		tab.height = height;
		setBounds(childId, 0, 0, 0, TAB);
	}

	public static void addHoveredConfigButton(RSInterface original, int ID, int IMAGEID, int disabledID, int enabledID, String spriteLoc) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = original.id;
		Tab.id = ID;
		Tab.type = 0;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = original.width;
		Tab.height = original.height;
		Tab.opacity = 0;
		Tab.hoverType = -1;
		RSInterface hover = addInterface(IMAGEID);
		hover.type = 5;
		hover.width = original.width;
		hover.height = original.height;
		hover.valueCompareType = original.valueCompareType;
		hover.requiredValues = original.requiredValues;
		hover.valueIndexArray = original.valueIndexArray;
		hover.sprite1 = imageLoader(disabledID, spriteLoc);
		hover.sprite2 = imageLoader(enabledID, spriteLoc);
		Tab.totalChildren(1);
		setBounds(IMAGEID, 0, 0, 0, Tab);
		Tab.tooltip = original.tooltip;
		Tab.isMouseoverTriggered = true;
	}

	public static void addHoverConfigButton(int ID, int hoverOver, int disabledID, int enabledID, String spriteLoc, int width, int height, String tooltip, int configID, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = ID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = 1;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.opacity = 0;
		Tab.hoverType = hoverOver;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(disabledID, spriteLoc);
		Tab.sprite2 = imageLoader(enabledID, spriteLoc);
		Tab.tooltip = tooltip;
	}

	public static void Bank(TextDrawingArea[] wid) {
		RSInterface Interface = addTabInterface(5292);
		setChildren(45, Interface);
		
		int id = 50000;

		/*
		 * Background
		 */
		addSprite(id, 0, "Bank/BANK");

		/*
		 * Close Window
		 */
		addHoverButton(id + 1, "Bank/BANK", 1, 16, 16, "Close window", 250, id + 2, 3);
		addHoveredButton(id + 2, "Bank/BANK", 2, 16, 16, id + 3);

		/*
		 * Swap Insert Mode
		 */
		addHoverConfigButton(id + 4, id + 5, 5, 6, "Bank/BANK", 35, 25, "Swap Insert Mode", 0, 1009);
		addHoveredConfigButton(interfaceCache[id + 4], id + 5, id + 6, 7, 8, "Bank/BANK");

		/*
		 * Search
		 */
		addHoverConfigButton(id + 7, id + 8, 13, 14, "Bank/BANK", 35, 25, "Search", 0, 1010);
		addHoveredConfigButton(interfaceCache[id + 7], id + 8, id + 9, 15, 16, "Bank/BANK");

		/*
		 * Bank Pin
		 */
		addHoverButton(id + 10, "Bank/BANK", 3, 114, 25, "Set A Bank PIN", 0, id + 11, 1);
		addHoveredButton(id + 11, "Bank/BANK", 4, 114, 25, id + 12);

		/*
		 * Withdraw As Note
		 */
		addHoverConfigButton(id + 13, id + 14, 9, 11, "Bank/BANK", 35, 25, "Withdraw", 0, 1011);
		addHoveredConfigButton(interfaceCache[id + 13], id + 14, id + 15, 10, 12, "Bank/BANK");

		/*
		 * Total Items
		 */
		addSprite(id + 16, 27, "Bank/BANK");
		addText(id + 17, "0", wid, 0, 0xB4965A, true);
		addText(id + 18, "Tab", wid, 0, 0xB4965A, true);
		addText(id + 19, "0", wid, 0, 0xB4965A, true);
		addText(id + 20, "0", wid, 0, 0xB4965A, true);

		/*
		 * Deposit Carried Items
		 */
		addHoverButton(id + 21, "Bank/BANK", 17, 35, 25, "Deposit carried items", 0, id + 22, 1);
		addHoveredButton(id + 22, "Bank/BANK", 18, 35, 25, id + 23);

		/*
		 * Deposit Worn Items
		 */
		addHoverButton(id + 24, "Bank/BANK", 19, 35, 25, "Deposit worn items", 0, id + 25, 1);
		addHoveredButton(id + 25, "Bank/BANK", 20, 35, 25, id + 26);

		/*
		 * Deposit BoB Items
		 */
		addHoverButton(id + 27, "Bank/BANK", 21, 35, 25, "Deposit BoB items", 0, id + 28, 1);
		addHoveredButton(id + 28, "Bank/BANK", 22, 35, 25, id + 29);

		/*
		 * Open Equipment
		 */
		addHoverButton(id + 30, "Bank/BANK", 23, 34, 34, "Open Equipment", 0, id + 31, 1);
		addHoveredButton(id + 31, "Bank/BANK", 24, 34, 34, id + 32);

		/*
		 * Set Up Tab Buttons
		 */
		addTabButton(id + 33, id + 34, "Bank/TAB", "View all", 1000);
		addTabButton(id + 35, id + 36, "Bank/TAB", "View tab 1", 1001);
		addTabButton(id + 37, id + 38, "Bank/TAB", "View tab 2", 1002);
		addTabButton(id + 39, id + 40, "Bank/TAB", "View tab 3", 1003);
		addTabButton(id + 41, id + 42, "Bank/TAB", "View tab 4", 1004);
		addTabButton(id + 43, id + 44, "Bank/TAB", "View tab 5", 1005);
		addTabButton(id + 45, id + 46, "Bank/TAB", "View tab 6", 1006);
		addTabButton(id + 47, id + 48, "Bank/TAB", "View tab 7", 1007);
		addTabButton(id + 49, id + 50, "Bank/TAB", "View tab 8", 1008);

		/*
		 * Set Up Tab Display Containers
		 */
		addTabItem(id + 51);
		addTabItem(id + 52);
		addTabItem(id + 53);
		addTabItem(id + 54);
		addTabItem(id + 55);
		addTabItem(id + 56);
		addTabItem(id + 57);
		addTabItem(id + 58);

		/*
		 * Add The Infinity Sign
		 */
		addTabSprite(id + 59, id + 60, 2, "Bank/TAB", 48, 38);

		/*
		 * Add The '+' Sign
		 */
		addTabSprite(id + 61, id + 62, 3, "Bank/TAB", 48, 38);

		setBounds(id, 13, 13, 0, Interface);
		setBounds(5383, 190, 15, 1, Interface);
		setBounds(id + 1, 478, 16, 2, Interface);
		setBounds(id + 2, 478, 16, 3, Interface);
		setBounds(id + 4, 25, 285, 4, Interface);
		setBounds(id + 5, 25, 285, 5, Interface);
		setBounds(id + 7, 65, 285, 6, Interface);
		setBounds(id + 8, 65, 285, 7, Interface);
		setBounds(id + 10, 110, 285, 8, Interface);
		setBounds(id + 11, 110, 285, 9, Interface);
		setBounds(id + 13, 236, 285, 10, Interface);
		setBounds(id + 14, 236, 285, 11, Interface);
		setBounds(id + 16, 277, 284, 12, Interface);
		setBounds(id + 17, 290, 287, 13, Interface);
		setBounds(id + 18, 310, 297, 14, Interface);
		setBounds(id + 19, 333, 287, 15, Interface);
		setBounds(id + 20, 352, 297, 16, Interface);
		setBounds(id + 21, 375, 285, 17, Interface);
		setBounds(id + 22, 375, 285, 18, Interface);
		setBounds(id + 24, 415, 285, 19, Interface);
		setBounds(id + 25, 415, 285, 20, Interface);
		setBounds(id + 27, 455, 285, 21, Interface);
		setBounds(id + 28, 455, 285, 22, Interface);
		setBounds(id + 30, 456, 36, 23, Interface);
		setBounds(id + 31, 456, 36, 24, Interface);
		setBounds(5385, -4, 74, 25, Interface);
		setBounds(id + 33, 21, 36, 26, Interface);
		setBounds(id + 35, 69, 36, 27, Interface);
		setBounds(id + 37, 117, 36, 28, Interface);
		setBounds(id + 39, 165, 36, 29, Interface);
		setBounds(id + 41, 213, 36, 30, Interface);
		setBounds(id + 43, 261, 36, 31, Interface);
		setBounds(id + 45, 309, 36, 32, Interface);
		setBounds(id + 47, 357, 36, 33, Interface);
		setBounds(id + 49, 405, 36, 34, Interface);
		setBounds(id + 51, 77, 38, 35, Interface);
		setBounds(id + 52, 77 + 48, 38, 36, Interface);
		setBounds(id + 53, 77 + 96, 38, 37, Interface);
		setBounds(id + 54, 77 + 144, 38, 38, Interface);
		setBounds(id + 55, 77 + 192, 38, 39, Interface);
		setBounds(id + 56, 77 + 240, 38, 40, Interface);
		setBounds(id + 57, 77 + 288, 38, 41, Interface);
		setBounds(id + 58, 77 + 336, 38, 42, Interface);
		setBounds(id + 59, 27, 38, 43, Interface);
		setBounds(id + 61, 76, 38, 44, Interface);

		/*
		 * Set Up Bank Tab Container
		 */
		Interface = interfaceCache[5385];
		Interface.children(1);
		addBankContainer(id + 63);
		setBounds(id + 63, 38, 7, 0, Interface);
		Interface.width = 480;
		Interface.height = 208;
	}

	public static void addPrayer(int i, int configId, int configFrame, int requiredValues, int prayerSpriteID, String PrayerName, int Hover) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 4;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.hoverType = -1;
		Interface.sprite1 = imageLoader(0, "CurseTab/GLOW");
		Interface.sprite2 = imageLoader(1, "CurseTab/GLOW");
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 1;
		Interface.requiredValues[0] = configId;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 5;
		Interface.valueIndexArray[0][1] = configFrame;
		Interface.valueIndexArray[0][2] = 0;
		Interface.tooltip = "Activate@or1@ " + PrayerName;
		Interface = addTabInterface(i + 1);
		Interface.id = i + 1;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.sprite1 = imageLoader(prayerSpriteID, "CurseTab/PRAYON");
		Interface.sprite2 = imageLoader(prayerSpriteID, "CurseTab/PRAYOFF");
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 2;
		Interface.requiredValues[0] = requiredValues + 1;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 2;
		Interface.valueIndexArray[0][1] = 5;
		Interface.valueIndexArray[0][2] = 0;
	}

	public static void drawTooltip(int id, String text) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.parentID = id;
		rsinterface.type = 0;
		rsinterface.isMouseoverTriggered = true;
		rsinterface.hoverType = -1;
		rsinterface.tooltip = text;
		rsinterface.totalChildren(1);
		rsinterface.child(0, id + 1, 0, 0);
	}

	public static void addPriceChecker(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		rsi.actions[0] = "Take 1";
		rsi.actions[1] = "Take 5";
		rsi.actions[2] = "Take 10";
		rsi.actions[3] = "Take All";
		rsi.actions[4] = "Take X";
		rsi.centerText = true;
		rsi.aBoolean227 = false;
		rsi.aBoolean235 = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.aBoolean259 = true;
		rsi.textShadow = false;
		rsi.invSpritePadX = 40;
		rsi.invSpritePadY = 28;
		rsi.height = 5;
		rsi.width = 5;
		rsi.parentID = 43933;
		rsi.id = 18246;
		rsi.type = 2;
	}

	public static void skillTab602(Sprite[] sprites, TextDrawingArea[] TDA) {
		RSInterface skill = addInterface(3917);
		addText(27203, "99", 16776960, false, true, -1, TDA, 0);
		addText(27204, "99", 16776960, false, true, -1, TDA, 0);
		addText(27205, "99", 16776960, false, true, -1, TDA, 0);
		addText(27206, "99", 16776960, false, true, -1, TDA, 0);
		int[] logoutID = { 2450, 2451, 2452 };
		int[] logoutID2 = { 2458 };
		for (int i : logoutID) {
			RSInterface Logout = interfaceCache[i];
			Logout.textColor = 16750623;
			Logout.contentType = 0;
		}
		for (int i : logoutID2) {
			RSInterface Logout = interfaceCache[i];
			Logout.contentType = 0;
		}
		skill.totalChildren(4);
		skill.child(0, 27203, 158, 175);
		skill.child(1, 27204, 171, 186);
		skill.child(2, 27205, 158, 203);
		skill.child(3, 27206, 171, 214);
		int[] spriteNames = { 82, 85, 96, 84, 98, 95, 83, 97, 92, 86, 99, 89, 87, 94, 93, 88, 91, 90, 102, 100, 101, 103, 104, 105, 106 };
		int[] buttons = { 8654, 8655, 8656, 8657, 8658, 8659, 8660, 8861, 8662, 8663, 8664, 8665, 8666, 8667, 8668, 8669, 8670, 8671, 8672, 12162, 13928, 27123, 27124, 27125, 27126 };
		int[] hovers = { 4040, 4076, 4112, 4046, 4082, 4118, 4052, 4088, 4124, 4058, 4094, 4130, 4064, 4100, 4136, 4070, 4106, 4142, 4160, 2832, 13917, 19005, 19006, 19007, 19008 };
		int[][] text = { { 4004, 4005 }, { 4016, 4017 }, { 4028, 4029 }, { 4006, 4007 }, { 4018, 4019 }, { 4030, 4031 }, { 4008, 4009 }, { 4020, 4021 }, { 4032, 4033 }, { 4010, 4011 }, { 4022, 4023 }, { 4034, 4035 }, { 4012, 4013 }, { 4024, 4025 }, { 4036, 4037 }, { 4014, 4015 }, { 4026, 4027 }, { 4038, 4039 }, { 4152, 4153 }, { 12166, 12167 },
				{ 13926, 13927 }, { 18165, 18169 }, { 18166, 18170 }, { 18167, 18171 }, { 18168, 18172 } };

		int[] icons = { 3965, 3966, 3967, 3968, 3969, 3970, 3971, 3972, 3973, 3974, 3975, 3976, 3977, 3978, 3979, 3980, 3981, 3982, 4151, 12165, 13925, 27227, 27228, 27229, 27230 };

		int[][] buttonCoords = { { 3, 5 }, { 65, 5 }, { 127, 5 }, { 3, 33 }, { 65, 33 }, { 127, 33 }, { 3, 61 }, { 65, 61 }, { 127, 61 }, { 3, 89 }, { 65, 89 }, { 127, 89 }, { 3, 117 }, { 65, 117 }, { 127, 117 }, { 3, 145 }, { 65, 145 }, { 127, 145 }, { 3, 173 }, { 65, 173 }, { 127, 173 }, { 3, 201 }, { 65, 201 }, { 127, 201 }, { 3, 229 } };
		int[][] iconCoords = { { 5, 7 }, { 68, 8 }, { 130, 7 }, { 8, 35 }, { 67, 34 }, { 130, 37 }, { 8, 65 }, { 66, 64 }, { 130, 62 }, { 6, 92 }, { 67, 95 }, { 132, 91 }, { 5, 119 }, { 69, 121 }, { 129, 119 }, { 5, 148 }, { 68, 147 }, { 131, 147 }, { 5, 174 }, { 68, 174 }, { 129, 175 }, { 5, 203 }, { 68, 202 }, { 130, 203 }, { 5, 231 } };
		int[][] textCoords = { { 29, 7, 44, 19 }, { 91, 7, 106, 19 }, { 153, 7, 168, 19 }, { 29, 35, 44, 47 }, { 91, 35, 106, 47 }, { 153, 35, 168, 47 }, { 29, 63, 44, 75 }, { 91, 63, 106, 75 }, { 153, 63, 168, 75 }, { 29, 91, 44, 103 }, { 91, 91, 106, 103 }, { 153, 91, 168, 103 }, { 29, 119, 44, 131 }, { 91, 119, 106, 131 },
				{ 153, 119, 168, 131 }, { 29, 147, 44, 159 }, { 91, 147, 106, 159 }, { 153, 147, 168, 159 }, { 29, 175, 44, 187 }, { 91, 175, 106, 187 }, { 153, 175, 168, 187 }, { 29, 203, 44, 215 }, { 91, 203, 106, 215 }, { 153, 203, 168, 215 }, { 29, 231, 44, 243 } };
		int[][] newText = { { 18165, 18166, 18167, 18168 }, { 18169, 18170, 18171, 18172 } };
		for (int i = 0; i < hovers.length; i++) {
			createSkillHover(hovers[i], 205 + i);
			addSkillButton(buttons[i], "View @or1@Skill@whi@ guide");
			addHoverButton(79924 + i, sprites[107], 0, 60, 27, "Set Level Goal", 997, -1, 1);
			addHoverButton(79949 + i, sprites[107], 0, 60, 27, "Set XP Goal", 998, -1, 1);
			addHoverButton(79974 + i, sprites[107], 0, 60, 27, "Clear Goal", 999, -1, 1);
			addImage(icons[i], sprites[spriteNames[i]]);
		}
		for (int i = 0; i < 4; i++) {
			addSkillText(newText[0][i], false, i + 21);
			addSkillText(newText[1][i], true, i + 21);
		}
		skill.children(icons.length + text.length * 2 + hovers.length + buttons.length * 4 + 1);
		int frame = 0;
		RSInterface totalLevel = interfaceCache[3984];
		totalLevel.message = "@yel@Total Level: %1";
		totalLevel.textDrawingAreas = fonts[2];
		skill.child(frame++, 3984, 95, 237);
		for (int i = 0; i < buttons.length; i++) {
			skill.child(frame++, 79974 + i, buttonCoords[i][0], buttonCoords[i][1]);
			skill.child(frame++, 79949 + i, buttonCoords[i][0], buttonCoords[i][1]);
			skill.child(frame++, 79924 + i, buttonCoords[i][0], buttonCoords[i][1]);
			skill.child(frame++, buttons[i], buttonCoords[i][0], buttonCoords[i][1]);
		}
		for (int i = 0; i < icons.length; i++) {
			skill.child(frame++, icons[i], iconCoords[i][0], iconCoords[i][1]);
			skill.child(frame++, text[i][0], textCoords[i][0], textCoords[i][1]);
			skill.child(frame++, text[i][1], textCoords[i][2], textCoords[i][3]);
		}
		for (int i = 0; i < icons.length; i++) {
			skill.child(frame++, hovers[i], buttonCoords[i][0], buttonCoords[i][1]);
		}
	}

	private static Sprite CustomSpriteLoader(int id, String s) {
		long l = (TextClass.method585(s) << 8) + (long) id;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite("/Attack/" + id + s);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public static void addButton(int i, int j, String s, String s1, int k, int l, int i1, int j1) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.id = i;
		rsinterface.parentID = i;
		rsinterface.type = 5;
		rsinterface.atActionType = l;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.hoverType = k;
		rsinterface.sprite1 = imageLoader(j, s);
		rsinterface.sprite2 = imageLoader(j, s);
		rsinterface.width = i1;
		rsinterface.height = j1;
		rsinterface.tooltip = s1;
		rsinterface.inventoryhover = true;
	}
	
	

	public static RSInterface addInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.id = id;
		rsi.parentID = id;
		rsi.width = 512;
		rsi.height = 334;
		return rsi;
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		if (centered)
			rsi.centerText = true;
		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.textColor = color;
		rsi.id = id;
		rsi.type = 4;
	}

	public static void textColor(int id, int color) {
		RSInterface rsi = interfaceCache[id];
		rsi.textColor = color;
	}

	public static void textSize(int id, TextDrawingArea tda[], int idx) {
		RSInterface rsi = interfaceCache[id];
		rsi.textDrawingAreas = tda[idx];
	}

	public static void addCacheSprite(int id, int sprite1, int sprite2, String sprites) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = method207(sprite1, aClass44, sprites);
		rsi.sprite2 = method207(sprite2, aClass44, sprites);
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}

	public static void sprite1(int id, int sprite) {
		RSInterface class9 = interfaceCache[id];
		class9.sprite1 = CustomSpriteLoader(sprite, "");
	}

	public static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		if (sprite2 == sprite)
			rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		else
			rsi.sprite2 = CustomSpriteLoader(sprite2, "");
		rsi.tooltip = s;
		rsi.contentType = 0;
		rsi.atActionType = 1;
		rsi.width = width;
		rsi.hoverType = 52;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
	}

	public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		rsi.requiredValues = new int[1];
		rsi.requiredValues[0] = 1;
		rsi.valueCompareType = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.hoverType = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public void totalChildren(int id, int x, int y) {
		children = new int[id];
		childX = new int[x];
		childY = new int[y];
	}

	public static void removeSomething(int id) {
		@SuppressWarnings("unused")
		RSInterface rsi = interfaceCache[id] = new RSInterface();
	}

	public void specialBar(int id) // 7599
	{
		/*
		 * addActionButton(ID, SpriteOFF, SpriteON, Width, Height,
		 * "SpriteText");
		 */
		addActionButton(id - 12, 7587, -1, 150, 26, "Use @gre@Special Attack");
		/* removeSomething(ID); */
		for (int i = id - 11; i < id; i++)
			removeSomething(i);

		RSInterface rsi = interfaceCache[id - 12];
		rsi.width = 150;
		rsi.height = 26;

		rsi = interfaceCache[id];
		rsi.width = 150;
		rsi.height = 26;

		rsi.child(0, id - 12, 0, 0);

		rsi.child(12, id + 1, 3, 7);

		rsi.child(23, id + 12, 16, 8);

		for (int i = 13; i < 23; i++) {
			rsi.childY[i] -= 1;
		}

		rsi = interfaceCache[id + 1];
		rsi.type = 5;
		rsi.sprite1 = CustomSpriteLoader(7600, "");

		for (int i = id + 2; i < id + 12; i++) {
			rsi = interfaceCache[i];
			rsi.type = 5;
		}

		sprite1(id + 2, 7601);
		sprite1(id + 3, 7602);
		sprite1(id + 4, 7603);
		sprite1(id + 5, 7604);
		sprite1(id + 6, 7605);
		sprite1(id + 7, 7606);
		sprite1(id + 8, 7607);
		sprite1(id + 9, 7608);
		sprite1(id + 10, 7609);
		sprite1(id + 11, 7610);
	}

	public static void Sidebar0(TextDrawingArea[] tda) {
		/*
		 * Sidebar0a(id, id2, id3, "text1", "text2", "text3", "text4", str1x,
		 * str1y, str2x, str2y, str3x, str3y, str4x, str4y, img1x, img1y, img2x,
		 * img2y, img3x, img3y, img4x, img4y, tda);
		 */
		Sidebar0a(1698, 1701, 7499, "Chop", "Hack", "Smash", "Block", 42, 75, 127, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(2276, 2279, 7574, "Stab", "Lunge", "Slash", "Block", 43, 75, 124, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(2423, 2426, 7599, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(3796, 3799, 7624, "Pound", "Pummel", "Spike", "Block", 39, 75, 121, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(4679, 4682, 7674, "Lunge", "Swipe", "Pound", "Block", 40, 75, 124, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(4705, 4708, 7699, "Chop", "Slash", "Smash", "Block", 42, 75, 125, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(5570, 5573, 7724, "Spike", "Impale", "Smash", "Block", 41, 75, 123, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(7762, 7765, 7800, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		/*
		 * Sidebar0b(id, id2, "text1", "text2", "text3", "text4", str1x, str1y,
		 * str2x, str2y, str3x, str3y, str4x, str4y, img1x, img1y, img2x, img2y,
		 * img3x, img3y, img4x, img4y, tda);
		 */
		Sidebar0b(776, 779, "Reap", "Chop", "Jab", "Block", 42, 75, 126, 75, 46, 128, 125, 128, 122, 103, 122, 50, 40, 103, 40, 50, tda);
		/*
		 * Sidebar0c(id, id2, id3, "text1", "text2", "text3", str1x, str1y,
		 * str2x, str2y, str3x, str3y, img1x, img1y, img2x, img2y, img3x, img3y,
		 * tda);
		 */
		Sidebar0c(425, 428, 7474, "Pound", "Pummel", "Block", 39, 75, 121, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(1749, 1752, 7524, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(1764, 1767, 7549, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(4446, 4449, 7649, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(5855, 5857, 7749, "Punch", "Kick", "Block", 40, 75, 129, 75, 42, 128, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0c(6103, 6132, 6117, "Bash", "Pound", "Block", 43, 75, 124, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(8460, 8463, 8493, "Jab", "Swipe", "Fend", 46, 75, 124, 75, 43, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(12290, 12293, 12323, "Flick", "Lash", "Deflect", 44, 75, 127, 75, 36, 128, 40, 50, 40, 103, 122, 50, tda);
		/*
		 * Sidebar0d(id, id2, "text1", "text2", "text3", str1x, str1y, str2x,
		 * str2y, str3x, str3y, img1x, img1y, img2x, img2y, img3x, img3y, tda);
		 */
		Sidebar0d(328, 331, "Bash", "Pound", "Focus", 42, 66, 39, 101, 41, 136, 40, 120, 40, 50, 40, 85, tda);

		RSInterface rsi = addInterface(19300);
		/* textSize(ID, wid, Size); */
		textSize(3983, tda, 0);
		/* addToggleButton(id, sprite, config, width, height, wid); */
		addToggleButton(150, 150, 172, 150, 44, "Auto Retaliate");

		rsi.totalChildren(2, 2, 2);
		rsi.child(0, 3983, 52, 25); // combat level
		rsi.child(1, 150, 21, 153); // auto retaliate

		rsi = interfaceCache[3983];
		rsi.centerText = true;
		rsi.textColor = 0xff981f;
	}
	
	public static void addBobStorage(int index) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];

		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];

		rsi.actions[0] = "Take 1";
		rsi.actions[1] = "Take 5";
		rsi.actions[2] = "Take 10";
		rsi.actions[3] = "Take All";
		rsi.actions[4] = "Take X";
		rsi.centerText = true;
		rsi.aBoolean227 = false;
		rsi.aBoolean235 = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.aBoolean259 = true;
		rsi.textShadow = false;
		rsi.invSpritePadX = 22;
		rsi.invSpritePadY = 21;
		rsi.height = 5;
		rsi.width = 6;
		rsi.parentID = 2702;
		rsi.id = 2700;
		rsi.type = 2;
	}

	public static void Sidebar0a(int id, int id2, int id3, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
																																																																															// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addText(id2 + 14, text4, tda, 0, 0xff981f, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.sprite1 = CustomSpriteLoader(19301, "");
			rsi.sprite2 = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0b(int id, int id2, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
																																																																													// nospec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addText(id2 + 14, text4, tda, 0, 0xff981f, false);

		rsi.width = 190;
		rsi.height = 261;

		int last = 14;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.sprite1 = CustomSpriteLoader(19301, "");
			rsi.sprite2 = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0c(int id, int id2, int id3, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) // 3button
																																																																// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addText(id2 + 11, text3, tda, 0, 0xff981f, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 12;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 99);
		frame++;
		rsi.child(frame, id2 + 4, 105, 46);
		frame++;
		rsi.child(frame, id2 + 5, 21, 46);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // chop
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // slash
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // lunge

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 6; i++) {
			rsi = interfaceCache[i];
			rsi.sprite1 = CustomSpriteLoader(19301, "");
			rsi.sprite2 = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0d(int id, int id2, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) // 3button
																																																														// nospec
																																																														// (magic
																																																														// intf)
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addText(id2 + 11, text3, tda, 0, 0xff981f, false);

		// addText(353, "Spell", tda, 0, 0xff981f, false);
		removeSomething(353);
		addText(354, "Spell", tda, 0, 0xff981f, false);

		addCacheSprite(337, 19, 0, "combaticons");
		addCacheSprite(338, 13, 0, "combaticons2");
		addCacheSprite(339, 14, 0, "combaticons2");

		/* addToggleButton(id, sprite, config, width, height, tooltip); */
		// addToggleButton(349, 349, 108, 68, 44, "Select");
		removeSomething(349);
		addToggleButton(350, 350, 108, 68, 44, "Select");

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 20, 115);
		frame++;
		rsi.child(frame, id2 + 4, 20, 80);
		frame++;
		rsi.child(frame, id2 + 5, 20, 45);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // bash
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // pound
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // focus

		rsi.child(frame, 349, 105, 46);
		frame++; // spell1
		rsi.child(frame, 350, 104, 106);
		frame++; // spell2

		rsi.child(frame, 353, 125, 74);
		frame++; // spell
		rsi.child(frame, 354, 125, 134);
		frame++; // spell

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
	}

	public static void emoteTab() {
		RSInterface tab = addTabInterface(147);
		RSInterface scroll = addTabInterface(148);
		tab.totalChildren(1);
		tab.child(0, 148, 0, 1);
		addButton(168, 0, "/Emotes/EMOTE", "Yes", 41, 47);
		addButton(169, 1, "/Emotes/EMOTE", "No", 41, 47);
		addButton(164, 2, "/Emotes/EMOTE", "Bow", 41, 47);
		addButton(165, 3, "/Emotes/EMOTE", "Angry", 41, 47);
		addButton(162, 4, "/Emotes/EMOTE", "Think", 41, 47);
		addButton(163, 5, "/Emotes/EMOTE", "Wave", 41, 47);
		addButton(13370, 6, "/Emotes/EMOTE", "Shrug", 41, 47);
		addButton(171, 7, "/Emotes/EMOTE", "Cheer", 41, 47);
		addButton(167, 8, "/Emotes/EMOTE", "Beckon", 41, 47);
		addButton(170, 9, "/Emotes/EMOTE", "Laugh", 41, 47);
		addButton(13366, 10, "/Emotes/EMOTE", "Jump for Joy", 41, 47);
		addButton(13368, 11, "/Emotes/EMOTE", "Yawn", 41, 47);
		addButton(166, 12, "/Emotes/EMOTE", "Dance", 41, 47);
		addButton(13363, 13, "/Emotes/EMOTE", "Jig", 41, 47);
		addButton(13364, 14, "/Emotes/EMOTE", "Spin", 41, 47);
		addButton(13365, 15, "/Emotes/EMOTE", "Headbang", 41, 47);
		addButton(161, 16, "/Emotes/EMOTE", "Cry", 41, 47);
		addButton(11100, 17, "/Emotes/EMOTE", "Blow kiss", 41, 47);
		addButton(13362, 18, "/Emotes/EMOTE", "Panic", 41, 47);
		addButton(13367, 19, "/Emotes/EMOTE", "Raspberry", 41, 47);
		addButton(172, 20, "/Emotes/EMOTE", "Clap", 41, 47);
		addButton(13369, 21, "/Emotes/EMOTE", "Salute", 41, 47);
		addButton(13383, 22, "/Emotes/EMOTE", "Goblin Bow", 41, 47);
		addButton(13384, 23, "/Emotes/EMOTE", "Goblin Salute", 41, 47);
		addButton(667, 24, "/Emotes/EMOTE", "Glass Box", 41, 47);
		addButton(6503, 25, "/Emotes/EMOTE", "Climb Rope", 41, 47);
		addButton(6506, 26, "/Emotes/EMOTE", "Lean On Air", 41, 47);
		addButton(666, 27, "/Emotes/EMOTE", "Glass Wall", 41, 47);
		addButton(18464, 28, "/Emotes/EMOTE", "Zombie Walk", 41, 47);
		addButton(18465, 29, "/Emotes/EMOTE", "Zombie Dance", 41, 47);
		addButton(15166, 30, "/Emotes/EMOTE", "Scared", 41, 47);
		addButton(18686, 31, "/Emotes/EMOTE", "Rabbit Hop", 41, 47);
		addConfigButton(154, 147, 32, 33, "EMOTE", 41, 47, "Skillcape Emote", 0, 1, 700);
		scroll.totalChildren(33);
		scroll.child(0, 168, 10, 7);
		scroll.child(1, 169, 54, 7);
		scroll.child(2, 164, 98, 14);
		scroll.child(3, 165, 137, 7);
		scroll.child(4, 162, 9, 56);
		scroll.child(5, 163, 48, 56);
		scroll.child(6, 13370, 95, 56);
		scroll.child(7, 171, 137, 56);
		scroll.child(8, 167, 7, 105);
		scroll.child(9, 170, 51, 105);
		scroll.child(10, 13366, 95, 104);
		scroll.child(11, 13368, 139, 105);
		scroll.child(12, 166, 6, 154);
		scroll.child(13, 13363, 50, 154);
		scroll.child(14, 13364, 90, 154);
		scroll.child(15, 13365, 135, 154);
		scroll.child(16, 161, 8, 204);
		scroll.child(17, 11100, 51, 203);
		scroll.child(18, 13362, 99, 204);
		scroll.child(19, 13367, 137, 203);
		scroll.child(20, 172, 10, 253);
		scroll.child(21, 13369, 53, 253);
		scroll.child(22, 13383, 88, 258);
		scroll.child(23, 13384, 138, 252);
		scroll.child(24, 667, 2, 303);
		scroll.child(25, 6503, 49, 302);
		scroll.child(26, 6506, 93, 302);
		scroll.child(27, 666, 137, 302);
		scroll.child(28, 18464, 9, 352);
		scroll.child(29, 18465, 50, 352);
		scroll.child(30, 15166, 94, 356);
		scroll.child(31, 18686, 141, 353);
		scroll.child(32, 154, 5, 401);
		scroll.width = 173;
		scroll.height = 258;
		scroll.scrollMax = 450;
	}

	public static void optionTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(904);
		RSInterface energy = interfaceCache[149];
		energy.textColor = 0xff9933;
		addSprite(905, 9, "/Options/SPRITE");
		addSprite(907, 18, "/Options/SPRITE");
		addSprite(909, 29, "/Options/SPRITE");
		addSprite(951, 32, "/Options/SPRITE");
		addSprite(953, 33, "/Options/SPRITE");
		addSprite(955, 34, "/Options/SPRITE");
		addSprite(947, 36, "/Options/SPRITE");
		addSprite(949, 35, "/Options/SPRITE");
		addSprite(36001, 4, "/Options/SPRITE");
		addSprite(36002, 4, "/Options/SPRITE");
		addSprite(36017, 4, "/Options/SPRITE");
		addConfigButton(152, 904, 30, 31, "/Options/SPRITE", 40, 40, "Toggle-run", 1, 5, 173);
		addConfigButton(913, 904, 30, 31, "/Options/SPRITE", 40, 40, "Toggle-Mouse Buttons", 0, 5, 170);
		addConfigButton(915, 904, 30, 31, "/Options/SPRITE", 40, 40, "Toggle-Chat Effects", 0, 5, 171);
		addConfigButton(957, 904, 30, 31, "/Options/SPRITE", 40, 40, "Toggle-Split Private Chat", 1, 5, 287);
		addConfigButton(12464, 904, 30, 31, "/Options/SPRITE", 40, 40, "Toggle-Accept Aid", 0, 5, 427);
		addConfigButton(906, 904, 10, 14, "/Options/SPRITE", 32, 16, "Dark", 1, 5, 166);
		addConfigButton(908, 904, 11, 15, "/Options/SPRITE", 32, 16, "Normal", 2, 5, 166);
		addConfigButton(910, 904, 12, 16, "/Options/SPRITE", 32, 16, "Bright", 3, 5, 166);
		addConfigButton(912, 904, 13, 17, "/Options/SPRITE", 32, 16, "Very Bright", 4, 5, 166);

		addHoverButton(36004, "/Options/SPRITE", 37, 54, 43, "Fixed", -1, 36005, 1);
		addHoveredButton(36005, "/Options/SPRITE", 38, 54, 43, 36006);

		addHoverButton(36007, "/Options/SPRITE", 39, 54, 43, "Resizable", -1, 36008, 1);
		addHoveredButton(36008, "/Options/SPRITE", 40, 54, 43, 36009);

		addHoverButton(36010, "/Options/SPRITE", 41, 54, 43, "Fullscreen", -1, 36011, 1);
		addHoveredButton(36011, "/Options/SPRITE", 42, 54, 43, 36012);

		addText(36016, "Game Settings", tda, 1, 0xff9933, true, true);
		addText(36003, "Game Mode", tda, 1, 0xff9933, true, true);
		addText(36013, "Fixed", tda, 0, 0xff9933, true, true);
		addText(36014, "Resizable", tda, 0, 0xff9933, true, true);
		addText(36015, "Fullscreen", tda, 0, 0xff9933, true, true);
		tab.totalChildren(30);
		tab.child(0, 913, 15, 153);
		tab.child(1, 955, 19, 159);
		tab.child(2, 915, 75, 153);
		tab.child(3, 953, 79, 160);
		tab.child(4, 957, 135, 153);
		tab.child(5, 951, 139, 159);
		tab.child(6, 12464, 45, 208);
		tab.child(7, 949, 50, 213);
		tab.child(8, 152, 105, 208);
		tab.child(9, 947, 117, 212);
		tab.child(10, 149, 113, 231);
		tab.child(11, 36001, 0, 18);
		tab.child(12, 36002, 0, 85);
		tab.child(13, 36003, 93, 3);
		tab.child(14, 36004, 9, 26);
		tab.child(15, 36005, 9, 26);
		tab.child(16, 36007, 70, 26);
		tab.child(17, 36008, 70, 26);
		tab.child(18, 36010, 131, 26);
		tab.child(19, 36011, 131, 26);
		tab.child(20, 36013, 37, 72);
		tab.child(21, 36014, 97, 72);
		tab.child(22, 36015, 157, 72);
		tab.child(23, 36016, 93, 88);
		tab.child(24, 36017, 0, 106);
		tab.child(25, 905, 13, 112);
		tab.child(26, 906, 48, 120);
		tab.child(27, 908, 80, 120);
		tab.child(28, 910, 112, 120);
		tab.child(29, 912, 144, 120);
	}

	public static void addButton(int id, Sprite sprite, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = sprite;
		tab.sprite2 = sprite;
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public int transparency;

    public static void addTransparentSprite(int i, int j, String s) {
        RSInterface rsinterface = interfaceCache[i] = new RSInterface();
        rsinterface.id = i;
        rsinterface.parentID = i;
        rsinterface.type = 5;
        rsinterface.atActionType = 0;
        rsinterface.contentType = 0;
        rsinterface.aByte254 = 0;
        rsinterface.hoverType = 52;
        rsinterface.sprite1 = imageLoader(j, s);
        rsinterface.sprite2 = imageLoader(j, s);
        rsinterface.width = 512;
        rsinterface.height = 334;
        rsinterface.drawsTransparent = true;
    }
	
    public static void addTransparentSprite(int id, int spriteId,String spriteName, int i) {
            RSInterface tab = interfaceCache[id] = new RSInterface();
            tab.id = id;
            tab.parentID = id;
            tab.type = 5;
            tab.atActionType = 0;
            tab.contentType = 0;
            tab.aByte254 = (byte) 0;
            tab.hoverType = 52;
            tab.sprite1 = imageLoader(spriteId, spriteName);
            tab.sprite2 = imageLoader(spriteId, spriteName);
            tab.width = 512;
            tab.height = 334;
            tab.drawsTransparent = true;
    }

	public String hoverText;

	public static void addHoverBox(int id, int ParentID, String text, String text2, int configId, int configFrame) {
		RSInterface rsi = addTabInterface(id);
		rsi.id = id;
		rsi.parentID = ParentID;
		rsi.type = 8;
		rsi.aString228 = text;
		rsi.message = text2;
		rsi.valueCompareType = new int[1];
		rsi.requiredValues = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.requiredValues[0] = configId;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configFrame;
		rsi.valueIndexArray[0][2] = 0;
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.hoverType = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.aString228 = "";
		RSInterface.textColor = k;
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}
	
	public static void addButton2(int id, int sid, String spriteName, String tooltip, int mOver, int atAction, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = atAction;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.hoverType = mOver;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = tooltip;
		tab.inventoryhover = true;
	}

	public static void addConfigButton(int ID, int pID, int bID, int bID2, String bName, int width, int height, String tT, int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.aByte254 = 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(bID, bName);
		Tab.sprite2 = imageLoader(bID2, bName);
		Tab.tooltip = tT;
	}

	public static void addSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName == null ? "" : spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName == null ? "" : spriteName);
		tab.width = 512;
		tab.height = 334;
	}

	public static void addInAreaHover(int i, String imageName, int sId, int sId2, int w, int h, String text, int contentType, int actionType) {//hoverable button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;//Sprite
		tab.atActionType = actionType;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = i;
		if(sId != -1)
			tab.sprite1 = imageLoader(sId, imageName);
		tab.sprite2 = imageLoader(sId2, imageName);
		tab.width = w;
		tab.height = h;
		tab.tooltip = text;
	}

	public static void addHoverButton(int i, String imageName, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
																																					// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.aByte254 = 0;
		tab.hoverType = hoverOver;
		tab.sprite1 = imageLoader(j, imageName);
		tab.sprite2 = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoverButton(int i, Sprite sprite, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
																																				// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.aByte254 = 0;
		tab.hoverType = hoverOver;
		tab.sprite1 = sprite;
		tab.sprite2 = sprite;
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w, int h, int IMAGEID) {// hoverable
																									// button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.aByte254 = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j, imageName);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverButton1(int i, String imageName, int j, int width, int height, String text, int hoverOver, int aT) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.interfaceType = 5;
		tab.atActionType = aT;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.sprite1 = imageLoader(j, imageName);
		tab.sprite2 = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoverImage(int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = 0;
		tab.hoverType = 52;
		tab.sprite1 = imageLoader(j, name);
		tab.sprite2 = imageLoader(k, name);
	}

	public static RSInterface addScreenInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = (byte) 0;
		tab.hoverType = 0;
		return tab;
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.aByte254 = (byte) 0;
		tab.hoverType = -1;// Int 230
		return tab;
	}

	protected static Sprite imageLoader(int i, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(s + " " + i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	private Model method206(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null)
			return model;
		if (i == 1)
			model = Model.method462(j);
		if (i == 2)
			model = EntityDefinition.forID(j).method160();
		if (i == 3)
			model = Client.myPlayer.method453();
		if (i == 4)
			model = ItemDefinition.forID(j).method202(50);
		if (i == 5)
			model = null;
		if (model != null)
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		return model;
	}

	private static Sprite method207(int i, Archive jagexArchive, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(jagexArchive, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void method208(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag)
			return;
		aMRUNodes_264.unlinkAll();
		if (model != null && j != 4)
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag)
			model = method206(anInt255, anInt256);
		else
			model = method206(anInt233, mediaID);
		if (model == null)
			return null;
		if (k == -1 && j == -1 && model.triangleTexture == null)
			return model;
		Model model_1 = new Model(true, Class36.method532(k) & Class36.method532(j), false, model);
		if (k != -1 || j != -1)
			model_1.createBones();
		if (k != -1)
			model_1.applyAnimation(k);
		if (j != -1)
			model_1.applyAnimation(j);
		model_1.method479(84, 1000, -90, -580, -90, true);
		return model_1;
	}

	public RSInterface() {
	}

	public static Archive aClass44;
	public boolean drawsTransparent;
	public static TextDrawingArea[] fonts;
	public Sprite sprite1;
	public int anInt208;
	public byte opacity;
	public boolean interfaceShown;
	public int interfaceType;
	public Sprite sprites[];
	public static RSInterface interfaceCache[];
	public int requiredValues[];
	public int contentType;// anInt214
	public int spritesX[];
	public int anInt216;
	public int atActionType;
	public String spellName;
	public int anInt219;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean centerText;
	public int scrollPosition;
	public String actions[];
	public int valueIndexArray[][];
	public boolean aBoolean227;
	public String aString228;
	public int hoverType;
	public int invSpritePadX;
	public int textColor;
	public int anInt233;
	public int mediaID;
	public boolean aBoolean235;
	public int parentID;
	public int spellUsableOn;
	private static MRUNodes aMRUNodes_238;
	public int anInt239;
	public int children[];
	public int childX[];
	public boolean usableItemInterface;
	public TextDrawingArea textDrawingAreas;
	public int invSpritePadY;
	public int valueCompareType[];
	public int anInt246;
	public int spritesY[];
	public String message;
	public boolean isInventoryInterface;
	public int id;
	public int invStackSizes[];
	public int inv[];
	public byte aByte254;
	private int anInt255;
	private int anInt256;
	public int anInt257;
	public int anInt258;
	public boolean aBoolean259;
	public int itemSpriteId1 = -1, itemSpriteId2 = -1, itemSpriteZoom1 = -1, itemSpriteZoom2 = -1, itemSpriteIndex = 0;
	public boolean greyScale;
	public Sprite sprite2;
	public int scrollMax;
	public int type;
	public boolean inventoryhover;
	public int anInt263;
	private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
	public int anInt265;
	public boolean isMouseoverTriggered;
	public int height;
	public boolean textShadow;
	public int modelZoom;
	public int modelRotation1;
	public int modelRotation2;
	public int childY[];

	@SuppressWarnings("unused")
	private static Sprite LoadLunarSprite(int i, String s) {
		Sprite sprite = imageLoader(i, "/Lunar/" + s);
		return sprite;
	}

	public static void addLunarSprite(int i, int j, String name) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = imageLoader(j, name);
		RSInterface.width = 500;
		RSInterface.height = 500;
		RSInterface.tooltip = "";
	}

	public static void drawRune(int i, int id, String runeName) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = imageLoader(id, "Lunar/RUNE");
		RSInterface.width = 500;
		RSInterface.height = 500;
	}

	public static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 4;
		rsInterface.atActionType = 0;
		rsInterface.contentType = 0;
		rsInterface.width = 0;
		rsInterface.height = 14;
		rsInterface.aByte254 = 0;
		rsInterface.hoverType = -1;
		rsInterface.valueCompareType = new int[1];
		rsInterface.requiredValues = new int[1];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = runeAmount;
		rsInterface.valueIndexArray = new int[1][4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = RuneID;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.centerText = true;
		rsInterface.textDrawingAreas = font[0];
		rsInterface.textShadow = true;
		rsInterface.message = "%1/" + runeAmount + "";
		rsInterface.aString228 = "";
		rsInterface.textColor = 12582912;
		rsInterface.anInt219 = 49152;
	}

	public static void homeTeleport() {
		RSInterface RSInterface = addInterface(30000);
		RSInterface.tooltip = "Cast @gre@Lunar Home Teleport";
		RSInterface.id = 30000;
		RSInterface.parentID = 30000;
		RSInterface.type = 5;
		RSInterface.atActionType = 5;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.hoverType = 30001;
		RSInterface.sprite1 = imageLoader(1, "Lunar/SPRITE");
		RSInterface.width = 20;
		RSInterface.height = 20;
		RSInterface Int = addInterface(30001);
		Int.isMouseoverTriggered = true;
		Int.hoverType = -1;
		setChildren(1, Int);
		addLunarSprite(30002, 0, "SPRITE");
		setBounds(30002, 0, 0, 0, Int);
	}

	public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast On";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[3];
		rsInterface.requiredValues = new int[3];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = lvl;
		rsInterface.valueIndexArray = new int[3][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[3];
		rsInterface.valueIndexArray[2][0] = 1;
		rsInterface.valueIndexArray[2][1] = 6;
		rsInterface.valueIndexArray[2][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(7, INT);
		addLunarSprite(ID + 2, 0, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 37, 35, 3, INT);// Rune
		setBounds(rune1, 112, 35, 4, INT);// Rune
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 50, 66, 5, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 123, 66, 6, INT);
	}

	public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 0, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 14, 35, 3, INT);
		setBounds(rune1, 74, 35, 4, INT);
		setBounds(rune2, 130, 35, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 66, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 66, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 66, 8, INT);
	}

	public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 1, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 21, 2, INT);
		setBounds(30016, 14, 48, 3, INT);
		setBounds(rune1, 74, 48, 4, INT);
		setBounds(rune2, 130, 48, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 79, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 79, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 79, 8, INT);
	}

	public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 2, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 34, 2, INT);
		setBounds(30016, 14, 61, 3, INT);
		setBounds(rune1, 74, 61, 4, INT);
		setBounds(rune2, 130, 61, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 92, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 92, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 92, 8, INT);
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	public static void configureLunar(TextDrawingArea[] TDA) {
		homeTeleport();
		constructLunar();
		drawRune(30003, 1, "Fire");
		drawRune(30004, 2, "Water");
		drawRune(30005, 3, "Air");
		drawRune(30006, 4, "Earth");
		drawRune(30007, 5, "Mind");
		drawRune(30008, 6, "Body");
		drawRune(30009, 7, "Death");
		drawRune(30010, 8, "Nature");
		drawRune(30011, 9, "Chaos");
		drawRune(30012, 10, "Law");
		drawRune(30013, 11, "Cosmic");
		drawRune(30014, 12, "Blood");
		drawRune(30015, 13, "Soul");
		drawRune(30016, 14, "Astral");
		addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Bake Pie", "Bake pies without a stove", TDA, 0, 16, 2);
		addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65, "Cure Plant", "Cure disease on farming patch", TDA, 1, 4, 2);
		addLunar3RunesBigBox(30032, 9075, 564, 558, 0, 0, 0, 30013, 30007, 65, "Monster Examine", "Detect the combat statistics of a\\nmonster", TDA, 2, 2, 2);
		addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "Npc Contact", "Speak with varied NPCs", TDA, 3, 0, 2);
		addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other", "Cure poisoned players", TDA, 4, 8, 2);
		addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify", "Fills certain vessels with water", TDA, 5, 0, 5);
		addLunar3RunesSmallBox(30064, 9075, 563, 557, 1, 0, 1, 30012, 30006, 68, "Moonclan Teleport", "Teleports you to moonclan island", TDA, 6, 0, 5);
		addLunar3RunesBigBox(30075, 9075, 563, 557, 1, 0, 3, 30012, 30006, 69, "Tele Group Moonclan", "Teleports players to Moonclan\\nisland", TDA, 7, 0, 5);
		addLunar3RunesSmallBox(30083, 9075, 563, 557, 1, 0, 5, 30012, 30006, 70, "Ourania Teleport", "Teleports you to ourania rune altar", TDA, 8, 0, 5);
		addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me", "Cures Poison", TDA, 9, 0, 5);
		addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70, "Hunter Kit", "Get a kit of hunting gear", TDA, 10, 0, 5);
		addLunar3RunesSmallBox(30106, 9075, 563, 555, 1, 0, 0, 30012, 30004, 71, "Waterbirth Teleport", "Teleports you to Waterbirth island", TDA, 11, 0, 5);
		addLunar3RunesBigBox(30114, 9075, 563, 555, 1, 0, 4, 30012, 30004, 72, "Tele Group Waterbirth", "Teleports players to Waterbirth\\nisland", TDA, 12, 0, 5);
		addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group", "Cures Poison on players", TDA, 13, 0, 5);
		addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74, "Stat Spy", "Cast on another player to see their\\nskill levels", TDA, 14, 8, 2);
		addLunar3RunesBigBox(30138, 9075, 563, 554, 1, 1, 2, 30012, 30003, 74, "Barbarian Teleport", "Teleports you to the Barbarian\\noutpost", TDA, 15, 0, 5);
		addLunar3RunesBigBox(30146, 9075, 563, 554, 1, 1, 5, 30012, 30003, 75, "Tele Group Barbarian", "Teleports players to the Barbarian\\noutpost", TDA, 16, 0, 5);
		addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make", "Make glass without a furnace", TDA, 17, 16, 2);
		addLunar3RunesSmallBox(30162, 9075, 563, 555, 1, 1, 3, 30012, 30004, 77, "Khazard Teleport", "Teleports you to Port khazard", TDA, 18, 0, 5);
		addLunar3RunesSmallBox(30170, 9075, 563, 555, 1, 1, 7, 30012, 30004, 78, "Tele Group Khazard", "Teleports players to Port khazard", TDA, 19, 0, 5);
		addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78, "Dream", "Take a rest and restore hitpoints 3\\n times faster", TDA, 20, 0, 5);
		addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery", "String amulets without wool", TDA, 21, 0, 5);
		addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80, "Stat Restore Pot\\nShare", "Share a potion with up to 4 nearby\\nplayers", TDA, 22, 0, 5);
		addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue", "Combine runes without a talisman", TDA, 23, 0, 5);
		addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82, "Fertile Soil", "Fertilise a farming patch with super\\ncompost", TDA, 24, 4, 2);
		addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83, "Boost Potion Share", "Shares a potion with up to 4 nearby\\nplayers", TDA, 25, 0, 5);
		addLunar3RunesSmallBox(30226, 9075, 563, 555, 2, 2, 9, 30012, 30004, 84, "Fishing Guild Teleport", "Teleports you to the fishing guild", TDA, 26, 0, 5);
		addLunar3RunesLargeBox(30234, 9075, 563, 555, 1, 2, 13, 30012, 30004, 85, "Tele Group Fishing Guild", "Teleports players to the Fishing\\nGuild", TDA, 27, 0, 5);
		addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make", "Turn Logs into planks", TDA, 28, 16, 5);
		addLunar3RunesSmallBox(30250, 9075, 563, 555, 2, 2, 9, 30012, 30004, 86, "Catherby Teleport", "Teleports you to Catherby", TDA, 29, 0, 5);
		addLunar3RunesSmallBox(30258, 9075, 563, 555, 2, 2, 14, 30012, 30004, 87, "Tele Group Catherby", "Teleports players to Catherby", TDA, 30, 0, 5);
		addLunar3RunesSmallBox(30266, 9075, 563, 555, 2, 2, 7, 30012, 30004, 88, "Ice Plateau Teleport", "Teleports you to Ice Plateau", TDA, 31, 0, 5);
		addLunar3RunesLargeBox(30274, 9075, 563, 555, 2, 2, 15, 30012, 30004, 89, "Tele Group Ice Plateau", "Teleports players to Ice Plateau", TDA, 32, 0, 5);
		addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer", "Spend HP and SA energy to\\n give another SA and run energy", TDA, 33, 8, 2);
		addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other", "Transfer up to 75% of hitpoints\\n to another player", TDA, 34, 8, 2);
		addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other", "Allows another player to rebound\\ndamage to an opponent", TDA, 35, 8, 2);
		addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9, 30009, 30006, 93, "Vengeance", "Rebound damage to an opponent", TDA, 36, 0, 5);
		addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group", "Transfer up to 75% of hitpoints\\n to a group", TDA, 37, 0, 5);
		addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap", "Change to another spellbook for 1\\nspell cast", TDA, 38, 0, 5);
	}

	public static void constructLunar() {
		RSInterface Interface = addTabInterface(29999);
		setChildren(80, Interface);
		setBounds(30000, 11, 10, 0, Interface);
		setBounds(30017, 40, 9, 1, Interface);
		setBounds(30025, 71, 12, 2, Interface);
		setBounds(30032, 103, 10, 3, Interface);
		setBounds(30040, 135, 12, 4, Interface);
		setBounds(30048, 165, 10, 5, Interface);
		setBounds(30056, 8, 38, 6, Interface);
		setBounds(30064, 39, 39, 7, Interface);
		setBounds(30075, 71, 39, 8, Interface);
		setBounds(30083, 103, 39, 9, Interface);
		setBounds(30091, 135, 39, 10, Interface);
		setBounds(30099, 165, 37, 11, Interface);
		setBounds(30106, 12, 68, 12, Interface);
		setBounds(30114, 42, 68, 13, Interface);
		setBounds(30122, 71, 68, 14, Interface);
		setBounds(30130, 103, 68, 15, Interface);
		setBounds(30138, 135, 68, 16, Interface);
		setBounds(30146, 165, 68, 17, Interface);
		setBounds(30154, 14, 97, 18, Interface);
		setBounds(30162, 42, 97, 19, Interface);
		setBounds(30170, 71, 97, 20, Interface);
		setBounds(30178, 101, 97, 21, Interface);
		setBounds(30186, 135, 98, 22, Interface);
		setBounds(30194, 168, 98, 23, Interface);
		setBounds(30202, 11, 125, 24, Interface);
		setBounds(30210, 42, 124, 25, Interface);
		setBounds(30218, 74, 125, 26, Interface);
		setBounds(30226, 103, 125, 27, Interface);
		setBounds(30234, 135, 125, 28, Interface);
		setBounds(30242, 164, 126, 29, Interface);
		setBounds(30250, 10, 155, 30, Interface);
		setBounds(30258, 42, 155, 31, Interface);
		setBounds(30266, 71, 155, 32, Interface);
		setBounds(30274, 103, 155, 33, Interface);
		setBounds(30282, 136, 155, 34, Interface);
		setBounds(30290, 165, 155, 35, Interface);
		setBounds(30298, 13, 185, 36, Interface);
		setBounds(30306, 42, 185, 37, Interface);
		setBounds(30314, 71, 184, 38, Interface);
		setBounds(30322, 104, 184, 39, Interface);
		setBounds(30001, 6, 184, 40, Interface);// hover
		setBounds(30018, 5, 176, 41, Interface);// hover
		setBounds(30026, 5, 176, 42, Interface);// hover
		setBounds(30033, 5, 163, 43, Interface);// hover
		setBounds(30041, 5, 176, 44, Interface);// hover
		setBounds(30049, 5, 176, 45, Interface);// hover
		setBounds(30057, 5, 176, 46, Interface);// hover
		setBounds(30065, 5, 176, 47, Interface);// hover
		setBounds(30076, 5, 163, 48, Interface);// hover
		setBounds(30084, 5, 176, 49, Interface);// hover
		setBounds(30092, 5, 176, 50, Interface);// hover
		setBounds(30100, 5, 176, 51, Interface);// hover
		setBounds(30107, 5, 176, 52, Interface);// hover
		setBounds(30115, 5, 163, 53, Interface);// hover
		setBounds(30123, 5, 176, 54, Interface);// hover
		setBounds(30131, 5, 163, 55, Interface);// hover
		setBounds(30139, 5, 163, 56, Interface);// hover
		setBounds(30147, 5, 163, 57, Interface);// hover
		setBounds(30155, 5, 176, 58, Interface);// hover
		setBounds(30163, 5, 176, 59, Interface);// hover
		setBounds(30171, 5, 176, 60, Interface);// hover
		setBounds(30179, 5, 163, 61, Interface);// hover
		setBounds(30187, 5, 176, 62, Interface);// hover
		setBounds(30195, 5, 149, 63, Interface);// hover
		setBounds(30203, 5, 176, 64, Interface);// hover
		setBounds(30211, 5, 163, 65, Interface);// hover
		setBounds(30219, 5, 163, 66, Interface);// hover
		setBounds(30227, 5, 176, 67, Interface);// hover
		setBounds(30235, 5, 149, 68, Interface);// hover
		setBounds(30243, 5, 176, 69, Interface);// hover
		setBounds(30251, 5, 5, 70, Interface);// hover
		setBounds(30259, 5, 5, 71, Interface);// hover
		setBounds(30267, 5, 5, 72, Interface);// hover
		setBounds(30275, 5, 5, 73, Interface);// hover
		setBounds(30283, 5, 5, 74, Interface);// hover
		setBounds(30291, 5, 5, 75, Interface);// hover
		setBounds(30299, 5, 5, 76, Interface);// hover
		setBounds(30307, 5, 5, 77, Interface);// hover
		setBounds(30323, 5, 5, 78, Interface);// hover
		setBounds(30315, 5, 5, 79, Interface);// hover
	}

	public static void createSkillHover(int id, int x) {
		RSInterface hover = addInterface(id);
		hover.inventoryhover = true;
		hover.type = 8;
		hover.message = "Attack: 1/1";
		hover.contentType = x;
		hover.width = 60;
		hover.height = 28;
	}

	public void children(int total) {
		children = new int[total];
		childX = new int[total];
		childY = new int[total];
	}

	public static void addImage(int id, String s) {
		RSInterface image = addInterface(id);
		image.type = 5;
		image.atActionType = 0;
		image.contentType = 0;
		image.width = 100;
		image.height = 100;
		image.sprite1 = getSprite(s);
	}

	public static void addImage(int id, Sprite s) {
		RSInterface image = addInterface(id);
		image.type = 5;
		image.atActionType = 0;
		image.contentType = 0;
		image.width = 100;
		image.height = 100;
		image.sprite1 = s;
	}

	public static void addSkillText(int id, boolean max, int skill) {
		RSInterface text = addInterface(id);
		text.id = id;
		text.parentID = id;
		text.type = 4;
		text.atActionType = 0;
		text.width = 15;
		text.height = 12;
		text.textDrawingAreas = fonts[0];
		text.textShadow = true;
		text.centerText = true;
		text.textColor = 16776960;
		if (!max) {
			text.valueIndexArray = new int[1][];
			text.valueIndexArray[0] = new int[3];
			text.valueIndexArray[0][0] = 1;
			text.valueIndexArray[0][1] = skill;
			text.valueIndexArray[0][2] = 0;
		} else {
			text.valueIndexArray = new int[2][];
			text.valueIndexArray[0] = new int[3];
			text.valueIndexArray[0][0] = 1;
			text.valueIndexArray[0][1] = skill;
			text.valueIndexArray[0][2] = 0;
			text.valueIndexArray[1] = new int[1];
			text.valueIndexArray[1][0] = 0;
		}
		text.message = "%1";
	}

	public static Sprite getSprite(String s) {
		Sprite image = null;
		try {
			image = new Sprite(s);
			if (image != null) {
				return image;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public static void addSkillButton(int id, String toolTip) {
		RSInterface button = addInterface(id);
		button.type = 5;
		button.atActionType = 5;
		button.contentType = 0;
		button.width = 60;
		button.height = 27;
		button.sprite1 = CustomSpriteLoader(33225, "");
		button.sprite1 = getSprite("Skill/Button");
		button.tooltip = toolTip;
	}

	public static void setBounds(int ID, int X, int Y, int frame, RSInterface RSinterface) {
		RSinterface.children[frame] = ID;
		RSinterface.childX[frame] = X;
		RSinterface.childY[frame] = Y;
	}

	public static void addChar(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 180;
		t.height = 190;
		t.aByte254 = 0;
		t.hoverType = 0;
		t.modelZoom = 560;
		t.modelRotation1 = 30;
		t.modelRotation2 = 0;
		t.anInt257 = -1;
		t.anInt258 = -1;
	}
	
	public static void addHover(int i, int j, int k, int l, int i1, String s, int j1, int k1, 
            String s1)
    {
        RSInterface rsinterface = addTabInterface(i);
        rsinterface.id = i;
        rsinterface.parentID = i;
        rsinterface.type = 5;
        rsinterface.atActionType = j;
        rsinterface.contentType = k;
        rsinterface.hoverType = l;
        rsinterface.sprite1 = imageLoader(i1, s);
        rsinterface.sprite2 = imageLoader(i1, s);
        rsinterface.width = j1;
        rsinterface.height = k1;
        rsinterface.tooltip = s1;
    }

	/*public static void addHover(int i, int aT, int cT, int hoverid, int sId, String NAME, int W, int H, String tip) {
		RSInterface hover = addTabInterface(i);
		hover.id = i;
		hover.parentID = i;
		hover.type = 5;
		hover.atActionType = aT;
		hover.contentType = cT;
		hover.hoverType = hoverid;
		hover.sprite1 = imageLoader(sId, NAME);
		hover.sprite2 = imageLoader(sId, NAME);
		hover.width = W;
		hover.height = H;
		hover.tooltip = tip;
	}*/

	public static void addText(int id, String text, TextDrawingArea wid[], int idx, int color) {
		RSInterface rsinterface = addTab(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = false;
		rsinterface.textShadow = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0;
		rsinterface.anInt239 = 0;
	}

	public static RSInterface addTab(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id; // 250
		tab.parentID = id; // 236
		tab.type = 0; // 262
		tab.atActionType = 0; // 217
		tab.contentType = 0;
		tab.width = 512; // 220
		tab.height = 700; // 267
		tab.opacity = (byte) 0;
		tab.hoverType = -1; // Int 230
		return tab;
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = tab.sprite1.myWidth;
		tab.height = tab.sprite2.myHeight;
		tab.tooltip = tooltip;
	}

	public String popupString;
	public Sprite[] savedSprite = new Sprite[10];
	
	public static void addSprite(int id, int spriteId1, int spriteId2,
			String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.sprite1 = imageLoader(spriteId1, spriteName);
		tab.sprite2 = imageLoader(spriteId2, spriteName);
		for (int i = 0; i < 10; i++) {
			tab.savedSprite[i] = imageLoader(i, spriteName);
		}
		tab.width = 512;
		tab.height = 334;
	}
	
	public static void addTooltipBox(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 8;
		rsi.popupString = text;
	}

	public static void addTooltip(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.type = 0;
		rsi.isMouseoverTriggered = true;
		rsi.hoverType = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}

	public static void addHovered(int i, int j, String s, int k, int l, int i1)
    {
        RSInterface rsinterface = addTabInterface(i);
        rsinterface.parentID = i;
        rsinterface.id = i;
        rsinterface.type = 0;
        rsinterface.atActionType = 0;
        rsinterface.width = k;
        rsinterface.height = l;
        rsinterface.isMouseoverTriggered = true;
        rsinterface.hoverType = -1;
        addSprite(i1, j, s);
        setChildren(1, rsinterface);
        setBounds(i1, 0, 0, 0, rsinterface);
    }
	public static void addHovered(int i, int j, String imageName, int w, int h, int IMAGEID, String tt) {
		RSInterface hover = addTabInterface(i);
		hover.parentID = i;
		hover.id = i;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = w;
		hover.height = h;
		hover.isMouseoverTriggered = true;
		hover.hoverType = -1;
		addSprite(IMAGEID, j, imageName);
		setChildren(1, hover);
		setBounds(IMAGEID, 0, 0, 0, hover);
		hover.tooltip = tt;
	}

	public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2, String NAME, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId, int hoverSpriteId2, String hoverSpriteName, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.aByte254 = 0;
		hover.hoverType = hoverid;
		hover.sprite1 = imageLoader(spriteId, NAME);
		hover.sprite2 = imageLoader(spriteId2, NAME);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.isMouseoverTriggered = true;
		hover.hoverType = -1;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, hoverSpriteName, configId, configFrame);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void addBankHover1(int interfaceID, int actionType, int hoverid, int spriteId, String NAME, int Width, int Height, String Tooltip, int hoverId2, int hoverSpriteId, String hoverSpriteName, int hoverId3, String hoverDisabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.aByte254 = 0;
		hover.hoverType = hoverid;
		hover.sprite1 = imageLoader(spriteId, NAME);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.isMouseoverTriggered = true;
		hover.hoverType = -1;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId, hoverSpriteName, 0, 0);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverDisabledText, 0, 0);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void addSprite(int ID, int i, int i2, String name, int configId, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.aByte254 = (byte) 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(i, name);
		Tab.sprite2 = imageLoader(i2, name);
	}

	public void setSprite(Sprite sprite) {
		sprite1 = sprite;
	}

	public static void addButton(int i, int j, String name, int W, int H, String S, int AT) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = AT;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.hoverType = 52;
		RSInterface.sprite1 = imageLoader(j, name);
		RSInterface.sprite2 = imageLoader(j, name);
		RSInterface.width = W;
		RSInterface.height = H;
		RSInterface.tooltip = S;
	}

	public static void addHoverText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color, boolean center, boolean textShadow, int width) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadow;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0xffffff;
		rsinterface.anInt239 = 0;
		rsinterface.tooltip = tooltip;
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, int j) {
		RSInterface rsinterface = addInterface(i);
		rsinterface.parentID = i;
		rsinterface.id = i;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 0;
		rsinterface.height = 0;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.hoverType = a;
		rsinterface.centerText = l;
		rsinterface.textShadow = m;
		rsinterface.textDrawingAreas = RSInterface.fonts[j];
		rsinterface.message = s;
		rsinterface.textColor = k;
	}

	public static void lotteryItem(int i) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.actions = new String[5];
		rsinterface.spritesX = new int[36];
		rsinterface.invStackSizes = new int[36];
		rsinterface.inv = new int[36];
		rsinterface.spritesY = new int[36];
		rsinterface.children = new int[0];
		rsinterface.childX = new int[0];
		rsinterface.childY = new int[0];
		rsinterface.spritesY[0] = 0;
		rsinterface.spritesY[1] = 0;
		rsinterface.spritesY[2] = 0;
		rsinterface.spritesY[3] = 0;
		rsinterface.spritesY[4] = 0;
		rsinterface.spritesY[5] = 0;
		rsinterface.spritesY[6] = 0;
		rsinterface.spritesY[7] = 0;
		rsinterface.spritesY[8] = 0;
		rsinterface.spritesY[9] = 0;
		rsinterface.spritesY[10] = 0;
		rsinterface.spritesY[11] = 0;
		rsinterface.spritesY[12] = 0;
		rsinterface.spritesY[13] = 0;
		rsinterface.spritesY[14] = 0;
		rsinterface.spritesY[15] = 0;
		rsinterface.spritesY[16] = 0;
		rsinterface.spritesY[17] = 0;
		rsinterface.spritesY[18] = 0;
		rsinterface.spritesY[19] = 0;
		rsinterface.spritesY[20] = 0;
		rsinterface.spritesY[21] = 0;
		rsinterface.spritesY[22] = 0;
		rsinterface.spritesY[23] = 0;
		rsinterface.spritesY[24] = 0;
		rsinterface.spritesY[25] = 0;
		rsinterface.spritesY[26] = 0;
		rsinterface.spritesY[27] = 0;
		rsinterface.spritesY[28] = 0;
		rsinterface.spritesY[29] = 0;
		rsinterface.spritesY[30] = 0;
		rsinterface.spritesY[31] = 0;
		rsinterface.spritesY[32] = 0;
		rsinterface.spritesY[33] = 0;
		rsinterface.spritesY[34] = 0;
		rsinterface.spritesY[35] = 0;
		rsinterface.invStackSizes[0] = 0;
		rsinterface.invStackSizes[1] = 0;
		rsinterface.invStackSizes[2] = 0;
		rsinterface.invStackSizes[3] = 0;
		rsinterface.invStackSizes[4] = 0;
		rsinterface.invStackSizes[5] = 0;
		rsinterface.invStackSizes[6] = 0;
		rsinterface.invStackSizes[7] = 0;
		rsinterface.invStackSizes[8] = 0;
		rsinterface.invStackSizes[9] = 0;
		rsinterface.invStackSizes[10] = 0;
		rsinterface.invStackSizes[11] = 0;
		rsinterface.invStackSizes[12] = 0;
		rsinterface.invStackSizes[13] = 0;
		rsinterface.invStackSizes[14] = 0;
		rsinterface.invStackSizes[15] = 0;
		rsinterface.invStackSizes[16] = 0;
		rsinterface.invStackSizes[17] = 0;
		rsinterface.invStackSizes[18] = 0;
		rsinterface.invStackSizes[19] = 0;
		rsinterface.invStackSizes[20] = 0;
		rsinterface.invStackSizes[21] = 0;
		rsinterface.invStackSizes[22] = 0;
		rsinterface.invStackSizes[23] = 0;
		rsinterface.invStackSizes[24] = 0;
		rsinterface.invStackSizes[25] = 0;
		rsinterface.invStackSizes[26] = 0;
		rsinterface.invStackSizes[27] = 0;
		rsinterface.invStackSizes[28] = 0;
		rsinterface.invStackSizes[29] = 0;
		rsinterface.invStackSizes[30] = 0;
		rsinterface.invStackSizes[31] = 0;
		rsinterface.invStackSizes[32] = 0;
		rsinterface.invStackSizes[33] = 0;
		rsinterface.invStackSizes[34] = 0;
		rsinterface.invStackSizes[35] = 0;
		rsinterface.inv[0] = 0;
		rsinterface.inv[1] = 0;
		rsinterface.inv[2] = 0;
		rsinterface.inv[3] = 0;
		rsinterface.inv[4] = 0;
		rsinterface.inv[5] = 0;
		rsinterface.inv[6] = 0;
		rsinterface.inv[7] = 0;
		rsinterface.inv[8] = 0;
		rsinterface.inv[9] = 0;
		rsinterface.inv[10] = 0;
		rsinterface.inv[11] = 0;
		rsinterface.inv[12] = 0;
		rsinterface.inv[13] = 0;
		rsinterface.inv[14] = 0;
		rsinterface.inv[15] = 0;
		rsinterface.inv[16] = 0;
		rsinterface.inv[17] = 0;
		rsinterface.inv[18] = 0;
		rsinterface.inv[19] = 0;
		rsinterface.inv[20] = 0;
		rsinterface.inv[21] = 0;
		rsinterface.inv[22] = 0;
		rsinterface.inv[23] = 0;
		rsinterface.inv[24] = 0;
		rsinterface.inv[25] = 0;
		rsinterface.inv[26] = 0;
		rsinterface.inv[27] = 0;
		rsinterface.inv[28] = 0;
		rsinterface.inv[29] = 0;
		rsinterface.inv[30] = 0;
		rsinterface.inv[31] = 0;
		rsinterface.inv[32] = 0;
		rsinterface.inv[33] = 0;
		rsinterface.inv[34] = 0;
		rsinterface.inv[35] = 0;
		rsinterface.spritesX[0] = 0;
		rsinterface.spritesX[1] = 0;
		rsinterface.spritesX[2] = 0;
		rsinterface.spritesX[3] = 0;
		rsinterface.spritesX[4] = 0;
		rsinterface.spritesX[5] = 0;
		rsinterface.spritesX[6] = 0;
		rsinterface.spritesX[7] = 0;
		rsinterface.spritesX[8] = 0;
		rsinterface.spritesX[9] = 0;
		rsinterface.spritesX[10] = 0;
		rsinterface.spritesX[11] = 0;
		rsinterface.spritesX[12] = 0;
		rsinterface.spritesX[13] = 0;
		rsinterface.spritesX[14] = 0;
		rsinterface.spritesX[15] = 0;
		rsinterface.spritesX[16] = 0;
		rsinterface.spritesX[17] = 0;
		rsinterface.spritesX[18] = 0;
		rsinterface.spritesX[19] = 0;
		rsinterface.spritesX[20] = 0;
		rsinterface.spritesX[21] = 0;
		rsinterface.spritesX[22] = 0;
		rsinterface.spritesX[23] = 0;
		rsinterface.spritesX[24] = 0;
		rsinterface.spritesX[25] = 0;
		rsinterface.spritesX[26] = 0;
		rsinterface.spritesX[27] = 0;
		rsinterface.spritesX[28] = 0;
		rsinterface.spritesX[29] = 0;
		rsinterface.spritesX[30] = 0;
		rsinterface.spritesX[31] = 0;
		rsinterface.spritesX[32] = 0;
		rsinterface.spritesX[33] = 0;
		rsinterface.spritesX[34] = 0;
		rsinterface.spritesX[35] = 0;
		rsinterface.centerText = false;
		rsinterface.aBoolean227 = false;
		rsinterface.aBoolean235 = false;
		rsinterface.usableItemInterface = false;
		rsinterface.isInventoryInterface = false;
		rsinterface.usableItemInterface = false;
		rsinterface.aBoolean259 = true;
		rsinterface.interfaceShown = false;
		rsinterface.textShadow = false;
		rsinterface.width = 8;
		rsinterface.hoverType = -1;
		rsinterface.invSpritePadX = 24;
		rsinterface.parentID = 17827;
		rsinterface.invSpritePadY = 24;
		rsinterface.id = 17824;
		rsinterface.type = 2;
		rsinterface.height = 4;
	}
	
}

