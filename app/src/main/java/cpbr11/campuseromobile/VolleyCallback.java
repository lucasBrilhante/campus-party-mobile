package cpbr11.campuseromobile;

import com.android.volley.VolleyError;

/**
 * Created by igor on 02/02/18.
 */

public interface VolleyCallback {
    void onResponse(String response);
    void onErrorResponse(VolleyError volleyError);
}
