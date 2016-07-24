package wsu.csc5991.trustcircle.task;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import wsu.csc5991.trustcircle.vo.Event;
import wsu.csc5991.trustcircle.vo.Member;

/**
 * Created by sasidhav on 7/23/16.
 */
public class RecordEventTask extends AsyncTask<String[], Void, Void> {

    @Override
    protected Void doInBackground(String[]... params) {
        try {
            Member member = new Member();
            System.out.println(params);
            System.out.println(params.length);
            System.out.println(params[0]);
            String[] arr = params[0];
            System.out.println(arr[0]+"-- "+ arr[1] +" -- " +arr[2]+" -- " +arr[3]);
            member.setMobileNumber(Integer.parseInt(arr[0]));
            member.setPin(Integer.parseInt(arr[1]));
            Event event = new Event();
            event.setName("Location");
            event.setValue(arr[2]);
            ArrayList<Event> eventList = new ArrayList<Event>();
            eventList.add(event);
            member.setEvents(eventList);
            String url = arr[3]+"/member/event";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            boolean output = restTemplate.postForObject(new URI(url), member, Boolean.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

}
