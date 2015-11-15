package com.mediaplayer.com;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.mediaplayer.manager.BroadcastManager;
import com.mediaplayer.manager.NotificationHelper;

/**
 * Implementation of App Widget functionality.
 */
public class NotificationService extends Service {

    NotificationHelper notificationHelper;

    @Override
    public void onCreate() {
        registerReceiver(notificationReceiver, new IntentFilter((BroadcastManager.NOTIFICATION_PLAY)));
        registerReceiver(notificationReceiver, new IntentFilter((BroadcastManager.NOTIFICATION_PAUSE)));
        registerReceiver(notificationReceiver, new IntentFilter((BroadcastManager.NOTIFICATION_NEXT)));
        registerReceiver(notificationReceiver, new IntentFilter((BroadcastManager.NOTIFICATION_PREV)));
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
                switch (action){
                    case BroadcastManager.NOTIFICATION_PAUSE:
                        SongsManager.getInstance().pause();
                        break;
                    case BroadcastManager.NOTIFICATION_PLAY:
                        SongsManager.getInstance().play();
                        break;
                    case BroadcastManager.NOTIFICATION_NEXT:
                        SongsManager.getInstance().playNextSong();
                        break;
                    case BroadcastManager.NOTIFICATION_PREV:
                        SongsManager.getInstance().playPreviousSong();
                        break;
                    case BroadcastManager.NOTIFICATION_RESUME:
                        SongsManager.getInstance().resume();
                        break;
                }
            if(SongsManager.getInstance().getCurrentSongInfo()!=null){
                if(notificationHelper!=null) notificationHelper.notificationCancel();
                notificationHelper = new NotificationHelper(NotificationService.this);
            }
        }
    };
}
