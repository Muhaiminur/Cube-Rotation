
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import com.jogamp.opengl.util.Animator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class CubeRotation implements GLEventListener, MouseListener {

    static GLProfile profile = GLProfile.get(GLProfile.GL2);
    static GLCapabilities capabilities = new GLCapabilities(profile);
    // The canvas 
    static GLCanvas glcanvas = new GLCanvas(capabilities);
    public static Animator animator;
    public double theta = 0;

    public static void main(String[] args) {
        //getting the capabilities object of GL2 profile

        CubeRotation c = new CubeRotation();

        //creating frame
        final JFrame frame = new JFrame("CubeRotation");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        glcanvas.addGLEventListener(new CubeRotation());

        CubeRotation s = new CubeRotation();
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setDoubleBuffered(true);
        GLCanvas canvas = new GLCanvas(caps);
        canvas.addGLEventListener(s);
        //JFrame frame = new JFrame("Animating cube");
        frame.setSize(600, 600);
        frame.add(canvas);
        frame.setVisible(true);
        canvas.addGLEventListener(new CubeRotation());
        animator = new Animator(canvas);
        animator.start();

    }

    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        //try {
        //Thread.sleep(10);
        //} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        //}
        gl.glBegin(GL2.GL_POINTS);
        theta += 0.01;
        double[] x = new double[8];
        double[] y = new double[8];
        x[0] = 0;
        y[0] = 0;
        x[1] = 0;
        y[1] = .5;
        x[2] = .5;
        y[2] = .5;
        x[3] = .5;
        y[3] = 0;
        x[4] = 0.25;
        y[4] = 0.25;
        x[5] = 0.25;
        y[5] = 0.75;
        x[6] = 0.75;
        y[6] = 0.75;
        x[7] = 0.75;
        y[7] = 0.25;
        double rx = x[0];
        double ry = y[0];
        for (int i = 0; i < 8; i++) {
            double r = (x[i] - rx) * Math.cos(theta) - (y[i] - ry) * Math.sin(theta);
            y[i] = (x[i] - rx) * Math.sin(theta) + (y[i] - ry) * Math.cos(theta);
            x[i] = r + rx;
            y[i] = y[i] + ry;
        }
        drawLine(gl, x[0], y[0], x[1], y[1]);
        drawLine(gl, x[1], y[1], x[2], y[2]);
        drawLine(gl, x[2], y[2], x[3], y[3]);
        drawLine(gl, x[3], y[3], x[0], y[0]);
        drawLine(gl, x[5], y[5], x[6], y[6]);
        drawLine(gl, x[6], y[6], x[7], y[7]);
        drawLine(gl, x[7], y[7], x[4], y[4]);
        drawLine(gl, x[4], y[4], x[5], y[5]);
        drawLine(gl, x[1], y[1], x[5], y[5]);
        drawLine(gl, x[2], y[2], x[6], y[6]);
        drawLine(gl, x[0], y[0], x[4], y[4]);
        drawLine(gl, x[3], y[3], x[7], y[7]);

        gl.glEnd();

    }

    public void mousePressed(MouseEvent mouse) {
    }

    public void mouseExited(MouseEvent mouse) {
    }

    public void mouseClicked(MouseEvent mouse) {
    }

    public void mouseEntered(MouseEvent mouse) {
    }

    public void mouseReleased(MouseEvent mouse) {
    }

    private void drawLine(GL2 gl, double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx >= 0 && dy >= 0) {
                draw(gl, x1, y1, x2, y2, 0);
            } else if (dx >= 0 && dy <= 0) {
                draw(gl, x1, -y1, x2, -y2, 7);
            } else if (dx <= 0 && dy >= 0) {
                draw(gl, -x1, y1, -x2, y2, 3);
            } else if (dx <= 0 && dy <= 0) {
                draw(gl, -x1, -y1, -x2, -y2, 4);
            }
        } else if (dx >= 0 && dy >= 0) {
            draw(gl, y1, x1, y2, x2, 1);
        } else if (dx >= 0 && dy <= 0) {
            draw(gl, -y1, x1, -y2, x2, 6);
        } else if (dx <= 0 && dy >= 0) {
            draw(gl, y1, -x1, y2, -x2, 2);
        } else if (dx <= 0 && dy <= 0) {
            draw(gl, -y1, -x1, -y2, -x2, 5);
        }
    }

    public void draw(GL2 gl, double x1, double y1, double x2, double y2, int slope) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dne = 2 * dy - 2 * dx;
        double de = 2 * dy;
        double dinit = 2 * dy - dx;
        drawPoint(gl, x1, y1, slope);

        double d1 = y1;
        for (double d = x1 + 0.001; d < x2; d = d + 0.001) {
            if (dinit > 0) {

                d1 = d1 + 0.001;
                drawPoint(gl, d, d1, slope);
                dinit = dinit + dne;
            } else {
                drawPoint(gl, d, d1, slope);
                dinit = dinit + de;
            }
        }
        drawPoint(gl, x2, y2, slope);
    }

    public void drawPoint(GL2 gl, double x, double y, int s) {
        if (s == 0) {
            gl.glVertex2d(x, y);
        } else if (s == 1) {

            gl.glVertex2d(y, x);
        } else if (s == 2) {
            gl.glVertex2d(-y, x);
        } else if (s == 3) {
            gl.glVertex2d(-x, y);
        } else if (s == 4) {
            gl.glVertex2d(-x, -y);
        } else if (s == 5) {
            gl.glVertex2d(-y, -x);
        } else if (s == 6) {
            gl.glVertex2d(y, -x);
        } else if (s == 7) {
            gl.glVertex2d(x, -y);
        }
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // method body
        //4. drive the display() in a loop
        drawable.getGL().setSwapInterval(1);
    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        // method body
    }
    //end of main

}
