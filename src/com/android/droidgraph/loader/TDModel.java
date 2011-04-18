package com.android.droidgraph.loader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.shape.GLShape;

public class TDModel extends GLShape{
	Vector<Float> v;
	Vector<Float> vn;
	Vector<Float> vt;
	Vector<TDModelPart> parts;
	FloatBuffer vertexBuffer;

	public TDModel(Vector<Float> v, Vector<Float> vn, Vector<Float> vt,
			Vector<TDModelPart> parts) {
		super();
		this.v = v;
		this.vn = vn;
		this.vt = vt;
		this.parts = parts;
	}
	public String toString(){
		String str=new String();
		str+="Number of parts: "+parts.size();
		str+="\nNumber of vertexes: "+v.size();
		str+="\nNumber of vns: "+vn.size();
		str+="\nNumber of vts: "+vt.size();
		str+="\n/////////////////////////\n";
		for(int i=0; i<parts.size(); i++){
			str+="Part "+i+'\n';
			str+=parts.get(i).toString();
			str+="\n/////////////////////////";
		}
		return str;
	}
	
	@Override
	public void draw(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		for(int i=0; i<parts.size(); i++){
			TDModelPart t=parts.get(i);
			Material m=t.getMaterial();
			if(m!=null){
				FloatBuffer a=m.getAmbientColorBuffer();
				FloatBuffer d=m.getDiffuseColorBuffer();
				FloatBuffer s=m.getSpecularColorBuffer();
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,a);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,s);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,d);
			}
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, t.getNormalBuffer());
			gl.glDrawElements(GL10.GL_TRIANGLES,t.getFacesCount(),GL10.GL_UNSIGNED_SHORT,t.getFaceBuffer());
			//gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
	}
	public void buildVertexBuffer(){
		ByteBuffer vBuf = ByteBuffer.allocateDirect(v.size() * 4);
		vBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = vBuf.asFloatBuffer();
		vertexBuffer.put(toPrimitiveArrayF(v));
		vertexBuffer.position(0);
	}
	private static float[] toPrimitiveArrayF(Vector<Float> vector){
		float[] f;
		f=new float[vector.size()];
		for (int i=0; i<vector.size(); i++){
			f[i]=vector.get(i);
		}
		return f;
	}
	@Override
	public BoundingBox getBounds() {
		// TODO Auto-generated method stub
		return null;
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


