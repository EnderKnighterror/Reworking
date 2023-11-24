
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
