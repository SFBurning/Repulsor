Repulsor rep; 

void setup() {
	frameRate(15);
	size(1200,1200);
	rep = new Repulsor(width/2, height/2, 100);
}

void draw() {
	background(0);
	rep.update(); 
}

