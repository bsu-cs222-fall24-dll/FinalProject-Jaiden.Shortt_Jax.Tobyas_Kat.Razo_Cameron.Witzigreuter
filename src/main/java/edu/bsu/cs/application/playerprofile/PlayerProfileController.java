package edu.bsu.cs.application.playerprofile;

import edu.bsu.cs.records.PlayerStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerProfileController {
    @FXML private Label userNameDisplayLabel;
    @FXML private Label typeDisplayLabel;
    @FXML private Label weblinkDisplayLabel;
    @FXML private Label twitchDisplayLabel;
    @FXML private Label hitBoxDisplayLabel;
    @FXML private Label youtubeDisplayLabel;
    @FXML private Label twitterDisplayLabel;
    @FXML private Label speedrunsLiveDisplayLabel;

    public void inputPlayerInformation(PlayerStorage playerStorage){
        userNameDisplayLabel.setText(playerStorage.name());
        weblinkDisplayLabel.setText(playerStorage.weblink());
        typeDisplayLabel.setText(playerStorage.type());
        twitchDisplayLabel.setText(playerStorage.socialMedias().get("twitch"));
        hitBoxDisplayLabel.setText(playerStorage.socialMedias().get("hitbox"));
        youtubeDisplayLabel.setText(playerStorage.socialMedias().get("youtube"));
        twitterDisplayLabel.setText(playerStorage.socialMedias().get("twitter"));
        speedrunsLiveDisplayLabel.setText(playerStorage.socialMedias().get("speedrunsLive"));

    }
}
