package Presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import dao.AppDatabase;
import model.Activity;


public class ActivityPresenter {
    private static ActivityPresenter ourInstance;

    public static ActivityPresenter getInstance() {
        if(ourInstance == null)
            ourInstance = new ActivityPresenter();
        return ourInstance;
    }

    private ActivityPresenter() {
    }

    public void insertActivies(Context context, Activity activity) throws ExecutionException, InterruptedException {
        InsertActivitiesBackground i = new InsertActivitiesBackground(context);
        i.execute(activity).get();
    }

    public List<Activity> getActivities(Context context) throws ExecutionException, InterruptedException {
        GetActivitiesBackground task = new GetActivitiesBackground(context);
        return task.execute().get();
    }


    private class InsertActivitiesBackground extends AsyncTask<Activity, Void, String> {

        private Context context;

        public InsertActivitiesBackground(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(Activity... params) {
            AppDatabase.getAppDatabase(context).activityDao().insertAll(params);

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class GetActivitiesBackground extends AsyncTask<Void, List<Activity>,List<Activity>> {

        private Context context;

        public GetActivitiesBackground(Context context){
            this.context = context;
        }

        @Override
        protected List<Activity> doInBackground(Void... params) {


            return AppDatabase.getAppDatabase(context).activityDao().getAll();
        }

        @Override
        protected void onPreExecute() {}

    }
}
