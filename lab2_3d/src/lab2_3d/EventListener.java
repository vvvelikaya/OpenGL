package lab2_3d;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.sun.prism.impl.BufferUtil;

public class EventListener implements GLEventListener {

	public static float unitsH;
	public static double angleH = 45.0, angleV = 45.0;
	private static final double PI = 3.14159265;

	public static boolean p = true;

	public static float e = 1.0f;

	final int N = 10;
	final float len = 2.0f;
	final float delta = len / (float) N;

	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		final GLU glu = new GLU();
		final GLUT glut = new GLUT();
		if (p) {
			unitsH = Renderer.getHeight() / (Renderer.getWidth() / Renderer.unitsW);
			gl.glMatrixMode(GL2.GL_PROJECTION);
			gl.glLoadIdentity();

			gl.glOrtho(-Renderer.unitsW, Renderer.unitsW, -unitsH, unitsH, -10, 100);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glLoadIdentity();
		} else {
			gl.glMatrixMode(GL2.GL_PROJECTION);
			gl.glLoadIdentity();
			// params: left, right, bottom, top, near, far
			gl.glFrustum(-Renderer.unitsW / 4, Renderer.unitsW / 4, -unitsH / 4, unitsH / 4, Renderer.unitsW / 2,
					4 * Renderer.unitsW);
			gl.glTranslatef(0, 0, -2 * Renderer.unitsW);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glLoadIdentity();
		}

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		glu.gluLookAt(Math.cos(angleV * PI / 180.0) * Math.cos(angleH * PI / 180.0), Math.sin(angleV * PI / 180.0),
				Math.cos(angleV * PI / 180.0) * Math.sin(angleH * PI / 180.0), 0.0, 0.0, 0.0, 0.0, e, 0.0);

		// Torus
		gl.glColor3f(0f, 1f, 1f); // sky blue (blue +green)
		glut.glutWireTorus(2, 4, 20, 30);

		// Cube
		gl.glTranslatef(0.0f, 4.0f, 8.0f);
		gl.glColor3f(0f, 1f, 0f); // green color
		glut.glutSolidCube(2);

		// Plane of dots
		gl.glTranslatef(-6.0f, 0.0f, 0.0f);
		gl.glColor4f(0.5f, 1.f, 1.f, 1.f);
		drawDots(gl);

		gl.glFlush();

	}

	public void drawDots(GL2 gl) {

		FloatBuffer varray;
		float[] array = new float[433];

		int i = 0;
		for (float x = 0.0f; x < len; x += delta)
			for (float y = 0.0f; y < len; y += delta) {
				array[i++] = x;
				array[i++] = y;
				array[i++] = (float) (Math.sin(y) * Math.sqrt(x));
			}

		varray = BufferUtil.newFloatBuffer(array.length);// array to buffer
		for (int j = 0; j < array.length; j++) {
			varray.put(array[j]);
		}
		varray.rewind();

		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL2.GL_FLOAT, 0, varray);
		gl.glPointSize(3.f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glDrawArrays(GL2.GL_POINTS, 0, 100);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(253.0f / 255.0f, 160.0f / 255.0f, 200.0f / 255.0f, 0.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);

		gl.glEnable(GL2.GL_DEPTH_TEST); // depth buffer comparison
		gl.glDepthFunc(GL2.GL_LESS);

		gl.glEnable(GL2.GL_LIGHT0); // lightning
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_LIGHTING);

		float ambient[] = { 0.4f, 0.4f, 0.4f, 1.0f };
		float diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float position[] = { 40.0f, 30.0f, 20.0f, 0.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		if (height <= 0) // avoid a divide by zero error!
		{
			height = 1;
		}

		unitsH = Renderer.getHeight() / (Renderer.getWidth() / Renderer.unitsW);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		gl.glOrtho(-Renderer.unitsW, Renderer.unitsW, -unitsH, unitsH, -10, 100);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

}
