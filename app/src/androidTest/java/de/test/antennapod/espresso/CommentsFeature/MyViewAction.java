package de.test.antennapod.espresso.CommentsFeature;

import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

class MyViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(android.support.test.espresso.UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }

        };
    }

}
