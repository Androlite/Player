package com.ndroidlite.player.custom;

/**
 * Created by sagartahelyani on 23-02-2017.
 */

public enum FontType {

    Regular(1),

    Medium(2),

    SemiBold(3),

    Bold(4);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    FontType(int id) {
        this.id = id;
    }
}
