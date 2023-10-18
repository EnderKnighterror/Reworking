package re.working.reworking;

import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CoinAnimation {

    private final Cylinder coin;

    public CoinAnimation() {
        coin = createCoin();
        RotateTransition rotateTransition = createRotation(coin);
        rotateTransition.play();
    }

    private Cylinder createCoin() {
        Cylinder newCoin = new Cylinder(100, 5);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("heads.png")));
        material.setSpecularMap(new Image(getClass().getResourceAsStream("tails.png")));
        newCoin.setMaterial(material);
        return newCoin;
    }

    private RotateTransition createRotation(Cylinder coin) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), coin);
        rotateTransition.setAxis(Rotate.X_AXIS);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        return rotateTransition;
    }
    public Cylinder getCoin() {
        return coin;
    }


    public boolean isHeadsup() {
        double rotation = coin.getRotate() % 360;
        return (rotation < 90 || rotation > 270);
    }
}
