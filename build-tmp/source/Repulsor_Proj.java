import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Repulsor_Proj extends PApplet {

Repulsor rep; 

public void setup() {
	frameRate(15);
	size(1200,1200);
	rep = new Repulsor(width/2, height/2, 100);
}

public void draw() {
	background(0);
	rep.update(); 
}

class Repulsor {

PVector pos;
final PVector restPos; 
PVector vel; 
PVector accel; 
float r; // Radius
float range; // Range sensitivity to mouse
float maxSpeed; 
float maxForce; 
float mass;

	Repulsor(float x_, float y_, float r_) {
		// CONTANTS 
		range = 200; 
		maxSpeed = 10; 
		maxForce = 10; 
		mass = 1;
		// Initialization
		pos = new PVector(x_, y_);
		restPos = new PVector(x_, y_);
		r = r_;
		vel = new PVector(0,0);
		accel = new PVector(0,0);
	}

	public void applyForce(PVector force) {
		PVector f = force.get(); 
		f.div(mass); 
		accel.add(f); 
	}

	// Generic move function
	public void move() {
		vel.add(accel); 
		pos.add(vel); 
		accel.mult(0); 
	}

	public void display() {
		// Blueish and a little transparent
		fill(131, 161, 255, 170);
		// draw the ellipse
		ellipseMode(CENTER);
		ellipse(pos.x, pos.y, r, r);
	}

	public void update() {
		PVector mousePos = new PVector(mouseX, mouseY); // Get mouse pos as PVector
		// Create a new PVector that points to the mouse
		PVector desired = PVector.sub(mousePos, pos);

		// Also create a scalar value to hold the magnitude of the vector
		float distance = desired.mag(); 
		// Normalize that shit!
		desired.normalize();
		// Multiply by -1 to reverse direction
		desired.mult(-1);
		// Check if distance is in range
		if (distance <= range) {
			float m = map(distance, 0, range, maxSpeed, 0); 
			desired.mult(m);
		} else {
			// The repulsor is not being influenced by the mouse, but is it being 
			// Multiply by max speed to temper the beast of motion!
			desired.mult(maxSpeed);
		}

		// So that was fun. 
		// Now let's steer this sucker. 
		PVector steer = PVector.sub(desired, vel);

		steer.limit(maxForce);
		applyForce(steer); 
		// Zero out the acceleration
		move(); 
		display(); 
		println(pos.x + ", " + pos.y);

	}



}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Repulsor_Proj" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
