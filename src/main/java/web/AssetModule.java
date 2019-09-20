/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import org.jooby.*;

/**
 *
 * @author vanta342
 */

public class AssetModule extends Jooby {
    public AssetModule() {
        assets("/*.html");
        assets("/css/*.css");
        assets("/js/*.js");
        assets("/images/*.png");
        assets("/images/*.jpg");
        // make index.html the default page
        assets("/", "index.html");
        // other html pages
        assets("/", "buy.html");
        assets("/", "createAccount.html");
        assets("/", "signin.html");
        assets("/", "viewProducts.html");
        assets("/", "viewCart.html");
        assets("/", "orderComplete.html");
        // prevent 404 errors due to browsers requesting favicons
        get("/favicon.ico", () -> Results.noContent());
    }
} 

