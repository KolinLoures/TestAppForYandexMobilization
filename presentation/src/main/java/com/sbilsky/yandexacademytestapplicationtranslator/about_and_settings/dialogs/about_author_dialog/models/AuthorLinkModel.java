package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_author_dialog.models;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AuthorLinkModel {
    private int iconId;
    private String name;
    private String link;

    public AuthorLinkModel(int iconId, String name, String link) {
        this.iconId = iconId;
        this.name = name;
        this.link = link;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
