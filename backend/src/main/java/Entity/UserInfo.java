package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class UserInfo {

    private String ipAdd;
    private List<String> responseId;
    private String semantics;
    private HashMap<String, String> responseList;

    // callbackArr contains flightId
    private String callbackFlight;

    // create UserInfo per user using IP address
    public UserInfo(String ipAdd, String semantics){
        this.ipAdd = ipAdd;
        this.semantics = semantics;
        this.responseId = new ArrayList<String>();
        this.responseList = new HashMap<String, String>();
        this.callbackFlight = "";
    }

    public String getIpAdd() {
        return ipAdd;
    }

    public void setIpAdd(String ipAdd) {
        this.ipAdd = ipAdd;
    }

    public List<String> getResponseId() {
        return responseId;
    }

    public void setResponseId(List<String> responseId) {
        this.responseId = responseId;
    }

    public void setResponse(String request, String response) {
        this.responseList.put(request, response);
    }

    public String getResponse(String request){
        String response = responseList.get(request);
        System.out.println("response: " + response);
        return response;
    }

    public String getSemantics() {
        return semantics;
    }

    public void setSemantics(String semantics) {
        this.semantics = semantics;
    }

    public String getCallbackFlight() {
        return callbackFlight;
    }

    public void setCallbackFlight(String callbackFlight) {
        this.callbackFlight = callbackFlight;
    }
}
