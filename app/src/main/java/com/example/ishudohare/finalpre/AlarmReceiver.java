package com.example.ishudohare.finalpre;

/**


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
       Toast.makeText(context, "Alarm", Toast.LENGTH_SHORT).show();
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification); r.play();
//        String msg;
//        String id = intent.getStringExtra("id");
//        String type = intent.getStringExtra("type");
//        String time = intent.getStringExtra("time");
//        String date = intent.getStringExtra("date");
////        if (type.equals("1")) {
////            msg = "Care UK appointment";
////        } else {
////            msg = "Exercise Reminder";
////        }
////        for (int i = 0; i < PP.parse2.size(); i++) {
////            if (PP.parse2.get(i).id.equals(id)) {
////                PP.parse2.remove(i);
////                PP.saveReminder();
////                break;
////            }
////        }
//        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        //CharSequence from = msg;
//        CharSequence message = time + " on " + date;
//        PendingIntent contentIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
//        am.cancel(contentIntent);
//        @SuppressWarnings("deprecation") Notification notif = new Notification(R.drawable.clock, "yo", System.currentTimeMillis());
//       // notif.setLatestEventInfo(context, from, message, contentIntent);
//        notif.flags |= Notification.FLAG_AUTO_CANCEL;
//        nm.notify(1, notif);
//        //nm.cancel(1);
//        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        // Vibrate for 500 milliseconds
//        v.vibrate(2000);
//        playSound(this, getAlarmUri());
//    }
//
//    private void playSound(AlarmReceiver context, Uri alert) {
//        final MediaPlayer mMediaPlayer = new MediaPlayer();
//        try {
//            mMediaPlayer.setDataSource(context, alert);
//            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
//                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
//                mMediaPlayer.prepare();
//                mMediaPlayer.start();
//                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mMediaPlayer.stop();
//                        mMediaPlayer.release();
//                    }
//                });
//            }
//        } catch (IOException e) {
//            System.out.println("OOPS");
//        }
//    } // Get an alarm sound. Try for an alarm. If none set, try notification, // Otherwise, ringtone
//
//    private Uri getAlarmUri() {
//        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alert == null) {
//            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            if (alert == null) {
//                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//            }
//        }
//         return alert; }

      //  context.startService(new Intent(context,AlarmRing.class));
        Bundle recv= intent.getExtras();
        String subname=recv.getString("subname");
        String time=recv.getString("time");
    Notification(context, "Reminder for "+subname,time);
    }
    public void Notification(Context context, String message,String time) { // Set Notification Title
     String strtitle = "Hello";
     // Open NotificationView Class on Notification Click
     Intent intent = new Intent(context, Overall.class);
     // Send data to NotificationViewClass
     //intent.putExtra("title", strtitle);
       // intent.putExtra("text", message);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context);
         // Set Icon .
         builder.setSmallIcon(R.drawable.clock);

         // Set Ticker Message
         builder.setTicker("Alarm is set") ;
         // Set Title
         builder.setContentTitle(message);
          // Set Text
        String filename=settings.filename;

        builder.setContentText(time+" minutes to start");
        builder.setColor(Color.BLUE);
         //  builder.addAction(R.drawable.rightarrow, "Action Button", pIntent);
        //builder.addAction(R.drawable.rightarrow,"Action Button",pIntent);
        // Add an Action Button below Notification
          // builder.addAction(R.drawable.tticon, "Action Button", pIntent);
            // Set PendingIntent into Notification
            builder.setContentIntent(pIntent) ;
            // Dismiss Notification
            builder.setAutoCancel(true);
        //builder.setDefaults(Notification.DEFAULT_SOUND);
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        builder.setSound(alert);
             // Create Notification Manager
             NotificationManager notificationmanager = (NotificationManager) context .getSystemService(Context.NOTIFICATION_SERVICE);
              // Build Notification with Notification Manager
        Random x=new Random();

        notificationmanager.notify(x.nextInt(), builder.build());
    }


    // Check for network availability private boolean isNetworkAvailable(Context context) { ConnectivityManager connectivityManager = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE); NetworkInfo activeNetworkInfo = connectivityManager .getActiveNetworkInfo(); return activeNetworkInfo != null; }
public android.support.v4.app.NotificationCompat.Action yo(){
    System.out.println("clicked button");
    return null;
}
}
