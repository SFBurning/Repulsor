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

ArrayList<Repulsor> rep = new ArrayList<Repulsor>(); 
float r = 10; 

public void setup() {
	frameRate(60);
	size(1200,1200);
	for(float i = 0; i < round(width/r); i++) {
		for(float j = 0; j < round(height/r); j++) {
			println("adding a repulsor"); 
			Repulsor tempRep = new Repulsor(i * r + r/2, j * r + r/2, r);
			rep.add(tempRep);
		}
	}
}

public void draw() {
	background(0);
	for (int i = rep.size()-1; i >= 0; i--) {
		Repulsor op = rep.get(i);
		op.update();
	}
}

public void mousePressed() {
	for (int i = rep.size()-1; i >= 0; i--) {
		Repulsor op = rep.get(i);
		boolean is = true;
		op.isReversed(is);
	}
}

public void mouseReleased() {
	for (int i = rep.size()-1; i >= 0; i--) {
		Repulsor op = rep.get(i);
		boolean is = false;
		op.isReversed(is);
	}
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
boolean reversed = false;

	public void isReversed(boolean is) {
		reversed = is; 
	}

	Repulsor(float x_, float y_, float r_) {
		// CONTANTS 
		range = 200; 
		maxSpeed = 10; 
		maxForce = 100; 
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
		if (distance <= range) {
			// Normalize that shit!
			desired.normalize();
			desired.mult(-1);
			
			// Check if distance is in range
			float m = map(distance, 0, range, maxSpeed, 0); 
			desired.mult(m);
			PVector steer = PVector.sub(desired, vel);

			steer.limit(maxForce);
			applyForce(steer); 
			move(); 
			display(); 
		} else {
			// The repulsor is not being influenced by the mouse, and must return home
			desired = PVector.sub(restPos, pos); // Point towards the rest pos
			distance = desired.mag(); // Get the distance
			if (distance <= 10) {
				pos = restPos.get();
			} else {
				desired.normalize(); // normalize it! 
				float m = map(distance, 0, range, 0, maxSpeed); // get the mapping function ready
				desired.mult(m); 
				PVector steer = PVector.sub(desired, vel);
				steer.limit(maxForce);
				applyForce(steer); 
				move(); 
				display(); 
			}
		}

		// So that was fun. 
		// Now let's steer this sucker. 

		// Zero out the acceleration

		//println(pos.x + ", " + pos.y);

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
