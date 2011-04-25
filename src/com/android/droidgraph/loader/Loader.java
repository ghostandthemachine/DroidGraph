package com.android.droidgraph.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.android.droidgraph.R;

public class Loader implements Serializable {
	private static final long serialVersionUID = -5579981347245166159L;
	private int IdxCnt;
	public static boolean useshading = false;
	public static float shadowcolor = 0.50f;

	public static GL11 gl11;
	private static int NBFACES = 0;
	private static int mVertBufferIndex;
	private static int mNormBufferIndex;
	private static int mTexBufferIndex;
	private static int mIndexBufferIndex;
	private static FloatBuffer mVertexBuffer;
	private static FloatBuffer mTexBuffer;
	private static FloatBuffer mNormBuffer;
	private static CharBuffer mIndexBuffer;
	private static Context mContext;

	private String fileName;

	public Loader(Context context, String fileName) {
		mContext = context;
		this.fileName = fileName;
		loadObj(fileName);
	}

	public void init(GL10 gl) {

		if (gl == null)
			return;
		gl11 = (GL11) gl;

		int[] buffer = new int[1];

		// vertex buffer.
		gl11.glGenBuffers(1, buffer, 0);
		mVertBufferIndex = buffer[0];
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, mVertexBuffer.capacity() * 4,
				mVertexBuffer, GL11.GL_STATIC_DRAW);
		Log.e(fileName, "Vx ok");

		// normal buffer.
		gl11.glGenBuffers(1, buffer, 0);
		mNormBufferIndex = buffer[0];
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mNormBufferIndex);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, mNormBuffer.capacity() * 4,
				mNormBuffer, GL11.GL_STATIC_DRAW);
		Log.e(fileName, "Vn ok");

		// texcoord buffer
		gl11.glGenBuffers(1, buffer, 0);
		mTexBufferIndex = buffer[0];
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mTexBufferIndex);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, mTexBuffer.capacity() * 4,
				mTexBuffer, GL11.GL_STATIC_DRAW);
		Log.e(fileName, "Vt ok");

		// unbind array buffer
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

		// Buffer d'indices
		gl11.glGenBuffers(1, buffer, 0);
		mIndexBufferIndex = buffer[0];
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferIndex);
		gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER,
				mIndexBuffer.capacity() * 2, mIndexBuffer, GL11.GL_STATIC_DRAW);

		// Unbind the element array buffer.
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		Log.e("VboCube", "Idx ok");
		IdxCnt = mIndexBuffer.capacity();
	}

	private void loadObj(String name) {
		try {
			InputStream fileIn = mContext.getResources().openRawResource(R.drawable.test);

			AssetManager am = mContext.getAssets();
			String str;
			String[] tmp;
			String[] ftmp;
			float v;
			ArrayList<Float> vlist = new ArrayList<Float>();
			ArrayList<Float> tlist = new ArrayList<Float>();
			ArrayList<Float> nlist = new ArrayList<Float>();
			ArrayList<Fp> fplist = new ArrayList<Fp>();

			BufferedReader inb = new BufferedReader(new InputStreamReader(am.open("test.obj"))); // need way to determine size buffer
			while ((str = inb.readLine()) != null) {
				tmp = str.split(" ");
				if (tmp[0].equalsIgnoreCase("v")) {

					for (int i = 1; i < 4; i++) {
						v = Float.parseFloat(tmp[i]);
						vlist.add(v);
					}

				}
				if (tmp[0].equalsIgnoreCase("vn")) {

					for (int i = 1; i < 4; i++) {
						v = Float.parseFloat(tmp[i]);
						nlist.add(v);
					}

				}
				if (tmp[0].equalsIgnoreCase("vt")) {
					for (int i = 1; i < 3; i++) {
						v = Float.parseFloat(tmp[i]);
						tlist.add(v);
					}

				}
				if (tmp[0].equalsIgnoreCase("f")) {
					for (int i = 1; i < 4; i++) {
						ftmp = tmp[i].split("/");

						long chi = Integer.parseInt(ftmp[0]) - 1;
						int cht = Integer.parseInt(ftmp[1]) - 1;
						int chn = Integer.parseInt(ftmp[2]) - 1;

						fplist.add(new Fp(chi, cht, chn));
					}
					NBFACES++;
				}
			}

			ByteBuffer vbb = ByteBuffer.allocateDirect(fplist.size() * 4 * 3);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer = vbb.asFloatBuffer();

			ByteBuffer vtbb = ByteBuffer.allocateDirect(fplist.size() * 4 * 2);
			vtbb.order(ByteOrder.nativeOrder());
			mTexBuffer = vtbb.asFloatBuffer();

			ByteBuffer nbb = ByteBuffer.allocateDirect(fplist.size() * 4 * 3);
			nbb.order(ByteOrder.nativeOrder());
			mNormBuffer = nbb.asFloatBuffer();

			for (int j = 0; j < fplist.size(); j++) {
				mVertexBuffer.put(vlist.get((int) (fplist.get(j).Vi * 3)));
				mVertexBuffer.put(vlist.get((int) (fplist.get(j).Vi * 3 + 1)));
				mVertexBuffer.put(vlist.get((int) (fplist.get(j).Vi * 3 + 2)));

				mTexBuffer.put(tlist.get(fplist.get(j).Ti * 2));
				mTexBuffer.put(tlist.get((fplist.get(j).Ti * 2) + 1));

				mNormBuffer.put(nlist.get(fplist.get(j).Ni * 3));
				mNormBuffer.put(nlist.get((fplist.get(j).Ni * 3) + 1));
				mNormBuffer.put(nlist.get((fplist.get(j).Ni * 3) + 2));
			}

			mIndexBuffer = CharBuffer.allocate(fplist.size());
			for (int j = 0; j < fplist.size(); j++) {
				mIndexBuffer.put((char) j);
			}

			mVertexBuffer.position(0);
			mTexBuffer.position(0);
			mNormBuffer.position(0);
			mIndexBuffer.position(0);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void draw(GL10 gl) {
		if (gl11 == null)
			return;
		gl.glPushMatrix();
		gl.glEnable(GL10.GL_TEXTURE_2D);

		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mTexBufferIndex);

		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
		gl11.glVertexPointer(3, GL10.GL_FLOAT, 0, 0);

		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mNormBufferIndex);
		gl11.glNormalPointer(GL10.GL_FLOAT, 0, 0);

		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferIndex);
		gl11.glDrawElements(GL11.GL_TRIANGLES, IdxCnt, GL11.GL_UNSIGNED_SHORT,
				0);

		gl.glPopMatrix();
		// gl11.glDisable(GL10.GL_TEXTURE_2D);
	}

	public void freeHardwareBuffers() {

		int[] buffer = new int[1];
		buffer[0] = mVertBufferIndex;
		gl11.glDeleteBuffers(1, buffer, 0);

		buffer[0] = mTexBufferIndex;
		gl11.glDeleteBuffers(1, buffer, 0);

		buffer[0] = mNormBufferIndex;
		gl11.glDeleteBuffers(1, buffer, 0);

		buffer[0] = mIndexBufferIndex;
		gl11.glDeleteBuffers(1, buffer, 0);

		mVertBufferIndex = 0;
		mIndexBufferIndex = 0;
		mTexBufferIndex = 0;
		mNormBufferIndex = 0;

		Log.d("SLWP", "sphere hardware buffer freed");

	}

	private class Fp {
		public long Vi;
		public int Ti;
		public int Ni;

		public Fp(long chi, int ti, int ni) {
			Vi = chi;
			Ti = ti;
			Ni = ni;
			// Log.e("VboCube",Vi+"/"+Ti+"/"+Ni);
		}
	}

}
