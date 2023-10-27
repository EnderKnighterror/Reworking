//package re.working.reworking;
//
//import javafx.animation.*;
//import javafx.scene.image.Image;
//import javafx.scene.paint.PhongMaterial;
//import javafx.scene.shape.Cylinder;
//import javafx.scene.transform.Rotate;
//import javafx.util.Duration;
//
//public class CoinAnimation {
//
//    private final Cylinder coin;
//    private final RotateTransition rotateTransition;
//    private final PhongMaterial material;
//    private final Image coinHeadsTexture;
//    private final Image coinTailsTexture;
//    private final Timeline spinningTimeline;
//
//    public CoinAnimation () {
//        material = new PhongMaterial();
//        coinHeadsTexture = new Image(getClass().getResourceAsStream("heads.png"));
//        coinTailsTexture = new Image(getClass().getResourceAsStream("tails.png"));
//        coin = createCoin();
//        spinningTimeline = createSpinningTimeline();
//        rotateTransition = createRotation();
//    }
//    private Cylinder createCoin() {
//        Cylinder newCoin = new Cylinder(100, 5);
//        material.setDiffuseMap(coinHeadsTexture);
//        newCoin.setMaterial(material);
//        return newCoin;
//    }
//
//    private RotateTransition createRotation() {
//        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), coin);
//        rotateTransition.setAxis(Rotate.X_AXIS);
//        rotateTransition.setFromAngle(0);
//        rotateTransition.setToAngle(360);
//        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
//        return rotateTransition;
//    }
//
//    private Timeline createSpinningTimeline() {
//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.ZERO, new KeyValue(coin.rotateProperty(), 0)),
//                new KeyFrame(Duration.seconds(3), new KeyValue(coin.rotateProperty(), 360))
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE);
//
//        timeline.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
//            double angle = coin.getRotate();
//            if (angle >= 0 && angle < 180) {
//                material.setDiffuseMap(coinTailsTexture);
//            } else {
//                material.setDiffuseMap(coinHeadsTexture);
//            }
//        });
//        return timeline;
//    }
//
//    public void startAnimation() {
//        spinningTimeline.play();
//    }
//
//    public  void stopAnimation(boolean headsUp) {
//        spinningTimeline.stop();
//        rotateTransition.setFromAngle(coin.getRotate());
//        rotateTransition.setToAngle(headsUp ? 0 : 180);
//        rotateTransition.play();
//    }
//
//    public Cylinder getCoin() {
//        return coin;
//    }
//
//    public boolean isHeadsup() {
//        double rotation = coin.getRotate() % 360;
//        return (rotation < 90 || rotation > 270);
//    }
//}
package re.working.reworking;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CoinAnimation {
    public static Cylinder coinModel(PhongMaterial material, Image texture, int posX, int posY, int posZ) {
        Cylinder cylinder = new Cylinder(100,5);
        cylinder.setTranslateX(posX); // Position the cylinder
        cylinder.setTranslateY(posY); // Position the cylinder
        cylinder.setTranslateZ(posZ); // Position the cylinder
        cylinder.setRotationAxis(Rotate.X_AXIS); // Set the rotation axis

        // applying coin texture to a material that then gets applied to the cylinder
        material.setDiffuseMap(texture);
        cylinder.setMaterial(material);

        return cylinder;
    }

    public static Image coinTexture(Coin c) {
        Image coinHeadsTexture = new Image("coin_4.png");
        Image coinTailsTexture = new Image("coin_0.png");

        if (c.isHeads()) {
            return coinHeadsTexture;
        } else {
            return coinTailsTexture;
        }
    }

    public static Timeline createSpinningTimeline(
            Cylinder cylinder,
            PhongMaterial material,
            Image textureHeads,
            Image textureTails) {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(cylinder.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(3), new KeyValue(cylinder.rotateProperty(), 360))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double angle = cylinder.getRotate();
            if (angle >= 0 && angle < 180) {
                material.setDiffuseMap(textureTails);
            } else {
                material.setDiffuseMap(textureHeads);
            }
        });
        return timeline;
    }

    public static void handleCoinAnimationStop(
            Timeline timelineOne,
            Timeline timelineTwo,
            Cylinder cylinderOne,
            Cylinder cylinderTwo,
            PhongMaterial materialOne,
            PhongMaterial materialTwo,
            Image coinTextureOne,
            Image coinTextureTwo) {

        timelineOne.stop(); // stop transition
        timelineTwo.stop(); // stop transition

        RotateTransition coin1rotate = new RotateTransition(Duration.seconds(0.5), cylinderOne);
        RotateTransition coin2rotate = new RotateTransition(Duration.seconds(0.5), cylinderTwo);
        coin1rotate.setByAngle(720);
        coin2rotate.setByAngle(720);
        coin1rotate.setFromAngle(90);
        coin2rotate.setFromAngle(90);
        coin1rotate.play();
        coin2rotate.play();

        materialOne.setDiffuseMap(coinTextureOne);
        materialTwo.setDiffuseMap(coinTextureTwo);
    }

    public static String handleGame(Coin coin1, Coin coin2, String selectedBet, String playerName) {
        GameLogic g = new GameLogic();
        return g.playGame(coin1.isHeads(), coin2.isHeads(), selectedBet, playerName);
    }
}
