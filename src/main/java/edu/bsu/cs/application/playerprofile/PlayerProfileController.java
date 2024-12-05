package edu.bsu.cs.application.playerprofile;

import edu.bsu.cs.records.PlayerStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class PlayerProfileController {
    @FXML private Label userNameDisplayLabel;
    @FXML private Label typeDisplayLabel;
    @FXML private Hyperlink weblinkHyperlink;
    @FXML private Hyperlink twitchHyperlink;
    @FXML private Hyperlink hitBoxHyperlink;
    @FXML private Hyperlink youtubeHyperlink;
    @FXML private Hyperlink twitterHyperlink;
    @FXML private Hyperlink speedrunsLiveHyperlink;

    public void inputPlayerInformation(PlayerStorage playerStorage){
        userNameDisplayLabel.setText(playerStorage.name());
        weblinkHyperlink.setText(playerStorage.weblink());
        typeDisplayLabel.setText(playerStorage.type());
        twitchHyperlink.setText(playerStorage.socialMedias().get("twitch"));
        hitBoxHyperlink.setText(playerStorage.socialMedias().get("hitbox"));
        youtubeHyperlink.setText(playerStorage.socialMedias().get("youtube"));
        twitterHyperlink.setText(playerStorage.socialMedias().get("twitter"));
        speedrunsLiveHyperlink.setText(playerStorage.socialMedias().get("speedrunsLive"));
    }

    public void openLinkAction(ActionEvent actionEvent) throws IOException {
        Desktop.getDesktop().browse(URI.create(((Hyperlink) actionEvent.getSource()).getText()));
    }
}
