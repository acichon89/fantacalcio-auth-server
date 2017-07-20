package com.javangarda.fantacalcio.authserver.application.internal.storage;


public enum AccountRole {
    ROLE_BASIC,
    ROLE_PREMIUM,
    ROLE_ADMIN,
    ROLE_SUPERADMIN,
    ROLE_MODERATOR;

    public static boolean isValid(String roleName){
        try {
            AccountRole.valueOf(roleName);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
