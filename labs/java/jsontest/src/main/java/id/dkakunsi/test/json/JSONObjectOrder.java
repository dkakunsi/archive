package id.dkakunsi.test.json;

import org.json.JSONObject;

public class JSONObjectOrder {

    public static void main(String[] args) {
        JSONObject json = createObject();
        System.out.println(json);
    }
    
    private static JSONObject createObject() {
        JSONObject json = new JSONObject();
        json.put("one", 1);
        json.put("two", 2);
        json.put("one", "1");
        json.put("three", Integer.valueOf(3));
        return json;
    }

    /*
     * Question:
     * 
     * What is the right printed value of `json` object (could be more than one right answers):
     * 
     * * A. {"one":"1","two":2,"three":3}
     * * B. {"one":"1","two":"2","three":"3"}
     * * C. {"two":2,"one":"1","three":3}
     * * D. {"two":2,"one":1,"three":3} 
     * 
     * Answer   : A & C
     * Reason   : An object is an unordered collection of zero or more name/value pairs.
     * Reference:
     *            - http://www.rfc-editor.org/rfc/rfc7159.txt
     *            - https://github.com/stleary/JSON-java [Section Unit Test - Conventions]
     */
}
