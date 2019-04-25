package com.base.permissions;

/**
 * @author : cuu
 * date    : 2019/4/10
 * desc    :
 */
public class ManifestRegisterException extends RuntimeException {
    ManifestRegisterException(String permission) {
        super(permission == null ?
                "No permissions are registered in the manifest file" :
                (permission + ": Permissions are not registered in the manifest file"));
    }
}
