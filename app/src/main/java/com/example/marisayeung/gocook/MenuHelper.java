package com.example.marisayeung.gocook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

/**
 * Created by marisayeung on 2/20/16.
 */
public class MenuHelper {
    public static boolean handleOnItemSelected(Context context, MenuItem item) {
        int id = item.getItemId();

//        Uninstall the app
        if (id == R.id.action_uninstall) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }

        return true;
    }
}

