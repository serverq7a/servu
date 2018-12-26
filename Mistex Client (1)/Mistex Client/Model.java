public class Model extends Animable {

	public static void clearCache() {
		modelHeaderCache = null;
		aBooleanArray1663 = null;
		aBooleanArray1664 = null;
		vertexPerspectiveY = null;
		vertexPerspectiveZ = null;
		vertexPerspectiveZAbs = null;
		anIntArray1668 = null;
		anIntArray1669 = null;
		anIntArray1670 = null;
		depthListIndices = null;
		faceLists = null;
		anIntArray1673 = null;
		anIntArrayArray1674 = null;
		anIntArray1675 = null;
		anIntArray1676 = null;
		anIntArray1677 = null;
		SINE = null;
		COSINE = null;
		HSL2RGB = null;
		modelIntArray4 = null;
	}
	
	public void applyAnimation(int currentId, int nextId, int cycle, int length) {
		if (vertexSkin == null) {
			return;
		}
		if (currentId == -1) {
			return;
		}
		final Class36 currAnim = Class36.method531(currentId);
		if (currAnim == null) {
			return;
		}
		final Class18 skinList = currAnim.aClass18_637;
		Class36 nextAnim = null;
    	if (nextId != -1) {
    		nextAnim = Class36.method531(nextId);
    		if (nextAnim.aClass18_637 != skinList) {
    			nextAnim = null;
    		}
    	}
    	vertexXModifier = 0;
    	vertexYModifier = 0;
    	vertexZModifier = 0;
		if (nextAnim == null) {
			for (int i = 0; i < currAnim.anInt638; i++) {
				int i_264_ = currAnim.anIntArray639[i];
				transformStep(skinList.anIntArray342[i_264_], skinList.anIntArrayArray343[i_264_], currAnim.anIntArray640[i], currAnim.anIntArray641[i], currAnim.anIntArray642[i]);
			}
		} else {
	    	int currFrameId = 0;
	    	int nextFrameId = 0;
		    for (int tlistId = 0; tlistId < skinList.length; tlistId++) {
  		    	boolean bool = false;
		    	if (currFrameId < currAnim.anInt638 && currAnim.anIntArray639[currFrameId] == tlistId) {
		    		bool = true;
		    	}
		    	boolean bool_76_ = false;
		    	if (nextFrameId < nextAnim.anInt638 && nextAnim.anIntArray639[nextFrameId] == tlistId) {
		    		bool_76_ = true;
		    	}
		    	if (bool || bool_76_) {
					int defaultModifier = 0;
					int opcode = skinList.anIntArray342[tlistId];
					if (opcode == 3) {
						defaultModifier = 128;
					}
					int currAnimX;
					int currAnimY;
					int currAnimZ;
					if (bool) {
						currAnimX = currAnim.anIntArray640[currFrameId];
						currAnimY = currAnim.anIntArray641[currFrameId];
						currAnimZ = currAnim.anIntArray642[currFrameId];
						currFrameId++;
					} else {
						currAnimX = defaultModifier;
						currAnimY = defaultModifier;
						currAnimZ = defaultModifier;
					}
					int nextAnimX;
					int nextAnimY;
					int nextAnimZ;
					if (bool_76_) {
						nextAnimX = nextAnim.anIntArray640[nextFrameId];
						nextAnimY = nextAnim.anIntArray641[nextFrameId];
						nextAnimZ = nextAnim.anIntArray642[nextFrameId];
						nextFrameId++;
					} else {
						nextAnimX = defaultModifier;
						nextAnimY = defaultModifier;
						nextAnimZ = defaultModifier;
					}
					int interpolatedX;
					int interpolatedY;
					int interpolatedZ;
					if (opcode == 2) {
						int deltaX = nextAnimX - currAnimX & 0xff;
						int deltaY = nextAnimY - currAnimY & 0xff;
						int deltaZ = nextAnimZ - currAnimZ & 0xff;
						if (deltaX >= 128) {
							deltaX -= 256;
						}
						if (deltaY >= 128) {
							deltaY -= 256;
						}
						if (deltaZ >= 128) {
							deltaZ -= 256;
						}
						interpolatedX = currAnimX + deltaX * cycle / length & 0xff;
						interpolatedY = currAnimY + deltaY * cycle / length & 0xff;
						interpolatedZ = currAnimZ + deltaZ * cycle / length & 0xff;
					} else {
						interpolatedX = currAnimX + (nextAnimX - currAnimX) * cycle / length;
						interpolatedY = currAnimY + (nextAnimY - currAnimY) * cycle / length;
						interpolatedZ = currAnimZ + (nextAnimZ - currAnimZ) * cycle / length;
					}
					transformStep(opcode, skinList.anIntArrayArray343[tlistId], interpolatedX, interpolatedY, interpolatedZ);
		    	}
		    }
	    }
	}

	public void read525Model(byte abyte0[], int modelID) {
		Buffer nc1 = new Buffer(abyte0);
		Buffer nc2 = new Buffer(abyte0);
		Buffer nc3 = new Buffer(abyte0);
		Buffer nc4 = new Buffer(abyte0);
		Buffer nc5 = new Buffer(abyte0);
		Buffer nc6 = new Buffer(abyte0);
		Buffer nc7 = new Buffer(abyte0);
		nc1.currentOffset = abyte0.length - 23;
		int numVertices = nc1.readUnsignedWord();
		int numTriangles = nc1.readUnsignedWord();
		int numTexTriangles = nc1.readUnsignedByte();
		ModelHeader ModelDef_1 = modelHeaderCache[modelID] = new ModelHeader();
		ModelDef_1.data = abyte0;
		ModelDef_1.vertexCount = numVertices;
		ModelDef_1.triangleCount = numTriangles;
		ModelDef_1.texturedTriangleCount = numTexTriangles;
		int l1 = nc1.readUnsignedByte();
		boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
		int i2 = nc1.readUnsignedByte();
		int j2 = nc1.readUnsignedByte();
		int k2 = nc1.readUnsignedByte();
		int l2 = nc1.readUnsignedByte();
		int i3 = nc1.readUnsignedByte();
		int j3 = nc1.readUnsignedWord();
		int k3 = nc1.readUnsignedWord();
		int l3 = nc1.readUnsignedWord();
		int i4 = nc1.readUnsignedWord();
		int j4 = nc1.readUnsignedWord();
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		byte[] x = null;
		byte[] O = null;
		byte[] J = null;
		byte[] F = null;
		byte[] cb = null;
		byte[] gb = null;
		byte[] lb = null;
		int[] kb = null;
		int[] y = null;
		int[] N = null;
		short[] D = null;
		int[] triangleColours2 = new int[numTriangles];
		if (numTexTriangles > 0) {
			O = new byte[numTexTriangles];
			nc1.currentOffset = 0;
			for (int j5 = 0; j5 < numTexTriangles; j5++) {
				byte byte0 = O[j5] = nc1.readSignedByte();
				if (byte0 == 0)
					k4++;
				if (byte0 >= 1 && byte0 <= 3)
					l4++;
				if (byte0 == 2)
					i5++;
			}
		}
		int k5 = numTexTriangles;
		int l5 = k5;
		k5 += numVertices;
		int i6 = k5;
		if (l1 == 1)
			k5 += numTriangles;
		int j6 = k5;
		k5 += numTriangles;
		int k6 = k5;
		if (i2 == 255)
			k5 += numTriangles;
		int l6 = k5;
		if (k2 == 1)
			k5 += numTriangles;
		int i7 = k5;
		if (i3 == 1)
			k5 += numVertices;
		int j7 = k5;
		if (j2 == 1)
			k5 += numTriangles;
		int k7 = k5;
		k5 += i4;
		int l7 = k5;
		if (l2 == 1)
			k5 += numTriangles * 2;
		int i8 = k5;
		k5 += j4;
		int j8 = k5;
		k5 += numTriangles * 2;
		int k8 = k5;
		k5 += j3;
		int l8 = k5;
		k5 += k3;
		int i9 = k5;
		k5 += l3;
		int j9 = k5;
		k5 += k4 * 6;
		int k9 = k5;
		k5 += l4 * 6;
		int l9 = k5;
		k5 += l4 * 6;
		int i10 = k5;
		k5 += l4;
		int j10 = k5;
		k5 += l4;
		int k10 = k5;
		k5 += l4 + i5 * 2;
		int[] vertexX = new int[numVertices];
		int[] vertexY = new int[numVertices];
		int[] vertexZ = new int[numVertices];
		int[] facePoint1 = new int[numTriangles];
		int[] facePoint2 = new int[numTriangles];
		int[] facePoint3 = new int[numTriangles];
		vertexVSkin = new int[numVertices];
		triangleDrawType = new int[numTriangles];
		facePriority = new int[numTriangles];
		triangleAlpha = new int[numTriangles];
		triangleTSkin = new int[numTriangles];
		if (i3 == 1)
			vertexVSkin = new int[numVertices];
		if (bool)
			triangleDrawType = new int[numTriangles];
		if (i2 == 255)
			facePriority = new int[numTriangles];
		else {
		}
		if (j2 == 1)
			triangleAlpha = new int[numTriangles];
		if (k2 == 1)
			triangleTSkin = new int[numTriangles];
		if (l2 == 1)
			D = new short[numTriangles];
		if (l2 == 1 && numTexTriangles > 0)
			x = new byte[numTriangles];
		triangleColours2 = new int[numTriangles];
		int[] texTrianglesPoint1 = null;
		int[] texTrianglesPoint2 = null;
		int[] texTrianglesPoint3 = null;
		if (numTexTriangles > 0) {
			texTrianglesPoint1 = new int[numTexTriangles];
			texTrianglesPoint2 = new int[numTexTriangles];
			texTrianglesPoint3 = new int[numTexTriangles];
			if (l4 > 0) {
				kb = new int[l4];
				N = new int[l4];
				y = new int[l4];
				gb = new byte[l4];
				lb = new byte[l4];
				F = new byte[l4];
			}
			if (i5 > 0) {
				cb = new byte[i5];
				J = new byte[i5];
			}
		}
		nc1.currentOffset = l5;
		nc2.currentOffset = k8;
		nc3.currentOffset = l8;
		nc4.currentOffset = i9;
		nc5.currentOffset = i7;
		int l10 = 0;
		int i11 = 0;
		int j11 = 0;
		for (int k11 = 0; k11 < numVertices; k11++) {
			int l11 = nc1.readUnsignedByte();
			int j12 = 0;
			if ((l11 & 1) != 0)
				j12 = nc2.method421();
			int l12 = 0;
			if ((l11 & 2) != 0)
				l12 = nc3.method421();
			int j13 = 0;
			if ((l11 & 4) != 0)
				j13 = nc4.method421();
			vertexX[k11] = l10 + j12;
			vertexY[k11] = i11 + l12;
			vertexZ[k11] = j11 + j13;
			l10 = vertexX[k11];
			i11 = vertexY[k11];
			j11 = vertexZ[k11];
			if (vertexVSkin != null)
				vertexVSkin[k11] = nc5.readUnsignedByte();
		}
		nc1.currentOffset = j8;
		nc2.currentOffset = i6;
		nc3.currentOffset = k6;
		nc4.currentOffset = j7;
		nc5.currentOffset = l6;
		nc6.currentOffset = l7;
		nc7.currentOffset = i8;
		for (int i12 = 0; i12 < numTriangles; i12++) {
			triangleColours2[i12] = nc1.readUnsignedWord();
			if (l1 == 1) {
				triangleDrawType[i12] = nc2.readSignedByte();
				if (triangleDrawType[i12] == 2)
					triangleColours2[i12] = 65535;
				triangleDrawType[i12] = 0;
			}
			if (i2 == 255) {
				facePriority[i12] = nc3.readSignedByte();
			}
			if (j2 == 1) {
				triangleAlpha[i12] = nc4.readSignedByte();
				if (triangleAlpha[i12] < 0)
					triangleAlpha[i12] = (256 + triangleAlpha[i12]);
			}
			if (k2 == 1)
				triangleTSkin[i12] = nc5.readUnsignedByte();
			if (l2 == 1)
				D[i12] = (short) (nc6.readUnsignedWord() - 1);
			if (x != null)
				if (D[i12] != -1)
					x[i12] = (byte) (nc7.readUnsignedByte() - 1);
				else
					x[i12] = -1;
		}
		nc1.currentOffset = k7;
		nc2.currentOffset = j6;
		int k12 = 0;
		int i13 = 0;
		int k13 = 0;
		int l13 = 0;
		for (int i14 = 0; i14 < numTriangles; i14++) {
			int j14 = nc2.readUnsignedByte();
			if (j14 == 1) {
				k12 = nc1.method421() + l13;
				l13 = k12;
				i13 = nc1.method421() + l13;
				l13 = i13;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 2) {
				i13 = k13;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 3) {
				k12 = k13;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 4) {
				int l14 = k12;
				k12 = i13;
				i13 = l14;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
		}
		nc1.currentOffset = j9;
		nc2.currentOffset = k9;
		nc3.currentOffset = l9;
		nc4.currentOffset = i10;
		nc5.currentOffset = j10;
		nc6.currentOffset = k10;
		for (int k14 = 0; k14 < numTexTriangles; k14++) {
			int i15 = O[k14] & 0xff;
			if (i15 == 0) {
				texTrianglesPoint1[k14] = nc1.readUnsignedWord();
				texTrianglesPoint2[k14] = nc1.readUnsignedWord();
				texTrianglesPoint3[k14] = nc1.readUnsignedWord();
			}
			if (i15 == 1) {
				texTrianglesPoint1[k14] = nc2.readUnsignedWord();
				texTrianglesPoint2[k14] = nc2.readUnsignedWord();
				texTrianglesPoint3[k14] = nc2.readUnsignedWord();
				kb[k14] = nc3.readUnsignedWord();
				N[k14] = nc3.readUnsignedWord();
				y[k14] = nc3.readUnsignedWord();
				gb[k14] = nc4.readSignedByte();
				lb[k14] = nc5.readSignedByte();
				F[k14] = nc6.readSignedByte();
			}
			if (i15 == 2) {
				texTrianglesPoint1[k14] = nc2.readUnsignedWord();
				texTrianglesPoint2[k14] = nc2.readUnsignedWord();
				texTrianglesPoint3[k14] = nc2.readUnsignedWord();
				kb[k14] = nc3.readUnsignedWord();
				N[k14] = nc3.readUnsignedWord();
				y[k14] = nc3.readUnsignedWord();
				gb[k14] = nc4.readSignedByte();
				lb[k14] = nc5.readSignedByte();
				F[k14] = nc6.readSignedByte();
				cb[k14] = nc6.readSignedByte();
				J[k14] = nc6.readSignedByte();
			}
			if (i15 == 3) {
				texTrianglesPoint1[k14] = nc2.readUnsignedWord();
				texTrianglesPoint2[k14] = nc2.readUnsignedWord();
				texTrianglesPoint3[k14] = nc2.readUnsignedWord();
				kb[k14] = nc3.readUnsignedWord();
				N[k14] = nc3.readUnsignedWord();
				y[k14] = nc3.readUnsignedWord();
				gb[k14] = nc4.readSignedByte();
				lb[k14] = nc5.readSignedByte();
				F[k14] = nc6.readSignedByte();
			}
		}
		if (i2 != 255) {
			for (int i12 = 0; i12 < numTriangles; i12++)
				facePriority[i12] = i2;
		}
		triangleTexture = triangleColours2;
		vertex_count = numVertices;
		triangle_count = numTriangles;
		vertex_x = vertexX;
		vertex_y = vertexY;
		vertex_z = vertexZ;
		triangle_a = facePoint1;
		triangle_b = facePoint2;
		triangle_c = facePoint3;
	}

	public Model(int modelId) {
		if (GameConfiguration.removeAllDoors) {
			if (modelId == 0)
				return;
		}
		byte[] is = modelHeaderCache[modelId].data;
		if (is[is.length - 1] == -1 && is[is.length - 2] == -1)
			read622Model(is, modelId);
		else
			readOldModel(modelId);
		if (newmodel[modelId]) {
			scale2(4);// 2 is too big -- 3 is almost right
		if(facePriority != null) {
			for(int j = 0; j < facePriority.length; j++)
                    	facePriority[j] = 10;
			}
		}
	}

	public void scale2(int i) {
		for (int i1 = 0; i1 < vertex_count; i1++) {
			vertex_x[i1] = vertex_x[i1] / i;
			vertex_y[i1] = vertex_y[i1] / i;
			vertex_z[i1] = vertex_z[i1] / i;
		}
	}

	public void read622Model(byte abyte0[], int modelID) {
		Buffer nc1 = new Buffer(abyte0);
		Buffer nc2 = new Buffer(abyte0);
		Buffer nc3 = new Buffer(abyte0);
		Buffer nc4 = new Buffer(abyte0);
		Buffer nc5 = new Buffer(abyte0);
		Buffer nc6 = new Buffer(abyte0);
		Buffer nc7 = new Buffer(abyte0);
		nc1.currentOffset = abyte0.length - 23;
		int numVertices = nc1.readUnsignedWord();
		int numTriangles = nc1.readUnsignedWord();
		int numTexTriangles = nc1.readUnsignedByte();
		ModelHeader ModelDef_1 = modelHeaderCache[modelID] = new ModelHeader();
		ModelDef_1.data = abyte0;
		ModelDef_1.vertexCount = numVertices;
		ModelDef_1.triangleCount = numTriangles;
		ModelDef_1.texturedTriangleCount = numTexTriangles;
		int l1 = nc1.readUnsignedByte();
		boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
		boolean bool_26_ = (0x8 & l1) == 8;
		if (!bool_26_) {
			read525Model(abyte0, modelID);
			return;
		}
		int newformat = 0;
		if (bool_26_) {
			nc1.currentOffset -= 7;
			newformat = nc1.readUnsignedByte();
			nc1.currentOffset += 6;
		}
		if (newformat == 15)
			newmodel[modelID] = true;
		int i2 = nc1.readUnsignedByte();
		int j2 = nc1.readUnsignedByte();
		int k2 = nc1.readUnsignedByte();
		int l2 = nc1.readUnsignedByte();
		int i3 = nc1.readUnsignedByte();
		int j3 = nc1.readUnsignedWord();
		int k3 = nc1.readUnsignedWord();
		int l3 = nc1.readUnsignedWord();
		int i4 = nc1.readUnsignedWord();
		int j4 = nc1.readUnsignedWord();
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		byte[] x = null;
		byte[] O = null;
		byte[] J = null;
		byte[] F = null;
		byte[] cb = null;
		byte[] gb = null;
		byte[] lb = null;
		int[] kb = null;
		int[] y = null;
		int[] N = null;
		short[] D = null;
		int[] triangleColours2 = new int[numTriangles];
		if (numTexTriangles > 0) {
			O = new byte[numTexTriangles];
			nc1.currentOffset = 0;
			for (int j5 = 0; j5 < numTexTriangles; j5++) {
				byte byte0 = O[j5] = nc1.readSignedByte();
				if (byte0 == 0)
					k4++;
				if (byte0 >= 1 && byte0 <= 3)
					l4++;
				if (byte0 == 2)
					i5++;
			}
		}
		int k5 = numTexTriangles;
		int l5 = k5;
		k5 += numVertices;
		int i6 = k5;
		if (bool)
			k5 += numTriangles;
		if (l1 == 1)
			k5 += numTriangles;
		int j6 = k5;
		k5 += numTriangles;
		int k6 = k5;
		if (i2 == 255)
			k5 += numTriangles;
		int l6 = k5;
		if (k2 == 1)
			k5 += numTriangles;
		int i7 = k5;
		if (i3 == 1)
			k5 += numVertices;
		int j7 = k5;
		if (j2 == 1)
			k5 += numTriangles;
		int k7 = k5;
		k5 += i4;
		int l7 = k5;
		if (l2 == 1)
			k5 += numTriangles * 2;
		int i8 = k5;
		k5 += j4;
		int j8 = k5;
		k5 += numTriangles * 2;
		int k8 = k5;
		k5 += j3;
		int l8 = k5;
		k5 += k3;
		int i9 = k5;
		k5 += l3;
		int j9 = k5;
		k5 += k4 * 6;
		int k9 = k5;
		k5 += l4 * 6;
		int i_59_ = 6;
		if (newformat != 14) {
			if (newformat >= 15)
				i_59_ = 9;
		} else
			i_59_ = 7;
		int l9 = k5;
		k5 += i_59_ * l4;
		int i10 = k5;
		k5 += l4;
		int j10 = k5;
		k5 += l4;
		int k10 = k5;
		k5 += l4 + i5 * 2;
		int[] vertexX = new int[numVertices];
		int[] vertexY = new int[numVertices];
		int[] vertexZ = new int[numVertices];
		int[] facePoint1 = new int[numTriangles];
		int[] facePoint2 = new int[numTriangles];
		int[] facePoint3 = new int[numTriangles];
		vertexVSkin = new int[numVertices];
		triangleDrawType = new int[numTriangles];
		facePriority = new int[numTriangles];
		triangleAlpha = new int[numTriangles];
		triangleTSkin = new int[numTriangles];
		if (i3 == 1)
			vertexVSkin = new int[numVertices];
		if (bool)
			triangleDrawType = new int[numTriangles];
		if (i2 == 255)
			facePriority = new int[numTriangles];
		else {
		}
		if (j2 == 1)
			triangleAlpha = new int[numTriangles];
		if (k2 == 1)
			triangleTSkin = new int[numTriangles];
		if (l2 == 1)
			D = new short[numTriangles];
		if (l2 == 1 && numTexTriangles > 0)
			x = new byte[numTriangles];
		triangleColours2 = new int[numTriangles];
		int[] texTrianglesPoint1 = null;
		int[] texTrianglesPoint2 = null;
		int[] texTrianglesPoint3 = null;
		if (numTexTriangles > 0) {
			texTrianglesPoint1 = new int[numTexTriangles];
			texTrianglesPoint2 = new int[numTexTriangles];
			texTrianglesPoint3 = new int[numTexTriangles];
			if (l4 > 0) {
				kb = new int[l4];
				N = new int[l4];
				y = new int[l4];
				gb = new byte[l4];
				lb = new byte[l4];
				F = new byte[l4];
			}
			if (i5 > 0) {
				cb = new byte[i5];
				J = new byte[i5];
			}
		}
		nc1.currentOffset = l5;
		nc2.currentOffset = k8;
		nc3.currentOffset = l8;
		nc4.currentOffset = i9;
		nc5.currentOffset = i7;
		int l10 = 0;
		int i11 = 0;
		int j11 = 0;
		for (int k11 = 0; k11 < numVertices; k11++) {
			int l11 = nc1.readUnsignedByte();
			int j12 = 0;
			if ((l11 & 1) != 0)
				j12 = nc2.method421();
			int l12 = 0;
			if ((l11 & 2) != 0)
				l12 = nc3.method421();
			int j13 = 0;
			if ((l11 & 4) != 0)
				j13 = nc4.method421();
			vertexX[k11] = l10 + j12;
			vertexY[k11] = i11 + l12;
			vertexZ[k11] = j11 + j13;
			l10 = vertexX[k11];
			i11 = vertexY[k11];
			j11 = vertexZ[k11];
			if (vertexVSkin != null)
				vertexVSkin[k11] = nc5.readUnsignedByte();
		}
		nc1.currentOffset = j8;
		nc2.currentOffset = i6;
		nc3.currentOffset = k6;
		nc4.currentOffset = j7;
		nc5.currentOffset = l6;
		nc6.currentOffset = l7;
		nc7.currentOffset = i8;
		for (int i12 = 0; i12 < numTriangles; i12++) {
			triangleColours2[i12] = nc1.readUnsignedWord();
			if (l1 == 1) {
				triangleDrawType[i12] = nc2.readSignedByte();
				if (triangleDrawType[i12] == 2)
					triangleColours2[i12] = 65535;
				triangleDrawType[i12] = 0;
			}
			if (i2 == 255) {
				facePriority[i12] = nc3.readSignedByte();
			}
			if (j2 == 1) {
				triangleAlpha[i12] = nc4.readSignedByte();
				if (triangleAlpha[i12] < 0)
					triangleAlpha[i12] = (256 + triangleAlpha[i12]);
			}
			if (k2 == 1)
				triangleTSkin[i12] = nc5.readUnsignedByte();
			if (l2 == 1)
				D[i12] = (short) (nc6.readUnsignedWord() - 1);
			if (x != null)
				if (D[i12] != -1)
					x[i12] = (byte) (nc7.readUnsignedByte() - 1);
				else
					x[i12] = -1;
		}
		nc1.currentOffset = k7;
		nc2.currentOffset = j6;
		int k12 = 0;
		int i13 = 0;
		int k13 = 0;
		int l13 = 0;
		for (int i14 = 0; i14 < numTriangles; i14++) {
			int j14 = nc2.readUnsignedByte();
			if (j14 == 1) {
				k12 = nc1.method421() + l13;
				l13 = k12;
				i13 = nc1.method421() + l13;
				l13 = i13;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 2) {
				i13 = k13;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 3) {
				k12 = k13;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 4) {
				int l14 = k12;
				k12 = i13;
				i13 = l14;
				k13 = nc1.method421() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
		}
		nc1.currentOffset = j9;
		nc2.currentOffset = k9;
		nc3.currentOffset = l9;
		nc4.currentOffset = i10;
		nc5.currentOffset = j10;
		nc6.currentOffset = k10;
		for (int k14 = 0; k14 < numTexTriangles; k14++) {
			int i15 = O[k14] & 0xff;
			if (i15 == 0) {
				texTrianglesPoint1[k14] = nc1.readUnsignedWord();
				texTrianglesPoint2[k14] = nc1.readUnsignedWord();
				texTrianglesPoint3[k14] = nc1.readUnsignedWord();
			}
			if (i15 == 1) {
				texTrianglesPoint1[k14] = nc2.readUnsignedWord();
				texTrianglesPoint2[k14] = nc2.readUnsignedWord();
				texTrianglesPoint3[k14] = nc2.readUnsignedWord();
				if (newformat < 15) {
					kb[k14] = nc3.readUnsignedWord();
					if (newformat >= 14)
						N[k14] = nc3.v(-1);
					else
						N[k14] = nc3.readUnsignedWord();
					y[k14] = nc3.readUnsignedWord();
				} else {
					kb[k14] = nc3.v(-1);
					N[k14] = nc3.v(-1);
					y[k14] = nc3.v(-1);
				}
				gb[k14] = nc4.readSignedByte();
				lb[k14] = nc5.readSignedByte();
				F[k14] = nc6.readSignedByte();
			}
			if (i15 == 2) {
				texTrianglesPoint1[k14] = nc2.readUnsignedWord();
				texTrianglesPoint2[k14] = nc2.readUnsignedWord();
				texTrianglesPoint3[k14] = nc2.readUnsignedWord();
				if (newformat >= 15) {
					kb[k14] = nc3.v(-1);
					N[k14] = nc3.v(-1);
					y[k14] = nc3.v(-1);
				} else {
					kb[k14] = nc3.readUnsignedWord();
					if (newformat < 14)
						N[k14] = nc3.readUnsignedWord();
					else
						N[k14] = nc3.v(-1);
					y[k14] = nc3.readUnsignedWord();
				}
				gb[k14] = nc4.readSignedByte();
				lb[k14] = nc5.readSignedByte();
				F[k14] = nc6.readSignedByte();
				cb[k14] = nc6.readSignedByte();
				J[k14] = nc6.readSignedByte();
			}
			if (i15 == 3) {
				texTrianglesPoint1[k14] = nc2.readUnsignedWord();
				texTrianglesPoint2[k14] = nc2.readUnsignedWord();
				texTrianglesPoint3[k14] = nc2.readUnsignedWord();
				if (newformat < 15) {
					kb[k14] = nc3.readUnsignedWord();
					if (newformat < 14)
						N[k14] = nc3.readUnsignedWord();
					else
						N[k14] = nc3.v(-1);
					y[k14] = nc3.readUnsignedWord();
				} else {
					kb[k14] = nc3.v(-1);
					N[k14] = nc3.v(-1);
					y[k14] = nc3.v(-1);
				}
				gb[k14] = nc4.readSignedByte();
				lb[k14] = nc5.readSignedByte();
				F[k14] = nc6.readSignedByte();
			}
		}
		if (i2 != 255) {
			for (int i12 = 0; i12 < numTriangles; i12++)
				facePriority[i12] = i2;
		}
		triangleTexture = triangleColours2;
		vertex_count = numVertices;
		triangle_count = numTriangles;
		vertex_x = vertexX;
		vertex_y = vertexY;
		vertex_z = vertexZ;
		triangle_a = facePoint1;
		triangle_b = facePoint2;
		triangle_c = facePoint3;
	}

	private void readOldModel(int i) {
		int j = -870;
		aBoolean1618 = true;
		singleSquare = false;
		anInt1620++;
		ModelHeader modelHeader = modelHeaderCache[i];
		vertex_count = modelHeader.vertexCount;
		triangle_count = modelHeader.triangleCount;
		triangleTextureCount = modelHeader.texturedTriangleCount;
		vertex_x = new int[vertex_count];
		vertex_y = new int[vertex_count];
		vertex_z = new int[vertex_count];
		triangle_a = new int[triangle_count];
		triangle_b = new int[triangle_count];
		while (j >= 0)
			aBoolean1618 = !aBoolean1618;
		triangle_c = new int[triangle_count];
		trianglePIndex = new int[triangleTextureCount];
		triangleMIndex = new int[triangleTextureCount];
		triangleNIndex = new int[triangleTextureCount];
		if (modelHeader.anInt376 >= 0)
			vertexVSkin = new int[vertex_count];
		if (modelHeader.anInt380 >= 0)
			triangleDrawType = new int[triangle_count];
		if (modelHeader.anInt381 >= 0)
			facePriority = new int[triangle_count];
		else
			anInt1641 = -modelHeader.anInt381 - 1;
		if (modelHeader.anInt382 >= 0)
			triangleAlpha = new int[triangle_count];
		if (modelHeader.anInt383 >= 0)
			triangleTSkin = new int[triangle_count];
		triangleTexture = new int[triangle_count];
		Buffer buffer = new Buffer(modelHeader.data);
		buffer.currentOffset = modelHeader.anInt372;
		Buffer stream_1 = new Buffer(modelHeader.data);
		stream_1.currentOffset = modelHeader.anInt373;
		Buffer stream_2 = new Buffer(modelHeader.data);
		stream_2.currentOffset = modelHeader.anInt374;
		Buffer stream_3 = new Buffer(modelHeader.data);
		stream_3.currentOffset = modelHeader.anInt375;
		Buffer stream_4 = new Buffer(modelHeader.data);
		stream_4.currentOffset = modelHeader.anInt376;
		int k = 0;
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < vertex_count; j1++) {
			int k1 = buffer.readUnsignedByte();
			int i2 = 0;
			if ((k1 & 1) != 0)
				i2 = stream_1.method421();
			int k2 = 0;
			if ((k1 & 2) != 0)
				k2 = stream_2.method421();
			int i3 = 0;
			if ((k1 & 4) != 0)
				i3 = stream_3.method421();
			vertex_x[j1] = k + i2;
			vertex_y[j1] = l + k2;
			vertex_z[j1] = i1 + i3;
			k = vertex_x[j1];
			l = vertex_y[j1];
			i1 = vertex_z[j1];
			if (vertexVSkin != null)
				vertexVSkin[j1] = stream_4.readUnsignedByte();
		}
		buffer.currentOffset = modelHeader.anInt379;
		stream_1.currentOffset = modelHeader.anInt380;
		stream_2.currentOffset = modelHeader.anInt381;
		stream_3.currentOffset = modelHeader.anInt382;
		stream_4.currentOffset = modelHeader.anInt383;
		for (int l1 = 0; l1 < triangle_count; l1++) {
			triangleTexture[l1] = buffer.readUnsignedWord();
			if (triangleDrawType != null)
				triangleDrawType[l1] = stream_1.readUnsignedByte();
			if (facePriority != null)
				facePriority[l1] = stream_2.readUnsignedByte();
			if (triangleAlpha != null) {
				triangleAlpha[l1] = stream_3.readUnsignedByte();
			}
			if (triangleTSkin != null)
				triangleTSkin[l1] = stream_4.readUnsignedByte();
		}
		buffer.currentOffset = modelHeader.anInt377;
		stream_1.currentOffset = modelHeader.anInt378;
		int j2 = 0;
		int l2 = 0;
		int j3 = 0;
		int k3 = 0;
		for (int l3 = 0; l3 < triangle_count; l3++) {
			int i4 = stream_1.readUnsignedByte();
			if (i4 == 1) {
				j2 = buffer.method421() + k3;
				k3 = j2;
				l2 = buffer.method421() + k3;
				k3 = l2;
				j3 = buffer.method421() + k3;
				k3 = j3;
				triangle_a[l3] = j2;
				triangle_b[l3] = l2;
				triangle_c[l3] = j3;
			}
			if (i4 == 2) {
				l2 = j3;
				j3 = buffer.method421() + k3;
				k3 = j3;
				triangle_a[l3] = j2;
				triangle_b[l3] = l2;
				triangle_c[l3] = j3;
			}
			if (i4 == 3) {
				j2 = j3;
				j3 = buffer.method421() + k3;
				k3 = j3;
				triangle_a[l3] = j2;
				triangle_b[l3] = l2;
				triangle_c[l3] = j3;
			}
			if (i4 == 4) {
				int k4 = j2;
				j2 = l2;
				l2 = k4;
				j3 = buffer.method421() + k3;
				k3 = j3;
				triangle_a[l3] = j2;
				triangle_b[l3] = l2;
				triangle_c[l3] = j3;
			}
		}
		buffer.currentOffset = modelHeader.anInt384;
		for (int j4 = 0; j4 < triangleTextureCount; j4++) {
			trianglePIndex[j4] = buffer.readUnsignedWord();
			triangleMIndex[j4] = buffer.readUnsignedWord();
			triangleNIndex[j4] = buffer.readUnsignedWord();
		}
	}

	public static void load(byte[] modelBuffer, int j) {
	try {
		if (modelBuffer == null) {
			ModelHeader class21 = modelHeaderCache[j] = new ModelHeader();
			class21.vertexCount = 0;
			class21.triangleCount = 0;
			class21.texturedTriangleCount = 0;
			return;
		}
		Buffer buffer = new Buffer(modelBuffer);
		buffer.currentOffset = modelBuffer.length - 18;
		ModelHeader modelHeader = modelHeaderCache[j] = new ModelHeader();
		modelHeader.data = modelBuffer;
		modelHeader.vertexCount = buffer.readUnsignedWord();
		modelHeader.triangleCount = buffer.readUnsignedWord();
		modelHeader.texturedTriangleCount = buffer.readUnsignedByte();
		int k = buffer.readUnsignedByte();
		int l = buffer.readUnsignedByte();
		int i1 = buffer.readUnsignedByte();
		int j1 = buffer.readUnsignedByte();
		int k1 = buffer.readUnsignedByte();
		int l1 = buffer.readUnsignedWord();
		int i2 = buffer.readUnsignedWord();
		int j2 = buffer.readUnsignedWord();
		int k2 = buffer.readUnsignedWord();
		int l2 = 0;
		modelHeader.anInt372 = l2;
		l2 += modelHeader.vertexCount;
		modelHeader.anInt378 = l2;
		l2 += modelHeader.triangleCount;
		modelHeader.anInt381 = l2;
		if (l == 255)
			l2 += modelHeader.triangleCount;
		else
			modelHeader.anInt381 = -l - 1;
		modelHeader.anInt383 = l2;
		if (j1 == 1)
			l2 += modelHeader.triangleCount;
		else
			modelHeader.anInt383 = -1;
		modelHeader.anInt380 = l2;
		if (k == 1)
			l2 += modelHeader.triangleCount;
		else
			modelHeader.anInt380 = -1;
		modelHeader.anInt376 = l2;
		if (k1 == 1)
			l2 += modelHeader.vertexCount;
		else
			modelHeader.anInt376 = -1;
		modelHeader.anInt382 = l2;
		if (i1 == 1)
			l2 += modelHeader.triangleCount;
		else
			modelHeader.anInt382 = -1;
		modelHeader.anInt377 = l2;
		l2 += k2;
		modelHeader.anInt379 = l2;
		l2 += modelHeader.triangleCount * 2;
		modelHeader.anInt384 = l2;
		l2 += modelHeader.texturedTriangleCount * 6;
		modelHeader.anInt373 = l2;
		l2 += l1;
		modelHeader.anInt374 = l2;
		l2 += i2;
		modelHeader.anInt375 = l2;
		l2 += j2;
		} catch (Exception _ex) {
		}
	}

	public static boolean newmodel[];

	public static void method459(int i,
			OnDemandFetcherParent onDemandFetcherParent) {
		modelHeaderCache = new ModelHeader[80000];
		newmodel = new boolean[100000];
		aOnDemandFetcherParent_1662 = onDemandFetcherParent;
	}

	public static void method461(int j) {
		modelHeaderCache[j] = null;
	}

	public static Model method462(int j) {
		if (modelHeaderCache == null)
			return null;
		ModelHeader class21 = modelHeaderCache[j];
		if (class21 == null) {
			aOnDemandFetcherParent_1662.method548(j);
			return null;
		} else {
			return new Model(j);
		}
	}

	public static boolean method463(int i) {
		if (modelHeaderCache == null)
			return false;

		ModelHeader class21 = modelHeaderCache[i];
		if (class21 == null) {
			aOnDemandFetcherParent_1662.method548(i);
			return false;
		} else {
			return true;
		}
	}

	private Model(boolean flag) {
		aBoolean1618 = true;
		singleSquare = false;
		if (!flag)
			aBoolean1618 = !aBoolean1618;
	}

	public Model(int i, Model amodel[]) {
		aBoolean1618 = true;
		singleSquare = false;
		anInt1620++;
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		vertex_count = 0;
		triangle_count = 0;
		triangleTextureCount = 0;
		anInt1641 = -1;
		for (int k = 0; k < i; k++) {
			Model model = amodel[k];
			if (model != null) {
				vertex_count += model.vertex_count;
				triangle_count += model.triangle_count;
				triangleTextureCount += model.triangleTextureCount;
				flag |= model.triangleDrawType != null;
				if (model.facePriority != null) {
					flag1 = true;
				} else {
					if (anInt1641 == -1)
						anInt1641 = model.anInt1641;
					if (anInt1641 != model.anInt1641)
						flag1 = true;
				}
				flag2 |= model.triangleAlpha != null;
				flag3 |= model.triangleTSkin != null;
			}
		}

		vertex_x = new int[vertex_count];
		vertex_y = new int[vertex_count];
		vertex_z = new int[vertex_count];
		vertexVSkin = new int[vertex_count];
		triangle_a = new int[triangle_count];
		triangle_b = new int[triangle_count];
		triangle_c = new int[triangle_count];
		trianglePIndex = new int[triangleTextureCount];
		triangleMIndex = new int[triangleTextureCount];
		triangleNIndex = new int[triangleTextureCount];
		if (flag)
			triangleDrawType = new int[triangle_count];
		if (flag1)
			facePriority = new int[triangle_count];
		if (flag2)
			triangleAlpha = new int[triangle_count];
		if (flag3)
			triangleTSkin = new int[triangle_count];
		triangleTexture = new int[triangle_count];
		vertex_count = 0;
		triangle_count = 0;
		triangleTextureCount = 0;
		int l = 0;
		for (int i1 = 0; i1 < i; i1++) {
			Model model_1 = amodel[i1];
			if (model_1 != null) {
				for (int j1 = 0; j1 < model_1.triangle_count; j1++) {
					if (flag)
						if (model_1.triangleDrawType == null) {
							triangleDrawType[triangle_count] = 0;
						} else {
							int k1 = model_1.triangleDrawType[j1];
							if ((k1 & 2) == 2)
								k1 += l << 2;
							triangleDrawType[triangle_count] = k1;
						}
					if (flag1)
						if (model_1.facePriority == null)
							facePriority[triangle_count] = model_1.anInt1641;
						else
							facePriority[triangle_count] = model_1.facePriority[j1];
					if (flag2)
						if (model_1.triangleAlpha == null)
							triangleAlpha[triangle_count] = 0;
						else
							triangleAlpha[triangle_count] = model_1.triangleAlpha[j1];

					if (flag3 && model_1.triangleTSkin != null)
						triangleTSkin[triangle_count] = model_1.triangleTSkin[j1];
					triangleTexture[triangle_count] = model_1.triangleTexture[j1];
					triangle_a[triangle_count] = method465(model_1,
							model_1.triangle_a[j1]);
					triangle_b[triangle_count] = method465(model_1,
							model_1.triangle_b[j1]);
					triangle_c[triangle_count] = method465(model_1,
							model_1.triangle_c[j1]);
					triangle_count++;
				}

				for (int l1 = 0; l1 < model_1.triangleTextureCount; l1++) {
					trianglePIndex[triangleTextureCount] = method465(model_1,
							model_1.trianglePIndex[l1]);
					triangleMIndex[triangleTextureCount] = method465(model_1,
							model_1.triangleMIndex[l1]);
					triangleNIndex[triangleTextureCount] = method465(model_1,
							model_1.triangleNIndex[l1]);
					triangleTextureCount++;
				}

				l += model_1.triangleTextureCount;
			}
		}

	}

	public Model(Model amodel[]) {
		int i = 2;
		aBoolean1618 = true;
		singleSquare = false;
		anInt1620++;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		vertex_count = 0;
		triangle_count = 0;
		triangleTextureCount = 0;
		anInt1641 = -1;
		for (int k = 0; k < i; k++) {
			Model model = amodel[k];
			if (model != null) {
				vertex_count += model.vertex_count;
				triangle_count += model.triangle_count;
				triangleTextureCount += model.triangleTextureCount;
				flag1 |= model.triangleDrawType != null;
				if (model.facePriority != null) {
					flag2 = true;
				} else {
					if (anInt1641 == -1)
						anInt1641 = model.anInt1641;
					if (anInt1641 != model.anInt1641)
						flag2 = true;
				}
				flag3 |= model.triangleAlpha != null;
				flag4 |= model.triangleTexture != null;
			}
		}

		vertex_x = new int[vertex_count];
		vertex_y = new int[vertex_count];
		vertex_z = new int[vertex_count];
		triangle_a = new int[triangle_count];
		triangle_b = new int[triangle_count];
		triangle_c = new int[triangle_count];
		triangle_hsl_a = new int[triangle_count];
		triangle_hsl_b = new int[triangle_count];
		triangle_hsl_c = new int[triangle_count];
		trianglePIndex = new int[triangleTextureCount];
		triangleMIndex = new int[triangleTextureCount];
		triangleNIndex = new int[triangleTextureCount];
		if (flag1)
			triangleDrawType = new int[triangle_count];
		if (flag2)
			facePriority = new int[triangle_count];
		if (flag3)
			triangleAlpha = new int[triangle_count];
		if (flag4)
			triangleTexture = new int[triangle_count];
		vertex_count = 0;
		triangle_count = 0;
		triangleTextureCount = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < i; j1++) {
			Model model_1 = amodel[j1];
			if (model_1 != null) {
				int k1 = vertex_count;
				for (int l1 = 0; l1 < model_1.vertex_count; l1++) {
					vertex_x[vertex_count] = model_1.vertex_x[l1];
					vertex_y[vertex_count] = model_1.vertex_y[l1];
					vertex_z[vertex_count] = model_1.vertex_z[l1];
					vertex_count++;
				}

				for (int i2 = 0; i2 < model_1.triangle_count; i2++) {
					triangle_a[triangle_count] = model_1.triangle_a[i2] + k1;
					triangle_b[triangle_count] = model_1.triangle_b[i2] + k1;
					triangle_c[triangle_count] = model_1.triangle_c[i2] + k1;
					triangle_hsl_a[triangle_count] = model_1.triangle_hsl_a[i2];
					triangle_hsl_b[triangle_count] = model_1.triangle_hsl_b[i2];
					triangle_hsl_c[triangle_count] = model_1.triangle_hsl_c[i2];
					if (flag1)
						if (model_1.triangleDrawType == null) {
							triangleDrawType[triangle_count] = 0;
						} else {
							int j2 = model_1.triangleDrawType[i2];
							if ((j2 & 2) == 2)
								j2 += i1 << 2;
							triangleDrawType[triangle_count] = j2;
						}
					if (flag2)
						if (model_1.facePriority == null)
							facePriority[triangle_count] = model_1.anInt1641;
						else
							facePriority[triangle_count] = model_1.facePriority[i2];
					if (flag3)
						if (model_1.triangleAlpha == null)
							triangleAlpha[triangle_count] = 0;
						else
							triangleAlpha[triangle_count] = model_1.triangleAlpha[i2];
					if (flag4 && model_1.triangleTexture != null)
						triangleTexture[triangle_count] = model_1.triangleTexture[i2];

					triangle_count++;
				}

				for (int k2 = 0; k2 < model_1.triangleTextureCount; k2++) {
					trianglePIndex[triangleTextureCount] = model_1.trianglePIndex[k2] + k1;
					triangleMIndex[triangleTextureCount] = model_1.triangleMIndex[k2] + k1;
					triangleNIndex[triangleTextureCount] = model_1.triangleNIndex[k2] + k1;
					triangleTextureCount++;
				}

				i1 += model_1.triangleTextureCount;
			}
		}

		method466();
	}

	public Model(boolean flag, boolean flag1, boolean flag2, Model model) {
		aBoolean1618 = true;
		singleSquare = false;
		anInt1620++;
		vertex_count = model.vertex_count;
		triangle_count = model.triangle_count;
		triangleTextureCount = model.triangleTextureCount;
		if (flag2) {
			vertex_x = model.vertex_x;
			vertex_y = model.vertex_y;
			vertex_z = model.vertex_z;
		} else {
			vertex_x = new int[vertex_count];
			vertex_y = new int[vertex_count];
			vertex_z = new int[vertex_count];
			for (int j = 0; j < vertex_count; j++) {
				vertex_x[j] = model.vertex_x[j];
				vertex_y[j] = model.vertex_y[j];
				vertex_z[j] = model.vertex_z[j];
			}

		}
		if (flag) {
			triangleTexture = model.triangleTexture;
		} else {
			triangleTexture = new int[triangle_count];
			for (int k = 0; k < triangle_count; k++)
				triangleTexture[k] = model.triangleTexture[k];

		}
		if (flag1) {
			triangleAlpha = model.triangleAlpha;
		} else {
			triangleAlpha = new int[triangle_count];
			if (model.triangleAlpha == null) {
				for (int l = 0; l < triangle_count; l++)
					triangleAlpha[l] = 0;

			} else {
				for (int i1 = 0; i1 < triangle_count; i1++)
					triangleAlpha[i1] = model.triangleAlpha[i1];

			}
		}
		vertexVSkin = model.vertexVSkin;
		triangleTSkin = model.triangleTSkin;
		triangleDrawType = model.triangleDrawType;
		triangle_a = model.triangle_a;
		triangle_b = model.triangle_b;
		triangle_c = model.triangle_c;
		facePriority = model.facePriority;
		anInt1641 = model.anInt1641;
		trianglePIndex = model.trianglePIndex;
		triangleMIndex = model.triangleMIndex;
		triangleNIndex = model.triangleNIndex;
	}

	public Model(boolean isSolid, boolean nonFlatShading, Model model) {
		aBoolean1618 = true;
		singleSquare = false;
		anInt1620++;
		vertex_count = model.vertex_count;
		triangle_count = model.triangle_count;
		triangleTextureCount = model.triangleTextureCount;
		if (isSolid) {
			vertex_y = new int[vertex_count];
			for (int j = 0; j < vertex_count; j++)
				vertex_y[j] = model.vertex_y[j];

		} else {
			vertex_y = model.vertex_y;
		}
		if (nonFlatShading) {
			triangle_hsl_a = new int[triangle_count];
			triangle_hsl_b = new int[triangle_count];
			triangle_hsl_c = new int[triangle_count];
			for (int k = 0; k < triangle_count; k++) {
				triangle_hsl_a[k] = model.triangle_hsl_a[k];
				triangle_hsl_b[k] = model.triangle_hsl_b[k];
				triangle_hsl_c[k] = model.triangle_hsl_c[k];
			}

			triangleDrawType = new int[triangle_count];
			if (model.triangleDrawType == null) {
				for (int l = 0; l < triangle_count; l++)
					triangleDrawType[l] = 0;

			} else {
				for (int i1 = 0; i1 < triangle_count; i1++)
					triangleDrawType[i1] = model.triangleDrawType[i1];

			}
			super.aClass33Array1425 = new LightVertex[vertex_count];
			for (int j1 = 0; j1 < vertex_count; j1++) {
				LightVertex class33 = super.aClass33Array1425[j1] = new LightVertex();
				LightVertex class33_1 = model.aClass33Array1425[j1];
				class33.anInt602 = class33_1.anInt602;
				class33.anInt603 = class33_1.anInt603;
				class33.anInt604 = class33_1.anInt604;
				class33.anInt605 = class33_1.anInt605;
			}

			vertexNormalOffset = model.vertexNormalOffset;
		} else {
			triangle_hsl_a = model.triangle_hsl_a;
			triangle_hsl_b = model.triangle_hsl_b;
			triangle_hsl_c = model.triangle_hsl_c;
			triangleDrawType = model.triangleDrawType;
		}
		vertex_x = model.vertex_x;
		vertex_z = model.vertex_z;
		triangleTexture = model.triangleTexture;
		triangleAlpha = model.triangleAlpha;
		facePriority = model.facePriority;
		anInt1641 = model.anInt1641;
		triangle_a = model.triangle_a;
		triangle_b = model.triangle_b;
		triangle_c = model.triangle_c;
		trianglePIndex = model.trianglePIndex;
		triangleMIndex = model.triangleMIndex;
		triangleNIndex = model.triangleNIndex;
		super.modelHeight = model.modelHeight;

		anInt1650 = model.anInt1650;
		anInt1653 = model.anInt1653;
		diagonal3D = model.diagonal3D;
		anInt1646 = model.anInt1646;
		anInt1648 = model.anInt1648;
		anInt1649 = model.anInt1649;
		anInt1647 = model.anInt1647;
	}

	public void method464(Model model, boolean flag) {
		vertex_count = model.vertex_count;
		triangle_count = model.triangle_count;
		triangleTextureCount = model.triangleTextureCount;
		if (anIntArray1622.length < vertex_count) {
			anIntArray1622 = new int[vertex_count + 10000];
			anIntArray1623 = new int[vertex_count + 10000];
			anIntArray1624 = new int[vertex_count + 10000];
		}
		vertex_x = anIntArray1622;
		vertex_y = anIntArray1623;
		vertex_z = anIntArray1624;
		for (int k = 0; k < vertex_count; k++) {
			vertex_x[k] = model.vertex_x[k];
			vertex_y[k] = model.vertex_y[k];
			vertex_z[k] = model.vertex_z[k];
		}

		if (flag) {
			triangleAlpha = model.triangleAlpha;
		} else {
			if (anIntArray1625.length < triangle_count)
				anIntArray1625 = new int[triangle_count + 100];
			triangleAlpha = anIntArray1625;
			if (model.triangleAlpha == null) {
				for (int l = 0; l < triangle_count; l++)
					triangleAlpha[l] = 0;

			} else {
				for (int i1 = 0; i1 < triangle_count; i1++)
					triangleAlpha[i1] = model.triangleAlpha[i1];

			}
		}
		triangleDrawType = model.triangleDrawType;
		triangleTexture = model.triangleTexture;
		facePriority = model.facePriority;
		anInt1641 = model.anInt1641;
		triangleSkin = model.triangleSkin;
		vertexSkin = model.vertexSkin;
		triangle_a = model.triangle_a;
		triangle_b = model.triangle_b;
		triangle_c = model.triangle_c;
		triangle_hsl_a = model.triangle_hsl_a;
		triangle_hsl_b = model.triangle_hsl_b;
		triangle_hsl_c = model.triangle_hsl_c;
		trianglePIndex = model.trianglePIndex;
		triangleMIndex = model.triangleMIndex;
		triangleNIndex = model.triangleNIndex;
	}

	private final int method465(Model model, int i) {
		int j = -1;
		int x = model.vertex_x[i];
		int y = model.vertex_y[i];
		int z = model.vertex_z[i];
		for (int j1 = 0; j1 < vertex_count; j1++) {
			if (x != vertex_x[j1] || y != vertex_y[j1]
			                                                   || z != vertex_z[j1])
				continue;
			j = j1;
			break;
		}

		if (j == -1) {
			vertex_x[vertex_count] = x;
			vertex_y[vertex_count] = y;
			vertex_z[vertex_count] = z;
			if (model.vertexVSkin != null)
				vertexVSkin[vertex_count] = model.vertexVSkin[i];
			j = vertex_count++;
		}
		return j;
	}

	public void method466() {
		super.modelHeight = 0;
		anInt1650 = 0;
		anInt1651 = 0;
		for (int verticePointer = 0; verticePointer < vertex_count; verticePointer++) {
			int vertice_x = vertex_x[verticePointer];
			int vertice_y = vertex_y[verticePointer];
			int vertice_z = vertex_z[verticePointer];
			if (-vertice_y > super.modelHeight)
				super.modelHeight = -vertice_y;
			if (vertice_y > anInt1651)
				anInt1651 = vertice_y;
			int diagonalBounds = vertice_x * vertice_x + vertice_z * vertice_z;
			if (diagonalBounds > anInt1650)
				anInt1650 = diagonalBounds;
		}
		anInt1650 = (int) (Math.sqrt(anInt1650) + 0.98999999999999999D);
		anInt1653 = (int) (Math.sqrt(anInt1650 * anInt1650 + super.modelHeight
				* super.modelHeight) + 0.98999999999999999D);
		diagonal3D = anInt1653
		+ (int) (Math.sqrt(anInt1650 * anInt1650 + anInt1651
				* anInt1651) + 0.98999999999999999D);
	}

	public void method467() {
		super.modelHeight = 0;
		anInt1651 = 0;
		for (int i = 0; i < vertex_count; i++) {
			int j = vertex_y[i];
			if (-j > super.modelHeight)
				super.modelHeight = -j;
			if (j > anInt1651)
				anInt1651 = j;
		}

		anInt1653 = (int) (Math.sqrt(anInt1650 * anInt1650 + super.modelHeight
				* super.modelHeight) + 0.98999999999999999D);
		diagonal3D = anInt1653
		+ (int) (Math.sqrt(anInt1650 * anInt1650 + anInt1651
				* anInt1651) + 0.98999999999999999D);
	}

	public void calculateDiagonalStats(int i) {
		super.modelHeight = 0;
		anInt1650 = 0;
		anInt1651 = 0;
		anInt1646 = 0xf423f;
		anInt1647 = 0xfff0bdc1;
		anInt1648 = 0xfffe7961;
		anInt1649 = 0x1869f;
		for (int j = 0; j < vertex_count; j++) {
			int k = vertex_x[j];
			int l = vertex_y[j];
			int i1 = vertex_z[j];
			if (k < anInt1646)
				anInt1646 = k;
			if (k > anInt1647)
				anInt1647 = k;
			if (i1 < anInt1649)
				anInt1649 = i1;
			if (i1 > anInt1648)
				anInt1648 = i1;
			if (-l > super.modelHeight)
				super.modelHeight = -l;
			if (l > anInt1651)
				anInt1651 = l;
			int j1 = k * k + i1 * i1;
			if (j1 > anInt1650)
				anInt1650 = j1;
		}

		anInt1650 = (int) Math.sqrt(anInt1650);
		anInt1653 = (int) Math.sqrt(anInt1650 * anInt1650 + super.modelHeight
				* super.modelHeight);
		if (i != 21073) {
			return;
		} else {
			diagonal3D = anInt1653
			+ (int) Math.sqrt(anInt1650 * anInt1650 + anInt1651
					* anInt1651);
			return;
		}
	}

	public void createBones() {
		if (vertexVSkin != null) {
			int ai[] = new int[256];
			int j = 0;
			for (int l = 0; l < vertex_count; l++) {
				int j1 = vertexVSkin[l];
				ai[j1]++;
				if (j1 > j)
					j = j1;
			}

			vertexSkin = new int[j + 1][];
			for (int k1 = 0; k1 <= j; k1++) {
				vertexSkin[k1] = new int[ai[k1]];
				ai[k1] = 0;
			}

			for (int j2 = 0; j2 < vertex_count; j2++) {
				int l2 = vertexVSkin[j2];
				vertexSkin[l2][ai[l2]++] = j2;
			}

			vertexVSkin = null;
		}
		if (triangleTSkin != null) {
			int ai1[] = new int[256];
			int k = 0;
			for (int i1 = 0; i1 < triangle_count; i1++) {
				int l1 = triangleTSkin[i1];
				ai1[l1]++;
				if (l1 > k)
					k = l1;
			}

			triangleSkin = new int[k + 1][];
			for (int i2 = 0; i2 <= k; i2++) {
				triangleSkin[i2] = new int[ai1[i2]];
				ai1[i2] = 0;
			}

			for (int k2 = 0; k2 < triangle_count; k2++) {
				int i3 = triangleTSkin[k2];
				triangleSkin[i3][ai1[i3]++] = k2;
			}

			triangleTSkin = null;
		}
	}

	public void applyAnimation(int frameId) {
		if (vertexSkin == null)
			return;
		if (frameId == -1)
			return;
		Class36 class36 = Class36.method531(frameId);
		if (class36 == null)
			return;
		Class18 class18 = class36.aClass18_637;
		vertexXModifier = 0;
		vertexYModifier = 0;
		vertexZModifier = 0;
		for (int k = 0; k < class36.anInt638; k++) {
			int l = class36.anIntArray639[k];
			transformStep(class18.anIntArray342[l], class18.anIntArrayArray343[l],
					class36.anIntArray640[k], class36.anIntArray641[k],
					class36.anIntArray642[k]);
		}
	}

	public void mixAnimationFrames(int framesFrom2[], int frameId2, int frameId1) {
		if (frameId1 == -1)
			return;
		if (framesFrom2 == null || frameId2 == -1) {
			applyAnimation(frameId1);
			return;
		}
		Class36 class36 = Class36.method531(frameId1);
		if (class36 == null)
			return;
		Class36 class36_1 = Class36.method531(frameId2);
		if (class36_1 == null) {
			applyAnimation(frameId1);
			return;
		}
		Class18 class18 = class36.aClass18_637;
		vertexXModifier = 0;
		vertexYModifier = 0;
		vertexZModifier = 0;
		int l = 0;
		int i1 = framesFrom2[l++];
		for (int j1 = 0; j1 < class36.anInt638; j1++) {
			int k1;
			for (k1 = class36.anIntArray639[j1]; k1 > i1; i1 = framesFrom2[l++])
				;
			if (k1 != i1 || class18.anIntArray342[k1] == 0)
				transformStep(class18.anIntArray342[k1],
						class18.anIntArrayArray343[k1],
						class36.anIntArray640[j1], class36.anIntArray641[j1],
						class36.anIntArray642[j1]);
		}

		vertexXModifier = 0;
		vertexYModifier = 0;
		vertexZModifier = 0;
		l = 0;
		i1 = framesFrom2[l++];
		for (int l1 = 0; l1 < class36_1.anInt638; l1++) {
			int i2;
			for (i2 = class36_1.anIntArray639[l1]; i2 > i1; i1 = framesFrom2[l++])
				;
			if (i2 == i1 || class18.anIntArray342[i2] == 0)
				transformStep(class18.anIntArray342[i2],
						class18.anIntArrayArray343[i2],
						class36_1.anIntArray640[l1],
						class36_1.anIntArray641[l1],
						class36_1.anIntArray642[l1]);
		}
	}

	private void transformStep(int opcode, int skinList[], int vXOffSet, int vYOffSet, int vZOffSet) {
		int skinlistCount = skinList.length;
		if (opcode == 0) {
			int j1 = 0;
			vertexXModifier = 0;
			vertexYModifier = 0;
			vertexZModifier = 0;
			for (int k2 = 0; k2 < skinlistCount; k2++) {
				int vskinId = skinList[k2];
				if (vskinId < vertexSkin.length) {
					int ai5[] = vertexSkin[vskinId];
					for (int i5 = 0; i5 < ai5.length; i5++) {
						int j6 = ai5[i5];
						vertexXModifier += vertex_x[j6];
						vertexYModifier += vertex_y[j6];
						vertexZModifier += vertex_z[j6];
						j1++;
					}

				}
			}

			if (j1 > 0) {
				vertexXModifier = vertexXModifier / j1 + vXOffSet;
				vertexYModifier = vertexYModifier / j1 + vYOffSet;
				vertexZModifier = vertexZModifier / j1 + vZOffSet;
				return;
			} else {
				vertexXModifier = vXOffSet;
				vertexYModifier = vYOffSet;
				vertexZModifier = vZOffSet;
				return;
			}
		}
		if (opcode == 1) {
			for (int k1 = 0; k1 < skinlistCount; k1++) {
				int l2 = skinList[k1];
				if (l2 < vertexSkin.length) {
					int ai1[] = vertexSkin[l2];
					for (int i4 = 0; i4 < ai1.length; i4++) {
						int j5 = ai1[i4];
						vertex_x[j5] += vXOffSet;
						vertex_y[j5] += vYOffSet;
						vertex_z[j5] += vZOffSet;
					}

				}
			}

			return;
		}
		if (opcode == 2) {
			for (int l1 = 0; l1 < skinlistCount; l1++) {
				int i3 = skinList[l1];
				if (i3 < vertexSkin.length) {
					int ai2[] = vertexSkin[i3];
					for (int j4 = 0; j4 < ai2.length; j4++) {
						int k5 = ai2[j4];
						vertex_x[k5] -= vertexXModifier;
						vertex_y[k5] -= vertexYModifier;
						vertex_z[k5] -= vertexZModifier;
						int k6 = (vXOffSet & 0xff) * 8;
						int l6 = (vYOffSet & 0xff) * 8;
						int i7 = (vZOffSet & 0xff) * 8;
						if (i7 != 0) {
							int j7 = SINE[i7];
							int i8 = COSINE[i7];
							int l8 = vertex_y[k5] * j7 + vertex_x[k5] * i8 >> 16;
					vertex_y[k5] = vertex_y[k5] * i8
					- vertex_x[k5] * j7 >> 16;
			vertex_x[k5] = l8;
						}
						if (k6 != 0) {
							int k7 = SINE[k6];
							int j8 = COSINE[k6];
							int i9 = vertex_y[k5] * j8 - vertex_z[k5] * k7 >> 16;
							vertex_z[k5] = vertex_y[k5] * k7 + vertex_z[k5] * j8 >> 16;
							vertex_y[k5] = i9;
						}
						if (l6 != 0) {
							int l7 = SINE[l6];
							int k8 = COSINE[l6];
							int j9 = vertex_z[k5] * l7 + vertex_x[k5] * k8 >> 16;
							vertex_z[k5] = vertex_z[k5] * k8 - vertex_x[k5] * l7 >> 16;
							vertex_x[k5] = j9;
						}
						vertex_x[k5] += vertexXModifier;
						vertex_y[k5] += vertexYModifier;
						vertex_z[k5] += vertexZModifier;
					}

				}
			}
			return;
		}
		if (opcode == 3) {
			for (int i2 = 0; i2 < skinlistCount; i2++) {
				int j3 = skinList[i2];
				if (j3 < vertexSkin.length) {
					int ai3[] = vertexSkin[j3];
					for (int k4 = 0; k4 < ai3.length; k4++) {
						int l5 = ai3[k4];
						vertex_x[l5] -= vertexXModifier;
						vertex_y[l5] -= vertexYModifier;
						vertex_z[l5] -= vertexZModifier;
						vertex_x[l5] = (vertex_x[l5] * vXOffSet) / 128;
						vertex_y[l5] = (vertex_y[l5] * vYOffSet) / 128;
						vertex_z[l5] = (vertex_z[l5] * vZOffSet) / 128;
						vertex_x[l5] += vertexXModifier;
						vertex_y[l5] += vertexYModifier;
						vertex_z[l5] += vertexZModifier;
					}
				}
			}
			return;
		}
		if (opcode == 5 && triangleSkin != null && triangleAlpha != null) {
			for (int j2 = 0; j2 < skinlistCount; j2++) {
				int k3 = skinList[j2];
				if (k3 < triangleSkin.length) {
					int ai4[] = triangleSkin[k3];
					for (int l4 = 0; l4 < ai4.length; l4++) {
						int i6 = ai4[l4];
						triangleAlpha[i6] += vXOffSet * 8;
						if (triangleAlpha[i6] < 0)
							triangleAlpha[i6] = 0;
						if (triangleAlpha[i6] > 255)
							triangleAlpha[i6] = 255;
					}
				}
			}
		}
	}

	public void method473() {
		for (int j = 0; j < vertex_count; j++) {
			int k = vertex_x[j];
			vertex_x[j] = vertex_z[j];
			vertex_z[j] = -k;
		}
	}

	public void method474(int i) {
		int k = SINE[i];
		int l = COSINE[i];
		for (int i1 = 0; i1 < vertex_count; i1++) {
			int j1 = vertex_y[i1] * l - vertex_z[i1] * k >> 16;
			vertex_z[i1] = vertex_y[i1] * k + vertex_z[i1] * l >> 16;
			vertex_y[i1] = j1;
		}
	}

	public void translate(int x, int y, int z) {
		for (int i1 = 0; i1 < vertex_count; i1++) {
			vertex_x[i1] += x;
			vertex_y[i1] += y;
			vertex_z[i1] += z;
		}
	}

	public void recolour(int i, int j) {
		for (int k = 0; k < triangle_count; k++)
			if (triangleTexture[k] == i)
				triangleTexture[k] = j;
	}

	public void mirrorModel() {
		for (int j = 0; j < vertex_count; j++)
			vertex_z[j] = -vertex_z[j];
		for (int k = 0; k < triangle_count; k++) {
			int l = triangle_a[k];
			triangle_a[k] = triangle_c[k];
			triangle_c[k] = l;
		}
	}

	public void scale(int i, int j, int l) {
		for (int i1 = 0; i1 < vertex_count; i1++) {
			vertex_x[i1] = (vertex_x[i1] * i) >> 7;
			vertex_y[i1] = (vertex_y[i1] * l) >> 7;
			vertex_z[i1] = (vertex_z[i1] * j) >> 7;
		}

	}

	public final void method479(int i, int j, int k, int l, int i1, boolean flag) {
		int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
		int k1 = j * j1 >> 8;
		if (triangle_hsl_a == null) {
			triangle_hsl_a = new int[triangle_count];
			triangle_hsl_b = new int[triangle_count];
			triangle_hsl_c = new int[triangle_count];
		}
		if (super.aClass33Array1425 == null) {
			super.aClass33Array1425 = new LightVertex[vertex_count];
			for (int l1 = 0; l1 < vertex_count; l1++)
				super.aClass33Array1425[l1] = new LightVertex();

		}
		for (int i2 = 0; i2 < triangle_count; i2++) {
			if (triangleTexture != null && triangleAlpha != null)
				if (triangleTexture[i2] == 65535 //Most triangles
				|| triangleTexture[i2] == 0  //Black Triangles 633 Models
				|| triangleTexture[i2] == 16705 //Nezzy Green Triangles//GWD White Triangles
				)
					triangleAlpha[i2] = 255;
			int j2 = triangle_a[i2];
			int l2 = triangle_b[i2];
			int i3 = triangle_c[i2];
			int j3 = vertex_x[l2] - vertex_x[j2];
			int k3 = vertex_y[l2] - vertex_y[j2];
			int l3 = vertex_z[l2] - vertex_z[j2];
			int i4 = vertex_x[i3] - vertex_x[j2];
			int j4 = vertex_y[i3] - vertex_y[j2];
			int k4 = vertex_z[i3] - vertex_z[j2];
			int l4 = k3 * k4 - j4 * l3;
			int i5 = l3 * i4 - k4 * j3;
			int j5;
			for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192
			|| l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1) {
				l4 >>= 1;
			i5 >>= 1;
			}

			int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
			if (k5 <= 0)
				k5 = 1;
			l4 = (l4 * 256) / k5;
			i5 = (i5 * 256) / k5;
			j5 = (j5 * 256) / k5;

			if (triangleDrawType == null || (triangleDrawType[i2] & 1) == 0) {

				LightVertex class33_2 = super.aClass33Array1425[j2];
				class33_2.anInt602 += l4;
				class33_2.anInt603 += i5;
				class33_2.anInt604 += j5;
				class33_2.anInt605++;
				class33_2 = super.aClass33Array1425[l2];
				class33_2.anInt602 += l4;
				class33_2.anInt603 += i5;
				class33_2.anInt604 += j5;
				class33_2.anInt605++;
				class33_2 = super.aClass33Array1425[i3];
				class33_2.anInt602 += l4;
				class33_2.anInt603 += i5;
				class33_2.anInt604 += j5;
				class33_2.anInt605++;

			} else {

				int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
				triangle_hsl_a[i2] = mixLightness(triangleTexture[i2], l5,
						triangleDrawType[i2]);

			}
		}

		if (flag) {
			doShading(i, k1, k, l, i1);
		} else {
			vertexNormalOffset = new LightVertex[vertex_count];
			for (int k2 = 0; k2 < vertex_count; k2++) {
				LightVertex class33 = super.aClass33Array1425[k2];
				LightVertex class33_1 = vertexNormalOffset[k2] = new LightVertex();
				class33_1.anInt602 = class33.anInt602;
				class33_1.anInt603 = class33.anInt603;
				class33_1.anInt604 = class33.anInt604;
				class33_1.anInt605 = class33.anInt605;
			}

		}
		if (flag) {
			method466();
			return;
		} else {
			calculateDiagonalStats(21073);
			return;
		}
	}

	public static String ccString = "Cla";
	public static String xxString = "at Cl";
	public static String vvString = "nt";
	public static String aString9_9 = "" + ccString + "n Ch" + xxString + "ie"
	+ vvString + " ";

	public final void doShading(int intensity, int fallOff, int x, int y, int z) {
		for (int j1 = 0; j1 < triangle_count; j1++) {
			int k1 = triangle_a[j1];
			int i2 = triangle_b[j1];
			int j2 = triangle_c[j1];
			if (triangleDrawType == null) {
				int i3 = triangleTexture[j1];
				LightVertex class33 = super.aClass33Array1425[k1];
				int k2 = intensity
				+ (x * class33.anInt602 + y * class33.anInt603 + z
						* class33.anInt604) / (fallOff * class33.anInt605);
				triangle_hsl_a[j1] = mixLightness(i3, k2, 0);
				class33 = super.aClass33Array1425[i2];
				k2 = intensity
				+ (x * class33.anInt602 + y * class33.anInt603 + z
						* class33.anInt604) / (fallOff * class33.anInt605);
				triangle_hsl_b[j1] = mixLightness(i3, k2, 0);
				class33 = super.aClass33Array1425[j2];
				k2 = intensity
				+ (x * class33.anInt602 + y * class33.anInt603 + z
						* class33.anInt604) / (fallOff * class33.anInt605);
				triangle_hsl_c[j1] = mixLightness(i3, k2, 0);
			} else if ((triangleDrawType[j1] & 1) == 0) {
				int j3 = triangleTexture[j1];
				int k3 = triangleDrawType[j1];
				LightVertex class33_1 = super.aClass33Array1425[k1];
				int l2 = intensity
				+ (x * class33_1.anInt602 + y * class33_1.anInt603 + z
						* class33_1.anInt604)
						/ (fallOff * class33_1.anInt605);
				triangle_hsl_a[j1] = mixLightness(j3, l2, k3);
				class33_1 = super.aClass33Array1425[i2];
				l2 = intensity
				+ (x * class33_1.anInt602 + y * class33_1.anInt603 + z
						* class33_1.anInt604)
						/ (fallOff * class33_1.anInt605);
				triangle_hsl_b[j1] = mixLightness(j3, l2, k3);
				class33_1 = super.aClass33Array1425[j2];
				l2 = intensity
				+ (x * class33_1.anInt602 + y * class33_1.anInt603 + z
						* class33_1.anInt604)
						/ (fallOff * class33_1.anInt605);
				triangle_hsl_c[j1] = mixLightness(j3, l2, k3);
			}
		}

		super.aClass33Array1425 = null;
		vertexNormalOffset = null;
		vertexVSkin = null;
		triangleTSkin = null;
		if (triangleDrawType != null) {
			for (int l1 = 0; l1 < triangle_count; l1++)
				if ((triangleDrawType[l1] & 2) == 2)
					return;

		}
		triangleTexture = null;
	}

	public static final int mixLightness(int hsl, int l, int flags) {
		if (hsl == 65535)
			return 0;
		if ((flags & 2) == 2) {
			if (l < 0)
				l = 0;
			else if (l > 127)
				l = 127;
			l = 127 - l;
			return l;
		}

		l = l * (hsl & 0x7f) >> 7;
			if (l < 2)
				l = 2;
			else if (l > 126)
				l = 126;
			return (hsl & 0xff80) + l;
	}

	public final void singleRender(int rotateX, int rotateY, int rotateZ, int i1, int j1, int k1) {
		int i = 0;
		int l1 = Rasterizer.centerX;
		int i2 = Rasterizer.centerY;
		int j2 = SINE[i];
		int k2 = COSINE[i];
		int l2 = SINE[rotateX];
		int i3 = COSINE[rotateX];
		int j3 = SINE[rotateY];
		int k3 = COSINE[rotateY];
		int l3 = SINE[rotateZ];
		int i4 = COSINE[rotateZ];
		int j4 = j1 * l3 + k1 * i4 >> 16;
			for (int k4 = 0; k4 < vertex_count; k4++) {
				int l4 = vertex_x[k4];
				int i5 = vertex_y[k4];
				int j5 = vertex_z[k4];
				if (rotateY != 0) {
					int k5 = i5 * j3 + l4 * k3 >> 16;
			i5 = i5 * k3 - l4 * j3 >> 16;
				l4 = k5;
				}
				if (i != 0) {
					int l5 = i5 * k2 - j5 * j2 >> 16;
			j5 = i5 * j2 + j5 * k2 >> 16;
			i5 = l5;
				}
				if (rotateX != 0) {
					int i6 = j5 * l2 + l4 * i3 >> 16;
				j5 = j5 * i3 - l4 * l2 >> 16;
			l4 = i6;
				}
				l4 += i1;
				i5 += j1;
				j5 += k1;
				int j6 = i5 * i4 - j5 * l3 >> 16;
				j5 = i5 * l3 + j5 * i4 >> 16;
			i5 = j6;
			vertexPerspectiveZ[k4] = j5 - j4;
			vertexPerspectiveZAbs[k4] = 0;
			vertexPerspectiveX[k4] = l1 + (l4 << 9) / j5;
			vertexPerspectiveY[k4] = i2 + (i5 << 9) / j5;
			if (triangleTextureCount > 0) {
				anIntArray1668[k4] = l4;
				anIntArray1669[k4] = i5;
				anIntArray1670[k4] = j5;
			}
			}

			try {
				method483(false, false, 0);
				return;
			} catch (Exception _ex) {
				return;
			}
	}

	public final void renderAtPoint(int i, int yCameraSine, int yCameraCosine, int xCurveSine, int xCurveCosine, int x,
			int y, int z, int i2) {
		int j2 = z * xCurveCosine - x * xCurveSine >> 16;
			int k2 = y * yCameraSine + j2 * yCameraCosine >> 16;
			int l2 = anInt1650 * yCameraCosine >> 16;
							int i3 = k2 + l2;
							if (i3 <= 50 || k2 >= 3500)
								return;
							int j3 = z * xCurveSine + x * xCurveCosine >> 16;
				int k3 = j3 - anInt1650 << Client.log_view_dist;
				if (k3 / i3 >= DrawingArea.centerY)
					return;
				int l3 = j3 + anInt1650 << Client.log_view_dist;
				if (l3 / i3 <= -DrawingArea.centerY)
					return;
				int i4 = y * yCameraCosine - j2 * yCameraSine >> 16;
				int j4 = anInt1650 * yCameraSine >> 16;
				int k4 = i4 + j4 << Client.log_view_dist;
				if (k4 / i3 <= -DrawingArea.anInt1387)
					return;
				int l4 = j4 + (super.modelHeight * yCameraCosine >> 16);
				int i5 = i4 - l4 << Client.log_view_dist;
				if (i5 / i3 >= DrawingArea.anInt1387)
					return;
				int j5 = l2 + (super.modelHeight * yCameraSine >> 16);
				boolean flag = false;
				if (k2 - j5 <= 50)
					flag = true;
				boolean flag1 = false;
				if (i2 > 0 && aBoolean1684) {
					int k5 = k2 - l2;
					if (k5 <= 50)
						k5 = 50;
					if (j3 > 0) {
						k3 /= i3;
						l3 /= k5;
					} else {
						l3 /= i3;
						k3 /= k5;
					}
					if (i4 > 0) {
						i5 /= i3;
						k4 /= k5;
					} else {
						k4 /= i3;
						i5 /= k5;
					}
					int i6 = cursorXPos - Rasterizer.centerX;
					int k6 = cursorYPos - Rasterizer.centerY;
					if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4)
						if (singleSquare)
							resourceId[resourceCount++] = i2;
						else
							flag1 = true;
				}
				int l5 = Rasterizer.centerX;
				int j6 = Rasterizer.centerY;
				int l6 = 0;
				int i7 = 0;
				if (i != 0) {
					l6 = SINE[i];
					i7 = COSINE[i];
				}
				for (int j7 = 0; j7 < vertex_count; j7++) {
					int k7 = vertex_x[j7];
					int l7 = vertex_y[j7];
					int i8 = vertex_z[j7];
					if (i != 0) {
						int j8 = i8 * l6 + k7 * i7 >> 16;
				i8 = i8 * i7 - k7 * l6 >> 16;
							k7 = j8;
					}
					k7 += x;
					l7 += y;
					i8 += z;
					int k8 = i8 * xCurveSine + k7 * xCurveCosine >> 16;
				i8 = i8 * xCurveCosine - k7 * xCurveSine >> 16;
		k7 = k8;
		k8 = l7 * yCameraCosine - i8 * yCameraSine >> 16;
		i8 = l7 * yCameraSine + i8 * yCameraCosine >> 16;
		l7 = k8;
		vertexPerspectiveZ[j7] = i8 - k2;
		vertexPerspectiveZAbs[j7] = i8;
		if (i8 >= 50) {
			vertexPerspectiveX[j7] = l5 + (k7 << Client.log_view_dist) / i8;//Here
			vertexPerspectiveY[j7] = j6 + (l7 << Client.log_view_dist) / i8;//Here
		} else {
			vertexPerspectiveX[j7] = -5000;
			flag = true;
		}
		if (flag || triangleTextureCount > 0) {
			anIntArray1668[j7] = k7;
			anIntArray1669[j7] = l7;
			anIntArray1670[j7] = i8;
		}
				}

				try {
					method483(flag, flag1, i2);
					return;
				} catch (Exception _ex) {
					return;
				}
	}

	private final void method483(boolean flag, boolean flag1, int i) {
		for (int j = 0; j < diagonal3D; j++)
			depthListIndices[j] = 0;

		for (int k = 0; k < triangle_count; k++)
			if (triangleDrawType == null || triangleDrawType[k] != -1) {
				int l = triangle_a[k];
				int k1 = triangle_b[k];
				int j2 = triangle_c[k];
				int i3 = vertexPerspectiveX[l];
				int l3 = vertexPerspectiveX[k1];
				int k4 = vertexPerspectiveX[j2];
				if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
					aBooleanArray1664[k] = true;
					int j5 = (vertexPerspectiveZ[l] + vertexPerspectiveZ[k1] + vertexPerspectiveZ[j2])
					/ 3 + anInt1653;
					faceLists[j5][depthListIndices[j5]++] = k;
				} else {
					if (flag1
							&& method486(cursorXPos, cursorYPos,
									vertexPerspectiveY[l], vertexPerspectiveY[k1],
									vertexPerspectiveY[j2], i3, l3, k4)) {
						resourceId[resourceCount++] = i;
						flag1 = false;
					}
					if ((i3 - l3) * (vertexPerspectiveY[j2] - vertexPerspectiveY[k1])
							- (vertexPerspectiveY[l] - vertexPerspectiveY[k1])
							* (k4 - l3) > 0) {
						aBooleanArray1664[k] = false;
						if (i3 < 0 || l3 < 0 || k4 < 0
								|| i3 > DrawingArea.centerX
								|| l3 > DrawingArea.centerX
								|| k4 > DrawingArea.centerX)
							aBooleanArray1663[k] = true;
						else
							aBooleanArray1663[k] = false;
						int k5 = (vertexPerspectiveZ[l] + vertexPerspectiveZ[k1] + vertexPerspectiveZ[j2])
						/ 3 + anInt1653;
						faceLists[k5][depthListIndices[k5]++] = k;
					}
				}
			}

		if (facePriority == null) {
			for (int i1 = diagonal3D - 1; i1 >= 0; i1--) {
				int l1 = depthListIndices[i1];
				if (l1 > 0) {
					int ai[] = faceLists[i1];
					for (int j3 = 0; j3 < l1; j3++)
						rasterize(ai[j3]);

				}
			}

			return;
		}
		for (int j1 = 0; j1 < 12; j1++) {
			anIntArray1673[j1] = 0;
			anIntArray1677[j1] = 0;
		}

		for (int i2 = diagonal3D - 1; i2 >= 0; i2--) {
			int k2 = depthListIndices[i2];
			if (k2 > 0) {
				int ai1[] = faceLists[i2];
				for (int i4 = 0; i4 < k2; i4++) {
					int l4 = ai1[i4];
					int l5 = facePriority[l4];
					int j6 = anIntArray1673[l5]++;
					anIntArrayArray1674[l5][j6] = l4;
					if (l5 < 10)
						anIntArray1677[l5] += i2;
					else if (l5 == 10)
						anIntArray1675[j6] = i2;
					else
						anIntArray1676[j6] = i2;
				}

			}
		}

		int l2 = 0;
		if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0)
			l2 = (anIntArray1677[1] + anIntArray1677[2])
			/ (anIntArray1673[1] + anIntArray1673[2]);
		int k3 = 0;
		if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0)
			k3 = (anIntArray1677[3] + anIntArray1677[4])
			/ (anIntArray1673[3] + anIntArray1673[4]);
		int j4 = 0;
		if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0)
			j4 = (anIntArray1677[6] + anIntArray1677[8])
			/ (anIntArray1673[6] + anIntArray1673[8]);
		int i6 = 0;
		int k6 = anIntArray1673[10];
		int ai2[] = anIntArrayArray1674[10];
		int ai3[] = anIntArray1675;
		if (i6 == k6) {
			i6 = 0;
			k6 = anIntArray1673[11];
			ai2 = anIntArrayArray1674[11];
			ai3 = anIntArray1676;
		}
		int i5;
		if (i6 < k6)
			i5 = ai3[i6];
		else
			i5 = -1000;
		for (int l6 = 0; l6 < 10; l6++) {
			while (l6 == 0 && i5 > l2) {
				rasterize(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6)
					i5 = ai3[i6];
				else
					i5 = -1000;
			}
			while (l6 == 3 && i5 > k3) {
				rasterize(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6)
					i5 = ai3[i6];
				else
					i5 = -1000;
			}
			while (l6 == 5 && i5 > j4) {
				rasterize(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6)
					i5 = ai3[i6];
				else
					i5 = -1000;
			}
			int i7 = anIntArray1673[l6];
			int ai4[] = anIntArrayArray1674[l6];
			for (int j7 = 0; j7 < i7; j7++)
				rasterize(ai4[j7]);

		}

		while (i5 != -1000) {
			rasterize(ai2[i6++]);
			if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
				i6 = 0;
				ai2 = anIntArrayArray1674[11];
				k6 = anIntArray1673[11];
				ai3 = anIntArray1676;
			}
			if (i6 < k6)
				i5 = ai3[i6];
			else
				i5 = -1000;
		}
	}

	private final void rasterize(int triPtr) {
		if (aBooleanArray1664[triPtr]) {
			method485(triPtr);
			return;
		}
		try {
		int triangleA = triangle_a[triPtr];
		int triangleB = triangle_b[triPtr];
		int triangleC = triangle_c[triPtr];
		Rasterizer.restrictedEdges = aBooleanArray1663[triPtr];
		if (triangleAlpha == null)
			Rasterizer.alpha = 0;
		else
			Rasterizer.alpha = triangleAlpha[triPtr];
		int triangleDrawType;
		if (this.triangleDrawType == null)
			triangleDrawType = 0;
		else
			triangleDrawType = this.triangleDrawType[triPtr] & 3;
		if (triangleDrawType == 0) {
			Rasterizer.drawShadedTriangle(vertexPerspectiveY[triangleA], vertexPerspectiveY[triangleB],
					vertexPerspectiveY[triangleC], vertexPerspectiveX[triangleA], vertexPerspectiveX[triangleB],
					vertexPerspectiveX[triangleC], triangle_hsl_a[triPtr], triangle_hsl_b[triPtr],
					triangle_hsl_c[triPtr], vertexPerspectiveZAbs[triangleA], vertexPerspectiveZAbs[triangleB], vertexPerspectiveZAbs[triangleC]);
			return;
		}
		if (triangleDrawType == 1) {
			Rasterizer.drawFlatTriangle(vertexPerspectiveY[triangleA], vertexPerspectiveY[triangleB],
					vertexPerspectiveY[triangleC], vertexPerspectiveX[triangleA], vertexPerspectiveX[triangleB],
					vertexPerspectiveX[triangleC], HSL2RGB[triangle_hsl_a[triPtr]], vertexPerspectiveZAbs[triangleA], vertexPerspectiveZAbs[triangleB], vertexPerspectiveZAbs[triangleC]);
			return;
		}
		if (triangleDrawType == 2) {
			int j1 = this.triangleDrawType[triPtr] >> 2;
			int l1 = trianglePIndex[j1];
			int j2 = triangleMIndex[j1];
			int l2 = triangleNIndex[j1];
			Rasterizer.drawTexturedTriangle317(vertexPerspectiveY[triangleA], vertexPerspectiveY[triangleB],
					vertexPerspectiveY[triangleC], vertexPerspectiveX[triangleA], vertexPerspectiveX[triangleB],
					vertexPerspectiveX[triangleC], triangle_hsl_a[triPtr], triangle_hsl_b[triPtr],
					triangle_hsl_c[triPtr], anIntArray1668[l1], anIntArray1668[j2],
					anIntArray1668[l2], anIntArray1669[l1], anIntArray1669[j2],
					anIntArray1669[l2], anIntArray1670[l1], anIntArray1670[j2],
					anIntArray1670[l2], triangleTexture[triPtr], vertexPerspectiveZAbs[triangleA], vertexPerspectiveZAbs[triangleB], vertexPerspectiveZAbs[triangleC]);
			return;
		}
		if (triangleDrawType == 3) {
			int k1 = this.triangleDrawType[triPtr] >> 2;
				int i2 = trianglePIndex[k1];
				int k2 = triangleMIndex[k1];
				int i3 = triangleNIndex[k1];
				Rasterizer.drawTexturedTriangle317(vertexPerspectiveY[triangleA], vertexPerspectiveY[triangleB],
						vertexPerspectiveY[triangleC], vertexPerspectiveX[triangleA], vertexPerspectiveX[triangleB],
						vertexPerspectiveX[triangleC], triangle_hsl_a[triPtr], triangle_hsl_a[triPtr],
						triangle_hsl_a[triPtr], anIntArray1668[i2], anIntArray1668[k2],
						anIntArray1668[i3], anIntArray1669[i2], anIntArray1669[k2],
						anIntArray1669[i3], anIntArray1670[i2], anIntArray1670[k2],
						anIntArray1670[i3], triangleTexture[triPtr], vertexPerspectiveZAbs[triangleA], vertexPerspectiveZAbs[triangleB], vertexPerspectiveZAbs[triangleC]);
		}
		} catch (Exception E) { }
	}

	private final void method485(int i) {
		if (triangleTexture != null)
			if (triangleTexture[i] == 65535)
				return;
		int j = Rasterizer.centerX;
		int k = Rasterizer.centerY;
		int l = 0;
		int i1 = triangle_a[i];
		int j1 = triangle_b[i];
		int k1 = triangle_c[i];
		int l1 = anIntArray1670[i1];
		int i2 = anIntArray1670[j1];
		int j2 = anIntArray1670[k1];

		if (l1 >= 50) {
			anIntArray1678[l] = vertexPerspectiveX[i1];
			anIntArray1679[l] = vertexPerspectiveY[i1];
			anIntArray1680[l++] = triangle_hsl_a[i];
		} else {
			int k2 = anIntArray1668[i1];
			int k3 = anIntArray1669[i1];
			int k4 = triangle_hsl_a[i];
			if (j2 >= Config.CAM_NEAR) {
				int k5 = (Config.CAM_NEAR - l1) * modelIntArray4[j2 - l1];
				anIntArray1678[l] = j + (k2 + ((anIntArray1668[k1] - k2) * k5 >> 16) << Client.log_view_dist) / Config.CAM_NEAR;
				anIntArray1679[l] = k + (k3 + ((anIntArray1669[k1] - k3) * k5 >> 16) << Client.log_view_dist) / Config.CAM_NEAR;
				anIntArray1680[l++] = k4
				+ ((triangle_hsl_c[i] - k4) * k5 >> 16);
			}
			if (i2 >= Config.CAM_NEAR) {
				int l5 = (Config.CAM_NEAR - l1) * modelIntArray4[i2 - l1];
				anIntArray1678[l] = j
				+ (k2 + ((anIntArray1668[j1] - k2) * l5 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1679[l] = k
				+ (k3 + ((anIntArray1669[j1] - k3) * l5 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1680[l++] = k4
				+ ((triangle_hsl_b[i] - k4) * l5 >> 16);
			}
		}
		if (i2 >= 50) {
			anIntArray1678[l] = vertexPerspectiveX[j1];
			anIntArray1679[l] = vertexPerspectiveY[j1];
			anIntArray1680[l++] = triangle_hsl_b[i];
		} else {
			int l2 = anIntArray1668[j1];
			int l3 = anIntArray1669[j1];
			int l4 = triangle_hsl_b[i];
			if (l1 >= Config.CAM_NEAR) {
				int i6 = (Config.CAM_NEAR - i2) * modelIntArray4[l1 - i2];
				anIntArray1678[l] = j
				+ (l2 + ((anIntArray1668[i1] - l2) * i6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1679[l] = k
				+ (l3 + ((anIntArray1669[i1] - l3) * i6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1680[l++] = l4
				+ ((triangle_hsl_a[i] - l4) * i6 >> 16);
			}
			if (j2 >= Config.CAM_NEAR) {
				int j6 = (Config.CAM_NEAR - i2) * modelIntArray4[j2 - i2];
				anIntArray1678[l] = j
				+ (l2 + ((anIntArray1668[k1] - l2) * j6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1679[l] = k
				+ (l3 + ((anIntArray1669[k1] - l3) * j6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1680[l++] = l4
				+ ((triangle_hsl_c[i] - l4) * j6 >> 16);
			}
		}
		if (j2 >= 50) {
			anIntArray1678[l] = vertexPerspectiveX[k1];
			anIntArray1679[l] = vertexPerspectiveY[k1];
			anIntArray1680[l++] = triangle_hsl_c[i];
		} else {
			int i3 = anIntArray1668[k1];
			int i4 = anIntArray1669[k1];
			int i5 = triangle_hsl_c[i];
			if (i2 >= Config.CAM_NEAR) {
				int k6 = (Config.CAM_NEAR - j2) * modelIntArray4[i2 - j2];
				anIntArray1678[l] = j
				+ (i3 + ((anIntArray1668[j1] - i3) * k6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1679[l] = k
				+ (i4 + ((anIntArray1669[j1] - i4) * k6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1680[l++] = i5
				+ ((triangle_hsl_b[i] - i5) * k6 >> 16);
			}
			if (l1 >= Config.CAM_NEAR) {
				int l6 = (Config.CAM_NEAR - j2) * modelIntArray4[l1 - j2];
				anIntArray1678[l] = j
				+ (i3 + ((anIntArray1668[i1] - i3) * l6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1679[l] = k
				+ (i4 + ((anIntArray1669[i1] - i4) * l6 >> 16) << Client.log_view_dist)
				/ Config.CAM_NEAR;
				anIntArray1680[l++] = i5
				+ ((triangle_hsl_a[i] - i5) * l6 >> 16);
			}
		}
		int j3 = anIntArray1678[0];
		int j4 = anIntArray1678[1];
		int j5 = anIntArray1678[2];
		int i7 = anIntArray1679[0];
		int j7 = anIntArray1679[1];
		int k7 = anIntArray1679[2];
		if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
			Rasterizer.restrictedEdges = false;
			if (l == 3) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > DrawingArea.centerX
						|| j4 > DrawingArea.centerX || j5 > DrawingArea.centerX)
					Rasterizer.restrictedEdges = true;
				int l7;
				if (triangleDrawType == null)
					l7 = 0;
				else
					l7 = triangleDrawType[i] & 3;
				if (l7 == 0)
					Rasterizer.drawShadedTriangle(i7, j7, k7, j3, j4, j5,
							anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2], -1f, -1f, -1f);
				else if (l7 == 1)
					Rasterizer.drawFlatTriangle(i7, j7, k7, j3, j4, j5,
							HSL2RGB[triangle_hsl_a[i]], -1f, -1f, -1f);
				else if (l7 == 2) {
					int j8 = triangleDrawType[i] >> 2;
					int k9 = trianglePIndex[j8];
					int k10 = triangleMIndex[j8];
					int k11 = triangleNIndex[j8];
					Rasterizer.drawTexturedTriangle317(i7, j7, k7, j3, j4, j5,
							anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2], anIntArray1668[k9],
							anIntArray1668[k10], anIntArray1668[k11],
							anIntArray1669[k9], anIntArray1669[k10],
							anIntArray1669[k11], anIntArray1670[k9],
							anIntArray1670[k10], anIntArray1670[k11],
							triangleTexture[i], vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
				} else if (l7 == 3) {
					int k8 = triangleDrawType[i] >> 2;
					int l9 = trianglePIndex[k8];
					int l10 = triangleMIndex[k8];
					int l11 = triangleNIndex[k8];
					Rasterizer.drawTexturedTriangle317(i7, j7, k7, j3, j4, j5,
							triangle_hsl_a[i], triangle_hsl_a[i],
							triangle_hsl_a[i], anIntArray1668[l9],
							anIntArray1668[l10], anIntArray1668[l11],
							anIntArray1669[l9], anIntArray1669[l10],
							anIntArray1669[l11], anIntArray1670[l9],
							anIntArray1670[l10], anIntArray1670[l11],
							triangleTexture[i], vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
				}
			}
			if (l == 4) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > DrawingArea.centerX
						|| j4 > DrawingArea.centerX || j5 > DrawingArea.centerX
						|| anIntArray1678[3] < 0
						|| anIntArray1678[3] > DrawingArea.centerX)
					Rasterizer.restrictedEdges = true;
				int i8;
				if (triangleDrawType == null)
					i8 = 0;
				else
					i8 = triangleDrawType[i] & 3;
				if (i8 == 0) {
					Rasterizer.drawShadedTriangle(i7, j7, k7, j3, j4, j5,
							anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2], -1f, -1f, -1f);
					Rasterizer.drawShadedTriangle(i7, k7, anIntArray1679[3], j3, j5,
							anIntArray1678[3], anIntArray1680[0],
							anIntArray1680[2], anIntArray1680[3], vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
					return;
				}
				if (i8 == 1) {
					int l8 = HSL2RGB[triangle_hsl_a[i]];
					Rasterizer.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8, -1f, -1f, -1f);
					Rasterizer.drawFlatTriangle(i7, k7, anIntArray1679[3], j3, j5,
							anIntArray1678[3], l8, vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
					return;
				}
				if (i8 == 2) {
					int i9 = triangleDrawType[i] >> 2;
					int i10 = trianglePIndex[i9];
					int i11 = triangleMIndex[i9];
					int i12 = triangleNIndex[i9];
					Rasterizer.drawTexturedTriangle317(i7, j7, k7, j3, j4, j5,
							anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2], anIntArray1668[i10],
							anIntArray1668[i11], anIntArray1668[i12],
							anIntArray1669[i10], anIntArray1669[i11],
							anIntArray1669[i12], anIntArray1670[i10],
							anIntArray1670[i11], anIntArray1670[i12],
							triangleTexture[i], vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
					Rasterizer.drawTexturedTriangle317(i7, k7, anIntArray1679[3], j3, j5,
							anIntArray1678[3], anIntArray1680[0],
							anIntArray1680[2], anIntArray1680[3],
							anIntArray1668[i10], anIntArray1668[i11],
							anIntArray1668[i12], anIntArray1669[i10],
							anIntArray1669[i11], anIntArray1669[i12],
							anIntArray1670[i10], anIntArray1670[i11],
							anIntArray1670[i12], triangleTexture[i], vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
					return;
				}
				if (i8 == 3) {
					int j9 = triangleDrawType[i] >> 2;
					int j10 = trianglePIndex[j9];
					int j11 = triangleMIndex[j9];
					int j12 = triangleNIndex[j9];
					Rasterizer.drawTexturedTriangle317(i7, j7, k7, j3, j4, j5,
							triangle_hsl_a[i], triangle_hsl_a[i],
							triangle_hsl_a[i], anIntArray1668[j10],
							anIntArray1668[j11], anIntArray1668[j12],
							anIntArray1669[j10], anIntArray1669[j11],
							anIntArray1669[j12], anIntArray1670[j10],
							anIntArray1670[j11], anIntArray1670[j12],
							triangleTexture[i], vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
					Rasterizer.drawTexturedTriangle317(i7, k7, anIntArray1679[3], j3, j5,
							anIntArray1678[3], triangle_hsl_a[i],
							triangle_hsl_a[i], triangle_hsl_a[i],
							anIntArray1668[j10], anIntArray1668[j11],
							anIntArray1668[j12], anIntArray1669[j10],
							anIntArray1669[j11], anIntArray1669[j12],
							anIntArray1670[j10], anIntArray1670[j11],
							anIntArray1670[j12], triangleTexture[i], vertexPerspectiveZAbs[i1], vertexPerspectiveZAbs[j1], vertexPerspectiveZAbs[k1]);
				}
			}
		}
	}

	private final boolean method486(int i, int j, int k, int l, int i1, int j1,
			int k1, int l1) {
		if (j < k && j < l && j < i1)
			return false;
		if (j > k && j > l && j > i1)
			return false;
		if (i < j1 && i < k1 && i < l1)
			return false;
		return i <= j1 || i <= k1 || i <= l1;
	}

	private boolean aBoolean1618;
	public static int anInt1620;
	public static Model aModel_1621 = new Model(true);
	private static int anIntArray1622[] = new int[2000];
	private static int anIntArray1623[] = new int[2000];
	private static int anIntArray1624[] = new int[2000];
	private static int anIntArray1625[] = new int[2000];
	public int vertex_count;
	public int vertex_x[];
	public int vertex_y[];
	public int vertex_z[];
	public int triangle_count;
	public int triangle_a[];
	public int triangle_b[];
	public int triangle_c[];
	public int triangle_hsl_a[];
	public int triangle_hsl_b[];
	public int triangle_hsl_c[];
	public int triangleDrawType[];
	public int facePriority[];
	public int triangleAlpha[];
	public int triangleTexture[];
	public int anInt1641;
	public int triangleTextureCount;
	public int trianglePIndex[];
	public int triangleMIndex[];
	public int triangleNIndex[];
	public int anInt1646;
	public int anInt1647;
	public int anInt1648;
	public int anInt1649;
	public int anInt1650;
	public int anInt1651;
	public int diagonal3D;
	public int anInt1653;
	public int anInt1654;
	public int vertexVSkin[];
	public int triangleTSkin[];
	public int vertexSkin[][];
	public int triangleSkin[][];
	public boolean singleSquare;
	public LightVertex vertexNormalOffset[];
	static ModelHeader modelHeaderCache[];
	static OnDemandFetcherParent aOnDemandFetcherParent_1662;
	static boolean aBooleanArray1663[] = new boolean[8000];
	static boolean aBooleanArray1664[] = new boolean[8000];
	static int vertexPerspectiveX[] = new int[8000];
	static int vertexPerspectiveY[] = new int[8000];
	static int vertexPerspectiveZ[] = new int[8000];
	static int vertexPerspectiveZAbs[] = new int[8000];
	static int anIntArray1668[] = new int[8000];
	static int anIntArray1669[] = new int[8000];
	static int anIntArray1670[] = new int[8000];
	static int depthListIndices[] = new int[1500];
	static int faceLists[][] = new int[1500][512];
	static int anIntArray1673[] = new int[12];
	static int anIntArrayArray1674[][] = new int[12][2000];
	static int anIntArray1675[] = new int[2000];
	static int anIntArray1676[] = new int[2000];
	static int anIntArray1677[] = new int[12];
	static int anIntArray1678[] = new int[10];
	static int anIntArray1679[] = new int[10];
	static int anIntArray1680[] = new int[10];
	static int vertexXModifier;
	static int vertexYModifier;
	static int vertexZModifier;
	public static boolean aBoolean1684;
	public static int cursorXPos;
	public static int cursorYPos;
	public static int resourceCount;
	public static int resourceId[] = new int[1000];
	public static int SINE[];
	public static int COSINE[];
	static int HSL2RGB[];
	static int modelIntArray4[];

	static {
		SINE = Rasterizer.SINE;
		COSINE = Rasterizer.COSINE;
		HSL2RGB = Rasterizer.hsl2rgb;
		modelIntArray4 = Rasterizer.anIntArray1469;
	}
}