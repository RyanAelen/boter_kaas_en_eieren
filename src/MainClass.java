import processing.core.PApplet;
import processing.core.PVector;

public class MainClass extends PApplet {
    public static void main(String[] args) {
        PApplet.main("MainClass", args);
    }

    PVector posistion;
    PVector grote;
    boolean erIsEenWinner;
    byte[][] game;
    final byte NIEMAND = 0;
    final byte PLAYERONE = 1;
    final byte PLAYERTWOO = 2;
    boolean playerOneAanDeberut;
    byte beurtenover;

    @Override
    public void settings() {
        size(400, 400);
        smooth(8);
    }

    @Override
    public void setup() {
        resetSpel();
        posistion = new PVector(100, 100);
        grote = new PVector(800, 800);
        surface.setTitle("Boter Kaas en Eieren");
    }

    @Override
    public void draw() {
        background(255);
        tekenraster();
        mousepostion();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game[i][j] == PLAYERONE) {
                    tekenKruis(i, j);
                } else if (game[i][j] == PLAYERTWOO) {
                    tekenkring(i, j);
                }
            }
        }
        if (erIsEenWinner) {
            if (beurtenover == 0) {
                displayWinner(checkWinner());
            }
            displayWinner(checkWinner());
        }
    }

    @Override
    public void mousePressed() {
        if (!erIsEenWinner) {
            Paar paar = mousepostion();
            if (paar != null) {
                if (game[paar.getX()][paar.getY()] == NIEMAND) {
                    game[paar.getX()][paar.getY()] = playerOneAanDeberut ? PLAYERONE : PLAYERTWOO;
                    playerOneAanDeberut = !playerOneAanDeberut;
                    beurtenover--;
                }
            }
        } else {
            if (mouseX >= posistion.x + (grote.x / 3f) && mouseX < posistion.x + (grote.x / 3f) * 2 && mouseY >= posistion.y + (grote.y / 3f) && mouseY < posistion.y + (grote.y / 3f) * 2) {
                resetSpel();
            }
        }
        if (checkWinner() != NIEMAND) {
            erIsEenWinner = true;
        }
        if (beurtenover == 0) {
            erIsEenWinner = true;
        }

    }

    private void resetSpel() {
        game = new byte[3][3];
        playerOneAanDeberut = true;
        erIsEenWinner = false;
        beurtenover = 9;
    }

    private void displayWinner(byte winner) {
        push();
        rectMode(CENTER);
        rect(posistion.x + grote.x / 2f, posistion.y + grote.y / 2f, grote.x / 5f, grote.y / 5f);
        textSize(32);
        textAlign(CENTER, CENTER);
        fill(255);
        if (winner == NIEMAND) {
            text("Tie", posistion.x + grote.x / 2f, posistion.y + grote.y / 2f);
        } else {
            text(winner == 1 ? "kruis" : "kring", posistion.x + grote.x / 2f, posistion.y + grote.y / 2f);
        }

        pop();

    }

    /**
     * @return 1 of 2 bij winner 0 bij geen winner
     */
    private byte checkWinner() {
        for (int i = 1; i < 3; i++) {
//            check diogonaal
            if ((game[0][0] == i && game[1][1] == i && game[2][2] == i) || (game[2][0] == i && game[1][1] == i && game[0][2] == i)) {
                return (byte) i;
            }
//            check horisontaal en vertiekaal
            for (int j = 0; j < 3; j++) {
                if ((game[j][0] == i && game[j][1] == i && game[j][2] == i) || (game[0][j] == i && game[1][j] == i && game[2][j] == i)) {
                    return (byte) i;
                }
            }
        }
        return NIEMAND;
    }

    public Paar mousepostion() {
        for (byte i = 0; i < 3; i++) {
            if (mouseX >= posistion.x && mouseX < posistion.x + (grote.x / 3f) + (grote.x / 3f) * i) {
                for (byte j = 0; j < 3; j++) {
                    if (mouseY >= posistion.y && mouseY < posistion.y + (grote.y / 3f) + (grote.y / 3f) * j) {
                        fill(255, 0, 0);
                        noStroke();
                        return new Paar(i, j);
                    }
                }
            }
        }
        return null;
    }

    public void tekenKruis(int x, int y) {
        push();
        rectMode(CENTER);
        fill(0, 255, 0);
        noStroke();
        translate(posistion.x + (grote.x / 6f) + (grote.x / 3f * x), posistion.y + (grote.y / 6f) + (grote.y / 3f * y));
        rotate(radians(45));
        rect(0, 0, grote.x / 3f, grote.y / 18f);
        rotate(radians(90));
        rect(0, 0, grote.x / 3f, grote.y / 18f);
        pop();
    }

    public void tekenkring(int x, int y) {
        push();
        ellipseMode(CENTER);
        fill(0, 0, 255);
        translate(posistion.x + (grote.x / 6f) + (grote.x / 3f * x), posistion.y + (grote.y / 6f) + (grote.y / 3f * y));
        stroke(0, 0, 255);
        strokeWeight(grote.x / 20f);
        noFill();
        circle(0, 0, grote.x / 4f);
        pop();
    }

    public void tekenraster() {
        stroke(0);
        fill(0);
        for (int i = 1; i < 3; i++) {
            line(posistion.x + (grote.x / 3f) * i, posistion.y, posistion.x + (grote.x / 3f) * i, posistion.y + (grote.y));
            line(posistion.x, posistion.y + (grote.y / 3f) * i, posistion.x + grote.x, posistion.y + (grote.y / 3f) * i);
        }

    }

    static final class Paar {
        byte x, y;

        Paar(byte i, byte j) {
            x = i;
            y = j;
        }

        byte getX() {
            return x;
        }

        byte getY() {
            return y;
        }
    }
}

