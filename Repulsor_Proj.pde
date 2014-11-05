ArrayList<Repulsor> rep = new ArrayList<Repulsor>(); 
float r = 10; 

void setup() {
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

void draw() {
	background(0);
	for (int i = rep.size()-1; i >= 0; i--) {
		Repulsor op = rep.get(i);
		op.update();
	}
}

void mousePressed() {
	for (int i = rep.size()-1; i >= 0; i--) {
		Repulsor op = rep.get(i);
		boolean is = true;
		op.isReversed(is);
	}
}

void mouseReleased() {
	for (int i = rep.size()-1; i >= 0; i--) {
		Repulsor op = rep.get(i);
		boolean is = false;
		op.isReversed(is);
	}
}