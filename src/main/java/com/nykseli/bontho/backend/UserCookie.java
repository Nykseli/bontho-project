package com.nykseli.bontho.backend;

import javax.servlet.http.Cookie;

import com.vaadin.flow.server.VaadinService;

public class UserCookie {
    public static final String COOKIE_NAME = "BONTHO_LOGIN";

    /**
     * Set the userId to a cookie
     *
     * @param userId id we want to set
     */
    public static void setLoginCookie(Integer userId) {
        Cookie cookie = getCookieByName(COOKIE_NAME);

        // If cookie already exists, update the userId just to be safe
        if (cookie != null) {
            cookie.setValue(userId.toString());
        } else {
            cookie = new Cookie(COOKIE_NAME, userId.toString());
        }

        // Cookie will always last for a month
        cookie.setMaxAge(30 * 24 * 60 * 60);

        // Set the cookie path
        cookie.setPath(VaadinService.getCurrentRequest().getContextPath());

        // Save the cookie
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    /**
     * Try to find the users id from a cookie
     *
     * @return Integer the userId if cookie is found. null if not
     */
    public static Integer getUserId() {
        Cookie cookie = getCookieByName(COOKIE_NAME);

        // If cookie exists, return the value as an Integer
        if (cookie != null) {
            String cookieVal = cookie.getValue();
            try {
                return Integer.valueOf(cookieVal);
            } catch (NumberFormatException err) {
                return null;
            }
        }

        // Just return null if the cookie doesn't exists
        return null;
    }

    /**
     * Destroy the users cookie
     */
    public static void destroyCookie() {
        Cookie cookie = getCookieByName(COOKIE_NAME);

        if (cookie != null) {
            // Setting max age to 0 seconds, makes the cookie invalid
            cookie.setMaxAge(0);
            // Save the cookie
            VaadinService.getCurrentResponse().addCookie(cookie);
        }
    }

    /**
     * Find a Cookie by its name
     *
     * @param name String of the cookies name
     * @return Cookie if found, null if not
     */
    private static Cookie getCookieByName(String name) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        // cookies is null if none is found
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }
}
