package lab3_lighting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class EventListener implements GLEventListener {

	public static float unitsH;
	public static double angleH = 45.0, angleV = 45.0;
	private static final double PI = 3.14159265;
	private float rpoly = 0.0f;

	private static boolean turnOffLighting = false;

	public static float e = 1.0f;

	final int N = 10;
	final float len = 2.0f;
	final float delta = len / (float) N;

	private Texture earthTexture;

	String path = "src/earthmap1k.jpg";

	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		final GLU glu = new GLU();
		final GLUT glut = new GLUT();
		if (turnOffLighting) {
			gl.glDisable(GL2.GL_LIGHTING);
		} else {
			gl.glEnable(GL2.GL_LIGHTING);
		}

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		// params: left, right, bottom, top, near, far
		gl.glFrustum(-Renderer.unitsW / 4, Renderer.unitsW / 4, -unitsH / 4, unitsH / 4, Renderer.unitsW / 2,
				4 * Renderer.unitsW);
		gl.glTranslatef(0, 0, -2 * Renderer.unitsW);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		glu.gluLookAt(Math.cos(angleV * PI / 180.0) * Math.cos(angleH * PI / 180.0), Math.sin(angleV * PI / 180.0),
				Math.cos(angleV * PI / 180.0) * Math.sin(angleH * PI / 180.0), 0.0, 0.0, 0.0, 0.0, e, 0.0);

		// Sun sphere
		gl.glColor3f(1f, 1f, 0f); // sky blue (blue +green)
		glut.glutWireSphere(3, 20, 20);

		earthTexture.enable(gl);
		earthTexture.bind(gl);

		gl.glRotatef(rpoly, 0.0f, 1.0f, 0.0f);
		// Draw sphere.
		gl.glTranslatef(-5.0f, 2.0f, 9.0f);
		gl.glColor3f(1f, 1f, 1f);
		GLUquadric earth = glu.gluNewQuadric();
		glu.gluQuadricTexture(earth, true);
		glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		final float radius = 1.578f;
		final int slices = 20;
		final int stacks = 20;
		glu.gluSphere(earth, radius, slices, stacks);
		glu.gluDeleteQuadric(earth);

		rpoly += 0.5f;

		gl.glFlush();

	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glShadeModel(GL2.GL_SMOOTH);

		// Prepare light parameters
		float SHINE_ALL_DIRECTIONS = 1;// point light
		float[] light_ambient = { 0.4f, 0.2f, 0.2f, 1.0f };// Інтен�?ивні�?ть фонового �?вітла
		float[] light_diffuse = { 1.0f, 1.0f, 1.0f, 1.0f };// Інтен�?ивні�?ть дифузного �?вітла
		float[] light_specular = { 0.8f, 0.8f, 0.8f, 1.0f }; // Інтен�?ивні�?ть дзеркального �?вітла (highlights)
		float[] lightPosition = { 0.0f, 0.0f, 0.0f, SHINE_ALL_DIRECTIONS };// light source position (x, y, z, w)

		// Set light parameters
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);

		// Set material properties
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, 0.3f);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SPECULAR, 0.3f);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);

		// Enable lighting in GL
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_DEPTH_TEST); // depth buffer comparison
		gl.glDepthFunc(GL2.GL_LESS);
		gl.glEnable(GL2.GL_ALPHA_TEST);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);

		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(), 20000, 20000);// timer for turning off lightning

		gl.glEnable(GL2.GL_TEXTURE_2D);

		// Load earth texture.
		try {
			earthTexture = loadTexture(path);
			earthTexture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
			earthTexture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
			earthTexture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			earthTexture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
		} catch (GLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public class MyTimerTask extends TimerTask {

		@Override
		public void run() {

			try {
				turnOffLighting = true;
				Thread.sleep(2000);
				turnOffLighting = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static Texture loadTexture(String file) throws GLException, IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(ImageIO.read(new File(file)), "jpg", os);
		InputStream fis = new ByteArrayInputStream(os.toByteArray());
		return TextureIO.newTexture(fis, true, TextureIO.JPG);
	}

}
