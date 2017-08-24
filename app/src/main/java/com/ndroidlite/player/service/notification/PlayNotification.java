package com.ndroidlite.player.service.notification;

import com.ndroidlite.player.service.MusicService;

/**
 * Created by chiragpatel on 25-07-2017.
 */

public interface PlayNotification {
    int NOTIFICATION_ID = 1;

    void init(MusicService service);

    void update();

    void stop();
}
