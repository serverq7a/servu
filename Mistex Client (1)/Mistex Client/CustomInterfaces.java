public class CustomInterfaces extends RSInterface {

    public static void unpackInterfaces(TextDrawingArea[] Mistex) {
        /*
         * Clan Chat
         */
        editClan(Mistex);
        clanChatTab(Mistex);

        /*
         * New Information Tab
         */
        informationTab(Mistex);

        /*
         * Settings Tab
         */
        settingsTab(Mistex);
        settingsTab2(Mistex);
        /*
         * Teleporting Interface
         */
        trainingTeleports(Mistex);
        minigameTeleports(Mistex);
        bossesTeleports(Mistex);
        playerKillingTeleports(Mistex);
        skillingTeleports(Mistex);
        donatorTeleports(Mistex);

        /*
         * Weapon Game Interface
         */
        weaponGame(Mistex);

        /*
         * Achievement Tab
         */
        achievementsTab(Mistex);

        /*
         * Player Profiler Tab
         */
        playerProfiler(Mistex);


        /*
         * Information Tab
         */
        infoTab(Mistex);

        /*
         * Friends & Ignore Tab
         */
        friendsTab(Mistex);
        ignoreTab(Mistex);

        /*
         * Prayer Tab
         */
        prayerTab(Mistex);

        /*
         * Sidebar Tab
         */
        //Sidebar0(Mistex);

        /*
         * Emote Tab
         */
        //emoteTab();

        /*
         * Equipments
         */
        equipmentTab(Mistex);
        equipmentScreen(Mistex);

        /*
         * Items on Death
         */
        itemsOnDeathDATA(Mistex);
        itemsOnDeath(Mistex);

        /*
         * PvP Statue Interface
         */
        PkPInterface(Mistex);
        PkPInterfaceBlank(Mistex);

        /*
         * Shop Interface
         */
        Shop(Mistex);

        /*
         * Quest Interface
         */
        questInterface(Mistex);

        /*
         * Skill Interface
         */
        skilllevel(Mistex);

        /*
         * Achievement Interface
         */
        Achievement(Mistex);

        /*
         * Quick Prayer Interface
         */
        quickPrayers(Mistex);

        /*
         * Trade Interface
         */
        Trade(Mistex);

        /*
         * PvP Interfaces
         */
        PVPInterface(Mistex);
        PVPInterface2(Mistex);
        PVPInterface3(Mistex);

        Curses(Mistex);

        /*
         * Pest Control Interfaces
         */
        Pestpanel(Mistex);
        Pestpanel2(Mistex);

        /*
         * Price Checker Interface
         */
        priceChecker(Mistex);

        /*
         * Fight Caves Interface
         */
        fightCaves(Mistex);

        /*
         * Quest Congratulations
         */
        Congrats(Mistex);

        /*
         * Bounty Hunter
         */
        //BountyHunter.initialize(Mistex);

        barrowText(Mistex);

        /*
         * Summoning
         */
        SummonTab(Mistex);
        summoningTab(Mistex);
        petSummoningTab(Mistex);
        bobInterface(Mistex);
       scrolls(Mistex);
       pouches(Mistex);
       
       /*
        * Dungeoneering
        */
       /*dungeoneeringTab(Mistex);
       formParty(Mistex);
		dungParty(Mistex);
		floorMenus(Mistex);
		InvtoParty(Mistex);*/
       

    }
    
	public static void formParty(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(27224);
		addSprite(27225, 0, "Interfaces/Dungeoneering/DUNG");
		addHoverButton(27326, "Interfaces/Dungeoneering/DUNG", 1, 16, 16, "Exit", 250, 27227, 5);
		addHoveredButton(27227, "Interfaces/Dungeoneering/DUNG", 2, 16, 16, 27228);
		addHoverButton(27229, "Interfaces/Dungeoneering/DUNG", 3, 180, 32, "Form Party", 250, 27230, 5);
		addHoveredButton(27230, "Interfaces/Dungeoneering/DUNG", 4, 180, 32, 27231);
		addHoverButton(27132, "Interfaces/Dungeoneering/DUNG", 7, 52, 25, "Reset", 250, 27133, 5);
		addHoveredButton(27133, "Interfaces/Dungeoneering/DUNG", 8, 52, 25, 27134);
		addText(27235, "", tda, 1, 0xffffff, true, true);
		addText(27236, "", tda, 1, 0xffffff, true, true);
		addText(27237, "", tda, 1, 0xffffff, true, true);
		addText(27238, "", tda, 1, 0xffffff, true, true);
		addText(27239, "", tda, 1, 0xffffff, true, true);
		addText(27240, "", tda, 2, 0xffffff, false, true);
		addText(27241, "", tda, 2, 0xffffff, true, true);
		addText(27242, "-", tda, 1, 0xffffff, false, true);
		addText(27243, "-", tda, 1, 0xffffff, false, true);
		int[][] data = {
				{27225, 0, 0}, {27326, 171, 1}, {27227, 171, 1}, {27229, 5, 111}, {27230, 5, 111}, {27132, 132, 230}, {27133, 132, 230},
				{27235, 91, 29}, {27236, 91, 44}, {27237, 91, 59}, {27238, 91, 75}, {27239, 91, 90}, {27240, 99, 156}, {27241, 103, 183}, {27242, 112, 229},
				{27243, 112, 245}
		};
		tab.totalChildren(16);
		for(int i = 0; i < data.length; i++) {
			tab.child(i, data[i][0], data[i][1], data[i][2]);
		}
	}

	public static void dungParty(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(26224);
		addSprite(26225, 0, "Interfaces/Dungeoneering/DUNG");
		addHoverButton(26226, "Interfaces/Dungeoneering/DUNG", 1, 16, 16, "Exit", 250, 26227, 5);
		addHoveredButton(26227, "Interfaces/Dungeoneering/DUNG", 2, 16, 16, 26228);
		addHoverButton(26229, "Interfaces/Dungeoneering/DUNG", 5, 90, 32, "Leave Party", 250, 26230, 5);
		addHoveredButton(26230, "Interfaces/Dungeoneering/DUNG", 6, 90, 32, 26231);
		addHoverButton(26232, "Interfaces/Dungeoneering/DUNG", 7, 52, 25, "Reset", 250, 26233, 5);
		addHoveredButton(26233, "Interfaces/Dungeoneering/DUNG", 8, 52, 25, 26234);
		addText(26235, "", tda, 1, 0xffffff, true, true);
		addText(26236, "", tda, 1, 0xffffff, true, true);
		addText(26237, "", tda, 1, 0xffffff, true, true);
		addText(26238, "", tda, 1, 0xffffff, true, true);
		addText(26239, "", tda, 1, 0xffffff, true, true);
		addText(26240, "0", tda, 2, 0xffffff, false, true);
		addText(26241, "0", tda, 2, 0xffffff, true, true);
		addText(26242, "0", tda, 1, 0xffffff, false, true);
		addText(26243, "0", tda, 1, 0xffffff, false, true);
		addHoverButton(26244, "Interfaces/Dungeoneering/DUNG", 26, 61, 20, "Change", 250, 26245, 5);
		addHoveredButton(26245, "Interfaces/Dungeoneering/DUNG", 18, 61, 20, 26246);
		addHoverButton(26247, "Interfaces/Dungeoneering/DUNG", 26, 61, 20, "Change", 250, 26248, 5);
		addHoveredButton(26248, "Interfaces/Dungeoneering/DUNG", 18, 61, 20, 26249);
		addHoverButton(26250, "Interfaces/Dungeoneering/DUNG", 16, 90, 32, "Invite player", 250, 26251, 5);
		addHoveredButton(26251, "Interfaces/Dungeoneering/DUNG", 17, 90, 32, 26252);
		int[][] data = {
				{26225, 0, 0}, {26226, 171, 1}, {26227, 171, 1}, {26229, 5, 111}, {26230, 5, 111}, {26232, 132, 230}, {26233, 132, 230},
				{26235, 91, 29}, {26236, 91, 44}, {26237, 91, 59}, {26238, 91, 75}, {26239, 91, 90}, {26240, 99, 156}, {26241, 103, 183}, {26242, 112, 229},
				{26243, 112, 245}, {26244, 121, 152}, {26245, 121, 152}, {26247, 121, 180}, {26248, 121, 180},
				{26250, 95, 111}, {26251, 95, 111}
		};
		tab.totalChildren(22);
		for(int i = 0; i < data.length; i++) {
			tab.child(i, data[i][0], data[i][1], data[i][2]);
		}
	}
	
	public static void handleFloorMenus(int number, int interfaceId) {
		RSInterface rsInterface = addInterface(interfaceId);
		addSprite(interfaceId+1, 18+number, "Interfaces/Dungeoneering/DUNG");
		
	    addHoverButton(interfaceId+2, "Interfaces/Dungeoneering/DUNG", 1, 16, 16, "Close", 250,interfaceId+3, 3);
	    addHoveredButton(interfaceId+3, "Interfaces/Dungeoneering/DUNG", 2, 16, 16, interfaceId+4);
	    addHoverButton(interfaceId+5, "", -1, 74, 30, "Confirm", 250, interfaceId+6, 5);
	    addHoveredButton(interfaceId+6, "Interfaces/Dungeoneering/DUNG", 25, 74, 30, interfaceId+7);
	    
	    addButton(interfaceId+8, -1, "", 21, 34, "Select", 1);
	    addButton(interfaceId+9, -1, "", 21, 34, "Select", 1);
	    addButton(interfaceId+10, -1, "", 21, 34, "Select", 1);
	    addButton(interfaceId+11, -1, "", 21, 34, "Select", 1);
	    addButton(interfaceId+12, -1, "", 21, 34, "Select", 1);
	    addButton(interfaceId+13, -1, "", 21, 34, "Select", 1);
	    
		rsInterface.totalChildren(11);
		int[][] childs = {
				{interfaceId + 1 , 0, 0}, {interfaceId + 2,  487, 4}, {interfaceId + 3, 487, 4}, 
				{interfaceId + 5, 169, 264}, {interfaceId + 6, 169, 264},{interfaceId + 8, 27, 37},
				{interfaceId + 9, 66, 37}, {interfaceId + 10, 105, 37}, {interfaceId + 11, 141, 37},
				{interfaceId + 12, 180, 37}, {interfaceId + 13, 219, 37}
		};
	    for (int i = 0; i < childs.length; i++)
		      rsInterface.child(i, childs[i][0], childs[i][1], childs[i][2]);
	}
	
	public static void floorMenus(TextDrawingArea[] TDA) {
		handleFloorMenus(1, 35233);
		handleFloorMenus(2, 35233 + 100);
		handleFloorMenus(3, 35233 + 200);
		handleFloorMenus(4, 35233 + 300);
		handleFloorMenus(5, 35233 + 400);
		handleFloorMenus(6, 35233 + 500);
	}

	  public static void InvtoParty(TextDrawingArea[] TDA) {
	    RSInterface rsInterface = addTabInterface(40224);
	    addSprite(40225, 10, "Interfaces/Dungeoneering/DUNG");
	    addHoverButton(40226, "Interfaces/Dungeoneering/DUNG", 1, 16, 16, "Close", 250, 40227, 3);
	    addHoveredButton(40227, "Interfaces/Dungeoneering/DUNG", 2, 16, 16, 26228);
	    addHoverButton(40229, "Interfaces/Dungeoneering/DUNG", 14, 72, 32, "Accept", 250, 40230, 5);
	    addHoveredButton(40230, "Interfaces/Dungeoneering/DUNG", 11, 72, 31, 40231);
	    addHoverButton(40232, "Interfaces/Dungeoneering/DUNG", 15, 72, 32, "Decline", 250, 40233, 5);
	    addHoveredButton(40233, "Interfaces/Dungeoneering/DUNG", 12, 72, 31, 40234);
	    addText(40235, "", TDA, 1, 16777215, true, true);
	    addText(40236, "", TDA, 1, 16777215, true, true);
	    addText(40237, "", TDA, 1, 16777215, true, true);
	    addText(40238, "", TDA, 1, 16777215, true, true);
	    addText(40239, "", TDA, 1, 16777215, true, true);
	    
	    addText(40240, "0", TDA, 2, 16777215, false, true);
	    addText(40241, "0", TDA, 2, 16777215, true, true);
	    
	    addText(40242, "", TDA, 1, 16777215, true, true);
	    addText(40243, "", TDA, 1, 16777215, true, true);
	    addText(40244, "", TDA, 1, 16777215, true, true);
	    addText(40245, "", TDA, 1, 16777215, true, true);
	    addText(40246, "", TDA, 1, 16777215, true, true);
	    
	    addText(40247, "", TDA, 1, 16777215, true, true);
	    addText(40248, "", TDA, 1, 16777215, true, true);
	    addText(40249, "", TDA, 1, 16777215, true, true);
	    addText(40250, "", TDA, 1, 16777215, true, true);
	    addText(40251, "", TDA, 1, 16777215, true, true);
	    
	    addText(40252, "", TDA, 1, 16777215, true, true);
	    addText(40253, "", TDA, 1, 16777215, true, true);
	    addText(40254, "", TDA, 1, 16777215, true, true);
	    addText(40255, "", TDA, 1, 16777215, true, true);
	    addText(40256, "", TDA, 1, 16777215, true, true);
	    
	    addText(40257, "", TDA, 1, 16777215, true, true);
	    addText(40258, "", TDA, 1, 16777215, true, true);
	    addText(40259, "", TDA, 1, 16777215, true, true);
	    addText(40260, "", TDA, 1, 16777215, true, true);
	    addText(40261, "", TDA, 1, 16777215, true, true);
	    int[][] arrayOfInt = { { 40225, 14, 20 }, { 40226, 468, 23 }, { 40227, 468, 23 }, { 40229, 128, 247 }, { 40230, 129, 248 }, { 40232, 218, 247 }, 
	    		{ 40233, 219, 248 }, { 40235, 93, 74 }, { 40236, 93, 93 }, { 40237, 93, 112 }, { 40238, 93, 131 }, { 40239, 93, 150}, 
	    		{ 40240, 287, 173 }, { 40241, 290, 198 }, { 40242, 220, 74 }, { 40243, 220, 93}, {40244, 220, 112},
	    		{ 40245, 220, 131}, {40246, 220 ,150}, {40247, 290 ,74}, {40248, 290 ,93}, {40249, 290 ,112}, {40250, 290 ,131},{40251, 290, 150},
	    		{40252, 360, 74}, {40253, 360, 93}, {40254, 360, 112}, {40255, 360, 131}, {40256, 360, 150},
	    		{40257, 440, 74}, {40258, 440, 93}, {40259, 440, 112}, {40260, 440, 131}, {40261, 440, 150}
	    };

	    rsInterface.totalChildren(34);
	    for (int i = 0; i < arrayOfInt.length; i++)
	      rsInterface.child(i, arrayOfInt[i][0], arrayOfInt[i][1], arrayOfInt[i][2]);
	  }
    
	private static String[] scrollNames = {"Howl","Dreadfowl Strike","Egg Spawn","Slime Spray","Stony Shell","Pester","Electric Lash","Venom Shot","Fireball Assault","Cheese Feast","Sandstorm","Generate Compost","Explode","Vampire Touch","Insane Ferocity","Multichop","Call of Arms","Call of Arms","Call of Arms","Call of Arms","Bronze Bull Rush","Unburden","Herbcall","Evil Flames","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Iron Bull Rush","Immense Heat","Thieving Fingers","Blood Drain","Tireless Run","Abyssal Drain","Dissolve","Steel Bull Rush","Fish Rain","Goad","Ambush","Rending","Doomsphere Device","Dust Cloud","Abyssal Stealth","Ophidian Incubation","Poisonous Blast","Mithril Bull Rush","Toad Bark","Testudo","Swallow Whole","Fruitfall","Famine","Arctic Blast","Rise from the Ashes","Volcanic Strength","Crushing Claw","Mantis Strike","Adamant Bull Rush","Inferno","Deadly Claw","Acorn Missile","Titan's Consitution","Titan's Consitution","Titan's onsitution","Regrowth","Spike Shot","Ebon Thunder","Swamp Plague","Rune Bull Rush","Healing Aura","Boil","Magic Focus","Essence Shipment","Iron Within","Winter Storage","Steel of Legends",};
	private static String[] pouchNames = {"Spirit Wolf", "Dreadfowl", "Spirit Spider", "Thorny Snail", "Granite Crab", "Spirit Mosquito", "Desert Wyrm", "Spirit Scorpian", "Spirit Tz-Kih", "Albino rat", "Spirit Kalphite", "Compost mound", "Giant Chinchompa", "Vampire Bat", "Honey badger", "Beaver", "Void Ravager", "Void Spinner", "Void Torcher", "Void Shifter", "Bronze minotaur", "Bull ant", "Macaw", "Evil Turnip", "Spirit Cockatrice", "Spirit Guthatrice", "Spirit Saratrice", "Spirit Zamatrice", "Spirit Pengatrice", "Spirit Coraxatrice", "Spirit Vulatrice", "Iron minotaur", "Pyrelord", "Magpie", "Bloated Leech", "Spirit terrorbird", "Abyssal Parasite", "Spirit Jelly", "Steel Minotaur", "Ibis","Spirit kyatt", "Spirit laurpia", "Spirit graahk", "Karamthulhu overlord", "Smoke Devil", "Abyssal Lurker", "Spirit cobra", "Stranger Plant", "Mithril minotaur", "Barker Toad", "War tortoise","Bunyip", "Fruit bat", "Ravenous Locust", "Artic Bear", "Pheonix", "Obsidian Golem", "Granite Lobster", "Praying mantis", "Forge regent", "Adamant minotaur", "Talon Beast", "Giant ent","Fire titan","Moss titan","Ice titan","Hydra","Spirit daggannoth","Lava titan","Swamp titan","Rune minotaur", "Unicorn Stallion", "Geyser titan", "Wolpertinger", "Abyssal Titan", "Iron titan","Pack Yack","Steel titan"};
	
	public static void scrolls(TextDrawingArea TDA[]) {
		RSInterface rsinterface = addInterface(38700);
		addButton(38701, 0, "Interfaces/Scrolls/SPRITE", "Pouches", 27640, 1,
				116, 20);
		addButton(38702, 1, "Interfaces/Scrolls/SPRITE", "Scrolls", 27640, 1,
				116, 20);
		RSInterface scroll = addTabInterface(38710);
		scroll.width = 430;
		scroll.height = 230;
		scroll.scrollMax = 550;
		// scroll.newScroller = true;
		int lastId = 38710;
		int lastImage = 4;
		for (int i = 0; i < 78; i++) {
			addHover(lastId + 1, 1, 0, lastId + 2, lastImage + 1,
					"Interfaces/Scrolls/Sprite", 46, 50, "Select "
							+ scrollNames[i] + " scroll");
			addHovered(lastId + 2, lastImage + 2, "Interfaces/Scrolls/Sprite",
					46, 50, lastId + 3);
			lastId += 3;
			lastImage += 2;
		}
		rsinterface.children = new int[7];
		rsinterface.childX = new int[7];
		rsinterface.childY = new int[7];
		rsinterface.children[0] = 39701;
		rsinterface.childX[0] = 14;
		rsinterface.childY[0] = 17;
		rsinterface.children[1] = 39702;
		rsinterface.childX[1] = 475;
		rsinterface.childY[1] = 30;
		rsinterface.children[2] = 39703;
		rsinterface.childX[2] = 475;
		rsinterface.childY[2] = 30;
		rsinterface.children[3] = 38701;
		rsinterface.childX[3] = 25;
		rsinterface.childY[3] = 30;
		rsinterface.children[4] = 38702;
		rsinterface.childX[4] = 128;
		rsinterface.childY[4] = 30;
		rsinterface.children[5] = 39707;
		rsinterface.childX[5] = 268;
		rsinterface.childY[5] = 30;
		rsinterface.children[6] = 38710;
		rsinterface.childX[6] = 35;
		rsinterface.childY[6] = 65;
		scroll.children = new int[156];
		scroll.childX = new int[156];
		scroll.childY = new int[156];
		int lastNumber = -1;
		int lastId2 = 38710;
		int lastX = -52;
		int lastY = 0;
		for (int i = 0; i < 78; i++) {
			scroll.children[lastNumber += 1] = lastId2 += 1;
			scroll.childX[lastNumber] = lastX += 52;
			scroll.childY[lastNumber] = lastY;
			scroll.children[lastNumber += 1] = lastId2 += 1;
			scroll.childX[lastNumber] = lastX;
			scroll.childY[lastNumber] = lastY;
			lastId2 += 1;
			if (lastX == 364) {
				lastX = -52;
				lastY += 55;
			}
		}
	}
	
	public static void addHover2(int i, int j, int k, int l, int i1, String s, int j1, int k1, 
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
	
	public static void addHovered2(int i, int j, String s, int k, int l, int i1)
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
	
	public static void pouches(TextDrawingArea[] TDA) {
		RSInterface rsinterface = addInterface(39700);
		addSprite(39701, 0, "Interfaces/Pouches/SPRITE");
		addHover2(39702, 3, 0, 39703, 1, "Interfaces/Pouches/SPRITE", 17, 17, "Close Window");
		addHovered2(39703, 2, "Interfaces/Pouches/SPRITE", 17, 17, 39704);
		addButton2(39705, 3, "Interfaces/Pouches/SPRITE", "Pouches", 27640, 1, 116, 20);
		addButton2(39706, 4, "Interfaces/Pouches/SPRITE", "Scrolls", 27640, 1, 116, 20);
		addText(39707, "Summoning Pouch Creation", TDA, 2, 0xff981f, false, true);
		//the weird thing is that the close button above ^ uses the same methods for the right clicks
			
		RSInterface scroll = addTabInterface(39710);
		scroll.width = 430; scroll.height = 230; scroll.scrollMax = 550;
		//scroll.newScroller = true;
		int lastId = 39710;
		int lastImage = 4;
		for (int i = 0; i < 78; i++) {
			addHover(lastId+1, 1, 0, lastId+2, lastImage+1, "Interfaces/Pouches/Sprite", 46, 50, "Infuse " + pouchNames[i] + " pouch");
			addHoveredButton(lastId+2, "Interfaces/Pouches/Sprite", lastImage+2, 46, 50, lastId+3);
			lastId += 3;
			lastImage += 2;
		}
	rsinterface.children = new int[7];		rsinterface.childX = new int[7];	rsinterface.childY = new int[7];
	rsinterface.children[0] = 39701;		rsinterface.childX[0] = 14;			rsinterface.childY[0] = 17;
	rsinterface.children[1] = 39702;		rsinterface.childX[1] = 475;		rsinterface.childY[1] = 30;        
	rsinterface.children[2] = 39703;		rsinterface.childX[2] = 475;		rsinterface.childY[2] = 30;        
	rsinterface.children[3] = 39705;		rsinterface.childX[3] = 25;			rsinterface.childY[3] = 30;       
	rsinterface.children[4] = 39706;		rsinterface.childX[4] = 128;		rsinterface.childY[4] = 30;       
	rsinterface.children[5] = 39707;		rsinterface.childX[5] = 268;		rsinterface.childY[5] = 30;       
	rsinterface.children[6] = 39710;		rsinterface.childX[6] = 35;			rsinterface.childY[6] = 65;
	scroll.children = new int[156];		scroll.childX = new int[156];			scroll.childY = new int[156];
	int lastNumber = -1;
	int lastId2 = 39710;
	int lastX = -52;
	int lastY = 0;
	for (int i = 0; i < 78; i++) {
		scroll.children[lastNumber+=1] = lastId2+=1;			scroll.childX[lastNumber] = lastX+=52;		scroll.childY[lastNumber] = lastY;
		scroll.children[lastNumber+=1] = lastId2+=1;			scroll.childX[lastNumber] = lastX;		scroll.childY[lastNumber] = lastY;
		lastId2 += 1;
		if (lastX == 364) {
			lastX = -52;
			lastY += 55;
		}
	}
}
    
    public static void bobInterface(TextDrawingArea[] tda) {
		RSInterface rsi = addTabInterface(2700);
		addSprite(2701, 0, "BoB/SPRITE");
		addBobStorage(2702);
		addHoverButton(2703, "BoB/SPRITE", 1, 21, 21,"Close", 250, 2704, 3);
		addHoveredButton(2704, "BoB/SPRITE", 2, 21, 21,2705);
		rsi.totalChildren(4);
		rsi.child(0, 2701, 90, 14);
		rsi.child(1, 2702, 106, 57);
		rsi.child(2, 2703, 431, 23);
		rsi.child(3, 2704, 431, 23);
	}

    
    private static final int WHITE_TEXT = 0xFFFFFF;
    private static final int GREY_TEXT = 0xB9B855;
    private static final int ORANGE_TEXT = 0xFF981F;

    private static void summoningTab(TextDrawingArea[] tda) {
        final String dir = "Tab/SPRITE";
        RSInterface rsi = addTabInterface(18017);
        addButton(18018, 0, dir, 143, 13, "Cast special", 1);
        addText(18019, "S P E C I A L  M O V E", tda, 0, WHITE_TEXT, false, false);
        addSprite(18020, 1, dir);
        addFamiliarHead(18021, 75, 50, 875);
        addSprite(18022, 2, dir);
        addConfigButton(18023, 18017, 4, 3, dir, 30, 31, "Cast special", 0, 1, 330);
        addText(18024, "0", tda, 0, ORANGE_TEXT, false, true);
        addSprite(18025, 5, dir);
        addConfigButton(18026, 18017, 7, 6, dir, 29, 39, "Attack", 0, 1, 333);
        addSprite(18027, 8, dir);
        addText(18028, "sdg", tda, 2, ORANGE_TEXT, true, false);
        addHoverButton(18029, dir, 9, 38, 38, "Withdraw BoB", -1, 18030, 1);
        addHoveredButton(18030, dir, 10, 38, 38, 18031);
        addHoverButton(18032, dir, 11, 38, 38, "Renew familiar", -1, 18033, 1);
        addHoveredButton(18033, dir, 12, 38, 38, 18034);
        addHoverButton(18035, dir, 13, 38, 38, "Call follower", -1, 18036, 1);
        addHoveredButton(18036, dir, 14, 38, 38, 18037);
        addHoverButton(18038, dir, 15, 38, 38, "Dismiss familiar", -1, 18039, 1);
        addHoveredButton(18039, dir, 16, 38, 38, 18040);
        addHead2(23027, 50, 50, 800);
        addSprite(18041, 17, dir);
        addSprite(18042, 18, dir);
        addText(18043, "", tda, 0, GREY_TEXT, false, true);
        addSprite(18044, 19, dir);
        addText(18045, "", tda, 0, GREY_TEXT, false, true);
        setChildren(25, rsi);
        setBounds(18018, 23, 10, 0, rsi);
        setBounds(18019, 43, 12, 1, rsi);
        setBounds(18020, 10, 32, 2, rsi);
        setBounds(18021, 63, 60, 3, rsi);
        setBounds(18022, 11, 32, 4, rsi);
        setBounds(18023, 14, 35, 5, rsi);
        setBounds(18024, 25, 69, 6, rsi);
        setBounds(18025, 138, 32, 7, rsi);
        setBounds(18026, 143, 36, 8, rsi);
        setBounds(18027, 12, 144, 9, rsi);
        setBounds(18028, 93, 146, 10, rsi);
        setBounds(18029, 23, 168, 11, rsi);
        setBounds(18030, 23, 168, 12, rsi);
        setBounds(18032, 75, 168, 13, rsi);
        setBounds(18033, 75, 168, 14, rsi);
        setBounds(18035, 23, 213, 15, rsi);
        setBounds(18036, 23, 213, 16, rsi);
        setBounds(18038, 75, 213, 17, rsi);
        setBounds(18039, 75, 213, 18, rsi);
        setBounds(18041, 130, 168, 19, rsi);
        setBounds(18042, 153, 170, 20, rsi);
        setBounds(18043, 148, 198, 21, rsi);
        setBounds(18044, 149, 213, 22, rsi);
        setBounds(18045, 145, 241, 23, rsi);
        setBounds(23027, 75, 55, 24, rsi);
    }

    private static void petSummoningTab(TextDrawingArea[] tda) {
        final String dir = "/Tab/SPRITE";
        RSInterface rsi = addTabInterface(19017);
        addSprite(19018, 1, dir);
        addFamiliarHead(19019, 75, 50, 900);
        addSprite(19020, 8, dir);
        addText(19021, "None", tda, 2, ORANGE_TEXT, true, false);
        addHoverButton(19022, dir, 13, 38, 38, "Call follower", -1, 19023, 1);
        addHoveredButton(19023, dir, 14, 38, 38, 19024);
        addHoverButton(19025, dir, 15, 38, 38, "Dismiss familiar", -1, 19026, 1);
        addHoveredButton(19026, dir, 16, 38, 38, 19027);
        addSprite(19028, 17, dir);
        addSprite(19029, 20, dir);
        addText(19030, "0%", tda, 0, GREY_TEXT, false, true);
        addSprite(19031, 21, dir);
        addText(19032, "0%", tda, 0, WHITE_TEXT, false, true);
        setChildren(13, rsi);
        setBounds(19018, 10, 32, 0, rsi);
        setBounds(19019, 65, 65, 1, rsi);
        setBounds(19020, 12, 145, 2, rsi);
        setBounds(19021, 93, 147, 3, rsi);
        setBounds(19022, 23, 213, 4, rsi);
        setBounds(19023, 23, 213, 5, rsi);
        setBounds(19025, 75, 213, 6, rsi);
        setBounds(19026, 75, 213, 7, rsi);
        setBounds(19028, 130, 168, 8, rsi);
        setBounds(19029, 148, 170, 9, rsi);
        setBounds(19030, 152, 198, 10, rsi);
        setBounds(19031, 149, 220, 11, rsi);
        setBounds(19032, 155, 241, 12, rsi);
    }

    private static void addFamiliarHead(int interfaceID, int width, int height, int zoom) {
        RSInterface rsi = addTabInterface(interfaceID);
        rsi.type = 6;
        rsi.anInt233 = 2;
        rsi.mediaID = 4000;
        rsi.modelZoom = zoom;
        rsi.modelRotation1 = 40;
        rsi.modelRotation2 = 1800;
        rsi.height = height;
        rsi.width = width;
    }
    private static void addHead2(int id, int w, int h, int zoom) { //tewst
        RSInterface rsinterface = addInterface(id);
        rsinterface.type = 6;
        rsinterface.anInt233 = 2;
        rsinterface.mediaID = 4000; //
        rsinterface.modelZoom = zoom;
        rsinterface.modelRotation1 = 40; //40;//wait
        rsinterface.modelRotation2 = 1900; //1900;
        rsinterface.height = h;
        rsinterface.width = w;
    }

    public static void SummonTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(25605);
        addSprite(25606, 4, "Summon/SUMMON");
        addSprite(25607, 5, "Summon/SUMMON");
        addSprite(25608, 9, "Summon/SUMMON");
        addButton(25609, 1, "Summon/SUMMON", "Call");
        addButton(25619, 2, "Summon/SUMMON", "Renew");
        addButton(25610, 3, "Summon/SUMMON", "Dismiss");
        //addSprite(25611, 7, "Interfaces/Summon/SUMMON");
        addText(25612, "", tda, 0, 0xc4b074, false, true); // Leveling ID
        addText(25616, "0", tda, 1, 0xff9040, false, true);
        addText(25614, "", tda, 1, 0xcc9900, true, true);
        addText(25615, "99.99", tda, 0, 0xc4b074, false, true); // Timer ID
        addButton(25613, 8, "Summon/SUMMON", "Special Move");
        addText(25622, "", tda, 0, 0xffffff, false, true);
        addButton(25617, 7, "Summon/SUMMON", "Special Move");
        addSprite(25618, 11, "Summon/SUMMON");
        addHead2(23027, 50, 50, 800);
        tab.totalChildren(15);
        tab.child(0, 25606, 11, 163);
        tab.child(1, 25607, 12, 141);
        tab.child(2, 25608, 9, 29);
        tab.child(3, 25609, 23, 211);
        tab.child(4, 25610, 121, 211);
        tab.child(5, 25612, 29, 194);
        tab.child(6, 25613, 9, 29);
        tab.child(7, 25614, 97, 144);
        tab.child(8, 25615, 127, 194);
        tab.child(9, 25616, 20, 63);
        tab.child(10, 25617, 31, 10);
        tab.child(11, 25618, 70, 59);
        tab.child(12, 25619, 72, 211);
        tab.child(13, 23027, 75, 55);
        tab.child(14, 25622, 42, 10);
        //tab.child(5, 25611, 20, 10);
        /*tab.child(6, 25612, 29, 194);
		tab.child(7, 25613, 9, 29);
		tab.child(8, 25614, 97, 144);
		tab.child(9, 25615, 127, 194);
		tab.child(10, 25616, 20, 63);
		tab.child(11, 25617, 37, 10);
		tab.child(12, 25618, 70, 59);
		tab.child(13, 25619, 72, 211);
		tab.child(14, 23027, 75, 55);*/
    }

    public static void barrowText(TextDrawingArea[] tda) {
        RSInterface tab = addScreenInterface(16128);
        addText(16129, "Barrows Brothers", tda, 2, 0xff981f, true, true);
        addText(16130, "Dharoks", tda, 1, 0x86B404, true, true);
        addText(16131, "Veracs", tda, 1, 0x86B404, true, true);
        addText(16132, "Ahrims", tda, 1, 0x86B404, true, true);
        addText(16133, "Torags", tda, 1, 0x86B404, true, true);
        addText(16134, "Guthans", tda, 1, 0x86B404, true, true);
        addText(16135, "Karils", tda, 1, 0x86B404, true, true);
        addText(16136, "Killcount:", tda, 2, 0xff981f, true, true);
        addText(16137, "#", tda, 1, 0x86B404, true, true);
        tab.totalChildren(9);
        tab.child(0, 16129, 452, 220);
        tab.child(1, 16130, 460, 240);
        tab.child(2, 16131, 460, 255);
        tab.child(3, 16132, 460, 270);
        tab.child(4, 16133, 460, 285);
        tab.child(5, 16134, 460, 300);
        tab.child(6, 16135, 460, 315);
        tab.child(7, 16136, 30, 318);
        tab.child(8, 16137, 68, 318);
    }

    public static void editClan(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(40172);
        addSprite(47251, 1, "/Clan Chat/CLAN");
        addHoverButton(47252, "/Clan Chat/BUTTON", 1, 150, 35, "Set name", 22222, 47253, 1);
        addHoveredButton(47253, "/Clan Chat/BUTTON", 2, 150, 35, 47254);
        addHoverButton(47255, "/Clan Chat/BUTTON", 1, 150, 35, "Anyone", -1, 47256, 1);
        addHoveredButton(47256, "/Clan Chat/BUTTON", 2, 150, 35, 47257);

        addHoverButton(48000, "b", 1, 150, 35, "Only me", -1, 47999, 1);
        addHoverButton(48001, "b", 1, 150, 35, "General+", -1, 47999, 1);
        addHoverButton(48002, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
        addHoverButton(48003, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
        addHoverButton(48004, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
        addHoverButton(48005, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
        addHoverButton(48006, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);
        addHoverButton(48007, "b", 1, 150, 35, "Any friends", -1, 47999, 1);

        addHoverButton(47258, "/Clan Chat/BUTTON", 1, 150, 35, "Anyone", -1, 47259, 1);
        addHoveredButton(47259, "/Clan Chat/BUTTON", 2, 150, 35, 17260);

        addHoverButton(48010, "b", 1, 150, 35, "Only me", -1, 47999, 1);
        addHoverButton(48011, "b", 1, 150, 35, "General+", -1, 47999, 1);
        addHoverButton(48012, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
        addHoverButton(48013, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
        addHoverButton(48014, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
        addHoverButton(48015, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
        addHoverButton(48016, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);
        addHoverButton(48017, "b", 1, 150, 35, "Any friends", -1, 47999, 1);

        addHoverButton(47261, "/Clan Chat/BUTTON", 1, 150, 35, "Only me", -1,
            47262, 1);
        addHoveredButton(47262, "/Clan Chat/BUTTON", 2, 150, 35, 47263);

        // addHoverButton(48020, "b", 1, 150, 35, "Only me", -1, 47999, 1);
        addHoverButton(48021, "b", 1, 150, 35, "General+", -1, 47999, 1);
        addHoverButton(48022, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
        addHoverButton(48023, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
        addHoverButton(48024, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
        addHoverButton(48025, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
        addHoverButton(48026, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);

        addHoverButton(47267, "/Clan Chat/CLOSE", 3, 16, 16, "Close", -1,
            47268, 1);
        addHoveredButton(47268, "/Clan Chat/CLOSE", 4, 16, 16, 47269);

        addText(47800, "Clan name:", tda, 0, 0xff981f, false, true);
        addText(47801, "Who can enter chat?", tda, 0, 0xff981f, false, true);
        addText(47812, "Who can talk on chat?", tda, 0, 0xff981f, false, true);
        addText(47813, "Who can kick on chat?", tda, 0, 0xff981f, false, true);
        addText(47814, "Alex", tda, 0, 0xffffff, true, true);
        addText(47815, "Anyone", tda, 0, 0xffffff, true, true);
        addText(47816, "Anyone", tda, 0, 0xffffff, true, true);
        addText(47817, "Only me", tda, 0, 0xffffff, true, true);
        tab.totalChildren(42);
        tab.child(0, 47251, 15, 15);
        tab.child(1, 47252, 25, 47 + 20);
        tab.child(2, 47253, 25, 47 + 20);
        tab.child(3, 47267, 476, 23);
        tab.child(4, 47268, 476, 23);
        tab.child(5, 48000, 25, 87 + 25);
        tab.child(6, 48001, 25, 87 + 25);
        tab.child(7, 48002, 25, 87 + 25);
        tab.child(8, 48003, 25, 87 + 25);
        tab.child(9, 48004, 25, 87 + 25);
        tab.child(10, 48005, 25, 87 + 25);
        tab.child(11, 48006, 25, 87 + 25);
        tab.child(12, 48007, 25, 87 + 25);
        tab.child(13, 47255, 25, 87 + 25);
        tab.child(14, 47256, 25, 87 + 25);
        tab.child(15, 48010, 25, 128 + 30);
        tab.child(16, 48011, 25, 128 + 30);
        tab.child(17, 48012, 25, 128 + 30);
        tab.child(18, 48013, 25, 128 + 30);
        tab.child(19, 48014, 25, 128 + 30);
        tab.child(20, 48015, 25, 128 + 30);
        tab.child(21, 48016, 25, 128 + 30);
        tab.child(22, 48017, 25, 128 + 30);
        tab.child(23, 47258, 25, 128 + 30);
        tab.child(24, 47259, 25, 128 + 30);
        // tab.child(25, 48020, 25, 168+35);
        tab.child(25, 48021, 25, 168 + 35);
        tab.child(26, 48022, 25, 168 + 35);
        tab.child(27, 48023, 25, 168 + 35);
        tab.child(28, 48024, 25, 168 + 35);
        tab.child(29, 48025, 25, 168 + 35);
        tab.child(30, 48026, 25, 168 + 35);
        tab.child(31, 47261, 25, 168 + 35);
        tab.child(32, 47262, 25, 168 + 35);
        tab.child(33, 47800, 73, 54 + 20);
        tab.child(34, 47801, 53, 95 + 25);
        tab.child(35, 47812, 53, 136 + 30);
        tab.child(36, 47813, 53, 177 + 35);
        tab.child(37, 47814, 100, 54 + 20 + 12);
        tab.child(38, 47815, 100, 95 + 25 + 12);
        tab.child(39, 47816, 100, 136 + 30 + 12);
        tab.child(40, 47817, 100, 177 + 35 + 12);
        tab.child(41, 44000, 0, 94);

        tab = addTabInterface(44000);
        tab.width = 474;
        tab.height = 213;
        tab.scrollMax = 2030;
        for (int i = 44001; i <= 44200; i++) {
            addText(i, "", tda, 1, 0xffff64, false, true);
        }
        for (int i = 44801; i <= 45000; i++) {
            addHoverText(i, "", "[CC]", tda, 1, 0xffffff, false, false, 150);
        }
        tab.totalChildren(400);
        int Child = 0;
        int Y = 3;
        for (int i = 44001; i <= 44200; i++) {
            tab.child(Child, i, 204, Y);
            Child++;
            Y += 13;
        }
        Y = 3;
        for (int i = 44801; i <= 45000; i++) {
            tab.child(Child, i, 343, Y);
            Child++;
            Y += 13;
        }
    }

    public static void clanChatTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(18128);
        addHoverButton(18129, "/Clan Chat/SPRITE", 6, 72, 32,
            "Join/Leave a Clan", 550, 18130, 1);
        addHoveredButton(18130, "/Clan Chat/SPRITE", 7, 72, 32, 18131);
        addHoverButton(18132, "/Clan Chat/SPRITE", 6, 72, 32,
            "Open Clan Setup", -1, 18133, 5);
        addHoveredButton(18133, "/Clan Chat/SPRITE", 7, 72, 32, 18134);
        addButton(18250, 0, "/Clan Chat/Lootshare", "Toggle lootshare");
        addText(18135, "Join Chat", tda, 0, 0xff9b00, true, true);
        addText(18136, "Clan Setup", tda, 0, 0xff9b00, true, true);
        addSprite(18137, 37, "/Clan Chat/SPRITE");
        addText(18138, "Clan Chat", tda, 1, 0xff9b00, true, true);
        addText(18139, "Talking in: Not in chat", tda, 0, 0xff9b00, false, true);
        addText(18140, "Owner: None", tda, 0, 0xff9b00, false, true);
        addHoverButton(18343, "/Clan Chat/kick", 0, 23, 23, "Kick", 677, 18344,
            1);
        addHoveredButton(18344, "/Clan Chat/kick", 1, 23, 23, 18345);
        tab.totalChildren(14);
        tab.child(0, 18137, 0, 62);
        tab.child(1, 18143, 0, 62);
        tab.child(2, 18129, 15, 226);
        tab.child(3, 18130, 15, 226);
        tab.child(4, 18132, 103, 226);
        tab.child(5, 18133, 103, 226);
        tab.child(6, 18135, 51, 237);
        tab.child(7, 18136, 139, 237);
        tab.child(8, 18138, 95, 1);
        tab.child(9, 18139, 10, 23);
        tab.child(10, 18140, 25, 38);
        tab.child(11, 18250, 145, 5);
        tab.child(12, 18343, 147, 35);
        tab.child(13, 18344, 147, 35);
        /* Text area */
        RSInterface list = addTabInterface(18143);
        list.totalChildren(200);

        for (int i = 38144; i <= 38244; i++) {
            addSprite(i, 0, 0, "/Clan Chat/CC");
        }
        for (int i = 18144; i <= 18244; i++) {
            addText(i, "", tda, 0, 0xffffff, false, true);
        }
        for (int id = 38144, i = 100; id <= 38243 && i <= 199; id++, i++) {
            list.children[i] = id;
            list.childX[i] = 5;
            for (int id2 = 38144, i2 = 101; id2 <= 38243 && i2 <= 199; id2++, i2++) {
                list.childY[0] = 2;
                list.childY[i2] = list.childY[i2 - 1] + 14;
            }
        }

        for (int id = 18144, i = 0; id <= 18243 && i <= 99; id++, i++) {
            list.children[i] = id;
            list.childX[i] = 12;
            for (int id2 = 18144, i2 = 1; id2 <= 18243 && i2 <= 99; id2++, i2++) {
                list.childY[0] = 2;
                list.childY[i2] = list.childY[i2 - 1] + 14;
            }
        }
        list.height = 158;
        list.width = 174;
        list.scrollMax = 1405;
    }

    public static void Congrats(TextDrawingArea[] TDA) {
        RSInterface Interface = addInterface(23000);
        addSprite(23001, 1, "Congrats/BACKGROUND");
        addHoverButton(23002, "Congrats/SPRITE", 1, 132, 27, "Continue", -1, 23002, 1);
        addText(23003, "", TDA, 2, 0x000000, true, false);
        addText(23004, "", TDA, 2, 0x000000, true, false);
        addText(23005, "", TDA, 2, 0x000000, true, false);
        addText(23006, "", TDA, 2, 0x000000, true, false);
        addText(23007, "", TDA, 2, 0x000000, true, false);
        setChildren(7, Interface);
        setBounds(23001, 1, 1, 0, Interface);
        setBounds(23002, 191, 269, 1, Interface);
        setBounds(23003, 261, 119, 2, Interface);
        setBounds(23004, 261, 164, 3, Interface);
        setBounds(23005, 261, 180, 4, Interface);
        setBounds(23006, 261, 196, 5, Interface);
        setBounds(23007, 261, 212, 6, Interface);
    }

    public static void priceChecker(TextDrawingArea[] tda) {
        RSInterface rsi = addTabInterface(43933);
        addSprite(18245, 1, "PriceChecker/CHECK");
        addPriceChecker(18246);
        addHoverButton(18247, "PriceChecker/CHECK", 3, 21, 21, "Close", 250, 18247, 3);
        addHoveredButton(18248, "PriceChecker/CHECK", 2, 21, 21, 18248);
        rsi.totalChildren(67);
        rsi.child(0, 18245, 10, 20);
        rsi.child(1, 18246, 100, 56);
        rsi.child(2, 18247, 472, 23);
        rsi.child(3, 18248, 472, 23);
        addText(18350, "Total value:", tda, 0, 0xFFFFFF, false, true);
        rsi.child(4, 18350, 225, 295);
        addText(18351, "0", tda, 0, 0xFFFFFF, true, true);
        rsi.child(5, 18351, 251, 306);
        addText(18352, "", tda, 0, 0xFFFFFF, false, true);
        rsi.child(6, 18352, 120, 150);
        addText(18353, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(7, 18353, 120, 85);
        addText(18354, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(8, 18354, 120, 95);
        addText(18355, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(9, 18355, 120, 105);
        addText(18356, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(10, 18356, 190, 85);
        addText(18357, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(11, 18357, 190, 95);
        addText(18358, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(12, 18358, 190, 105);
        addText(18359, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(13, 18359, 260, 85);
        addText(18360, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(14, 18360, 260, 95);
        addText(18361, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(15, 18361, 260, 105);
        addText(18362, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(16, 18362, 330, 85);
        addText(18363, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(17, 18363, 330, 95);
        addText(18364, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(18, 18364, 330, 105);
        addText(18365, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(19, 18365, 400, 85);
        addText(18366, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(20, 18366, 400, 95);
        addText(18367, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(21, 18367, 400, 105);
        addText(18368, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(22, 18368, 120, 145);
        addText(18369, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(23, 18369, 120, 155);
        addText(18370, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(24, 18370, 120, 165);
        addText(18371, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(25, 18371, 190, 145);
        addText(18372, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(26, 18372, 190, 155);
        addText(18373, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(27, 18373, 190, 165);
        addText(18374, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(28, 18374, 260, 145);
        addText(18375, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(29, 18375, 260, 155);
        addText(18376, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(30, 18376, 260, 165);
        addText(18377, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(31, 18377, 330, 145);
        addText(18378, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(32, 18378, 330, 155);
        addText(18379, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(33, 18379, 330, 165);
        addText(18380, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(34, 18380, 400, 145);
        addText(18381, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(35, 18381, 400, 155);
        addText(18382, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(36, 18382, 400, 165);
        addText(18383, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(37, 18383, 120, 205);
        addText(18384, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(38, 18384, 120, 215);
        addText(18385, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(39, 18385, 120, 225);
        addText(18386, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(40, 18386, 190, 205);
        addText(18387, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(41, 18387, 190, 215);
        addText(18388, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(42, 18388, 190, 225);
        addText(18389, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(43, 18389, 260, 205);
        addText(18390, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(44, 18390, 260, 215);
        addText(18391, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(45, 18391, 260, 225);
        addText(18392, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(46, 18392, 330, 205);
        addText(18393, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(47, 18393, 330, 215);
        addText(18394, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(48, 18394, 330, 225);
        addText(18395, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(49, 18395, 400, 205);
        addText(18396, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(50, 18396, 400, 215);
        addText(18397, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(51, 18397, 400, 225);
        addText(18398, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(52, 18398, 120, 260);
        addText(18399, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(53, 18399, 120, 270);
        addText(18400, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(54, 18400, 120, 280);
        addText(18401, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(55, 18401, 190, 260);
        addText(18402, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(56, 18402, 190, 270);
        addText(18403, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(57, 18403, 190, 280);
        addText(18404, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(58, 18404, 260, 260);
        addText(18405, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(59, 18405, 260, 270);
        addText(18406, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(60, 18406, 260, 280);
        addText(18407, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(61, 18407, 330, 260);
        addText(18408, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(62, 18408, 330, 270);
        addText(18409, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(63, 18409, 330, 280);
        addText(18410, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(64, 18410, 400, 260);
        addText(18411, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(65, 18411, 400, 270);
        addText(18412, "", tda, 0, 0xFFFFFF, true, true);
        rsi.child(66, 18412, 400, 280);
    }

    public static void weaponGame(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(25347);
        tab.totalChildren(6);
        addSprite(25348, 0, "/Bounty/bh");
        addText(25349, "Test1", 0xFFFFFF, false, true, -1, tda, 1);
        addText(25350, "Test2", 0xB50707, true, true, -1, tda, 2);
        addText(25351, "Test3", 0xFFFFFF, false, true, -1, tda, 1);
        addText(25352, "Test4", 0xB50707, true, true, -1, tda, 2);
        addText(25353, "Time Remaining:", 0xFFFFFF, false, true, 52, tda, 0);
        tab.child(0, 25348, 332, 3);
        tab.child(1, 25349, 340, 13);
        tab.child(2, 25350, 452, 13);
        tab.child(3, 25351, 340, 34);
        tab.child(4, 25352, 452, 34);
        tab.child(5, 25353, 350, 63);
    }
    
    public static void dungeoneeringTab(TextDrawingArea[] TDA) {
        RSInterface Interface = addTabInterface(27000);
        setChildren(6, Interface);
        addText(27055, "Mistex", 0xFF981F, false, true, 52, TDA, 2);
        addSprite(27057, 0, "InformationTab/QUEST");
        addSprite(27058, 4, "InformationTab/QUEST");
        addText(27059, "Players Online:", 0xFF981F, false, true, 52, TDA, 0);
        addText(633, "1", 0xFF0000, false, true, 52, TDA, 0);
        setBounds(27055, 4, 2, 0, Interface);
        setBounds(27057, 0, 24, 1, Interface);
        setBounds(27058, 0, 22, 2, Interface);
        setBounds(27058, 0, 242, 3, Interface);
        setBounds(27059, 4, 246, 4, Interface);
        setBounds(27060, 0, 24, 5, Interface);
        Interface = addTabInterface(27060);
        Interface.scrollMax = 1600;
        Interface.height = 218;
        Interface.width = 174;
        setChildren(101, Interface);
        setBounds(663, 4, 67, 0, Interface);
        int Y = 2;
        int frame = 1;
        for (int i = 25026; i <= 25125; i++) {
            addText(i, "", 0xFF0000, false, true, -1, TDA, 1, 0xFFFFFF);
            setBounds(i, 8, Y, frame, Interface);
            frame++;
            Y += 16;
        }
    }

    public static void informationTab(TextDrawingArea[] TDA) {
        RSInterface Interface = addTabInterface(37000);
        setChildren(6, Interface);
        addText(37055, "Mistex", 0xFF981F, false, true, 52, TDA, 2);
        addSprite(37057, 0, "InformationTab/QUEST");
        addSprite(37058, 4, "InformationTab/QUEST");
        addText(37059, "Players Online:", 0xFF981F, false, true, 52, TDA, 0);
        addText(633, "", 0xFF0000, false, true, 52, TDA, 0);
        setBounds(37055, 4, 2, 0, Interface);
        setBounds(37057, 0, 24, 1, Interface);
        setBounds(37058, 0, 22, 2, Interface);
        setBounds(37058, 0, 242, 3, Interface);
        setBounds(37059, 4, 246, 4, Interface);
        setBounds(37060, 0, 24, 5, Interface);
        Interface = addTabInterface(37060);
        Interface.scrollMax = 1600;
        Interface.height = 218;
        Interface.width = 174;
        setChildren(101, Interface);
        setBounds(663, 4, 67, 0, Interface);
        int Y = 2;
        int frame = 1;
        for (int i = 35026; i <= 35125; i++) {
            addText(i, "", 0xFF0000, false, true, -1, TDA, 1, 0xFFFFFF);
            setBounds(i, 8, Y, frame, Interface);
            frame++;
            Y += 16;
        }
    }

    public static void fightCaves(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(24347);
        tab.totalChildren(2);
        addSprite(24348, 2, "/Bounty/bh");
        addText(24349, "Current Round: 0", 0xFFFFFF, false, true, -1, tda, 1);
        tab.child(0, 24348, 332, 3);
        tab.child(1, 24349, 355, 18);

    }

    public static void Pestpanel(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(21119);
        addText(21120, "Hacker", 0x999999, false, true, 52, tda, 1);
        addText(21121, "Alert", 0x33cc00, false, true, 52, tda, 1);
        addText(21122, "(Need 5 to 25 players)", 0xFFcc33, false, true, 52, tda, 1);
        addText(21123, "Points", 0x33ccff, false, true, 52, tda, 1);
        int last = 4;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21120, 15, 12, 0, RSinterface);
        setBounds(21121, 15, 30, 1, RSinterface);
        setBounds(21122, 15, 48, 2, RSinterface);
        setBounds(21123, 15, 66, 3, RSinterface);
    }

    public static void Pestpanel2(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(21100);
        addSprite(21101, 0, "Pest Control/PEST1");
        addSprite(21102, 1, "Pest Control/PEST1");
        addSprite(21103, 2, "Pest Control/PEST1");
        addSprite(21104, 3, "Pest Control/PEST1");
        addSprite(21105, 4, "Pest Control/PEST1");
        addSprite(21106, 5, "Pest Control/PEST1");
        addText(21107, "", 0xCC00CC, false, true, 52, tda, 1);
        addText(21108, "", 0x0000FF, false, true, 52, tda, 1);
        addText(21109, "", 0xFFFF44, false, true, 52, tda, 1);
        addText(21110, "", 0xCC0000, false, true, 52, tda, 1);
        addText(21111, "250", 0x99FF33, false, true, 52, tda, 1); // w purp
        addText(21112, "250", 0x99FF33, false, true, 52, tda, 1); // e blue
        addText(21113, "250", 0x99FF33, false, true, 52, tda, 1); // se yel
        addText(21114, "250", 0x99FF33, false, true, 52, tda, 1); // sw red
        addText(21115, "200", 0x99FF33, false, true, 52, tda, 1); // attacks
        addText(21116, "0", 0x99FF33, false, true, 52, tda, 1); // knights hp
        addText(21117, "Time Remaining:", 0xFFFFFF, false, true, 52, tda, 0);
        addText(21118, "", 0xFFFFFF, false, true, 52, tda, 0);
        int last = 18;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21101, 361, 26, 0, RSinterface);
        setBounds(21102, 396, 26, 1, RSinterface);
        setBounds(21103, 436, 26, 2, RSinterface);
        setBounds(21104, 474, 26, 3, RSinterface);
        setBounds(21105, 3, 21, 4, RSinterface);
        setBounds(21106, 3, 50, 5, RSinterface);
        setBounds(21107, 371, 60, 6, RSinterface);
        setBounds(21108, 409, 60, 7, RSinterface);
        setBounds(21109, 443, 60, 8, RSinterface);
        setBounds(21110, 479, 60, 9, RSinterface);
        setBounds(21111, 362, 10, 10, RSinterface);
        setBounds(21112, 398, 10, 11, RSinterface);
        setBounds(21113, 436, 10, 12, RSinterface);
        setBounds(21114, 475, 10, 13, RSinterface);
        setBounds(21115, 32, 32, 14, RSinterface);
        setBounds(21116, 32, 62, 15, RSinterface);
        setBounds(21117, 8, 88, 16, RSinterface);
        setBounds(21118, 87, 88, 17, RSinterface);
    }

    public static void trainingTeleports(TextDrawingArea[] paramArrayOfRSFont) {
        RSInterface localRSInterface = addInterface(60600);
        addSprite(60601, 0, "Teleporting/Background");
        addHoverButton(60602, "Teleporting/Tab", 0, 120, 26, "Select", 0, 60603, 1);
        addHoveredButton(60603, "Teleporting/Tab", 1, 120, 26, 60604);
        addHoverButton(60605, "Teleporting/Tab", 0, 120, 26, "Select", 0, 60606, 1);
        addHoveredButton(60606, "Teleporting/Tab", 1, 120, 26, 60607);
        addHoverButton(60608, "Teleporting/Tab", 0, 120, 26, "Select", 0, 60609, 1);
        addHoveredButton(60609, "Teleporting/Tab", 1, 120, 26, 60610);
        addHoverButton(60611, "Teleporting/Tab", 0, 120, 26, "Select", 0, 60612, 1);
        addHoveredButton(60612, "Teleporting/Tab", 1, 120, 26, 60613);
        addHoverButton(60614, "Teleporting/Tab", 0, 120, 26, "Select", 0, 60615, 1);
        addHoveredButton(60615, "Teleporting/Tab", 1, 120, 26, 60616);
        addHoverButton(60617, "Teleporting/Tab", 0, 120, 26, "Select", 0, 60618, 1);
        addHoveredButton(60618, "Teleporting/Tab", 1, 120, 26, 60619);
        addHoverButton(60622, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60623, 1);
        addHoveredButton(60623, "Teleporting/Button", 1, 90, 32, 60624);
        addHoverButton(60625, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60626, 1);
        addHoveredButton(60626, "Teleporting/Button", 1, 90, 32, 60627);
        addHoverButton(60628, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60629, 1);
        addHoveredButton(60629, "Teleporting/Button", 1, 90, 32, 60630);
        addHoverButton(60631, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60632, 1);
        addHoveredButton(60632, "Teleporting/Button", 1, 90, 32, 60633);
        addHoverButton(60634, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60635, 1);
        addHoveredButton(60635, "Teleporting/Button", 1, 90, 32, 60636);
        addHoverButton(60637, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60638, 1);
        addHoveredButton(60638, "Teleporting/Button", 1, 90, 32, 60639);
        addHoverButton(60640, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60641, 1);
        addHoveredButton(60641, "Teleporting/Button", 1, 90, 32, 60642);
        addHoverButton(60643, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60644, 1);
        addHoveredButton(60644, "Teleporting/Button", 1, 90, 32, 60645);
        addHoverButton(60646, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60647, 1);
        addHoveredButton(60647, "Teleporting/Button", 1, 90, 32, 60648);
        addHoverButton(60649, "Teleporting/Lock", 0, 69, 67, "View", 0, 60650, 1);
        addHoveredButton(60650, "Teleporting/Lock", 1, 69, 67, 60651);
        addHoverButton(60652, "Teleporting/Close", 0, 16, 16, "Close", 0, 60653, 3);
        addHoveredButton(60653, "Teleporting/Close", 1, 16, 16, 60654);

        addButton(60655, 0, "Teleporting/Icon", "");
        addText(60656, "Training", paramArrayOfRSFont, 1, 16252462, true, true);
        addText(60657, "Minigames", paramArrayOfRSFont, 1, 16252462, true, true);
        addText(60658, "Bosses", paramArrayOfRSFont, 1, 16252462, true, true);
        addText(60659, "Player-Killing", paramArrayOfRSFont, 1, 16252462, true, true);
        addText(60660, "Skilling", paramArrayOfRSFont, 1, 16252462, true, true);
        addText(60661, "Donator", paramArrayOfRSFont, 1, 16252462, true, true);

        addText(60662, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60663, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60664, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60665, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60666, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60667, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60668, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60669, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60670, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60671, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60672, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60673, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60674, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60675, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60676, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60677, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60678, "", paramArrayOfRSFont, 1, 16750623, true, true);
        addText(60679, "", paramArrayOfRSFont, 1, 16750623, true, true);

        addText(60690, "Teleporting - Select your destination", paramArrayOfRSFont, 2, 16750623, true, true);
        setChildren(69, localRSInterface);
        localRSInterface.child(0, 60601, 1, 5);
        localRSInterface.child(1, 60602, 14, 41);
        localRSInterface.child(2, 60603, 14, 41);
        localRSInterface.child(3, 60605, 14, 67);
        localRSInterface.child(4, 60606, 14, 67);
        localRSInterface.child(5, 60608, 14, 93);
        localRSInterface.child(6, 60609, 14, 93);
        localRSInterface.child(7, 60611, 14, 119);
        localRSInterface.child(8, 60612, 14, 119);
        localRSInterface.child(9, 60614, 14, 145);
        localRSInterface.child(10, 60615, 14, 145);
        localRSInterface.child(11, 60617, 14, 171);
        localRSInterface.child(12, 60618, 14, 171);
        localRSInterface.child(13, 60622, 153, 78);
        localRSInterface.child(14, 60623, 153, 78);
        localRSInterface.child(15, 60625, 267, 78);
        localRSInterface.child(16, 60626, 267, 78);
        localRSInterface.child(17, 60628, 383, 78);
        localRSInterface.child(18, 60629, 383, 78);
        localRSInterface.child(19, 60631, 153, 159);
        localRSInterface.child(20, 60632, 153, 159);
        localRSInterface.child(21, 60634, 267, 159);
        localRSInterface.child(22, 60635, 267, 159);
        localRSInterface.child(23, 60637, 383, 159);
        localRSInterface.child(24, 60638, 383, 159);
        localRSInterface.child(25, 60640, 153, 232);
        localRSInterface.child(26, 60641, 153, 232);
        localRSInterface.child(27, 60643, 267, 232);
        localRSInterface.child(28, 60644, 267, 232);
        localRSInterface.child(29, 60646, 383, 232);
        localRSInterface.child(30, 60647, 383, 232);
        localRSInterface.child(31, 60649, 38, 207);
        localRSInterface.child(32, 60650, 38, 207);
        localRSInterface.child(33, 60652, 480, 17);
        localRSInterface.child(34, 60653, 480, 17);
        localRSInterface.child(35, 60655, 190, 58);
        localRSInterface.child(36, 60655, 304, 58);
        localRSInterface.child(37, 60655, 419, 58);
        localRSInterface.child(38, 60655, 188, 139);
        localRSInterface.child(39, 60655, 304, 139);
        localRSInterface.child(40, 60655, 420, 139);
        localRSInterface.child(41, 60655, 188, 212);
        localRSInterface.child(42, 60655, 305, 212);
        localRSInterface.child(43, 60655, 419, 212);
        localRSInterface.child(44, 60656, 75, 50);
        localRSInterface.child(45, 60657, 75, 75);
        localRSInterface.child(46, 60658, 75, 103);
        localRSInterface.child(47, 60659, 75, 127);
        localRSInterface.child(48, 60660, 75, 155);
        localRSInterface.child(49, 60661, 75, 179);
        localRSInterface.child(50, 60662, 198, 79);
        localRSInterface.child(51, 60663, 198, 93);
        localRSInterface.child(52, 60664, 313, 79);
        localRSInterface.child(53, 60665, 313, 93);
        localRSInterface.child(54, 60666, 429, 79);
        localRSInterface.child(55, 60667, 429, 93);
        localRSInterface.child(56, 60668, 198, 160);
        localRSInterface.child(57, 60669, 198, 174);
        localRSInterface.child(58, 60670, 313, 160);
        localRSInterface.child(59, 60671, 313, 174);
        localRSInterface.child(60, 60672, 429, 160);
        localRSInterface.child(61, 60673, 429, 174);
        localRSInterface.child(62, 60674, 198, 233);
        localRSInterface.child(63, 60675, 198, 247);
        localRSInterface.child(64, 60676, 313, 233);
        localRSInterface.child(65, 60677, 313, 247);
        localRSInterface.child(66, 60678, 429, 233);
        localRSInterface.child(67, 60679, 429, 247);

        localRSInterface.child(68, 60690, 258, 18);
    }

    public static void minigameTeleports(TextDrawingArea[] TDA) {
        RSInterface localRSInterface = addInterface(60700);
        addButton(60682, 1, "Teleporting/Icon", "");
        setChildren(69, localRSInterface);
        localRSInterface.child(0, 60601, 1, 5);
        localRSInterface.child(1, 60602, 14, 41);
        localRSInterface.child(2, 60603, 14, 41);
        localRSInterface.child(3, 60605, 14, 67);
        localRSInterface.child(4, 60606, 14, 67);
        localRSInterface.child(5, 60608, 14, 93);
        localRSInterface.child(6, 60609, 14, 93);
        localRSInterface.child(7, 60611, 14, 119);
        localRSInterface.child(8, 60612, 14, 119);
        localRSInterface.child(9, 60614, 14, 145);
        localRSInterface.child(10, 60615, 14, 145);
        localRSInterface.child(11, 60617, 14, 171);
        localRSInterface.child(12, 60618, 14, 171);
        localRSInterface.child(13, 60622, 153, 78);
        localRSInterface.child(14, 60623, 153, 78);
        localRSInterface.child(15, 60625, 267, 78);
        localRSInterface.child(16, 60626, 267, 78);
        localRSInterface.child(17, 60628, 383, 78);
        localRSInterface.child(18, 60629, 383, 78);
        localRSInterface.child(19, 60631, 153, 159);
        localRSInterface.child(20, 60632, 153, 159);
        localRSInterface.child(21, 60634, 267, 159);
        localRSInterface.child(22, 60635, 267, 159);
        localRSInterface.child(23, 60637, 383, 159);
        localRSInterface.child(24, 60638, 383, 159);
        localRSInterface.child(25, 60640, 153, 232);
        localRSInterface.child(26, 60641, 153, 232);
        localRSInterface.child(27, 60643, 267, 232);
        localRSInterface.child(28, 60644, 267, 232);
        localRSInterface.child(29, 60646, 383, 232);
        localRSInterface.child(30, 60647, 383, 232);
        localRSInterface.child(31, 60649, 38, 207);
        localRSInterface.child(32, 60650, 38, 207);
        localRSInterface.child(33, 60652, 480, 17);
        localRSInterface.child(34, 60653, 480, 17);
        localRSInterface.child(35, 60682, 190, 58);
        localRSInterface.child(36, 60682, 304, 58);
        localRSInterface.child(37, 60682, 419, 58);
        localRSInterface.child(38, 60682, 188, 139);
        localRSInterface.child(39, 60682, 304, 139);
        localRSInterface.child(40, 60682, 420, 139);
        localRSInterface.child(41, 60682, 188, 213);
        localRSInterface.child(42, 60682, 305, 213);
        localRSInterface.child(43, 60682, 420, 214);
        localRSInterface.child(44, 60656, 75, 50);
        localRSInterface.child(45, 60657, 75, 75);
        localRSInterface.child(46, 60658, 75, 103);
        localRSInterface.child(47, 60659, 75, 127);
        localRSInterface.child(48, 60660, 75, 155);
        localRSInterface.child(49, 60661, 75, 179);
        localRSInterface.child(50, 60662, 198, 79);
        localRSInterface.child(51, 60663, 198, 93);
        localRSInterface.child(52, 60664, 313, 79);
        localRSInterface.child(53, 60665, 313, 93);
        localRSInterface.child(54, 60666, 429, 79);
        localRSInterface.child(55, 60667, 429, 93);
        localRSInterface.child(56, 60668, 198, 160);
        localRSInterface.child(57, 60669, 198, 174);
        localRSInterface.child(58, 60670, 313, 160);
        localRSInterface.child(59, 60671, 313, 174);
        localRSInterface.child(60, 60672, 429, 160);
        localRSInterface.child(61, 60673, 429, 174);
        localRSInterface.child(62, 60674, 198, 233);
        localRSInterface.child(63, 60675, 198, 247);
        localRSInterface.child(64, 60676, 313, 233);
        localRSInterface.child(65, 60677, 313, 247);
        localRSInterface.child(66, 60678, 429, 233);
        localRSInterface.child(67, 60679, 429, 247);

        localRSInterface.child(68, 60690, 258, 18);
    }

    public static void bossesTeleports(TextDrawingArea[] TDA) {
        RSInterface localRSInterface = addInterface(60800);
        addButton(60683, 2, "Teleporting/Icon", "");
        setChildren(69, localRSInterface);
        localRSInterface.child(0, 60601, 1, 5);
        localRSInterface.child(1, 60602, 14, 41);
        localRSInterface.child(2, 60603, 14, 41);
        localRSInterface.child(3, 60605, 14, 67);
        localRSInterface.child(4, 60606, 14, 67);
        localRSInterface.child(5, 60608, 14, 93);
        localRSInterface.child(6, 60609, 14, 93);
        localRSInterface.child(7, 60611, 14, 119);
        localRSInterface.child(8, 60612, 14, 119);
        localRSInterface.child(9, 60614, 14, 145);
        localRSInterface.child(10, 60615, 14, 145);
        localRSInterface.child(11, 60617, 14, 171);
        localRSInterface.child(12, 60618, 14, 171);
        localRSInterface.child(13, 60622, 153, 78);
        localRSInterface.child(14, 60623, 153, 78);
        localRSInterface.child(15, 60625, 267, 78);
        localRSInterface.child(16, 60626, 267, 78);
        localRSInterface.child(17, 60628, 383, 78);
        localRSInterface.child(18, 60629, 383, 78);
        localRSInterface.child(19, 60631, 153, 159);
        localRSInterface.child(20, 60632, 153, 159);
        localRSInterface.child(21, 60634, 267, 159);
        localRSInterface.child(22, 60635, 267, 159);
        localRSInterface.child(23, 60637, 383, 159);
        localRSInterface.child(24, 60638, 383, 159);
        localRSInterface.child(25, 60640, 153, 232);
        localRSInterface.child(26, 60641, 153, 232);
        localRSInterface.child(27, 60643, 267, 232);
        localRSInterface.child(28, 60644, 267, 232);
        localRSInterface.child(29, 60646, 383, 232);
        localRSInterface.child(30, 60647, 383, 232);
        localRSInterface.child(31, 60649, 38, 207);
        localRSInterface.child(32, 60650, 38, 207);
        localRSInterface.child(33, 60652, 480, 17);
        localRSInterface.child(34, 60653, 480, 17);
        localRSInterface.child(35, 60683, 190, 58);
        localRSInterface.child(36, 60683, 303, 58);
        localRSInterface.child(37, 60683, 419, 58);
        localRSInterface.child(38, 60683, 188, 139);
        localRSInterface.child(39, 60683, 303, 139);
        localRSInterface.child(40, 60683, 420, 139);
        localRSInterface.child(41, 60683, 188, 213);
        localRSInterface.child(42, 60683, 303, 213);
        localRSInterface.child(43, 60683, 419, 213);
        localRSInterface.child(44, 60656, 75, 50);
        localRSInterface.child(45, 60657, 75, 75);
        localRSInterface.child(46, 60658, 75, 103);
        localRSInterface.child(47, 60659, 75, 127);
        localRSInterface.child(48, 60660, 75, 155);
        localRSInterface.child(49, 60661, 75, 179);
        localRSInterface.child(50, 60662, 198, 79);
        localRSInterface.child(51, 60663, 198, 93);
        localRSInterface.child(52, 60664, 313, 79);
        localRSInterface.child(53, 60665, 313, 93);
        localRSInterface.child(54, 60666, 429, 79);
        localRSInterface.child(55, 60667, 429, 93);
        localRSInterface.child(56, 60668, 198, 160);
        localRSInterface.child(57, 60669, 198, 174);
        localRSInterface.child(58, 60670, 313, 160);
        localRSInterface.child(59, 60671, 313, 174);
        localRSInterface.child(60, 60672, 429, 160);
        localRSInterface.child(61, 60673, 429, 174);
        localRSInterface.child(62, 60674, 198, 233);
        localRSInterface.child(63, 60675, 198, 247);
        localRSInterface.child(64, 60676, 313, 233);
        localRSInterface.child(65, 60677, 313, 247);
        localRSInterface.child(66, 60678, 429, 233);
        localRSInterface.child(67, 60679, 429, 247);
        localRSInterface.child(68, 60690, 258, 18);
    }

    public static void playerKillingTeleports(TextDrawingArea[] TDA) {
        RSInterface localRSInterface = addInterface(60900);
        addButton(60684, 3, "Teleporting/Icon", "");
        setChildren(69, localRSInterface);
        localRSInterface.child(0, 60601, 1, 5);
        localRSInterface.child(1, 60602, 14, 41);
        localRSInterface.child(2, 60603, 14, 41);
        localRSInterface.child(3, 60605, 14, 67);
        localRSInterface.child(4, 60606, 14, 67);
        localRSInterface.child(5, 60608, 14, 93);
        localRSInterface.child(6, 60609, 14, 93);
        localRSInterface.child(7, 60611, 14, 119);
        localRSInterface.child(8, 60612, 14, 119);
        localRSInterface.child(9, 60614, 14, 145);
        localRSInterface.child(10, 60615, 14, 145);
        localRSInterface.child(11, 60617, 14, 171);
        localRSInterface.child(12, 60618, 14, 171);
        localRSInterface.child(13, 60622, 153, 78);
        localRSInterface.child(14, 60623, 153, 78);
        localRSInterface.child(15, 60625, 267, 78);
        localRSInterface.child(16, 60626, 267, 78);
        localRSInterface.child(17, 60628, 383, 78);
        localRSInterface.child(18, 60629, 383, 78);
        localRSInterface.child(19, 60631, 153, 159);
        localRSInterface.child(20, 60632, 153, 159);
        localRSInterface.child(21, 60634, 267, 159);
        localRSInterface.child(22, 60635, 267, 159);
        localRSInterface.child(23, 60637, 383, 159);
        localRSInterface.child(24, 60638, 383, 159);
        localRSInterface.child(25, 60640, 153, 232);
        localRSInterface.child(26, 60641, 153, 232);
        localRSInterface.child(27, 60643, 267, 232);
        localRSInterface.child(28, 60644, 267, 232);
        localRSInterface.child(29, 60646, 383, 232);
        localRSInterface.child(30, 60647, 383, 232);
        localRSInterface.child(31, 60649, 38, 207);
        localRSInterface.child(32, 60650, 38, 207);
        localRSInterface.child(33, 60652, 480, 17);
        localRSInterface.child(34, 60653, 480, 17);
        localRSInterface.child(35, 60684, 189, 56);
        localRSInterface.child(36, 60684, 303, 56);
        localRSInterface.child(37, 60684, 418, 56);
        localRSInterface.child(38, 60684, 187, 137);
        localRSInterface.child(39, 60684, 303, 137);
        localRSInterface.child(40, 60684, 419, 137);
        localRSInterface.child(41, 60684, 187, 210);
        localRSInterface.child(42, 60684, 303, 210);
        localRSInterface.child(43, 60684, 419, 210);
        localRSInterface.child(44, 60656, 75, 50);
        localRSInterface.child(45, 60657, 75, 75);
        localRSInterface.child(46, 60658, 75, 103);
        localRSInterface.child(47, 60659, 75, 127);
        localRSInterface.child(48, 60660, 75, 155);
        localRSInterface.child(49, 60661, 75, 179);
        localRSInterface.child(50, 60662, 198, 79);
        localRSInterface.child(51, 60663, 198, 93);
        localRSInterface.child(52, 60664, 313, 79);
        localRSInterface.child(53, 60665, 313, 93);
        localRSInterface.child(54, 60666, 429, 79);
        localRSInterface.child(55, 60667, 429, 93);
        localRSInterface.child(56, 60668, 198, 160);
        localRSInterface.child(57, 60669, 198, 174);
        localRSInterface.child(58, 60670, 313, 160);
        localRSInterface.child(59, 60671, 313, 174);
        localRSInterface.child(60, 60672, 429, 160);
        localRSInterface.child(61, 60673, 429, 174);
        localRSInterface.child(62, 60674, 198, 233);
        localRSInterface.child(63, 60675, 198, 247);
        localRSInterface.child(64, 60676, 313, 233);
        localRSInterface.child(65, 60677, 313, 247);
        localRSInterface.child(66, 60678, 429, 233);
        localRSInterface.child(67, 60679, 429, 247);
        localRSInterface.child(68, 60690, 258, 18);
    }

    public static void skillingTeleports(TextDrawingArea[] TDA) {
        RSInterface localRSInterface = addInterface(61000);
        addButton(60685, 4, "Teleporting/Icon", "");
        setChildren(69, localRSInterface);
        localRSInterface.child(0, 60601, 1, 5);
        localRSInterface.child(1, 60602, 14, 41);
        localRSInterface.child(2, 60603, 14, 41);
        localRSInterface.child(3, 60605, 14, 67);
        localRSInterface.child(4, 60606, 14, 67);
        localRSInterface.child(5, 60608, 14, 93);
        localRSInterface.child(6, 60609, 14, 93);
        localRSInterface.child(7, 60611, 14, 119);
        localRSInterface.child(8, 60612, 14, 119);
        localRSInterface.child(9, 60614, 14, 145);
        localRSInterface.child(10, 60615, 14, 145);
        localRSInterface.child(11, 60617, 14, 171);
        localRSInterface.child(12, 60618, 14, 171);
        localRSInterface.child(13, 60622, 153, 78);
        localRSInterface.child(14, 60623, 153, 78);
        localRSInterface.child(15, 60625, 267, 78);
        localRSInterface.child(16, 60626, 267, 78);
        localRSInterface.child(17, 60628, 383, 78);
        localRSInterface.child(18, 60629, 383, 78);
        localRSInterface.child(19, 60631, 153, 159);
        localRSInterface.child(20, 60632, 153, 159);
        localRSInterface.child(21, 60634, 267, 159);
        localRSInterface.child(22, 60635, 267, 159);
        localRSInterface.child(23, 60637, 383, 159);
        localRSInterface.child(24, 60638, 383, 159);
        localRSInterface.child(25, 60640, 153, 232);
        localRSInterface.child(26, 60641, 153, 232);
        localRSInterface.child(27, 60643, 267, 232);
        localRSInterface.child(28, 60644, 267, 232);
        localRSInterface.child(29, 60646, 383, 232);
        localRSInterface.child(30, 60647, 383, 232);
        localRSInterface.child(31, 60649, 38, 207);
        localRSInterface.child(32, 60650, 38, 207);
        localRSInterface.child(33, 60652, 480, 17);
        localRSInterface.child(34, 60653, 480, 17);
        localRSInterface.child(35, 60685, 190, 58);
        localRSInterface.child(36, 60685, 304, 58);
        localRSInterface.child(37, 60685, 419, 58);
        localRSInterface.child(38, 60685, 188, 139);
        localRSInterface.child(39, 60685, 304, 139);
        localRSInterface.child(40, 60685, 420, 139);
        localRSInterface.child(41, 60685, 188, 213);
        localRSInterface.child(42, 60685, 305, 213);
        localRSInterface.child(43, 60685, 419, 213);
        localRSInterface.child(44, 60656, 75, 50);
        localRSInterface.child(45, 60657, 75, 75);
        localRSInterface.child(46, 60658, 75, 103);
        localRSInterface.child(47, 60659, 75, 127);
        localRSInterface.child(48, 60660, 75, 155);
        localRSInterface.child(49, 60661, 75, 179);
        localRSInterface.child(50, 60662, 198, 79);
        localRSInterface.child(51, 60663, 198, 93);
        localRSInterface.child(52, 60664, 313, 79);
        localRSInterface.child(53, 60665, 313, 93);
        localRSInterface.child(54, 60666, 429, 79);
        localRSInterface.child(55, 60667, 429, 93);
        localRSInterface.child(56, 60668, 198, 160);
        localRSInterface.child(57, 60669, 198, 174);
        localRSInterface.child(58, 60670, 313, 160);
        localRSInterface.child(59, 60671, 313, 174);
        localRSInterface.child(60, 60672, 429, 160);
        localRSInterface.child(61, 60673, 429, 174);
        localRSInterface.child(62, 60674, 198, 233);
        localRSInterface.child(63, 60675, 198, 247);
        localRSInterface.child(64, 60676, 313, 233);
        localRSInterface.child(65, 60677, 313, 247);
        localRSInterface.child(66, 60678, 429, 233);
        localRSInterface.child(67, 60679, 429, 247);
        localRSInterface.child(68, 60690, 258, 18);
    }

    public static void donatorTeleports(TextDrawingArea[] TDA) {
        RSInterface localRSInterface = addInterface(61100);
        addButton(60686, 5, "Teleporting/Icon", "");
        setChildren(69, localRSInterface);
        localRSInterface.child(0, 60601, 1, 5);
        localRSInterface.child(1, 60602, 14, 41);
        localRSInterface.child(2, 60603, 14, 41);
        localRSInterface.child(3, 60605, 14, 67);
        localRSInterface.child(4, 60606, 14, 67);
        localRSInterface.child(5, 60608, 14, 93);
        localRSInterface.child(6, 60609, 14, 93);
        localRSInterface.child(7, 60611, 14, 119);
        localRSInterface.child(8, 60612, 14, 119);
        localRSInterface.child(9, 60614, 14, 145);
        localRSInterface.child(10, 60615, 14, 145);
        localRSInterface.child(11, 60617, 14, 171);
        localRSInterface.child(12, 60618, 14, 171);
        localRSInterface.child(13, 60622, 153, 78);
        localRSInterface.child(14, 60623, 153, 78);
        localRSInterface.child(15, 60625, 267, 78);
        localRSInterface.child(16, 60626, 267, 78);
        localRSInterface.child(17, 60628, 383, 78);
        localRSInterface.child(18, 60629, 383, 78);
        localRSInterface.child(19, 60631, 153, 159);
        localRSInterface.child(20, 60632, 153, 159);
        localRSInterface.child(21, 60634, 267, 159);
        localRSInterface.child(22, 60635, 267, 159);
        localRSInterface.child(23, 60637, 383, 159);
        localRSInterface.child(24, 60638, 383, 159);
        localRSInterface.child(25, 60640, 153, 232);
        localRSInterface.child(26, 60641, 153, 232);
        localRSInterface.child(27, 60643, 267, 232);
        localRSInterface.child(28, 60644, 267, 232);
        localRSInterface.child(29, 60646, 383, 232);
        localRSInterface.child(30, 60647, 383, 232);
        localRSInterface.child(31, 60649, 38, 207);
        localRSInterface.child(32, 60650, 38, 207);
        localRSInterface.child(33, 60652, 480, 17);
        localRSInterface.child(34, 60653, 480, 17);
        localRSInterface.child(35, 60686, 190, 58);
        localRSInterface.child(36, 60686, 304, 58);
        localRSInterface.child(37, 60686, 419, 58);
        localRSInterface.child(38, 60686, 188, 139);
        localRSInterface.child(39, 60686, 304, 139);
        localRSInterface.child(40, 60686, 420, 139);
        localRSInterface.child(41, 60686, 188, 213);
        localRSInterface.child(42, 60686, 304, 213);
        localRSInterface.child(43, 60686, 419, 213);
        localRSInterface.child(44, 60656, 75, 50);
        localRSInterface.child(45, 60657, 75, 75);
        localRSInterface.child(46, 60658, 75, 103);
        localRSInterface.child(47, 60659, 75, 127);
        localRSInterface.child(48, 60660, 75, 155);
        localRSInterface.child(49, 60661, 75, 179);
        localRSInterface.child(50, 60662, 198, 79);
        localRSInterface.child(51, 60663, 198, 93);
        localRSInterface.child(52, 60664, 313, 79);
        localRSInterface.child(53, 60665, 313, 93);
        localRSInterface.child(54, 60666, 429, 79);
        localRSInterface.child(55, 60667, 429, 93);
        localRSInterface.child(56, 60668, 198, 160);
        localRSInterface.child(57, 60669, 198, 174);
        localRSInterface.child(58, 60670, 313, 160);
        localRSInterface.child(59, 60671, 313, 174);
        localRSInterface.child(60, 60672, 429, 160);
        localRSInterface.child(61, 60673, 429, 174);
        localRSInterface.child(62, 60674, 198, 233);
        localRSInterface.child(63, 60675, 198, 247);
        localRSInterface.child(64, 60676, 313, 233);
        localRSInterface.child(65, 60677, 313, 247);
        localRSInterface.child(66, 60678, 429, 233);
        localRSInterface.child(67, 60679, 429, 247);

        localRSInterface.child(68, 60690, 258, 18);
    }

    public static void PVPInterface(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(21200);
        addSprite(21201, 0, "");
        addText(21202, "", tda, 1, 0xff9040, true, true);
        int last = 2;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21201, 400, 285, 0, RSinterface);
        setBounds(21202, 468, 318, 1, RSinterface);
    }

    public static void PVPInterface2(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(21300);
        addSprite(21301, 0, "");
        addText(21302, "", tda, 1, 0xff9040, true, true);
        int last = 2;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21301, 400, 285, 0, RSinterface);
        setBounds(21302, 468, 318, 1, RSinterface);
    }

    public static void PVPInterface3(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(21400);
        addSprite(21401, 0, "");
        addText(21402, "", tda, 1, 0xff9040, true, true);
        addText(21403, "", tda, 1, 0xffffff, true, true);
        int last = 3;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21401, 400, 285, 0, RSinterface);
        setBounds(21402, 468, 318, 1, RSinterface);
        setBounds(21403, 468, 318, 2, RSinterface); // TIMER
    }

    public static final void settingsTab(TextDrawingArea[] TDA) {
        RSInterface localRSInterface = addInterface(45500);
        addSprite(45501, 10, "/Settings/SPRITE");
        addHoverButton(45502, "/Settings/SPRITE", 2, 200, 30, "Toggle HP Bar", -1, 45503, 1);
        addHoveredButton(45503, "/Settings/SPRITE", 3, 200, 30, 45504);
        addHoverButton(45505, "/Settings/SPRITE", 2, 200, 30, "Toggle 10x Hits", -1, 45506, 1);
        addHoveredButton(45506, "/Settings/SPRITE", 3, 200, 30, 45507);
        addHoverButton(45508, "/Settings/SPRITE", 2, 200, 30, "Toggle Cursors", -1, 45509, 1);
        addHoveredButton(45509, "/Settings/SPRITE", 3, 200, 30, 45510);
        addHoverButton(45511, "/Settings/SPRITE", 2, 200, 30, "Toggle Menus", -1, 45512, 1);
        addHoveredButton(45512, "/Settings/SPRITE", 3, 200, 30, 45513);
        addHoverButton(45514, "/Settings/SPRITE", 2, 200, 30, "Toggle Custom HD", -1, 45515, 1);
        addHoveredButton(45515, "/Settings/SPRITE", 3, 200, 30, 45516);
        addHoverButton(45517, "/Settings/SPRITE", 2, 200, 30, "Toggle Hover Box", -1, 45518, 1);
        addHoveredButton(45518, "/Settings/SPRITE", 3, 200, 30, 45519);
        addHoverButton(45520, "/Settings/SPRITE", 2, 200, 30, "Toggle HP Above", -1, 45521, 1);
        addHoveredButton(45521, "/Settings/SPRITE", 3, 200, 30, 45522);
        addHoverButton(45523, "/Settings/SPRITE", 1, 200, 30, "Next Page", -1, 45524, 1); // next,
        // 1
        addHoveredButton(45524, "/Settings/SPRITE", 5, 200, 30, 45525); // 5
        addText(45526, "Toggle HP Bar", TDA, 0, 16750623, false, true);
        addText(45527, "Toggle 10x Hits", TDA, 0, 16750623, false, true);
        addText(45528, "Toggle Cursors", TDA, 0, 16750623, false, true);
        addText(45529, "Toggle Menus", TDA, 0, 16750623, false, true);
        addText(45530, "Toggle Custom HD", TDA, 0, 16750623, false, true);
        addText(45531, "Toggle Hover Box", TDA, 0, 16750623, false, true);
        addText(45532, "Toggle HP Above", TDA, 0, 16750623, false, true);
        localRSInterface.totalChildren(24);
        localRSInterface.child(0, 45501, 0, 0);
        localRSInterface.child(1, 45502, 25, 50);
        localRSInterface.child(2, 45503, 25, 50);
        localRSInterface.child(3, 45505, 25, 75);
        localRSInterface.child(4, 45506, 25, 75);
        localRSInterface.child(5, 45508, 25, 100);
        localRSInterface.child(6, 45509, 25, 100);
        localRSInterface.child(7, 45511, 25, 125);
        localRSInterface.child(8, 45512, 25, 125);
        localRSInterface.child(9, 45514, 25, 150);
        localRSInterface.child(10, 45515, 25, 150);
        localRSInterface.child(11, 45517, 25, 175);
        localRSInterface.child(12, 45518, 25, 175);
        localRSInterface.child(13, 45520, 25, 200);
        localRSInterface.child(14, 45521, 25, 200);
        localRSInterface.child(15, 45523, 38, 236);
        localRSInterface.child(16, 45524, 38, 236); // end
        localRSInterface.child(17, 45526, 38, 53);
        localRSInterface.child(18, 45527, 38, 78);
        localRSInterface.child(19, 45528, 38, 103);
        localRSInterface.child(20, 45529, 38, 128);
        localRSInterface.child(21, 45530, 38, 153);
        localRSInterface.child(22, 45531, 38, 178);
        localRSInterface.child(23, 45532, 38, 203);
        localRSInterface = addTabInterface(14000);
        localRSInterface.width = 474;
        localRSInterface.height = 213;
        localRSInterface.scrollMax = 305;
        for (int i = 14001; i <= 14030; ++i) {
            addText(i, "", TDA, 1, 16777215, false, true);
        }
        localRSInterface.totalChildren(30);
        int i = 0;
        int j = 5;
        for (int k = 14001; k <= 14030; ++k) {
            localRSInterface.child(i, k, 248, j);
            ++i;
            j += 13;
        }
    }

    public static final void settingsTab2(TextDrawingArea[] TDA) {
        RSInterface localRSInterface = addInterface(47500);
        addSprite(47501, 10, "/Settings/SPRITE");
        addHoverButton(47502, "/Settings/SPRITE", 2, 200, 30, "Toggle TriviaBot", -1, 47503, 1);
        addHoveredButton(47503, "/Settings/SPRITE", 3, 200, 30, 47504);
        addHoverButton(47505, "/Settings/SPRITE", 2, 200, 30, "Toggle Tweening", -1, 47506, 1);
        addHoveredButton(47506, "/Settings/SPRITE", 3, 200, 30, 47507);
        addHoverButton(47508, "/Settings/SPRITE", 2, 200, 30, "Toggle Yell Channel", -1, 47509, 1);
        addHoveredButton(47509, "/Settings/SPRITE", 3, 200, 30, 47510);
        addHoverButton(47511, "/Settings/SPRITE", 2, 200, 30, "Toggle Skill Orbs", -1, 47512, 1);
        addHoveredButton(47512, "/Settings/SPRITE", 3, 200, 30, 47513);
        addHoverButton(47514, "/Settings/SPRITE", 2, 200, 30, "Toggle Depth Buffering", -1, 47515, 1);
        addHoveredButton(47515, "/Settings/SPRITE", 3, 200, 30, 47516);
        addHoverButton(47517, "/Settings/SPRITE", 2, 200, 30, "", -1, 47518, 1);
        addHoveredButton(47518, "/Settings/SPRITE", 3, 200, 30, 47519);
        addHoverButton(47520, "/Settings/SPRITE", 2, 200, 30, "", -1, 47521, 1);
        addHoveredButton(47521, "/Settings/SPRITE", 3, 200, 30, 47522);
        addHoverButton(47523, "/Settings/SPRITE", 7, 200, 30, "", -1, 47524, 1); // next,
        // 1
        addHoveredButton(47524, "/Settings/SPRITE", 6, 200, 30, 47525); // 5
        addText(47526, "...", TDA, 0, 16750623, false, true);
        addText(47527, "...", TDA, 0, 16750623, false, true);
        addText(47528, "...", TDA, 0, 16750623, false, true);
        addText(47529, "...", TDA, 0, 16750623, false, true);
        addText(47530, "...", TDA, 0, 16750623, false, true);
        addText(47531, "...", TDA, 0, 16750623, false, true);
        addText(47532, "...", TDA, 0, 16750623, false, true);
        localRSInterface.totalChildren(24);
        localRSInterface.child(0, 47501, 0, 0);
        localRSInterface.child(1, 47502, 25, 50);
        localRSInterface.child(2, 47503, 25, 50);
        localRSInterface.child(3, 47505, 25, 75);
        localRSInterface.child(4, 47506, 25, 75);
        localRSInterface.child(5, 47508, 25, 100);
        localRSInterface.child(6, 47509, 25, 100);
        localRSInterface.child(7, 47511, 25, 125);
        localRSInterface.child(8, 47512, 25, 125);
        localRSInterface.child(9, 47514, 25, 150);
        localRSInterface.child(10, 47515, 25, 150);
        localRSInterface.child(11, 47517, 25, 175);
        localRSInterface.child(12, 47518, 25, 175);
        localRSInterface.child(13, 47520, 25, 200);
        localRSInterface.child(14, 47521, 25, 200);
        localRSInterface.child(15, 47523, 38, 236);
        localRSInterface.child(16, 47524, 38, 236); // end
        localRSInterface.child(17, 47526, 38, 53);
        localRSInterface.child(18, 47527, 38, 78);
        localRSInterface.child(19, 47528, 38, 103);
        localRSInterface.child(20, 47529, 38, 128);
        localRSInterface.child(21, 47530, 38, 153);
        localRSInterface.child(22, 47531, 38, 178);
        localRSInterface.child(23, 47532, 38, 203);
        localRSInterface = addTabInterface(14000);
        localRSInterface.width = 474;
        localRSInterface.height = 213;
        localRSInterface.scrollMax = 305;
        for (int i = 14001; i <= 14030; ++i) {
            addText(i, "", TDA, 1, 16777215, false, true);
        }
        localRSInterface.totalChildren(30);
        int i = 0;
        int j = 5;
        for (int k = 14001; k <= 14030; ++k) {
            localRSInterface.child(i, k, 248, j);
            ++i;
            j += 13;
        }
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
        addConfigButton(154, 147, 32, 32, "/Emotes/EMOTE", 41, 47, "Skillcape Emote", 0, 1, 700);
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

    public static void Trade(TextDrawingArea[] TDA) {
        RSInterface Interface = addInterface(3323);
        setChildren(19, Interface);
        addSprite(3324, 6, "/TradeTab/TRADE");
        addHover(3442, 3, 0, 3325, 1, "/Bank/BANK", 17, 17, "Close Window");
        addHovered(3325, 2, "/Bank/BANK", 17, 17, 3326);
        addText(3417, "Trading With:", 0xFF981F, true, true, 52, TDA, 2);
        addText(3418, "Trader's Offer", 0xFF981F, false, true, 52, TDA, 1);
        addText(3419, "Your Offer", 0xFF981F, false, true, 52, TDA, 1);
        addText(3421, "Accept", 0x00C000, true, true, 52, TDA, 1);
        addText(3423, "Decline", 0xC00000, true, true, 52, TDA, 1);

        addText(3431, "Waiting For Other Player", 0xFFFFFF, true, true, 52, TDA, 1);
        addText(23504, "Wealth transfer: 2147,000,000 coins' worth to Zezimablud12", 0xB9B855, true, true, -1, TDA, 0);
        addText(23505, "1 has\\n 28 free\\n inventory slots.", 0xFF981F, true, true, -1, TDA, 0);

        addText(23506, "Wealth transfer: 2147,000,000 coins' worth to Zezimablud12", 0xB9B855, false, true, -1, TDA, 0);
        addText(23507, "Wealth transfer: 2147,000,000 coins' worth to me", 0xB9B855, false, true, -1, TDA, 0);

        addHover(3420, 1, 0, 3327, 5, "/TradeTab/TRADE", 65, 32, "Accept");
        addHovered(3327, 2, "/TradeTab/TRADE", 65, 32, 3328);
        addHover(3422, 3, 0, 3329, 5, "/TradeTab/TRADE", 65, 32, "Decline");
        addHovered(3329, 2, "/TradeTab/TRADE", 65, 32, 3330);
        setBounds(3324, 0, 16, 0, Interface);
        setBounds(3442, 485, 24, 1, Interface);
        setBounds(3325, 485, 24, 2, Interface);
        setBounds(3417, 258, 25, 3, Interface);
        setBounds(3418, 355, 51, 4, Interface);
        setBounds(3419, 68, 51, 5, Interface);
        setBounds(3420, 223, 120, 6, Interface);
        setBounds(3327, 223, 120, 7, Interface);
        setBounds(3422, 223, 160, 8, Interface);
        setBounds(3329, 223, 160, 9, Interface);
        setBounds(3421, 256, 127, 10, Interface);
        setBounds(3423, 256, 167, 11, Interface);
        setBounds(3431, 256, 272, 12, Interface);
        setBounds(3415, 12, 64, 13, Interface);
        setBounds(3416, 321, 67, 14, Interface);
        setBounds(23505, 256, 67, 16, Interface);
        setBounds(23504, 255, 310, 15, Interface);
        setBounds(23506, 20, 310, 17, Interface);
        setBounds(23507, 380, 310, 18, Interface);
        Interface = addInterface(3443);
        setChildren(15, Interface);
        addSprite(3444, 3, "/TradeTab/TRADE");
        addButton(3546, 2, "/ShopTab/SHOP", 63, 24, "Accept", 1);
        addButton(3548, 2, "/ShopTab/SHOP", 63, 24, "Decline", 3);
        addText(3547, "Accept", 0x00C000, true, true, 52, TDA, 1);
        addText(3549, "Decline", 0xC00000, true, true, 52, TDA, 1);
        addText(3450, "Trading With:", 0x00FFFF, true, true, 52, TDA, 2);
        addText(3451, "Yourself", 0x00FFFF, true, true, 52, TDA, 2);
        setBounds(3444, 12, 20, 0, Interface);
        setBounds(3442, 470, 32, 1, Interface);
        setBounds(3325, 470, 32, 2, Interface);
        setBounds(3535, 130, 28, 3, Interface);
        setBounds(3536, 105, 47, 4, Interface);
        setBounds(3546, 189, 295, 5, Interface);
        setBounds(3548, 258, 295, 6, Interface);
        setBounds(3547, 220, 299, 7, Interface);
        setBounds(3549, 288, 299, 8, Interface);
        setBounds(3557, 71, 87, 9, Interface);
        setBounds(3558, 315, 87, 10, Interface);
        setBounds(3533, 64, 70, 11, Interface);
        setBounds(3534, 297, 70, 12, Interface);
        setBounds(3450, 95, 289, 13, Interface);
        setBounds(3451, 95, 304, 14, Interface);
    }

    public static void prayerTab(TextDrawingArea[] tda) {
        RSInterface rsinterface = addInterface(5608);
        int i = 0;
        int j = 0;
        byte byte0 = 10;
        byte byte1 = 50;
        byte byte2 = 10;
        byte byte3 = 86;
        byte byte4 = 10;
        byte byte5 = 122;
        byte byte6 = 10;
        char c = '\237';
        byte byte7 = 10;
        byte byte8 = 86;
        int k = 1;
        byte byte9 = 52;
        addText(687, "", 0xff981f, false, true, -1, tda, 1);
        addSprite(25105, 0, "PrayerTab/PRAYERICON");
        addPrayerWithTooltip(25000, 0, 83, 0, j, 25052, "Activate @lre@Thick Skin");
        j++;
        addPrayerWithTooltip(25002, 0, 84, 3, j, 25054, "Activate @lre@Burst of Strength");
        j++;
        addPrayerWithTooltip(25004, 0, 85, 6, j, 25056, "Activate @lre@Clarity of Thought");
        j++;
        addPrayerWithTooltip(25006, 0, 601, 7, j, 25058, "Activate @lre@Sharp Eye");
        j++;
        addPrayerWithTooltip(25008, 0, 602, 8, j, 25060, "Activate @lre@Mystic Will");
        j++;
        addPrayerWithTooltip(25010, 0, 86, 9, j, 25062, "Activate @lre@Rock Skin");
        j++;
        addPrayerWithTooltip(25012, 0, 87, 12, j, 25064, "Activate @lre@Superhuman Strength");
        j++;
        addPrayerWithTooltip(25014, 0, 88, 15, j, 25066, "Activate @lre@Improved Reflexes");
        j++;
        addPrayerWithTooltip(25016, 0, 89, 18, j, 25068, "Activate @lre@Rapid Restore");
        j++;
        addPrayerWithTooltip(25018, 0, 90, 21, j, 25070, "Activate @lre@Rapid Heal");
        j++;
        addPrayerWithTooltip(25020, 0, 91, 24, j, 25072, "Activate @lre@Protect Item");
        j++;
        addPrayerWithTooltip(25022, 0, 603, 25, j, 25074, "Activate @lre@Hawk Eye");
        j++;
        addPrayerWithTooltip(25024, 0, 604, 26, j, 25076, "Activate @lre@Mystic Lore");
        j++;
        addPrayerWithTooltip(25026, 0, 92, 27, j, 25078, "Activate @lre@Steel Skin");
        j++;
        addPrayerWithTooltip(25028, 0, 93, 30, j, 25080, "Activate @lre@Ultimate Strength");
        j++;
        addPrayerWithTooltip(25030, 0, 94, 33, j, 25082, "Activate @lre@Incredible Reflexes");
        j++;
        addPrayerWithTooltip(25032, 0, 95, 36, j, 25084, "Activate @lre@Protect from Magic");
        j++;
        addPrayerWithTooltip(25034, 0, 96, 39, j, 25086, "Activate @lre@Protect from Missles");
        j++;
        addPrayerWithTooltip(25036, 0, 97, 42, j, 25088, "Activate @lre@Protect from Melee");
        j++;
        addPrayerWithTooltip(25038, 0, 605, 43, j, 25090, "Activate @lre@Eagle Eye");
        j++;
        addPrayerWithTooltip(25040, 0, 606, 44, j, 25092, "Activate @lre@Mystic Might");
        j++;
        addPrayerWithTooltip(25042, 0, 98, 45, j, 25094, "Activate @lre@Retribution");
        j++;
        addPrayerWithTooltip(25044, 0, 99, 48, j, 25096, "Activate @lre@Redemption");
        j++;
        addPrayerWithTooltip(25046, 0, 100, 51, j, 25098, "Activate @lre@Smite");
        j++;
        addPrayerWithTooltip(25048, 0, 607, 59, j, 25100, "Activate @lre@Chivalry");
        j++;
        addPrayerWithTooltip(25050, 0, 608, 69, j, 25102, "Activate @lre@Piety");
        j++;
        addTooltip(25052, "Level 01\nThick Skin\nIncreases your Defence by 5%");
        addTooltip(25054, "Level 04\nBurst of Strength\nIncreases your Strength by 5%");
        addTooltip(25056, "Level 07\nClarity of Thought\nIncreases your Attack by 5%");
        addTooltip(25058, "Level 08\nSharp Eye\nIncreases your Ranged by 5%");
        addTooltip(25060, "Level 09\nMystic Will\nIncreases your Magic by 5%");
        addTooltip(25062, "Level 10\nRock Skin\nIncreases your Defence by 10%");
        addTooltip(25064, "Level 13\nSuperhuman Strength\nIncreases your Strength by 10%");
        addTooltip(25066, "Level 16\nImproved Reflexes\nIncreases your Attack by 10%");
        addTooltip(25068, "Level 19\nRapid Restore\n2x restore rate for all stats\nexcept Hitpoints, Summon" + "ing\nand Prayer");
        addTooltip(25070, "Level 22\nRapid Heal\n2x restore rate for the\nHitpoints stat");
        addTooltip(25072, "Level 25\nProtect Item\nKeep 1 extra item if you die");
        addTooltip(25074, "Level 26\nHawk Eye\nIncreases your Ranged by 10%");
        addTooltip(25076, "Level 27\nMystic Lore\nIncreases your Magic by 10%");
        addTooltip(25078, "Level 28\nSteel Skin\nIncreases your Defence by 15%");
        addTooltip(25080, "Level 31\nUltimate Strength\nIncreases your Strength by 15%");
        addTooltip(25082, "Level 34\nIncredible Reflexes\nIncreases your Attack by 15%");
        addTooltip(25084, "Level 37\nProtect from Magic\nProtection from magical attacks");
        addTooltip(25086, "Level 40\nProtect from Missles\nProtection from ranged attacks");
        addTooltip(25088, "Level 43\nProtect from Melee\nProtection from melee attacks");
        addTooltip(25090, "Level 44\nEagle Eye\nIncreases your Ranged by 15%");
        addTooltip(25092, "Level 45\nMystic Might\nIncreases your Magic by 15%");
        addTooltip(25094, "Level 46\nRetribution\nInflicts damage to nearby\ntargets if you die");
        addTooltip(25096, "Level 49\nRedemption\nHeals you when damaged\nand Hitpoints falls\nbelow 10%");
        addTooltip(25098, "Level 52\nSmite\n1/4 of damage dealt is\nalso removed from\nopponent's Prayer");
        addTooltip(25100, "Level 60\nChivalry\nIncreases your Defence by 20%,\nStrength by 18%, and Attack " + "by\n15%");
        addTooltip(25102, "Level 70\nPiety\nIncreases your Defence by 25%,\nStrength by 23%, and Attack by\n" + "20%");
        setChildren(80, rsinterface);
        setBounds(687, 85, 241, i, rsinterface);
        i++;
        setBounds(25105, 65, 241, i, rsinterface);
        i++;
        setBounds(25000, 2, 5, i, rsinterface);
        i++;
        setBounds(25001, 5, 8, i, rsinterface);
        i++;
        setBounds(25002, 40, 5, i, rsinterface);
        i++;
        setBounds(25003, 44, 8, i, rsinterface);
        i++;
        setBounds(25004, 76, 5, i, rsinterface);
        i++;
        setBounds(25005, 79, 11, i, rsinterface);
        i++;
        setBounds(25006, 113, 5, i, rsinterface);
        i++;
        setBounds(25007, 116, 10, i, rsinterface);
        i++;
        setBounds(25008, 150, 5, i, rsinterface);
        i++;
        setBounds(25009, 153, 9, i, rsinterface);
        i++;
        setBounds(25010, 2, 45, i, rsinterface);
        i++;
        setBounds(25011, 5, 48, i, rsinterface);
        i++;
        setBounds(25012, 39, 45, i, rsinterface);
        i++;
        setBounds(25013, 44, 47, i, rsinterface);
        i++;
        setBounds(25014, 76, 45, i, rsinterface);
        i++;
        setBounds(25015, 79, 49, i, rsinterface);
        i++;
        setBounds(25016, 113, 45, i, rsinterface);
        i++;
        setBounds(25017, 116, 50, i, rsinterface);
        i++;
        setBounds(25018, 151, 45, i, rsinterface);
        i++;
        setBounds(25019, 154, 50, i, rsinterface);
        i++;
        setBounds(25020, 2, 82, i, rsinterface);
        i++;
        setBounds(25021, 4, 84, i, rsinterface);
        i++;
        setBounds(25022, 40, 82, i, rsinterface);
        i++;
        setBounds(25023, 44, 87, i, rsinterface);
        i++;
        setBounds(25024, 77, 82, i, rsinterface);
        i++;
        setBounds(25025, 81, 85, i, rsinterface);
        i++;
        setBounds(25026, 114, 83, i, rsinterface);
        i++;
        setBounds(25027, 117, 85, i, rsinterface);
        i++;
        setBounds(25028, 153, 83, i, rsinterface);
        i++;
        setBounds(25029, 156, 87, i, rsinterface);
        i++;
        setBounds(25030, 2, 120, i, rsinterface);
        i++;
        setBounds(25031, 5, 125, i, rsinterface);
        i++;
        setBounds(25032, 40, 120, i, rsinterface);
        i++;
        setBounds(25033, 43, 124, i, rsinterface);
        i++;
        setBounds(25034, 78, 120, i, rsinterface);
        i++;
        setBounds(25035, 83, 124, i, rsinterface);
        i++;
        setBounds(25036, 114, 120, i, rsinterface);
        i++;
        setBounds(25037, 115, 121, i, rsinterface);
        i++;
        setBounds(25038, 151, 120, i, rsinterface);
        i++;
        setBounds(25039, 154, 124, i, rsinterface);
        i++;
        setBounds(25040, 2, 158, i, rsinterface);
        i++;
        setBounds(25041, 5, 160, i, rsinterface);
        i++;
        setBounds(25042, 39, 158, i, rsinterface);
        i++;
        setBounds(25043, 41, 158, i, rsinterface);
        i++;
        setBounds(25044, 76, 158, i, rsinterface);
        i++;
        setBounds(25045, 79, 163, i, rsinterface);
        i++;
        setBounds(25046, 114, 158, i, rsinterface);
        i++;
        setBounds(25047, 116, 158, i, rsinterface);
        i++;
        setBounds(25048, 153, 158, i, rsinterface);
        i++;
        setBounds(25049, 161, 160, i, rsinterface);
        i++;
        setBounds(25050, 2, 196, i, rsinterface);
        i++;
        setBounds(25051, 4, 207, i, rsinterface);
        setBoundry(++i, 25052, byte0 - 2, byte1, rsinterface);
        setBoundry(++i, 25054, byte0 - 5, byte1, rsinterface);
        setBoundry(++i, 25056, byte0, byte1, rsinterface);
        setBoundry(++i, 25058, byte0, byte1, rsinterface);
        setBoundry(++i, 25060, byte0, byte1, rsinterface);
        setBoundry(++i, 25062, byte2 - 9, byte3, rsinterface);
        setBoundry(++i, 25064, byte2 - 11, byte3, rsinterface);
        setBoundry(++i, 25066, byte2, byte3, rsinterface);
        setBoundry(++i, 25068, byte2, byte3, rsinterface);
        setBoundry(++i, 25070, byte2 + 25, byte3, rsinterface);
        setBoundry(++i, 25072, byte4, byte5, rsinterface);
        setBoundry(++i, 25074, byte4 - 2, byte5, rsinterface);
        setBoundry(++i, 25076, byte4, byte5, rsinterface);
        setBoundry(++i, 25078, byte4 - 7, byte5, rsinterface);
        setBoundry(++i, 25080, byte4 - 10, byte5, rsinterface);
        setBoundry(++i, 25082, byte6, c, rsinterface);
        setBoundry(++i, 25084, byte6 - 8, c, rsinterface);
        setBoundry(++i, 25086, byte6 - 7, c, rsinterface);
        setBoundry(++i, 25088, byte6 - 2, c, rsinterface);
        setBoundry(++i, 25090, byte6 - 2, c, rsinterface);
        setBoundry(++i, 25092, byte7, byte8, rsinterface);
        setBoundry(++i, 25094, byte7, byte8 - 20, rsinterface);
        setBoundry(++i, 25096, byte7, byte8 - 25, rsinterface);
        setBoundry(++i, 25098, byte7 + 15, byte8 - 25, rsinterface);
        setBoundry(++i, 25100, byte7 - 12, byte8 - 20, rsinterface);
        setBoundry(++i, 25102, k - 2, byte9, rsinterface);
        i++;
    }

    public static void addSpriteWithHover(int id, int spriteId, String spriteName, int hover) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = hover;
        tab.sprite1 = imageLoader(spriteId, spriteName);
        tab.sprite2 = imageLoader(spriteId, spriteName);
        tab.width = 190;
        tab.height = 47;
    }

    public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j, int dsc) {
        RSInterface rsinterface = addTabInterface(i);
        rsinterface.parentID = i;
        rsinterface.id = i;
        rsinterface.type = 4;
        rsinterface.atActionType = 1;
        rsinterface.width = 174;
        rsinterface.height = 11;
        rsinterface.contentType = 0;
        rsinterface.aByte254 = 0;
        rsinterface.hoverType = a;
        rsinterface.centerText = l;
        rsinterface.textShadow = m;
        rsinterface.textDrawingAreas = TDA[j];
        rsinterface.message = s;
        rsinterface.aString228 = "";
        rsinterface.anInt219 = 0;
        rsinterface.textColor = k;
        rsinterface.anInt216 = dsc;
        rsinterface.tooltip = s;
    }

    public static void setBoundry(int frame, int ID, int X, int Y, RSInterface RSInterface) {
        RSInterface.children[frame] = ID;
        RSInterface.childX[frame] = X;
        RSInterface.childY[frame] = Y;
    }

    public static void addPrayerWithTooltip(int i, int configId, int configFrame, int requiredValues, int prayerSpriteID, int Hover, String tooltip) {
        RSInterface Interface = addTabInterface(i);
        Interface.id = i;
        Interface.parentID = 5608;
        Interface.type = 5;
        Interface.atActionType = 4;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.hoverType = Hover;
        Interface.sprite1 = imageLoader(0, "PrayerTab/PRAYERGLOW");
        Interface.sprite2 = imageLoader(1, "PrayerTab/PRAYERGLOW");
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
        Interface.tooltip = tooltip;
        Interface = addTabInterface(i + 1);
        Interface.id = i + 1;
        Interface.parentID = 5608;
        Interface.type = 5;
        Interface.atActionType = 0;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.sprite1 = imageLoader(prayerSpriteID, "PrayerTab/PRAYERON");
        Interface.sprite2 = imageLoader(prayerSpriteID, "PrayerTab/PRAYEROFF");
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

    public static void addPrayer(int i, int configId, int configFrame, int anIntArray212, int spriteID, String prayerName) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = 5608;
        tab.type = 5;
        tab.atActionType = 4;
        tab.contentType = 0;
        tab.aByte254 = 0;
        tab.hoverType = -1;
        tab.sprite1 = imageLoader(0, "PRAYERGLOW");
        tab.sprite2 = imageLoader(1, "PRAYERGLOW");
        tab.width = 34;
        tab.height = 34;
        tab.valueCompareType = new int[1];
        tab.requiredValues = new int[1];
        tab.valueCompareType[0] = 1;
        tab.requiredValues[0] = configId;
        tab.valueIndexArray = new int[1][3];
        tab.valueIndexArray[0][0] = 5;
        tab.valueIndexArray[0][1] = configFrame;
        tab.valueIndexArray[0][2] = 0;
        tab.tooltip = "Activate@or2@ " + prayerName;
        // tab.tooltip = "Select";
        RSInterface tab2 = addTabInterface(i + 1);
        tab2.id = i + 1;
        tab2.parentID = 5608;
        tab2.type = 5;
        tab2.atActionType = 0;
        tab2.contentType = 0;
        tab2.aByte254 = 0;
        tab2.hoverType = -1;
        tab2.sprite1 = imageLoader(spriteID, "/PRAYER/PRAYON");
        tab2.sprite2 = imageLoader(spriteID, "/PRAYER/PRAYOFF");
        tab2.width = 34;
        tab2.height = 34;
        tab2.valueCompareType = new int[1];
        tab2.requiredValues = new int[1];
        tab2.valueCompareType[0] = 2;
        tab2.requiredValues[0] = anIntArray212 + 1;
        tab2.valueIndexArray = new int[1][3];
        tab2.valueIndexArray[0][0] = 2;
        tab2.valueIndexArray[0][1] = 5;
        tab2.valueIndexArray[0][2] = 0;
        // RSInterface tab3 = addTabInterface(i + 50);
    }

    public static void quickPrayers(TextDrawingArea[] TDA) {
        int i = 0;
        RSInterface localRSInterface = addTabInterface(20000);
        addSprite(17201, 3, "QuickPrayer/Sprite");
        addText(17240, "Select your quick prayers:", TDA, 0, 16750623, false, true);
        addTransparentSprite(17249, 0, "QuickPrayer/Sprite", 50);
        int j = 17202;
        for (int k = 630;
            (j <= 17231) || (k <= 659); ++k) {
            addConfigButton(j, 17200, 2, 1, "QuickPrayer/Sprite", 14, 15, "Select", 0, 1, k);
            j++;
        }
        addHoverButton(17241, "QuickPrayer/Sprite", 4, 190, 24, "Confirm Selection", -1, 17242, 1);
        addHoveredButton(17242, "QuickPrayer/Sprite", 5, 190, 24, 17243);
        setChildren(58, localRSInterface);
        setBounds(25001, 5, 28, i++, localRSInterface);
        setBounds(25003, 44, 28, i++, localRSInterface);
        setBounds(25005, 79, 31, i++, localRSInterface);
        setBounds(25007, 116, 30, i++, localRSInterface);
        setBounds(25009, 153, 29, i++, localRSInterface);
        setBounds(25011, 5, 68, i++, localRSInterface);
        setBounds(25013, 44, 67, i++, localRSInterface);
        setBounds(25015, 79, 69, i++, localRSInterface);
        setBounds(25017, 116, 70, i++, localRSInterface);
        setBounds(25019, 154, 70, i++, localRSInterface);
        setBounds(25021, 4, 104, i++, localRSInterface);
        setBounds(25023, 44, 107, i++, localRSInterface);
        setBounds(25025, 81, 105, i++, localRSInterface);
        setBounds(25027, 117, 105, i++, localRSInterface);
        setBounds(25029, 156, 107, i++, localRSInterface);
        setBounds(25031, 5, 145, i++, localRSInterface);
        setBounds(25033, 43, 144, i++, localRSInterface);
        setBounds(25035, 83, 144, i++, localRSInterface);
        setBounds(25037, 115, 141, i++, localRSInterface);
        setBounds(25039, 154, 144, i++, localRSInterface);
        setBounds(25041, 5, 180, i++, localRSInterface);
        setBounds(25043, 41, 178, i++, localRSInterface);
        setBounds(25045, 79, 183, i++, localRSInterface);
        setBounds(25047, 116, 178, i++, localRSInterface);
        setBounds(25049, 161, 180, i++, localRSInterface);
        // setBounds(18015, 4, 210, i++, localRSInterface);
        setBounds(25051, 5, 217, i++, localRSInterface);
        // setBounds(18061, 78, 212, i++, localRSInterface);
        // setBounds(18121, 116, 208, i++, localRSInterface);
        setBounds(17249, 0, 25, i++, localRSInterface);
        setBounds(17201, 0, 22, i++, localRSInterface);
        setBounds(17201, 0, 237, i++, localRSInterface);
        setBounds(17202, 2, 25, i++, localRSInterface);
        setBounds(17203, 41, 25, i++, localRSInterface);
        setBounds(17204, 76, 25, i++, localRSInterface);
        setBounds(17205, 113, 25, i++, localRSInterface);
        setBounds(17206, 150, 25, i++, localRSInterface);
        setBounds(17207, 2, 65, i++, localRSInterface);
        setBounds(17208, 41, 65, i++, localRSInterface);
        setBounds(17209, 76, 65, i++, localRSInterface);
        setBounds(17210, 113, 65, i++, localRSInterface);
        setBounds(17211, 150, 65, i++, localRSInterface);
        setBounds(17212, 2, 102, i++, localRSInterface);
        setBounds(17213, 41, 102, i++, localRSInterface);
        setBounds(17214, 76, 102, i++, localRSInterface);
        setBounds(17215, 113, 102, i++, localRSInterface);
        setBounds(17216, 150, 102, i++, localRSInterface);
        setBounds(17217, 2, 141, i++, localRSInterface);
        setBounds(17218, 41, 141, i++, localRSInterface);
        setBounds(17219, 76, 141, i++, localRSInterface);
        setBounds(17220, 113, 141, i++, localRSInterface);
        setBounds(17221, 150, 141, i++, localRSInterface);
        setBounds(17222, 2, 177, i++, localRSInterface);
        setBounds(17223, 41, 177, i++, localRSInterface);
        setBounds(17224, 76, 177, i++, localRSInterface);
        setBounds(17225, 113, 177, i++, localRSInterface);
        setBounds(17226, 150, 177, i++, localRSInterface);
        setBounds(17227, 1, 211, i++, localRSInterface);
        // setBounds(17228, 1, 211, i++, localRSInterface);
        // setBounds(17229, 75, 211, i++, localRSInterface);
        // setBounds(17230, 113, 211, i++, localRSInterface);
        setBounds(17240, 5, 5, i++, localRSInterface);
        setBounds(17241, 0, 237, i++, localRSInterface);
        setBounds(17242, 0, 237, i++, localRSInterface);
    }

    public static void Curses(TextDrawingArea[] TDA) {
        RSInterface Interface = addTabInterface(22500);
        int index = 0;
        addText(687, "99/99", 16750623, false, false, -1, TDA, 1);
        addSprite(22502, 0, "CurseTab/ICON");
        addPrayer(22503, 0, 610, 49, 7, "Protect Item", 22582);
        addPrayer(22505, 0, 611, 49, 4, "Sap Warrior", 22544);
        addPrayer(22507, 0, 612, 51, 5, "Sap Ranger", 22546);
        addPrayer(22509, 0, 613, 53, 3, "Sap Mage", 22548);
        addPrayer(22511, 0, 614, 55, 2, "Sap Spirit", 22550);
        addPrayer(22513, 0, 615, 58, 18, "Berserker", 22552);
        addPrayer(22515, 0, 616, 61, 15, "Deflect Summoning", 22554);
        addPrayer(22517, 0, 617, 64, 17, "Deflect Magic", 22556);
        addPrayer(22519, 0, 618, 67, 16, "Deflect Missiles", 22558);
        addPrayer(22521, 0, 619, 70, 6, "Deflect Melee", 22560);
        addPrayer(22523, 0, 620, 73, 9, "Leech Attack", 22562);
        addPrayer(22525, 0, 621, 75, 10, "Leech Ranged", 22564);
        addPrayer(22527, 0, 622, 77, 11, "Leech Magic", 22566);
        addPrayer(22529, 0, 623, 79, 12, "Leech Defence", 22568);
        addPrayer(22531, 0, 624, 81, 13, "Leech Strength", 22570);
        addPrayer(22533, 0, 625, 83, 14, "Leech Energy", 22572);
        addPrayer(22535, 0, 626, 85, 19, "Leech Special Attack", 22574);
        addPrayer(22537, 0, 627, 88, 1, "Wrath", 22576);
        addPrayer(22539, 0, 628, 91, 8, "Soul Split", 22578);
        addPrayer(22541, 0, 629, 94, 20, "Turmoil", 22580);
        drawTooltip(22582, "Level 50\nProtect Item\nKeep 1 extra item if you die");
        drawTooltip(22544, "Level 50\nSap Warrior\nDrains 10% of enemy Attack,\nStrength and Defence,\nincreasing to 20% over time");
        drawTooltip(22546, "Level 52\nSap Ranger\nDrains 10% of enemy Ranged\nand Defence, increasing to 20%\nover time");
        drawTooltip(22548, "Level 54\nSap Mage\nDrains 10% of enemy Magic\nand Defence, increasing to 20%\nover time");
        drawTooltip(22550, "Level 56\nSap Spirit\nDrains enenmy special attack\nenergy");
        drawTooltip(22552, "Level 59\nBerserker\nBoosted stats last 15% longer");
        drawTooltip(22554, "Level 62\nDeflect Summoning\nReduces damage dealt from\nSummoning scrolls, prevents the\nuse of a familiar's special\nattack, and can deflect some of\ndamage back to the attacker");
        drawTooltip(22556, "Level 65\nDeflect Magic\nProtects against magical attacks\nand can deflect some of the\ndamage back to the attacker");
        drawTooltip(22558, "Level 68\nDeflect Missiles\nProtects against ranged attacks\nand can deflect some of the\ndamage back to the attacker");
        drawTooltip(22560, "Level 71\nDeflect Melee\nProtects against melee attacks\nand can deflect some of the\ndamage back to the attacker");
        drawTooltip(22562, "Level 74\nLeech Attack\nBoosts Attack by 5%, increasing\nto 10% over time, while draining\nenemy Attack by 10%, increasing\nto 25% over time");
        drawTooltip(22564, "Level 76\nLeech Ranged\nBoosts Ranged by 5%, increasing\nto 10% over time, while draining\nenemy Ranged by 10%,\nincreasing to 25% over\ntime");
        drawTooltip(22566, "Level 78\nLeech Magic\nBoosts Magic by 5%, increasing\nto 10% over time, while draining\nenemy Magic by 10%, increasing\nto 25% over time");
        drawTooltip(22568, "Level 80\nLeech Defence\nBoosts Defence by 5%, increasing\nto 10% over time, while draining\n enemy Defence by10%,\nincreasing to 25% over\ntime");
        drawTooltip(22570, "Level 82\nLeech Strength\nBoosts Strength by 5%, increasing\nto 10% over time, while draining\nenemy Strength by 10%, increasing\n to 25% over time");
        drawTooltip(22572, "Level 84\nLeech Energy\nDrains enemy run energy, while\nincreasing your own");
        drawTooltip(22574, "Level 86\nLeech Special Attack\nDrains enemy special attack\nenergy, while increasing your\nown");
        drawTooltip(22576, "Level 89\nWrath\nInflicts damage to nearby\ntargets if you die");
        drawTooltip(22578, "Level 92\nSoul Split\n1/4 of damage dealt is also removed\nfrom opponent's Prayer and\nadded to your Hitpoints");
        drawTooltip(22580, "Level 95\nTurmoil\nIncreases Attack and Defence\nby 15%, plus 15% of enemy's\nlevel, and Strength by 23% plus\n10% of enemy's level");
        setChildren(62, Interface);
        setBounds(687, 85, 241, index, Interface);
        index++;
        setBounds(22502, 65, 241, index, Interface);
        index++;
        setBounds(22503, 2, 5, index, Interface);
        index++;
        setBounds(22504, 8, 8, index, Interface);
        index++;
        setBounds(22505, 40, 5, index, Interface);
        index++;
        setBounds(22506, 47, 12, index, Interface);
        index++;
        setBounds(22507, 76, 5, index, Interface);
        index++;
        setBounds(22508, 82, 11, index, Interface);
        index++;
        setBounds(22509, 113, 5, index, Interface);
        index++;
        setBounds(22510, 116, 8, index, Interface);
        index++;
        setBounds(22511, 150, 5, index, Interface);
        index++;
        setBounds(22512, 155, 10, index, Interface);
        index++;
        setBounds(22513, 2, 45, index, Interface);
        index++;
        setBounds(22514, 9, 48, index, Interface);
        index++;
        setBounds(22515, 39, 45, index, Interface);
        index++;
        setBounds(22516, 42, 47, index, Interface);
        index++;
        setBounds(22517, 76, 45, index, Interface);
        index++;
        setBounds(22518, 79, 48, index, Interface);
        index++;
        setBounds(22519, 113, 45, index, Interface);
        index++;
        setBounds(22520, 116, 48, index, Interface);
        index++;
        setBounds(22521, 151, 45, index, Interface);
        index++;
        setBounds(22522, 154, 48, index, Interface);
        index++;
        setBounds(22523, 2, 82, index, Interface);
        index++;
        setBounds(22524, 6, 86, index, Interface);
        index++;
        setBounds(22525, 40, 82, index, Interface);
        index++;
        setBounds(22526, 42, 86, index, Interface);
        index++;
        setBounds(22527, 77, 82, index, Interface);
        index++;
        setBounds(22528, 79, 86, index, Interface);
        index++;
        setBounds(22529, 114, 83, index, Interface);
        index++;
        setBounds(22530, 119, 87, index, Interface);
        index++;
        setBounds(22531, 153, 83, index, Interface);
        index++;
        setBounds(22532, 156, 86, index, Interface);
        index++;
        setBounds(22533, 2, 120, index, Interface);
        index++;
        setBounds(22534, 7, 125, index, Interface);
        index++;
        setBounds(22535, 40, 120, index, Interface);
        index++;
        setBounds(22536, 45, 124, index, Interface);
        index++;
        setBounds(22537, 78, 120, index, Interface);
        index++;
        setBounds(22538, 86, 124, index, Interface);
        index++;
        setBounds(22539, 114, 120, index, Interface);
        index++;
        setBounds(22540, 120, 125, index, Interface);
        index++;
        setBounds(22541, 151, 120, index, Interface);
        index++;
        setBounds(22542, 153, 127, index, Interface);
        index++;
        setBounds(22582, 10, 40, index, Interface);
        index++;
        setBounds(22544, 20, 40, index, Interface);
        index++;
        setBounds(22546, 20, 40, index, Interface);
        index++;
        setBounds(22548, 20, 40, index, Interface);
        index++;
        setBounds(22550, 20, 40, index, Interface);
        index++;
        setBounds(22552, 10, 80, index, Interface);
        index++;
        setBounds(22554, 10, 80, index, Interface);
        index++;
        setBounds(22556, 10, 80, index, Interface);
        index++;
        setBounds(22558, 10, 80, index, Interface);
        index++;
        setBounds(22560, 10, 80, index, Interface);
        index++;
        setBounds(22562, 10, 120, index, Interface);
        index++;
        setBounds(22564, 10, 120, index, Interface);
        index++;
        setBounds(22566, 10, 120, index, Interface);
        index++;
        setBounds(22568, 5, 120, index, Interface);
        index++;
        setBounds(22570, 5, 120, index, Interface);
        index++;
        setBounds(22572, 10, 160, index, Interface);
        index++;
        setBounds(22574, 10, 160, index, Interface);
        index++;
        setBounds(22576, 10, 160, index, Interface);
        index++;
        setBounds(22578, 10, 160, index, Interface);
        index++;
        setBounds(22580, 10, 160, index, Interface);
        index++;
    }

    public static void skilllevel(TextDrawingArea[] tda) {
        RSInterface attack = interfaceCache[6247];
        RSInterface defence = interfaceCache[6253];
        RSInterface str = interfaceCache[6206];
        RSInterface hits = interfaceCache[6216];
        RSInterface rng = interfaceCache[4443];
        RSInterface pray = interfaceCache[6242];
        RSInterface mage = interfaceCache[6211];
        RSInterface cook = interfaceCache[6226];
        RSInterface wood = interfaceCache[4272];
        RSInterface flet = interfaceCache[6231];
        RSInterface fish = interfaceCache[6258];
        RSInterface fire = interfaceCache[4282];
        RSInterface craf = interfaceCache[6263];
        RSInterface smit = interfaceCache[6221];
        RSInterface mine = interfaceCache[4416];
        RSInterface herb = interfaceCache[6237];
        RSInterface agil = interfaceCache[4277];
        RSInterface thie = interfaceCache[4261];
        RSInterface slay = interfaceCache[12122];
        RSInterface farm = interfaceCache[5267];
        RSInterface rune = interfaceCache[4267];
        RSInterface cons = interfaceCache[7267];
        RSInterface hunt = interfaceCache[8267];
        RSInterface summ = addInterface(9267);
        RSInterface dung = addInterface(10267);
        addSprite(17878, 0, "Interfaces/skillchat/skill");
        addSprite(17879, 1, "Interfaces/skillchat/skill");
        addSprite(17880, 2, "Interfaces/skillchat/skill");
        addSprite(17881, 3, "Interfaces/skillchat/skill");
        addSprite(17882, 4, "Interfaces/skillchat/skill");
        addSprite(17883, 5, "Interfaces/skillchat/skill");
        addSprite(17884, 6, "Interfaces/skillchat/skill");
        addSprite(17885, 7, "Interfaces/skillchat/skill");
        addSprite(17886, 8, "Interfaces/skillchat/skill");
        addSprite(17887, 9, "Interfaces/skillchat/skill");
        addSprite(17888, 10, "Interfaces/skillchat/skill");
        addSprite(17889, 11, "Interfaces/skillchat/skill");
        addSprite(17890, 12, "Interfaces/skillchat/skill");
        addSprite(17891, 13, "Interfaces/skillchat/skill");
        addSprite(17892, 14, "Interfaces/skillchat/skill");
        addSprite(17893, 15, "Interfaces/skillchat/skill");
        addSprite(17894, 16, "Interfaces/skillchat/skill");
        addSprite(17895, 17, "Interfaces/skillchat/skill");
        addSprite(17896, 18, "Interfaces/skillchat/skill");
        addSprite(11897, 19, "Interfaces/skillchat/skill");
        addSprite(17898, 20, "Interfaces/skillchat/skill");
        addSprite(17899, 21, "Interfaces/skillchat/skill");
        addSprite(17900, 22, "Interfaces/skillchat/skill");
        addSprite(17901, 23, "Interfaces/skillchat/skill");
        addSprite(17902, 24, "Interfaces/skillchat/skill");
        setChildren(4, attack);
        setBounds(17878, 20, 30, 0, attack);
        setBounds(4268, 80, 15, 1, attack);
        setBounds(4269, 80, 45, 2, attack);
        setBounds(358, 95, 75, 3, attack);
        setChildren(4, defence);
        setBounds(17879, 20, 30, 0, defence);
        setBounds(4268, 80, 15, 1, defence);
        setBounds(4269, 80, 45, 2, defence);
        setBounds(358, 95, 75, 3, defence);
        setChildren(4, str);
        setBounds(17880, 20, 30, 0, str);
        setBounds(4268, 80, 15, 1, str);
        setBounds(4269, 80, 45, 2, str);
        setBounds(358, 95, 75, 3, str);
        setChildren(4, hits);
        setBounds(17881, 20, 30, 0, hits);
        setBounds(4268, 80, 15, 1, hits);
        setBounds(4269, 80, 45, 2, hits);
        setBounds(358, 95, 75, 3, hits);
        setChildren(4, rng);
        setBounds(17882, 20, 30, 0, rng);
        setBounds(4268, 80, 15, 1, rng);
        setBounds(4269, 80, 45, 2, rng);
        setBounds(358, 95, 75, 3, rng);
        setChildren(4, pray);
        setBounds(17883, 20, 30, 0, pray);
        setBounds(4268, 80, 15, 1, pray);
        setBounds(4269, 80, 45, 2, pray);
        setBounds(358, 95, 75, 3, pray);
        setChildren(4, mage);
        setBounds(17884, 20, 30, 0, mage);
        setBounds(4268, 80, 15, 1, mage);
        setBounds(4269, 80, 45, 2, mage);
        setBounds(358, 95, 75, 3, mage);
        setChildren(4, cook);
        setBounds(17885, 20, 30, 0, cook);
        setBounds(4268, 80, 15, 1, cook);
        setBounds(4269, 80, 45, 2, cook);
        setBounds(358, 95, 75, 3, cook);
        setChildren(4, wood);
        setBounds(17886, 20, 30, 0, wood);
        setBounds(4268, 80, 15, 1, wood);
        setBounds(4269, 80, 45, 2, wood);
        setBounds(358, 95, 75, 3, wood);
        setChildren(4, flet);
        setBounds(17887, 20, 30, 0, flet);
        setBounds(4268, 80, 15, 1, flet);
        setBounds(4269, 80, 45, 2, flet);
        setBounds(358, 95, 75, 3, flet);
        setChildren(4, fish);
        setBounds(17888, 20, 30, 0, fish);
        setBounds(4268, 80, 15, 1, fish);
        setBounds(4269, 80, 45, 2, fish);
        setBounds(358, 95, 75, 3, fish);
        setChildren(4, fire);
        setBounds(17889, 20, 30, 0, fire);
        setBounds(4268, 80, 15, 1, fire);
        setBounds(4269, 80, 45, 2, fire);
        setBounds(358, 95, 75, 3, fire);
        setChildren(4, craf);
        setBounds(17890, 20, 30, 0, craf);
        setBounds(4268, 80, 15, 1, craf);
        setBounds(4269, 80, 45, 2, craf);
        setBounds(358, 95, 75, 3, craf);
        setChildren(4, smit);
        setBounds(17891, 20, 30, 0, smit);
        setBounds(4268, 80, 15, 1, smit);
        setBounds(4269, 80, 45, 2, smit);
        setBounds(358, 95, 75, 3, smit);
        setChildren(4, mine);
        setBounds(17892, 20, 30, 0, mine);
        setBounds(4268, 80, 15, 1, mine);
        setBounds(4269, 80, 45, 2, mine);
        setBounds(358, 95, 75, 3, mine);
        setChildren(4, herb);
        setBounds(17893, 20, 30, 0, herb);
        setBounds(4268, 80, 15, 1, herb);
        setBounds(4269, 80, 45, 2, herb);
        setBounds(358, 95, 75, 3, herb);
        setChildren(4, agil);
        setBounds(17894, 20, 30, 0, agil);
        setBounds(4268, 80, 15, 1, agil);
        setBounds(4269, 80, 45, 2, agil);
        setBounds(358, 95, 75, 3, agil);
        setChildren(4, thie);
        setBounds(17895, 20, 30, 0, thie);
        setBounds(4268, 80, 15, 1, thie);
        setBounds(4269, 80, 45, 2, thie);
        setBounds(358, 95, 75, 3, thie);
        setChildren(4, slay);
        setBounds(17896, 20, 30, 0, slay);
        setBounds(4268, 80, 15, 1, slay);
        setBounds(4269, 80, 45, 2, slay);
        setBounds(358, 95, 75, 3, slay);
        setChildren(3, farm);
        setBounds(4268, 80, 15, 0, farm);
        setBounds(4269, 80, 45, 1, farm);
        setBounds(358, 95, 75, 2, farm);
        setChildren(4, rune);
        setBounds(17898, 20, 30, 0, rune);
        setBounds(4268, 80, 15, 1, rune);
        setBounds(4269, 80, 45, 2, rune);
        setBounds(358, 95, 75, 3, rune);
        setChildren(3, cons);
        setBounds(4268, 80, 15, 0, cons);
        setBounds(4269, 80, 45, 1, cons);
        setBounds(358, 95, 75, 2, cons);
        setChildren(3, hunt);
        setBounds(4268, 80, 15, 0, hunt);
        setBounds(4269, 80, 45, 1, hunt);
        setBounds(358, 95, 75, 2, hunt);
        setChildren(4, summ);
        setBounds(17901, 20, 30, 0, summ);
        setBounds(4268, 80, 15, 1, summ);
        setBounds(4269, 80, 45, 2, summ);
        setBounds(358, 95, 75, 3, summ);
        setChildren(4, dung);
        setBounds(17902, 20, 30, 0, dung);
        setBounds(4268, 80, 15, 1, dung);
        setBounds(4269, 80, 45, 2, dung);
        setBounds(358, 95, 75, 3, dung);
    }

    public static void Achievement(TextDrawingArea[] TDA) {
        String directory = "/Achievements/img";
        RSInterface tab1 = addTabInterface(23133);
        addButton(23134, 1, directory, "", 0, 0, 500, 500);
        addText(23135, "T A S K C O M P L E T E", 0xAF6A1A, false, true, 52, 2);
        addText(23136, "You completed the achievement\\!", 0xAF6A1B, true, true, 0, 1);
        tab1.totalChildren(3);
        tab1.child(0, 23134, 158, 0);
        tab1.child(1, 23135, 193, 5);
        tab1.child(2, 23136, 254, 22);
    }

    public static void questInterface(TextDrawingArea[] TDA) {
        RSInterface Interface = addInterface(8134);
        Interface.centerText = true;
        addSprite(8135, 0, "QuestTab/QUESTBG");
        addSprite(8136, 1, "QuestTab/QUESTBG");
        addText(8144, "Quest Name", 0x000000, true, false, 52, TDA, 3); // 249
        // 18
        addHover(8137, 3, 0, 8138, 0, "QuestTab/CLOSE", 26, 23, "Close");
        addHovered(8138, 1, "QuestTab/CLOSE", 26, 23, 8139);
        setChildren(6, Interface);
        setBounds(8136, 18, 4, 0, Interface);
        setBounds(8135, 18, 62, 1, Interface);
        setBounds(8144, 260, 15, 2, Interface);
        setBounds(8140, 50, 86, 3, Interface);
        setBounds(8137, 452, 63, 4, Interface);
        setBounds(8138, 452, 63, 5, Interface);
        Interface = addInterface(8140);
        Interface.height = 217;
        Interface.width = 404;
        Interface.scrollMax = 1300;
        setChildren(51, Interface);
        int Ypos = 18;
        int frameID = 0;
        for (int iD = 8145; iD <= 8195; iD++) {
            // addText(iD, "", 0x000080, true, false, 52, TDA, 1);
            if (iD == 8155)
                addHoverText(iD, "", "Change title", TDA, 1, 0xff0000, true, false, 30); // 48
            else
                addHoverText(iD, "", "", TDA, 1, 0xff0000, true, false, 30); // 48

            setBounds(iD, 202, Ypos, frameID, Interface);
            frameID++;
            Ypos += 19;
            Ypos++;
        }
    }

    public static void friendsTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(5065);
        RSInterface list = interfaceCache[5066];
        addSprite(16126, 4, "/PrivateMessaging/SPRITE");
        addSprite(16127, 1, "/PrivateMessaging/SPRITE");
        addText(5067, "Friends List", tda, 1, 0xff9933, true, true);
        addText(5070, "    Mistex", tda, 0, 0xff9933, false, true);
        addText(5071, "", tda, 0, 0xff9933, false, true);
        addHoverButton(5068, "/PrivateMessaging/SPRITE", 6, 29, 29, "Add Name", 201, 5072, 1);
        addHoveredButton(5072, "/PrivateMessaging/SPRITE", 7, 29, 29, 5073);
        addHoverButton(5069, "/PrivateMessaging/SPRITE", 9, 29, 29, "Delete Name", 202, 5074, 1);
        addHoveredButton(5074, "/PrivateMessaging/SPRITE", 10, 29, 29, 5075);
        addHoverButton(5076, "/PrivateMessaging/SPRITE", 15, 215, 32, "", 0, 5077, 1);
        addHoveredButton(5077, "/PrivateMessaging/SPRITE", 16, 215, 32, 5078);
        addHoverButton(5079, "/PrivateMessaging/SPRITE", 17, 215, 32, "", 0, 5080, 1);
        addHoveredButton(5080, "/PrivateMessaging/SPRITE", 18, 215, 32, 5081);
        tab.totalChildren(15);
        tab.child(0, 16127, 0, 40);
        tab.child(1, 5067, 92, 5);
        tab.child(2, 16126, 0, 40);
        tab.child(3, 5066, 0, 42);
        tab.child(4, 16126, 0, 231);
        tab.child(5, 5068, 5, 240);
        tab.child(6, 5072, 4, 240);
        tab.child(7, 5069, 25, 240);
        tab.child(8, 5074, 24, 240);
        tab.child(9, 5076, 150, 236);
        tab.child(10, 5077, 150, 237);
        tab.child(11, 5079, 170, 236);
        tab.child(12, 5080, 170, 237);
        tab.child(13, 5070, 64, 25);
        tab.child(14, 5071, 106, 237);
        list.height = 189;
        list.width = 174;
        list.scrollMax = 200;
        for (int id = 5092, i = 0; id <= 5191 && i <= 99; id++, i++) {
            list.children[i] = id;
            list.childX[i] = 3;
            list.childY[i] = list.childY[i] - 7;
        }
        for (int id = 5192, i = 100; id <= 5291 && i <= 199; id++, i++) {
            list.children[i] = id;
            list.childX[i] = 131;
            list.childY[i] = list.childY[i] - 7;
        }
    }

    public static void ignoreTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(5715);
        RSInterface list = interfaceCache[5716];
        addText(5717, "Ignore List", tda, 1, 0xff9933, true, true);
        addText(5720, "    Mistex", tda, 0, 0xff9933, false, true);
        addText(5721, "", tda, 0, 0xff9933, false, true);
        addHoverButton(5718, "/PrivateMessaging/SPRITE", 11, 29, 29, "Add Name", 501, 5722, 1);
        addHoveredButton(5722, "/PrivateMessaging/SPRITE", 12, 29, 29, 5723);
        addHoverButton(5719, "/PrivateMessaging/SPRITE", 13, 29, 29, "Delete Name", 502, 5724, 1);
        addHoveredButton(5724, "/PrivateMessaging/SPRITE", 14, 29, 29, 5725);
        tab.totalChildren(15);
        tab.child(0, 5717, 92, 5);
        tab.child(1, 16127, 0, 40);
        tab.child(2, 16126, 0, 40);
        tab.child(3, 5716, 0, 42);
        tab.child(4, 16126, 0, 231);
        tab.child(5, 5718, 5, 240);
        tab.child(6, 5722, 4, 240);
        tab.child(7, 5719, 25, 240);
        tab.child(8, 5724, 24, 240);
        tab.child(9, 5076, 150, 236);
        tab.child(10, 5077, 150, 237);
        tab.child(11, 5079, 170, 236);
        tab.child(12, 5080, 170, 237);
        tab.child(13, 5720, 64, 25);
        tab.child(14, 5721, 108, 237);
        list.height = 189;
        list.width = 174;
        list.scrollMax = 200;
        for (int id = 5742, i = 0; id <= 5841 && i <= 99; id++, i++) {
            list.children[i] = id;
            list.childX[i] = 3;
            list.childY[i] = list.childY[i] - 7;
        }
    }

    public static void PkPInterface(TextDrawingArea[] wid) {
        RSInterface rsinterface = addTabInterface(15000);
        rsinterface.totalChildren(3);
        addSprite(15001, 0, "/PkInterface/SPRITE");
        lotteryItem(15002);
        addText(15003, "You've won a : ", wid, 0, 0xffffff, false, true);
        rsinterface.child(0, 15001, 165, 120);
        rsinterface.child(1, 15002, 250, 180);
        rsinterface.child(2, 15003, 185, 240);
    }

    public static void PkPInterfaceBlank(TextDrawingArea[] wid) {
        RSInterface rsinterface = addTabInterface(16000);
        rsinterface.totalChildren(3);
        addSprite(16001, 1, "/PkInterface/SPRITE");
        lotteryItem(16002);
        addText(16003, "You've won a : ", wid, 0, 0xffffff, false, true);
        rsinterface.child(0, 16001, 165, 120);
        rsinterface.child(1, 16002, 250, 180);
        rsinterface.child(2, 16003, 185, 240);
    }

    private static void playerProfiler(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(29000);
        addSprite(29001, 1, "/Achievements/SPRITE");
        addText(29002, "Player Profiler", 0xAF6A1A, false, true, 52, 2);
        setChildren(3, tab);
        setBounds(29001, 0, -3, 0, tab);
        setBounds(29002, 11, 7, 1, tab);
        setBounds(29003, 0, 29, 2, tab);

        RSInterface scrollInterface = addTabInterface(29003);
        scrollInterface.width = 166;
        scrollInterface.height = 224;
        scrollInterface.scrollMax = 750; // we will need to adjust this
        setChildren(50, scrollInterface);
        int y = 0;
        for (int i = 0; i < 50; i++) {
            addHoverText(29004 + i, "", "View", tda, 0, 0xff0000, false, true, 60);
            setBounds(29004 + i, 11, y, i, scrollInterface);
            y += 15;
        }
    }

    private static void achievementsTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(31000);
        addSprite(31001, 0, "/Achievements/TABBG");
        addText(31002, "Achievements", 0xAF6A1A, false, true, 52, 2);
        setChildren(3, tab);
        setBounds(31001, 0, -3, 0, tab);
        setBounds(31002, 11, 7, 1, tab);
        setBounds(31003, 0, 25, 2, tab);

        RSInterface scrollInterface = addTabInterface(31003);
        scrollInterface.width = 174;
        scrollInterface.height = 236;
        scrollInterface.scrollMax = 750; // we will need to adjust this
        setChildren(50, scrollInterface);
        int y = 0;
        for (int i = 0; i < 50; i++) {
            addHoverText(31004 + i, " ", "View", tda, 0, 0xff0000, false, true, 60);
            setBounds(31004 + i, 11, y, i, scrollInterface);
            y += 15;
        }
    }

    public static void infoTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(6000);
        addText(6100, "Information", tda, 2, 0xff9933, true, true);
        addSprite(6001, 0, "/QuestTab/img");
        addSprite(6002, 1, "/QuestTab/img");
        addText(6200, "@lre@Name: @gre@- ", tda, 0, 0xff9933, false, true);
        addText(6201, "@lre@Level: @gre@-", tda, 0, 0xff9933, false, true);
        addText(6202, "@lre@[@gre@0@lre@] Voting Points", tda, 0, 0xff9933, false, true);
        addText(6203, "@lre@[@gre@0@lre@] Slayer Points", tda, 0, 0xff9933, false, true);
        addText(6204, "@lre@[@gre@0@lre@] Pest Control Points", tda, 0, 0xff9933, false, true);
        addText(6205, "@lre@[@gre@0@lre@] Donator Points", tda, 0, 0xff9933, false, true);
        addText(6206, "@lre@[@gre@0@lre@] Skilling Points", tda, 0, 0xff9933, false, true);
        addText(6207, "@lre@[@gre@0@lre@] PvM Points", tda, 0, 0xff9933, false, true);
        addText(6208, "@lre@[@gre@0@lre@] Quest Points", tda, 0, 0xff9933, false, true);
        addText(6209, "@lre@[@gre@0@lre@] Target Kills", tda, 0, 0xff9933, false, true);
        addText(6210, "@lre@[@gre@0@lre@] Target Points", tda, 0, 0xff9933, false, true);
        addText(6211, "@lre@[@gre@0@lre@] PK Points", tda, 0, 0xff9933, false, true);
        addText(6212, "@lre@[@gre@0@lre@] Kills", tda, 0, 0xff9933, false, true);
        addText(6213, "@lre@[@gre@0@lre@] Deaths", tda, 0, 0xff9933, false, true);
        addText(6214, "@lre@[@gre@0@lre@] Kill/Death Ratio", tda, 0, 0xff9933, false, true);
        addText(6215, "@lre@www.@gre@Mistex@lre@.org", tda, 0, 0xff9933, true, true);
        int y = 25;
        tab.totalChildren(21);
        tab.child(0, 6100, 95, 3);
        tab.child(1, 6002, 0, 20);
        tab.child(2, 6001, 0, 23);
        tab.child(3, 6001, 0, 56);
        tab.child(4, 6200, 15, y);
        y += 13;
        tab.child(5, 6201, 15, y);
        y += 16;
        tab.child(6, 6202, 15, y);
        y += 15;
        tab.child(7, 6203, 15, y);
        y += 15;
        tab.child(8, 6204, 15, y);
        y += 15;
        tab.child(9, 6205, 15, y);
        y += 15;
        tab.child(10, 6206, 15, y);
        y += 15;
        tab.child(11, 6207, 15, y);
        y += 15;
        tab.child(12, 6208, 15, y);
        y += 15;
        tab.child(13, 6209, 15, y);
        y += 15;
        tab.child(14, 6210, 15, y);
        y += 15;
        tab.child(15, 6211, 15, y);
        y += 15;
        tab.child(16, 6212, 15, y);
        y += 15;
        tab.child(17, 6213, 15, y);
        y += 15;
        tab.child(18, 6214, 15, y);
        y += 15;
        tab.child(19, 6002, 0, 248);
        tab.child(20, 6215, 95, 250);
    }

    private static void Shop(TextDrawingArea[] TDA) {
        RSInterface rsinterface = addInterface(3824);
        setChildren(8 + 36, rsinterface);
        addSprite(3825, 0, "Shop/SHOP");
        addHover(3902, 1, 0, 3826, 1, "Shop/CLOSE", 17, 17, "Close Window");
        addHovered(3826, 2, "Shop/CLOSE", 17, 17, 3827);
        addText(19679, "", 0xff981f, false, true, 52, TDA, 1);
        addText(19680, "", 0xbf751d, false, true, 52, TDA, 1);
        addButton(19681, 2, "Shop/SHOP", 0, 0, "", 1);
        addSprite(19687, 1, "Shop/ITEMBG");
        for (int i = 0; i < 36; i++) {
            addText(28000 + i, "-1,0", 0xffff00, false, false, 52, TDA, 0);
        }
        setBounds(3825, 6, 8, 0, rsinterface);
        setBounds(3902, 478, 10, 1, rsinterface);
        setBounds(3826, 478, 10, 2, rsinterface);
        setBounds(3900, 32, 50, 3, rsinterface);
        setBounds(3901, 240, 11, 4, rsinterface);
        setBounds(19679, 42, 54, 5, rsinterface);
        setBounds(19680, 150, 54, 6, rsinterface);
        setBounds(19681, 129, 50, 7, rsinterface);
        for (int i = 0; i < 36; i++) {
            int x = i % 9;
            int y = i / 9;
            x = 52 * x + 32;
            y = 65 * y + 84;
            setBounds(28000 + i, x, y, 8 + i, rsinterface);
        }
        rsinterface = interfaceCache[3900];
        setChildren(1, rsinterface);
        setBounds(19687, 6, 15, 0, rsinterface);
        rsinterface.invSpritePadX = 20;
        rsinterface.width = 9;
        rsinterface.height = 4;
        rsinterface.invSpritePadY = 33;
        rsinterface.spritesX = new int[36];
        rsinterface.spritesY = new int[36];
        rsinterface.inv = new int[36];
        rsinterface.invStackSizes = new int[36];
        rsinterface = addInterface(19682);
        addSprite(19683, 1, "Shop/SHOP");
        addText(19684, "Main Stock", 0xbf751d, false, true, 52, TDA, 1);
        addText(19685, "Store Info", 0xff981f, false, true, 52, TDA, 1);
        addButton(19686, 2, "Shop/SHOP", 95, 19, "Main Stock", 1);
        setChildren(7, rsinterface);
        setBounds(19683, 12, 12, 0, rsinterface);
        setBounds(3901, 240, 21, 1, rsinterface);
        setBounds(19684, 42, 54, 2, rsinterface);
        setBounds(19685, 150, 54, 3, rsinterface);
        setBounds(19686, 23, 50, 4, rsinterface);
        setBounds(3902, 471, 22, 5, rsinterface);
        setBounds(3826, 60, 85, 6, rsinterface);
    }

    public static void equipmentScreen(TextDrawingArea[] wid) {
        RSInterface tab = addTabInterface(15106);
        addSprite(15107, 1, "Equipment/bg");
        addHoverButton(15210, "Equipment/SPRITE", 1, 21, 21, "Close", 250, 15211, 3);
        addHoveredButton(15211, "Equipment/SPRITE", 3, 21, 21, 15212);
        addText(15111, "", wid, 2, 0xe4a146, false, true);
        int rofl = 3;
        addText(15112, "Attack bonuses", wid, 2, 0xFF8900, false, true);
        addText(15113, "Defence bonuses", wid, 2, 0xFF8900, false, true);
        addText(15114, "Other bonuses", wid, 2, 0xFF8900, false, true);
        addText(19148, "Summoning: +0", wid, 1, 0xFF8900, false, true);
        addText(19149, "Absorb Melee: +0%", wid, 1, 0xFF9200, false, true);
        addText(19150, "Absorb Magic: +0%", wid, 1, 0xFF9200, false, true);
        addText(19151, "Absorb Ranged: +0%", wid, 1, 0xFF9200, false, true);
        addText(19152, "Ranged Strength: 0", wid, 1, 0xFF9200, false, true);
        addText(19153, "Magic Damage: +0%", wid, 1, 0xFF9200, false, true);
        for (int i = 1675; i <= 1684; i++) {
            textSize(i, wid, 1);
        }
        textSize(1686, wid, 1);
        textSize(1687, wid, 1);
        addChar(15125);
        tab.totalChildren(51);
        tab.child(0, 15107, 15, 5);
        tab.child(1, 15210, 476, 8);
        tab.child(2, 15211, 476, 8);
        tab.child(3, 15111, 14, 30);
        int Child = 4;
        int Y = 45;
        tab.child(16, 15112, 24, 30 - rofl);
        for (int i = 1675; i <= 1679; i++) {
            tab.child(Child, i, 29, Y - rofl);
            Child++;
            Y += 14;
        }
        int edit = 7 + rofl;
        tab.child(18, 15113, 24, 122 - edit); // 147
        tab.child(9, 1680, 29, 137 - edit - 2); // 161
        tab.child(10, 1681, 29, 153 - edit - 3);
        tab.child(11, 1682, 29, 168 - edit - 3);
        tab.child(12, 1683, 29, 183 - edit - 3);
        tab.child(13, 1684, 29, 197 - edit - 3);
        tab.child(44, 19148, 29, 211 - edit - 3);
        tab.child(45, 19149, 29, 225 - edit - 3);
        tab.child(46, 19150, 29, 239 - edit - 3);
        tab.child(47, 19151, 29, 253 - edit - 3);
        /* bottom */
        int edit2 = 33 - rofl, edit3 = 2;
        tab.child(19, 15114, 24, 223 + edit2);
        tab.child(14, 1686, 29, 262 - 24 + edit2 - edit3);
        tab.child(17, 19152, 29, 276 - 24 + edit2 - edit3);
        tab.child(48, 1687, 29, 290 - 24 + edit2 - edit3);
        tab.child(49, 19153, 29, 304 - 24 + edit2 - edit3);

        tab.child(15, 15125, 170, 200);
        tab.child(20, 1645, 104 + 295, 149 - 52);
        tab.child(21, 1646, 399, 163);
        tab.child(22, 1647, 399, 163);
        tab.child(23, 1648, 399, 58 + 146);
        tab.child(24, 1649, 26 + 22 + 297 - 2, 110 - 44 + 118 - 13 + 5);
        tab.child(25, 1650, 321 + 22, 58 + 154);
        tab.child(26, 1651, 321 + 134, 58 + 118);
        tab.child(27, 1652, 321 + 134, 58 + 154);
        tab.child(28, 1653, 321 + 48, 58 + 81);
        tab.child(29, 1654, 321 + 107, 58 + 81);
        tab.child(30, 1655, 321 + 58, 58 + 42);
        tab.child(31, 1656, 321 + 112, 58 + 41);
        tab.child(32, 1657, 321 + 78, 58 + 4);
        tab.child(33, 1658, 321 + 37, 58 + 43);
        tab.child(34, 1659, 321 + 78, 58 + 43);
        tab.child(35, 1660, 321 + 119, 58 + 43);
        tab.child(36, 1661, 321 + 22, 58 + 82);
        tab.child(37, 1662, 321 + 78, 58 + 82);
        tab.child(38, 1663, 321 + 134, 58 + 82);
        tab.child(39, 1664, 321 + 78, 58 + 122);
        tab.child(40, 1665, 321 + 78, 58 + 162);
        tab.child(41, 1666, 321 + 22, 58 + 162);
        tab.child(42, 1667, 321 + 134, 58 + 162);
        tab.child(43, 1688, 50 + 297 - 2, 110 - 13 + 5);
        for (int i = 1675; i <= 1684; i++) {
            RSInterface rsi = interfaceCache[i];
            rsi.textColor = 0xFF9200;
            rsi.centerText = false;
        }
        for (int i = 1686; i <= 1687; i++) {
            RSInterface rsi = interfaceCache[i];
            rsi.textColor = 0xFF9200;
            rsi.centerText = false;
        }

        /*
         * bank thing
         */
        RSInterface bankthing = addInterface(50064);
        bankthing.totalChildren(2);
        addHoverButton(50065, "Bank/BANK", 25, 34, 37, "Back To Bank", 0, 50066, 1);
        addHoveredButton(50066, "Bank/BANK", 26, 34, 37, 50067);
        bankthing.child(0, 50065, 0, 0);
        bankthing.child(1, 50066, 0, 0);
        bankthing.width = 34;
        bankthing.height = 37;
        bankthing.isMouseoverTriggered = true;
        tab.child(50, 50064, 454, 30);
    }

    public static void equipmentTab(TextDrawingArea[] wid) {
        RSInterface Interface = interfaceCache[1644];
        addSprite(15101, 0, "Equipment/bl");
        addSprite(15102, 1, "Equipment/bl");
        addSprite(15109, 2, "Equipment/bl");
        addSprite(15110, 3, "Equipment/bl");
        removeSomething(21338);
        removeSomething(21344);
        removeSomething(21342);
        removeSomething(21341);
        removeSomething(21340);
        removeSomething(15103);
        removeSomething(15104);
        Interface.children[26] = 27650;
        Interface.childX[26] = 0;
        Interface.childY[26] = 0;
        Interface = addInterface(27650);
        addHoverButton(15201, "Equipment/CUSTOM", 1, 40, 40, "Show Equipment Screen", 0, 15202, 1);
        addHoveredButton(15202, "Equipment/CUSTOM", 5, 40, 40, 15203);
        addHoverButton(15204, "Equipment/CUSTOM", 2, 40, 40, "Items Kept on Death", 0, 15205, 1);
        addHoveredButton(15205, "Equipment/CUSTOM", 4, 40, 40, 15206);
        addHoverButton(15207, "Equipment/CUSTOM", 3, 40, 40, "Price Checker", 0, 15208, 1);
        addHoveredButton(15208, "Equipment/CUSTOM", 6, 40, 40, 15209);
        setChildren(6, Interface);
        setBounds(15201, 21, 210, 0, Interface);
        setBounds(15202, 21, 210, 1, Interface);
        setBounds(15204, 132, 210, 2, Interface);
        setBounds(15205, 132, 210, 3, Interface);
        setBounds(15207, 76, 210, 4, Interface);
        setBounds(15208, 76, 210, 5, Interface);
    }

    public static void itemsOnDeathDATA(TextDrawingArea[] wid) {
        RSInterface rsinterface = addInterface(17115);
        addText(17109, "7", wid, 0, 0xff981f);
        addText(17110, "b", wid, 0, 0xff981f);
        addText(17111, "c", wid, 0, 0xff981f);
        addText(17112, "8", wid, 0, 0xff981f);
        addText(17113, "e", wid, 0, 0xff981f);
        addText(17114, "f", wid, 0, 0xff981f);
        addText(17117, "9", wid, 0, 0xff981f);
        addText(17118, "h", wid, 0, 0xff981f);
        addText(17119, "i", wid, 0, 0xff981f);
        addText(17120, "10", wid, 0, 0xff981f);
        addText(17121, "k", wid, 0, 0xff981f);
        addText(17122, "l", wid, 0, 0xff981f);
        addText(17123, "11", wid, 0, 0xff981f);
        addText(17124, "n", wid, 0, 0xff981f);
        addText(17125, "o", wid, 0, 0xff981f);
        addText(17126, "12", wid, 0, 0xff981f);
        addText(17127, "q", wid, 0, 0xff981f);
        addText(17128, "r", wid, 0, 0xff981f);
        addText(17129, "13", wid, 0, 0xff981f);
        addText(17130, "t", wid, 0, 0xff981f);
        rsinterface.parentID = 17115;
        rsinterface.id = 17115;
        rsinterface.interfaceType = 0;
        rsinterface.atActionType = 0;
        rsinterface.contentType = 0;
        rsinterface.width = 130;
        rsinterface.height = 197;
        rsinterface.opacity = 0;
        rsinterface.hoverType = -1;
        rsinterface.scrollMax = 280;
        rsinterface.children = new int[20];
        rsinterface.childX = new int[20];
        rsinterface.childY = new int[20];
        rsinterface.children[0] = 17109;
        rsinterface.childX[0] = 0;
        rsinterface.childY[0] = 0;
        rsinterface.children[1] = 17110;
        rsinterface.childX[1] = 0;
        rsinterface.childY[1] = 12;
        rsinterface.children[2] = 17111;
        rsinterface.childX[2] = 0;
        rsinterface.childY[2] = 24;
        rsinterface.children[3] = 17112;
        rsinterface.childX[3] = 0;
        rsinterface.childY[3] = 36;
        rsinterface.children[4] = 17113;
        rsinterface.childX[4] = 0;
        rsinterface.childY[4] = 48;
        rsinterface.children[5] = 17114;
        rsinterface.childX[5] = 0;
        rsinterface.childY[5] = 60;
        rsinterface.children[6] = 17117;
        rsinterface.childX[6] = 0;
        rsinterface.childY[6] = 72;
        rsinterface.children[7] = 17118;
        rsinterface.childX[7] = 0;
        rsinterface.childY[7] = 84;
        rsinterface.children[8] = 17119;
        rsinterface.childX[8] = 0;
        rsinterface.childY[8] = 96;
        rsinterface.children[9] = 17120;
        rsinterface.childX[9] = 0;
        rsinterface.childY[9] = 108;
        rsinterface.children[10] = 17121;
        rsinterface.childX[10] = 0;
        rsinterface.childY[10] = 120;
        rsinterface.children[11] = 17122;
        rsinterface.childX[11] = 0;
        rsinterface.childY[11] = 132;
        rsinterface.children[12] = 17123;
        rsinterface.childX[12] = 0;
        rsinterface.childY[12] = 144;
        rsinterface.children[13] = 17124;
        rsinterface.childX[13] = 0;
        rsinterface.childY[13] = 156;
        rsinterface.children[14] = 17125;
        rsinterface.childX[14] = 0;
        rsinterface.childY[14] = 168;
        rsinterface.children[15] = 17126;
        rsinterface.childX[15] = 0;
        rsinterface.childY[15] = 180;
        rsinterface.children[16] = 17127;
        rsinterface.childX[16] = 0;
        rsinterface.childY[16] = 192;
        rsinterface.children[17] = 17128;
        rsinterface.childX[17] = 0;
        rsinterface.childY[17] = 204;
        rsinterface.children[18] = 17129;
        rsinterface.childX[18] = 0;
        rsinterface.childY[18] = 216;
        rsinterface.children[19] = 17130;
        rsinterface.childX[19] = 0;
        rsinterface.childY[19] = 228;
    }

    public static void itemsOnDeath(TextDrawingArea[] wid) {
        RSInterface rsinterface = addInterface(17100);
        addSprite(17101, 2, "ItemsKeptOnDeath/SPRITE");
        addHover(17102, 3, 0, 10601, 1, "ItemsKeptOnDeath/SPRITE", 17, 17, "Close Window");
        addHovered(10601, 3, "ItemsKeptOnDeath/SPRITE", 17, 17, 10602);
        addText(17103, "Items Kept on Death", wid, 2, 0xff981f);
        addText(17104, "2", wid, 1, 0xff981f);
        addText(17105, "3", wid, 1, 0xff981f);
        addText(17106, "4", wid, 1, 0xff981f);
        addText(17107, "5", wid, 1, 0xffcc33);
        addText(17108, "6", wid, 1, 0xffcc33);
        rsinterface.scrollMax = 0;
        rsinterface.interfaceShown = false;
        rsinterface.children = new int[12];
        rsinterface.childX = new int[12];
        rsinterface.childY = new int[12];

        rsinterface.children[0] = 17101;
        rsinterface.childX[0] = 7;
        rsinterface.childY[0] = 8;
        rsinterface.children[1] = 17102;
        rsinterface.childX[1] = 480;
        rsinterface.childY[1] = 17;
        rsinterface.children[2] = 17103;
        rsinterface.childX[2] = 185;
        rsinterface.childY[2] = 18;
        rsinterface.children[3] = 17104;
        rsinterface.childX[3] = 22;
        rsinterface.childY[3] = 50;
        rsinterface.children[4] = 17105;
        rsinterface.childX[4] = 22;
        rsinterface.childY[4] = 110;
        rsinterface.children[5] = 17106;
        rsinterface.childX[5] = 347;
        rsinterface.childY[5] = 47;
        rsinterface.children[6] = 17107;
        rsinterface.childX[6] = 349;
        rsinterface.childY[6] = 270;
        rsinterface.children[7] = 17108;
        rsinterface.childX[7] = 398;
        rsinterface.childY[7] = 298;
        rsinterface.children[8] = 17115;
        rsinterface.childX[8] = 348;
        rsinterface.childY[8] = 64;
        rsinterface.children[9] = 10494;
        rsinterface.childX[9] = 26;
        rsinterface.childY[9] = 74;
        rsinterface.children[10] = 10600;
        rsinterface.childX[10] = 26;
        rsinterface.childY[10] = 133;
        rsinterface.children[11] = 10601;
        rsinterface.childX[11] = 480;
        rsinterface.childY[11] = 17;
        rsinterface = interfaceCache[10494];
        rsinterface.invSpritePadX = 6;
        rsinterface.invSpritePadY = 5;
        rsinterface = interfaceCache[10600];
        rsinterface.invSpritePadX = 6;
        rsinterface.invSpritePadY = 5;
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
    



}