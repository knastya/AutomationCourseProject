package com.example.teamcity.ui;

import com.example.teamcity.ui.pages.LoginPage;
import com.example.teamcity.ui.pages.StartupPage;
import org.testng.annotations.Test;

public class SetupTest extends BaseUiTest {

    @Test
    public void startUpTest() {
        new StartupPage()
                .open()
                .setUpTeamcity();
        new LoginPage()
                .checkHeader("Create Administrator Account");
    }
}
