package com.example.notificationwithrecycleview;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationwithrecycleview.databinding.ActivityMainBinding;

import java.util.ArrayList;

//ne7na badna na3ml recycele view no3red fe asme2 w bas nekbous 3ala wa7de mena na3mel notification badna na3mel tasmem notification howe nafso l recycle view bas la yezbat costume layout notification mamno3 est3mel wala she wala view ykoun bel android metl msln constaint a fene

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<User> arrayList;
    UserAddpater userAddpater;
    ActivityResultLauncher<String> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {

            }
        });
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
        arrayList=new ArrayList<>();
        arrayList.add(new User(R.drawable.baseline_supervised_user_circle_24,"khaled assidi","kaa@hotmail.com",700));
        arrayList.add(new User(R.drawable.baseline_verified_user_24,"ahamd daher","ahamd@hotmail.com",800));
        userAddpater=new UserAddpater(this, arrayList, new onclick() {
            @Override
            public void onclickuser(User user) {
                if(user!=null){
                    shownotification(user);

                }
            }
        });

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        binding.recycleView.setLayoutManager(layoutManager);
        binding.recycleView.setAdapter(userAddpater);

    }


    private void shownotification(User user) {
        String channelID="CHA101";
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,channelID);
        builder.setOngoing(true);
        builder.setOnlyAlertOnce(true);
        builder.setContentIntent(pendingIntent);
        //hal2 bade 2a3ml design ta3ele bade est3mel method esma setcontent bt5od remote views
        //ta3amoul ma3 l views metl textview,button..etc ta3amoul ma3a mn de5el notification  3an tare2 remote views ma byenfa3 ta3mela inflate mobasharatan lesh l2no l notification ma7touta barat l application ta3elak le byet3mel ma3 notification mesh application ta3elak application b2lb system
        //fa ba3mel method btrje3 remoteviews object howe 3ebra 3an class bemasel l 3anaser le badna net3emal ma3a bel notification ya3ne aye 3osnour badak defo b tasmem 5as b notification byet7awal la7alo la object remote views 2aw layout kola lama ta3mela inflate 3ala l notification btet7ewal la object mn no3 remote views l2no mn barat l application ta3elak jowet l application ta3elak btet7wal la view l inflate ama barat application ta3elak byet7awal la remoteviews l2no btem ta7akoum feha remotly
        //getcostumedesign l method l 3am 2a3mela 3ebara 3an method btest2bel object l user w bt3belak bayneto b2lb costume design w btrj3lk yeh ya3ne metl l getview le kona nest3mela bel listview
        builder.setCustomContentView(getcostumDesign(user));
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setSmallIcon(R.drawable.baseline_supervised_user_circle_24);
        //hala2 3shen e3roud notification bade estd3e l mouder 2aw l application le bt3roud l ntifications w t2olo 5od hay notification w 3rouda
        //keef bade estd3e bestd3e l notificationmanger class heda l mouder le mn5eleo bslmo l notification by3rouda
//        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);:hyde bestd3e l get system 3ashen jeeb aye serviece mawjoude bel system ya3ne badak tosal la bloototh,wifi la aye sha8le b2lb l system application bel system 2aw service mn 5elela fa ana bhay l 7ale 3am jeeb service le bt3roud notification le heye b2lb system heye metl k2no application 3am jeba
//            w b2olo manger.notify() hyde 3ashn yreda ba3te l id w b3te l builder.build 3ashn ya3mel build la notification w yr3ele yeha
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //la sho heda l id 3ashn ykoun ra2m lal notification w ra2m heda momken ykoun unique w moken tkarero sho le byefre2 ma3ak
        //iza 3ardt 10 notification koloun b nafs l id la7 yen3rdo l 10 b nafs l maken ya3ne 7a yseer yshel l notification l ademe w y7ot wa7de jdede
        //law kel wa7de ra2m mo5talef la7 ye3roudn ta7t ba3d

        //ba3d 3ande l notification channel:
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(channelID,"users",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

        }
        manager.notify(1,builder.build());



    }

    private RemoteViews getcostumDesign(User user){
        //bade 2a3ml inflate lal costume design bel 3anaser le feha w t3be l data b2lba le mn l user w traje3 shshe jehze 3ashn t3beha b setcontent(btestd3e l method w bt3tha l user) fa shshe 7a tejek kemle design 7a tetrkab 3ala l notification setcontnt berkeb l costume design 3ala l notification

        //fa b3mel object mn Remoteviews bade 2a3mlo inflate 2elo w lal 3anser le jowe bel binding akked la2 ,bel inflate l 3ade la2 2alak bel remote views
        //bt3ml obejt mn remoteview w bt3te bel costructor esm l package tabe3 l application tyeb lesh bye5od esm l package l2no metl ma 2olna notification mesh 3am ten3ered 3ala application ta3etna 3am ten3ered b application tene fa l application le mn bara lezm ya3ref esm package ba3den ye5od layout le badna n7oto 3ala l application R.layout.costumeitem
        //l2no application le byen3ered fe l notification application 8areb mawjoud bel system fa bado yaref l packgename w l esm layout 3ashn ya3ref yosal la heda layout b2lb hyda l application yosal la tasmem le b2lb heda l application w ya3melo inflate
        RemoteViews remoteViews=new RemoteViews(getApplicationContext().getPackageName(),R.layout.costume_user_item);

        //hala2 ma mne2dar n2olo remoteviews.settextview la2 ma mne2dar lesh lezm test5dem method esma settextviewtext() btrou7 enta bt3te  id taba3 textview tab3tak w l paramter tene taba3 hyde l method l value le bada tet3aba bel heda text
        if(user!=null) {
            remoteViews.setTextViewText(R.id.user_title, user.getName());
            remoteViews.setTextViewText(R.id.use_email, user.getEmail());
            remoteViews.setTextViewText(R.id.use_salary, user.getSalary() + "$");
            remoteViews.setImageViewResource(R.id.use_image_view, user.getImage());
        }
        return remoteViews;
    }
}