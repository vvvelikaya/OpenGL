package lab1_opengl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class EventListener implements GLEventListener {

	public static float xx;
	public static float unitsH;

	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glLineWidth(1);
		gl.glLoadIdentity();
		String s = "";
		float[] coordinates = new float[8];

		JSONObject jsonObject;
		try {
			jsonObject = JSONparser.readFile("rabbit.json");
			Set<String> itr = jsonObject.keySet();

			Iterator<String> it = itr.iterator();
			while (it.hasNext()) {

				s = it.next();
				// System.out.println(s);

				if (checkForWord(s, "polygon")) {
					coordinates = JSONparser.getArray(s, jsonObject);
					drawPolygon(coordinates, gl);
					drawLine4(coordinates, gl);
				} else if (checkForWord(s, "quad")) {
					coordinates = JSONparser.getArray(s, jsonObject);
					drawQuad(coordinates, gl);
					drawLine4(coordinates, gl);
				} else if (checkForWord(s, "triangle")) {
					coordinates = JSONparser.getArray(s, jsonObject);
					drawTriangle(coordinates, gl);
					drawLine3(coordinates, gl);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.glFlush();

	}

	private void drawPolygon(float[] coordinates, GL2 gl) {

		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(0.5961f, 0.7961f, 0.0f);
		for (int i = 0; i < 7; i = i + 2) {
			gl.glVertex2f(coordinates[i] + xx, coordinates[i + 1]);
		}
		gl.glEnd();

	}

	private void drawQuad(float[] coordinates, GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(0.5961f, 0.7961f, 0.0f);
		for (int i = 0; i < 7; i = i + 2) {
			gl.glVertex2f(coordinates[i] + xx, coordinates[i + 1]);

		}
		gl.glEnd();
	}

	private void drawTriangle(float[] coordinates, GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f(0.5961f, 0.7961f, 0.0f);
		for (int i = 0; i < 6; i = i + 2) {
			gl.glVertex2f(coordinates[i] + xx, coordinates[i + 1]);
		}
		gl.glEnd();
	}

	private void drawLine3(float[] coordinates, GL2 gl) {
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor3f(0.0f, 0.0f, 0);
		for (int i = 0; i < 6; i = i + 2) {
			gl.glVertex2f(coordinates[i] + xx, coordinates[i + 1]);
		}
		gl.glEnd();

	}

	private void drawLine4(float[] coordinates, GL2 gl) {
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor3f(0.0f, 0.0f, 0);
		for (int i = 0; i < 7; i = i + 2) {
			gl.glVertex2f(coordinates[i] + xx, coordinates[i + 1]);
		}
		gl.glEnd();

	}

	boolean checkForWord(String line, String word) {
		return line.contains(word);
	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	public void init(GLAutoDrawable drawable) {
		System.out.println("init() called");
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(253.0f / 255.0f, 160.0f / 255.0f, 200.0f / 255.0f, 0.0f);

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

		gl.glOrtho(-Renderer.unitsW, Renderer.unitsW, -unitsH, unitsH, -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

	}

}
