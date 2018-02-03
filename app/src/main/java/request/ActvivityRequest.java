package request;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.Activity;

public class ActvivityRequest extends AsyncTask<Void, List<Activity>, List<Activity>> {

    private ProgressDialog dialog;
    private android.app.Activity activity;

    public ActvivityRequest(android.app.Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

    }

    protected List<Activity> doInBackground(Void... args) {

        boolean hasNext = true;
        ArrayList<Activity> allActivities = new ArrayList<>();
        String url = "https://sandboxapi.campuse.ro/agenda/list/campus-party-brasil-2018?page=5";

        while (hasNext) {

            JSONObject response = null;
            try {
                GetRequest request = new GetRequest();
                String responseAsString = request.getJSON(url,50000);
                response = new JSONObject(responseAsString);
                int size = response.getJSONArray("results").length();
                for (int i = 0; i < size; i++) {
                    JSONObject activityJson = response.getJSONArray("results").getJSONObject(i);

                    Activity activity = new Activity(activityJson.getString("title"),
                            activityJson.getString("slug"),
                            activityJson.getString("description"),
                            activityJson.getString("start_date"),
                            activityJson.getString("end_date"),
                            activityJson.getString("get_type"),
                            activityJson.getString("stadium_name"));

                    allActivities.add(activity);
                }

                Comparator<Activity> cmp = new Comparator<Activity>() {
                    @Override
                    public int compare(Activity activity1, Activity activity2) {
                        return activity1.getStartDate().compareTo(activity2.getStartDate());
                    }
                };

                Collections.sort(allActivities, cmp);

                if(response.getString("next") == "null"){
                    hasNext = false;
                }else {
                    url = response.getString("next");
                }
                hasNext = false;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }

        }

        return allActivities;
    }
}
