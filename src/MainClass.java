import processing.core.PApplet;

public class MainClass extends PApplet {
    public static void main(String[] args) {
        PApplet.main("MainClass", args);
    }

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
            int[] arr = mousepostion();
            if (game[arr[0]][arr[1]] == NIEMAND) {
                game[arr[0]][arr[1]] = playerOneAanDeberut ? PLAYERONE : PLAYERTWOO;
                playerOneAanDeberut = !playerOneAanDeberut;
                beurtenover--;
            }
        } else {
            if (mouseX >= width / 3f && mouseX < width / 3f * 2 && mouseY >= height / 3f && mouseY < height / 3f * 2) {
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
        rect(width / 2f, height / 2f, width / 5f, height / 5f);
        textSize(32);
        textAlign(CENTER, CENTER);
        fill(255);
        if (winner == NIEMAND) {
            text("Tie", width / 2f, height / 2f);
        } else {
            text(winner == 1 ? "kruis" : "kring", width / 2f, height / 2f);
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

    public int[] mousepostion() {
        for (int i = 0; i < 3; i++) {
            if (mouseX >= 0 && mouseX < width / 3f + width / 3f * i) {
                for (int j = 0; j < 3; j++) {
                    if (mouseY >= 0 && mouseY < height / 3f + height / 3f * j) {
                        fill(255, 0, 0);
                        noStroke();
                        return new int[]{i, j};
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
        translate((width / 6f) + (width / 3f * x), (height / 6f) + (height / 3f * y));
        rotate(radians(45));
        rect(0, 0, 90, 20);
        rotate(radians(90));
        rect(0, 0, 90, 20);
        pop();
    }

    public void tekenkring(int x, int y) {
        push();
        ellipseMode(CENTER);
        fill(0, 0, 255);
        translate((width / 6f) + (width / 3f * x), (height / 6f) + (height / 3f * y));
        stroke(0, 0, 255);
        strokeWeight(15);
        noFill();
        circle(0, 0, 90);
        pop();
    }

    public void tekenraster() {
        stroke(0);
        fill(0);
        for (int i = 1; i < 3; i++) {
            line(width / 3f * i, 0, width / 3f * i, height);
            line(0, height / 3f * i, width, height / 3f * i);
        }

    }
}

