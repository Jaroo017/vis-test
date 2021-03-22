package com.osekj.vis.config.util

import com.osekj.vis.config.TestUser
import com.osekj.vis.util.JwtTokenUtil

class SecurityUtil {

    static String buildJWT(TestUser testUser) {
        JwtTokenUtil.generateToken(testUser.username, testUser.role, testUser.authorities)
    }
}
