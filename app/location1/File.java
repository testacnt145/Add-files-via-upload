package com.nearbylocation;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.nearbylocation.dagger.AppComponent;
import com.nearbylocation.dagger.AppModule;
import com.nearbylocation.dagger.DaggerAppComponent;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;

public class App extends Application {

    //MVP + package layering (google samples)
    //https://github.com/googlesamples/android-architecture

    //Clean Architeccture
    //https://fernandocejas.com/2015/07/18/architecting-android-the-evolution/
    //https://github.com/android10/Android-CleanArchitecture/tree/master/presentation/src/main/java/com/fernandocejas/android10/sample/presentation/view

    //dagger
    private AppComponent component;

    public static Context applicationContext;


    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        //Realm
        Realm.init(this);

        //Stetho
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this)
                        )
                        .enableWebKitInspector(
                                RealmInspectorModulesProvider
                                        .builder(this)
                                        .build()
                        )
                        .build());



        //Dagger
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.inject(this);

    }

    public AppComponent getAppComponent() {
        return component;
    }
}
