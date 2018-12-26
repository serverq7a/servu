public final class VarBit {

	public static void unpackConfig(Archive streamLoader) {
		Buffer stream = new Buffer(streamLoader.getDataForName("varbit.dat"));
		int cacheSize = stream.readUnsignedWord();

		if (cache == null)
			cache = new VarBit[cacheSize];

		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null)
				cache[j] = new VarBit();
			cache[j].readValues(stream);
		}

		if (stream.currentOffset != stream.buffer.length)
			System.err.println("Varbit size mismatch!");
	}

	private void readValues(Buffer stream) {
		anInt648 = stream.readUnsignedWord();
		anInt649 = stream.readUnsignedByte();
		anInt650 = stream.readUnsignedByte();
	}

	public static VarBit cache[];
	public int anInt648;
	public int anInt649;
	public int anInt650;
}
