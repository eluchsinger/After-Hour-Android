package ch.hsr.afterhour.gui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.TicketCategory;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

public class EventListActivity extends AppCompatActivity  implements EventListFragment.OnMyEventListListener {

    private final int FRAGMENT_CONTAINER = R.id.eventlist_fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(FRAGMENT_CONTAINER, new EventListFragment())
                .commit();
    }

    @Override
    public void onMyEventInteraction(Event item) {
    }

    @Override
    public void buyTicket(TicketCategory ticketCategory) {
        BuyTicketTask buyTicketTask = new BuyTicketTask();
        buyTicketTask.execute(ticketCategory);
    }

    private class BuyTicketTask extends AsyncTask<TicketCategory, Void, Boolean> {

        @Override
        protected Boolean doInBackground(TicketCategory... ticketCategories) {
            try {
                Application.get().getServerAPI().buyTicket(1, ticketCategories[0].getId());
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FoxHttpException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
            }
        }
    }
}
