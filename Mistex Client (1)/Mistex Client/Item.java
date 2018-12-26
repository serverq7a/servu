final class Item extends Animable {

	public final Model getRotatedModel() {
		ItemDefinition itemDefinition = ItemDefinition.forID(ID);
			return itemDefinition.getModel(anInt1559);
	}

	public Item() {
	}

	public int ID;
	public int x;
	public int y;
	public int anInt1559;
}
