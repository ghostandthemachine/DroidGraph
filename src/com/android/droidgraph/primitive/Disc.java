package com.android.droidgraph.primitive;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.shape.GLShape;

public class Disc extends GLShape {

	private float innerRadius;

	private float outerRadius;

	private float segmentSize;

	private int segments;

	float[] verts;
	
	Polygon[] polygons;


	public Disc(int segs, float ir, float or) {

		segments = segs;

		innerRadius = ir;
		outerRadius = or;
		segmentSize = (float) (360 / segments);

		makeDiscVerts();
		makePolygons(verts);

	}

	public Disc() {
		this(36, 0.25f, 1.0f);
	}

	public void makeDiscVerts() {

		polygons = new Polygon[segments];

		float defaultZ = 0f;

		verts = new float[segments * 6];

		for (int i = 0; i < verts.length; i += 6) {

			float tx = (i / 6) * segmentSize;
			tx = (float) Math.cos(Math.toRadians(tx));

			float ty = (i / 6) * segmentSize;
			ty = (float) Math.sin(Math.toRadians(ty));

			verts[i] = tx * innerRadius; // inside verts
			verts[i + 1] = ty * innerRadius; //
			verts[i + 2] = defaultZ; //

			verts[i + 3] = tx * outerRadius; // outer verts
			verts[i + 4] = ty * outerRadius; //
			verts[i + 5] = defaultZ; //


		}
	}

	private void makePolygons(float[] verts) {

		// Loop is offset to deal with using first and last verts from the array
		// but not getting an array index exception

		for (int j = 1; j < segments; j++) {
			int i = (j * 6);
			float[] polyVerts = { verts[i - 6], verts[i - 5], verts[i - 4],
					verts[i - 3], verts[i - 2], verts[i - 1], verts[i],
					verts[i + 1], verts[i + 2], verts[i + 3], verts[i + 4],
					verts[i + 5] };

			polygons[j - 1] = new Polygon(polyVerts);

		}

		// make the last polygon from the last and first verts of the vert array
		polygons[polygons.length - 1] = new Polygon(new float[] {
				verts[verts.length - 6], verts[verts.length - 5],
				verts[verts.length - 4], verts[verts.length - 3],
				verts[verts.length - 2], verts[verts.length - 1], verts[0],
				verts[1], verts[2], verts[3], verts[4], verts[5]

		});
	}

	public void setColor(float r, float g, float b, float a) {
		for (Polygon poly : polygons) {
			poly.setColor(r, g, b, a);
		}
	}

	public void draw(GL10 gl) {
		
		for (int i = 0; i <= polygons.length - 1; i++) {
			
			polygons[i].draw(gl);
		}
	}



	@Override
	public void loadGLTexture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FloatBuffer getTextureBuffer() {
		// TODO Auto-generated method stub
		return null;
	}


}
