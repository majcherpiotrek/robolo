package com.ksm.robolo.roboloapp.events;

import com.ksm.robolo.roboloapp.tos.UserTO;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserTO user;

    public OnRegistrationCompleteEvent(UserTO user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public UserTO getUser() {
        return user;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }
}
