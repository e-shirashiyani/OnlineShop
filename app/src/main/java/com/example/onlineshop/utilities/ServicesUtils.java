package com.example.onlineshop.utilities;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.SalesReport;
import com.example.onlineshop.data.repository.OnlineStoreRepository;
import com.example.onlineshop.event.NotificationEvent;
import com.example.onlineshop.view.activity.HomeActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ServicesUtils {
    private static final int NOTIFICATION_ID = 1;

    public static void pollAndShowNotification(Context context, String tag) {

        OnlineStoreRepository repository = new OnlineStoreRepository();
        List<SalesReport> salesReports = repository.fetchSalesReport();

        if (salesReports == null) {
            Log.d(tag, "Items from server not fetched");
            return;
        }

        SalesReport salesReport = salesReports.get(0);
        String serverId = String.valueOf(salesReport.getTotalItems());
//        String serverId = "3";
        String lastSavedId = QueryPreferences.getNumberOfProduct(context);
        if (!serverId.equals(lastSavedId)) {
            Log.d(tag, "show notification");

            sendNotificationEvent(context);
        } else {
            Log.d(tag, "do nothing");
        }

        QueryPreferences.setNumberOfProduct(context, serverId);
    }

    private static void sendNotificationEvent(Context context) {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                HomeActivity.newIntent(context),
                0);

        String channelId = context.getResources().getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getResources().getString(R.string.new_product_title))
                .setContentText(context.getResources().getString(R.string.new_product_text))
                .setSmallIcon(R.drawable.ic_store)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationEvent notificationEvent = new NotificationEvent(NOTIFICATION_ID, notification);
        EventBus.getDefault().post(notificationEvent);
    }

}

