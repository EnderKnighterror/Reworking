package re.working.reworking;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CoinAnimation {

    private final Cylinder coin;
    private final RotateTransition rotateTransition;
    private final PhongMaterial material;
    private final Image coinHeadsTexture;
    private final Image coinTailsTexture;
    private final Timeline spinningTimeline;

    public CoinAnimation () {
        material = new PhongMaterial();
        coinHeadsTexture = new Image(getClass().getResourceAsStream("heads.png"));
        coinTailsTexture = new Image(getClass().getResourceAsStream("tails.png"));
        coin = createCoin();
        spinningTimeline = createSpinningTimeline();
        rotateTransition = createRotation();
    }
    private Cylinder createCoin() {
        Cylinder newCoin = new Cylinder(100, 5);
        material.setDiffuseMap(coinHeadsTexture);
        newCoin.setMaterial(material);
        return newCoin;
    }

    private RotateTransition createRotation() {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), coin);
        rotateTransition.setAxis(Rotate.X_AXIS);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        return rotateTransition;
    }

    private Timeline createSpinningTimeline() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(coin.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(3), new KeyValue(coin.rotateProperty(), 360))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double angle = coin.getRotate();
            if (angle >= 0 && angle < 180) {
                material.setDiffuseMap(coinTailsTexture);
            } else {
                material.setDiffuseMap(coinHeadsTexture);
            }
        });
        return timeline;
    }

    public void startAnimation() {
        spinningTimeline.play();
    }

    public  void stopAnimation(boolean headsUp) {
        spinningTimeline.stop();
        rotateTransition.setFromAngle(coin.getRotate());
        rotateTransition.setToAngle(headsUp ? 0 : 180);
        rotateTransition.play();
    }

    public Cylinder getCoin() {
        return coin;
    }

    public boolean isHeadsup() {
        double rotation = coin.getRotate() % 360;
        return (rotation < 90 || rotation > 270);
    }
}
