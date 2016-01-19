package com.a_caring_reminder.app.texts;

import android.support.annotation.NonNull;

import com.a_caring_reminder.app.models.Text;

import java.util.List;

/**
 * Created by darrankelinske on 1/16/16.
 */
public class TextsContract {

    interface View {

//        void setProgressIndicator(boolean active);

        void showTexts(List<Text> texts);

        void showAddText();

        void showTextDetailUi(String textId);
    }

    interface UserActionsListener {

        void loadTexts(boolean forceUpdate);

        void addNewText();

        void openTextDetails(@NonNull Text requestedText);
    }

}
