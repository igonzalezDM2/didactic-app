package com.example.didactic_app.wordsearch.di.component;


import com.example.didactic_app.wordsearch.di.modules.AppModule;
import com.example.didactic_app.wordsearch.di.modules.DataSourceModule;
import com.example.didactic_app.wordsearch.features.FullscreenActivity;
import com.example.didactic_app.wordsearch.features.gameover.GameOverActivity;
import com.example.didactic_app.wordsearch.features.gameplay.GamePlayActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by abdularis on 18/07/17.
 */

@Singleton
@Component(modules = {AppModule.class, DataSourceModule.class})
public interface AppComponent {

    void inject(GamePlayActivity activity);

//    void inject(MainMenuActivity activity);

    void inject(GameOverActivity activity);

    void inject(FullscreenActivity activity);

//    void inject(GameHistoryActivity activity);

}
