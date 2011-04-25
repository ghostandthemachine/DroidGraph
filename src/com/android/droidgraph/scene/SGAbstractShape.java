package com.android.droidgraph.scene;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.shape.GLShape;
import com.android.droidgraph.util.SGColorI;

public abstract class SGAbstractShape extends SGLeaf {

	public enum Mode {
		STROKE, FILL, STROKE_FILL
	};

	public static SGColorI gColorID = new SGColorI(0, 0, 0, 0);
	private SGColorI m_colorID = new SGColorI();

	protected ArrayList<Material> materials = new ArrayList<Material>();

	private boolean hasTexture = false;
	protected boolean selected;;

	public SGAbstractShape() {
		this(false);

		// Set up this nodes unique color id
		m_colorID.color[0] = gColorID.color[0];
		m_colorID.color[1] = gColorID.color[1];
		m_colorID.color[2] = gColorID.color[2];

		gColorID.color[0]++;
		if (gColorID.color[0] > 255) {
			gColorID.color[0] = 0;
			gColorID.color[1]++;
			if (gColorID.color[1] > 255) {
				gColorID.color[1] = 0;
				gColorID.color[2]++;
			}
		}
	}

	public void pickColorPaint(GL10 gl) {
		gl.glColor4f(m_colorID.getRedF(), m_colorID.getGreenF(), m_colorID.getBlueF(), m_colorID.getAlphaF());
	}

	public SGAbstractShape(boolean hasTex) {
		hasTexture = hasTex;
	}

	public abstract GLShape getShape();

	public void load(GL10 gl) {
		loadMaterials(gl);
	}

	public final Material[] getMaterial() {
		return (Material[]) materials.toArray();
	}

	public void addMaterial(Material material) {
		addMaterial(0, material);
	}

	public void addMaterial(int position, Material material) {
		materials.add(position, material);
	}

	public void removeMaterial(Material material) {
		materials.remove(material);
	}

	@Override
	public void paint(GL10 gl) {

	}

	public BoundingBox getBounds(Transform3D transform) {
		return null;
	}

	private void loadMaterials(GL10 gl) {
		if (materials != null) {
			for (Material material : materials) {
				material.loadMaterial(gl);
			}
		}
	}

	public void setHasTexture(boolean b) {
		hasTexture = b;
	}

	public boolean hasTexture() {
		return hasTexture;
	}

	public SGColorI getColorID() {
		return m_colorID;
	}

	public void setSelected(boolean b) {
		selected = b;
	}
	
	public boolean isSelected() {
		return selected;
	}
}
