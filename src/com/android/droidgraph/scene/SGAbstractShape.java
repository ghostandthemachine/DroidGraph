package com.android.droidgraph.scene;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.shape.GLShape;
import com.android.droidgraph.util.SGColor;

public abstract class SGAbstractShape extends SGLeaf {

	public enum Mode {
		STROKE, FILL, STROKE_FILL
	};

	protected ArrayList<Material> materials = new ArrayList<Material>();
	
	private boolean hasTexture = false;;
	
	
	public SGAbstractShape() {
		this(false);
	}
	
	public SGAbstractShape(boolean hasTex) {
		hasTexture = hasTex;
	}

	public abstract GLShape getShape();
	
	public void load(GL10 gl){
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
		if(materials != null) {
			for(Material material : materials) {
				material.loadMaterial(gl);
				log.pl("SGAbstractShape", "loadTexture(gl)");
			}
		}
	}
	
	public void setHasTexture(boolean b) {
		hasTexture = b;
	}
	
	public boolean hasTexture() {
		return hasTexture;
	}
}
